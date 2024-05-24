package org.openjfx.cybooks.Controllers;

import com.google.protobuf.StringValue;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.database.BookFilter;
import org.openjfx.cybooks.database.CustomerFilter;
import org.openjfx.cybooks.database.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SearchBookPageController implements Initializable {



    @FXML
    private AnchorPane SearchBookAnchorPane;
    @FXML
    private JFXButton FilterSearchButton;
    @FXML
    private Separator Separator;
    @FXML
    private VBox BooksVbox;
    @FXML
    private FontAwesomeIconView ChevronLeft;

    @FXML
    private FontAwesomeIconView ChevronRight;

    private int currentPage = 0;
    private int rowsPerPage = 10;
    private List<Book> results;
    private static BookFilter filter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize filter with empty fields for books in the library, this ensures that not too much books are showing
        filter = new BookFilter("","","", "", null, "", true);
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

    @FXML
    private void Previous() throws IOException {
        showPage(currentPage - 1);
    }

    @FXML
    private void Next() throws IOException {
        showPage(currentPage + 1);
    }


    private List<Book> getResultsFromDatabase(BookFilter filter) {
        results = DBHandler.getBooksByFilter(filter);
        return results;
    }

    private int getTotalPages() {
        return (results.size() + rowsPerPage - 1) / rowsPerPage;
    }

    private void showPage(int page) throws IOException {
        int totalPages = getTotalPages();
        if (page < 0 || page > results.size() / rowsPerPage) {
            return;
        }
        if ( getTotalPages() <= 1 ){
            Separator.setVisible(false);
        }

        currentPage = page;
        BooksVbox.getChildren().clear();

        int start = page * rowsPerPage;
        int end = Math.min(start + rowsPerPage, results.size());


        for (int i = start; i < end; i++) {
            Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/Item-Book.fxml")));
            if (node instanceof Parent){

                Book book = results.get(i);

                Label titleLabel = (Label) node.lookup("#titleLabel");
                Label authorLabel = (Label) node.lookup("#authorLabel");
                Label editorLabel = (Label) node.lookup("#editorLabel");
                Label subjectLabel = (Label) node.lookup("#subjectLabel");
                Label IDLabel = (Label) node.lookup("#IDLabel");
                Label stockLabel = (Label) node.lookup("#stockLabel");
                Button button = (Button) ((Parent)node).lookup("#BookButton");

                titleLabel.setText(String.valueOf(book.getTitle()));
                StringBuilder authors = new StringBuilder();
                for (String author : book.getAuthors()){
                    authors.append(author);
                }
                authorLabel.setText(authors.toString());
                editorLabel.setText(book.getPublisher());
                StringBuilder subjects = new StringBuilder();
                for (String subject : book.getSubjects()){
                    authors.append(subject);
                }
                subjectLabel.setText(subjects.toString());
                IDLabel.setText(book.getId());
                stockLabel.setText(String.valueOf(book.getStock()));
            }
            BooksVbox.getChildren().add(node);
        }
        System.out.println("Showing page " + page + " from index " + start + " to " + (end - 1));
        updateButtonStates(totalPages);
    }

    private void updateButtonStates(int totalPages) {
        ChevronLeft.setVisible(currentPage > 0);
        ChevronRight.setVisible(currentPage < totalPages - 1);
    }



    @FXML
    private void handleButtonClick(Book book) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/Book-page.fxml"));
            Parent parent = SearchBookAnchorPane.getParent();

            if (parent instanceof AnchorPane) {
                AnchorPane center = (AnchorPane) parent;

                AnchorPane newCenter = loader.load();
                // Set the size constraints of the new AnchorPane
                newCenter.setPrefSize(center.getWidth(), center.getHeight());

                AnchorPane.setTopAnchor(newCenter, 0.0);
                AnchorPane.setLeftAnchor(newCenter, 210.0);
                AnchorPane.setBottomAnchor(newCenter, 0.0);
                AnchorPane.setRightAnchor(newCenter, 210.0);

                // Replace the embedded node with the new one
                center.getChildren().setAll(newCenter);

                BookPageController bookPageController = loader.getController();
                bookPageController.setButtonBook(book);
            } else {
                System.err.println("Parent is not an instance of AnchorPane");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void openFilterDialog(ActionEvent event) {
        try {
            // Load the FXML file for the dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/openjfx/cybooks/fxmlFiles/FilterDialogBook.fxml"));
            Parent root = loader.load();

            // Get the controller instance from the loader
            FilterDialogBookController dialogController = loader.getController();
            // Pass the reference to this SearchUserPageController
            dialogController.setSearchBookPageController(this);
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

    public void refreshSearchResults(BookFilter updatedFilter) {
        System.out.println("Filter applied : " + updatedFilter);
        // Update filter with the new one
        filter = updatedFilter;

        // Refresh the search results
        results = getResultsFromDatabase(filter);
        try {
            showPage(0);
            updateButtonStates(results.size() / rowsPerPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
