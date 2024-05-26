package org.openjfx.cybooks.Controllers;

import com.jfoenix.controls.JFXToggleButton;

import org.openjfx.cybooks.UserInput.FieldChecks;
import org.openjfx.cybooks.UserInput.IncorrectFieldException;
import org.openjfx.cybooks.database.BookFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;


/**
 * Controller class for the page that allows the user to filter their search of a book
 */
public class FilterDialogBookController {
    /**
     * Author field for the search filtering
     */
    @FXML
    private TextField AutorField;
    /**
     * Publisher field for the search filtering
     */
    @FXML
    private TextField EditorField;
    /**
     * Identifier field for the search filtering
     */
    @FXML
    private TextField IdField;
    /**
     * Button used to search only for books present in the SQL tables
     */
    @FXML
    private JFXToggleButton inLibraryToggle;
    /**
     * Subject field for the search filtering
     */
    @FXML
    private TextField SubjectField;
    /**
     * Title field for the search filtering
     */
    @FXML
    private TextField TitleField;
    /**
     * Year field for the search filtering
     */
    @FXML
    private TextField YearField;
    /**
     * BookFilter object used for the search filtering
     */
    private BookFilter filter;
    /**
     * Label object used to display error messages
     */
    @FXML
    private Label errorLabel;
    /**
     * A SearchBookPageController associated with the dialog box
     */
    private SearchBookPageController searchBookPageController;


    /**
     * Setter for the searchBookPageController attribute
     * @param controller The new controller object (SearchBookPageController)
     */
    public void setSearchBookPageController(SearchBookPageController controller) {
        this.searchBookPageController = controller;
    }


    /**
     * Setter for the book filter attribute
     * @param filter The new BookFilter attribute
     */
    public void setFilter(BookFilter filter) {
        this.filter = filter;
        // Initialize fields with current filter state
        TitleField.setText(filter.getTitle());
        AutorField.setText(filter.getAuthor());
        EditorField.setText(filter.getEditor());
        inLibraryToggle.setSelected(filter.isDatabaseOnly());
        SubjectField.setText(filter.getTheme());
        YearField.setText(filter.getDate());
        // Assuming Integer fields
        IdField.setText(filter.getId() != null ? String.valueOf(filter.getId()) : "");
    }


    /**
     * Method used to make the book search controller use the book filter attribute of an object
     */
    @FXML
    public void saveNewFilter() {
        try {
            if (FieldChecks.isValidDialogBookFilter(YearField.getText(), IdField.getText())) {
                // Update filter with inputs fields
                filter.setTitle(TitleField.getText().trim());
                filter.setAuthor(AutorField.getText().trim());
                filter.setTheme(SubjectField.getText().trim());
                filter.setDate(YearField.getText().trim());
                filter.setEditor(EditorField.getText().trim());
                filter.setDatabaseOnly(inLibraryToggle.isSelected());
                filter.setId(IdField.getText().trim());

                // Notify SearchBookPageController about the updated filter
                searchBookPageController.refreshSearchResults(filter);

                // Close the dialog
                Stage stage = (Stage) IdField.getScene().getWindow();
                stage.close();
            }

        } catch (IncorrectFieldException e) {
            errorLabel.setText(e.getMessage());
        }
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
