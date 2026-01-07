/**
 * Magazine class - A specific type of LibraryItem for magazines
 * Extends LibraryItem with String type parameter
 */
public class Magazine extends LibraryItem<String> {
    
    /**
     * Constructor for creating a Magazine item
     * @param title The title of the magazine
     * @param author The publisher of the magazine
     * @param itemID Unique identifier for the magazine
     */
    public Magazine(String title, String author, String itemID) {
        super(title, author, itemID, "Magazine");
    }
}
