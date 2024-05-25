package org.openjfx.cybooks.Controllers;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openjfx.cybooks.database.CustomerFilter;
import org.openjfx.cybooks.database.LoanFilter;

import static org.openjfx.cybooks.Controllers.FilterDialogUserController.isInteger;

public class FilterDialogLoanController {

    /**
     * BookID field for the search filtering
     */
    @FXML
    private TextField BookID;

    /**
     * Customer ID field for the search filtering
     */
    @FXML
    private TextField CustomerID;
    /**
     * Completed toggle for the search filtering
     */
    @FXML
    private JFXToggleButton completedToggle;
    /**
     * Expired toggle for the search filtering
     */
    @FXML
    private JFXToggleButton expiredToggle;

    /**
     * Loanfilter object used for the search filtering
     */
    private LoanFilter filter;

    /**
     * A SearchLoanPageController associated with the dialog box
     */
    private SearchLoanPageController searchLoanPageController;

    /**
     * Setter for the searchLoanPageController attribute
     * @param controller The new controller object (SearchLoanPageController)
     */
    public void setSearchLoanPageController(SearchLoanPageController controller) {
        this.searchLoanPageController = controller;
    }

    /**
     * Setter for the loan filter attribute
     * @param filter The new Loanfilter attribute
     */
    public void setFilter(LoanFilter filter) {
        this.filter = filter;
        // Initialize fields with current filter state
        BookID.setText(filter.getBookID());
        completedToggle.setSelected(filter.isCompleted());
        expiredToggle.setSelected(filter.isExpired());
        // Assuming Integer fields
        CustomerID.setText(filter.getCustomerID() != null ? String.valueOf(filter.getCustomerID()) : "");
    }


    /**
     * Method used to make the user search controller use the user filter attribute of an object
     */
    @FXML
    public void saveNewFilter(){
        // Update filter with inputs fields
        filter.setBookID(BookID.getText().trim());
        filter.setCompleted(completedToggle.isSelected());
        filter.setExpired(expiredToggle.isSelected());
        // Parse Integer fields
        if (!CustomerID.getText().trim().isEmpty() && isInteger(CustomerID.getText().trim())) {
            filter.setCustomerID(Integer.parseInt(CustomerID.getText().trim()));
        } else {
            filter.setCustomerID(null); // Handle empty field scenario
        }

        // Notify SearchUserPageController about the updated filter
        searchLoanPageController.refreshSearchResults(filter);

        // Close the dialog
        Stage stage = (Stage) CustomerID.getScene().getWindow();
        stage.close();
    }

    /**
     * This method is used to close the dialog window
     * @param actionEvent The event that occurs and makes the window close
     */
    @FXML
    public void closeDialog(ActionEvent actionEvent) {
        // Close the dialog
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        stage.close();
    }

    /**
     * This method is used to reset the fields of the filtering
     * @param actionEvent The event that occurs and makes the fields reset
     */
    public void reset(ActionEvent actionEvent) {
        BookID.clear();
        CustomerID.clear();
        completedToggle.setSelected(false);
        expiredToggle.setSelected(false);
        // Reset filter
        filter = new LoanFilter(null, "", false, false);
    }
}
