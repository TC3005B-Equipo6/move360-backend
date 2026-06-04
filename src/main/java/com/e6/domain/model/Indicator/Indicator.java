package com.e6.domain.model.Indicator;

import java.time.LocalDate;
import java.util.UUID;

public class Indicator {
    private int id;
    private String title;
    private String subtitle;
    private IndicatorType type;
    private Double data;
    private Relationship relationship;
    private Double deltaData;
    private Operation operation;
    private LocalDate startDate;
    private LocalDate endDate;
    private String query;
    private UUID dashboardId;
    private Coordinate coordinate;
    private int sourceId;
    private int tableId;
    private Integer columnId;
    private IndicatorFilterSelection filters = IndicatorFilterSelection.empty();

    public Indicator() {}

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private int id;
        private String title;
        private String subtitle;
        private IndicatorType type;
        private Double data;
        private Relationship relationship;
        private Double deltaData;
        private Operation operation;
        private LocalDate startDate;
        private LocalDate endDate;
        private String query;
        private UUID dashboardId;
        private Coordinate coordinate;
        private int sourceId;
        private int tableId;
        private Integer columnId;
        private IndicatorFilterSelection filters = IndicatorFilterSelection.empty();

        public Builder id(int id){
            this.id = id;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder subtitle(String subtitle){
            this.subtitle = subtitle;
            return this;
        }

        public Builder type(IndicatorType type){
            this.type = type;
            return this;
        }

        public Builder data(Double data){
            this.data = data;
            return this;
        }

        public Builder relationship(Relationship relationship){
            this.relationship = relationship;
            return this;
        }

        public Builder deltaData(Double deltaData){
            this.deltaData = deltaData;
            return this;
        }

        public Builder operation(Operation operation){
            this.operation = operation;
            return this;
        }

        public Builder startDate(LocalDate startDate){
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate){
            this.endDate = endDate;
            return this;
        }

        public Builder query(String query){
            this.query = query;
            return this;
        }

        public Builder dashboard(UUID dashboardId){
            this.dashboardId = dashboardId;
            return this;
        }

        public Builder coordinate(Coordinate coordinate){
            this.coordinate = coordinate;
            return this;
        }

        public Builder source(int sourceId){
            this.sourceId = sourceId;
            return this;
        }

        public Builder table(int tableId){
            this.tableId = tableId;
            return this;
        }

        public Builder column(Integer columnId){
            this.columnId = columnId;
            return this;
        }

        public Builder filters(IndicatorFilterSelection filters){
            this.filters = filters == null ? IndicatorFilterSelection.empty() : filters;
            return this;
        }

        public Indicator build(){
            Indicator indicator = new Indicator();
            indicator.id = this.id;
            indicator.title = this.title;
            indicator.subtitle = this.subtitle;
            indicator.type = this.type;
            indicator.data = this.data;
            indicator.relationship = this.relationship;
            indicator.deltaData = this.deltaData;
            indicator.operation = this.operation;
            indicator.startDate = this.startDate;
            indicator.endDate = this.endDate;
            indicator.query = this.query;
            indicator.dashboardId = this.dashboardId;
            indicator.coordinate = this.coordinate;
            indicator.sourceId = this.sourceId;
            indicator.tableId = this.tableId;
            indicator.columnId = this.columnId;
            indicator.filters = this.filters;
            return indicator;
        }
    }

    public Indicator(int id, String title, String subtitle, IndicatorType type, Double data, Relationship relationship, Double deltaData, Operation operation, LocalDate startDate, LocalDate endDate, String query, UUID dashboardId, Coordinate coordinate, int sourceId, int tableId, Integer columnId, IndicatorFilterSelection filters) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.type = type;
        this.data = data;
        this.relationship = relationship;
        this.deltaData = deltaData;
        this.operation = operation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.query = query;
        this.dashboardId = dashboardId;
        this.coordinate = coordinate;
        this.sourceId = sourceId;
        this.tableId = tableId;
        this.columnId = columnId;
        this.filters = filters == null ? IndicatorFilterSelection.empty() : filters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public IndicatorType getType() {
        return type;
    }

    public void setType(IndicatorType type) {
        this.type = type;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public Double getDeltaData() {
        return deltaData;
    }

    public void setDeltaData(Double deltaData) {
        this.deltaData = deltaData;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public UUID getDashboardId() {
        return dashboardId;
    }

    public void setDashboardId(UUID dashboardId) {
        this.dashboardId = dashboardId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public IndicatorFilterSelection getFilters() {
        return filters == null ? IndicatorFilterSelection.empty() : filters;
    }

    public void setFilters(IndicatorFilterSelection filters) {
        this.filters = filters == null ? IndicatorFilterSelection.empty() : filters;
    }
}
