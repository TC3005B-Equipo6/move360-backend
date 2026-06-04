package com.e6.domain.model.graph;

import com.e6.domain.model.Dashboard;
import com.e6.domain.model.Indicator.Coordinate;
import com.e6.domain.model.source.Source;

import java.time.LocalDate;

public class Graph {
    private int id;
    private Size size;
    private GraphType type;
    private Source source;
    private String query;
    private LocalDate startDate;
    private LocalDate endDate;
    private Dashboard dashboard;
    private Coordinate coordinate;

    public Graph() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private Size size;
        private GraphType type;
        private Source source;
        private String query;
        private LocalDate startDate;
        private LocalDate endDate;
        private Dashboard dashboard;
        private Coordinate coordinate;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder size(Size size){
            this.size = size;
            return this;
        }

        public Builder type(GraphType type){
            this.type = type;
            return this;
        }

        public Builder query(String query) {
            this.query = query;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder dashboard(Dashboard dashboard) {
            this.dashboard = dashboard;
            return this;
        }

        public Builder coordinate(Coordinate coordinate) {
            this.coordinate = coordinate;
            return this;
        }
        public Builder sources(Source source){
            this.source = source;
            return this;
        }

        public Graph build() {
            Graph graph = new Graph();
            graph.id = this.id;
            graph.size = this.size;
            graph.type = this.type;
            graph.source = this.source;
            graph.query = this.query;
            graph.startDate = this.startDate;
            graph.endDate = this.endDate;
            graph.dashboard = this.dashboard;
            graph.coordinate = this.coordinate;
            return graph;
        }
    }

    public Graph(int id, Size size, GraphType type, Source source, String query, LocalDate startDate, LocalDate endDate, Dashboard dashboard, Coordinate coordinate) {
        this.id = id;
        this.size = size;
        this.type = type;
        this.source = source;
        this.query = query;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dashboard = dashboard;
        this.coordinate = coordinate;
    }

    public int getId() {
        return id;
    }

    public Size getSize() {
        return size;
    }

    public GraphType getType() {
        return type;
    }

    public Source getSource() {
        return source;
    }

    public String getQuery() {
        return query;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
