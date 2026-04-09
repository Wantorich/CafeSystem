package com.cafesystem;

import lombok.Getter;

public class OrderItem {
  private final Menu menu;
  private final int quantity;
  @Getter
  private int subTotal;

  private OrderItem(Menu menu, int quantity) {
    this.menu = menu;
    this.quantity = quantity;
    calculateSubTotal();
  }

  public static OrderItem createOrderItem(Menu menu, int quantity) {
    if (quantity <= 0)
      throw new IllegalArgumentException("주문 항목 수량은 0보다 커야합니다.");
    return new OrderItem(menu, quantity);
  }

  private void calculateSubTotal() {
    subTotal = menu.getPrice() * quantity;
  }
}
