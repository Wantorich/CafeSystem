package com.cafesystem.discount;

import com.cafesystem.Price;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MembershipDiscount implements DiscountPolicy {
  private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.1");

  @Override
  public Price apply(Price origin) {
    BigDecimal price = BigDecimal.valueOf(origin.getMoney());
    BigDecimal discounted = price.multiply(BigDecimal.ONE.subtract(DISCOUNT_RATE));
    BigDecimal result = discounted.setScale(0, RoundingMode.HALF_UP);
    return Price.of(result.max(BigDecimal.ONE).intValue());
  }
}
