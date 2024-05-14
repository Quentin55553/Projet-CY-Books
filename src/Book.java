public class Book extends Core{

    protected int ISBN;
    protected String title;
    protected String author;
    protected int stock;
    protected int total;

    protected int getISBN(){
        return ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable(){
        return stock > 0;
    }
}
