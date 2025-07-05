package fawry.model.service;

import java.util.HashMap;
import java.util.Map;

import fawry.model.Shippable;

public class ShippingService {
  double shippingFee = 0;
  double shippingFeePerKg = 0.06;
  Map<Shippable, Integer> items = new HashMap<>();

  public void addItem(Shippable item, int quantity) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    items.merge(item, quantity, Integer::sum);
  }

  public double calculateShippingFee() {
    for (var entry : items.entrySet()) {
      var shippable = entry.getKey();
      int quantity = entry.getValue();
      double itemWeight = shippable.getWeight();

      shippingFee += itemWeight * quantity * shippingFeePerKg;
    }

    return shippingFee;
  }
}
