package org.openjfx.cybooks.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.openjfx.cybooks.Main;
import org.openjfx.cybooks.data.Librarian;
import org.openjfx.cybooks.database.DBHandler;
import org.openjfx.cybooks.database.IncorrectPasswordException;

import java.io.IOException;
import java.util.NoSuchElementException;


public class LogInController {
    private Main main;
    private Stage primaryStage;

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label errorLabel;


    // Default constructor
    public LogInController() {
    }


    public void setMain(Main main) {
        this.main = main;
    }


    // Setter for the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    @FXML
    protected void loginClicked() throws IOException {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (!login.isEmpty() && !password.isEmpty()) {
            if (isValidCredentials(login, password)) {
                // Go to home page
                main.showHomeScene(login);
            }

        } else {
            errorLabel.setText("Les deux champs ne doivent pas être vides");
        }
    }


    private boolean isValidCredentials(String login, String password) {
        Librarian librarian = null;

        try {
            librarian = DBHandler.librarianAuthentication(login, password);

        } catch (NoSuchElementException | IncorrectPasswordException e) {
            errorLabel.setText(e.getMessage());
        }

        // Returns 'true' if the 'librarian' variable is not null, 'false' otherwise
        return librarian != null;
    }


    @FXML
    protected void signUpClicked() throws IOException {
        // go to sign up page
        main.showSignUpScene();
    }
}