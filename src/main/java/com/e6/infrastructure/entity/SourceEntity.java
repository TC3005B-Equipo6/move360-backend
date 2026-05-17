package com.e6.infrastructure.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "source")
public class SourceEntity {

    @Id
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @ManyToMany(mappedBy = "sources", fetch = FetchType.LAZY)
    private Set<GraphEntity> graphs = new HashSet<>();

    public SourceEntity() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GraphEntity> getGraphs() {
        return graphs;
    }

    public void setGraphs(Set<GraphEntity> graphs) {
        this.graphs = graphs;
    }
}
