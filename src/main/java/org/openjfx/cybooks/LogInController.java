package org.openjfx.cybooks;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openjfx.cybooks.data.Librarian;
import org.openjfx.cybooks.database.DBHandler;

import java.io.IOException;


public class LogInController {
    private Main main;
    private Stage primaryStage;

    @FXML
    public TextField usernameField;
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
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            if (isValidCredentials(username, password)) {
                // Go to home page
                main.showHomeScene();
            }

        } else {
            errorLabel.setText("Identifiant et/ou mot de passe incorrect");
        }
    }


    private boolean isValidCredentials(String username, String password) {
        Librarian librarian = DBHandler.librarianAuthentication(username, password);

        // Returns 'true' if the 'librarian' variable is not null, 'false' otherwise
        return librarian != null;
    }


    @FXML
    protected void signUpClicked() throws IOException {
        // go to sign up page
        main.showSignUpScene();
    }
}