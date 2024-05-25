package org.openjfx.cybooks.data;

import org.openjfx.cybooks.API.APIErrorException;
import org.openjfx.cybooks.API.APIHandler;
import org.openjfx.cybooks.API.QueryParameterException;
import org.openjfx.cybooks.API.SearchResult;
import org.openjfx.cybooks.database.BookFilter;
import org.openjfx.cybooks.database.CustomerFilter;
import org.openjfx.cybooks.database.DBHandler;
import org.openjfx.cybooks.UserInput.IncorrectPasswordException;
import org.openjfx.cybooks.database.LoanFilter;

import java.sql.SQLException;
import java.util.*;

/**
 * The Core class represents the core of the app's data model.
 * It is used as an intermediate between the front-end and the back-end,
 * where the font-end is what the user see and the back-end is the database and the API
 */
public class Core {

    /**
     * Returns a filled Book from its id in database (Internet connection required)
     * @param id The id in database
     * @return A filled Book with all the information available
     * @throws NoSuchElementException Thrown if the book is missing on the servers
     */
    public static Book getBook(String id) throws NoSuchElementException {
        APIHandler API = new APIHandler();
        String bookLink = "https://gallica.bnf.fr/ark:/" + id;
        Book book = null;

        try {
            book = DBHandler.getBook(id);
        } catch (NoSuchElementException e) {
            // if the book doesn't exist in database, it may exist online
            System.out.println(e.getMessage());
            book = new Book(id, 0, 0);
        }



        // get data from API
        try {
            API.generateQueryStandard("", "", "", bookLink, "", "", "");
            API.exec();

            if (API.getNumberOfResults() == 0) {
                throw new NoSuchElementException("No such book on servers");
            } else {

                SearchResult result = API.getResults().get(0);
                book.setTitle(result.getTitle());
                book.setAuthors(result.getAuthors());
                book.setDate(result.getDate());
                book.setDescription(result.getDescription());
                book.setLanguage(result.getLanguage());
                book.setSubjects(result.getSubjects());
                book.setImageLink(result.getImageLink());
                book.setPublisher(result.getPublisher());
            }

        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        } catch (QueryParameterException e) {
            System.out.println(e.getMessage());
        } catch (APIErrorException e) {
            System.out.println(e.getMessage());
        }
        return book;
    }

    /**
     * Returns a list of customers corresponding to the search filter
     * @param filter The filter to be used for research
     * @return A list of customers corresponding to the search filter
     */
    public static List<Customer> getCustomersByFilter(CustomerFilter filter) {
        return DBHandler.getCustomersByFilter(filter);
    }

    /**
     * Returns a list of all the books that are in the database (Internet connection required)
     * @return A list of books filled with all the information available
     */
    public static List<Book> getAllBooks () {
        List<Book> books = new ArrayList<>();
        List<Book> DBBooks = DBHandler.getAllBooks();

        for (Book b : DBBooks) {
            books.add(Core.getBook(b.getId()));
        }
        return books;
    }

    /**
     * Returns a list of filled books corresponding to the filter
     * @param filter The filter to be used for research
     * @return A list of books filled with all the information available
     */
    public static List<Book> getBooksByFilter(BookFilter filter) {

        List<Book> list = new ArrayList<>();
        List<Book> APIList = new ArrayList<>();
        List<SearchResult> results;
        APIHandler API = new APIHandler();
        String bookLink = "";

        if (filter.isEmpty() && filter.isDatabaseOnly())
            return getAllBooks();


        if (!Objects.equals(filter.getId(), ""))
            bookLink = "https://gallica.bnf.fr/ark:/" + filter.getId();

        try {
            API.generateQueryStandard(filter.getTitle(),
                    filter.getAuthor(),
                    filter.getDate(),
                    bookLink,
                    filter.getEditor(),
                    "",
                    filter.getTheme());
            API.exec();
            results = API.getResults();
            for (SearchResult s : results) {
                String bookId = s.getIdentifier();
                bookId = bookId.replace("https://gallica.bnf.fr/ark:/", "");
                bookId = bookId.replace("/date", "");
                APIList.add(new Book(bookId,
                        s.getTitle(),
                        s.getAuthors(),
                        s.getDate(),
                        s.getPublisher(),
                        s.getLanguage(),
                        s.getDescription(),
                        s.getSubjects(),
                        s.getImageLink()));
            }

        } catch (APIErrorException e) {
            System.out.println(e.getMessage());
        } catch (QueryParameterException e) {
            System.out.println(e.getMessage());
        }

        for (Book b : APIList) {
            try {
                String id = b.getId();
                id = id.replace("https://gallica.bnf.fr/ark:/", "");
                id = id.replace("/date", "");
                Book dbBook = DBHandler.getBook(id);
                b.setStock(dbBook.getStock());
                b.setTotal(dbBook.getTotal());
                list.add(b);
            } catch (NoSuchElementException e) {
                if (!filter.isDatabaseOnly()) {
                    b.setStock(0);
                    b.setTotal(0);
                    list.add(b);
                } else
                    System.out.println("Some books couldn't be found online");
            }
        }
        return list;
    }


