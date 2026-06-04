package com.e6.domain.model.graph;

import java.util.List;
import java.util.Map;

public class GraphSeries {
    private int id;
    private String seriesKey;
    private String label;
    private String color;
    private List<Map<String, Object>> data;

    public GraphSeries() {
    }

    public GraphSeries(int id, String seriesKey, String label, String color, List<Map<String, Object>> data) {
        this.id = id;
        this.seriesKey = seriesKey;
        this.label = label;
        this.color = color;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public String getSeriesKey() {
        return seriesKey;
    }

    public String getLabel() {
        return label;
    }

    public String getColor() {
        return color;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }
}
