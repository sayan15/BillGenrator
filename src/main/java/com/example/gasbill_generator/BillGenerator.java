package com.example.gasbill_generator;

import java.time.LocalDate;
import java.util.Date;

public class BillGenerator {
    private int customerId;
    private LocalDate lastBillDate;
    private double lastElectricityUnitReading;
    private double gasUnitReading;
    private double accountBalance;
    private double gasBill;
    private double electricityBill;
    private double total;
    private double due;

    public BillGenerator(int customerId, LocalDate dateJoined, double lastElectricityUnitReading, double gasUnitReading, double accountBalance, double gasBill, double electricityBill, double total, double due) {
        this.customerId = customerId;
        this.lastBillDate = dateJoined;
        this.lastElectricityUnitReading = lastElectricityUnitReading;
        this.gasUnitReading = gasUnitReading;
        this.accountBalance = accountBalance;
        this.gasBill = gasBill;
        this.electricityBill = electricityBill;
        this.total = total;
        this.due = due;
    }

    public int getCustomerId() {
        return customerId;
    }


    public LocalDate getlastBillDate() {
        return lastBillDate;
    }

    public void setlastBillDate(LocalDate dateJoined) {
        this.lastBillDate = dateJoined;
    }

    public double getLastElectricityUnitReading() {
        return lastElectricityUnitReading;
    }

    public void setLastElectricityUnitReading(double lastElectricityUnitReading) {
        this.lastElectricityUnitReading = lastElectricityUnitReading;
    }

    public double getGasUnitReading() {
        return gasUnitReading;
    }

    public void setGasUnitReading(double gasUnitReading) {
        this.gasUnitReading = gasUnitReading;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double balane){
        this.accountBalance=balane;
    }
    public double calculateAccountBalance() {
        if(accountBalance==0.0){
            return 0.0;
        }else{
            if(accountBalance>=(getElectricityBill()+getGasBill())){
                return accountBalance-(getElectricityBill()+getGasBill());
            }else {
                setAccountBalance(0.0);
                setDue((getElectricityBill()+getGasBill())-accountBalance);
                return 0.0;
            }
        }
    }

    public double getGasBill() {
        return gasBill;
    }

    public void setGasBill(double gasBill) {
        this.gasBill = gasBill;
    }

    public double getElectricityBill() {
        return electricityBill;
    }

    public void setElectricityBill(double electricityBill) {
        this.electricityBill = electricityBill;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDue() {
        return due;
    }

    public void setDue(double due) {
        this.due = due;
    }

    public String toString(double val){
        return String.valueOf(val);
    }
}
