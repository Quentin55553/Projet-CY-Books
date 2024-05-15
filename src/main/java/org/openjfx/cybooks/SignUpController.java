package org.openjfx.cybooks;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class SignUpController {
    private Main main;
    private Stage primaryStage;

    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public PasswordField password2;
    @FXML
    public Label errorLabel;


    // Default constructor
    public SignUpController() {
    }


    public void setMain(Main main) {
        this.main = main;
    }


    // Setter for the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    @FXML
    protected void logInClicked() throws IOException {
        // go to log in page
        main.showLogInScene();
    }


    @FXML
    public void signUpClicked() {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        String confirmedPassword = password2.getText();

        if (isValidSignUp(enteredUsername, enteredPassword, confirmedPassword)) {
            // go to login page
            try {
                main.showLogInScene();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            errorLabel.setText("L'identifiant doit avoir au moins 1 caractère et les deux mots de passe doivent être identiques");
        }
    }


    private boolean isValidSignUp(String username, String password, String confirmedPassword) {
        // Assuming a valid sign up requires username and matching passwords
        return !username.isEmpty() && password.equals(confirmedPassword);
    }
}
