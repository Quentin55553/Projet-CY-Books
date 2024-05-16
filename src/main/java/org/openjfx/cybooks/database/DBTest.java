package org.openjfx.cybooks.database;

import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.data.Loan;

import java.util.List;
import java.util.NoSuchElementException;


public class DBTest {
    public static void main(String[] args) {
        List<Book> books;
        List<Loan> loans;
        List<Customer> customers;

        try {
            // System.out.println(DBHandler.getBook("22784"));
            // List<Loan> loans = DBHandler.getLoans();

            // customers = DBHandler.getCustomers("Be");
            // for (Customer c : customers) {
            //     System.out.println(c);
            // }

            // DBHandler.updateLoan(5, true);

            loans = DBHandler.getOngoingLoans();

            // DBHandler.addCustomer("Bel", "Theo");
            // books = DBHandler.getMostPopularBooks();
            // books = DBHandler.getMostPopularBooksSince("2024-05-14");

            // for (Book b : books) {
            //   System.out.println(b);
            // }

            for (Loan l : loans) {
                System.out.println(l);
            }

        } catch (NoSuchElementException e) {
            System.out.println(e);
        }
    }
}
