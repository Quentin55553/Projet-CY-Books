package org.openjfx.cybooks.database;

import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.data.Loan;

import java.sql.*;
import java.util.ArrayList;
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





    public static Book getBook(int id) throws NoSuchElementException {
        createConnexion();
        ResultSet res;
        Book book;

        try {
            res = statement.executeQuery("SELECT * FROM books WHERE id='" + id + "'");
            res.next();
            book = new Book(id, res.getString("ISBN"), res.getInt("quantity"), res.getInt("stock"));


        } catch (SQLException e) {
            throw new NoSuchElementException("No such book with id " + id + " in database");
        }

        closeConnexion();

        return book;
    }

    public static Book getBook(String ISBN) throws NoSuchElementException {
        createConnexion();
        ResultSet res;
        Book book;

        try {
            res = statement.executeQuery("SELECT * FROM books WHERE ISBN='" + ISBN + "'");

            res.next();
            book = new Book(res.getInt("id"), ISBN, res.getInt("quantity"), res.getInt("stock"));


        } catch (SQLException e) {
            throw new NoSuchElementException("No such book with ISBN " + ISBN + " in database");
        }

        closeConnexion();

        return book;
    }

    public static List<Loan> getLoans(int customer_id) throws NoSuchElementException {
        createConnexion();
        ResultSet res;
        List<Loan> loans = new ArrayList<>();

        try {
            res = statement.executeQuery("SELECT loans.id, book_id, customer_id, begin_date, duration, completed, books.ISBN as ISBN FROM loans, books WHERE customer_id='" + customer_id + "' AND book_id=books.id");

            while (res.next()) {
                loans.add(new Loan(res.getInt("id"), res.getString("ISBN"), customer_id, res.getDate("begin_date"), res.getInt("duration"), res.getBoolean("completed")));
            }


        } catch (SQLException e) {
            throw new NoSuchElementException("No such loan with customer_id " + customer_id + " in database");
        }

        closeConnexion();

        return loans;
    }

    public static List<Loan> getLoans (String ISBN) throws NoSuchElementException {
        createConnexion();
        ResultSet res;
        Loan loan;
        ArrayList<Loan> loans = new ArrayList<>();

        try {
            res = statement.executeQuery("SELECT loans.id, customer_id, begin_date, duration, completed FROM loans, books WHERE loans.book_id=books.id and books.ISBN='" + ISBN + "'");

            while (res.next()){
                loan = new Loan(res.getInt("id"), ISBN, res.getInt("customer_id"), res.getDate("begin_date"), res.getInt("duration"), res.getBoolean("completed"));
                loans.add(loan);
            }


        } catch (SQLException e) {
            throw new NoSuchElementException("No such loan with ISBN " + ISBN + " in database");
        }

        closeConnexion();

        return loans;
    }

    public static List<Loan> getLoans () throws NoSuchElementException {
        createConnexion();
        ResultSet res;
        Loan loan;
        ArrayList<Loan> loans = new ArrayList<>();

        try {
            res = statement.executeQuery("SELECT loans.id, book_id, customer_id, begin_date, duration, completed, books.ISBN as ISBN FROM loans, books WHERE books.id=loans.book_id");

            while (res.next()){

                loan = new Loan(res.getInt("id"), res.getString("ISBN"), res.getInt("customer_id"), res.getDate("begin_date"), res.getInt("duration"), res.getBoolean("completed"));
                loans.add(loan);
            }


        } catch (SQLException e) {
            throw new NoSuchElementException("No loans in database");
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
            while (res.next()) {
                customer = new Customer(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
                customers.add(customer);
            }


        } catch (SQLException e) {
            throw new NoSuchElementException("No such customer " + name + " in database");
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
            res = statement.executeQuery("SELECT loans.id, book_id, customer_id, begin_date, duration, completed, books.ISBN as ISBN FROM loans, books WHERE completed='0' AND loans.book_id=books.id");

            while (res.next()){
                loan = new Loan(res.getInt("id"), res.getString("ISBN"), res.getInt("customer_id"), res.getDate("begin_date"), res.getInt("duration"), false);
                loans.add(loan);
            }


        } catch (SQLException e) {
            throw new NoSuchElementException("No such loans in database");
        }

        closeConnexion();

        return loans;
    }

    public static void addBook (String ISBN, int stock, int total) {
        createConnexion();

        try {
            statement.execute("INSERT INTO books (ISBN, quantity, stock) VALUES ('" + ISBN + "', '" + total + "', '" + stock + "')");
        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
    }

    public static void addLoan (int book_id, int customer_id, int duration) {
        createConnexion();

        try {
            statement.execute("INSERT INTO loans (book_id, customer_id, duration) VALUES ('" + book_id + "', '" + customer_id + "', '" + duration + "')");
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

    public static void updateLoanDuration (int id, int duration) {
        createConnexion();

        try {
            statement.execute("UPDATE loans SET duration='" + duration + "' WHERE id='" + id + "'");
        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
    }

    public static void updateBookStock (int id, int stock) {
        createConnexion();

        try {
            statement.execute("UPDATE books SET stock='" + stock + "' WHERE id='" + id + "'");
        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
    }
    public static void updateBookTotal (int id, int total) {
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
                System.out.println(res.getString("id") + ", " + res.getInt("nb"));
                Book book = new Book(res.getInt("id"), res.getString("ISBN"), res.getInt("quantity"), res.getInt("stock"));
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
                System.out.println(res.getString("id") + ", " + res.getInt("nb"));
                Book book = new Book(res.getInt("id"), res.getString("ISBN"), res.getInt("quantity"), res.getInt("stock"));
                books.add(book);
            }


        } catch (SQLException e) {
            System.out.println(e);
        }
        closeConnexion();
        return books;
    }
}
