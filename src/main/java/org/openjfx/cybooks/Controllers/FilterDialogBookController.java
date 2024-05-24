package org.openjfx.cybooks.Controllers;


import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import org.controlsfx.control.ToggleSwitch;
import org.openjfx.cybooks.database.BookFilter;


import static org.openjfx.cybooks.Controllers.FilterDialogUserController.isInteger;

public class FilterDialogBookController {

    @FXML
    private TextField AutorField;

    @FXML
    private TextField EditorField;

    @FXML
    private TextField IdField;

    @FXML
    private JFXToggleButton inLibraryToggle;

    @FXML
    private TextField SubjectField;

    @FXML
    private TextField TitleField;

    @FXML
    private TextField YearField;

    private BookFilter filter;

    private SearchBookPageController searchBookPageController;

    // Setter method for searchBookPageController
    public void setSearchBookPageController(SearchBookPageController controller) {
        this.searchBookPageController = controller;
    }



    public void setFilter(BookFilter filter) {
        this.filter = filter;
        // Initialize fields with current filter state
        TitleField.setText(filter.getTitle());
        AutorField.setText(filter.getAuthor());
        EditorField.setText(filter.getEditor());
        inLibraryToggle.setSelected(filter.isInLibrary());
        SubjectField.setText(filter.getTheme());
        YearField.setText(filter.getDate());
        // Assuming Integer fields
        IdField.setText(filter.getId() != null ? String.valueOf(filter.getId()) : "");
    }


    @FXML
    public void saveNewFilter(){
        // Update filter with inputs fields
        filter.setTitle(TitleField.getText().trim());
        filter.setAuthor(AutorField.getText().trim());
        filter.setTheme(SubjectField.getText().trim());
        filter.setDate(YearField.getText().trim());
        filter.setEditor(EditorField.getText().trim());
        filter.setInLibrary(inLibraryToggle.isSelected());
        filter.setId(IdField.getText().trim());

        // Notify SearchUserPageController about the updated filter
        searchBookPageController.refreshSearchResults(filter);

        // Close the dialog
        Stage stage = (Stage) IdField.getScene().getWindow();
        stage.close();
    }

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
        inLibraryToggle.setSelected(false);
        // Reset filter
        filter = new BookFilter("", "", "", "", null, "", false);
    }

}