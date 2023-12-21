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

public class EditCustomerController {

    @FXML
    private TextField searchField;
    @FXML
    private TextField customerIdField;

    @FXML
    private TextField customerNameField;
    @FXML
    private DatePicker registeredDate;
    @FXML
    private TextField selectedElectricity;
    @FXML
    private TextField selectedGas;
    @FXML
    private Label statusLabel;
    @FXML
    private NumericTextField accountBalanceField;

    @FXML
    private TextField customerEmailField;
    @FXML
    private NumericTextField lastElectricReading;
    @FXML
    private  NumericTextField lastGadReading;

    private Customer firstResult;
    private   BillGenerator billDetails;
    //check for the specific customer
    @FXML
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

    //populate the customer details
    private void populateResultFields(List<Customer> searchResults) throws ParseException {
        if (!searchResults.isEmpty()) {
            firstResult = searchResults.get(0);

            // Populate fields with the first result
            customerIdField.setText(String.valueOf(firstResult.getCustomerId()));
            customerNameField.setText(firstResult.getCustomerName());
            customerEmailField.setText(firstResult.getCustomerEmail());
            registeredDate.setValue(firstResult.getDateJoined());
            selectedElectricity.setText(firstResult.getSelectedElectricityTarriff().getTarrifName());
            selectedGas.setText(firstResult.getSelectedGasTarriff().getTarrifName());
            statusLabel.setText("");
        } else {
            // Clear fields if there are no results
            customerIdField.setText("");
            customerNameField.setText("");
            selectedElectricity.setText("");
            selectedGas.setText("");
            accountBalanceField.setText("");
            lastElectricReading.setText("");
            lastGadReading.setText("");
            statusLabel.setText("unable to finde the customer id - "+searchField.getText());
            // Clear other fields as needed
        }
    }

    //populate the customer bill details
    private void populateResultBillFields(List<BillGenerator> searchResults) {
        if (!searchResults.isEmpty()) {
            billDetails = searchResults.get(0);

            // Populate fields with the first result
            accountBalanceField.setNumericValue(billDetails.getAccountBalance());
            lastElectricReading.setNumericValue(billDetails.getLastElectricityUnitReading());
            lastGadReading.setNumericValue(billDetails.getGasUnitReading());
            statusLabel.setText("");
        } else {
            // Clear fields if there are no results
            statusLabel.setText("unable to finde the customer id - "+searchField.getText());
            // Clear other fields as needed
        }
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
    protected void updateCustomer(ActionEvent e) throws IOException, ClassNotFoundException {
        if(areAllFieldsFilled()){
            //update customeObject
            firstResult.setCustomerEmail(customerEmailField.getText());
            firstResult.setCustomerName(customerNameField.getText());
            firstResult.setDateJoined(registeredDate.getValue());

            //update bill account detail
            billDetails.setAccountBalance(accountBalanceField.getNumericValue());
            billDetails.setGasUnitReading(lastGadReading.getNumericValue());
            billDetails.setLastElectricityUnitReading(lastElectricReading.getNumericValue());

            if(StoreGetCustomer.updateCustomerInFile(firstResult) && StoreGetBillDetails.UpdateDetails(billDetails)){
                showMessage("Customer details updated Successfully");
            }else{
                errorMessage("update Failed");
            }
        }

    }

    @FXML
    protected void deleteCustomer(ActionEvent e) throws IOException, ClassNotFoundException {
        if(areAllFieldsFilled()){

            if(StoreGetCustomer.deleteCustomerFromFile(firstResult.getCustomerId()) && StoreGetBillDetails.DeleteDetails(billDetails.getCustomerId())){
                showMessage("Customer details deleted Successfully");
            }else{
                errorMessage("Delete Failed");
            }
        }

    }

    public static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void errorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean areAllFieldsFilled() {
        return customerIdField.getText() != null && !customerIdField.getText().trim().isEmpty() &&
                customerNameField.getText() != null && !customerNameField.getText().trim().isEmpty() &&
                registeredDate.getValue() != null &&
                selectedElectricity.getText() != null && !selectedElectricity.getText().trim().isEmpty() &&
                selectedGas.getText() != null && !selectedGas.getText().trim().isEmpty() &&
                accountBalanceField.getText() != null && !accountBalanceField.getText().trim().isEmpty() &&
                customerEmailField.getText() != null && !customerEmailField.getText().trim().isEmpty() &&
                lastElectricReading.getText() != null && !lastElectricReading.getText().trim().isEmpty() &&
                lastGadReading.getText() != null && !lastGadReading.getText().trim().isEmpty();
    }

}
