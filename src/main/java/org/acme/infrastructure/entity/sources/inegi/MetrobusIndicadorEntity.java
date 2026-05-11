package org.acme.infrastructure.entity.sources.inegi;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "i_metrobus")
public class MetrobusIndicadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "`year`", nullable = false)
    private int year;

    @Column(name = "`month`", nullable = false)
    private int month;

    @Column(nullable = false)
    private int routes;

    @Column(name = "km_traveled", nullable = false)
    private BigDecimal kmTraveled;

    @Column(nullable = false)
    private int units;

    @Column(name = "operations_week", nullable = false)
    private int operationsWeek;

    @Column(name = "operations_weekend", nullable = false)
    private int operationsWeekend;

    @Column(name = "full_fare_passengers", nullable = false)
    private int fullFarePassengers;

    @Column(name = "courtesy_passengers", nullable = false)
    private int courtesyPassengers;

    public MetrobusIndicadorEntity() {}

    public MetrobusIndicadorEntity(int id, int year, int month, int routes, BigDecimal kmTraveled, int units, int operationsWeek, int operationsWeekend, int fullFarePassengers, int courtesyPassengers) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.routes = routes;
        this.kmTraveled = kmTraveled;
        this.units = units;
        this.operationsWeek = operationsWeek;
        this.operationsWeekend = operationsWeekend;
        this.fullFarePassengers = fullFarePassengers;
        this.courtesyPassengers = courtesyPassengers;
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

    public BigDecimal getKmTraveled() {
        return kmTraveled;
    }

    public void setKmTraveled(BigDecimal kmTraveled) {
        this.kmTraveled = kmTraveled;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
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

    public int getFullFarePassengers() {
        return fullFarePassengers;
    }

    public void setFullFarePassengers(int fullFarePassengers) {
        this.fullFarePassengers = fullFarePassengers;
    }

    public int getCourtesyPassengers() {
        return courtesyPassengers;
    }

    public void setCourtesyPassengers(int courtesyPassengers) {
        this.courtesyPassengers = courtesyPassengers;
    }
}
