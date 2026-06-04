package com.e6.application.usecase.graph;

import com.e6.application.dto.graph.GraphCatalogResponseDTO;
import com.e6.domain.repository.SourceRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetGraphCatalogUseCase {

    private final SourceRepository sourceRepository;

    public GetGraphCatalogUseCase(SourceRepository sourceRepository) {
        this.sourceRepository = sourceRepository;
    }

    public GraphCatalogResponseDTO execute() {
        return GraphCatalogResponseDTO.from(sourceRepository.getGraphCatalog());
    }
}
