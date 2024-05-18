package org.openjfx.cybooks.database;

import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.data.Librarian;
import org.openjfx.cybooks.data.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class DBHandler {

    private static Connection connexion;
    private static Statement statement;


    private static void createConnexion () {
        String url = "jdbc:mysql://localhost/CY-Books";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connexion = DriverManager.getConnection(url, user, password);
            statement = connexion.createStatement();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeConnexion () {
        try {
            connexion.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }





    public static Book getBook(String id) throws NoSuchElementException {
        createConnexion();
        ResultSet res;
        Book book = null;

        try {
            res = statement.executeQuery("SELECT * FROM books WHERE id='" + id + "'");
            if (res.getFetchSize() == 0)
                throw new NoSuchElementException("No such book with id " + id + " in database");
            res.next();
            book = new Book(id, res.getInt("quantity"), res.getInt("stock"));


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnexion();

        return book;
    }

    public static List<Loan> getLoansByCustomer(int customer_id) throws NoSuchElementException {
        createConnexion();
        ResultSet res;
        List<Loan> loans = new ArrayList<>();

        try {
            res = statement.executeQuery("SELECT loans.id, book_id, customer_id, begin_date, expiration_date, completed FROM loans, books WHERE customer_id='" + customer_id + "' AND book_id=books.id");
            if (res.getFetchSize() == 0)
                throw new NoSuchElementException("No such loan with customer_id " + customer_id + " in database");
            while (res.next()) {
                loans.add(new Loan(res.getInt("id"), res.getString("book_id"), customer_id, res.getDate("begin_date"), res.getDate("expiration_date"), res.getBoolean("completed")));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnexion();

        return loans;
    }

    public static List<Loan> getLoansByBookId(String bookId) throws NoSuchElementException {
        createConnexion();
        ResultSet res;
        Loan loan;
        ArrayList<Loan> loans = new ArrayList<>();

        try {
            res = statement.executeQuery("SELECT loans.id, customer_id, begin_date, expiration_date, completed FROM loans, books WHERE loans.book_id=books.id");
            if (res.getFetchSize() == 0)
                throw new NoSuchElementException("No such loan with bookId " + bookId + " in database");
            while (res.next()){
                loan = new Loan(res.getInt("id"), bookId, res.getInt("customer_id"), res.getDate("begin_date"), res.getDate("expiration_date"), res.getBoolean("completed"));
                loans.add(loan);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnexion();

        return loans;
    }

    public static List<Loan> getLoans() throws NoSuchElementException {
        createConnexion();
        ResultSet res;
        Loan loan;
        ArrayList<Loan> loans = new ArrayList<>();

        try {
            res = statement.executeQuery("SELECT loans.id, book_id, customer_id, begin_date, expiration_date, completed FROM loans");
            if (res.getFetchSize() == 0)
                throw new NoSuchElementException("No loans in database");

            while (res.next()){

                loan = new Loan(res.getInt("id"), res.getString("book_id"), res.getInt("customer_id"), res.getDate("begin_date"), res.getDate("expiration_date"), res.getBoolean("completed"));
                loans.add(loan);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnexion();

        return loans;
    }

    public static List<Customer> getCustomers(String name) throws NoSuchElementException {
        createConnexion();
        ResultSet res;
        Customer customer;
        ArrayList<Customer> customers = new ArrayList<>();

        try {
            res = statement.executeQuery("SELECT * FROM customers WHERE last_name like '%" + name + "%'");
            if (res.getFetchSize() == 0)
                throw new NoSuchElementException("No such customer with name " + name + " in database");
            while (res.next()) {
                customer = new Customer(res.getInt("id"), res.getString("first_name"), res.getString("last_name"), res.getString("tel"), res.getString("email"), res.getString("address"));
                customers.add(customer);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnexion();

        return customers;
    }
    public static List<Loan> getOngoingLoans () throws NoSuchElementException {
        createConnexion();
        ResultSet res;
        Loan loan;
        ArrayList<Loan> loans = new ArrayList<>();

        try {
            res = statement.executeQuery("SELECT loans.id, book_id, customer_id, begin_date, expiration_date, completed FROM loans WHERE completed='0'");
            if (res.getFetchSize() == 0)
                throw new NoSuchElementException("No ongoing loans in database");

            while (res.next()){
                loan = new Loan(res.getInt("id"), res.getString("book_id"), res.getInt("customer_id"), res.getDate("begin_date"), res.getDate("expiration_date"), false);
                loans.add(loan);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnexion();

        return loans;
    }

    public static void addBook (String ID, int stock, int total) {
        createConnexion();

        try {
            statement.execute("INSERT INTO books (id, quantity, stock) VALUES ('" + ID + "', '" + total + "', '" + stock + "')");
        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
    }

    public static void addLoan (String book_id, int customer_id, Date expirationDate) {
        createConnexion();

        try {
            statement.execute("INSERT INTO loans (book_id, customer_id, expiration_date) VALUES ('" + book_id + "', '" + customer_id + "', '" + expirationDate + "')");
        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
    }

    public static void addCustomer (String lastName, String firstName) {
        createConnexion();

        try {
            statement.execute("INSERT INTO customers (`last_name`, `first_name`) VALUES ('" + lastName + "', '" + firstName + "')");
        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
    }

    public static void updateCustomer (int id, String lastName, String firstName) {
        createConnexion();

        try {
            statement.execute("UPDATE customers SET last_name='" + lastName + "', first_name='" + firstName + "' WHERE id='" + id + "'");
        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
    }

    public static void updateLoan (int id, boolean completed) {
        createConnexion();

        int value = (completed ? 1 : 0);

        try {
            statement.execute("UPDATE loans SET completed='" + value + "' WHERE id='" + id + "'");
        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
    }

    public static void updateExpirationDate(String id, Date expirationDate) {
        createConnexion();

        try {
            statement.execute("UPDATE loans SET expiration_date='" + expirationDate + "' WHERE id='" + id + "'");
        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
    }

    public static void updateBookStock (String id, int stock) {
        createConnexion();

        try {
            statement.execute("UPDATE books SET stock='" + stock + "' WHERE id='" + id + "'");
        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
    }
    public static void updateBookTotal (String id, int total) {
        createConnexion();

        try {
            statement.execute("UPDATE books SET quantity='" + total + "' WHERE id='" + id + "'");
        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
    }

    public static List<Book> getMostPopularBooks() {
        createConnexion();
        ResultSet res;

        List<Book> books = new ArrayList<>();


        try {
//            resBooks = statement.executeQuery("SELECT * FROM books WHERE id IN (SELECT book_id FROM loans GROUP BY book_id ORDER BY COUNT(book_id) DESC)");
            res = statement.executeQuery("SELECT *, count(book_id) as nb FROM books RIGHT JOIN loans l on books.id = l.book_id GROUP BY book_id ORDER BY COUNT(book_id) DESC");


            while (res.next()) {
//                System.out.println(res.getString("id") + ", " + res.getInt("nb"));
                Book book = new Book(res.getString("id"), res.getInt("quantity"), res.getInt("stock"));
                books.add(book);
            }


        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
        return books;
    }

    public static List<Book> getMostPopularBooksSince(String date) {
        createConnexion();
        ResultSet res;

        List<Book> books = new ArrayList<>();


        try {
            res = statement.executeQuery("SELECT *, count(book_id) as nb FROM books RIGHT JOIN loans l on books.id = l.book_id WHERE begin_date >= '" + date + "' GROUP BY book_id ORDER BY COUNT(book_id) DESC");


            while (res.next()) {
//                System.out.println(res.getString("id") + ", " + res.getInt("nb"));
                Book book = new Book(res.getString("id"), res.getInt("quantity"), res.getInt("stock"));
                books.add(book);
            }


        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
        return books;
    }

    public static List<Loan> getExpiredLoans() throws NoSuchElementException {
        createConnexion();
        ResultSet res;
        List<Loan> loans = new ArrayList<>();

        try {
            res = statement.executeQuery("SELECT * FROM loans WHERE expiration_date < CURRENT_DATE");
            if (res.getFetchSize() == 0)
                throw new NoSuchElementException("No expired loans in database in database");
            while (res.next()) {
                boolean completed = (res.getInt("completed") != 0);
                loans.add(new Loan(res.getInt("id"), res.getString("book_id"), res.getInt("customer_id"), res.getDate("begin_date"), res.getDate("expiration_date"), completed));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        closeConnexion();
        return loans;
    }

    public static Librarian librarianAuthentication(String user, String password) {
        createConnexion();
        ResultSet res;
        Librarian librarian = null;

        try {
            res = statement.executeQuery("SELECT * FROM librarians WHERE last_name='" + user + "'AND password='" + password + "'");
            res.next();
            librarian = new Librarian(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnexion();

        return librarian;
    }
}
