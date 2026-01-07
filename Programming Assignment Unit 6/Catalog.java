import java.util.ArrayList;
import java.util.List;

/**
 * Generic Catalog class that can store and manage different types of LibraryItems.
 * This class demonstrates the use of generics to create a flexible library catalog system.
 * 
 * @param <T> The type of LibraryItem this catalog will store (must extend LibraryItem)
 */
public class Catalog<T extends LibraryItem<?>> {
    private List<T> items;
    private String catalogName;
    
    /**
     * Constructor to create a new Catalog
     * @param catalogName The name of this catalog
     */
    public Catalog(String catalogName) {
        this.catalogName = catalogName;
        this.items = new ArrayList<>();
    }
    
    /**
     * Adds a new item to the catalog
     * @param item The LibraryItem to add
     * @return true if the item was successfully added, false if item with same ID already exists
     */
    public boolean addItem(T item) {
        // Check if an item with the same ID already exists
        if (findItemByID(item.getItemID()) != null) {
            System.err.println("ERROR: An item with ID '" + item.getItemID() + "' already exists in the catalog.");
            return false;
        }
        
        items.add(item);
        System.out.println("✓ Item added successfully: " + item.getTitle());
        return true;
    }
    
    /**
     * Removes an item from the catalog by its ID
     * @param itemID The ID of the item to remove
     * @return true if the item was successfully removed, false if item not found
     * @throws ItemNotFoundException if the item with the specified ID doesn't exist
     */
    public boolean removeItem(String itemID) throws ItemNotFoundException {
        T itemToRemove = findItemByID(itemID);
        
        if (itemToRemove == null) {
            throw new ItemNotFoundException("ERROR: Item with ID '" + itemID + "' not found in the catalog.");
        }
        
        items.remove(itemToRemove);
        System.out.println("✓ Item removed successfully: " + itemToRemove.getTitle());
        return true;
    }
    
    /**
     * Retrieves an item from the catalog by its ID
     * @param itemID The ID of the item to retrieve
     * @return The LibraryItem if found, null otherwise
     */
    public T getItem(String itemID) {
        return findItemByID(itemID);
    }
    
    /**
     * Helper method to find an item by its ID
     * @param itemID The ID to search for
     * @return The LibraryItem if found, null otherwise
     */
    private T findItemByID(String itemID) {
        for (T item : items) {
            if (item.getItemID().equals(itemID)) {
                return item;
            }
        }
        return null;
    }
    
    /**
     * Displays all items in the catalog in a formatted table
     */
    public void displayCatalog() {
        if (items.isEmpty()) {
            System.out.println("\n═══════════════════════════════════════════════════════════════");
            System.out.println("  The catalog is currently empty.");
            System.out.println("═══════════════════════════════════════════════════════════════\n");
            return;
        }
        
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                           " + catalogName.toUpperCase() + "                                      ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Total Items: " + items.size() + "                                                                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════╝\n");
        
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).toString());
        }
        System.out.println();
    }
    
    /**
     * Custom exception for when an item is not found in the catalog
     */
    public static class ItemNotFoundException extends Exception {
        public ItemNotFoundException(String message) {
            super(message);
        }
    }
}
