package com.cafesystem.discount;

import com.cafesystem.Price;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SeasonDiscount extends AbstractDiscount {
  private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.15");

  @Override
  public int discount(Price origin) {
    BigDecimal price = BigDecimal.valueOf(origin.getMoney());
    BigDecimal discounted = price.multiply(BigDecimal.ONE.subtract(DISCOUNT_RATE));
    return discounted.setScale(0, RoundingMode.HALF_UP).intValue();
  }
}
