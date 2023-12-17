package com.example.gasbill_generator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    @FXML
    private Label welcomeText;

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


}
