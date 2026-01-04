import java.util.Scanner;

/**
 * LibraryCatalogSystem - Main class for the Generic Library Catalog application
 * This class provides a command-line interface for managing a library catalog
 * that can store different types of items (Books, DVDs, Magazines)
 * 
 * CS 1103 - Programming Assignment Unit 8
 * Generic Library Catalog System
 */
public class LibraryCatalogSystem {
    private Catalog<LibraryItem<?>> catalog;
    private Scanner scanner;
    private boolean running;
    
    /**
     * Constructor - Initializes the catalog system
     */
    public LibraryCatalogSystem() {
        catalog = new Catalog<>("Library Catalog");
        scanner = new Scanner(System.in);
        running = true;
    }
    
    /**
     * Main method - Entry point for the application
     */
    public static void main(String[] args) {
        displayWelcome();
        
        LibraryCatalogSystem system = new LibraryCatalogSystem();
        system.run();
    }
    
    /**
     * Displays the welcome screen
     */
    private static void displayWelcome() {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println("║           GENERIC LIBRARY CATALOG SYSTEM                      ║");
        System.out.println("║           CS 1103 - Programming Assignment Unit 6             ║");
        System.out.println("║                                                               ║");
        System.out.println("║   A flexible catalog system using Java Generics               ║");
        System.out.println("║   Supporting: Books, DVDs, and Magazines                      ║");
        System.out.println("║                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
    }
    
    /**
     * Main run loop for the application
     */
    public void run() {
        while (running) {
            displayMenu();
            int choice = getMenuChoice();
            processMenuChoice(choice);
        }
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println("║              Thank you for using our system!                  ║");
        System.out.println("║                     Goodbye!                                  ║");
        System.out.println("║                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
    }
    
    /**
     * Displays the main menu
     */
    private void displayMenu() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                        MAIN MENU                              ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Add a New Item                                            ║");
        System.out.println("║  2. Remove an Item                                            ║");
        System.out.println("║  3. View Item Details                                         ║");
        System.out.println("║  4. Display Entire Catalog                                    ║");
        System.out.println("║  5. Exit                                                      ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.print("\nEnter your choice (1-5): ");
    }
    
    /**
     * Gets the user's menu choice with error handling
     * @return The validated menu choice
     */
    private int getMenuChoice() {
        try {
            String input = scanner.nextLine().trim();
            int choice = Integer.parseInt(input);
            
            if (choice < 1 || choice > 5) {
                System.err.println("ERROR: Please enter a number between 1 and 5.");
                return 0;
            }
            
            return choice;
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Invalid input. Please enter a number.");
            return 0;
        }
    }
    
    /**
     * Processes the user's menu choice
     * @param choice The menu option selected
     */
    private void processMenuChoice(int choice) {
        switch (choice) {
            case 1:
                addItem();
                break;
            case 2:
                removeItem();
                break;
            case 3:
                viewItemDetails();
                break;
            case 4:
                displayCatalog();
                break;
            case 5:
                running = false;
                break;
            default:
                // Invalid choice already handled in getMenuChoice
                break;
        }
    }
    
    /**
     * Adds a new library item to the catalog
     */
    private void addItem() {
        System.out.println("\n─────────── ADD NEW ITEM ───────────");
        System.out.println("Select item type:");
        System.out.println("1. Book");
        System.out.println("2. DVD");
        System.out.println("3. Magazine");
        System.out.print("Enter type (1-3): ");
        
        try {
            int type = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Enter Item ID: ");
            String itemID = scanner.nextLine().trim();
            
            if (itemID.isEmpty()) {
                System.err.println("ERROR: Item ID cannot be empty.");
                return;
            }
            
            System.out.print("Enter Title: ");
            String title = scanner.nextLine().trim();
            
            if (title.isEmpty()) {
                System.err.println("ERROR: Title cannot be empty.");
                return;
            }
            
            System.out.print("Enter Author/Publisher/Studio: ");
            String author = scanner.nextLine().trim();
            
            if (author.isEmpty()) {
                System.err.println("ERROR: Author cannot be empty.");
                return;
            }
            
            LibraryItem<?> item = null;
            
            switch (type) {
                case 1:
                    item = new Book(title, author, itemID);
                    break;
                case 2:
                    item = new DVD(title, author, itemID);
                    break;
                case 3:
                    item = new Magazine(title, author, itemID);
                    break;
                default:
                    System.err.println("ERROR: Invalid item type.");
                    return;
            }
            
            catalog.addItem(item);
            
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Invalid input. Please enter a number.");
        } catch (Exception e) {
            System.err.println("ERROR: Failed to add item: " + e.getMessage());
        }
    }
    
    /**
     * Removes an item from the catalog
     */
    private void removeItem() {
        System.out.println("\n─────────── REMOVE ITEM ───────────");
        
        System.out.print("Enter Item ID to remove: ");
        String itemID = scanner.nextLine().trim();
        
        try {
            catalog.removeItem(itemID);
        } catch (Catalog.ItemNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Views detailed information about a specific item
     */
    private void viewItemDetails() {
        System.out.println("\n─────────── VIEW ITEM DETAILS ───────────");
        
        System.out.print("Enter Item ID: ");
        String itemID = scanner.nextLine().trim();
        
        LibraryItem<?> item = catalog.getItem(itemID);
        
        if (item == null) {
            System.err.println("ERROR: Item with ID '" + itemID + "' not found.");
        } else {
            System.out.println("\n" + item.getDetailedInfo());
        }
    }
    
    /**
     * Displays the entire catalog
     */
    private void displayCatalog() {
        catalog.displayCatalog();
    }
}
