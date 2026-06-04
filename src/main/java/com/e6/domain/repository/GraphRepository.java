package com.e6.domain.repository;

import com.e6.application.dto.graph.GetGraphResponseDTO;
import com.e6.application.dto.graph.CreateGraphResponseDTO;
import com.e6.application.dto.graph.UpdateGraphResponseDTO;
import com.e6.domain.model.graph.Graph;

public interface GraphRepository {
    CreateGraphResponseDTO createGraph(Graph graph);

    GetGraphResponseDTO findGraphById(int id);

    UpdateGraphResponseDTO updateGraph(Graph graph);

    void deleteGraphById(int id);
}
