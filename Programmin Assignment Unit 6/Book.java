/**
 * Book class - A specific type of LibraryItem for books
 * Extends LibraryItem with String type parameter
 */
public class Book extends LibraryItem<String> {
    
    /**
     * Constructor for creating a Book item
     * @param title The title of the book
     * @param author The author of the book
     * @param itemID Unique identifier for the book
     */
    public Book(String title, String author, String itemID) {
        super(title, author, itemID, "Book");
    }
}
