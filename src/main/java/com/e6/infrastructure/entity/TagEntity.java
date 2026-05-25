package com.e6.infrastructure.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "color_id", nullable = false)
    private ColorEntity color;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<DashboardEntity> dashboards = new HashSet<>();

    public TagEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColorEntity getColor() {
        return color;
    }

    public void setColor(ColorEntity color) {
        this.color = color;
    }

    public Set<DashboardEntity> getDashboards() {
        return dashboards;
    }

    public void setDashboards(Set<DashboardEntity> dashboards) {
        this.dashboards = dashboards;
    }
}
