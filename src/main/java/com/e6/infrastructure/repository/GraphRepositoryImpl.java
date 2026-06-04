package com.e6.infrastructure.repository;

import com.e6.domain.exception.DashboardNotFoundException;
import com.e6.domain.exception.GraphNotFoundException;
import com.e6.domain.model.graph.Graph;
import com.e6.domain.repository.GraphRepository;
import com.e6.infrastructure.entity.DashboardEntity;
import com.e6.infrastructure.entity.GraphEntity;
import com.e6.infrastructure.mapper.GraphMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Map;

@ApplicationScoped
public class GraphRepositoryImpl implements GraphRepository, PanacheRepositoryBase<GraphEntity, Integer> {

    private static final String FETCH_GRAPH_HINT = "jakarta.persistence.fetchgraph";
    private static final String GRAPH_FULL = "Graph.full";

    @Override
    @Transactional
    public Graph createGraph(Graph graph) {
        DashboardEntity dashboard = getEntityManager().find(DashboardEntity.class, graph.getDashboardId());
        if (dashboard == null) {
            throw new DashboardNotFoundException(String.valueOf(graph.getDashboardId()));
        }

        GraphEntity entity = GraphMapper.toEntity(graph);
        entity.setDashboard(dashboard);
        persist(entity);
        return GraphMapper.toDomain(entity);
    }

    @Override
    public Graph findGraphById(int id) {
        GraphEntity entity = getEntityManager().find(
                GraphEntity.class,
                id,
                Map.of(FETCH_GRAPH_HINT, getEntityManager().getEntityGraph(GRAPH_FULL))
        );
        if (entity == null) {
            throw new GraphNotFoundException(String.valueOf(id));
        }
        return GraphMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public Graph updateGraph(Graph graph) {
        GraphEntity entity = findByIdOptional(graph.getId())
                .orElseThrow(() -> new GraphNotFoundException(String.valueOf(graph.getId())));
        GraphMapper.copyToEntity(graph, entity);
        return GraphMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public void deleteGraphById(int id) {
        boolean deleted = deleteById(id);
        if (!deleted) {
            throw new GraphNotFoundException(String.valueOf(id));
        }
    }
}
