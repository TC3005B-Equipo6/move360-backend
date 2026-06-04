package com.e6.domain.repository;

import com.e6.domain.model.graph.Graph;

public interface GraphRepository {
    Graph createGraph(Graph graph);

    Graph findGraphById(int id);

    Graph updateGraph(Graph graph);

    void deleteGraphById(int id);
}
