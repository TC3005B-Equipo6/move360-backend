package com.e6.domain.repository;

import com.e6.domain.model.graph.Graph;
import com.e6.domain.model.graph.GraphSnapshot;

public interface GraphRepository {
    GraphSnapshot calculateSnapshot(Graph graph);

    Graph createGraph(Graph graph);

    Graph findGraphById(int id);

    Graph updateGraph(Graph graph);

    void deleteGraphById(int id);
}
