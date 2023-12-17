package com.example.gasbill_generator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RegisterCustomerController {
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerEmailField;
    @FXML
    private ComboBox<Tariff> tarrifTypeElectricityComboBox;
    @FXML
    private ComboBox<Tariff> tarrifTypeGasComboBox;

    @FXML
    private NumericTextField electricityunitPerKwhField;

    @FXML
    private NumericTextField gasunitPerKwhField;

    @FXML
    private DatePicker dateJoinedPicker;

    @FXML
    private  Label statusLabel;


    @FXML
    protected  void registerCutomer(ActionEvent e) throws IOException, ClassNotFoundException, ParseException {
        // Validate all fields before proceeding with saving.
        if (isValidInput()) {
            Customer customer=new Customer(customerNameField.getText(),customerEmailField.getText(), dateJoinedPicker.getValue(), tarrifTypeElectricityComboBox.getValue(),tarrifTypeGasComboBox.getValue(),electricityunitPerKwhField.getNumericValue(),gasunitPerKwhField.getNumericValue());

            statusLabel.setText(StoreGetCustomer.addCustomerToFile((customer)));
        } else {
            System.out.println("Please fill in all required fields.");
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
    private void initialize() {
        // Call this method during the initialization of your controller, for example, in the initialize method.

        try {
            ArrayList<Tariff> tariffList = StoreGetTariff.readFromFile();

            // Separate tariffs based on type
            List<Tariff> gasTariffs = tariffList.stream()
                    .filter(tariff -> "Gas".equals(tariff.getTarrifType()))
                    .collect(Collectors.toList());

            List<Tariff> electricityTariffs = tariffList.stream()
                    .filter(tariff -> "Electricity".equals(tariff.getTarrifType()))
                    .collect(Collectors.toList());

            tarrifTypeElectricityComboBox.getItems().addAll(electricityTariffs);
            tarrifTypeGasComboBox.getItems().addAll(gasTariffs);

            // Set custom cell factory to display tariff names
            tarrifTypeElectricityComboBox.setCellFactory(new Callback<ListView<Tariff>, ListCell<Tariff>>() {
                @Override
                public ListCell<Tariff> call(ListView<Tariff> param) {
                    return new ListCell<Tariff>() {
                        @Override
                        protected void updateItem(Tariff item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.getTarrifName());
                            }
                        }
                    };
                }
            });

            // Set custom button cell for displaying the selected item
            tarrifTypeElectricityComboBox.setButtonCell(new ListCell<Tariff>() {
                @Override
                protected void updateItem(Tariff item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getTarrifName());
                    }
                }
            });

            // Set custom cell factory to display tariff names gas
            tarrifTypeGasComboBox.setCellFactory(new Callback<ListView<Tariff>, ListCell<Tariff>>() {
                @Override
                public ListCell<Tariff> call(ListView<Tariff> param) {
                    return new ListCell<Tariff>() {
                        @Override
                        protected void updateItem(Tariff item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.getTarrifName());
                            }
                        }
                    };
                }
            });

            // Set custom button cell for displaying the selected item gas
            tarrifTypeGasComboBox.setButtonCell(new ListCell<Tariff>() {
                @Override
                protected void updateItem(Tariff item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getTarrifName());
                    }
                }
            });
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    private boolean isValidInput() {
        // Check if all fields are filled.
        return !customerNameField.getText().isEmpty() &&
                !customerEmailField.getText().isEmpty() &&
                tarrifTypeElectricityComboBox.getValue() != null &&
                tarrifTypeGasComboBox.getValue() != null &&
                !electricityunitPerKwhField.getText().isEmpty() &&
                !gasunitPerKwhField.getText().isEmpty() &&
                dateJoinedPicker.getValue()!= null;
    }

}
