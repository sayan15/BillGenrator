package com.example.gasbill_generator;

import java.time.LocalDate;

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



    private double payment;

    public BillGenerator(int customerId, LocalDate lastBillDate , double lastElectricityUnitReading, double gasUnitReading, double accountBalance, double gasBill, double electricityBill, double total, double due) {
        this.customerId = customerId;
        this.lastBillDate = lastBillDate ;
        this.lastElectricityUnitReading = lastElectricityUnitReading;
        this.gasUnitReading = gasUnitReading;
        this.accountBalance = accountBalance;
        this.gasBill = gasBill;
        this.electricityBill = electricityBill;
        this.total = total;
        this.due = due;
        this.payment=0.0;
    }


    public void makePayment(double payment) {
        this.payment = payment;
        setAccountBalance(getAccountBalance()+payment);
    }

    public double getPayment(){
        return payment;
    }



    public int getCustomerId() {
        return customerId;
    }


    public LocalDate getLastBillDate() {
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
            if(accountBalance>=(getElectricityBill()+getGasBill()+getDue())){
                setAccountBalance(Math.round((accountBalance-(getElectricityBill()+getGasBill()+getDue()))*100.0)/100.0);
                return getAccountBalance();
            }else {
                setDue((getElectricityBill()+getGasBill()+getDue())-accountBalance);
                setAccountBalance(0.0);
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
        return getElectricityBill()+getGasBill();
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
