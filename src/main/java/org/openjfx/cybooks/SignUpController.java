package org.openjfx.cybooks;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openjfx.cybooks.data.Core;

import java.io.IOException;
import java.sql.SQLException;


public class SignUpController {
    private Main main;
    private Stage primaryStage;

    @FXML
    public TextField login;
    @FXML
    public TextField lastname;
    @FXML
    public TextField firstname;
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
        String enteredLogin = login.getText();
        String enteredLastname = lastname.getText();
        String enteredFirstname = firstname.getText();
        String enteredPassword = password.getText();
        String confirmedPassword = password2.getText();

        if (isValidSignUp(enteredLogin, enteredLastname, enteredFirstname, enteredPassword, confirmedPassword)) {
            // Go to login page
            try {
                Core.addLibrarian(enteredLogin, enteredLastname, enteredFirstname, enteredPassword);
                main.showLogInScene();

            } catch (IOException e) {
                e.printStackTrace();

            } catch (SQLException e) {
                errorLabel.setText(e.getMessage());
            }

        } else {
            errorLabel.setText("Tous les champs doivent avoir au moins 1 caractère \n et les deux mots de passe doivent être identiques");
        }
    }


    private boolean isValidSignUp(String login, String lastname, String firstname, String password, String confirmedPassword) {
        // Assuming a valid sign up requires a unique login and matching passwords
        return !login.isEmpty() && !lastname.isEmpty() && !firstname.isEmpty() && !password.isEmpty() && password.equals(confirmedPassword);
    }
}
