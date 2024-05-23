package org.openjfx.cybooks.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.controlsfx.control.textfield.CustomTextField;
import org.openjfx.cybooks.Main;
import org.openjfx.cybooks.UserInput.FieldChecks;
import org.openjfx.cybooks.UserInput.IncorrectFieldException;
import org.openjfx.cybooks.database.*;

import java.sql.SQLException;


public class addCustomerPageController {
    private Main main;
    private Stage primaryStage;

    @FXML
    public CustomTextField firstnameField;
    @FXML
    public CustomTextField lastnameField;
    @FXML
    public CustomTextField telField;
    @FXML
    public CustomTextField emailField;
    @FXML
    public CustomTextField addressField;
    @FXML
    public Label errorLabel;
    @FXML
    public Label confirmationLabel;

    public void setMain(Main main) {
        this.main = main;
    }

    // Setter for the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void addCustomerClicked() {
        String enteredFirstname = firstnameField.getText();
        String enteredLastname = lastnameField.getText();
        String enteredTelephone = telField.getText();
        String enteredEmail = emailField.getText();
        String enteredAddress = addressField.getText();

        try {
            if (FieldChecks.isValidCustomer(enteredFirstname, enteredLastname, enteredTelephone, enteredEmail, enteredAddress)) {
                DBHandler.addCustomer(enteredFirstname, enteredLastname, enteredTelephone, enteredEmail, enteredAddress);
                confirmationLabel.setText("Membre ajouté !");
                errorLabel.setText("");
            }

        } catch (IncorrectFieldException | SQLException e) {
            confirmationLabel.setText("");
            errorLabel.setText(e.getMessage());
        }
    }
}
