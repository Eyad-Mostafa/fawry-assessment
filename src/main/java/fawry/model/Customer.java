package fawry.model;

import fawry.model.service.ShippingService;
import java.util.HashMap;
import java.util.Map;

public class Customer {
  int id;
  String name;
  String email;
  double balance;
  Cart cart;

  public Customer(int id, String name, String email, double balance, Cart cart) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.balance = balance;
    this.cart = cart;
  }

  public void addToCart(Product product, int quantity) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }
    if (cart == null) {
      throw new IllegalStateException("Cart is not initialized");
    }
    if (cart.items == null) {
      throw new IllegalStateException("Cart items map is not initialized");
    }
    if (product.name == null || product.name.isEmpty()) {
      throw new IllegalArgumentException("Product name cannot be null or empty");
    }
    if (product.price < 0) {
      throw new IllegalArgumentException("Product price cannot be negative");
    }
    if (product.quantity < 0) {
      throw new IllegalArgumentException("Product quantity cannot be negative");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be greater than zero");
    }
    if (product.quantity < quantity) {
      throw new IllegalArgumentException("Insufficient product quantity available");
    }
    cart.items.merge(product, quantity, Integer::sum);
  }

  public void checkout() {
    if (cart == null || cart.items == null || cart.items.isEmpty()) {
      throw new IllegalStateException("Cart is empty or not initialized");
    }

    var shippingService = new ShippingService();
    double subtotal = 0;
    Map<Shippable, Integer> shippableItems = new HashMap<>();
    double totalWeightInGrams = 0;
    String currancy = "EGP";

    for (var entry : cart.items.entrySet()) {
      Product product = entry.getKey();
      int quantity = entry.getValue();

      if (product instanceof Expirable expirableProduct) {
        if (expirableProduct.isExpired()) {
          throw new IllegalArgumentException("Cannot checkout expired product: " + product.name);
        }
      }

      if (product instanceof Shippable shippable) {
        shippingService.addItem(shippable, quantity);
        shippableItems.put(shippable, quantity);
        totalWeightInGrams += shippable.getWeight() * quantity;
      }

      double productCost = product.price * quantity;
      subtotal += productCost;
      product.quantity -= quantity;
    }

    if (!shippableItems.isEmpty()) {
      System.out.println("** Shipment notice **");
      for (var entry : shippableItems.entrySet()) {
        Shippable shippable = entry.getKey();
        int quantity = entry.getValue();
        System.out.printf("%dx %s %dg%n", quantity, shippable.getName(), (int) (shippable.getWeight() * quantity));
      }
      System.out.printf("Total package weight %.2fkg%n", totalWeightInGrams / 1000.0);
    }

    double shippingFee = shippingService.calculateShippingFee();
    double totalCost = subtotal + shippingFee;

    if (totalCost > balance) {
      throw new IllegalStateException(
          "Insufficient balance for checkout. Total cost: " + totalCost + " " + currancy + ", Balance: " + balance + " " + currancy);
    }

    System.out.println("** Checkout receipt **");
    for (var entry : cart.items.entrySet()) {
      Product product = entry.getKey();
      int quantity = entry.getValue();
      double productCost = product.price * quantity;
      System.out.printf("%dx %s %d %s%n", quantity, product.name, (int) productCost, currancy);
    }
    System.out.println("----------------------");
    System.out.printf("Subtotal %d %s%n", (int) subtotal, currancy);
    System.out.printf("Shipping %d %s%n", (int) shippingFee, currancy);
    System.out.printf("Amount %d %s%n", (int) totalCost, currancy);

    balance -= totalCost;
    cart.items.clear();

    System.out.println("Checkout successful for customer: " + name);
  }
}