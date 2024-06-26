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


/**
 * Controller class for the page that allows the user to see all the expired loans
 */
public class LoanIssuesPageControler implements Initializable {

    /**
     * Anchor pane for the view of the loans
     */
    @FXML
    AnchorPane LoanIssuesAnchorPane;
    /**
     * Line to organize the view
     */
    @FXML
    private Separator Separator;
    /**
     * Container for the loans
     */
    @FXML
    private VBox LoanIssuesVbox;
    /**
     * Icon that can take the user to the previous page of the loans
     */
    @FXML
    private FontAwesomeIconView ChevronLeft;
    /**
     * Icon that can take the user to the next page of the loans
     */
    @FXML
    private FontAwesomeIconView ChevronRight;
    /**
     * Value of the current page of the loans (0 at first because we are on the first page)
     */
    private int currentPage = 0;
    /**
     * Number of loans for one page
     */
    private int rowsPerPage = 10;
    /**
     * List of all the loans that are late
     */
    private List<Loan> results;

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
            updateButtonStates(results.size() / rowsPerPage);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * Method called on the left arrow click to go to the previous page of the loans
     * @throws IOException If an error occurs during the page transition
     */
    @FXML
    private void Previous() throws IOException {
        showPage(currentPage - 1);
    }
    /**
     * Method called on the right arrow click to go to the next page of the loans
     * @throws IOException If an error occurs during the page transition
     */
    @FXML
    private void Next() throws IOException {
        showPage(currentPage + 1);
    }

    /**
     * Method used to get all the loans that are late from the database
     * @return A Loan List
     */
    private List<Loan> getResultsFromDatabase() {
        return DBHandler.getExpiredLoans();
    }

    /**
     * This method is used to get the number of pages for the view from a number of results (results attribute)
     * @return The number of pages needed to show the all of the results attribute
     */
    private int getTotalPages() {
        int total = results.size() / rowsPerPage;
        if (results.size() % rowsPerPage != 0) {
            total++; // Add one more page for the remaining items
        }
        return total;
    }

    /**
     * This method is called to show the page number 'page' of the loans
     * @param page The page to show
     * @throws IOException If there is an error during the page transition
     */
    private void showPage(int page) throws IOException {
        int totalPages = getTotalPages();
        if (page < 0 || page > results.size() / rowsPerPage) {
            return;
        }
        if ( totalPages <= 1 ){
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
            // set button's action
            isLateButton.setOnAction(event -> {
                Core.updateLoan(loan.getId(),true);
                // update button
                isLateButton.setVisible(false);
                isReturnedButton.setVisible(true);
                // if the loan is completed it should not be showing, so we update the page
                refreshResults();
            });
            LoanIssuesVbox.getChildren().add(node);
        }
        System.out.println("Showing page " + page + " from index " + start + " to " + (end - 1));
        updateButtonStates(totalPages);
    }

    /**
     * This method is called to refresh the results (just getting a new set of results from the database)
     */
    private void refreshResults() {
        results = getResultsFromDatabase();
        try {
            showPage(currentPage);
            updateButtonStates(currentPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
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


}
