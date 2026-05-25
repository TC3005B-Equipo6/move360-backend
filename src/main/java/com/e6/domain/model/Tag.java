package com.e6.domain.model;

import java.util.HashSet;
import java.util.Set;

public class Tag {
    private int id;
    private String name;
    private Color color;
    private Set<Dashboard> dashboards = new HashSet<>();

    public Tag() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String name;
        private Color color;
        private Set<Dashboard> dashboards = new HashSet<>();

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        public Builder dashboards(Set<Dashboard> dashboards) {
            this.dashboards = dashboards;
            return this;
        }

        public Tag build() {
            Tag tag = new Tag();
            tag.id = this.id;
            tag.name = this.name;
            tag.color = this.color;
            tag.dashboards = this.dashboards;
            return tag;
        }
    }

    public Tag(int id, String name, Color color, Set<Dashboard> dashboards) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.dashboards = dashboards;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Set<Dashboard> getDashboards() {
        return dashboards;
    }
}
