package fawry.model;

public abstract class Product {
  protected String name;
  double price;
  int quantity;

  public Product(String name, double price, int quantity) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  public abstract String getName();
}
