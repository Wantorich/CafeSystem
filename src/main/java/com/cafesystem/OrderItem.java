package com.cafesystem;

import lombok.Getter;

public class OrderItem {
  private final Menu menu;
  private final Quantity quantity;
  @Getter
  private int subTotal;

  private OrderItem(Menu menu, Quantity quantity) {
    this.menu = menu;
    this.quantity = quantity;
  }

  public static OrderItem createOrderItem(Menu menu, Quantity quantity) {
    OrderItem orderItem = new OrderItem(menu, quantity);
    orderItem.calculateSubTotal();
    return orderItem;
  }

  private void calculateSubTotal() {
    Price menuPrice = menu.getPrice();
    subTotal = quantity.times(menuPrice);
  }
}
