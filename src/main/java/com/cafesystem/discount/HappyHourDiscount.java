package com.cafesystem.discount;

import com.cafesystem.common.Price;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class HappyHourDiscount extends AbstractDiscount {
  private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.2");

  @Override
  public int discount(Price origin) {
    BigDecimal price = BigDecimal.valueOf(origin.getMoney());
    BigDecimal discounted = price.multiply(BigDecimal.ONE.subtract(DISCOUNT_RATE));
    BigDecimal result = discounted.setScale(0, RoundingMode.HALF_UP);
    return result.intValue();
  }
}
