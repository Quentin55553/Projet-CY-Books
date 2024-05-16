package org.openjfx.cybooks.data;

import org.openjfx.cybooks.database.DBHandler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class Core {

    public static Book getBook(int id) throws NoSuchElementException {
        Book book = DBHandler.getBook(id);
        // get data from API
        return book;
    }

    public static Book getBook(String ISBN) throws NoSuchElementException {
        Book book = DBHandler.getBook(ISBN);
        // get data from API
        return book;
    }

    public static List<Loan> getLoans(int customerId) throws NoSuchElementException {
        return DBHandler.getLoansByCustomer(customerId);
    }

    public static List<Loan> getLoans(String ISBN) throws NoSuchElementException {
        return DBHandler.getLoansByISBN(ISBN);
    }

    public static List<Loan> getLoans() throws NoSuchElementException {
        return DBHandler.getLoans();
    }

    public static List<Loan> getOngoingLoans() throws NoSuchElementException {
        return DBHandler.getOngoingLoans();
    }

    public static List<Customer> getCustomers(String name) {
        return DBHandler.getCustomers(name);
    }

    public static void addBook(String ISBN, int stock, int total) {
        DBHandler.addBook(ISBN, stock, total);
    }

    public static void addLoan(Book book, Customer customer, int duration) {
        DBHandler.addLoan(book.getId(), customer.getId(), duration);
    }

    public static void addCustomer(String firstName, String lastName) {
        DBHandler.addCustomer(lastName, firstName);
    }

    public static void updateCustomer(int id, String firstName, String lastName) {
        DBHandler.updateCustomer(id, lastName, firstName);
    }



    public static void updateLoan(int id, boolean completed) {
        DBHandler.updateLoan(id, completed);
    }

    public static void updateLoanDuration(int id, int duration) {
        DBHandler.updateLoanDuration(id, duration);
    }


    public static void updateBookStock(Book book, int stock) {
        book.setStock(stock);
        DBHandler.updateLoanDuration(book.getId(), stock);
    }

    public static void updateBookTotal(Book book, int total) {
        book.setTotal(total);
        DBHandler.updateLoanDuration(book.getId(), total);
    }

    public static List<Book> getMostPopularBooks() {
        return DBHandler.getMostPopularBooks();
    }

    public static List<Book> getMostPopularBooksSince(String date) {
        return DBHandler.getMostPopularBooksSince(date);
    }

    public static void removeBook() {

    }

    public Boolean librarianAuthentication(String login, String password) {
        return true;
    }


}
