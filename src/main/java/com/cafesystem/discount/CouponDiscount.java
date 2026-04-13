package com.cafesystem.discount;

import com.cafesystem.Price;
import java.math.BigDecimal;

public class CouponDiscount implements DiscountPolicy {
  private static final BigDecimal DISCOUNT_PRICE = new BigDecimal("1000");

  @Override
  public Price apply(Price origin) {
    BigDecimal price = BigDecimal.valueOf(origin.getMoney());
    BigDecimal discounted = price.subtract(DISCOUNT_PRICE);
    return Price.of(discounted.max(BigDecimal.ONE).intValue());
  }
}
