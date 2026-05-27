package com.e6.infrastructure.mapper;

import com.e6.domain.model.Graph;
import com.e6.infrastructure.entity.GraphEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class GraphMapper {

    public static Graph toDomain(GraphEntity entity) {
        Graph graph = new Graph();
        graph.setId(entity.getId() == null ? 0 : entity.getId());
        graph.setQuery(entity.getQuery());
        graph.setStartDate(entity.getStartDate());
        graph.setEndDate(entity.getEndDate());
        graph.setCoordinate(entity.getCoordinate());
        graph.setDashboard(DashboardMapper.toDomainSimple(entity.getDashboard()));
        // TODO: crear SourceMapper y mapear entity.getSources() -> graph.setSources(...)
        graph.setSources(new HashSet<>());
        return graph;
    }

    public static Graph toDomainWithoutDashboard(GraphEntity entity) {
        Graph graph = new Graph();
        graph.setId(entity.getId() == null ? 0 : entity.getId());
        graph.setQuery(entity.getQuery());
        graph.setStartDate(entity.getStartDate());
        graph.setEndDate(entity.getEndDate());
        graph.setCoordinate(entity.getCoordinate());
        // TODO: crear SourceMapper y mapear entity.getSources() -> graph.setSources(...)
        graph.setSources(new HashSet<>());
        return graph;
    }

    public static Set<Graph> toDomainSetWithoutDashboard(Set<GraphEntity> entities) {
        return entities.stream()
                .map(GraphMapper::toDomainWithoutDashboard)
                .collect(Collectors.toSet());
    }

    public static GraphEntity toEntity(Graph graph) {
        GraphEntity entity = new GraphEntity();
        if (graph.getId() != 0) {
            entity.setId(graph.getId());
        }
        entity.setQuery(graph.getQuery());
        entity.setStartDate(graph.getStartDate());
        entity.setEndDate(graph.getEndDate());
        entity.setCoordinate(graph.getCoordinate());
        if (graph.getDashboard() != null) {
            entity.setDashboard(DashboardMapper.toEntity(graph.getDashboard()));
        }
        // TODO: crear SourceMapper y setear entity.setSources(...) cuando persistencia anidada de sources se necesite
        return entity;
    }

    public static Set<GraphEntity> toEntitySet(Set<Graph> graphs) {
        return graphs.stream()
                .map(GraphMapper::toEntity)
                .collect(Collectors.toSet());
    }
}
