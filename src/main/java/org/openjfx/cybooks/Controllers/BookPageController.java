package org.openjfx.cybooks.Controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.database.DBHandler;

/**
 * Controller for the page showing the information of a specific book
 */
public class BookPageController {
    /**
     * Text area used to show the authors of the book
     */
    @FXML
    private JFXTextArea author;
    /**
     * Text area used to show the description of the book
     */
    @FXML
    private JFXTextArea description;
    /**
     * Text area used to show the stock of the book
     */
    @FXML
    private TextField stock;
    /**
     * Text area used to show the quantity of the book
     */
    @FXML
    private TextField quantity;

    /**
     * Text area used to show the title of the book
     */
    @FXML
    private JFXTextArea title;
    /**
     * Book object representing the book
     */
    private Book book;

    /**
     * Method used to load the book's information on the page
     * @param book
     */
    public void setButtonBook(Book book) {
        this.book = book;
        loadBookInformation();
    }

    /**
     * This method sets the text on the fields to show the information of the book
     */
    private void loadBookInformation() {
        title.setText(book.getTitle());
        author.setText(String.valueOf(book.getAuthors()));
        description.setText(book.getDescription());
        stock.setText(String.valueOf(book.getStock()));
        quantity.setText(String.valueOf(book.getTotal()));
    }

    /**
     * This method handles editing of the stock
     * The user must enter a new value and press the enter key
     */
    @FXML
    private void editStock(){
        // Make the stock text field editable and request focus
        stock.setEditable(true);
        stock.requestFocus();

        // Select all text to make it easier to edit
        stock.selectAll();

        // Add a key event handler to listen for the Enter key press
        stock.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    String newstock = stock.getText().trim();
                    if(checkStockupdate(newstock)){
                        // update stock and quantity
                        DBHandler.updateBookTotal(book.getId(), book.getTotal()+Integer.parseInt(newstock)- book.getStock());
                        book.setTotal(book.getTotal()+Integer.parseInt(newstock)- book.getStock());
                        DBHandler.updateBookStock(book.getId(), Integer.parseInt(newstock));
                        book.setStock(Integer.parseInt(newstock));
                        // Set the new quantity field
                        quantity.setText(String.valueOf(book.getTotal()));
                    }
                    else{
                        stock.setText(String.valueOf(book.getStock()));
                    }
                    // Make the text field uneditable again
                    stock.setEditable(false);
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * This method checks if the stock value entered is correct
     * @param newStock The new stock value entered by the user
     * @return true if the value is correct, false if not
     */
    private boolean checkStockupdate(String newStock){
        if(newStock == null || newStock.equals("")){
            return false;
        }
        try{
            int stockint = Integer.parseInt(newStock);
            return stockint >= 0;
        }catch(NumberFormatException e){
            return false;
        }
    }


}
