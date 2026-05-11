package org.acme.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Indicator {
    private UUID id;
    private Source source;
    private LocalDate startDate;
    private LocalDate endDate;
    private String query;
    private Dashboard dashboard;
    private String title;
    private Color color;
    private String coordinate;

    public Indicator() {}

    public Indicator(UUID id, Source source, LocalDate startDate, LocalDate endDate, String query, Dashboard dashboard, String title, Color color, String coordinate) {
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
