package com.example.gasbill_generator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class GasBill extends Bill{
    private final double correlationFactor=1.02264;
    private final double calorrifficVale=39.4;

    public GasBill(double standingCharge, double startingReading, double endReading, LocalDate startDate, LocalDate endDate, double rate) {
        super(standingCharge, startingReading, endReading, startDate, endDate, rate);
    }

    public double m3ToKwhConverstion(double reading){
        return ((reading*correlationFactor)*calorrifficVale)/3.6;
    }

    public double getUsedGasChargeAmount(){
        return ((m3ToKwhConverstion(unitsToM3(getUsedUnits())))*(getRate()/100));
    }

    public double unitsToM3(double val){
        return val*2.83;
    }

    public double getStandingChargeAmount(){
        return ChronoUnit.DAYS.between(getStartDate(), getEndDate())*(getStandingCharge()/100);
    }

    public double getTotalGastBill(){
        double totalBill = getStandingChargeAmount() + getUsedGasChargeAmount();
        return Math.round(totalBill * 100.0) / 100.0;
    }

    public double getUsedUnits(){
        return getEndReading()-getStartingReading();
    }

    public long getDays(){
        return ChronoUnit.DAYS.between(getStartDate(), getEndDate());
    }

    public String toString(double val){
        return String.valueOf(val);
    }

}
