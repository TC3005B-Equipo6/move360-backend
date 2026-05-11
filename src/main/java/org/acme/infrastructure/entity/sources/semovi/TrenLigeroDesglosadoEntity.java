package com.e6.infrastructure.entity.sources.semovi;

import jakarta.persistence.*;

@Entity
@Table(name = "s_tren_ligero_desglosado")
public class TrenLigeroDesglosadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "`day`", nullable = false)
    private int day;

    @Column(name = "`month`", nullable = false)
    private int month;

    @Column(name = "`year`", nullable = false)
    private int year;

    @Column(name = "payment_type", nullable = false, length = 100)
    private String paymentType;

    @Column(nullable = false)
    private int passengers;

    public TrenLigeroDesglosadoEntity() {}

    public TrenLigeroDesglosadoEntity(int id, int day, int month, int year, String paymentType, int passengers) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.paymentType = paymentType;
        this.passengers = passengers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }
}
