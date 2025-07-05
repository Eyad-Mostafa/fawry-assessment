package fawry.model.product;

import fawry.model.Product;
import fawry.model.Shippable;

public class TV extends Product implements Shippable {
  double weight;

  public TV(String name, double price, int quantity, double weight) {
    super(name, price, quantity);
    this.weight = weight;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public double getWeight() {
    return weight;
  }
}
