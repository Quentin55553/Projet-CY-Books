package org.openjfx.cybooks.data;

import java.util.Objects;


public class Book {
    private final String id;
    private String title;
    private String author;
    private int total;
    private int stock;


    public Book (String id, int total, int stock) {
        this.id = id;
        this.stock = stock;
        this.total = total;
    }


    public Book(String id, String title, String author, int total, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.total = total;
        this.stock = stock;
    }


    public String getId() {
        return id;
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
        return id == book.id;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public String toString () {
        return "id: " + id
            + "\ntitle: " + title
            + "\nauthor: " + author
            + "\nstock: " + stock
            + "\ntotal: " + total;
    }
}
