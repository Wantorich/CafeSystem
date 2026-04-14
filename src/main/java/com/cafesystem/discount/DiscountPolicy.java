package com.cafesystem.discount;

import com.cafesystem.common.Price;

public interface DiscountPolicy {
  Price apply(Price origin);
}
