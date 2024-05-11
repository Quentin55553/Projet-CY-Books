package org.openjfx.cybooks.data;

import java.util.Objects;

public class Book {
    private final int ISBN;
    private final String title;
    private final String author;
    private final int total;
    private int stock;


    public Book(int ISBN, String title, String author, int total, int stock) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.total = total;
        this.stock = stock;
    }

    public int getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getTotal() {
        return total;
    }

    public int getStock() {
        return stock;
    }

    public void setStock (int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return ISBN == book.ISBN;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }

    public String toString () {
        return "ISBN: " + ISBN
                + "\ntitle: " + title
                + "\nauthor: " + author
                + "\nstock: " + stock
                + "\ntotal: " + total;

    }
}
