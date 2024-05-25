package org.openjfx.cybooks.database;

import org.openjfx.cybooks.UserInput.IncorrectPasswordException;
import org.openjfx.cybooks.data.Book;
import org.openjfx.cybooks.data.Customer;
import org.openjfx.cybooks.data.Librarian;
import org.openjfx.cybooks.data.Loan;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * The DBHandler class is used to do queries to the database
 */
public class DBHandler {
    /**
     * Contains a connection to the database
     */
    private static Connection connection;
    /**
     * Contains the statement to the database
     */
    private static Statement statement;


    /**
     * Creates the first connection to the database, used to initialize it
     */
    private static void createFirstConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "");
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates a new connection to the database
     * @implNote Must be called before any interaction with the database
     */
    private static void createConnection() throws SQLSyntaxErrorException {
        try {

            // trying to connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/CY-Books", "root", "");
            // creating the statement object to be used for incoming queries
            statement = connection.createStatement();

            // now that the connection is opened, we can do queries in the other methods

        } catch (SQLSyntaxErrorException e) {
            throw e;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Closes the connection to the database
     * @implNote Must be called after any interaction with the database
     */
    private static void closeConnection () {
        try {
            // trying to close the connection
            connection.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Initialize the database on the servers
     * @param filePath The database's file path
     */
    public static void executeSQLFile(String filePath) {
        try {
            createConnection();

        } catch (SQLSyntaxErrorException exception) {
            System.out.println("Database CY-Books not found, trying to create it");
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

        closeConnection();
    }


    /**
     * Returns a semi-empty Book from its id in database
     * @param id The id in database
     * @return A semi-empty Book with all the information available
     * @throws NoSuchElementException Thrown if the book is missing on the database
     */
    public static Book getBook(String id) throws NoSuchElementException {
        ResultSet res;
        Book book = null;

        try {
            // connect to the database
            createConnection();
            // make the query
            res = statement.executeQuery("SELECT * FROM books WHERE id='" + id + "'");

            // catch results if existing

            if (!res.next()) {
                closeConnection();
                throw new NoSuchElementException("No such book with id " + id + " in database");
            }

            // create the book object
            book = new Book(id, res.getInt("quantity"), res.getInt("stock"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // always close the connection
        closeConnection();

        return book;
    }


    /**
     * Checks if a Book is in database from its id
     * @param id the id to test
     * @return true if it is in database false otherwise
     */
    public static boolean isInLibrary(String id) {
        ResultSet res = null;
        boolean isInLibrary = false;

        try {
            createConnection();
        } catch (SQLException e) {

        }

        String query = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, id);
            res = preparedStatement.executeQuery();

            if (res.next()) {
                isInLibrary = true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            try {
                if (res != null) {
                    res.close();
                }

                closeConnection();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return isInLibrary;
    }


    /**
     * Returns a list of loans from some customer
     * @param customer_id The customer's id
     * @return A list of loans from some customer
     */
    public static List<Loan> getLoansByCustomer(int customer_id) {
        ResultSet res;
        List<Loan> loans = new ArrayList<>();

        try {
            createConnection();
            res = statement.executeQuery("SELECT loans.id, book_id, customer_id, begin_date, expiration_date, completed FROM loans, books WHERE customer_id='" + customer_id + "' AND book_id=books.id");

            while (res.next()) {
                loans.add(new Loan(res.getInt("id"), res.getString("book_id"), customer_id, res.getDate("begin_date"), res.getDate("expiration_date"), res.getBoolean("completed")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnection();

        return loans;
    }


    /**
     * Returns a list of loans from some book
     * @param bookId The book's id
     * @return A list of loans from some book
     */
    public static List<Loan> getLoansByBookId(String bookId) {
        ResultSet res;
        Loan loan;
        ArrayList<Loan> loans = new ArrayList<>();

        try {
            createConnection();
            res = statement.executeQuery("SELECT loans.id, customer_id, begin_date, expiration_date, completed FROM loans, books WHERE loans.book_id=books.id");

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


    /**
     * Returns a list of all loans
     * @return A list of all loans
     */
    public static List<Loan> getLoans() {
        ResultSet res;
        Loan loan;
        ArrayList<Loan> loans = new ArrayList<>();

        try {
            createConnection();
            res = statement.executeQuery("SELECT loans.id, book_id, customer_id, begin_date, expiration_date, completed FROM loans");

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


    /**
     * Returns a list of customers whose last name matches the name
     * @param name The name used as filter
     * @return A list of matching customers
     */
    public static List<Customer> getCustomers(String name) {
        ResultSet res;
        Customer customer;
        ArrayList<Customer> customers = new ArrayList<>();

        try {
            createConnection();
            res = statement.executeQuery("SELECT * FROM customers WHERE last_name like '%" + name + "%'");

            while (res.next()) {
                customer = new Customer(res.getInt("id"), res.getString("first_name"), res.getString("last_name"), res.getString("tel"), res.getString("email"), res.getString("address"), res.getInt("loan_count"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnection();

        return customers;
    }


    /**
     * Returns a list of all the customers
     * @return A list of all the customers
     */
    public static List<Customer> getAllCustomers() {
        ResultSet res;
        Customer customer;
        ArrayList<Customer> customers = new ArrayList<>();

        try {
            createConnection();
            res = statement.executeQuery("SELECT * FROM customers");

            while (res.next()) {
                customer = new Customer (
                        res.getInt("id"),
                        res.getString("first_name"),
                        res.getString("last_name"),
                        res.getString("tel"),
                        res.getString("email"),
                        res.getString("address"),
                        res.getInt("loan_count")
                );
                customers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnection();

        return customers;
    }


    /**
     * Returns a list of all ongoing loans
     * @return A list of all ongoing loans
     */
    public static List<Loan> getOngoingLoans () {
        ResultSet res;
        Loan loan;
        ArrayList<Loan> loans = new ArrayList<>();

        try {
            createConnection();
            res = statement.executeQuery("SELECT loans.id, book_id, customer_id, begin_date, expiration_date, completed FROM loans WHERE completed='0'");

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


    /**
     * Adds a book in the database
     * @param ID The book's id
     * @param stock The amount of books currently available
     * @param total The total amount of books once all books have been returned
     */
    public static void addBook (String ID, int stock, int total) {
        try {
            createConnection();
            statement.execute("INSERT INTO books (id, quantity, stock) VALUES ('" + ID + "', '" + total + "', '" + stock + "')");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnection();
    }


    /**
     * Adds a loan to the database
     * @param book_id The book's id
     * @param customer_id The customer's id
     * @param expirationDate The date of which the book must be returned
     */
    public static void addLoan (String book_id, int customer_id, String expirationDate) {
        try {
            createConnection();
            statement.execute("INSERT INTO loans (book_id, customer_id, expiration_date) VALUES ('" + book_id + "', '" + customer_id + "', '" + expirationDate + "')");
            statement.execute("UPDATE books SET stock=stock-1 WHERE id='" + book_id + "'");
            statement.execute("UPDATE customers SET loan_count=loan_count+1 WHERE id='" + customer_id + "'");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnection();
    }


    /**
     * Adds a customer to the database
     * @param firstname The customer's first name
     * @param lastname The customer's last name
     * @param tel The customer's phone number
     * @param email The customer's email
     * @param address The customer's address
     * @throws SQLException Thrown if the customer already exists or if one of the fields is incorrect
     */
    public static void addCustomer (String firstname, String lastname, String tel, String email, String address) throws SQLException {
        try {
            createConnection();
            statement.execute("INSERT INTO customers (`last_name`, `first_name`, `tel`, `email`, `address`) VALUES ('" + lastname + "', '" + firstname + "', '" + tel + "', '" + email + "', '" + address + "')");

        } catch (SQLException e) {
            closeConnection();
            // Checks if MySQL indicates a unique constraint violation
            if (e.getErrorCode() == 1062) {
                throw new SQLException("L'email et/ou le numéro de téléphone est déjà utilisé");

            } else {
                throw new SQLException("Une erreur s'est produite");
            }
        }

        closeConnection();
    }


    /**
     * Adds a librarian to the database
     * @param login The librarian's login
     * @param lastName The librarian's last name
     * @param firstName The librarian's first name
     * @param password The librarian's password
     * @throws SQLException Thrown if the librarian already exist in the database
     */
    public static void addLibrarian (String login, String lastName, String firstName, String password) throws SQLException {
        try {
            createConnection();
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            statement.execute("INSERT INTO librarians (`login`, `last_name`, `first_name`, `password`) VALUES ('" + login + "', '" + lastName + "', '" + firstName + "', '" + hashedPassword +"')");

        } catch (SQLException e) {
            closeConnection();
            // Checks if MySQL indicates a unique constraint violation
            if (e.getErrorCode() == 1062) {
                throw new SQLException("Ce login est déjà utilisé");

            } else {
                throw new SQLException("Une erreur s'est produite");
            }
        }

        closeConnection();
    }


    /**
     * Update the customer's first name and last name
     * @param id The customer's id
     * @param firstName The customer's new first name
     * @param lastName The customer's new last name
     */
    public static void updateCustomer (int id, String lastName, String firstName) {
        try {
            createConnection();
            statement.execute("UPDATE customers SET last_name='" + lastName + "', first_name='" + firstName + "' WHERE id='" + id + "'");

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    /**
     * Updates all the customer's fields
     * @param id The customer's id
     * @param lastName The customer's last name
     * @param firstName The customer's first name
     * @param tel The customer's phone number
     * @param email The customer's email
     * @param address The customer's address
     * @throws SQLException Thrown if one of the fields already exist in the database
     */
    public static void updateEntireCustomer (int id, String lastName, String firstName, String tel, String email, String address) throws SQLException {

        try {
            createConnection();
            statement.execute("UPDATE customers SET last_name='" + lastName + "', first_name='" + firstName + "', tel='" + tel + "', email='" + email + "', address='" + address + "' WHERE id='" + id + "'");

        } catch (SQLException e) {
            closeConnection();
            // Checks if MySQL indicates a unique constraint violation
            if (e.getErrorCode() == 1062) {
                throw new SQLException("L'email et/ou le numéro de téléphone est déjà utilisé");

            } else {
                throw new SQLException("Une erreur s'est produite");
            }
        }

        closeConnection();
    }


    /**
     * Updates a loan's status
     * @param id The loan id
     * @param completed Whether the book has been given back or not
     */
    public static void updateLoan (int id, boolean completed) {
        int value = (completed ? 1 : 0);

        try {
            createConnection();
            statement.execute("UPDATE loans SET completed='" + value + "' WHERE id='" + id + "'");
            statement.execute("UPDATE books SET stock=stock+1 WHERE id=(SELECT book_id FROM loans WHERE id =" + id + ")");

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    /**
     * Updates a loan's expiration date
     * @param id The loan's id
     * @param expirationDate The new expiration date
     */
    public static void updateExpirationDate(String id, String expirationDate) {
        try {
            createConnection();
            statement.execute("UPDATE loans SET expiration_date='" + expirationDate + "' WHERE id='" + id + "'");

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    /**
     * Updates a book's stock
     * @param id The id of the book to be updated
     * @param stock The book's new stock
     */
    public static void updateBookStock (String id, int stock) {
        try {
            createConnection();
            statement.execute("UPDATE books SET stock='" + stock + "' WHERE id='" + id + "'");

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    /**
     * Updates a book's total amount
     * @param id The id of the book to be updated
     * @param total The book's new total
     */
    public static void updateBookTotal (String id, int total) {
        try {
            createConnection();
            statement.execute("UPDATE books SET quantity='" + total + "' WHERE id='" + id + "'");

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    /**
     * Returns a list of the most popular books in the database
     * @return A list of the most popular books in the database
     */
    public static List<Book> getMostPopularBooks() {
        ResultSet res;

        List<Book> books = new ArrayList<>();

        try {
            createConnection();
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

    /**
     * Returns a list of the most popular books in the database since some date
     * @param date The date to start the research from
     * @return A list of the most popular books in the database since some date
     */
    public static List<Book> getMostPopularBooksSince(String date) {
        ResultSet res;

        List<Book> books = new ArrayList<>();

        try {
            createConnection();
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


    /**
     * Returns a list of the loans that have expired
     * @return A list of the loans that have expired
     */
    public static List<Loan> getExpiredLoans() {
        ResultSet res;
        List<Loan> loans = new ArrayList<>();

        try {
            createConnection();
            res = statement.executeQuery("SELECT * FROM loans WHERE completed = 0 AND expiration_date < CURRENT_DATE");

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


    /**
     * Tries to log in the librarian with the login and password provided
     * @param login The librarian's login
     * @param password The librarian's password
     * @return A new Librarian object
     * @throws NoSuchElementException Thrown if the librarian is missing in the database
     * @throws IncorrectPasswordException Thrown if the librarian's password is incorrect
     */
    public static Librarian librarianAuthentication(String login, String password) throws IncorrectPasswordException, NoSuchElementException {
        ResultSet res;
        Librarian librarian = null;

        try {
            createConnection();
            res = statement.executeQuery("SELECT * FROM librarians WHERE login='" + login + "'");

            if (!res.next()) {
                closeConnection();
                throw new NoSuchElementException("Nom d'utilisateur introuvable");
            }

            String hashedPassword = res.getString("password");

            if (BCrypt.checkpw(password, hashedPassword))
                librarian = new Librarian(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));

            else {
                closeConnection();
                throw new IncorrectPasswordException("Mot de passe incorrect");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnection();

        return librarian;
    }


    /**
     * Returns a new librarian corresponding to the login
     * @param login The login to be used as filter
     * @return A new librarian corresponding to the login
     */
    public static Librarian getLibrarian(String login) {
        ResultSet res;
        Librarian librarian = null;

        try {
            createConnection();
            res = statement.executeQuery("SELECT * FROM librarians WHERE login='" + login + "'");

            if (res.next()) {
                librarian = new Librarian(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnection();

        return librarian;
    }


    /**
     * Returns the librarian's login
     * @param librarian The librarian
     * @return The librarian's login
     */
    public static String getLibrarianLogin(Librarian librarian) {
        ResultSet res;
        String login = null;

        try {
            createConnection();
            String query = "SELECT login FROM librarians WHERE id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(librarian.getId()));
            res = statement.executeQuery();

            if (res.next()) {
                login = res.getString("login");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            closeConnection();
        }

        return login;
    }

    /**
     * Returns a list of customers corresponding to the search filter
     * @param filter The filter to be used for research
     * @return A list of customers corresponding to the search filter
     */
    public static List<Customer> getCustomersByFilter(CustomerFilter filter) {
        List<Customer> customers = new ArrayList<>();
        ResultSet res;

        Integer id = filter.getId();
        String firstName = filter.getFirstName();
        String lastName = filter.getLastName();
        String email = filter.getEmail();
        String tel = filter.getTel();
        String address = filter.getAddress();
        Integer inf = filter.getInf();
        Integer sup = filter.getSup();

        List<String> conditions = new ArrayList<>();

        // Check if any filtering conditions are specified
        if (id !=null) {
            conditions.add("id = " + id);

        }
        if (firstName != null && !firstName.isEmpty()) {
            conditions.add("first_name LIKE '%" + firstName + "%'");

        }
        if (lastName != null && !lastName.isEmpty()) {
            conditions.add("last_name LIKE '%" + lastName + "%'");

        }
        if (email != null && !email.isEmpty()) {
            conditions.add("email LIKE '%" + email + "%'");

        }
        if (tel != null && !tel.isEmpty()) {
            conditions.add("tel LIKE '%" + tel + "%'");

        }
        if (address != null && !address.isEmpty()) {
            conditions.add("address LIKE '%" + address + "%'");

        }
        if (inf != null) {
            conditions.add("loan_count < " + inf);

        }
        if (sup != null) {
            conditions.add("loan_count > " + sup);

        }

        String query = "SELECT * FROM customers";
        if (!conditions.isEmpty()) {
            query += " WHERE " + String.join(" AND ", conditions);
        }

        try {
            createConnection();
            res = statement.executeQuery(query);

            while (res.next()) {
                Customer customer = new Customer(res.getInt("id"), res.getString("first_name"), res.getString("last_name"), res.getString("tel"), res.getString("email"), res.getString("address"), res.getInt("loan_count"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnection();

        return customers;
    }


    /**
     * Returns a list of loans corresponding to the search filter
     * @param filter The filter to be used for research
     * @return A list of loans corresponding to the search filter
     */
    public static List<Loan> getLoansByFilter (LoanFilter filter) {
        List<Loan> loans = new ArrayList<>();
        ResultSet res;

        Integer customerID = filter.getCustomerID();
        String bookID = filter.getBookID();
        boolean completed = filter.isCompleted();
        boolean expired = filter.isExpired();

        // Handle the case where customerID is null, bookID is empty, and completed is false
        if (customerID == null && bookID.isEmpty() && !completed) {
            if (!expired) {
                System.out.println(getOngoingLoans());
                //return getOngoingLoans();
            } else {
                return getExpiredLoans();
            }
        }

        // Start building the query
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM loans WHERE ");

        // Add completed condition
        queryBuilder.append("completed = ").append(completed ? 1 : 0);

        // Add customerID condition if provided
        if (customerID != null) {
            queryBuilder.append(" AND customer_id = ").append(customerID);
        }

        // Add bookID condition if provided
        if (bookID != null && !bookID.isEmpty()) {
            queryBuilder.append(" AND book_id LIKE '%").append(bookID).append("%'");
        }

        // Add expired condition if provided
        if (expired) {
            queryBuilder.append(" AND expiration_date < CURRENT_DATE");
        } else {
            queryBuilder.append(" AND expiration_date >= CURRENT_DATE");
        }

        String query = queryBuilder.toString();

        try {
            createConnection();
            res = statement.executeQuery(query);

            while (res.next()) {
                Loan loan = new Loan(
                        res.getInt("id"),
                        res.getString("book_id"),
                        res.getInt("customer_id"),
                        res.getDate("begin_date"),
                        res.getDate("expiration_date"),
                        res.getBoolean("completed")
                );
                loans.add(loan);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnection();
        }
        return loans;
    }


    /**
     * Returns a list of semi-empty books from the database
     * @return A list of semi-empty books from the database
     */
    public static List<Book> getAllBooks () {
        List<Book> books = new ArrayList<>();
        ResultSet res;

        try {
            createConnection();
            res = statement.executeQuery("SELECT * from books");

            while (res.next()) {
                Book book = new Book(res.getString("id"), res.getInt("quantity"), res.getInt("stock"));
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnection();

        return books;
    }
}
