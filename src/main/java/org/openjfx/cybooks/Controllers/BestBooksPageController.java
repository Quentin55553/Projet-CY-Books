package org.openjfx.cybooks.Controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Core;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The BestBooksPageController class handles the UI logic for the Best Books Page in the CY-Books application.
 * It implements the Initializable interface to initialize the controller after its root element has been completely processed.
 */
public class BestBooksPageController implements Initializable {

    /**
     * Text area to display the author of the first book.
     */
    @FXML
    private JFXTextArea author1;
    /**
     * Text area to display the author of the second book.
     */
    @FXML
    private JFXTextArea author2;
    /**
     * Text area to display the author of the third book.
     */
    @FXML
    private JFXTextArea author3;
    /**
     * Text area to display the ID of the first book.
     */
    @FXML
    private JFXTextArea id1;
    /**
     * Text area to display the ID of the second book.
     */
    @FXML
    private JFXTextArea id2;
    /**
     * Text area to display the ID of the third book.
     */
    @FXML
    private JFXTextArea id3;
    /**
     * Text area to display the title of the first book.
     */
    @FXML
    private JFXTextArea title1;
    /**
     * Text area to display the title of the second book.
     */
    @FXML
    private JFXTextArea title2;
    /**
     * Text area to display the title of the third book.
     */
    @FXML
    private JFXTextArea title3;
    /**
     * Text to display the number of loans for the first book.
     */
    @FXML
    private Text nb1;
    /**
     * Text to display the number of loans for the second book.
     */
    @FXML
    private Text nb2;
    /**
     * Text to display the number of loans for the second book.
     */
    @FXML
    private Text nb3;
    /**
     * Text to display the selected date.
     */
    @FXML
    private Text date;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date.setText(today.format(formatter));
        updateBooksDisplay(today);
    }

    /**
     * Handles the date click event to show a date picker dialog for the user to select a date.
     * Updates the display with the selected date.
     *
     * @param event The mouse event triggered by clicking on the date text.
     */
    @FXML
    private void handleDateClick(MouseEvent event){
        DatePicker datePicker = new DatePicker();
        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Sélectionnez une date");
        dialog.setHeaderText("Veuillez sélectionner une date :");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(datePicker);
        dialogPane.getButtonTypes().addAll(javafx.scene.control.ButtonType.OK, javafx.scene.control.ButtonType.CANCEL);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == javafx.scene.control.ButtonType.OK) {
                return datePicker.getValue();
            }
            return null;
        });

            Optional<LocalDate> result = dialog.showAndWait();
            result.ifPresent(selectedDate -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date.setText(selectedDate.format(formatter));

                updateBooksDisplay(selectedDate);
            });
    }

    /**
     * Updates the books display with the most popular books since the given date.
     *
     * @param selectedDate The date from which to retrieve the most popular books.
     */
    private void updateBooksDisplay(LocalDate selectedDate) {
        String sqlDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Book> books = Core.getMostPopularBooksSince(sqlDate);
        String textid1 = "";
        String textid2 = "";
        String textid3 = "";
        if (books.size() > 0) {
            textid1 = books.get(0).getId();
            id1.setText(textid1);
            String texttitle1 = Core.getBook(textid1).getTitle();
            title1.setText(texttitle1.isEmpty() ? "N/A" : texttitle1);
            String textauthor1 = String.valueOf(Core.getBook(textid1).getAuthors());
            author1.setText(textauthor1 == null || textauthor1.isEmpty() ? "N/A" : textauthor1);
        } else {
            id1.setText("N/A");
            title1.setText("N/A");
            author1.setText("N/A");
        }
        if (books.size() > 1) {
            textid2 = books.get(1).getId();
            id2.setText(textid2);
            String texttitle2 = Core.getBook(textid2).getTitle();
            title2.setText(texttitle2.isEmpty() ? "N/A" : texttitle2);
            String textauthor2 = String.valueOf(Core.getBook(textid2).getAuthors());
            author2.setText(textauthor2 == null || textauthor2.isEmpty() ? "N/A" : textauthor2);
        } else {
            id2.setText("N/A");
            title2.setText("N/A");
            author2.setText("N/A");
        }
        if (books.size() > 2) {
            textid3 = books.get(2).getId();
            id3.setText(textid3);
            String texttitle3 = Core.getBook(textid3).getTitle();
            title3.setText(texttitle3.isEmpty() ? "N/A" : texttitle3);
            String textauthor3 = String.valueOf(Core.getBook(textid3).getAuthors());
            author3.setText(textauthor3 == null || textauthor3.isEmpty() ? "N/A" : textauthor3);
        } else {
            id3.setText("N/A");
            title3.setText("N/A");
            author3.setText("N/A");
        }
        List<Integer> ListNbLoanTop3 = Core.getTop3LoansCountsSince(id1.getText(), id2.getText(), id3.getText(), sqlDate);
        if (ListNbLoanTop3.size() > 0) {
            nb1.setText(String.valueOf(ListNbLoanTop3.get(0)));
            nb2.setText(ListNbLoanTop3.size() > 1 ? String.valueOf(ListNbLoanTop3.get(1)) : "N/A");
            nb3.setText(ListNbLoanTop3.size() > 2 ? String.valueOf(ListNbLoanTop3.get(2)) : "N/A");
        } else {
            nb1.setText("N/A");
            nb2.setText("N/A");
            nb3.setText("N/A");
        }
    }
}