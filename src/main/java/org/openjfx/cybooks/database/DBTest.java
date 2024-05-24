package org.openjfx.cybooks.database;

import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Core;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.data.Loan;

import java.sql.Time;
import java.time.LocalDate;
import java.util.*;


public class DBTest {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        List<Loan> loans = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        CustomerFilter customerFilter = new CustomerFilter("", "", -1, "", "", "", 10, 2);
        BookFilter bookFilter = new BookFilter("", "", "", "", "", "", false);

        try {

            books = Core.getBooksByFilter(bookFilter);


            System.out.println(books.size());

             for (Book b : books) {
               System.out.println(b);
             }

//            for (Loan l : loans) {
//                System.out.println(l);
//            }
//             for (Customer c : customers) {
//                 System.out.println(c);
//             }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
