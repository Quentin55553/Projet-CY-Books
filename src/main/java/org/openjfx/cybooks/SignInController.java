package org.openjfx.cybooks;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInController {


    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public PasswordField password2;
    @FXML
    public Label errorLabel;

    private Stage primaryStage;

    // Default constructor
    public SignInController() {
    }

    // Setter for the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    protected void logUpClicked() throws IOException {
        // go to log in page
        main.showLogInScene();
    }

    @FXML
    public void signInClicked() {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        String confirmedPassword = password2.getText();

        if (isValidSignIn(enteredUsername, enteredPassword, confirmedPassword)) {
            // go to login page
            try {
                main.showLogInScene();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("ERROR");
        }
    }

    private boolean isValidSignIn(String username, String password, String confirmedPassword) {
        // Assuming a valid sign-in requires username and matching passwords
        return !username.isEmpty() && password.equals(confirmedPassword);
    }
}
