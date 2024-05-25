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
    /**
     * The customer to edit
     */
    private Customer customer;

    /**
     * Firstname field for editing the information of the customer
     */
    @FXML
    public CustomTextField firstnameField;
    /**
     * Lastname field for editing the information of the customer
     */
    @FXML
    public CustomTextField lastnameField;
    /**
     * Phone number field for editing the information of the customer
     */
    @FXML
    public CustomTextField telField;
    /**
     * Email field for editing the information of the customer
     */
    @FXML
    public CustomTextField emailField;
    /**
     * Adress field for editing the information of the customer
     */
    @FXML
    public CustomTextField addressField;
    /**
     * Label used to show the error messages
     */
    @FXML
    public Label errorLabel;
    /**
     * Label used to show the confirmation messages
     */
    @FXML
    public Label confirmationLabel;

    /**
     * This method loads the page for the specified customer
     * @param customer The Customer object to use
     */
    public void setButtonCustomer(Customer customer) {
        this.customer = customer;

        loadCustomerInformation();
    }

    /**
     * This method loads the information of the customer on the view
     */
    public void loadCustomerInformation() {
        firstnameField.setText(customer.getFirstName());
        lastnameField.setText(customer.getLastName());
        telField.setText(customer.getTel());
        emailField.setText(customer.getEmail());
        addressField.setText(customer.getAddress());
    }


    /**
     * This method updates the profile of a customer in the database using the information entered in the fields of the page
     */
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
