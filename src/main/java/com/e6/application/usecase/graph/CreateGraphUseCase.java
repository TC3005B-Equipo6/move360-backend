package com.e6.application.usecase.graph;

import com.e6.application.dto.graph.CreateGraphDTO;
import com.e6.application.dto.graph.CreateGraphResponseDTO;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.repository.GraphRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateGraphUseCase {

    private final GraphRepository graphRepository;

    public CreateGraphUseCase(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    public CreateGraphResponseDTO execute(CreateGraphDTO createGraphDTO){
        Graph graph = new Graph();
        return  graphRepository.createGraph(graph);
    }
}
