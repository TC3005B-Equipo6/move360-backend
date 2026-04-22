package com.e6.domain.model;



import java.util.Date;
import java.util.UUID;

public class Graph {
    private UUID id;
    private String query;
    private Date startDate;
    private Date endDate;
    private Dashboard dashboard;
    private String coordinate;

    public Graph (){}

    public Graph (UUID id, String query, Date startDate, Date endDate, Dashboard dashboard, String coordinate) {
        this.id = id;
        this.query = query;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dashboard = dashboard;
        this.coordinate = coordinate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }
}
