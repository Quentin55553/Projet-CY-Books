package org.openjfx.cybooks.Controllers;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.database.DBHandler;

public class BookPageController {
    @FXML
    private JFXTextArea author;
    @FXML
    private JFXTextArea description;
    @FXML
    private TextField stock;
    @FXML
    private TextField quantity;

    @FXML
    private JFXTextArea title;
    /**
     * Book object representing the book
     */
    private Book book;

    public void setButtonBook(Book book) {
        this.book = book;
        loadBookInformation();
    }

    private void loadBookInformation() {
        title.setText(book.getTitle());
        author.setText(String.valueOf(book.getAuthors()));
        description.setText(book.getDescription());
        stock.setText(String.valueOf(book.getStock()));
        quantity.setText(String.valueOf(book.getTotal()));
    }

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
