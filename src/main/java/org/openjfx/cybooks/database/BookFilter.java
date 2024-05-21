package org.openjfx.cybooks.database;

public class BookFilter {
    private String title;
    private String author;
    private String theme;
    private String date;
    private String id;
    private String editor;

    public BookFilter(String title, String author, String theme, String date, String id, String editor) {
        this.title = title;
        this.author = author;
        this.theme = theme;
        this.date = date;
        this.id = id;
        this.editor = editor;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTheme() {
        return theme;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getEditor() {
        return editor;
    }
}
