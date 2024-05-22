package org.openjfx.cybooks.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class ProfilePageController implements Initializable {
    @FXML
    private VBox CustomerHistory;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 10 le nombre de line de la requete     à changer
        Node[] nodes = new Node[10];

        for (int i = 0; i < nodes.length; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/Item-CustomerHistory.fxml")));
                CustomerHistory.getChildren().add(nodes[i]);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
