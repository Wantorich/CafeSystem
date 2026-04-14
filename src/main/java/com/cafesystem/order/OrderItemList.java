package com.cafesystem.order;

import com.cafesystem.common.Price;
import java.util.Collection;
import java.util.List;

public class OrderItemList {

  private final Collection<OrderItem> orderItems;

  private OrderItemList(Collection<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  public static OrderItemList of(Collection<OrderItem> orderItems) {
    if (orderItems == null || orderItems.isEmpty())
      throw new IllegalArgumentException("주문은 최소 1개의 주문 항목을 포함해야합니다");
    Collection<OrderItem> orderItemView = List.copyOf(orderItems);
    return new OrderItemList(orderItemView);
  }

  public Price sum() {
    int sum = orderItems.stream()
        .map(OrderItem::calculateSubTotal)
        .mapToInt(Price::getMoney)
        .sum();
    return Price.of(sum);
  }
}
