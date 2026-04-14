package com.cafesystem.order;

import com.cafesystem.common.Price;
import com.cafesystem.discount.DiscountPolicy;
import com.cafesystem.discount.NoDiscount;
import java.util.Objects;

public class Order {
  private final OrderItemList orderItemList;
  private final DiscountPolicy discountPolicy;

  private Order(OrderItemList orderItemList, DiscountPolicy discount) {
    this.orderItemList = orderItemList;
    this.discountPolicy = discount;
  }

  public static Order createOrder(OrderItemList orderItemList) {
    return createOrder(orderItemList, new NoDiscount());
  }

  public static Order createOrder(OrderItemList orderItemList, DiscountPolicy discountPolicy) {
    Objects.requireNonNull(orderItemList, "orderItemList cannot be null");
    if (Objects.isNull(discountPolicy))
      return new Order(orderItemList, new NoDiscount());
    return new Order(orderItemList, discountPolicy);
  }

  public Price calculateTotalPrice() {
    Price origin = orderItemList.sum();
    return discountPolicy.apply(origin);
  }
}
