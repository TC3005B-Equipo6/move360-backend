package com.e6.application.usecase.graph;

import com.e6.domain.repository.GraphRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteGraphByIdUseCase {

    private final GraphRepository graphRepository;

    public DeleteGraphByIdUseCase(GraphRepository graphRepository) {
        this.graphRepository = graphRepository;
    }

    public void execute(int id){
        graphRepository.deleteGraphById(id);
    }
}
