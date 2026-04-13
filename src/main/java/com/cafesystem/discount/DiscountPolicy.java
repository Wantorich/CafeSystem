package com.cafesystem.discount;

import com.cafesystem.Price;

public interface DiscountPolicy {
  Price apply(Price origin);
}
