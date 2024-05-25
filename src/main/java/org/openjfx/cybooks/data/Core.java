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


public class Core {

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

    public static List<Customer> getCustomersByFilter(CustomerFilter filter) {
        return DBHandler.getCustomersByFilter(filter);
    }


    public static List<Book> getAllBooks () {
        List<Book> books = new ArrayList<>();
        List<Book> DBBooks = DBHandler.getAllBooks();

        for (Book b : DBBooks) {
            books.add(Core.getBook(b.getId()));
        }
        return books;
    }

    public static List<Book> getBooksByFilter(BookFilter filter) {

        List<Book> list = new ArrayList<>();
        List<Book> APIList = new ArrayList<>();
        List<SearchResult> results;
        APIHandler API = new APIHandler();
        String bookLink = "";

        if (filter.isEmpty())
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



    public static List<Loan> getLoans(int customerId) throws NoSuchElementException {
        return DBHandler.getLoansByCustomer(customerId);
    }

    public static List<Loan> getLoans(String book_id) throws NoSuchElementException {
        return DBHandler.getLoansByBookId(book_id);
    }

    public static List<Loan> getLoans() throws NoSuchElementException {
        return DBHandler.getLoans();
    }

    public static List<Loan> getOngoingLoans() throws NoSuchElementException {
        return DBHandler.getOngoingLoans();
    }

    public static List<Loan> getLoansByFilter(LoanFilter filter) throws NoSuchElementException {
        return DBHandler.getLoansByFilter(filter);
    }

    public static List<Customer> getCustomers(String name) {
        return DBHandler.getCustomers(name);
    }

    public static void addBook(String id, int stock, int total) {
        DBHandler.addBook(id, stock, total);
    }

    public static void addLoan(Book book, Customer customer, String expirationDate) {
        DBHandler.addLoan(book.getId(), customer.getId(), expirationDate);
    }


    public static void addCustomer(String firstname, String lastname, String tel, String email, String address) throws SQLException {
        DBHandler.addCustomer(firstname, lastname, tel, email, address);
    }

    public static void updateCustomer(int id, String firstName, String lastName) {
        DBHandler.updateCustomer(id, lastName, firstName);
    }



    public static void updateLoan(int id, boolean completed) {
        DBHandler.updateLoan(id, completed);
    }

    public static void updateLoanExpirationDate(String id, String expirationDate) {
        DBHandler.updateExpirationDate(id, expirationDate);
    }


    public static void updateBookStock(Book book, int stock) {
        book.setStock(stock);
        DBHandler.updateBookStock(book.getId(), stock);
    }

    public static void updateBookTotal(Book book, int total) {
        book.setTotal(total);
        DBHandler.updateBookTotal(book.getId(), total);
    }

    public static List<Book> getMostPopularBooks() {
        return DBHandler.getMostPopularBooks();
    }

    public static List<Book> getMostPopularBooksSince(String date) {
        return DBHandler.getMostPopularBooksSince(date);
    }

    public static List<Loan> getExpiredLoans () {
        return DBHandler.getExpiredLoans();
    }

    public static void removeBook() {

    }

    public Librarian librarianAuthentication(String login, String password) throws NoSuchElementException, IncorrectPasswordException {
        return DBHandler.librarianAuthentication(login, password);
    }

    public static void addLibrarian (String login, String lastName, String firstName, String password) throws SQLException {
        DBHandler.addLibrarian(login, lastName, firstName, password);
    }

    public static int getLoanCount (int customerId) throws NoSuchElementException {
        return DBHandler.getLoanCount(customerId);
    }


}
