package com.e6.domain.model;

import com.e6.domain.model.graph.Graph;

public class Series {
    private int id;
    private String data;
    private Graph graph;

    public Series(){}

    public Series(int id, String data, Graph graph) {
        this.id = id;
        this.data = data;
        this.graph = graph;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
