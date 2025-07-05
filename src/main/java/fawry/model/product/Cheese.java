package fawry.model.product;

import java.time.LocalDate;

import fawry.model.ExpirableProduct;
import fawry.model.Shippable;

public class Cheese extends ExpirableProduct implements Shippable {
  double weight;

  public Cheese(String name, double price, int quantity, LocalDate expirationDate) {
    super(name, price, quantity, expirationDate);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public double getWeight() {
    return this.weight;
  }
}