    /**
     * Returns the list of all loans from a certain customer
     * @param customerId The customer's id
     * @return The list of all loans from a certain customer
     * @throws NoSuchElementException Thrown if the customer's id is missing in the database
     */
    public static List<Loan> getLoans(int customerId) throws NoSuchElementException {
        return DBHandler.getLoansByCustomer(customerId);
    }

    /**
     * Returns the list of all loans from a certain book
     * @param book_id The book's id
     * @return The list of all loans from a certain book
     * @throws NoSuchElementException Thrown if the book's id is missing in the database
     */
    public static List<Loan> getLoans(String book_id) throws NoSuchElementException {
        return DBHandler.getLoansByBookId(book_id);
    }

    /**
     * Returns a list of all loans
     * @return A list of all loans
     */
    public static List<Loan> getLoans() {
        return DBHandler.getLoans();
    }

    /**
     * Returns a list of all ongoing loans
     * @return A list of all ongoing loans
     */
    public static List<Loan> getOngoingLoans() {
        return DBHandler.getOngoingLoans();
    }

    /**
     * Returns a list of loans corresponding to the filter
     * @param filter The filter to be used
     * @return A list of loans corresponding to the filter
     */
    public static List<Loan> getLoansByFilter(LoanFilter filter) {
        return DBHandler.getLoansByFilter(filter);
    }

    /**
     * Returns a list of customers whose last name matches the name
     * @param name The name used as filter
     * @return A list of matching customers
     */
    public static List<Customer> getCustomers(String name) {
        return DBHandler.getCustomers(name);
    }

    /**
     * Adds a book in the database
     * @param id The book's id
     * @param stock The amount of books currently available
     * @param total The total amount of books once all books have been returned
     */
    public static void addBook(String id, int stock, int total) {
        DBHandler.addBook(id, stock, total);
    }

    /**
     * Adds a loan to the database
     * @param book The book to be lent
     * @param customer The customer who loans the book
     * @param expirationDate The date of which the book must be returned
     */
    public static void addLoan(Book book, Customer customer, String expirationDate) {
        DBHandler.addLoan(book.getId(), customer.getId(), expirationDate);
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
    public static void addCustomer(String firstname, String lastname, String tel, String email, String address) throws SQLException {
        DBHandler.addCustomer(firstname, lastname, tel, email, address);
    }

    /**
     * Update the customer's first name and last name
     * @param id The customer's id
     * @param firstName The customer's new first name
     * @param lastName The customer's new last name
     */
    public static void updateCustomer(int id, String firstName, String lastName) {
        DBHandler.updateCustomer(id, lastName, firstName);
    }

    /**
     * Updates a loan's status
     * @param id The loan id
     * @param completed Whether the book has been given back or not
     */
    public static void updateLoan(int id, boolean completed) {
        DBHandler.updateLoan(id, completed);
    }

    /**
     * Updates a loan's expiration date
     * @param id The loan's id
     * @param expirationDate The new expiration date
     */
    public static void updateLoanExpirationDate(String id, String expirationDate) {
        DBHandler.updateExpirationDate(id, expirationDate);
    }

    /**
     * Updates a book's stock
     * @param book The book to be updated
     * @param stock The book's new stock
     */
    public static void updateBookStock(Book book, int stock) {
        book.setStock(stock);
        DBHandler.updateBookStock(book.getId(), stock);
    }

    /**
     * Updates a book's total amount
     * @param book The book to be updated
     * @param total The book's new total
     */
    public static void updateBookTotal(Book book, int total) {
        book.setTotal(total);
        DBHandler.updateBookTotal(book.getId(), total);
    }

    /**
     * Returns a list of the most popular books in the database
     * @return A list of the most popular books in the database
     */
    public static List<Book> getMostPopularBooks() {
        return DBHandler.getMostPopularBooks();
    }

    /**
     * Returns a list of the most popular books in the database since some date
     * @param date The date to start the research from
     * @return A list of the most popular books in the database since some date
     */
    public static List<Book> getMostPopularBooksSince(String date) {
        return DBHandler.getMostPopularBooksSince(date);
    }

    /**
     * Returns a list of the loans that have expired
     * @return A list of the loans that have expired
     */
    public static List<Loan> getExpiredLoans () {
        return DBHandler.getExpiredLoans();
    }

    /**
     * Tries to log in the librarian with the login and password provided
     * @param login The librarian's login
     * @param password The librarian's password
     * @return A new Librarian object
     * @throws NoSuchElementException Thrown if the librarian is missing in the database
     * @throws IncorrectPasswordException Thrown if the librarian's password is incorrect
     */
    public Librarian librarianAuthentication(String login, String password) throws NoSuchElementException, IncorrectPasswordException {
        return DBHandler.librarianAuthentication(login, password);
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
        DBHandler.addLibrarian(login, lastName, firstName, password);
    }

}
