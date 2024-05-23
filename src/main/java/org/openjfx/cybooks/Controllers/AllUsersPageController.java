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


public class AllUsersPageController implements Initializable {
    @FXML
    private VBox AllUsersVbox;
    @FXML
    private FontAwesomeIconView ChevronLeft;
    @FXML
    private FontAwesomeIconView ChevronRight;
    private int currentPage = 0;
    private int rowsPerPage = 10;
    private List<Customer> results;

    @FXML
    AnchorPane AllUsersAnchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        results = getResultsFromDatabase();

        try {
            showPage(0);

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


    private List<Customer> getResultsFromDatabase() {
        return DBHandler.getAllCustomers();
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


    private void updateButtonStates(int totalPages) {
        ChevronLeft.setVisible(currentPage > 0);
        ChevronRight.setVisible(currentPage < totalPages - 1);
    }


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
