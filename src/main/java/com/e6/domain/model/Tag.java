package com.e6.domain.model;

import java.util.HashSet;
import java.util.Set;

public class Tag {
    private int id;
    private String name;
    private Color color;
    private Set<Dashboard> dashboards = new HashSet<>();

    public Tag () {}

    public Tag (int id, String name, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Dashboard> getDashboards() {
        return dashboards;
    }

    public void setDashboards(Set<Dashboard> dashboards) {
        this.dashboards = dashboards;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
