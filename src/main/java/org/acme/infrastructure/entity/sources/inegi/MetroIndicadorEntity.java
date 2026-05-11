package com.e6.infrastructure.entity.sources.inegi;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "i_metro")
public class MetroIndicadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "`year`", nullable = false)
    private int year;

    @Column(name = "`month`", nullable = false)
    private int month;

    @Column(nullable = false)
    private int operations;

    @Column(name = "km_traveled", nullable = false)
    private BigDecimal kmTraveled;

    @Column(nullable = false)
    private int passengers;

    @Column(name = "energy_consumed", nullable = false)
    private BigDecimal energyConsumed;

    public MetroIndicadorEntity() {}

    public MetroIndicadorEntity(int id, int year, int month, int operations, BigDecimal kmTraveled, int passengers, BigDecimal energyConsumed) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.operations = operations;
        this.kmTraveled = kmTraveled;
        this.passengers = passengers;
        this.energyConsumed = energyConsumed;
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

    public BigDecimal getEnergyConsumed() {
        return energyConsumed;
    }

    public void setEnergyConsumed(BigDecimal energyConsumed) {
        this.energyConsumed = energyConsumed;
    }
}
