package com.e6.domain.model.graph;

import java.util.List;

public record GraphCatalog(List<GraphCatalogSource> sources) {
    public record GraphCatalogSource(
            int sourceId,
            String name,
            List<GraphCatalogTable> tables
    ) {
    }

    public record GraphCatalogTable(
            int sourceId,
            int tableId,
            String displayName,
            String tableName,
            String defaultDimension,
            List<GraphCatalogColumn> dimensions,
            List<GraphCatalogColumn> metrics,
            boolean daily
    ) {
    }

    public record GraphCatalogColumn(
            String columnName,
            String displayName
    ) {
    }
}
