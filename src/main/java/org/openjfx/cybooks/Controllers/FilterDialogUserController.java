package org.openjfx.cybooks.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openjfx.cybooks.database.CustomerFilter;
import org.openjfx.cybooks.Controllers.SearchUserPageController;

public class FilterDialogUserController{

    @FXML
    private TextField AddressField;

    @FXML
    private TextField EmailField;

    @FXML
    private TextField FirstNameField;

    @FXML
    private TextField IDField;

    @FXML
    private TextField LastNameFIeld;

    @FXML
    private TextField LoanInfField;

    @FXML
    private TextField LoanSuppField;

    @FXML
    private TextField PhoneField;

    private CustomerFilter filter;

    private SearchUserPageController searchUserPageController;

    // Setter method for searchUserPageController
    public void setSearchUserPageController(SearchUserPageController controller) {
        this.searchUserPageController = controller;
    }


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


    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

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


    @FXML
    public void closeDialog(ActionEvent actionEvent) {
        // Close the dialog
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        stage.close();
    }

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