package com.e6.domain.model.Indicator;

import com.e6.domain.model.source.Source;

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

        public Builder delta(Relationship relationship){
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
            return indicator;
        }
    }

    public Indicator(int id, String title, String subtitle, IndicatorType type, Double data, Relationship relationship, Double deltaData, Operation operation, LocalDate startDate, LocalDate endDate, String query, UUID dashboardId, Coordinate coordinate, int sourceId) {
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
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public IndicatorType getType() {
        return type;
    }

    public Double getData() {
        return data;
    }

    public Relationship getDelta() {
        return relationship;
    }

    public Double getDeltaData() {
        return deltaData;
    }

    public Operation getOperation() {
        return operation;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getQuery() {
        return query;
    }

    public UUID getDashboardId() {
        return dashboardId;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getSourceId() {
        return sourceId;
    }
}
