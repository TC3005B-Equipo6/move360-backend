package org.acme.domain.model;

import java.util.UUID;

public class Series {
    private UUID id;
    private String data;
    private Graph graph;

    public Series(){}

    public Series(UUID id, String data, Graph graph) {
        this.id = id;
        this.data = data;
        this.graph = graph;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }
}
