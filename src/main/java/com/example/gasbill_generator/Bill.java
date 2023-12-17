package com.example.gasbill_generator;

import java.time.LocalDate;

public class Bill {
    private double standingCharge;
    private double startingReading;
    private double endReading;
    private LocalDate startDate;
    private LocalDate endDate;

    private double Rate;

    public Bill(double standingCharge, double startingReading, double endReading, LocalDate startDate, LocalDate endDate, double rate) {
        this.standingCharge = standingCharge;
        this.startingReading = startingReading;
        this.endReading = endReading;
        this.startDate = startDate;
        this.endDate = endDate;
        Rate = rate;
    }

    public double getStandingCharge() {
        return standingCharge;
    }

    public void setStandingCharge(double standingCharge) {
        this.standingCharge = standingCharge;
    }

    public double getStartingReading() {
        return startingReading;
    }

    public void setStartingReading(double startingReading) {
        this.startingReading = startingReading;
    }

    public double getEndReading() {
        return endReading;
    }

    public void setEndReading(double endReading) {
        this.endReading = endReading;
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

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        Rate = rate;
    }
}
