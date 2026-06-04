package com.e6.domain.model.graph;

import com.e6.domain.model.Dashboard;
import com.e6.domain.model.Indicator.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Graph {
    private int id;
    private UUID dashboardId;
    private String title;
    private String subtitle;
    private GraphSize size;
    private GraphType type;
    private int sourceId;
    private int tableId;
    private String dimensionColumn;
    private List<String> metricColumns = new ArrayList<>();
    private GraphOperation operation;
    private boolean compareEnabled;
    private Integer compareTableId;
    private String startMonth;
    private String endMonth;
    private Coordinate coordinate;
    private Double delta;
    private List<Map<String, Object>> data = new ArrayList<>();
    private List<GraphSeries> series = new ArrayList<>();
    private String query;

    public Graph() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder from(Graph graph) {
        return builder()
                .id(graph.getId())
                .dashboardId(graph.getDashboardId())
                .title(graph.getTitle())
                .subtitle(graph.getSubtitle())
                .size(graph.getSize())
                .type(graph.getType())
                .sourceId(graph.getSourceId())
                .tableId(graph.getTableId())
                .dimensionColumn(graph.getDimensionColumn())
                .metricColumns(graph.getMetricColumns())
                .operation(graph.getOperation())
                .compareEnabled(graph.isCompareEnabled())
                .compareTableId(graph.getCompareTableId())
                .startMonth(graph.getStartMonth())
                .endMonth(graph.getEndMonth())
                .coordinate(graph.getCoordinate())
                .delta(graph.getDelta())
                .data(graph.getData())
                .series(graph.getSeries())
                .query(graph.getQuery());
    }

    public static class Builder {
        private final Graph graph = new Graph();

        public Builder id(int id) {
            graph.id = id;
            return this;
        }

        public Builder dashboardId(UUID dashboardId) {
            graph.dashboardId = dashboardId;
            return this;
        }

        public Builder title(String title) {
            graph.title = title;
            return this;
        }

        public Builder subtitle(String subtitle) {
            graph.subtitle = subtitle;
            return this;
        }

        public Builder dashboard(Dashboard dashboard) {
            graph.dashboardId = dashboard == null ? null : dashboard.getId();
            return this;
        }

        public Builder size(GraphSize size) {
            graph.size = size;
            return this;
        }

        public Builder type(GraphType type) {
            graph.type = type;
            return this;
        }

        public Builder sourceId(int sourceId) {
            graph.sourceId = sourceId;
            return this;
        }

        public Builder tableId(int tableId) {
            graph.tableId = tableId;
            return this;
        }

        public Builder dimensionColumn(String dimensionColumn) {
            graph.dimensionColumn = dimensionColumn;
            return this;
        }

        public Builder metricColumns(List<String> metricColumns) {
            graph.metricColumns = metricColumns == null ? new ArrayList<>() : new ArrayList<>(metricColumns);
            return this;
        }

        public Builder operation(GraphOperation operation) {
            graph.operation = operation;
            return this;
        }

        public Builder compareEnabled(boolean compareEnabled) {
            graph.compareEnabled = compareEnabled;
            return this;
        }

        public Builder compareTableId(Integer compareTableId) {
            graph.compareTableId = compareTableId;
            return this;
        }

        public Builder startMonth(String startMonth) {
            graph.startMonth = startMonth;
            return this;
        }

        public Builder endMonth(String endMonth) {
            graph.endMonth = endMonth;
            return this;
        }

        public Builder coordinate(Coordinate coordinate) {
            graph.coordinate = coordinate;
            return this;
        }

        public Builder delta(Double delta) {
            graph.delta = delta;
            return this;
        }

        public Builder data(List<Map<String, Object>> data) {
            graph.data = data == null ? new ArrayList<>() : new ArrayList<>(data);
            return this;
        }

        public Builder series(List<GraphSeries> series) {
            graph.series = series == null ? new ArrayList<>() : new ArrayList<>(series);
            return this;
        }

        public Builder query(String query) {
            graph.query = query;
            return this;
        }

        public Graph build() {
            return graph;
        }
    }

    public int getId() {
        return id;
    }

    public UUID getDashboardId() {
        return dashboardId;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public GraphSize getSize() {
        return size;
    }

    public GraphType getType() {
        return type;
    }

    public int getSourceId() {
        return sourceId;
    }

    public int getTableId() {
        return tableId;
    }

    public String getDimensionColumn() {
        return dimensionColumn;
    }

    public List<String> getMetricColumns() {
        return metricColumns;
    }

    public GraphOperation getOperation() {
        return operation;
    }

    public boolean isCompareEnabled() {
        return compareEnabled;
    }

    public Integer getCompareTableId() {
        return compareTableId;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Double getDelta() {
        return delta;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public List<GraphSeries> getSeries() {
        return series;
    }

    public String getQuery() {
        return query;
    }
}
