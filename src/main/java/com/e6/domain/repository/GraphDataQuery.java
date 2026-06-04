package com.e6.domain.repository;

import com.e6.domain.model.graph.Graph;
import com.e6.domain.model.graph.GraphSnapshot;

public interface GraphDataQuery {
    GraphSnapshot calculate(Graph graph);
}
