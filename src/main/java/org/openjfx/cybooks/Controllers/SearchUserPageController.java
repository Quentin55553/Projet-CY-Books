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

/**
 * Controller class for the customer search page
 */
public class SearchUserPageController implements Initializable {

    /**
     * Anchor pane for the view of the search
     */
    @FXML
    private AnchorPane SearchUsersAnchorPane;
    /**
     * Button used by the user if they want to filter their search
     */
    @FXML
    private JFXButton FilterSearchButton;
    /**
     * Line to organize the view
     */
    @FXML
    private javafx.scene.control.Separator Separator;
    /**
     * View of the found customers
     */
    @FXML
    private VBox UsersVbox;
    /**
     * Icon that can take the user to the previous page of the results
     */
    @FXML
    private FontAwesomeIconView ChevronLeft;
    /**
     * Icon that can take the user to the next page of the results
     */
    @FXML
    private FontAwesomeIconView ChevronRight;

    /**
     * Value of the current page of the results (0 at first because we are on the first page)
     */
    private int currentPage = 0;
    /**
     * Number of results for one results page
     */
    private int rowsPerPage = 10;
    /**
     * List of results for the search (Customer objects)
     */
    private List<Customer> results;
    /**
     * CustomerFilter object used for the search filtering
     */
    private static CustomerFilter filter;

    /**
     * This method handles the first arrival on the page, with an empty filter search
     * @param url Not used
     * @param resourceBundle Not used
     */
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


    /**
     * Method called on the left arrow click to go to the previous page of the results
     * @throws IOException If an error occurs during the page transition
     */
    @FXML
    private void Previous() throws IOException {
        showPage(currentPage - 1);
    }

    /**
     * Method called on the right arrow click to go to the next page of the results
     * @throws IOException If an error occurs during the page transition
     */
    @FXML
    private void Next() throws IOException {
        showPage(currentPage + 1);
    }


    /**
     * This method is used to get the results of a filtered customer search using a CustomerFilter object
     * It puts the results in the results attribute
     * @param filter The CustomerFilter to use
     * @return Also returns the results found
     */
    private List<Customer> getResultsFromDatabase(CustomerFilter filter) {
        results = DBHandler.getCustomersByFilter(filter);
        System.out.println(results);
        return results;
    }

    /**
     * This method is used to get the number of pages for the view from a number of found results (results attribute)
     * @return The number of pages needed to show the all of the results attribute
     */
    private int getTotalPages() {
        return (results.size() + rowsPerPage - 1) / rowsPerPage;
    }

    /**
     * This method is called to show the page number 'page' of the results of the Customer search
     * @param page The page to show
     * @throws IOException If there is an error during the page transition
     */
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
                button.setOnAction(actionEvent -> handleButtonClick(customer));
            }
            UsersVbox.getChildren().add(node);
        }
        System.out.println("Showing page " + page + " from index " + start + " to " + (end - 1));
        updateButtonStates(totalPages);
    }

    /**
     * This method is used to make the arrows visible or not depending on the current page
     * Left arrow is not visible at first page, right is not at the last page
     * @param totalPages Number of total pages
     */
    private void updateButtonStates(int totalPages) {
        ChevronLeft.setVisible(currentPage > 0);
        ChevronRight.setVisible(currentPage < totalPages - 1);
    }


    /**
     * This method is used to handle a click on a customer
     * It will show the profile page of the selected customer
     * @param customer The clicked customer (Customer)
     */
    @FXML
    private void handleButtonClick(Customer customer) {
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

                ProfilePageController controller = loader.getController();
                controller.setButtonCustomer(customer);
            } else {
                System.err.println("Parent is not an instance of AnchorPane");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is called to show the dialog page used for the search filtering
     * @param event The event that makes the dialog page open
     */
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

    /**
     * This method is called to refresh the search results, getting new results from the database using a CustomerFilter object
     * @param updatedFilter The filter to use
     */
    public void refreshSearchResults(CustomerFilter updatedFilter) {
        System.out.println("Filter applied : " + updatedFilter);
        // Update filter with the new one
        filter = updatedFilter;

        // Refresh the search results
        results = getResultsFromDatabase(filter);
        try {
            showPage(0);
            //updateButtonStates(results.size() / rowsPerPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
