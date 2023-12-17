package com.example.gasbill_generator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label statusLabel;


    @FXML
    protected  void ValidateUser(ActionEvent e) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        // Perform authentication logic (you can replace this with your authentication logic)
        if ("admin".equals(username) && "password".equals(password)) {
            statusLabel.setText("Login successful!");
            Parent parent = FXMLLoader.load(getClass().getResource("HomePage.fxml"));

            // Build the scene graph.
            Scene scene = new Scene(parent);

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            // Display our window, using the scene graph.
            stage.setTitle("Home Page");
            stage.setScene(scene);
            stage.show();
        } else {
            statusLabel.setText("Invalid username or password.");
        }
    }
}
