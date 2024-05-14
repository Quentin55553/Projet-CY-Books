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

public class LogInController {

    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label errorLabel;

    private Stage primaryStage;

    // Default constructor
    public LogInController() {
    }

    // Setter for the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    @FXML
    protected void loginClicked() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(isValidCredentials(username, password)) {
            // go to home page
            main.showHomeScene();
        } else {
            errorLabel.setText("Invalid username or password");
        }
    }

    private boolean isValidCredentials(String username, String password) {
        return username.equals("admin") && password.equals("admin");
    }

    @FXML
    protected void signUpClicked() throws IOException {
        // go to sign in page
        main.showSignInScene();
    }
}