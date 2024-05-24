package org.openjfx.cybooks.Controllers;

import com.jfoenix.controls.JFXButton;
import org.openjfx.cybooks.API.APIErrorException;
import org.openjfx.cybooks.API.APIHandler;
import org.openjfx.cybooks.API.QueryParameterException;
import org.openjfx.cybooks.API.SearchResult;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.data.Loan;
import org.openjfx.cybooks.database.DBHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ProfilePageController implements Initializable {
    @FXML
    private VBox CustomerHistory;
    @FXML
    private Text firstname;
    @FXML
    private Text lastname;
    private Customer customer;
    private List<Loan> loans;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // setButtonCustomer() has to be called first before displaying the page. So we load the page in another dedicated method.
    }


    public void setButtonCustomer(Customer customer) {
        this.customer = customer;
        this.loans = DBHandler.getLoansByCustomer(this.customer.getId());

        loadCustomerInformation();
        loadCustomerHistory();
    }


    private void loadCustomerInformation() {
        firstname.setText(customer.getFirstName());
        lastname.setText(customer.getLastName());
    }


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
}
