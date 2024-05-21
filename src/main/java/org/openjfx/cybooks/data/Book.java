package org.openjfx.cybooks.data;

import java.util.List;
import java.util.Objects;


public class Book {
    private final String id;
    private int total;
    private int stock;


    private String title;
    private List<String> authors;
    private String date;
    private String publisher;
    private String language;
    private String description;
    private List<String> subjects;
    private String imageLink;


    public Book (String id, int total, int stock) {
        this.id = id;
        this.stock = stock;
        this.total = total;
    }


    public Book(String id, String title, List<String> authors, String date, String publisher, String language, String description, List<String> subjects, String imageLink) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.date = date;
        this.publisher = publisher;
        this.language = language;
        this.description = description;
        this.subjects = subjects;
        this.imageLink = imageLink;
        this.stock = 0;
        this.total = 0;
    }

    public String getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public List<String> getAuthors() {
        return authors;
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


    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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
            + "\nauthors: " + authors
            + "\npublisher: " + publisher
            + "\ndescription: " + description
            + "\ndate: " + date
            + "\nsubjects: " + subjects
            + "\nlanguage: " + language
            + "\nimage link: " + imageLink
            + "\nstock: " + stock
            + "\ntotal: " + total;
    }
}
