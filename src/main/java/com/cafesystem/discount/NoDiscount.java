package com.cafesystem.discount;

import com.cafesystem.Price;
import java.math.BigDecimal;

public class NoDiscount implements DiscountPolicy {

  @Override
  public Price apply(Price origin) {
    BigDecimal price = BigDecimal.valueOf(origin.getMoney());
    return Price.of(price.max(BigDecimal.ONE).intValue());
  }
}
