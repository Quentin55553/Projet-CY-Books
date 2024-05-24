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


public class DBHandler {
    private static Connection connection;
    private static Statement statement;


    private static void createFirstConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "cytech0001");
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost/CY-Books", "root", "cytech0001");
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
            if (!res.next())
                throw new NoSuchElementException("No such book with id " + id + " in database");

            book = new Book(id, res.getInt("quantity"), res.getInt("stock"));


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        closeConnection();

        return book;
    }


    public static List<Loan> getLoansByCustomer(int customer_id) {
        createConnection();
        ResultSet res;
        List<Loan> loans = new ArrayList<>();

        try {
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


    public static List<Loan> getLoansByBookId(String bookId) {
        createConnection();
        ResultSet res;
        Loan loan;
        ArrayList<Loan> loans = new ArrayList<>();

        try {
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


    public static List<Loan> getLoans() {
        createConnection();
        ResultSet res;
        Loan loan;
        ArrayList<Loan> loans = new ArrayList<>();

        try {
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


    public static List<Customer> getCustomers(String name) {
        createConnection();
        ResultSet res;
        Customer customer;
        ArrayList<Customer> customers = new ArrayList<>();

        try {
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


    public static List<Customer> getAllCustomers() {
        createConnection();
        ResultSet res;
        Customer customer;
        ArrayList<Customer> customers = new ArrayList<>();

        try {
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


    public static List<Loan> getOngoingLoans () {
        createConnection();
        ResultSet res;
        Loan loan;
        ArrayList<Loan> loans = new ArrayList<>();

        try {
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


    public static void addBook (String ID, int stock, int total) {
        createConnection();

        try {
            statement.execute("INSERT INTO books (id, quantity, stock) VALUES ('" + ID + "', '" + total + "', '" + stock + "')");

        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    public static void addLoan (String book_id, int customer_id, String expirationDate) {
        createConnection();

        try {
            statement.execute("INSERT INTO loans (book_id, customer_id, expiration_date) VALUES ('" + book_id + "', '" + customer_id + "', '" + expirationDate + "')");
            statement.execute("UPDATE books SET stock=stock-1 WHERE id='" + book_id + "'");
            statement.execute("UPDATE customers SET loan_count=loan_count+1 WHERE id='" + customer_id + "'");
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
                throw new SQLException("L'email et/ou le numéro de téléphone est déjà utilisé");

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
                throw new SQLException("Ce login est déjà utilisé");

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
            statement.execute("UPDATE books SET stock=stock+1 WHERE id=(SELECT book_id FROM loans WHERE id =" + id + ")");
        } catch (SQLException e) {
            System.out.println(e);
        }

        closeConnection();
    }


    public static void updateExpirationDate(String id, String expirationDate) {
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


    public static List<Loan> getExpiredLoans() {
        createConnection();
        ResultSet res;
        List<Loan> loans = new ArrayList<>();

        try {
            res = statement.executeQuery("SELECT * FROM loans WHERE expiration_date < CURRENT_DATE");

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

    public static List<Customer> getCustomersByFilter(CustomerFilter filter) {
        createConnection();
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

        /*
        String query = "SELECT * FROM customers WHERE";

        if (id != null)
            query += "id='"+id+"'";
        }
        query += "first_name LIKE '%" + firstName + "%' AND " +
                    "last_name LIKE '%" + lastName + "%' AND " +
                    "tel LIKE '%" + tel + "%' AND " +
                    "email LIKE '%" + email + "%' AND " +
                    "address LIKE '%" + address + "%'";

         if (inf != null)
             query += "AND id IN (SELECT customer_id FROM loans GROUP by customer_id HAVING COUNT(customer_id) < " + inf + ")";
         if (sup != null)
             query += "AND id IN (SELECT customer_id FROM loans GROUP by customer_id HAVING COUNT(customer_id) > " + sup + ")";*/

        List<String> conditions = new ArrayList<>();

        // Check if any filtering conditions are specified
        if(id !=null){
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
            conditions.add("id IN (SELECT customer_id FROM loans GROUP BY customer_id HAVING COUNT(customer_id) < " + inf + ")");
        }
        if (sup != null) {
            conditions.add("id IN (SELECT customer_id FROM loans GROUP BY customer_id HAVING COUNT(customer_id) > " + sup + ")");
        }

        String query = "SELECT * FROM customers";
        if (!conditions.isEmpty()) {
            query += " WHERE " + String.join(" AND ", conditions);
        }


        try {
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

    public static List<Book> getBooksByFilter (BookFilter filter) {
        createConnection();
        List<Book> books = new ArrayList<>();
        ResultSet res;

        try {
            res = statement.executeQuery("SELECT * from books WHERE id='"+ filter.getId() +"'");

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

    public static int getLoanCount(int customerId) throws NoSuchElementException {
        createConnection();
        ResultSet res;
        int count = 0;
        try {
            res = statement.executeQuery("SELECT loan_count FROM customers WHERE id=" + customerId);
            if (res.next())
                count = res.getInt("c");
            else
                throw new NoSuchElementException("No customer with id " + customerId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        closeConnection();
        return count;
    }
}
