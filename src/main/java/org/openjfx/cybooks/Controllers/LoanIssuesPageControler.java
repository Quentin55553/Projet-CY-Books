package org.openjfx.cybooks.Controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Core;
import org.openjfx.cybooks.data.Loan;
import org.openjfx.cybooks.database.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoanIssuesPageControler implements Initializable {


    @FXML
    AnchorPane LoanIssuesAnchorPane;
    @FXML
    private Separator Separator;
    @FXML
    private VBox LoanIssuesVbox;
    @FXML
    private FontAwesomeIconView ChevronLeft;

    @FXML
    private FontAwesomeIconView ChevronRight;

    private int currentPage = 0;
    private int rowsPerPage = 10;
    private List<Loan> results;

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


    private List<Loan> getResultsFromDatabase() {
        return DBHandler.getExpiredLoans();
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
        LoanIssuesVbox.getChildren().clear();

        int start = page * rowsPerPage;
        int end = Math.min(start + rowsPerPage, results.size());


        for (int i = start; i < end; i++) {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/Item-Loan.fxml")));

            Loan loan = results.get(i);

            Label END = (Label) node.lookup("#END");
            Label START = (Label) node.lookup("#START");
            Label IDBook = (Label) node.lookup("#IDBook");
            Label IDMember = (Label) node.lookup("#IDMember");
            Label Title = (Label) node.lookup("#Title");
            JFXButton ReturnButton = (JFXButton) node.lookup("#ReturnButton");
            JFXButton isReturnedButton = (JFXButton) node.lookup("#isReturnedButton");
            JFXButton isLateButton = (JFXButton) node.lookup("#isLateButton");

            END.setText(loan.getExpirationDate());
            START.setText(loan.getBeginDate());
            IDBook.setText(loan.getBookId());
            IDMember.setText(String.valueOf(loan.getCustomerId()));
            Title.setText( ((Book) Core.getBook(loan.getBookId())).getTitle() );
            // by default return button is the only one visible in the item-loan
            // since here we only display expired returns
            // hide return button
            ReturnButton.setVisible(false);
            // show is late button
            isLateButton.setVisible(true);
            // set buttons's action
            isLateButton.setOnAction(event -> {
                Core.updateLoan(loan.getId(),true);
                // update button
                isLateButton.setVisible(false);
                isReturnedButton.setVisible(true);
                // if the loan is completed it should not be showing so we update the page
                refreshResults();
            });
            LoanIssuesVbox.getChildren().add(node);
        }
        System.out.println("Showing page " + page + " from index " + start + " to " + (end - 1));
        updateButtonStates(totalPages);
    }

    private void refreshResults() {
        results = getResultsFromDatabase();
        try {
            showPage(currentPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateButtonStates(int totalPages) {
        ChevronLeft.setVisible(currentPage > 0);
        ChevronRight.setVisible(currentPage < totalPages - 1);
    }


}