package fawry.model.product;

import java.time.LocalDate;

import fawry.model.ExpirableProduct;
import fawry.model.Shippable;

public class Biscuit extends ExpirableProduct implements Shippable {
  double weight;

  public Biscuit(String name, double price, int quantity, LocalDate expirationDate) {
    super(name, price, quantity, expirationDate);
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
