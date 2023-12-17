package com.example.gasbill_generator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;

public class AccountStatementController {
    @FXML
    private Label previousBalance;
    @FXML
    private Label PaymentMade;
    @FXML
    private Label balance;
    @FXML
    private Label electricity;
    @FXML
    private Label gas;
    @FXML
    private Label accountBalance;
    public void setData(Customer customer,double payment, BillGenerator billGenerator, double electricityEndReading, double gasEndReading, LocalDate billDate){
        GasBill gasBill=new GasBill(customer.getSelectedGasTarriff().getStandingCharge(),billGenerator.getGasUnitReading(),gasEndReading,billGenerator.getlastBillDate(),billDate,customer.getSelectedGasTarriff().getUnitPerKwh());
        ElectricityBill electricityBill=new ElectricityBill(customer.getSelectedElectricityTarriff().getStandingCharge(),billGenerator.getLastElectricityUnitReading(),electricityEndReading,billGenerator.getlastBillDate(),billDate,customer.getSelectedElectricityTarriff().getUnitPerKwh());
        //set the values to billGenerator class
        billGenerator.setAccountBalance(billGenerator.getAccountBalance()+payment);
        billGenerator.setElectricityBill(electricityBill.getTotalElectricityBill());
        billGenerator.setGasBill(gasBill.getTotalGastBill());
        billGenerator.setlastBillDate(billDate);
        billGenerator.setLastElectricityUnitReading(electricityEndReading);
        billGenerator.setGasUnitReading(gasEndReading);

        previousBalance.setText("£"+billGenerator.toString(billGenerator.getAccountBalance()));
        PaymentMade.setText("£"+payment);
        balance.setText("£"+billGenerator.toString(billGenerator.getAccountBalance()));
        electricity.setText(electricityBill.toString(electricityBill.getTotalElectricityBill()));
        gas.setText(gasBill.toString(gasBill.getTotalGastBill()));
        accountBalance.setText("£"+billGenerator.calculateAccountBalance());
    }


}
