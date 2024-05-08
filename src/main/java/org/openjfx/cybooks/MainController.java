package org.openjfx.cybooks;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label errorLabel;

    private Stage primaryStage;

    // Default constructor
    public MainController() {
    }

    // Setter for the primary stage
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    @FXML
    protected void loginClicked() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(isValidCredentials(username, password)) {
            // go to second page
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("second-page.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 800, 600);
                primaryStage.setScene(scene);
                // Set full screen mode
                //primaryStage.setFullScreen(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Invalid username or password");
        }
    }

    private boolean isValidCredentials(String username, String password) {
        return username.equals("admin") && password.equals("admin");
    }
}