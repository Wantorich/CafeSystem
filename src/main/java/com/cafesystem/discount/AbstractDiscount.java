package com.cafesystem.discount;

import com.cafesystem.Price;

public abstract class AbstractDiscount implements DiscountPolicy {
  private static final int MIN_PRICE = 1;

  @Override
  public final Price apply(Price origin) {
    int discounted = discount(origin);
    int revisedPrice = Math.max(MIN_PRICE, discounted);
    return Price.of(revisedPrice);
  }

  protected abstract int discount(Price origin);
}
