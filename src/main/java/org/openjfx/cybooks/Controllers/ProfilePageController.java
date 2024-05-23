package org.openjfx.cybooks.Controllers;

import com.jfoenix.controls.JFXButton;
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
import java.text.SimpleDateFormat;
import java.util.Date;
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
        for (Loan loan : loans) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/Item-CustomerHistory.fxml"));
                Node node = loader.load();

                Label titleLabel = (Label) node.lookup("#title");
                Label authorLabel = (Label) node.lookup("#author");
                Label genreLabel = (Label) node.lookup("#genre");
                Label loanDateLabel = (Label) node.lookup("#loanDate");
                JFXButton completedButton = (JFXButton) node.lookup("#loanCompleted");

                APIHandler api = new APIHandler();
                api.generateQueryStandard("", "", "", loan.getBookId(), "", "", "");
                List <SearchResult> results = api.getResults();

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

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                loanDateLabel.setText(dateFormat.format(loan.getBeginDate()));

                completedButton.setId("CompletedLoanButton" + loan.getId());
                updateCompletedButtonText(completedButton, loan.getCompleted());
                completedButton.setOnAction(actionEvent -> toggleLoanButtonClick(loan));

                CustomerHistory.getChildren().add(node);

            } catch (IOException | QueryParameterException e) {
                e.printStackTrace();

            }
        }
    }


    private void toggleLoanButtonClick(Loan loan) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Changement du statut de l'emprunt");
        alert.setHeaderText("Changer le statut de l'emprunt");
        alert.setContentText("Voulez-vous changer le statut de l'empurnt ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            loan.setCompleted(!loan.getCompleted());
            DBHandler.updateLoan(loan.getId(), loan.getCompleted());

            System.out.println(loan.getCompleted());

            for (Node node : CustomerHistory.getChildren()) {
                if (node.lookup("#CompletedLoanButton" + loan.getId()) != null) {
                    JFXButton completedButton = (JFXButton) node.lookup("#CompletedLoanButton" + loan.getId());
                    updateCompletedButtonText(completedButton, loan.getCompleted());
                }
            }
        }
    }


    private void updateCompletedButtonText(JFXButton button, boolean isCompleted) {
        if (isCompleted) {
            button.setText("Rendu");

        } else {
            button.setText("Pas rendu");
        }
    }
}
