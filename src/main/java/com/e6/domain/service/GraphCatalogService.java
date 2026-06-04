package com.e6.domain.service;

import com.e6.domain.model.graph.GraphCatalog;
import com.e6.domain.model.graph.GraphCatalog.GraphCatalogColumn;
import com.e6.domain.model.graph.GraphCatalog.GraphCatalogSource;
import com.e6.domain.model.graph.GraphCatalog.GraphCatalogTable;
import com.e6.domain.model.source.Filter;
import com.e6.domain.model.source.Source;
import com.e6.domain.model.source.Table;
import com.e6.domain.repository.SourceRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class GraphCatalogService {

    private static final GraphCatalogColumn MONTH_DIMENSION = new GraphCatalogColumn("month", "Mes");
    private static final GraphCatalogColumn YEAR_DIMENSION = new GraphCatalogColumn("year", "Anio");
    private static final GraphCatalogColumn PASSENGERS_METRIC = new GraphCatalogColumn("passengers", "Pasajeros");

    private final SourceRepository sourceRepository;

    public GraphCatalogService(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    public GraphCatalog catalog() {
        List<Source> sources = sourceRepository.getCatalogSources();
        List<GraphCatalogSource> catalogSources = new ArrayList<>();

        for (int sourceIndex = 0; sourceIndex < sources.size(); sourceIndex++) {
            Source source = sources.get(sourceIndex);
            List<GraphCatalogTable> tables = new ArrayList<>();

            for (int tableIndex = 0; tableIndex < source.tables().size(); tableIndex++) {
                tables.add(toCatalogTable(sourceIndex, tableIndex, source.tables().get(tableIndex)));
            }

            catalogSources.add(new GraphCatalogSource(sourceIndex, source.name(), tables));
        }

        return new GraphCatalog(catalogSources);
    }

    public GraphCatalogTable resolveTable(int sourceId, int tableId) {
        return catalog().sources().stream()
                .filter(source -> source.sourceId() == sourceId)
                .findFirst()
                .flatMap(source -> source.tables().stream()
                        .filter(table -> table.tableId() == tableId)
                        .findFirst())
                .orElseThrow(() -> new IllegalArgumentException("Graph table not found: " + sourceId + "/" + tableId));
    }

    public void requireDimension(GraphCatalogTable table, String dimensionColumn) {
        if (dimensionColumn == null || table.dimensions().stream().noneMatch(column -> column.columnName().equals(dimensionColumn))) {
            throw new IllegalArgumentException("Invalid dimensionColumn: " + dimensionColumn);
        }
    }

    public void requireMetrics(GraphCatalogTable table, List<String> metricColumns) {
        if (metricColumns == null || metricColumns.isEmpty()) {
            throw new IllegalArgumentException("metricColumns must contain at least one metric");
        }

        for (String metricColumn : metricColumns) {
            if (table.metrics().stream().noneMatch(column -> column.columnName().equals(metricColumn))) {
                throw new IllegalArgumentException("Invalid metricColumn: " + metricColumn);
            }
        }
    }

    public String metricLabel(GraphCatalogTable table, String metricColumn) {
        return table.metrics().stream()
                .filter(column -> column.columnName().equals(metricColumn))
                .map(GraphCatalogColumn::displayName)
                .findFirst()
                .orElse(metricColumn);
    }

    private GraphCatalogTable toCatalogTable(int sourceId, int tableId, Table table) {
        boolean daily = table.filters() != null && !table.filters().isEmpty();

        Map<String, GraphCatalogColumn> dimensions = new LinkedHashMap<>();
        dimensions.put(MONTH_DIMENSION.columnName(), MONTH_DIMENSION);
        dimensions.put(YEAR_DIMENSION.columnName(), YEAR_DIMENSION);

        if (table.filters() != null) {
            for (Filter filter : table.filters()) {
                dimensions.putIfAbsent(
                        filter.columnName(),
                        new GraphCatalogColumn(filter.columnName(), filter.displayName()));
            }
        }

        List<GraphCatalogColumn> metrics = new ArrayList<>();
        if (table.columns() != null && !table.columns().isEmpty()) {
            metrics = table.columns().stream()
                    .map(column -> new GraphCatalogColumn(column.columnName(), column.displayName()))
                    .toList();
        } else {
            metrics.add(PASSENGERS_METRIC);
        }

        return new GraphCatalogTable(
                sourceId,
                tableId,
                table.displayName(),
                table.tableName(),
                MONTH_DIMENSION.columnName(),
                List.copyOf(dimensions.values()),
                metrics,
                daily);
    }
}
