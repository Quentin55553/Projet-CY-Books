package org.openjfx.cybooks;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;


public class addCustomerPageController {
    private Main main;
    private Stage primaryStage;

    @FXML
    public CustomTextField firstname;
    @FXML
    public CustomTextField lastname;
    @FXML
    public CustomTextField telephone;
    @FXML
    public CustomTextField email;
    @FXML
    public CustomTextField address;
    @FXML
    public Label errorLabel;


    public void setMain(Main main) {
        this.main = main;
    }


    // Setter for the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public boolean isValidPhoneNumber(String tel) {

    }


    public boolean isValidEmail(String email) {

    }


    public boolean isValidCustomer(String address) {

    }


    @FXML
    public void addCustomerClicked() {

    }
}
