package org.openjfx.cybooks.database;

import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Core;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.data.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class DBTest {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        List<Loan> loans = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        CustomerFilter customerFilter = new CustomerFilter(null, null, -1, null, null, null, 10, 2);
        BookFilter bookFilter = new BookFilter(null, null, null, null, "28737", null);

        try {

//            customers = Core.getCustomersByFilter(customerFilter);
//            customers = Core.getCustomers("Bel");
//
//            loans = Core.getExpiredLoans();

            books = Core.getBooksByFilter(bookFilter);

             for (Book b : books) {
               System.out.println(b);
             }

            for (Loan l : loans) {
                System.out.println(l);
            }
             for (Customer c : customers) {
                 System.out.println(c);
             }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
