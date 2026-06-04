package com.e6.infrastructure.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

    @Entity
    @Table(name = "indicator")
    public class IndicatorEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "start_date", nullable = false)
        private LocalDate startDate;

        @Column(name = "end_date", nullable = false)
        private LocalDate endDate;

        @Column(nullable = false)
        @JdbcTypeCode(SqlTypes.JSON)
        private String query;

        @Column(nullable = false)
        private String title;

        @Column(nullable = false, length = 50)
        private String coordinate;

        @Column(nullable = false)
        private int sourceId;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "dashboard_id", nullable = false)
        private DashboardEntity dashboard;

    public IndicatorEntity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public int getSource() {
        return sourceId;
    }

    public void setSource(int sourceId) {
        this.sourceId = sourceId;
    }

    public DashboardEntity getDashboard() {
        return dashboard;
    }

    public void setDashboard(DashboardEntity dashboard) {
        this.dashboard = dashboard;
    }
}

