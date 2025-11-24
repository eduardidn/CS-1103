import com.ecommerce.Product;
import com.ecommerce.Customer;
import com.ecommerce.orders.Order;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Product> catalog = new ArrayList<>();

        // Initialize some products for the store
        catalog.add(new Product(101, "Laptop", 999.99));
        catalog.add(new Product(102, "Smartphone", 599.50));
        catalog.add(new Product(103, "Headphones", 49.99));
        catalog.add(new Product(104, "Smart Watch", 199.99));

        System.out.println("Welcome to the Simple E-Commerce System");
        
        // Create a customer
        int customerId = (int) (Math.random() * 1000) + 1;
        System.out.println("Generated Customer ID: " + customerId);

        System.out.print("Please enter your Name: ");
        String name = scanner.nextLine();

        Customer customer = new Customer(customerId, name);
        System.out.println("Welcome, " + customer.getName() + "!");

        // Main interaction loop
        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Browse Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Place Order");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayProducts(catalog);
                    break;

                case "2":
                    displayProducts(catalog);
                    System.out.print("Enter the Product ID to add: ");
                    try {
                        int pid = Integer.parseInt(scanner.nextLine());
                        Product selectedProduct = catalog.stream()
                            .filter(p -> p.getProductID() == pid)
                            .findFirst()
                            .orElse(null);
    
                        if (selectedProduct != null) {
                            customer.addProductToCart(selectedProduct);
                        } else {
                            System.out.println("Product with ID " + pid + " not found.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a numeric Product ID.");
                    }
                    break;

                case "3":
                    customer.displayShoppingCart();
                    break;

                case "4":
                    if (customer.getShoppingCart().isEmpty()) {
                        System.out.println("Your cart is empty. Add items before placing an order.");
                    } else {
                        // Generate a random order ID
                        int orderId = (int) (Math.random() * 10000);
                        Order newOrder = new Order(orderId, customer);
                        newOrder.generateOrderSummary();
                        
                        // Clear cart and finish
                        customer.clearCart();
                        System.out.println("Thank you for your order!");
                    }
                    break;

                case "5":
                    running = false;
                    System.out.println("Thanks, " + customer.getName() + "!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        scanner.close();
    }

    private static void displayProducts(List<Product> catalog) {
        System.out.println("\n--- Available Products ---");
        for (Product product : catalog) {
            System.out.println(product);
        }
    }
}
