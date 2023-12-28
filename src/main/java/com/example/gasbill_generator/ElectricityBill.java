package com.example.gasbill_generator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ElectricityBill extends Bill{
    public ElectricityBill(double standingCharge, double startingReading, double endReading, LocalDate startDate, LocalDate endDate, double rate) {
        super(standingCharge, startingReading, endReading, startDate, endDate, rate);
    }

    public double getStandingChargeAmount(){
        return ChronoUnit.DAYS.between(getStartDate(), getEndDate())*(getStandingCharge()/100);
    }

    public double getUsedElectricityChargeAmount(){
        return (getEndReading()-getStartingReading())*(getRate()/100);
    }

    public double getTotalElectricityBill() {
        double totalBill = getStandingChargeAmount() + getUsedElectricityChargeAmount();
        return Math.round(totalBill * 100.0) / 100.0;
    }

    public  double getKwh(){
        return (getEndReading()-getStartingReading());
    }

    public  long getDays(){
        return ChronoUnit.DAYS.between(getStartDate(), getEndDate());
    }
    public String toString(double val){
        return String.valueOf(val);
    }
}
