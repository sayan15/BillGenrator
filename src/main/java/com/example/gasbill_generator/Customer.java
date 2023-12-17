package com.example.gasbill_generator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Customer implements Serializable {

    private int customerId;
    private String customerName;
    private String customerEmail;
    private LocalDate dateJoined;
    private Tariff selectedElectricityTarriff;
    private Tariff selectedGasTarriff;
    private double electricityunitKwhField;
    private double gasunitKwhField;

    public Customer(String customerName, String customerEmail, LocalDate dateJoined, Tariff selectedElectricityTarriff, Tariff selectedGasTarriff, double electricityunitKwhField, double gasunitKwhField) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.dateJoined = dateJoined;
        this.selectedElectricityTarriff = selectedElectricityTarriff;
        this.selectedGasTarriff = selectedGasTarriff;
        this.electricityunitKwhField = electricityunitKwhField;
        this.gasunitKwhField = gasunitKwhField;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public Tariff getSelectedElectricityTarriff() {
        return selectedElectricityTarriff;
    }

    public void setSelectedElectricityTarriff(Tariff selectedElectricityTarriff) {
        this.selectedElectricityTarriff = selectedElectricityTarriff;
    }

    public Tariff getSelectedGasTarriff() {
        return selectedGasTarriff;
    }

    public void setSelectedGasTarriff(Tariff selectedGasTarriff) {
        this.selectedGasTarriff = selectedGasTarriff;
    }

    public double getElectricityunitKwhField() {
        return electricityunitKwhField;
    }

    public void setElectricityunitKwhField(double electricityunitKwhField) {
        this.electricityunitKwhField = electricityunitKwhField;
    }

    public double getGasunitKwhField() {
        return gasunitKwhField;
    }

    public void setGasunitKwhField(double gasunitKwhField) {
        this.gasunitKwhField = gasunitKwhField;
    }
}
