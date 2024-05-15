package org.openjfx.cybooks.database;


import java.util.NoSuchElementException;

public class DBTest {

    public static void main(String[] args) {

        try {
//            DBHandler.getBook("22784");
//            List<Loan> loans = DBHandler.getLoans();
//            for (Loan l : loans) {
//                System.out.println(l);
//            }



//            DBHandler.addLoan(1, 2, 5);

//            DBHandler.addCustomer("Be", "Theo");

            DBHandler.getMostPopularBooks();

        } catch (NoSuchElementException e) {
            System.out.println(e);
        }




    }
}
