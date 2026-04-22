package com.e6.domain.model;

import java.util.Date;
import java.util.UUID;

public class Indicator {
    private UUID id;
    private Source source;
    private Date startDate;
    private Date endDate;
    private String query;
    private Dashboard dashboard;
    private String title;
    private Color color;
    private String coordinate;

    public Indicator() {}

    public Indicator(UUID id, Source source, Date startDate, Date endDate, String query, Dashboard dashboard, String title, Color color, String coordinate) {
        this.id = id;
        this.source = source;
        this.startDate = startDate;
        this.endDate = endDate;
        this.query = query;
        this.dashboard = dashboard;
        this.title = title;
        this.color = color;
        this.coordinate = coordinate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }
}
