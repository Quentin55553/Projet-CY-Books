package org.openjfx.cybooks.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.openjfx.cybooks.Main;
import org.openjfx.cybooks.UserInput.FieldChecks;
import org.openjfx.cybooks.UserInput.IncorrectFieldException;
import org.openjfx.cybooks.data.Librarian;
import org.openjfx.cybooks.database.DBHandler;
import org.openjfx.cybooks.UserInput.IncorrectPasswordException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * Controller class for the page that allows the user to log in using their credentials
 */
public class LogInController {
    /**
     * Reference to the main application
     */
    private Main main;
    /**
     * Main container
     */
    private Stage primaryStage;

    /**
     * Login field for logging in
     */
    @FXML
    public TextField loginField;
    /**
     * Password field for logging in
     */
    @FXML
    public PasswordField passwordField;
    /**
     * Label used to show the error messages
     */
    @FXML
    public Label errorLabel;


    /**
     * Default constructor for the LogInController class
     */
    public LogInController() {
    }


    /**
     * Setter for the main attribute
     * @param main The reference to the main app
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
     * This method handles the login button click
     * It checks if the entered credentials are valid
     * @throws IOException If an error occurs during a scene transition to the home page
     */
    @FXML
    protected void loginClicked() throws IOException {
        String login = loginField.getText();
        String password = passwordField.getText();

        try {
            if (FieldChecks.areValidCredentials(login, password)) {
                // Go to home page
                main.showHomeScene(login);
            }

        } catch (IncorrectFieldException | NoSuchElementException e) {
            errorLabel.setText(e.getMessage());
        }
    }


    /**
     * This method handles the sign up button click
     * It changes the page to the sign up page
     * @throws IOException If there is an error during the scene transition
     */
    @FXML
    protected void signUpClicked() throws IOException {
        // go to sign up page
        main.showSignUpScene();
    }
}