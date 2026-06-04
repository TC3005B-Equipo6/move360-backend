package com.e6.infrastructure.repository;

import com.e6.application.dto.indicator.IndicatorFiltersDTO;
import com.e6.application.dto.source.FilterResponseDTO;
import com.e6.application.dto.source.SourceItemResponseDTO;
import com.e6.domain.model.graph.GraphCatalog;
import com.e6.domain.model.graph.GraphCatalog.GraphCatalogColumn;
import com.e6.domain.model.graph.GraphCatalog.GraphCatalogSource;
import com.e6.domain.model.graph.GraphCatalog.GraphCatalogTable;
import com.e6.domain.model.source.Filter;
import com.e6.domain.model.source.FilterMetadata;
import com.e6.domain.model.source.Metadata;
import com.e6.domain.model.source.Source;
import com.e6.domain.model.source.Table;
import com.e6.domain.repository.SourceRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ApplicationScoped
public class SourceRepositoryImpl implements SourceRepository {

    private static final GraphCatalogColumn MONTH_DIMENSION = new GraphCatalogColumn("month", "Mes");
    private static final GraphCatalogColumn YEAR_DIMENSION = new GraphCatalogColumn("year", "Anio");
    private static final GraphCatalogColumn PASSENGERS_METRIC = new GraphCatalogColumn("passengers", "Pasajeros");

    private final List<Source> sources;

    public SourceRepositoryImpl(ObjectMapper mapper) throws IOException {

        TypeReference<List<Source>> type = new TypeReference<>() {};

        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("sources.json");

        if (is == null) {
            throw new IllegalStateException("sources.json not found");
        }

        this.sources = mapper.readValue(is, type);
    }


    @Override
    public List<SourceItemResponseDTO> getSource() {
        return  sources.stream()
                .map(t -> SourceItemResponseDTO.of(
                        t.id(),
                        t.name()))
                .toList();
    }

    @Override
    public List<SourceItemResponseDTO> getTables(int id) {
        return sources.get(id).tables().stream()
                .map(t -> SourceItemResponseDTO.of(
                        t.id(),
                        t.displayName()))
                .toList();
    }

    @Override
    public List<SourceItemResponseDTO> getColumns(int id) {
        return sources.getFirst().tables().get(id).columns().stream()
                .map(t -> SourceItemResponseDTO.of(
                        t.id(),
                        t.displayName()))
                .toList();
    }

    @Override
    public List<FilterResponseDTO> getFilters(int id) {
        return sources.getLast().tables().get(id).filters().stream()
                .map(t -> FilterResponseDTO.of(
                        t.id(),
                        t.displayName(),
                        t.values().toArray(new String[0])))
                .toList();
    }

    @Override
    public Metadata getMetadata(
            int sourceId,
            int tableId,
            int columnId,
            IndicatorFiltersDTO filtersDto) {

        var source = sources.get(sourceId);
        var table = source.tables().get(tableId);

        switch (sourceId) {

            case 0:
                return new Metadata(
                        table.tableName(),
                        table.columns()
                                .get(columnId)
                                .columnName(),
                        Set.of()
                );

            case 1:

                if (filtersDto.ids().length != filtersDto.values().length) {
                    throw new IllegalArgumentException(
                            "Filter ids and values must have the same length");
                }

                Set<FilterMetadata> resolvedFilters =
                        IntStream.range(0, filtersDto.ids().length)
                                .mapToObj(i -> {

                                    int filterId = filtersDto.ids()[i];
                                    String value = filtersDto.values()[i];

                                    var filter = table.filters()
                                            .stream()
                                            .filter(f -> f.id() == filterId)
                                            .findFirst()
                                            .orElseThrow(() ->
                                                    new IllegalArgumentException(
                                                            "Filter not found: " + filterId));

                                    return new FilterMetadata(
                                            filter.columnName(),
                                            value
                                    );
                                })
                                .collect(Collectors.toSet());

                return new Metadata(
                        table.tableName(),
                        "passengers",
                        resolvedFilters
                );

            default:
                throw new IllegalArgumentException(
                    "Unsupported sourceId: " + sourceId);
        }
    }

    @Override
    public GraphCatalog getGraphCatalog() {
        List<GraphCatalogSource> catalogSources = new ArrayList<>();

        for (int sourceIndex = 0; sourceIndex < sources.size(); sourceIndex++) {
            Source source = sources.get(sourceIndex);
            List<GraphCatalogTable> tables = new ArrayList<>();

            for (int tableIndex = 0; tableIndex < source.tables().size(); tableIndex++) {
                tables.add(toGraphCatalogTable(sourceIndex, tableIndex, source.tables().get(tableIndex)));
            }

            catalogSources.add(new GraphCatalogSource(sourceIndex, source.name(), tables));
        }

        return new GraphCatalog(catalogSources);
    }

    @Override
    public GraphCatalogTable resolveGraphTable(int sourceId, int tableId) {
        return getGraphCatalog().sources().stream()
                .filter(source -> source.sourceId() == sourceId)
                .findFirst()
                .flatMap(source -> source.tables().stream()
                        .filter(table -> table.tableId() == tableId)
                        .findFirst())
                .orElseThrow(() -> new IllegalArgumentException("Graph table not found: " + sourceId + "/" + tableId));
    }

    @Override
    public void requireGraphDimension(GraphCatalogTable table, String dimensionColumn) {
        if (dimensionColumn == null || table.dimensions().stream().noneMatch(column -> column.columnName().equals(dimensionColumn))) {
            throw new IllegalArgumentException("Invalid dimensionColumn: " + dimensionColumn);
        }
    }

    @Override
    public void requireGraphMetrics(GraphCatalogTable table, List<String> metricColumns) {
        if (metricColumns == null || metricColumns.isEmpty()) {
            throw new IllegalArgumentException("metricColumns must contain at least one metric");
        }

        for (String metricColumn : metricColumns) {
            if (table.metrics().stream().noneMatch(column -> column.columnName().equals(metricColumn))) {
                throw new IllegalArgumentException("Invalid metricColumn: " + metricColumn);
            }
        }
    }

    @Override
    public String getGraphMetricLabel(GraphCatalogTable table, String metricColumn) {
        return table.metrics().stream()
                .filter(column -> column.columnName().equals(metricColumn))
                .map(GraphCatalogColumn::displayName)
                .findFirst()
                .orElse(metricColumn);
    }

    private GraphCatalogTable toGraphCatalogTable(int sourceId, int tableId, Table table) {
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
