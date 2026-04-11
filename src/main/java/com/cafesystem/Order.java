package com.cafesystem;

import java.util.Objects;

public class Order {
  private final OrderItemList orderItemList;

  private Order(OrderItemList orderItemList) {
    this.orderItemList = orderItemList;
  }

  public static Order createOrder(OrderItemList orderItemList) {
    Objects.requireNonNull(orderItemList, "orderItemList cannot be null");
    return new Order(orderItemList);
  }

  public Price calculateTotalPrice() {
    return orderItemList.sum();
  }
}
