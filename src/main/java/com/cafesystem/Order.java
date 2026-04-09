package com.cafesystem;

import java.util.Collection;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

public class Order {
  private final Collection<OrderItem> orderItems;
  @Getter
  private int totalPrice;

  private Order(Collection<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  public static Order createOrder(Collection<OrderItem> orderItems) {
    if (orderItems == null || orderItems.isEmpty())
      throw new IllegalArgumentException("주문은 최소 1개의 주문 항목을 포함해야합니다");
    return new Order(orderItems);
  }

  public void calculateTotalPrice() {
    totalPrice = orderItems.stream().mapToInt(OrderItem::getSubTotal).sum();
  }
}
