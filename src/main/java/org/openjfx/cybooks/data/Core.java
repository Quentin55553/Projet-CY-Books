package org.openjfx.cybooks.data;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Core {
    private static ArrayList<Book> bookList;
    private static ArrayList<Loan> loanList;
    private static ArrayList<Customer> customerList;

    public static void init () {

    }


    public static Customer loadCustomer(int id) {
        return null;
    }

    public static Book loadBook( int ISBN) {
        return null;
    }

    public static Loan loadLoan(int id) {
        return null;
    }

    public static List<Customer> getCustomers(){
        return null;
    }

    public static List<Book> getBooks() {
        return null;
    }

    public static List<Loan> getLoans() {
        return null;
    }

    public static void addCustomer(String firstName, String lastName) {
        Customer c = new Customer(0, firstName, lastName);
        customerList.add(c);
        // add customer to database
    }

    public static void alterCustomer(int id, String firstName, String lastName) {
        Customer c = customerList.get(id);
        c.setFirstName(firstName);
        c.setLastName(lastName);
        // modify user in database
    }

    public static void addLoan(Book book, Customer customer, int duration) {
//        Loan n = new Loan(0, book.getISBN(), customer.getId(), new Date(), duration);
    }

    public static void alterLoan() {

    }

    public static void addBook(int ISBN, int total) {

    }

    public static void alterBook(int total) {

    }

    public static void removeBook() {

    }

    public Boolean librarianAuthentication(String login, String password) {
        return true;
    }


}
