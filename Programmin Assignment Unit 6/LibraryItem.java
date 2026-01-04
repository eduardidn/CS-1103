/**
 * Generic LibraryItem class that represents an item in the library catalog.
 * This class can be extended or used to create different types of library items.
 * 
 * @param <T> The type of the item (e.g., String for item type description)
 */
public class LibraryItem<T> {
    private String title;
    private String author;
    private String itemID;
    private T itemType;  // Generic type for additional flexibility
    
    /**
     * Constructor to create a new LibraryItem
     * @param title The title of the item
     * @param author The author/creator of the item
     * @param itemID The unique identifier for the item
     * @param itemType The type of the item (Book, DVD, Magazine, etc.)
     */
    public LibraryItem(String title, String author, String itemID, T itemType) {
        this.title = title;
        this.author = author;
        this.itemID = itemID;
        this.itemType = itemType;
    }
    
    // Getters and Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getItemID() {
        return itemID;
    }
    
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
    
    public T getItemType() {
        return itemType;
    }
    
    public void setItemType(T itemType) {
        this.itemType = itemType;
    }
    
    /**
     * Returns a formatted string representation of the library item
     * @return String containing item details
     */
    @Override
    public String toString() {
        return String.format("ID: %-10s | Type: %-10s | Title: %-30s | Author: %s", 
                            itemID, itemType, title, author);
    }
    
    /**
     * Returns detailed information about the item
     * @return Detailed string representation
     */
    public String getDetailedInfo() {
        StringBuilder info = new StringBuilder();
        info.append("╔════════════════════════════════════════════════════════════════╗\n");
        info.append("║                      ITEM DETAILS                              ║\n");
        info.append("╠════════════════════════════════════════════════════════════════╣\n");
        info.append(String.format("║ Item ID:     %-49s ║\n", itemID));
        info.append(String.format("║ Type:        %-49s ║\n", itemType));
        info.append(String.format("║ Title:       %-49s ║\n", title));
        info.append(String.format("║ Author:      %-49s ║\n", author));
        info.append("╚════════════════════════════════════════════════════════════════╝");
        return info.toString();
    }
}
