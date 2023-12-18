package com.example.gasbill_generator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;
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
    private  GasBill gasBill;
    private ElectricityBill electricityBill;
    private BillGenerator billGenerator;
    private  Customer customer;
    public void setData(Customer customer,double payment, BillGenerator billGenerator, double electricityEndReading, double gasEndReading, LocalDate billDate){
        gasBill=new GasBill(customer.getSelectedGasTarriff().getStandingCharge(),billGenerator.getGasUnitReading(),gasEndReading,billGenerator.getLastBillDate(),billDate,customer.getSelectedGasTarriff().getUnitPerKwh());
        electricityBill=new ElectricityBill(customer.getSelectedElectricityTarriff().getStandingCharge(),billGenerator.getLastElectricityUnitReading(),electricityEndReading,billGenerator.getLastBillDate(),billDate,customer.getSelectedElectricityTarriff().getUnitPerKwh());
        //set the values to billGenerator class
        this.billGenerator=billGenerator;
        this.customer=customer;
        previousBalance.setText("£"+billGenerator.toString(billGenerator.getAccountBalance()));

        billGenerator.makePayment(payment);
        billGenerator.setElectricityBill(electricityBill.getTotalElectricityBill());
        billGenerator.setGasBill(gasBill.getTotalGastBill());
        billGenerator.setlastBillDate(billDate);
        billGenerator.setLastElectricityUnitReading(electricityEndReading);
        billGenerator.setGasUnitReading(gasEndReading);


        PaymentMade.setText("£"+payment);
        balance.setText("£"+billGenerator.toString(billGenerator.getAccountBalance()));
        electricity.setText(electricityBill.toString(electricityBill.getTotalElectricityBill()));
        gas.setText(gasBill.toString(gasBill.getTotalGastBill()));
        accountBalance.setText("£"+billGenerator.calculateAccountBalance());
    }

    @FXML
    private void approveStatement(ActionEvent e) throws IOException, ClassNotFoundException {
        StoreGetBillDetails.UpdateDetails(billGenerator);
        String[] recepients = {customer.getCustomerEmail()};
        String[] bccRecepients = {customer.getCustomerEmail()};
        String subject = "Monthly Bill";
        String filePath = billGenerator.getCustomerId()+"_"+billGenerator.getLastBillDate().toString()+".pdf";

        Pdf_Creator pdf_creator=new Pdf_Creator();
        pdf_creator.convertNodeToPdf(billGenerator,filePath);

        MailUtil mailUtil = new MailUtil();
        boolean mailSent = mailUtil.sendMail(recepients, bccRecepients, subject, filePath);

        if (mailSent) {
            showMessage("Mail sent successfully!");
        } else {
            showMessage("Failed to send mail.");
        }
    }

    //create alert
    public static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
