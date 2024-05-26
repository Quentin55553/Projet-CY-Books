package org.openjfx.cybooks.database;

import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.data.Loan;
import java.util.*;


public class DBTest {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        List<Loan> loans = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        CustomerFilter customerFilter = new CustomerFilter("", "", -1, "", "", "", 10, 2);
        BookFilter bookFilter = new BookFilter("", "", "", "", "", "", false);

        try {

            /*Book thebook = Core.getBook("12148/bpt6k33646735");
            String thebokktitle = thebook.getTitle();

            System.out.println(thebokktitle);

             /*for (Book b : books) {
               System.out.println(b);
             }*/

//            for (Loan l : loans) {
//                System.out.println(l);
//            }
//             for (Customer c : customers) {
//                 System.out.println(c);
//             }

            DBHandler.getMostPopularBooks();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
