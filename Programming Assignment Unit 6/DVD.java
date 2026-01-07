/**
 * DVD class - A specific type of LibraryItem for DVDs
 * Extends LibraryItem with String type parameter
 */
public class DVD extends LibraryItem<String> {
    
    /**
     * Constructor for creating a DVD item
     * @param title The title of the DVD
     * @param author The producer/studio of the DVD
     * @param itemID Unique identifier for the DVD
     */
    public DVD(String title, String author, String itemID) {
        super(title, author, itemID, "DVD");
    }
}
