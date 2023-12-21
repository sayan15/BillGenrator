package com.example.gasbill_generator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

public class BillGeneratorController {
    @FXML
    private TextField searchField;

    @FXML
    private TextField customerIdField;

    @FXML
    private TextField customerNameField;
    @FXML
    private TextField RegisteredDate;
    @FXML
    private TextField selectedElectricity;
    @FXML
    private TextField selectedGas;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField accountBalanceField;
    @FXML
    private NumericTextField electricityUnitField;
    @FXML
    private NumericTextField gasUnitField;
    @FXML
    private DatePicker dateBillPicker;
    @FXML
    private NumericTextField paymentField;
    @FXML
    private TextField lastElectricReading;
    @FXML
    private  TextField lastGadReading;
    @FXML
    private TextField lastPaymentDate;
    private Customer firstResult;
    private   BillGenerator billDetails;
    @FXML
    //check for the specific customer
    private void searchCustomers(ActionEvent event) throws IOException, ClassNotFoundException, ParseException {
        String searchCriteria = searchField.getText().toLowerCase();
        if(!searchCriteria.isEmpty()){
            //read from customer file
            List<Customer> searchResults = StoreGetCustomer.readFromCustomerFile().stream()
                    .filter(customer -> matchesSearchCriteria(customer, searchCriteria))
                    .collect(Collectors.toList());

            //read from bill file to get the bill details
            List<BillGenerator> csvFilesearchResults=StoreGetBillDetails.readFromCustomerBillFile().stream()
                    .filter(customer->matchesSearchCriteriaCsv(customer, searchCriteria))
                    .collect(Collectors.toList());
            //pupulte customer details
            populateResultFields(searchResults);
            //populate bill details
            populateResultBillFields(csvFilesearchResults);
        }

    }

    //populate the customer details
    private void populateResultFields(List<Customer> searchResults) throws ParseException {
        if (!searchResults.isEmpty()) {
            firstResult = searchResults.get(0);

            // Populate fields with the first result
            customerIdField.setText(String.valueOf(firstResult.getCustomerId()));
            customerNameField.setText(firstResult.getCustomerName());
            RegisteredDate.setText(firstResult.getDateJoined().toString());
            selectedElectricity.setText(firstResult.getSelectedElectricityTarriff().getTarrifName());
            selectedGas.setText(firstResult.getSelectedGasTarriff().getTarrifName());
            statusLabel.setText("");
        } else {
            // Clear fields if there are no results
            customerIdField.setText("");
            customerNameField.setText("");
            RegisteredDate.setText("");
            selectedElectricity.setText("");
            selectedGas.setText("");
            accountBalanceField.setText("");
            statusLabel.setText("unable to finde the customer id - "+searchField.getText());
            // Clear other fields as needed
        }
    }
    //populate the customer bill details
    private void populateResultBillFields(List<BillGenerator> searchResults) {
        if (!searchResults.isEmpty()) {
            billDetails = searchResults.get(0);

            // Populate fields with the first result
            accountBalanceField.setText("£"+billDetails.toString(billDetails.getAccountBalance()));
            lastElectricReading.setText(billDetails.toString(billDetails.getLastElectricityUnitReading())+"p");
            lastGadReading.setText(billDetails.toString(billDetails.getGasUnitReading())+"p");
            lastPaymentDate.setText(billDetails.getLastBillDate().toString());
            statusLabel.setText("");
        } else {
            // Clear fields if there are no results
            statusLabel.setText("unable to finde the customer id - "+searchField.getText());
            // Clear other fields as needed
        }
    }
    //dearch criteria based on customer id
    private boolean matchesSearchCriteria(Customer customer, String searchCriteria) {
        return String.valueOf(customer.getCustomerId()).contains(searchCriteria);
        // Add other criteria as needed
    }
    //dearch criteria based on customer id from csv file
    private boolean matchesSearchCriteriaCsv(BillGenerator customer, String searchCriteria) {
        return String.valueOf(customer.getCustomerId()).contains(searchCriteria);
        // Add other criteria as needed
    }
    @FXML
    public void goBack(ActionEvent e) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("HomePage.fxml"));

        // Build the scene graph.
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        // Display our window, using the scene graph.
        stage.setTitle("Home Page");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void generateStatement(ActionEvent e) throws IOException{
        if(!isAnyFieldEmpty() && checkdate() && checkReading() ){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountStatement.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            AccountStatementController secondController = loader.getController();

            // Pass data to the second controller
            secondController.setData(firstResult,paymentField.getNumericValue(),billDetails,electricityUnitField.getNumericValue(),gasUnitField.getNumericValue(),dateBillPicker.getValue());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }

    }

    //check before proceed further
    private boolean isAnyFieldEmpty() {
        return isTextFieldEmpty(searchField)
                || isTextFieldEmpty(customerIdField)
                || isTextFieldEmpty(customerNameField)
                || isTextFieldEmpty(RegisteredDate)
                || isTextFieldEmpty(selectedElectricity)
                || isTextFieldEmpty(selectedGas)
                || isTextFieldEmpty(accountBalanceField)
                || electricityUnitField.getText().trim().isEmpty()
                || gasUnitField.getText().trim().isEmpty()
                || dateBillPicker.getValue() == null;
    }

    //check the date should be greater than lastBilldate
    private boolean checkdate(){
        //check the date is after last bill date and on or before today
        if(dateBillPicker.getValue().isAfter(billDetails.getLastBillDate()  ) && !dateBillPicker.getValue().isBefore(billDetails.getLastBillDate()  )){
            return true;
        }
        else {
            showError("Please select the correct date");
            return false;
        }
    }

    //check the reading should be greater than last bill reading
    private boolean checkReading() {
        if (electricityUnitField.getNumericValue() > billDetails.getLastElectricityUnitReading()
                && gasUnitField.getNumericValue() > billDetails.getGasUnitReading()) {
            return true;
        } else {
            showError("Reading should be greater the last reading");
            return false; // Add a return statement for the case when conditions are not met
        }
    }
    //check for the payment amount
    private double checkPayment(){
        if(paymentField.getNumericValue()>0.0){
            return paymentField.getNumericValue();
        }else{
            return 0.0;
        }
    }
    //create alert
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isTextFieldEmpty(TextField textField) {
        return textField.getText().trim().isEmpty();
    }

}
