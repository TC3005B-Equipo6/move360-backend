package com.e6.infrastructure.infrastructure.entity.sources.inegi;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "i_tren_suburbano")
public class TrenSuburbanoIndicadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "`year`", nullable = false)
    private int year;

    @Column(name = "`month`", nullable = false)
    private int month;

    @Column(nullable = false)
    private int routes;

    @Column(name = "operations_week", nullable = false)
    private int operationsWeek;

    @Column(name = "operations_weekend", nullable = false)
    private int operationsWeekend;

    @Column(name = "km_traveled", nullable = false)
    private BigDecimal kmTraveled;

    @Column(nullable = false)
    private int passengers;

    public TrenSuburbanoIndicadorEntity() {}

    public TrenSuburbanoIndicadorEntity(int id, int year, int month, int routes, int operationsWeek, int operationsWeekend, BigDecimal kmTraveled, int passengers) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.routes = routes;
        this.operationsWeek = operationsWeek;
        this.operationsWeekend = operationsWeekend;
        this.kmTraveled = kmTraveled;
        this.passengers = passengers;
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

    public int getRoutes() {
        return routes;
    }

    public void setRoutes(int routes) {
        this.routes = routes;
    }

    public int getOperationsWeek() {
        return operationsWeek;
    }

    public void setOperationsWeek(int operationsWeek) {
        this.operationsWeek = operationsWeek;
    }

    public int getOperationsWeekend() {
        return operationsWeekend;
    }

    public void setOperationsWeekend(int operationsWeekend) {
        this.operationsWeekend = operationsWeekend;
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
}
