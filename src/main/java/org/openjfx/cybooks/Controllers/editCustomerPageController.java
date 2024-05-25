package org.openjfx.cybooks.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.controlsfx.control.textfield.CustomTextField;
import org.openjfx.cybooks.UserInput.FieldChecks;
import org.openjfx.cybooks.UserInput.IncorrectFieldException;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.database.DBHandler;

import java.sql.SQLException;


public class editCustomerPageController {
    private Customer customer;

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


    public void setButtonCustomer(Customer customer) {
        this.customer = customer;

        loadCustomerInformation();
    }


    public void loadCustomerInformation() {
        firstnameField.setText(customer.getFirstName());
        lastnameField.setText(customer.getLastName());
        telField.setText(customer.getTel());
        emailField.setText(customer.getEmail());
        addressField.setText(customer.getAddress());
    }


    @FXML
    public void modifyCustomerInformations() {
        String newFirstName = firstnameField.getText();
        String newLastName = lastnameField.getText();
        String newTel = telField.getText();
        String newEmail = emailField.getText();
        String newAddress = addressField.getText();

        try {
            if (FieldChecks.isValidCustomer(newFirstName, newLastName, newTel, newEmail, newAddress)) {
                DBHandler.updateEntireCustomer(customer.getId(), newLastName, newFirstName, newTel, newEmail, newAddress);
                customer.setFirstName(newFirstName);
                customer.setLastName(newLastName);
                customer.setTel(newTel);
                customer.setEmail(newEmail);
                customer.setAddress(newAddress);
                confirmationLabel.setText("Modifications effectuées !");
                errorLabel.setText("");
            }

        } catch (IncorrectFieldException | SQLException e) {
            confirmationLabel.setText("");
            errorLabel.setText(e.getMessage());
        }
    }
}
