package com.e6.domain.repository;

import com.e6.application.dto.indicator.IndicatorFiltersDTO;
import com.e6.application.dto.source.*;
import com.e6.domain.model.graph.GraphCatalog;
import com.e6.domain.model.graph.GraphCatalog.GraphCatalogTable;
import com.e6.domain.model.source.Metadata;

import java.util.List;

public interface SourceRepository {
    List<SourceItemResponseDTO> getSource();
    List<SourceItemResponseDTO> getTables(int id);
    List<SourceItemResponseDTO> getColumns(int id);
    List<FilterResponseDTO> getFilters(int id);
    Metadata getMetadata(int sourceId, int tableId, int columnId, IndicatorFiltersDTO filters);
    GraphCatalog getGraphCatalog();
    GraphCatalogTable resolveGraphTable(int sourceId, int tableId);
    void requireGraphDimension(GraphCatalogTable table, String dimensionColumn);
    void requireGraphMetrics(GraphCatalogTable table, List<String> metricColumns);
    String getGraphMetricLabel(GraphCatalogTable table, String metricColumn);
}
