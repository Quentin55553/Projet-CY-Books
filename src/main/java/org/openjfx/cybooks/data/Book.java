package org.openjfx.cybooks.data;

import java.util.Objects;

public class Book {

    private final int id;
    private final String ISBN;
    private String title;
    private String author;
    private int total;
    private int stock;

    public Book (int id, String ISBN, int total, int stock) {
        this.id = id;
        this.ISBN = ISBN;
        this.stock = stock;
        this.total = total;
    }

    public Book(int id, String ISBN, String title, String author, int total, int stock) {
        this.id = id;
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.total = total;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getISBN() {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTotal(int total) {
        this.total = total;
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
