package org.openjfx.cybooks.data;

import org.openjfx.cybooks.database.BookFilter;
import org.openjfx.cybooks.database.CustomerFilter;
import org.openjfx.cybooks.database.DBHandler;
import org.openjfx.cybooks.database.IncorrectPasswordException;

import java.sql.SQLException;
import java.util.*;


public class Core {

    public static Book getBook(String id) throws NoSuchElementException {
        Book book = DBHandler.getBook(id);
        // get data from API
        return book;
    }

    public static List<Customer> getCustomersByFilter(CustomerFilter filter) {
        return DBHandler.getCustomersByFilter(filter);
    }

    public static List<Book> getBooksByFilter(BookFilter filter) {

        List<Book> list = DBHandler.getBooksByFilter(filter);
        return list;
    }

    public static List<Loan> getLoans(int customerId) throws NoSuchElementException {
        return DBHandler.getLoansByCustomer(customerId);
    }

    public static List<Loan> getLoans(String book_id) throws NoSuchElementException {
        return DBHandler.getLoansByBookId(book_id);
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

    public static void addBook(String id, int stock, int total) {
        DBHandler.addBook(id, stock, total);
    }

    public static void addLoan(Book book, Customer customer, String expirationDate) {
        DBHandler.addLoan(book.getId(), customer.getId(), expirationDate);
    }


    public static void addCustomer(String firstname, String lastname, String tel, String email, String address) throws SQLException {
        DBHandler.addCustomer(firstname, lastname, tel, email, address);
    }

    public static void updateCustomer(int id, String firstName, String lastName) {
        DBHandler.updateCustomer(id, lastName, firstName);
    }



    public static void updateLoan(int id, boolean completed) {
        DBHandler.updateLoan(id, completed);
    }

    public static void updateLoanExpirationDate(String id, String expirationDate) {
        DBHandler.updateExpirationDate(id, expirationDate);
    }


    public static void updateBookStock(Book book, int stock) {
        book.setStock(stock);
        DBHandler.updateBookStock(book.getId(), stock);
    }

    public static void updateBookTotal(Book book, int total) {
        book.setTotal(total);
        DBHandler.updateBookTotal(book.getId(), total);
    }

    public static List<Book> getMostPopularBooks() {
        return DBHandler.getMostPopularBooks();
    }

    public static List<Book> getMostPopularBooksSince(String date) {
        return DBHandler.getMostPopularBooksSince(date);
    }

    public static List<Loan> getExpiredLoans () {
        return DBHandler.getExpiredLoans();
    }

    public static void removeBook() {

    }

    public Librarian librarianAuthentication(String login, String password) throws NoSuchElementException, IncorrectPasswordException {
        return DBHandler.librarianAuthentication(login, password);
    }

    public static void addLibrarian (String login, String lastName, String firstName, String password) throws SQLException {
        DBHandler.addLibrarian(login, lastName, firstName, password);
    }


}
