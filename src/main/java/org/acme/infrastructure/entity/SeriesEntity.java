package org.acme.infrastructure.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "series")
public class SeriesEntity {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36)
    private UUID id;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private String data;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "graph_id", nullable = false)
    private GraphEntity graph;

    public SeriesEntity() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
