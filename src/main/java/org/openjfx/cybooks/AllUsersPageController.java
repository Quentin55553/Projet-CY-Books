package org.openjfx.cybooks;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AllUsersPageController implements Initializable {

    @FXML
    private VBox AllUsersVbox;
    @FXML
    private FontAwesomeIconView ChevronLeft;

    @FXML
    private FontAwesomeIconView ChevronRight;

    /*@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 10 le nombre de line de la requete     à changer
        Node[] nodes = new Node[10];
        for (int i = 0; i < nodes.length; i++) {
            try {
                nodes[i] = (Node) FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Item-AllUsers.fxml")));
                AllUsersVbox.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    /*@FXML
    private TextField rowsPerPageField;*/

    private int currentPage = 0;
    private int rowsPerPage = 10; // Valeur par défaut
    private List<String> results;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        results = getResultsFromDatabase();
        try {
            showPage(0);
            updateButtonStates(results.size() / rowsPerPage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*// Initialiser le champ de saisie avec la valeur par défaut
        rowsPerPageField.setText(String.valueOf(rowsPerPage));*/
    }

    @FXML
    private void Previous() throws IOException {
        showPage(currentPage - 1);
    }

    @FXML
    private void Next() throws IOException {
        showPage(currentPage + 1);
    }

    /*@FXML
    private void handleRowsPerPageChange() {
        try {
            int newRowsPerPage = Integer.parseInt(rowsPerPageField.getText());
            if (newRowsPerPage > 0) {
                rowsPerPage = newRowsPerPage;
                showPage(0); // Revenir à la première page après le changement
            }
        } catch (NumberFormatException e) {
            // Gérer l'exception si l'utilisateur n'entre pas un nombre valide
            rowsPerPageField.setText(String.valueOf(rowsPerPage));
        }
    }*/

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
            AllUsersVbox.getChildren().add(node);
        }
        System.out.println("Showing page " + page + " from index " + start + " to " + (end - 1));
        updateButtonStates(totalPages);
    }

    private void updateButtonStates(int totalPages) {
        ChevronLeft.setVisible(currentPage > 0);
        ChevronRight.setVisible(currentPage < totalPages - 1);
    }

}



