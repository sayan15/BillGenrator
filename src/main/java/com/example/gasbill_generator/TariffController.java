package com.example.gasbill_generator;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TariffController implements Initializable {
    @FXML
    private TextField tarrifNameField;

    @FXML
    private ComboBox<String> tarrifTypeComboBox;

    @FXML
    private NumericTextField standingChargeField;

    @FXML
    private NumericTextField  unitPerKwhField;
    @FXML
    private TableView<Tariff> tariffTableView;

        @FXML
        public void saveTariff(ActionEvent e) throws IOException,ClassNotFoundException{ {
            // Validate all fields before proceeding with saving.
            if (isValidInput()) {
                // Your save logic here
                Tariff tariff=new Tariff(1,tarrifNameField.getText(),tarrifTypeComboBox.getValue(), standingChargeField.getNumericValue(), unitPerKwhField.getNumericValue());

                System.out.println(StoreGetTariff.addTariffToFile(tariff));
                populateTariffTable();
            } else {
                System.out.println("Please fill in all required fields.");
            }

        }}

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
    //populte the existing tartiff in the table
    public void populateTariffTable() {
        try {
            ArrayList<Tariff> existingTariffs = StoreGetTariff.readFromFile();

            TableColumn<Tariff, String> tariffNameColumn = new TableColumn<>("Tariff Name");
            tariffNameColumn.setCellValueFactory(new PropertyValueFactory<>("tarrifName"));

            TableColumn<Tariff, String> tariffTypeColumn = new TableColumn<>("Tariff Type");
            tariffTypeColumn.setCellValueFactory(new PropertyValueFactory<>("tarrifType"));

            TableColumn<Tariff, Double> standingChargeColumn = new TableColumn<>("Standing Charge");
            standingChargeColumn.setCellValueFactory(new PropertyValueFactory<>("standingCharge"));

            TableColumn<Tariff, Double> unitPerKwhColumn = new TableColumn<>("Unit per kWh");
            unitPerKwhColumn.setCellValueFactory(new PropertyValueFactory<>("unitPerKwh"));

            // Add a column for delete buttons
            TableColumn<Tariff, Boolean> actionCol = new TableColumn<>("Action");
            actionCol.setSortable(false);

            // create a cell value factory with a custom cell for each row in the table.
            actionCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tariff, Boolean>, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Tariff, Boolean> features) {
                    return new SimpleBooleanProperty(true); // You can customize this based on your data
                }
            });

            // Set the custom cell factory
            actionCol.setCellFactory(new Callback<TableColumn<Tariff, Boolean>, TableCell<Tariff, Boolean>>() {
                @Override
                public TableCell<Tariff, Boolean> call(TableColumn<Tariff, Boolean> personBooleanTableColumn) {
                    return new DeleteTarriffButtonCell();
                }
            });

            tariffTableView.getColumns().clear();
            tariffTableView.getColumns().addAll(tariffNameColumn, tariffTypeColumn, standingChargeColumn, unitPerKwhColumn, actionCol);

            ObservableList<Tariff> tariffObservableList = FXCollections.observableArrayList(existingTariffs);
            tariffTableView.getItems().clear();
            tariffTableView.getItems().addAll(tariffObservableList);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
    }

    // Initialize method, called when the view is loaded
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateTariffTable();
    }

    private boolean isValidInput() {
            // Check if all fields are filled.
            return !tarrifNameField.getText().isEmpty() &&
                    tarrifTypeComboBox.getValue() != null &&
                    !standingChargeField.getText().isEmpty() &&
                    !unitPerKwhField.getText().isEmpty();
        }
}
