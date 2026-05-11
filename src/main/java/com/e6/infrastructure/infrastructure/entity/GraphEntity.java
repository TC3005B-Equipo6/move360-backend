package com.e6.infrastructure.infrastructure.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "graph")
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "Graph.full",
                attributeNodes = {
                        @NamedAttributeNode("dashboard"),
                        @NamedAttributeNode("sources")
                }
        )
)
public class GraphEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36)
    private UUID id;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private String query;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, length = 50)
    private String coordinate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dashboard_id", nullable = false)
    private DashboardEntity dashboard;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "graph_source",
            joinColumns = @JoinColumn(name = "graph_id"),
            inverseJoinColumns = @JoinColumn(name = "source_id")
    )
    private Set<SourceEntity> sources = new HashSet<>();

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

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public DashboardEntity getDashboard() {
        return dashboard;
    }

    public void setDashboard(DashboardEntity dashboard) {
        this.dashboard = dashboard;
    }

    public Set<SourceEntity> getSources() {
        return sources;
    }

    public void setSources(Set<SourceEntity> sources) {
        this.sources = sources;
    }
}
