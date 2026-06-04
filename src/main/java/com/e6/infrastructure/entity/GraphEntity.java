package com.e6.infrastructure.entity;

import com.e6.domain.model.graph.GraphOperation;
import com.e6.domain.model.graph.GraphSize;
import com.e6.domain.model.graph.GraphType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "graph")
@NamedEntityGraph(
        name = "Graph.full",
        attributeNodes = {
                @NamedAttributeNode("dashboard"),
                @NamedAttributeNode("series")
        }
)
public class GraphEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(nullable = true)
    private String subtitle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private GraphSize size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private GraphType type;

    @Column(name = "source_id", nullable = false)
    private int sourceId;

    @Column(name = "table_id", nullable = false)
    private int tableId;

    @Column(name = "dimension_column", nullable = false, length = 100)
    private String dimensionColumn;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metric_columns", nullable = false)
    private String metricColumns;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private GraphOperation operation;

    @Column(name = "compare_enabled", nullable = false)
    private boolean compareEnabled;

    @Column(name = "compare_table_id")
    private Integer compareTableId;

    @Column(name = "start_month", nullable = false, length = 7)
    private String startMonth;

    @Column(name = "end_month", nullable = false, length = 7)
    private String endMonth;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private String query;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private String coordinate;

    @Column
    private Double delta;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false)
    private String data;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dashboard_id", nullable = false)
    private DashboardEntity dashboard;

    @OneToMany(mappedBy = "graph", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<SeriesEntity> series = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public GraphSize getSize() {
        return size;
    }

    public void setSize(GraphSize size) {
        this.size = size;
    }

    public GraphType getType() {
        return type;
    }

    public void setType(GraphType type) {
        this.type = type;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getDimensionColumn() {
        return dimensionColumn;
    }

    public void setDimensionColumn(String dimensionColumn) {
        this.dimensionColumn = dimensionColumn;
    }

    public String getMetricColumns() {
        return metricColumns;
    }

    public void setMetricColumns(String metricColumns) {
        this.metricColumns = metricColumns;
    }

    public GraphOperation getOperation() {
        return operation;
    }

    public void setOperation(GraphOperation operation) {
        this.operation = operation;
    }

    public boolean isCompareEnabled() {
        return compareEnabled;
    }

    public void setCompareEnabled(boolean compareEnabled) {
        this.compareEnabled = compareEnabled;
    }

    public Integer getCompareTableId() {
        return compareTableId;
    }

    public void setCompareTableId(Integer compareTableId) {
        this.compareTableId = compareTableId;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
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

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public Double getDelta() {
        return delta;
    }

    public void setDelta(Double delta) {
        this.delta = delta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public DashboardEntity getDashboard() {
        return dashboard;
    }

    public void setDashboard(DashboardEntity dashboard) {
        this.dashboard = dashboard;
    }

    public List<SeriesEntity> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesEntity> series) {
        this.series = series;
    }
}
