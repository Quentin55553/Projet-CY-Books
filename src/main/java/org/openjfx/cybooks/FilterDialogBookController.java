package org.openjfx.cybooks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.controlsfx.control.ToggleSwitch;

public class FilterDialogBookController {

    @FXML
    private TextField AutorField;

    @FXML
    private TextField EditorField;

    @FXML
    private TextField IdField;

    @FXML
    private ToggleSwitch InLibraryToggle;

    @FXML
    private TextField SubjectField;

    @FXML
    private TextField TitleField;

    @FXML
    private TextField YearField;

    @FXML
    public void closeDialog(ActionEvent actionEvent) {
        // Close the dialog
        Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void reset(ActionEvent actionEvent) {
        AutorField.clear();
        IdField.clear();
        EditorField.clear();
        SubjectField.clear();
        TitleField.clear();
        YearField.clear();
        InLibraryToggle.setSelected(false);
    }
}