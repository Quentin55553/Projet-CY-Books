package org.openjfx.cybooks.Controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.database.CustomerFilter;
import org.openjfx.cybooks.database.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SearchUserPageController implements Initializable {



    @FXML
    private AnchorPane SearchUsersAnchorPane;
    @FXML
    private JFXButton FilterSearchButton;
    @FXML
    private javafx.scene.control.Separator Separator;
    @FXML
    private VBox UsersVbox;
    @FXML
    private FontAwesomeIconView ChevronLeft;

    @FXML
    private FontAwesomeIconView ChevronRight;

    private int currentPage = 0;
    private int rowsPerPage = 10; // Valeur par défaut
    private List<Customer> results;
    private static CustomerFilter filter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize filter with empty fields, so it shows all the users
        filter = new CustomerFilter("","",null, "", "", "", null, null);
        System.out.println(filter);
        results = getResultsFromDatabase(filter);

        FilterSearchButton.setOnAction(this::openFilterDialog);

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


    private List<Customer> getResultsFromDatabase(CustomerFilter filter) {
        results = DBHandler.getCustomersByFilter(filter);
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
        if ( getTotalPages() <= 1 ){
            Separator.setVisible(false);
        }

        currentPage = page;
        UsersVbox.getChildren().clear();

        int start = page * rowsPerPage;
        int end = Math.min(start + rowsPerPage, results.size());


        for (int i = start; i < end; i++) {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/Item-AllUsers.fxml")));
            if (node instanceof Parent){

                Customer customer = results.get(i);

                Label idLabel = (Label) node.lookup("#IDLabel");
                Label firstNameLabel = (Label) node.lookup("#FirstNameLabel");
                Label lastNameLabel = (Label) node.lookup("#LastNameLabel");
                Label telLabel = (Label) node.lookup("#TelLabel");
                Label emailLabel = (Label) node.lookup("#EmailLabel");
                Label addressLabel = (Label) node.lookup("#AddressLabel");
                Label nbLoansLabel = (Label) node.lookup("#NbLoansLabel");
                Button button = (Button) node.lookup("#ProfileButton");

                idLabel.setText(String.valueOf(customer.getId()));
                firstNameLabel.setText(customer.getFirstName());
                lastNameLabel.setText(customer.getLastName());
                telLabel.setText(customer.getTel());
                emailLabel.setText(customer.getEmail());
                addressLabel.setText(customer.getAddress());
                nbLoansLabel.setText(String.valueOf(customer.getLoanCount()));


                button.setId("ProfileButton" + customer.getId());
                button.setOnAction(actionEvent -> handleButtonClick(button.getId()));
            }
            UsersVbox.getChildren().add(node);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/Profile-page.fxml"));
            Parent parent = SearchUsersAnchorPane.getParent();

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

    @FXML
    private void openFilterDialog(ActionEvent event) {
        try {
            // Load the FXML file for the dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/FilterDialogUser.fxml"));
            Parent root = loader.load();

            // Get the controller instance from the loader
            FilterDialogUserController dialogController = loader.getController();
            // Pass the reference to this SearchUserPageController
            dialogController.setSearchUserPageController(this);
            // Pass the filter to the dialog controller
            dialogController.setFilter(filter);

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            // Prevents the user from resizing the window
            dialogStage.setResizable(false);

            // Set modality
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(((Stage) ((JFXButton) event.getSource()).getScene().getWindow()));

            // Show the dialog and wait until it is closed
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshSearchResults(CustomerFilter updatedFilter) {
        System.out.println("Filter applied : " + updatedFilter);
        // Update filter with the new one
        filter = updatedFilter;

        // Refresh the search results
        results = getResultsFromDatabase(filter);
        try {
            showPage(0);
            updateButtonStates(results.size() / rowsPerPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
