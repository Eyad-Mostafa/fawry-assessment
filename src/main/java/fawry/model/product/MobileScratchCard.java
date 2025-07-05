package fawry.model.product;

import fawry.model.Product;

public class MobileScratchCard extends Product {
  public MobileScratchCard(String name, double price, int quantity) {
    super(name, price, quantity);
  }

  @Override
  public String getName() {
    return name;
  }
}
