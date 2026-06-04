package com.e6.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "series")
public class SeriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "series_key", nullable = false, length = 100)
    private String seriesKey;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false, length = 20)
    private String color;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private String data;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "graph_id", nullable = false)
    private GraphEntity graph;

    public SeriesEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeriesKey() {
        return seriesKey;
    }

    public void setSeriesKey(String seriesKey) {
        this.seriesKey = seriesKey;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public GraphEntity getGraph() {
        return graph;
    }

    public void setGraph(GraphEntity graph) {
        this.graph = graph;
    }
}
