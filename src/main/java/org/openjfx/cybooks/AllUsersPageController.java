package org.openjfx.cybooks;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class AllUsersPageController implements Initializable {
    @FXML
    private HomePageController homePageController;
    @FXML
    private VBox AllUsersVbox;
    @FXML
    private FontAwesomeIconView ChevronLeft;
    @FXML
    private FontAwesomeIconView ChevronRight;
    private int currentPage = 0;
    private int rowsPerPage = 10; // Valeur par défaut
    private List<String> results;
    @FXML
    AnchorPane AllUsersAnchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        results = getResultsFromDatabase();
        try {
            showPage(0);
            updateButtonStates(results.size() / rowsPerPage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void Previous() throws IOException {
        showPage(currentPage - 1);
    }


    @FXML
    private void Next() throws IOException {
        showPage(currentPage + 1);
    }


    private List<String> getResultsFromDatabase() {
        List<String> results = new ArrayList<>();

        // test
        results.add("apple");
        results.add("banana");
        results.add("cherry");
        results.add("date");
        results.add("elderberry");
        results.add("fig");
        results.add("grape");
        results.add("honeydew");
        results.add("kiwi");
        results.add("lemon");
        results.add("mango");
        results.add("nectarine");
        results.add("orange");
        results.add("papaya");
        results.add("quince");
        results.add("raspberry");
        results.add("strawberry");
        results.add("tangerine");
        results.add("ugli fruit");
        results.add("vanilla bean");
        results.add("watermelon");
        results.add("xigua");
        results.add("yellow passion fruit");
        results.add("zucchini");
        /*try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "your_username", "your_password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM your_table");
            while (resultSet.next()) {
                results.add(resultSet.getString("your_column"));
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return results;
    }


    private int getTotalPages() {
        return (results.size() + rowsPerPage - 1) / rowsPerPage;
    }


    private void showPage(int page) throws IOException {
        int totalPages = getTotalPages();

        if (page < 0 || page > results.size() / rowsPerPage) {
            return;
        }

        currentPage = page;
        AllUsersVbox.getChildren().clear();

        int start = page * rowsPerPage;
        int end = Math.min(start + rowsPerPage, results.size());

        for (int i = start; i < end; i++) {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Item-AllUsers.fxml")));

            if (node instanceof Parent){
                Button button = (Button) ((Parent)node).lookup("#ProfilButton");

                if (button != null) {
                    //set ID   (il faudrait mettre l'id du membre comme ça on appel la fonction pour la page spécifique)
                    button.setId("ProfilButton"+i);
                    // Set the onAction event handler for the button
                    button.setOnAction(actionEvent -> handleButtonClick(button.getId()));
                }
            }
            AllUsersVbox.getChildren().add(node);
        }

        System.out.println("Showing page " + page + " from index " + start + " to " + (end - 1));
        updateButtonStates(totalPages);
    }


    private void updateButtonStates(int totalPages) {
        ChevronLeft.setVisible(currentPage > 0);
        ChevronRight.setVisible(currentPage < totalPages - 1);
    }


    @FXML
    private void handleButtonClick(String id) {
        System.out.println(id);

        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Profil-page.fxml"));
            Parent parent = AllUsersAnchorPane.getParent();

            if (parent instanceof AnchorPane) {
                AnchorPane center = (AnchorPane) parent;

                AnchorPane newCenter = loader.load();
                // Set the size constraints of the new AnchorPane
                newCenter.setPrefSize(center.getWidth(), center.getHeight());

                AnchorPane.setTopAnchor(newCenter, 0.0);
                AnchorPane.setLeftAnchor(newCenter, 210.0);
                AnchorPane.setBottomAnchor(newCenter, 0.0);
                AnchorPane.setRightAnchor(newCenter, 210.0);

                // Replace the embedded node with the new one
                center.getChildren().setAll(newCenter);

            } else {
                System.err.println("Parent is not an instance of AnchorPane");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
