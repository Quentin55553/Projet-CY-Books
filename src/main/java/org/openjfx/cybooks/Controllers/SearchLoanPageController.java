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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Core;
import org.openjfx.cybooks.data.Loan;
import org.openjfx.cybooks.database.DBHandler;
import org.openjfx.cybooks.database.LoanFilter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ResourceBundle;

public class SearchLoanPageController implements Initializable {
    /**
     * Anchor pane for the view of the search
     */
    @FXML
    private AnchorPane SearchLoansAnchorPane;
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
    private VBox LoansVbox;
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
    private List<Loan> results;
    /**
     * LoanFilter object used for the search filtering
     */
    private static LoanFilter filter;

    /**
     * This method handles the first arrival on the page, with an empty filter search
     * @param url Not used
     * @param resourceBundle Not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize filter with empty fields, so it shows all the loans not expired
        filter = new LoanFilter(null,"",false, false);
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
     * This method is used to get the results of a filtered loan search using a LoanFilter object
     * It puts the results in the results attribute
     * @param filter The LoanFilter to use
     * @return Also returns the results found
     */
    private List<Loan> getResultsFromDatabase(LoanFilter filter) {
        results = DBHandler.getLoansByFilter(filter);
        return results;
    }

    /**
     * This method is used to get the number of pages for the view from a number of found results (results attribute)
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
     * This method is called to show the page number 'page' of the results of the Customer search
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
        LoansVbox.getChildren().clear();

        int start = page * rowsPerPage;
        int end = Math.min(start + rowsPerPage, results.size());


        for (int i = start; i < end; i++) {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/Item-Loan.fxml")));
            if (node instanceof Parent){

                Loan loan = results.get(i);

                Label START = (Label) node.lookup("#START");
                Label END = (Label) node.lookup("#END");
                Label IDMember = (Label) node.lookup("#IDMember");
                Label IDBook = (Label) node.lookup("#IDBook");
                Label Title = (Label) node.lookup("#Title");
                JFXButton ReturnButton = (JFXButton) node.lookup("#ReturnButton");
                JFXButton isReturnedButton = (JFXButton) node.lookup("#isReturnedButton");
                JFXButton isLateButton = (JFXButton) node.lookup("#isLateButton");


                START.setText(loan.getBeginDate());
                END.setText(loan.getExpirationDate());
                IDMember.setText(String.valueOf(loan.getCustomerId()));
                IDBook.setText(loan.getBookId());

                try {
                     Book book = Core.getBook(loan.getBookId());
                    if (book != null && book.getTitle() != null && !book.getTitle().isEmpty()) {
                        System.out.println("Book found: " + book.getTitle());
                        Title.setText(book.getTitle());
                    } else {
                        System.out.println("Book or title is null or empty");
                        Title.setText("N/A");
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Book not found: " + e.getMessage());
                    Title.setText("N/A");
                } catch (Exception e) {
                    Title.setText("N/A");
                }

                // by default return button is the only one visible in the item-loan
                if(loan.isCompleted()){
                    // hide return button
                    ReturnButton.setVisible(false);
                    // show is returned button
                    isReturnedButton.setVisible(true);
                    // prevents user from interacting with it
                    isReturnedButton.setDisable(true);
                    // normal opacity
                    isReturnedButton.setOpacity(1.0);
                }
                else if(loan.hasExpired()){
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
                        // prevents user from interacting with it
                        isReturnedButton.setDisable(true);
                        // normal opacity
                        isReturnedButton.setOpacity(1.0);
                    });
                }
                else{
                    // if neither are showing then RetrunButton is showing
                    // set button's action
                    ReturnButton.setOnAction(event -> {
                        Core.updateLoan(loan.getId(),true);
                        // update button
                        ReturnButton.setVisible(false);
                        isReturnedButton.setVisible(true);
                        // prevents user from interacting with it
                        isReturnedButton.setDisable(true);
                        // normal opacity
                        isReturnedButton.setOpacity(1.0);
                    });
                }

            }
            LoansVbox.getChildren().add(node);
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
     * This method is called to show the dialog page used for the search filtering
     * @param event The event that makes the dialog page open
     */
    @FXML
    private void openFilterDialog(ActionEvent event) {
        try {
            // Load the FXML file for the dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/FilterDialogLoan.fxml"));
            Parent root = loader.load();

            // Get the controller instance from the loader
            FilterDialogLoanController dialogController = loader.getController();
            // Pass the reference to this SearchLoanPageController
            dialogController.setSearchLoanPageController(this);
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
     * This method is called to refresh the search results, getting new results from the database using a LoanFilter object
     * @param updatedFilter The filter to use
     */
    public void refreshSearchResults(LoanFilter updatedFilter) {
        System.out.println("Filter applied : " + updatedFilter);
        // Update filter with the new one
        filter = updatedFilter;

        // Refresh the search results
        results = getResultsFromDatabase(filter);
        try {
            showPage(0);
            updateButtonStates(getTotalPages());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
