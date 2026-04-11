package com.cafesystem;

public class Quantity {

  private final int amount;

  private Quantity(int amount) {
    this.amount = amount;
  }

  public static Quantity of(int amount) {
    if (amount <= 0) throw new IllegalArgumentException("수량은 양수여야 합니다.");
    return new Quantity(amount);
  }

  public Price times(Price price) {
    return Price.of(amount * price.getMoney());
  }
}
