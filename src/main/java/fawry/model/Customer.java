package fawry.model;

import fawry.model.service.ShippingService;

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
    double totalCost = 0;

    for (var entry : cart.items.entrySet()) {
      Product product = entry.getKey();
      int quantity = entry.getValue();

      if (product instanceof Expirable) {
        Expirable expirableProduct = (Expirable) product;

        if (expirableProduct.isExpired()) {
          throw new IllegalArgumentException("Cannot checkout expired product: " + product.name);
        }
      }

      if (product instanceof Shippable shippable) {
        shippingService.addItem(shippable, quantity);
      }

      double productCost = product.price * quantity;
      totalCost += productCost;
      product.quantity -= quantity;
    }

    double shippingFee = shippingService.calculateShippingFee();
    totalCost += shippingFee;

    if (totalCost > balance) {
      throw new IllegalStateException("Insufficient balance for checkout. Total cost: " + totalCost + ", Balance: " + balance);
    }

    balance -= totalCost;
    
    cart.items.clear();

    System.out.println("Checkout successful for customer: " + name);
  }
}
