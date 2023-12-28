package com.example.gasbill_generator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class GasBillPageController {

    private  GasBill gasBill;
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
    private Label units;
    @FXML
    private Label m3;
    @FXML
    private Label m3Calculated;

    @FXML
    private Label factor;
    @FXML
    private Label calarific;
    @FXML
    private Label Kwh;
    @FXML
    private Label rate;
    @FXML
    private Label price;


    public void setData(Customer customer,BillGenerator billGenerator, double gasEndReading, LocalDate billDate) {
        gasBill = new GasBill(customer.getSelectedGasTarriff().getStandingCharge(), billGenerator.getGasUnitReading(), gasEndReading, billGenerator.getLastBillDate(), billDate, customer.getSelectedGasTarriff().getUnitPerKwh());

        standingCharge.setText(String.valueOf(gasBill.getDays())+" * "+customer.getSelectedGasTarriff().getStandingCharge()+"  = "+gasBill.getStandingChargeAmount());
        singleRate.setText(gasBill.toString(Math.round(gasBill.getUsedGasChargeAmount()* 100.0) / 100.0));
        totalCharges.setText(gasBill.toString(gasBill.getTotalGastBill()));

        period.setText(gasBill.getStartDate().toString()+" - "+billDate);
        openRaeding.setText(gasBill.toString(gasBill.getStartingReading()));
        closeReading.setText(gasBill.toString(gasEndReading));
        units.setText(gasBill.toString(gasBill.getUsedUnits()));
        m3.setText(gasBill.toString(gasBill.unitsToM3(gasBill.getUsedUnits())));

        m3Calculated.setText(gasBill.toString(gasBill.unitsToM3(gasBill.getUsedUnits())));
        factor.setText("*1.02264");
        calarific.setText("* 39.4 / 3.6");
        Kwh.setText(gasBill.toString(gasBill.m3ToKwhConverstion(gasBill.unitsToM3(gasBill.getUsedUnits()))));
        rate.setText(String.valueOf(customer.getSelectedGasTarriff().getUnitPerKwh()));
        price.setText(gasBill.toString(Math.round(gasBill.getUsedGasChargeAmount()* 100.0) / 100.0));
    }
}
