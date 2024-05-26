package org.openjfx.cybooks.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Core;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.database.CustomerFilter;
import org.openjfx.cybooks.database.DBHandler;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Controller class for the page that allows the user to add a loan
 */
public class addLoanPageController {

    /**
     * Button that allows the user to check if the book can be found
     */
    @FXML
    private JFXButton checkBook;

    /**
     * Button that allows the user to check if the customer can be found
     */
    @FXML
    private JFXButton checkCustomer;

    /**
     * Error box that shows when the book can't be found
     */
    @FXML
    private HBox errorBook;

    /**
     * Error box that shows when the customer can't be found
     */
    @FXML
    private HBox errorCustomer;
    /**
     * Error box that shows when the book is not available
     */
    @FXML
    private HBox toMuchLoanBook;

    /**
     * Error box that shows when the customer made too much loans
     */
    @FXML
    private HBox toMuchLoanCustomer;

    /**
     * Field used to enter the firstname of the customer
     */
    @FXML
    private TextField FirstName;

    /**
     * Field used to enter the identifier of the book
     */
    @FXML
    private TextField ID;
    /**
     * Field used to enter the lastname of the customer
     */
    @FXML
    private TextField LastName;

    /**
     * Box that shows when the book is found in the database
     */
    @FXML
    private HBox okBook;

    /**
     * Box shown when the customer is found in the database
     */
    @FXML
    private HBox okCustomer;
    /**
     * Label used to show if the loan was validated or not
     */
    @FXML
    private Label validateState;
    /**
     * The customer borrowing a book
     */
    Customer customerSelected;
    /**
     * The borrowed book
     */
    Book bookSelected;
    /**
     * The expiration date of the loan
     */
    LocalDate expirationdate;


    /**
     * This method checks if the customer can be fetched from the database using their name and shows messages depending on the result
     */
    @FXML
    private void checkCustomerFields(){
        okCustomer.setVisible(false);
        errorCustomer.setVisible(false);
        toMuchLoanCustomer.setVisible(false);
        CustomerFilter filter = new CustomerFilter(LastName.getText().trim(), FirstName.getText().trim(),null,"","","",null, null);
        List<Customer> custommer= DBHandler.getCustomersByFilter(filter);
        if(custommer.size() == 1){
            // Ensures the customer did not make too much loans
            if(custommer.get(0).getLoanCount() < 10){
                okCustomer.setVisible(true);
                checkCustomer.setUserData(true);
                customerSelected = custommer.get(0);
            }
            else{
                toMuchLoanCustomer.setVisible(true);
                checkCustomer.setUserData(false);
                clearCustomerFields();
            }
        }
        else {
            errorCustomer.setVisible(true);
            checkCustomer.setUserData(false);
            clearCustomerFields();
        }
    }

    /**
     * This method checks if the book can be fetched from the database using its identifier and shows messages depending on the result
     */
    @FXML
    private void checkBookField(){
        okBook.setVisible(false);
        errorBook.setVisible(false);
        toMuchLoanBook.setVisible(false);
        try{
            bookSelected = DBHandler.getBook(ID.getText().trim());
            // Ensures the book is in stock for loans
            if(bookSelected.getStock() > 0){
                okBook.setVisible(true);
                checkBook.setUserData(true);
            }
            else{
                toMuchLoanBook.setVisible(true);
                checkBook.setUserData(false);
                clearLoanFields();
            }
        }
        catch(NoSuchElementException e) {
                errorBook.setVisible(true);
                checkBook.setUserData(false);
                clearLoanFields();
        }
    }


    /**
     * This method is used to clear the fields corresponding to the customer
     */
    private void clearCustomerFields(){
        FirstName.clear();
        LastName.clear();
    }

    /**
     * This method is used to clear the fields corresponding to the book
     */
    private void clearLoanFields(){
        ID.clear();
    }

    /**
     * This method is called when the loan is validated. The librarian must enter an expiration date for the loan
     * @return true if a date was entered, false if not
     */
    public boolean PickDate() {
        // Create the date picker dialog
        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Date d'expiration");

        // Initialize DatePicker with today's date
        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isBefore(LocalDate.now())); // Disable past dates
            }
        });
        dialog.getDialogPane().setContent(datePicker);

        // Add buttons to the dialog
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Handle the result when OK is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return datePicker.getValue();
            }
            return null; // Return null if canceled
        });

        // Show the dialog and wait for the user response
        Optional<LocalDate> result = dialog.showAndWait();

        // Process the selected date (if any)
        result.ifPresent(selectedDate -> {
            expirationdate = selectedDate; // Store the selected date
        });

        return result.isPresent(); // Return true if a date was selected
    }


    /**
     * This method is used to clear the messages and information on the page
     */
    private void clearPage(){
        okBook.setVisible(false);
        errorBook.setVisible(false);
        errorCustomer.setVisible(false);
        okCustomer.setVisible(false);
        FirstName.clear();
        LastName.clear();
        ID.clear();
    }

    /**
     * This methods adds the loan to the database if it is valid.
     * It shows a confirmation or an error message
     */
    @FXML
    private void ValidateLoan(){
        if(Boolean.TRUE.equals(checkCustomer.getUserData()) && Boolean.TRUE.equals(checkBook.getUserData()) && PickDate()){
            Core.addLoan(bookSelected,customerSelected,String.valueOf(expirationdate));
            clearPage();
            validateState.setText("Emprunt enregistré !");
            validateState.setStyle("-fx-text-fill: #606C38;");
        }
        else{
            validateState.setText("L'emprunt ne peut pas être validé");
            validateState.setStyle("-fx-text-fill: #a94442;");
        }
    }


}
