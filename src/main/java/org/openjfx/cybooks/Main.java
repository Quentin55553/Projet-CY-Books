package org.openjfx.cybooks;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        // Load the icon image
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/openjfx/cybooks/img/icon.gif")));

        // Set the icon for the stage
        primaryStage.getIcons().add(icon);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/cybooks/home-page.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.setPrimaryStage(primaryStage); // Pass the primary stage to the controller
        
        Scene scene = new Scene(root); // Set initial scene
        primaryStage.setScene(scene);
        primaryStage.setTitle("CY-Books");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
