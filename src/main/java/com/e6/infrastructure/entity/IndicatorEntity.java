package com.e6.infrastructure.entity;

import com.e6.domain.model.Indicator.IndicatorType;
import com.e6.domain.model.Indicator.Operation;
import com.e6.domain.model.Indicator.Relationship;
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

        @Column(nullable = true)
        private String subtitle;

        @Column(nullable = false, length = 50)
        private String coordinate;

        @Column(name = "source_id", nullable = false)
        private int sourceId;

        @Column(name = "table_id")
        private Integer tableId;

        @Column(name = "column_id")
        private Integer columnId;

        @Column
        @JdbcTypeCode(SqlTypes.JSON)
        private String filters;

        @Column( nullable = false)
        private IndicatorType type;

        @Column(nullable = false)
        private Double data;

        @Column(nullable = false)
        private Double deltaData;

        @Column(nullable = false)
        private Relationship relationship;

        @Column(nullable = false)
        private Operation operation;

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

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(String coordinate) {
            this.coordinate = coordinate;
        }

        public int getSourceId() {
            return sourceId;
        }

        public void setSourceId(int sourceId) {
            this.sourceId = sourceId;
        }

        public int getTableId() {
            return tableId == null ? 0 : tableId;
        }

        public void setTableId(int tableId) {
            this.tableId = tableId;
        }

        public Integer getColumnId() {
            return columnId;
        }

        public void setColumnId(Integer columnId) {
            this.columnId = columnId;
        }

        public String getFilters() {
            return filters;
        }

        public void setFilters(String filters) {
            this.filters = filters;
        }

        public IndicatorType getType() {
            return type;
        }

        public void setType(IndicatorType type) {
            this.type = type;
        }

        public Double getData() {
            return data;
        }

        public void setData(Double data) {
            this.data = data;
        }

        public Double getDeltaData() {
            return deltaData;
        }

        public void setDeltaData(Double deltaData) {
            this.deltaData = deltaData;
        }

        public Relationship getRelationship() {
            return relationship;
        }

        public void setRelationship(Relationship relationship) {
            this.relationship = relationship;
        }

        public Operation getOperation() {
            return operation;
        }

        public void setOperation(Operation operation) {
            this.operation = operation;
        }

        public DashboardEntity getDashboard() {
            return dashboard;
        }

        public void setDashboard(DashboardEntity dashboard) {
            this.dashboard = dashboard;
        }
    }

