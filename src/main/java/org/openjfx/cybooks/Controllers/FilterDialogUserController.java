package org.openjfx.cybooks.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FilterDialogUserController {

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

    @FXML
    public void closeDialog(javafx.event.ActionEvent actionEvent) {
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
    }
}