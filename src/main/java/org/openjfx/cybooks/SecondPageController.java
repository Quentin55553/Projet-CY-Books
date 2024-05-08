package org.openjfx.cybooks;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class SecondPageController {

    @FXML
    private Label headerLabel;
    @FXML
    private ImageView book;

    @FXML
    protected void initialize() {
        // Load and set the image
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/openjfx/cybooks/img/enchanted_book.gif")));
        book.setImage(image);
        // Set the fit width and height
        book.setFitWidth(25);
        book.setFitHeight(25);

    }
}