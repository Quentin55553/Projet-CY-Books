package org.openjfx.cybooks.Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.database.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for the page that allows the user to see a list of all the library's customers
 */
public class AllUsersPageController implements Initializable {
    /**
     * Container for the customers
     */
    @FXML
    private VBox AllUsersVbox;
    /**
     * Icon that can take the user to the previous page of the customers
     */
    @FXML
    private FontAwesomeIconView ChevronLeft;
    /**
     * Icon that can take the user to the next page of the customers
     */
    @FXML
    private FontAwesomeIconView ChevronRight;
    /**
     * Value of the current page of the customers (0 at first because we are on the first page)
     */
    private int currentPage = 0;
    /**
     * Number of customers for one page
     */
    private int rowsPerPage = 10;
    /**
     * List of all the customers
     */
    private List<Customer> results;
    /**
     * Anchor pane for the view of the customers
     */
    @FXML
    AnchorPane AllUsersAnchorPane;


    /**
     * Method called on arrival on the page
     * The results are fetched from the database and loaded on the view
     * @param url Not used
     * @param resourceBundle Not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        results = getResultsFromDatabase();

        try {
            showPage(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method called on the left arrow click to go to the previous page of the customers
     * @throws IOException If an error occurs during the page transition
     */
    @FXML
    private void Previous() throws IOException {
        showPage(currentPage - 1);
    }

    /**
     * Method called on the right arrow click to go to the next page of the customers
     * @throws IOException If an error occurs during the page transition
     */
    @FXML
    private void Next() throws IOException {
        showPage(currentPage + 1);
    }

    /**
     * Method used to get all the customers from the database
     * @return A Customer List
     */
    private List<Customer> getResultsFromDatabase() {
        return DBHandler.getAllCustomers();
    }

    /**
     * This method is used to get the number of pages for the view from a number of results (results attribute)
     * @return The number of pages needed to show the all of the results attribute
     */
    private int getTotalPages() {
        return (results.size() + rowsPerPage - 1) / rowsPerPage;
    }


    /**
     * This method is called to show the page number 'page' of the customers
     * @param page The page to show
     * @throws IOException If there is an error during the page transition
     */
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
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/Item-AllUsers.fxml")));

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

            AllUsersVbox.getChildren().add(node);
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

                ProfilePageController controller = loader.getController();
                controller.setButtonCustomer(customer);

            } else {
                System.err.println("Parent is not an instance of AnchorPane");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
