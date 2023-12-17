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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewCutomersPageController implements Initializable {
    @FXML
    private TableView<Customer> customerTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateCustomerTable();
    }
    //populte the existing tartiff in the table
    public void populateCustomerTable() {
        try {
            ArrayList<Customer> existingCustomer= StoreGetCustomer.readFromCustomerFile();

            TableColumn<Customer, String> customerNameColumn = new TableColumn<>("Customer Name");
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

            TableColumn<Customer, String> customerEmailColumn = new TableColumn<>("Customer Email");
            customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));

            TableColumn<Customer, Date> dateJoinedColumn = new TableColumn<>("Date Joined");
            dateJoinedColumn.setCellValueFactory(new PropertyValueFactory<>("dateJoined"));

            TableColumn<Customer, Tariff> selectedElectricityTariffColumn = new TableColumn<>("Selected Electricity Tariff");
            selectedElectricityTariffColumn.setCellValueFactory(new PropertyValueFactory<>("selectedElectricityTarriff"));
            selectedElectricityTariffColumn.setCellFactory(column -> {
                return new TableCell<Customer, Tariff>() {
                    @Override
                    protected void updateItem(Tariff tariff, boolean empty) {
                        super.updateItem(tariff, empty);

                        if (tariff == null || empty) {
                            setText(null);
                        } else {
                            setText(tariff.getTarrifName());
                        }
                    }
                };
            });

            TableColumn<Customer, Tariff> selectedGasTariffColumn = new TableColumn<>("Selected Gas Tariff");
            selectedGasTariffColumn.setCellValueFactory(new PropertyValueFactory<>("selectedGasTarriff"));
            selectedGasTariffColumn.setCellFactory(column -> {
                return new TableCell<Customer, Tariff>() {
                    @Override
                    protected void updateItem(Tariff tariff, boolean empty) {
                        super.updateItem(tariff, empty);

                        if (tariff == null || empty) {
                            setText(null);
                        } else {
                            setText(tariff.getTarrifName());
                        }
                    }
                };
            });

            TableColumn<Customer, Double> electricityUnitKwhColumn = new TableColumn<>("Electricity Unit kWh");
            electricityUnitKwhColumn.setCellValueFactory(new PropertyValueFactory<>("electricityunitKwhField"));

            TableColumn<Customer, Double> gasUnitKwhColumn = new TableColumn<>("Gas Unit kWh");
            gasUnitKwhColumn.setCellValueFactory(new PropertyValueFactory<>("gasunitKwhField"));

            customerTableView.getColumns().clear();
            customerTableView.getColumns().addAll(customerNameColumn, customerEmailColumn, dateJoinedColumn,
                    selectedElectricityTariffColumn, selectedGasTariffColumn, electricityUnitKwhColumn, gasUnitKwhColumn);

            ObservableList<Customer> customerObservableList = FXCollections.observableArrayList(existingCustomer);
            customerTableView.getItems().clear();
            customerTableView.getItems().addAll(customerObservableList);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
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

}
