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

public class BestBooksPageController implements Initializable {

    @FXML
    private AnchorPane bestBooksPage;
    @FXML
    private JFXTextArea author1;

    @FXML
    private JFXTextArea author2;

    @FXML
    private JFXTextArea author3;

    @FXML
    private JFXTextArea id1;

    @FXML
    private JFXTextArea id2;

    @FXML
    private JFXTextArea id3;

    @FXML
    private JFXTextArea title1;

    @FXML
    private JFXTextArea title2;

    @FXML
    private JFXTextArea title3;

    @FXML
    private Text nb1;

    @FXML
    private Text nb2;

    @FXML
    private Text nb3;

    @FXML
    private Text date;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Afficher la date du jour
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date.setText(today.format(formatter));
        // Mettre à jour l'affichage avec la date du jour
        updateBooksDisplay(today);
    }


    @FXML
    private void handleDateClick(MouseEvent event){
        // Créer le DatePicker
        DatePicker datePicker = new DatePicker();
        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Sélectionnez une date");
        dialog.setHeaderText("Veuillez sélectionner une date :");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(datePicker);
        dialogPane.getButtonTypes().addAll(javafx.scene.control.ButtonType.OK, javafx.scene.control.ButtonType.CANCEL);
        dialog.setResultConverter(new Callback<javafx.scene.control.ButtonType, LocalDate>() {
            @Override
            public LocalDate call(javafx.scene.control.ButtonType buttonType) {
                if (buttonType == javafx.scene.control.ButtonType.OK) {
                    return datePicker.getValue();
                }
                return null;
            }
        });

            Optional<LocalDate> result = dialog.showAndWait();
            result.ifPresent(selectedDate -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date.setText(selectedDate.format(formatter));

                // Mettre à jour l'affichage avec la nouvelle date sélectionnée
                updateBooksDisplay(selectedDate);
            });
    }


    private void updateBooksDisplay(LocalDate selectedDate) {
        List<Book> books = Core.getMostPopularBooksSince(selectedDate.toString());
        if (books.size() > 0) {
            String textid1 = books.get(0).getId();
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
            String textid2 = books.get(1).getId();
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
            String textid3 = books.get(2).getId();
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
        List<Integer> ListNbLoanTop3 = Core.getTop3LoansCountsSince(id1.getText(), id2.getText(), id3.getText(), selectedDate.toString());
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