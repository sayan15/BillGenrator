package com.example.gasbill_generator;

import java.io.Serializable;

public class Tariff implements Serializable {


    private int tarrifId;
    private String tarrifName;
    private String tarrifType;
    private double standingCharge;
    private double unitPerKwh;

    public Tariff(int tarrifId, String tarrifName, String tarrifType, double standingCharge, double unitPerKwh) {
        this.tarrifId= tarrifId;
        this.tarrifName = tarrifName;
        this.tarrifType = tarrifType;
        this.standingCharge = standingCharge;
        this.unitPerKwh = unitPerKwh;
    }

    public String getTarrifName() {
        return tarrifName;
    }

    public void setTarrifName(String tarrifName) {
        this.tarrifName = tarrifName;
    }

    public String getTarrifType() {
        return tarrifType;
    }

    public void setTarrifType(String tarrifType) {
        this.tarrifType = tarrifType;
    }

    public double getStandingCharge() {
        return standingCharge;
    }

    public void setStandingCharge(double standingCharge) {
        this.standingCharge = standingCharge;
    }

    public double getUnitPerKwh() {
        return unitPerKwh;
    }

    public void setUnitPerKwh(double unitPerKwh) {
        this.unitPerKwh = unitPerKwh;
    }

    public int getTarrifId() {
        return tarrifId;
    }

    public void setTarrifId(int tarrifId) {
        this.tarrifId=tarrifId;
    }


}
