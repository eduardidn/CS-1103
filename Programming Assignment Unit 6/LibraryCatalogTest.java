/**
 * LibraryCatalogTest - Comprehensive testing for the Generic Library Catalog
 * Tests all required functionalities including adding, removing, and retrieving items
 * 
 * CS 1103 - Programming Assignment Unit 6
 * 
 * To run this test: javac LibraryCatalogTest.java && java LibraryCatalogTest
 */
public class LibraryCatalogTest {
    
    private static int totalTests = 0;
    private static int passedTests = 0;
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║         GENERIC LIBRARY CATALOG - TEST SUITE                  ║");
        System.out.println("║         CS 1103 - Programming Assignment Unit 6               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝\n");
        
        testAddingItems();
        testRemovingItems();
        testRetrievingItems();
        testErrorHandling();
        testGenericCompatibility();
        
        displayTestSummary();
    }
    
    private static void testAddingItems() {
        System.out.println("\n━━━ TEST 1: ADDING ITEMS ━━━\n");
        
        Catalog<LibraryItem<?>> catalog = new Catalog<>("Test Catalog");
        
        Book book1 = new Book("The Hobbit", "J.R.R. Tolkien", "B001");
        test("Add Book", catalog.addItem(book1));
        
        DVD dvd1 = new DVD("The Matrix", "Warner Bros", "D001");
        test("Add DVD", catalog.addItem(dvd1));
        
        Magazine mag1 = new Magazine("Science Today", "Science Press", "M001");
        test("Add Magazine", catalog.addItem(mag1));
        
        Book duplicateBook = new Book("Duplicate", "Unknown", "B001");
        test("Reject Duplicate ID", !catalog.addItem(duplicateBook));
    }
    
    private static void testRemovingItems() {
        System.out.println("\n━━━ TEST 2: REMOVING ITEMS ━━━\n");
        
        Catalog<LibraryItem<?>> catalog = new Catalog<>("Test Catalog");
        catalog.addItem(new Book("Book 1", "Author 1", "B100"));
        catalog.addItem(new DVD("DVD 1", "Studio 1", "D100"));
        
        try {
            boolean removed = catalog.removeItem("B100");
            test("Remove Existing Item", removed);
        } catch (Exception e) {
            test("Remove Existing Item", false);
        }
        
        try {
            catalog.removeItem("NONEXISTENT");
            test("Remove Non-existent (Exception Expected)", false);
        } catch (Catalog.ItemNotFoundException e) {
            test("Remove Non-existent (Exception Caught)", true);
        }
    }
    
    private static void testRetrievingItems() {
        System.out.println("\n━━━ TEST 3: RETRIEVING ITEMS ━━━\n");
        
        Catalog<LibraryItem<?>> catalog = new Catalog<>("Test Catalog");
        Book testBook = new Book("Test Book", "Test Author", "B200");
        catalog.addItem(testBook);
        
        LibraryItem<?> retrieved = catalog.getItem("B200");
        test("Retrieve Existing Item", retrieved != null && retrieved.getItemID().equals("B200"));
        
        LibraryItem<?> notFound = catalog.getItem("INVALID");
        test("Retrieve Non-existent Returns Null", notFound == null);
        
        boolean detailsCorrect = retrieved != null && 
                                retrieved.getTitle().equals("Test Book") && 
                                retrieved.getAuthor().equals("Test Author");
        test("Item Details Match", detailsCorrect);
    }
    
    private static void testErrorHandling() {
        System.out.println("\n━━━ TEST 4: ERROR HANDLING ━━━\n");
        
        Catalog<LibraryItem<?>> catalog = new Catalog<>("Test Catalog");
        
        try {
            catalog.removeItem("B999");
            test("Remove from Empty (Exception Expected)", false);
        } catch (Catalog.ItemNotFoundException e) {
            test("Remove from Empty (Exception Caught)", true);
        }
        
        catalog.addItem(new Book("Book 1", "Author", "B500"));
        boolean duplicate = catalog.addItem(new Book("Book 2", "Author", "B500"));
        test("Duplicate ID Rejected", !duplicate);
        
        try {
            catalog.removeItem("B500");
            catalog.removeItem("B500");
            test("Multiple Removals (Exception Expected)", false);
        } catch (Catalog.ItemNotFoundException e) {
            test("Multiple Removals (Exception Caught)", true);
        }
    }
    
    private static void testGenericCompatibility() {
        System.out.println("\n━━━ TEST 5: GENERIC COMPATIBILITY ━━━\n");
        
        Catalog<LibraryItem<?>> catalog = new Catalog<>("Mixed Catalog");
        boolean added1 = catalog.addItem(new Book("Book", "Author", "B600"));
        boolean added2 = catalog.addItem(new DVD("DVD", "Studio", "D600"));
        boolean added3 = catalog.addItem(new Magazine("Magazine", "Publisher", "M600"));
        test("Mixed Item Types Added", added1 && added2 && added3);
        
        LibraryItem<?> book = catalog.getItem("B600");
        LibraryItem<?> dvd = catalog.getItem("D600");
        LibraryItem<?> mag = catalog.getItem("M600");
        test("Retrieve Different Types", book != null && dvd != null && mag != null);
        
        boolean correctTypes = (book instanceof Book) && 
                              (dvd instanceof DVD) && 
                              (mag instanceof Magazine);
        test("Type Checking", correctTypes);
        
        System.out.println("\n━━━ CATALOG DISPLAY TEST ━━━");
        catalog.displayCatalog();
    }
    
    private static void test(String testName, boolean condition) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.println("✓ PASSED: " + testName);
        } else {
            System.err.println("✗ FAILED: " + testName);
        }
    }
    
    private static void displayTestSummary() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    TEST SUMMARY                               ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println(String.format("║ Total Tests:     %-44d ║", totalTests));
        System.out.println(String.format("║ Passed Tests:    %-44d ║", passedTests));
        System.out.println(String.format("║ Failed Tests:    %-44d ║", (totalTests - passedTests)));
        
        double successRate = (totalTests > 0) ? (passedTests * 100.0 / totalTests) : 0.0;
        System.out.println(String.format("║ Success Rate:    %.2f%%%-40s ║", successRate, ""));
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        
        if (passedTests == totalTests) {
            System.out.println("\n✓ ALL TESTS PASSED! The catalog system works correctly.\n");
        } else {
            System.err.println("\n✗ Some tests failed. Review the implementation.\n");
        }
    }
}
