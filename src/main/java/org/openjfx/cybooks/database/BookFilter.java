package org.openjfx.cybooks.database;

import org.openjfx.cybooks.data.Book;

import java.util.Objects;

public class BookFilter {
    private String title;
    private String author;
    private String theme;
    private String date;
    private String id;
    private String editor;
    private boolean databaseOnly;

    public BookFilter(String title, String author, String theme, String date, String id, String editor, boolean databaseOnly) {
        this.title = title;
        this.author = author;
        this.theme = theme;
        this.date = date;
        this.id = id;
        this.editor = editor;
        this.databaseOnly = databaseOnly;
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

    public boolean isDatabaseOnly() {return databaseOnly;}

    public boolean isEmpty() {
        return Objects.equals(title, "")
                && Objects.equals(author, "")
                && Objects.equals(theme, "")
                && Objects.equals(date, "")
                && Objects.equals(id, "")
                && Objects.equals(editor, "");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public void setDatabaseOnly(boolean databaseOnly) {
        this.databaseOnly = databaseOnly;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BookFilter{");

        if (title != null && !title.isEmpty()) {
            sb.append("title='").append(title).append('\'');
        } else {
            sb.append("title=unspecified");
        }

        if (author != null && !author.isEmpty()) {
            sb.append(", author='").append(author).append('\'');
        } else {
            sb.append(", author=unspecified");
        }

        if (theme != null && !theme.isEmpty()) {
            sb.append(", theme='").append(theme).append('\'');
        } else {
            sb.append(", theme=unspecified");
        }

        if (date != null && !date.isEmpty()) {
            sb.append(", date='").append(date).append('\'');
        } else {
            sb.append(", date=unspecified");
        }

        if (id != null && !id.isEmpty()) {
            sb.append(", id=").append(id);
        } else {
            sb.append(", id=unspecified");
        }

        if (editor != null && !editor.isEmpty()) {
            sb.append(", editor='").append(editor).append('\'');
        } else {
            sb.append(", editor=unspecified");
        }
        sb.append(", databaseOnly=").append(databaseOnly);
        sb.append('}');
        return sb.toString();
    }


}
