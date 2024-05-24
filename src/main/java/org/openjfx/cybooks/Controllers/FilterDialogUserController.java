package org.openjfx.cybooks.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openjfx.cybooks.database.CustomerFilter;
import org.openjfx.cybooks.Controllers.SearchUserPageController;

/**
 * Controller class for the page that allows the user to filter their search of a member of the library
 */
public class FilterDialogUserController{

    /**
     * Adress field for the search filtering
     */
    @FXML
    private TextField AddressField;

    /**
     * Email field for the search filtering
     */
    @FXML
    private TextField EmailField;
    /**
     * Firstname field for the search filtering
     */
    @FXML
    private TextField FirstNameField;
    /**
     * ID field for the search filtering
     */
    @FXML
    private TextField IDField;

    /**
     * Lastname field for the search filtering
     */
    @FXML
    private TextField LastNameFIeld;

    /**
     *  field for the search filtering
     */
    @FXML
    private TextField LoanInfField;

    /**
     *  field for the search filtering
     */
    @FXML
    private TextField LoanSuppField;

    /**
     * Phone number field for the search filtering
     */
    @FXML
    private TextField PhoneField;

    /**
     * CustomerFilter object used for the search filtering
     */
    private CustomerFilter filter;

    /**
     * A SearchUserPageController associated with the dialog box
     */
    private SearchUserPageController searchUserPageController;

    /**
     * Setter for the searchUserPageController attribute
     * @param controller The new controller object (SearchUserPageController)
     */
    public void setSearchUserPageController(SearchUserPageController controller) {
        this.searchUserPageController = controller;
    }

    /**
     * Setter for the user filter attribute
     * @param filter The new CustomerFilter attribute
     */
    public void setFilter(CustomerFilter filter) {
        this.filter = filter;
        // Initialize fields with current filter state
        FirstNameField.setText(filter.getFirstName());
        LastNameFIeld.setText(filter.getLastName());
        EmailField.setText(filter.getEmail());
        PhoneField.setText(filter.getTel());
        AddressField.setText(filter.getAddress());
        // Assuming Integer fields
        IDField.setText(filter.getId() != null ? String.valueOf(filter.getId()) : "");
        LoanInfField.setText(filter.getInf() != null ? String.valueOf(filter.getInf()) : "");
        LoanSuppField.setText(filter.getSup() != null ? String.valueOf(filter.getSup()) : "");
    }

    /**
     * This method checks if a String can be parsed as an integer
     * @param str The string to check
     * @return true if the string contains only an integer
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Method used to make the user search controller use the user filter attribute of an object
     */
    @FXML
    public void saveNewFilter(){
        // Update filter with inputs fields
        filter.setFirstName(FirstNameField.getText().trim());
        filter.setLastName(LastNameFIeld.getText().trim());
        filter.setEmail(EmailField.getText().trim());
        filter.setTel(PhoneField.getText().trim());
        filter.setAddress(AddressField.getText().trim());
        // Parse Integer fields
        if (!IDField.getText().trim().isEmpty() && isInteger(IDField.getText().trim())) {
            filter.setId(Integer.parseInt(IDField.getText().trim()));
        } else {
            filter.setId(null); // Handle empty field scenario
        }
        if (!LoanInfField.getText().trim().isEmpty() && isInteger(LoanInfField.getText().trim())) {
            filter.setInf(Integer.parseInt(LoanInfField.getText().trim()));
        } else {
            filter.setInf(null); // Handle empty field scenario
        }
        if (!LoanSuppField.getText().trim().isEmpty() && isInteger(LoanSuppField.getText().trim())) {
            filter.setSup(Integer.parseInt(LoanSuppField.getText().trim()));
        } else {
            filter.setSup(null); // Handle empty field scenario
        }

        // Notify SearchUserPageController about the updated filter
        searchUserPageController.refreshSearchResults(filter);

        // Close the dialog
        Stage stage = (Stage) IDField.getScene().getWindow();
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
        AddressField.clear();
        EmailField.clear();
        FirstNameField.clear();
        IDField.clear();
        LastNameFIeld.clear();
        LoanInfField.clear();
        LoanSuppField.clear();
        PhoneField.clear();
        // Reset filter
        filter = new CustomerFilter("", "", null, "", "", "", null, null);
    }

}