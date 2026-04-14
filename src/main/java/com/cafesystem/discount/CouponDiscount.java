package com.cafesystem.discount;

import com.cafesystem.Price;
import java.math.BigDecimal;

public class CouponDiscount extends AbstractDiscount {
  private static final BigDecimal DISCOUNT_AMOUNT = new BigDecimal("1000");

  @Override
  public int discount(Price origin) {
    BigDecimal price = BigDecimal.valueOf(origin.getMoney());
    BigDecimal discounted = price.subtract(DISCOUNT_AMOUNT);
    return discounted.intValue();
  }
}
