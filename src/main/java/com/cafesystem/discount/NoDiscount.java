package com.cafesystem.discount;

import com.cafesystem.Price;

public class NoDiscount extends AbstractDiscount {

  @Override
  public int discount(Price origin) {
    return origin.getMoney();
  }
}
