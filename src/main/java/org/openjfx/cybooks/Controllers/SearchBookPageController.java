package org.openjfx.cybooks.Controllers;

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
import org.openjfx.cybooks.database.DBHandler;
import org.openjfx.cybooks.data.Core;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller class for the book search page
 */
public class SearchBookPageController implements Initializable {


    /**
     * Anchor pane for the view of the search
     */
    @FXML
    private AnchorPane SearchBookAnchorPane;
    /**
     * Button used by the user if they want to filter their search
     */
    @FXML
    private JFXButton FilterSearchButton;
    /**
     * Line to organize the view
     */
    @FXML
    private Separator Separator;
    /**
     * View of the found books
     */
    @FXML
    private VBox BooksVbox;
    /**
     * Icon that can take the user to the previous page of the results
     */
    @FXML
    private FontAwesomeIconView ChevronLeft;
    /**
     * Icon that can take the user to the next page of the results
     */
    @FXML
    private FontAwesomeIconView ChevronRight;
    /**
     * Value of the current page of the results (0 at first because we are on the first page)
     */
    private int currentPage = 0;
    /**
     * Number of results for one results page
     */
    private int rowsPerPage = 10;
    /**
     * List of results for the search (Book objects)
     */
    private List<Book> results;
    /**
     * BookFilter object used for the search filtering
     */
    private static BookFilter filter;

    /**
     * This method handles the first arrival on the page, with an empty filter search (except for the databaseOnly field, which is set to true)
     * @param url Not used
     * @param resourceBundle Not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize filter with empty fields for books in the library, this ensures that not too much books are showing
        filter = new BookFilter("","","", "", "", "", true);
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

    /**
     * Method called on the left arrow click to go to the previous page of the results
     * @throws IOException If an error occurs during the page transition
     */
    @FXML
    private void Previous() throws IOException {
        showPage(currentPage - 1);
    }

    /**
     * Method called on the right arrow click to go to the next page of the results
     * @throws IOException If an error occurs during the page transition
     */
    @FXML
    private void Next() throws IOException {
        showPage(currentPage + 1);
    }


    /**
     * This method is used to get the results of a filtered book search using a BookFilter object
     * It puts the results in the results attribute
     * @param filter The BookFilter to use
     * @return Also returns the results found
     */
    private List<Book> getResultsFromDatabase(BookFilter filter) {
        results = Core.getBooksByFilter(filter);
        System.out.println(results);
        return results;
    }

    /**
     * This method is used to get the number of pages for the view from a number of found results (results attribute)
     * @return The number of pages needed to show the all of the results attribute
     */
    private int getTotalPages() {
        return (results.size() + rowsPerPage - 1) / rowsPerPage;
    }

    /**
     * This method is called to show the page number 'page' of the results of the Book search
     * @param page The page to show
     * @throws IOException If there is an error during the page transition
     */
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

                if(book.getTitle() != null && !book.getTitle().isEmpty()){
                    titleLabel.setText(String.valueOf(book.getTitle()));
                }
                else{
                    titleLabel.setText("N/A");
                }

                if(book.getAuthors() != null && !book.getAuthors().isEmpty()){
                    StringBuilder authors = new StringBuilder();
                    for (String author : book.getAuthors()){
                        authors.append(author);
                    }
                    authorLabel.setText(authors.toString());
                }
                else {
                    authorLabel.setText("N/A");
                }
                editorLabel.setText(book.getPublisher());
                if(book.getSubjects() != null && !book.getSubjects().isEmpty()){
                    StringBuilder subjects = new StringBuilder();
                    for (String subject : book.getSubjects()){
                        subjects.append(subject);
                    }
                    subjectLabel.setText(subjects.toString());
                }
                else{
                    subjectLabel.setText("N/A");
                }
                IDLabel.setText(book.getId());
                stockLabel.setText(String.valueOf(book.getStock()));
                button.setId("BookPage" + book.getId());
                // Book in library
                if(DBHandler.getBook(book.getId()).equals(book)){
                    button.setOnAction(actionEvent -> handleButtonClick(book));
                }
                // Book not in library
                else{
                    FontAwesomeIconView icon = (FontAwesomeIconView) button.getGraphic();
                    icon.setGlyphName("PLUS");
                    button.setOnAction(actionEvent -> AddToLibrary(book,button));
                }

            }
            BooksVbox.getChildren().add(node);
        }
        System.out.println("Showing page " + page + " from index " + start + " to " + (end - 1));
        updateButtonStates(totalPages);
    }

    /**
     * This method is used to make the arrows visible or not depending on the current page
     * Left arrow is not visible at first page, right is not at the last page
     * @param totalPages Number of total pages
     */
    private void updateButtonStates(int totalPages) {
        ChevronLeft.setVisible(currentPage > 0);
        ChevronRight.setVisible(currentPage < totalPages - 1);
    }


    /**
     * This method is used to handle a click on a book
     * It will show the book page of the selected book
     * @param book The clicked book (Book)
     */
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

    /**
     * This method handles a click on the 'add to library' button.
     * The book is added to the library and his button updated to now take the user to its page
     * @param book The Book object
     * @param button The button associated with the book
     */
    private void AddToLibrary(Book book, Button button) {
        Core.addBook(book.getId(), 5,5);
        book.setStock(5);
        book.setTotal(5);
        FontAwesomeIconView icon = (FontAwesomeIconView) button.getGraphic();
        icon.setGlyphName("BOOK");
        button.setOnAction(actionEvent -> handleButtonClick(book));
    }


    /**
     * This method is called to show the dialog page used for the search filtering
     * @param event The event that makes the dialog page open
     */
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

    /**
     * This method is called to refresh the search results, getting new results from the database using a BookFilter object
     * @param updatedFilter The new filter to use
     */
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
