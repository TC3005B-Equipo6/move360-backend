package com.e6.application.usecase.graph;

import com.e6.application.dto.graph.UpdateGraphDTO;
import com.e6.application.dto.graph.UpdateGraphResponseDTO;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.repository.GraphRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UpdateGraphUseCase {

    private final GraphRepository graphRepository;

    public UpdateGraphUseCase(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    public UpdateGraphResponseDTO execute(int id, UpdateGraphDTO updateGraphDTO){
        Graph graph = new Graph();
        return graphRepository.updateGraph(graph);
    }
}
