package org.acme.infrastructure.entity.sources.inegi;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "i_trolebus")
public class TrolebusIndicadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "`year`", nullable = false)
    private int year;

    @Column(name = "`month`", nullable = false)
    private int month;

    @Column(name = "service_lines", nullable = false)
    private int serviceLines;

    @Column(nullable = false)
    private int operations;

    @Column(name = "km_traveled", nullable = false)
    private BigDecimal kmTraveled;

    @Column(nullable = false)
    private int passengers;

    @Column(name = "fare_revenue", nullable = false)
    private BigDecimal fareRevenue;

    public TrolebusIndicadorEntity() {}

    public TrolebusIndicadorEntity(int id, int year, int month, int serviceLines, int operations, BigDecimal kmTraveled, int passengers, BigDecimal fareRevenue) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.serviceLines = serviceLines;
        this.operations = operations;
        this.kmTraveled = kmTraveled;
        this.passengers = passengers;
        this.fareRevenue = fareRevenue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getServiceLines() {
        return serviceLines;
    }

    public void setServiceLines(int serviceLines) {
        this.serviceLines = serviceLines;
    }

    public int getOperations() {
        return operations;
    }

    public void setOperations(int operations) {
        this.operations = operations;
    }

    public BigDecimal getKmTraveled() {
        return kmTraveled;
    }

    public void setKmTraveled(BigDecimal kmTraveled) {
        this.kmTraveled = kmTraveled;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public BigDecimal getFareRevenue() {
        return fareRevenue;
    }

    public void setFareRevenue(BigDecimal fareRevenue) {
        this.fareRevenue = fareRevenue;
    }
}
