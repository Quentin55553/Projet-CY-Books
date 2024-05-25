package org.openjfx.cybooks.database;

import org.openjfx.cybooks.data.Book;

import java.util.Objects;

public class BookFilter {

    /**
     * The book's title
     */
    private String title;

    /**
     * The book's author
     */
    private String author;

    /**
     * The book's theme
     */
    private String theme;

    /**
     * The book's date
     */
    private String date;

    /**
     * The book's id on the database
     */
    private String id;

    /**
     * The book's editor
     */
    private String editor;

    /**
     * Whether the research must be only on the database or not
     */
    private boolean databaseOnly;

    /**
     * Constructor for the BookFilter object
     * @param title The book's title
     * @param author The book's author
     * @param theme The book's theme
     * @param date The book's date
     * @param id The book's id on the database
     * @param editor The book's editor
     * @param databaseOnly Whether the research must be only on the database or not
     */

    public BookFilter(String title, String author, String theme, String date, String id, String editor, boolean databaseOnly) {
        this.title = title;
        this.author = author;
        this.theme = theme;
        this.date = date;
        this.id = id;
        this.editor = editor;
        this.databaseOnly = databaseOnly;
    }

    /**
     * Returns the title filter
     * @return The title filter
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author filter
     * @return The author filter
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the theme filter
     * @return The theme filter
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Returns the date filter
     * @return The date filter
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the id filter
     * @return The id filter
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the editor filter
     * @return The editor filter
     */
    public String getEditor() {
        return editor;
    }

    /**
     * Returns the database only filter
     * @return The database only filter
     */
    public boolean isDatabaseOnly() {
        return databaseOnly;
    }

    /**
     * Returns a boolean whether the filter is empty or not
     * @return A boolean whether the filter is empty or not
     */
    public boolean isEmpty() {
        return Objects.equals(title, "")
                && Objects.equals(author, "")
                && Objects.equals(theme, "")
                && Objects.equals(date, "")
                && Objects.equals(id, "")
                && Objects.equals(editor, "");
    }

    /**
     * Setter for the title filter
     * @param title The title filter
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter for the author filter
     * @param author The author filter
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Setter for the theme filter
     * @param theme The theme filter
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Setter for the date filter
     * @param date The date filter
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Setter for the id filter
     * @param id The id filter
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter for the editor filter
     * @param editor The editor filter
     */
    public void setEditor(String editor) {
        this.editor = editor;
    }

    /**
     * Setter for the database  filter
     * @param databaseOnly The database  filter
     */
    public void setDatabaseOnly(boolean databaseOnly) {
        this.databaseOnly = databaseOnly;
    }

    /**
     * Returns a string representation of the object
     * @return A string representation of the object
     */
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
