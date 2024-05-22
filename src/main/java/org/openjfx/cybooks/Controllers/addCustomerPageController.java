package org.openjfx.cybooks.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.controlsfx.control.textfield.CustomTextField;
import org.openjfx.cybooks.Main;
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


    public boolean isValidFirstname(String firstname) throws IncorrectFirstnameException {
        String format = "[a-zA-Z]+";
        String newFirstname = firstname.trim();

        if (newFirstname.isEmpty()) {
            throw new IncorrectFirstnameException("Le prénom ne peut pas être vide");
        }

        if (!newFirstname.matches(format)) {
            throw new IncorrectFirstnameException("Le format du prénom est incorrect");
        }

        return true;
    }


    public boolean isValidLastname(String lastname) throws IncorrectLastnameException {
        String format = "[a-zA-Z]+";
        String newLastname = lastname.trim();

        if (newLastname.isEmpty()) {
            throw new IncorrectLastnameException("Le nom ne peut pas être vide");
        }

        if (!newLastname.matches(format)) {
            throw new IncorrectLastnameException("Le format du nom est incorrect");
        }

        return true;
    }


    public boolean isValidPhoneNumber(String tel) throws IncorrectPhoneNumberException {
        String format = "^(0[1-9])\\d{8}$";
        String newTel = tel.trim();

        if (newTel.isEmpty()) {
            throw new IncorrectPhoneNumberException("Le numéro de téléphone ne peut pas être vide");
        }

        if (!newTel.matches(format)) {
            throw new IncorrectPhoneNumberException("Le format du numéro de téléphone est incorrect");
        }

        return true;
    }


    public boolean isValidEmail(String email) throws IncorrectEmailException {
        String format = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        String newEmail = email.trim();

        if (newEmail.isEmpty()) {
            throw new IncorrectEmailException("L'email ne peut pas être vide");
        }

        if (!newEmail.matches(format)) {
            throw new IncorrectEmailException("Le format de l'adresse email est incorrect");
        }

        return true;
    }


    public boolean isValidAddress(String address) throws IncorrectAddressException {
        String newAddress = address.trim();

        // Checks whether the address is empty or null
        if (newAddress.isEmpty()) {
            throw new IncorrectAddressException("L'adresse ne peut pas être vide");
        }

        // Splits the address into two parts at the first space
        String[] parts = newAddress.split("\\s+", 2);
        // Checks whether there are two distinct parts in the address and whether the first one only contains numbers
        if (parts.length != 2 || !parts[0].matches("\\d+")) {
            throw new IncorrectAddressException("Le format de l'adresse est incorrect");
        }

        return true;
    }


    @FXML
    public void addCustomerClicked() {
        String enteredFirstname = firstnameField.getText();
        String enteredLastname = lastnameField.getText();
        String enteredTelephone = telField.getText();
        String enteredEmail = emailField.getText();
        String enteredAddress = addressField.getText();

        try {
            if (isValidCustomer(enteredFirstname, enteredLastname, enteredTelephone, enteredEmail, enteredAddress)) {
                DBHandler.addCustomer(enteredFirstname, enteredLastname, enteredTelephone, enteredEmail, enteredAddress);
                confirmationLabel.setText("Membre ajouté !");
                errorLabel.setText("");
            }

        } catch (IncorrectFieldException | SQLException e) {
            confirmationLabel.setText("");
            errorLabel.setText(e.getMessage());
        }
    }


    public boolean isValidCustomer(String firstname, String lastname, String telephone, String email, String address) throws IncorrectFieldException {
        return isValidFirstname(firstname) && isValidLastname(lastname) && isValidPhoneNumber(telephone) && isValidEmail(email) && isValidAddress(address);
    }
}
