package com.e6.infrastructure.repository;

import com.e6.application.dto.graph.CreateGraphResponseDTO;
import com.e6.application.dto.graph.GetGraphResponseDTO;
import com.e6.application.dto.graph.UpdateGraphResponseDTO;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.repository.GraphRepository;
import com.e6.infrastructure.entity.GraphEntity;
import com.e6.infrastructure.mapper.GraphMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GraphRepositoryImpl implements GraphRepository, PanacheRepositoryBase<GraphEntity, Integer> {

    @Override
    public CreateGraphResponseDTO createGraph(Graph graph) {

        persist(GraphMapper.toEntity(graph));
        //TODO: terminar método from de CreateGraphResponseDTO...
        return new CreateGraphResponseDTO();
    }

    @Override
    public GetGraphResponseDTO findGraphById(int id) {
        return null;
    }

    @Override
    public UpdateGraphResponseDTO updateGraph(Graph graph) {
        return null;
    }

    @Override
    public void deleteGraphById(int id) {

    }
}
