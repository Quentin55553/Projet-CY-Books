package org.openjfx.cybooks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.openjfx.cybooks.Controllers.HomePageController;
import org.openjfx.cybooks.Controllers.LogInController;
import org.openjfx.cybooks.Controllers.SignUpController;
import org.openjfx.cybooks.database.DBHandler;

import java.io.IOException;
import java.nio.file.Paths;
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
        primaryStage.setTitle("CY-Books");

        // Show the login scene
        showLogInScene();
    }


    public void showSignUpScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmlFiles/signup-page.fxml"));
        Parent root = loader.load();
        SignUpController controller = loader.getController();
        controller.setMain(this);
        // Prevents the user from resizing the window
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root, 550, 470));
        primaryStage.show();
    }


    public void showLogInScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmlFiles/login-page.fxml"));
        Parent root = loader.load();
        LogInController controller = loader.getController();
        controller.setMain(this);
        // Prevents the user from resizing the window
        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root, 550, 470));
        primaryStage.show();
    }


    public void showHomeScene(String id) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxmlFiles/home-page.fxml"));
        Parent root = loader.load();
        HomePageController controller = loader.getController();
        controller.setMain(this);
        // Pass the librarian id to controller and set it
        controller.setLibrarianID(id);

        // Makes the user able to resize the window
        primaryStage.setResizable(true);

        primaryStage.setScene(new Scene(root, 1300, 800));
        primaryStage.show();
        // Log
        System.out.println("Connected to: " + id);
    }


    public static void main(String[] args) {
        // Executes the database creation file
        DBHandler.executeSQLFile(Paths.get("").toAbsolutePath().toString() + "/src/main/resources/org/openjfx/cybooks/database/CY-Books.sql");

        launch(args);
    }
}
