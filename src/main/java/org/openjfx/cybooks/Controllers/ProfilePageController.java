package org.openjfx.cybooks.Controllers;

import com.jfoenix.controls.JFXButton;

import org.openjfx.cybooks.API.APIErrorException;
import org.openjfx.cybooks.API.APIHandler;
import org.openjfx.cybooks.API.QueryParameterException;
import org.openjfx.cybooks.API.SearchResult;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.data.Loan;
import org.openjfx.cybooks.database.DBHandler;

import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.io.IOException;
import java.util.List;


/**
 * Controller that allows the user to see the profile of a customer, with his loan history
 */
public class ProfilePageController {
    /**
     * View of the customer's loans
     */
    @FXML
    private VBox CustomerHistory;
    /**
     * Firstname of the customer
     */
    @FXML
    private Text firstname;
    /**
     * Lastname of the customer
     */
    @FXML
    private Text lastname;
    /**
     * The root of the page
     */
    @FXML
    public AnchorPane Root;
    /**
     * Customer object representing the customer
     */
    private Customer customer;
    /**
     * Loan list of the customer
     */
    private List<Loan> loans;


    /**
     * This method loads the page for a given customer
     * It will show their loan history and their personal information
     * @param customer The customer to show
     */
    public void setButtonCustomer(Customer customer) {
        this.customer = customer;
        this.loans = DBHandler.getLoansByCustomer(this.customer.getId());

        loadCustomerInformation();
        loadCustomerHistory();
    }


    /**
     * This method puts the customer's information on the view
     */
    private void loadCustomerInformation() {
        firstname.setText(customer.getFirstName());
        lastname.setText(customer.getLastName());
    }

    /**
     * This method is called to show the loan history of the current customer
     * It uses an APIHandler object to search for each book's information and show it if there is no errors
     * A completed / late button is also handled in this method. The user can mark each loan as completed or late
     */
    private void loadCustomerHistory() {
        APIHandler api = new APIHandler();
        List <SearchResult> results;

        for (Loan loan : loans) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/Item-CustomerHistory.fxml"));
                Node node = loader.load();

                Label titleLabel = (Label) node.lookup("#title");
                Label authorLabel = (Label) node.lookup("#author");
                Label genreLabel = (Label) node.lookup("#genre");
                Label loanDateLabel = (Label) node.lookup("#loanDate");
                JFXButton completedButton = (JFXButton) node.lookup("#completedLoan");
                JFXButton notCompletedButton = (JFXButton) node.lookup("#notCompletedLoan");
                JFXButton lateButton = (JFXButton) node.lookup("#lateLoan");

                api.generateQueryStandard("", "", "", loan.getBookId(), "", "", "");
                api.exec();
                results = api.getResults();

                if (api.getNumberOfResults() >= 1) {
                    titleLabel.setText(results.get(0).getTitle());
                    authorLabel.setText(results.get(0).getAuthors().get(0));
                    genreLabel.setText(results.get(0).getSubjects().get(0));

                // No results returned
                } else {
                    titleLabel.setText("N/A");
                    authorLabel.setText("N/A");
                    genreLabel.setText("N/A");
                }

                loanDateLabel.setText(loan.getBeginDate());

                completedButton.setId("completedLoanButton" + loan.getId());
                notCompletedButton.setId("notCompletedLoanButton" + loan.getId());
                lateButton.setId("lateLoanButton" + loan.getId());

                setButtonVisibility(completedButton, notCompletedButton, lateButton, loan.isCompleted(), loan.hasExpired());

                completedButton.setOnAction(actionEvent -> toggleLoanStatus(loan, completedButton, notCompletedButton, lateButton));
                notCompletedButton.setOnAction(actionEvent -> toggleLoanStatus(loan, completedButton, notCompletedButton, lateButton));
                lateButton.setOnAction(actionEvent -> toggleLoanStatus(loan, completedButton, notCompletedButton, lateButton));

                CustomerHistory.getChildren().add(node);

            } catch (IOException | QueryParameterException | APIErrorException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * This method changes the status of a loan and handles the visibility of the associated buttons
     * @param loan The loan to change
     * @param completedButton The button showing if the loan is completed
     * @param notCompletedButton The button showing if the loan is not completed
     * @param lateButton The button showing if the loan is late
     */
    private void toggleLoanStatus(Loan loan, JFXButton completedButton, JFXButton notCompletedButton, JFXButton lateButton) {
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setTitle("Changement du statut de l'emprunt");

        if (!loan.isCompleted()) {
            alert.setHeaderText("Inscrire comme rendu");

        } else {
            if (loan.hasExpired()) {
                if (loan.isCompleted()) {
                    alert.setHeaderText("Inscrire comme en retard");
                }

            } else {
                if (loan.isCompleted()) {
                    alert.setHeaderText("Inscrire comme non rendu");
                }
            }
        }

        alert.setContentText("Voulez-vous changer le statut de l'emprunt ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            loan.setCompleted(!loan.isCompleted());
            DBHandler.updateLoan(loan.getId(), loan.isCompleted());
            setButtonVisibility(completedButton, notCompletedButton, lateButton, loan.isCompleted(), loan.hasExpired());
        }
    }

    /**
     * This method hides or shows buttons to represent the status of a loan
     * @param completedButton The button showing if the loan is completed
     * @param notCompletedButton The button showing if the loan is not completed
     * @param lateButton The button showing if the loan is late
     * @param isCompleted The status of the loan
     * @param hasExpired The status of the loan
     */
    private void setButtonVisibility(JFXButton completedButton, JFXButton notCompletedButton, JFXButton lateButton, boolean isCompleted, boolean hasExpired) {
        if (hasExpired) {
            completedButton.setVisible(isCompleted);
            notCompletedButton.setVisible(false);
            lateButton.setVisible(!isCompleted);

        } else {
            completedButton.setVisible(isCompleted);
            notCompletedButton.setVisible(!isCompleted);
            lateButton.setVisible(false);
        }
    }


    /**
     * This method handles the edit profile button click
     * It changes the page to the edit profile page
     */
    @FXML
    protected void editProfileClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/editCustomer-page.fxml"));
            AnchorPane newCenter = loader.load();

            // Set the size constraints of the new AnchorPane
            newCenter.setPrefSize(newCenter.getPrefWidth(), newCenter.getPrefHeight());

            AnchorPane.setTopAnchor(newCenter, 0.0);
            AnchorPane.setLeftAnchor(newCenter, 0.0);
            AnchorPane.setBottomAnchor(newCenter, 0.0);
            AnchorPane.setRightAnchor(newCenter, 0.0);

            // Replace the current center pane with the new one
            Root.getChildren().setAll(newCenter);

            editCustomerPageController controller = loader.getController();
            controller.setButtonCustomer(customer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
