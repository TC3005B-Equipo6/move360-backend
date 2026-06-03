package com.e6.domain.model.Indicator;

import com.e6.domain.model.source.Source;

import java.time.LocalDateTime;
import java.util.UUID;

public class Indicator {
    private int id;
    private String title;
    private String subtitle;
    private IndicatorType type;
    private Double data;
    private Delta delta;
    private Double deltaData;
    private Operation operation;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String query;
    private UUID dashboardId;
    private Coordinate coordinate;
    private Source source;

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
        private Delta delta;
        private Double deltaData;
        private Operation operation;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private String query;
        private UUID dashboardId;
        private Coordinate coordinate;
        private Source source;

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

        public Builder delta(Delta delta){
            this.delta = delta;
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

        public Builder startDate(LocalDateTime startDate){
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDateTime endDate){
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

        public Builder source(Source source){
            this.source = source;
            return this;
        }

        public Indicator build(){
            Indicator indicator = new Indicator();
            indicator.id = this.id;
            indicator.title = this.title;
            indicator.subtitle = this.subtitle;
            indicator.type = this.type;
            indicator.data = this.data;
            indicator.delta = this.delta;
            indicator.deltaData = this.deltaData;
            indicator.operation = this.operation;
            indicator.startDate = this.startDate;
            indicator.endDate = this.endDate;
            indicator.query = this.query;
            indicator.dashboardId = this.dashboardId;
            indicator.coordinate = this.coordinate;
            indicator.source = this.source;
            return indicator;
        }
    }

    public Indicator(int id, String title, String subtitle, IndicatorType type, Double data, Delta delta, Double deltaData, Operation operation, LocalDateTime startDate, LocalDateTime endDate, String query, UUID dashboardId, Coordinate coordinate, Source source) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.type = type;
        this.data = data;
        this.delta = delta;
        this.deltaData = deltaData;
        this.operation = operation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.query = query;
        this.dashboardId = dashboardId;
        this.coordinate = coordinate;
        this.source = source;
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

    public Delta getDelta() {
        return delta;
    }

    public Double getDeltaData() {
        return deltaData;
    }

    public Operation getOperation() {
        return operation;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
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

    public Source getSource() {
        return source;
    }
}
