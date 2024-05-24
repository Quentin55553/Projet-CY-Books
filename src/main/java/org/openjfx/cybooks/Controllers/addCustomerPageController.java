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


/**
 * Controller class for the page that allows the user to add a new customer to the database
 */
public class addCustomerPageController {

    /**
     * Reference to the main application
     */
    private Main main;
    /**
     * Main container
     */
    private Stage primaryStage;

    /**
     * Firstname field for the addition of a customer
     */
    @FXML
    public CustomTextField firstnameField;
    /**
     * Lastname field for the addition of a customer
     */
    @FXML
    public CustomTextField lastnameField;

    /**
     * Phone number field for the addition of a customer
     */
    @FXML
    public CustomTextField telField;
    /**
     * Email field for the addition of a customer
     */
    @FXML
    public CustomTextField emailField;
    /**
     * Adress field for the addition of a customer
     */
    @FXML
    public CustomTextField addressField;
    /**
     * Error label for the addition of a customer
     */
    @FXML
    public Label errorLabel;

    /**
     * Confirmation label for the addition of a customer
     */
    @FXML
    public Label confirmationLabel;

    /**
     * Setter for the main attribute
     * @param main Reference to the main application
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * Setter for the primary stage attribute
     * @param primaryStage The main container
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * This method is called when the 'Add customer' button is clicked.
     * It checks if the entered values are correct and if they are, adds the new customer to the database
     */
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
