package com.e6.infrastructure.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
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
    private String query;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(nullable = false, length = 50)
    private String coordinate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dashboard_id", nullable = false)
    private DashboardEntity dashboard;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "graph_sources",
            joinColumns = @JoinColumn(name = "graph_id"),
            inverseJoinColumns = @JoinColumn(name = "source_id")
    )
    private Set<SourceEntity> sources = new HashSet<>();
}
