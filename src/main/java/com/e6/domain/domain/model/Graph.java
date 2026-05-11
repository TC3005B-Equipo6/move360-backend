package com.e6.domain.domain.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Graph {
    private UUID id;
    private String query;
    private LocalDate startDate;
    private LocalDate endDate;
    private Dashboard dashboard;
    private String coordinate;
    private Set<Source> sources = new HashSet<>();

    public Graph (){}

    public Graph (UUID id, String query, LocalDate startDate, LocalDate endDate, Dashboard dashboard, String coordinate, Set<Source> sources) {
        this.id = id;
        this.query = query;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dashboard = dashboard;
        this.coordinate = coordinate;
        this.sources = sources;
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

    public Set<Source> getSources() {
        return sources;
    }

    public void setSources(Set<Source> sources) {
        this.sources = sources;
    }
}
