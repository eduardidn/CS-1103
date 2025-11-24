package com.ecommerce;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int customerID;
    private String name;
    private List<Product> shoppingCart;

    public Customer(int customerID, String name) {
        if (customerID <= 0) {
             System.out.println("Error: Customer ID must be positive. Setting to default 1.");
             this.customerID = 1;
        } else {
            this.customerID = customerID;
        }
        this.name = name;
        this.shoppingCart = new ArrayList<>();
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        if (customerID <= 0) {
            System.out.println("Error: Customer ID must be positive.");
        } else {
            this.customerID = customerID;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void addProductToCart(Product product) {
        shoppingCart.add(product);
        System.out.println(product.getName() + " added to cart.");
    }

    public void removeProductFromCart(Product product) {
        if (shoppingCart.remove(product)) {
            System.out.println(product.getName() + " removed from cart.");
        } else {
            System.out.println("Product not found in cart.");
        }
    }

    public double calculateTotalCost() {
        double total = 0;
        for (Product product : shoppingCart) {
            total += product.getPrice();
        }
        return total;
    }

    public void displayShoppingCart() {
        System.out.println("\n--- Your Shopping Cart ---");
        if (shoppingCart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            for (Product p : shoppingCart) {
                System.out.println(p);
            }
            System.out.println("Total Cost: $" + calculateTotalCost());
        }
    }
    
    // This method will be used to clear cart after order is placed
    public void clearCart() {
        shoppingCart.clear();
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerID + ", Name: " + name;
    }
}
