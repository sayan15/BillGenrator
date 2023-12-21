package com.example.gasbill_generator;

import javafx.application.Platform;
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

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class HomePageController implements Initializable {
    @FXML
    private Label numberCustomer;
    @FXML
    private Label numberTarriff;

    @FXML
    private TextField searchField;
    @FXML
    private TableView<BillGenerator> homePageTableView;


    @FXML
    protected void onHelloButtonClick(ActionEvent e) throws IOException,ClassNotFoundException {
        Parent parent = FXMLLoader.load(getClass().getResource("TarrifCreator.fxml"));

        // Build the scene graph.
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        // Display our window, using the scene graph.
        stage.setTitle("Tarriff Creator");
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    protected void customerButtonClick(ActionEvent e) throws IOException,ClassNotFoundException {
        Parent parent = FXMLLoader.load(getClass().getResource("RegisterCustomerPage.fxml"));

        // Build the scene graph.
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        // Display our window, using the scene graph.
        stage.setTitle("Tarriff Creator");
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    protected void viewCustomerClick(ActionEvent e) throws IOException,ClassNotFoundException {
        Parent parent = FXMLLoader.load(getClass().getResource("ViewCustomersPage.fxml"));

        // Build the scene graph.
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        // Display our window, using the scene graph.
        stage.setTitle("View Customers");
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    protected void generateBillClick(ActionEvent e) throws IOException,ClassNotFoundException {
        Parent parent = FXMLLoader.load(getClass().getResource("BillGenerator.fxml"));

        // Build the scene graph.
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        // Display our window, using the scene graph.
        stage.setTitle("Generate Bill");
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    protected void editCustomer(ActionEvent e) throws IOException,ClassNotFoundException {
        Parent parent = FXMLLoader.load(getClass().getResource("EditCustomer.fxml"));

        // Build the scene graph.
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        // Display our window, using the scene graph.
        stage.setTitle("Edit Customer");
        stage.setScene(scene);
        stage.show();


    }

    private void populateDashBoard() throws IOException, ClassNotFoundException {
        ArrayList<Customer> customers = StoreGetCustomer.readFromCustomerFile();
        ArrayList<Tariff> tariffs=StoreGetTariff.readFromFile();

        numberCustomer.setText(String.valueOf(customers.size()));
        numberTarriff.setText(String.valueOf(tariffs.size()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            populateDashBoard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //check for the specific customer
    @FXML
    private void getPaymentHistory(ActionEvent event) throws IOException, ClassNotFoundException, ParseException {
        String searchCriteria = searchField.getText().toLowerCase();
        if(!searchCriteria.isEmpty()){
            //read from customer file
            List<BillGenerator> searchResults = StoreGetBillDetails.readFromCustomerPaymentFile().stream()
                    .filter(customer -> matchesSearchCriteria(customer, searchCriteria))
                    .collect(Collectors.toList());

            //pupulte customer details
            populateResultFields(searchResults);
        }

    }

    //dearch criteria based on customer id
    private boolean matchesSearchCriteria(BillGenerator customer, String searchCriteria) {
        return String.valueOf(customer.getCustomerId()).contains(searchCriteria);
        // Add other criteria as needed
    }

    private void populateResultFields(List<BillGenerator> searchResults) throws ParseException {
        List<BillGenerator> existingCustomer= searchResults;

        TableColumn<BillGenerator, String> customerNameColumn = new TableColumn<>("Customer ID");
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));


        TableColumn<BillGenerator, LocalDate> dateJoinedColumn = new TableColumn<>("lastBillDate");
        dateJoinedColumn.setCellValueFactory(new PropertyValueFactory<>("lastBillDate"));


        TableColumn<BillGenerator, Double> gasBillColumn = new TableColumn<>("Gas Bill");
        gasBillColumn.setCellValueFactory(new PropertyValueFactory<>("gasBill"));

        TableColumn<BillGenerator, Double> electricityBillColumn= new TableColumn<>("Electric Bill");
        electricityBillColumn.setCellValueFactory(new PropertyValueFactory<>("electricityBill"));

        TableColumn<BillGenerator, Double> totalBillColumn= new TableColumn<>("Total Bill");
        totalBillColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<BillGenerator, Double> paymentColumn= new TableColumn<>("Payment");
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("payment"));

        homePageTableView.getColumns().clear();
        homePageTableView.getColumns().addAll(customerNameColumn, dateJoinedColumn, gasBillColumn,electricityBillColumn,totalBillColumn,paymentColumn);

        ObservableList<BillGenerator> customerObservableList = FXCollections.observableArrayList(existingCustomer);
        homePageTableView.getItems().clear();
        homePageTableView.getItems().addAll(customerObservableList);
    }

    //logout from the application
    @FXML
    protected void logOutClick(ActionEvent e)throws IOException{
        Platform.exit();
    }
}
