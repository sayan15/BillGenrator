package com.example.gasbill_generator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class ElectricityBillPageController {
    private ElectricityBill electricityBill;
    @FXML
    private Label standingCharge;

    @FXML
    private Label singleRate;
    @FXML
    private Label totalCharges;

    @FXML
    private Label period;
    @FXML
    private Label openRaeding;
    @FXML
    private Label closeReading;
    @FXML
    private Label kwh;
    public void setData(Customer customer,BillGenerator billGenerator, double electricityEndReading,  LocalDate billDate){

        electricityBill=new ElectricityBill(customer.getSelectedElectricityTarriff().getStandingCharge(),billGenerator.getLastElectricityUnitReading(),electricityEndReading,billGenerator.getLastBillDate(),billDate,customer.getSelectedElectricityTarriff().getUnitPerKwh());
        //set the values to billGenerator class

        standingCharge.setText(electricityBill.getDays()+" * "+customer.getSelectedElectricityTarriff().getStandingCharge()+"p     ="+electricityBill.toString(electricityBill.getStandingChargeAmount()));
        singleRate.setText(electricityBill.toString(Math.round(electricityBill.getUsedElectricityChargeAmount() * 100.0) / 100.0));
        totalCharges.setText(electricityBill.toString(electricityBill.getTotalElectricityBill()));
        period.setText(billGenerator.getLastBillDate().toString()+"-"+billDate);
        openRaeding.setText(billGenerator.toString(billGenerator.getLastElectricityUnitReading()));
        closeReading.setText(String.valueOf(electricityEndReading));
        kwh.setText(electricityBill.toString(electricityBill.getKwh()));
    }
}
