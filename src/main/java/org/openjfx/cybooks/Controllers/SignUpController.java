package org.openjfx.cybooks.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openjfx.cybooks.Main;
import org.openjfx.cybooks.UserInput.FieldChecks;
import org.openjfx.cybooks.UserInput.IncorrectFieldException;
import org.openjfx.cybooks.data.Core;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller class for the page that allows the user to create an account
 */
public class SignUpController {
    /**
     * Reference to the main application
     */
    private Main main;
    /**
     * Main container
     */
    private Stage primaryStage;

    /**
     * Login field for signing up
     */
    @FXML
    public TextField login;
    /**
     * Lastname field for signing up
     */
    @FXML
    public TextField lastname;
    /**
     * Firstname field for signing up
     */
    @FXML
    public TextField firstname;
    /**
     * Password field for signing up
     */
    @FXML
    public PasswordField password;
    /**
     * Password confirmation field for signing up
     */
    @FXML
    public PasswordField password2;
    /**
     * Label used to show the error messages
     */
    @FXML
    public Label errorLabel;


    /**
     * Default constructor for the SignUpController class
     */
    public SignUpController() {
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
     * This method handles the log in button click
     * It changes the page to the log in page
     * @throws IOException If there is an error during the scene transition
     */
    @FXML
    protected void logInClicked() throws IOException {
        // go to log in page
        main.showLogInScene();
    }

    /**
     * This method handles the sign up button click
     * It checks if the entered information is valid and adds the new user to the database if that's the case
     */
    @FXML
    public void signUpClicked() {
        String enteredLogin = login.getText();
        String enteredLastname = lastname.getText();
        String enteredFirstname = firstname.getText();
        String enteredPassword = password.getText();
        String confirmedPassword = password2.getText();
        try {
            if (FieldChecks.isValidSignUp(enteredLogin, enteredLastname, enteredFirstname, enteredPassword, confirmedPassword)) {
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
        catch (IncorrectFieldException e){
            errorLabel.setText(e.getMessage());
        }
    }


}
