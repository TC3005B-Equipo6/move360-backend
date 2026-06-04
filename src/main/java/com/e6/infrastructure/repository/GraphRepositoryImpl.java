package com.e6.infrastructure.repository;

import com.e6.domain.exception.DashboardNotFoundException;
import com.e6.domain.exception.GraphNotFoundException;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.model.graph.GraphCatalog.GraphCatalogTable;
import com.e6.domain.model.graph.GraphSeries;
import com.e6.domain.model.graph.GraphSnapshot;
import com.e6.domain.model.graph.GraphType;
import com.e6.domain.repository.GraphRepository;
import com.e6.domain.repository.SourceRepository;
import com.e6.infrastructure.entity.DashboardEntity;
import com.e6.infrastructure.entity.GraphEntity;
import com.e6.infrastructure.mapper.GraphMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class GraphRepositoryImpl implements GraphRepository, PanacheRepositoryBase<GraphEntity, Integer> {

    private static final String FETCH_GRAPH_HINT = "jakarta.persistence.fetchgraph";
    private static final String GRAPH_FULL = "Graph.full";
    private static final String[] COLORS = {
            "#2563EB",
            "#059669",
            "#D97706",
            "#DC2626",
            "#7C3AED",
            "#0891B2",
            "#4B5563",
            "#DB2777"
    };

    private final SourceRepository sourceRepository;

    public GraphRepositoryImpl(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    @Override
    public GraphSnapshot calculateSnapshot(Graph graph) {
        GraphCatalogTable table = sourceRepository.resolveGraphTable(graph.getSourceId(), graph.getTableId());
        validateGraph(table, graph);

        YearMonth startMonth = parseMonth(graph.getStartMonth(), "startMonth");
        YearMonth endMonth = parseMonth(graph.getEndMonth(), "endMonth");
        if (endMonth.isBefore(startMonth)) {
            throw new IllegalArgumentException("endMonth must be greater than or equal to startMonth");
        }

        LocalDate startDate = startMonth.atDay(1);
        LocalDate endDate = endMonth.atEndOfMonth();
        List<Map<String, Object>> rows = fetchRows(table, graph, startDate, endDate, false);

        if (graph.isCompareEnabled() && graph.getCompareTableId() != null) {
            GraphCatalogTable compareTable = sourceRepository.resolveGraphTable(graph.getSourceId(), graph.getCompareTableId());
            sourceRepository.requireGraphDimension(compareTable, graph.getDimensionColumn());
            sourceRepository.requireGraphMetrics(compareTable, graph.getMetricColumns());
            mergeCompareRows(rows, fetchRows(compareTable, graph, startDate, endDate, true));
        }

        long monthCount = ChronoUnit.MONTHS.between(startMonth, endMonth) + 1;
        LocalDate previousStart = startMonth.minusMonths(monthCount).atDay(1);
        LocalDate previousEnd = startMonth.minusMonths(1).atEndOfMonth();
        double actualTotal = fetchTotal(table, graph, startDate, endDate);
        double previousTotal = fetchTotal(table, graph, previousStart, previousEnd);
        Double delta = Math.abs(previousTotal) < 0.0000001
                ? null
                : ((actualTotal - previousTotal) / previousTotal) * 100.0;

        return new GraphSnapshot(delta, rows, buildSeries(table, graph, rows));
    }

    @Override
    @Transactional
    public Graph createGraph(Graph graph) {
        DashboardEntity dashboard = getEntityManager().find(DashboardEntity.class, graph.getDashboardId());
        if (dashboard == null) {
            throw new DashboardNotFoundException(String.valueOf(graph.getDashboardId()));
        }

        GraphEntity entity = GraphMapper.toEntity(graph);
        entity.setDashboard(dashboard);
        persist(entity);
        flush();
        return GraphMapper.toDomain(entity);
    }

    @Override
    public Graph findGraphById(int id) {
        GraphEntity entity = getEntityManager().find(
                GraphEntity.class,
                id,
                Map.of(FETCH_GRAPH_HINT, getEntityManager().getEntityGraph(GRAPH_FULL))
        );
        if (entity == null) {
            throw new GraphNotFoundException(String.valueOf(id));
        }
        return GraphMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public Graph updateGraph(Graph graph) {
        GraphEntity entity = findByIdOptional(graph.getId())
                .orElseThrow(() -> new GraphNotFoundException(String.valueOf(graph.getId())));
        GraphMapper.copyToEntity(graph, entity);
        flush();
        return GraphMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void deleteGraphById(int id) {
        boolean deleted = deleteById(id);
        if (!deleted) {
            throw new GraphNotFoundException(String.valueOf(id));
        }
    }

    private void validateGraph(GraphCatalogTable table, Graph graph) {
        if (graph.getType() == null) {
            throw new IllegalArgumentException("type is required");
        }
        if (graph.getOperation() == null) {
            throw new IllegalArgumentException("operation is required");
        }
        sourceRepository.requireGraphDimension(table, graph.getDimensionColumn());
        sourceRepository.requireGraphMetrics(table, graph.getMetricColumns());
        if (graph.getType() == GraphType.RANKING && graph.getMetricColumns().size() != 1) {
            throw new IllegalArgumentException("RANKING graphs require exactly one metricColumn");
        }
    }

    private List<Map<String, Object>> fetchRows(GraphCatalogTable table, Graph graph, LocalDate startDate, LocalDate endDate, boolean compare) {
        DimensionSql dimensionSql = dimensionSql(graph.getDimensionColumn());
        List<String> metricColumns = graph.getMetricColumns();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(dimensionSql.select())
                .append(" AS name");

        for (String metricColumn : metricColumns) {
            String alias = compare ? compareAlias(metricColumn) : metricColumn;
            sql.append(", COALESCE(")
                    .append(graph.getOperation().name())
                    .append("(")
                    .append(metricColumn)
                    .append("), 0) AS ")
                    .append(alias);
        }

        sql.append(" FROM ")
                .append(table.tableName())
                .append(" WHERE ")
                .append(dateExpression(table))
                .append(" BETWEEN :startDate AND :endDate")
                .append(" GROUP BY ")
                .append(dimensionSql.groupBy());

        if (graph.getType() == GraphType.RANKING) {
            sql.append(" ORDER BY ")
                    .append(compare ? compareAlias(metricColumns.getFirst()) : metricColumns.getFirst())
                    .append(" DESC");
        } else {
            sql.append(" ORDER BY ")
                    .append(dimensionSql.orderBy());
        }

        Query query = getEntityManager().createNativeQuery(sql.toString());
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        List<?> resultList = query.getResultList();
        List<Map<String, Object>> rows = new ArrayList<>();
        for (Object result : resultList) {
            Object[] values = (Object[]) result;
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("name", values[0] == null ? "" : values[0].toString());
            for (int index = 0; index < metricColumns.size(); index++) {
                String key = compare ? compareAlias(metricColumns.get(index)) : metricColumns.get(index);
                row.put(key, toDouble(values[index + 1]));
            }
            rows.add(row);
        }
        return rows;
    }

    private void mergeCompareRows(List<Map<String, Object>> rows, List<Map<String, Object>> compareRows) {
        Map<String, Map<String, Object>> byName = new LinkedHashMap<>();
        for (Map<String, Object> row : rows) {
            byName.put(row.get("name").toString(), row);
        }

        for (Map<String, Object> compareRow : compareRows) {
            String name = compareRow.get("name").toString();
            Map<String, Object> row = byName.computeIfAbsent(name, key -> {
                Map<String, Object> created = new LinkedHashMap<>();
                created.put("name", key);
                rows.add(created);
                return created;
            });
            compareRow.forEach((key, value) -> {
                if (!"name".equals(key)) {
                    row.put(key, value);
                }
            });
        }
    }

    private double fetchTotal(GraphCatalogTable table, Graph graph, LocalDate startDate, LocalDate endDate) {
        StringBuilder sql = new StringBuilder("SELECT ");
        for (int index = 0; index < graph.getMetricColumns().size(); index++) {
            if (index > 0) {
                sql.append(", ");
            }
            sql.append("COALESCE(")
                    .append(graph.getOperation().name())
                    .append("(")
                    .append(graph.getMetricColumns().get(index))
                    .append("), 0)");
        }

        sql.append(" FROM ")
                .append(table.tableName())
                .append(" WHERE ")
                .append(dateExpression(table))
                .append(" BETWEEN :startDate AND :endDate");

        Query query = getEntityManager().createNativeQuery(sql.toString());
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        Object result = query.getSingleResult();

        if (result instanceof Object[] values) {
            double total = 0;
            for (Object value : values) {
                total += toDouble(value);
            }
            return total;
        }

        return toDouble(result);
    }

    private List<GraphSeries> buildSeries(GraphCatalogTable table, Graph graph, List<Map<String, Object>> rows) {
        List<GraphSeries> series = new ArrayList<>();
        int index = 0;
        for (String metricColumn : graph.getMetricColumns()) {
            series.add(new GraphSeries(
                    0,
                    metricColumn,
                    sourceRepository.getGraphMetricLabel(table, metricColumn),
                    COLORS[index % COLORS.length],
                    seriesData(rows, metricColumn)));
            index++;
        }

        if (graph.isCompareEnabled() && graph.getCompareTableId() != null) {
            GraphCatalogTable compareTable = sourceRepository.resolveGraphTable(graph.getSourceId(), graph.getCompareTableId());
            for (String metricColumn : graph.getMetricColumns()) {
                String key = compareAlias(metricColumn);
                series.add(new GraphSeries(
                        0,
                        key,
                        compareTable.displayName() + " " + sourceRepository.getGraphMetricLabel(compareTable, metricColumn),
                        COLORS[index % COLORS.length],
                        seriesData(rows, key)));
                index++;
            }
        }

        return series;
    }

    private List<Map<String, Object>> seriesData(List<Map<String, Object>> rows, String key) {
        List<Map<String, Object>> data = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> point = new LinkedHashMap<>();
            point.put("name", row.get("name"));
            point.put("value", row.get(key));
            data.add(point);
        }
        return data;
    }

    private YearMonth parseMonth(String value, String field) {
        try {
            return YearMonth.parse(value);
        } catch (DateTimeParseException | NullPointerException e) {
            throw new IllegalArgumentException(field + " must use YYYY-MM format");
        }
    }

    private String dateExpression(GraphCatalogTable table) {
        if (table.daily()) {
            return "DATE(CONCAT(year, '-', month, '-', day))";
        }
        return "STR_TO_DATE(CONCAT(year, '-', month, '-01'), '%Y-%m-%d')";
    }

    private DimensionSql dimensionSql(String dimensionColumn) {
        return switch (dimensionColumn) {
            case "month" -> new DimensionSql(
                    "CONCAT(year, '-', LPAD(month, 2, '0'))",
                    "year, month",
                    "year, month");
            case "year" -> new DimensionSql(
                    "CAST(year AS CHAR)",
                    "year",
                    "year");
            default -> new DimensionSql(
                    dimensionColumn,
                    dimensionColumn,
                    dimensionColumn);
        };
    }

    private String compareAlias(String metricColumn) {
        return metricColumn + "_compare";
    }

    private double toDouble(Object value) {
        if (value == null) {
            return 0.0;
        }
        if (value instanceof Number number) {
            return number.doubleValue();
        }
        return Double.parseDouble(value.toString());
    }

    private record DimensionSql(String select, String groupBy, String orderBy) {
    }
}
