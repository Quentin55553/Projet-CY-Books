package org.openjfx.cybooks.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Core;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.database.CustomerFilter;
import org.openjfx.cybooks.database.DBHandler;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


public class addLoanPageController {

    @FXML
    private JFXButton checkBook;

    @FXML
    private JFXButton checkCustomer;

    @FXML
    private HBox errorBook;

    @FXML
    private HBox errorCustomer;

    @FXML
    private TextField FirstName;

    @FXML
    private TextField ID;

    @FXML
    private TextField LastName;

    @FXML
    private HBox okBook;

    @FXML
    private HBox okCustomer;
    @FXML
    private Label validateState;
    Customer customerSelected;
    Book bookSelected;
    LocalDate expirationdate;


    @FXML
    private void checkCustomerFields(){
        okCustomer.setVisible(false);
        errorCustomer.setVisible(false);
        CustomerFilter filter = new CustomerFilter(LastName.getText().trim(), FirstName.getText().trim(),null,"","","",null, null);
        List<Customer> custommer= DBHandler.getCustomersByFilter(filter);
        System.out.println(filter);
        System.out.println(custommer);
        if(custommer.size() == 1){
            okCustomer.setVisible(true);
            checkCustomer.setUserData(true);
            customerSelected = custommer.get(0);
        }
        else {
            errorCustomer.setVisible(true);
            checkCustomer.setUserData(false);
            clearCustomerFields();
        }
    }

    @FXML
    private void checkBookField(){
        okBook.setVisible(false);
        errorBook.setVisible(false);
        try{
            bookSelected = DBHandler.getBook(ID.getText().trim());
            okBook.setVisible(true);
            checkBook.setUserData(true);
        }
        catch(NoSuchElementException e) {
                errorBook.setVisible(true);
                checkBook.setUserData(false);
                clearLoanFields();
        }
    }


    private void clearCustomerFields(){
        FirstName.clear();
        LastName.clear();
    }
    private void clearLoanFields(){
        ID.clear();
    }

    /*public boolean Action() {
        // Create the date picker dialog
        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Select a Date");

        // Create and configure the DatePicker
        DatePicker datePicker = new DatePicker();
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
            return null;
        });

        // Show the dialog and wait for the user response
        Optional<LocalDate> result = dialog.showAndWait();

        // Process the selected date (if any)
        result.ifPresent(selectedDate -> {
            Platform.runLater(() -> {
                System.out.println("Selected Date: " + selectedDate);
                expirationdate = selectedDate;
            });
        });

        return result.isPresent();
    }*/
    public boolean PickDate() {
        // Create the date picker dialog
        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Select a Date");

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
            System.out.println("Selected Date: " + selectedDate);
            expirationdate = selectedDate; // Store the selected date
        });

        return result.isPresent(); // Return true if a date was selected
    }


    private void clearPage(){
        okBook.setVisible(false);
        errorBook.setVisible(false);
        errorCustomer.setVisible(false);
        okCustomer.setVisible(false);
        FirstName.clear();
        LastName.clear();
        ID.clear();
    }

    @FXML
    private void ValidateLoan(){
        if(Boolean.TRUE.equals(checkCustomer.getUserData()) && Boolean.TRUE.equals(checkBook.getUserData()) && PickDate()){
            Core.addLoan(bookSelected,customerSelected,String.valueOf(expirationdate));
            System.out.println(String.valueOf(expirationdate));
            clearPage();
            validateState.setText("Emprunt enregistré !");
            validateState.setStyle("-fx-text-fill: #606C38;");
        }
        else{
            validateState.setText("L'emprunt ne peux pas être valider");
            validateState.setStyle("-fx-text-fill: #a94442;");
        }
    }


}
