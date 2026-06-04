package com.e6.infrastructure.repository;

import com.e6.domain.model.graph.Graph;
import com.e6.domain.model.graph.GraphCatalog.GraphCatalogTable;
import com.e6.domain.model.graph.GraphSeries;
import com.e6.domain.model.graph.GraphSnapshot;
import com.e6.domain.model.graph.GraphType;
import com.e6.domain.repository.GraphDataQuery;
import com.e6.domain.service.GraphCatalogService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class GraphDataQueryImpl implements GraphDataQuery {

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

    private final EntityManager entityManager;
    private final GraphCatalogService graphCatalogService;

    public GraphDataQueryImpl(EntityManager entityManager, GraphCatalogService graphCatalogService) {
        this.entityManager = entityManager;
        this.graphCatalogService = graphCatalogService;
    }

    @Override
    public GraphSnapshot calculate(Graph graph) {
        GraphCatalogTable table = graphCatalogService.resolveTable(graph.getSourceId(), graph.getTableId());
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
            GraphCatalogTable compareTable = graphCatalogService.resolveTable(graph.getSourceId(), graph.getCompareTableId());
            graphCatalogService.requireDimension(compareTable, graph.getDimensionColumn());
            graphCatalogService.requireMetrics(compareTable, graph.getMetricColumns());
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

    private void validateGraph(GraphCatalogTable table, Graph graph) {
        if (graph.getType() == null) {
            throw new IllegalArgumentException("type is required");
        }
        if (graph.getOperation() == null) {
            throw new IllegalArgumentException("operation is required");
        }
        graphCatalogService.requireDimension(table, graph.getDimensionColumn());
        graphCatalogService.requireMetrics(table, graph.getMetricColumns());
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

        Query query = entityManager.createNativeQuery(sql.toString());
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

        Query query = entityManager.createNativeQuery(sql.toString());
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
                    graphCatalogService.metricLabel(table, metricColumn),
                    COLORS[index % COLORS.length],
                    seriesData(rows, metricColumn)));
            index++;
        }

        if (graph.isCompareEnabled() && graph.getCompareTableId() != null) {
            GraphCatalogTable compareTable = graphCatalogService.resolveTable(graph.getSourceId(), graph.getCompareTableId());
            for (String metricColumn : graph.getMetricColumns()) {
                String key = compareAlias(metricColumn);
                series.add(new GraphSeries(
                        0,
                        key,
                        compareTable.displayName() + " " + graphCatalogService.metricLabel(compareTable, metricColumn),
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
