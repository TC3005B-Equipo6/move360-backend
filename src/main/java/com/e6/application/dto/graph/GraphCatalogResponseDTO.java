package com.e6.application.dto.graph;

import com.e6.domain.model.graph.GraphCatalog;
import com.e6.domain.model.graph.GraphCatalog.GraphCatalogColumn;
import com.e6.domain.model.graph.GraphCatalog.GraphCatalogSource;
import com.e6.domain.model.graph.GraphCatalog.GraphCatalogTable;

import java.util.List;

public record GraphCatalogResponseDTO(List<GraphCatalogSourceDTO> sources) {
    public static GraphCatalogResponseDTO from(GraphCatalog catalog) {
        return new GraphCatalogResponseDTO(
                catalog.sources().stream()
                        .map(GraphCatalogSourceDTO::from)
                        .toList()
        );
    }

    public record GraphCatalogSourceDTO(
            int sourceId,
            String name,
            List<GraphCatalogTableDTO> tables
    ) {
        static GraphCatalogSourceDTO from(GraphCatalogSource source) {
            return new GraphCatalogSourceDTO(
                    source.sourceId(),
                    source.name(),
                    source.tables().stream().map(GraphCatalogTableDTO::from).toList()
            );
        }
    }

    public record GraphCatalogTableDTO(
            int tableId,
            String displayName,
            String defaultDimension,
            List<GraphCatalogColumnDTO> dimensions,
            List<GraphCatalogColumnDTO> metrics
    ) {
        static GraphCatalogTableDTO from(GraphCatalogTable table) {
            return new GraphCatalogTableDTO(
                    table.tableId(),
                    table.displayName(),
                    table.defaultDimension(),
                    table.dimensions().stream().map(GraphCatalogColumnDTO::from).toList(),
                    table.metrics().stream().map(GraphCatalogColumnDTO::from).toList()
            );
        }
    }

    public record GraphCatalogColumnDTO(
            String columnName,
            String displayName
    ) {
        static GraphCatalogColumnDTO from(GraphCatalogColumn column) {
            return new GraphCatalogColumnDTO(column.columnName(), column.displayName());
        }
    }
}
