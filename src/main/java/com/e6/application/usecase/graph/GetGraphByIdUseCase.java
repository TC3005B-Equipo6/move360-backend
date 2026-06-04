package com.e6.application.usecase.graph;

import com.e6.application.dto.graph.GetGraphResponseDTO;
import com.e6.domain.repository.GraphRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetGraphByIdUseCase {

    private final GraphRepository graphRepository;

    public GetGraphByIdUseCase(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    public GetGraphResponseDTO execute(int id) {
        return graphRepository.findGraphById(id);
    }
}
