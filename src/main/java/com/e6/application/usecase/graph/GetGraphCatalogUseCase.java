package com.e6.application.usecase.graph;

import com.e6.application.dto.graph.GraphCatalogResponseDTO;
import com.e6.domain.service.GraphCatalogService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetGraphCatalogUseCase {

    private final GraphCatalogService graphCatalogService;

    public GetGraphCatalogUseCase(GraphCatalogService graphCatalogService) {
        this.graphCatalogService = graphCatalogService;
    }

    public GraphCatalogResponseDTO execute() {
        return GraphCatalogResponseDTO.from(graphCatalogService.catalog());
    }
}
