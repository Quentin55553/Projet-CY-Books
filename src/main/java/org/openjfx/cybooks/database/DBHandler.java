package org.openjfx.cybooks.database;

import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.data.Librarian;
import org.openjfx.cybooks.data.Loan;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


public class DBHandler {
    private static Connection connection;
    private static Statement statement;


    private static void createFirstConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "");
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/CY-Books", "root", "");
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void closeConnection () {
        try {
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void executeSQLFile(String filePath) {
        createFirstConnection();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder sql = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sql.append(line).append("\n");
            }

            String[] sqlCommands = sql.toString().split(";");

            for (String command : sqlCommands) {
                if (!command.trim().isEmpty()) {
                    statement.execute(command.trim());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Book getBook(String id) throws NoSuchElementException {
        createConnection();
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

        closeConnection();

        return book;
    }


    public static List<Loan> getLoansByCustomer(int customer_id) throws NoSuchElementException {
        createConnection();
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

        closeConnection();

        return loans;
    }


    public static List<Loan> getLoansByBookId(String bookId) throws NoSuchElementException {
        createConnection();
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

        closeConnection();

        return loans;
    }


    public static List<Loan> getLoans() throws NoSuchElementException {
        createConnection();
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

        closeConnection();

        return loans;
    }


    public static List<Customer> getCustomers(String name) throws NoSuchElementException {
        createConnection();
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

        closeConnection();

        return customers;
    }


    public static List<Loan> getOngoingLoans () throws NoSuchElementException {
        createConnection();
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

        closeConnection();

        return loans;
    }


    public static void addBook (String ID, int stock, int total) {
        createConnection();

        try {
            statement.execute("INSERT INTO books (id, quantity, stock) VALUES ('" + ID + "', '" + total + "', '" + stock + "')");

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    public static void addLoan (String book_id, int customer_id, Date expirationDate) {
        createConnection();

        try {
            statement.execute("INSERT INTO loans (book_id, customer_id, expiration_date) VALUES ('" + book_id + "', '" + customer_id + "', '" + expirationDate + "')");

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    public static void addCustomer (String firstname, String lastname, String tel, String email, String address) throws SQLException {
        createConnection();

        try {
            statement.execute("INSERT INTO customers (`last_name`, `first_name`, `tel`, `email`, `address`) VALUES ('" + lastname + "', '" + firstname + "', '" + tel + "', '" + email + "', '" + address + "')");

        } catch (SQLException e) {
            // Checks if MySQL indicates a unique constraint violation
            if (e.getErrorCode() == 1062) {
                throw new SQLException("Ce membre existe déjà");

            } else {
                throw new SQLException("Une erreur s'est produite");
            }
        }

        closeConnection();
    }


    public static void addLibrarian (String login, String lastName, String firstName, String password) throws SQLException {
        createConnection();

        try {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            statement.execute("INSERT INTO librarians (`login`, `last_name`, `first_name`, `password`) VALUES ('" + login + "', '" + lastName + "', '" + firstName + "', '" + hashedPassword +"')");

        } catch (SQLException e) {
            // Checks if MySQL indicates a unique constraint violation
            if (e.getErrorCode() == 1062) {
                throw new SQLException("Cet identifiant existe déjà");

            } else {
                throw new SQLException("Une erreur s'est produite");
            }
        }

        closeConnection();
    }


    public static void updateCustomer (int id, String lastName, String firstName) {
        createConnection();

        try {
            statement.execute("UPDATE customers SET last_name='" + lastName + "', first_name='" + firstName + "' WHERE id='" + id + "'");

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    public static void updateLoan (int id, boolean completed) {
        createConnection();

        int value = (completed ? 1 : 0);

        try {
            statement.execute("UPDATE loans SET completed='" + value + "' WHERE id='" + id + "'");

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    public static void updateExpirationDate(String id, Date expirationDate) {
        createConnection();

        try {
            statement.execute("UPDATE loans SET expiration_date='" + expirationDate + "' WHERE id='" + id + "'");

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    public static void updateBookStock (String id, int stock) {
        createConnection();

        try {
            statement.execute("UPDATE books SET stock='" + stock + "' WHERE id='" + id + "'");

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    public static void updateBookTotal (String id, int total) {
        createConnection();

        try {
            statement.execute("UPDATE books SET quantity='" + total + "' WHERE id='" + id + "'");

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    public static List<Book> getMostPopularBooks() {
        createConnection();
        ResultSet res;

        List<Book> books = new ArrayList<>();

        try {
            // resBooks = statement.executeQuery("SELECT * FROM books WHERE id IN (SELECT book_id FROM loans GROUP BY book_id ORDER BY COUNT(book_id) DESC)");
            res = statement.executeQuery("SELECT *, count(book_id) as nb FROM books RIGHT JOIN loans l on books.id = l.book_id GROUP BY book_id ORDER BY COUNT(book_id) DESC");

            while (res.next()) {
                // System.out.println(res.getString("id") + ", " + res.getInt("nb"));
                Book book = new Book(res.getString("id"), res.getInt("quantity"), res.getInt("stock"));
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();

        return books;
    }


    public static List<Book> getMostPopularBooksSince(String date) {
        createConnection();
        ResultSet res;

        List<Book> books = new ArrayList<>();

        try {
            res = statement.executeQuery("SELECT *, count(book_id) as nb FROM books RIGHT JOIN loans l on books.id = l.book_id WHERE begin_date >= '" + date + "' GROUP BY book_id ORDER BY COUNT(book_id) DESC");

            while (res.next()) {
                // System.out.println(res.getString("id") + ", " + res.getInt("nb"));
                Book book = new Book(res.getString("id"), res.getInt("quantity"), res.getInt("stock"));
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();

        return books;
    }


    public static List<Loan> getExpiredLoans() throws NoSuchElementException {
        createConnection();
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

        closeConnection();

        return loans;
    }


    public static Librarian librarianAuthentication(String login, String password) throws IncorrectPasswordException, NoSuchElementException {
        createConnection();
        ResultSet res;
        Librarian librarian = null;

        try {
            res = statement.executeQuery("SELECT * FROM librarians WHERE login='" + login + "'");

            if (!res.next()) {
                throw new NoSuchElementException("Nom d'utilisateur introuvable");
            }

            String hashedPassword = res.getString("password");

            if (BCrypt.checkpw(password, hashedPassword))
                librarian = new Librarian(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));

            else
                throw new IncorrectPasswordException("Mot de passe incorrect");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnection();

        return librarian;
    }
}
