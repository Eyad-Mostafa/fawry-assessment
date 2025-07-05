package fawry;

import java.time.LocalDate;

import fawry.model.Cart;
import fawry.model.Customer;
import fawry.model.product.*;

public class Main {
    public static void main(String[] args) {
        var cart = new Cart();
        var customer = new Customer(1, "Ahmed", "ahmed@example.com", 100000, cart);
        var cheese = new Cheese("Cheddar", 50.0, 2, LocalDate.of(2025, 12, 31));
        var biscuit = new Biscuit("Digestive", 20.0, 5, LocalDate.of(2024, 11, 30));
        var mobile = new Mobile("iPhone X", 15000.0, 0);
        var tv = new TV("Samsung 55 inch", 30000.0, 1);
        var mobileScratchCard = new MobileScratchCard("Vodafone Recharge", 100.0, 10);
        customer.addToCart(cheese, 1);
        // customer.addToCart(biscuit, 2);
        // customer.addToCart(mobile, 1);
        customer.addToCart(tv, 1);
        customer.addToCart(mobileScratchCard, 5);

        System.out.println("Cart items before checkout:");
        for (var entry : cart.items.entrySet()) {
            var product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(product.getName() + " - Quantity: " + quantity);
        }

        try {
            customer.checkout();
        } catch (IllegalArgumentException e) {
            System.out.println("Checkout failed: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Checkout failed: " + e.getMessage());
        }

        System.out.println("Cart items after checkout:");
        for (var entry : cart.items.entrySet()) {
            var product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(product.getName() + " - Quantity: " + quantity);
        }

        System.out.println("Thanks!");
    }
}