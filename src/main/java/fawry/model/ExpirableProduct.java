package fawry.model;

import java.time.LocalDate;

public abstract class ExpirableProduct extends Product implements Expirable {
  LocalDate expirationDate;

  public ExpirableProduct(String name, double price, int quantity, LocalDate expirationDate) {
    super(name, price, quantity);
    this.expirationDate = expirationDate;
  }

  @Override
  public boolean isExpired() {
    return LocalDate.now().isAfter(expirationDate);
  }
}
