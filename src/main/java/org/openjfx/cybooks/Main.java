package org.openjfx.cybooks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        // Load the icon image
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/openjfx/cybooks/img/Cy-books-logo.png")));

        // Set the icon for the stage
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Cy Books");

        // Show the login scene
        showHomeScene();
    }

    public void showSignInScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signin-page.fxml"));
        Parent root = loader.load();
        SignInController controller = loader.getController();
        controller.setMain(this);

        primaryStage.setScene(new Scene(root, 550, 400));
        primaryStage.show();
    }

    public void showLogInScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-page.fxml"));
        Parent root = loader.load();
        LogInController controller = loader.getController();

        controller.setMain(this);
        primaryStage.setScene(new Scene(root, 550, 400));
        primaryStage.show();
    }

    public void showHomeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home-page.fxml"));
        Parent root = loader.load();
        HomePageController controller = loader.getController();
        controller.setMain(this);


        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
