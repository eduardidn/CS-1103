package com.ecommerce.orders;

import com.ecommerce.Customer;
import com.ecommerce.Product;
import java.util.List;
import java.util.ArrayList;

public class Order {
    private int orderID;
    private Customer customer;
    private List<Product> products;
    private double orderTotal;
    private String status;

    public Order(int orderID, Customer customer) {
        this.orderID = orderID;
        this.customer = customer;
        this.products = new ArrayList<>(customer.getShoppingCart());
        this.orderTotal = customer.calculateTotalCost();
        this.status = "Placed";
    }

    public void generateOrderSummary() {
        System.out.println("\n--- Order Summary ---");
        System.out.println("Order ID: " + orderID);
        System.out.println("Customer: " + customer.getName());
        System.out.println("Status: " + status);
        System.out.println("Products:");
        for (Product p : products) {
            System.out.println("- " + p.getName() + ": $" + p.getPrice());
        }
        System.out.println("Total Amount: $" + orderTotal);
        System.out.println("---------------------");
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Order " + orderID + " status updated to: " + newStatus);
    }

    public int getOrderID() {
        return orderID;
    }
}
