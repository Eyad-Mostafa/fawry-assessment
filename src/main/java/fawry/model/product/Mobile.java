package fawry.model.product;

import fawry.model.Product;
import fawry.model.Shippable;

public class Mobile extends Product implements Shippable{
  double weight;

  public Mobile(String name, double price, int quantity) {
    super(name, price, quantity);
  }

  @Override
  public String getName() {
    return name;
  }

  public double getWeight() {
    return weight;
  }
}
