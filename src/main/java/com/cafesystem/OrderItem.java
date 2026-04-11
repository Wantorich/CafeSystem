package com.cafesystem;

public class OrderItem {
  private final Menu menu;
  private final Quantity quantity;

  private OrderItem(Menu menu, Quantity quantity) {
    this.menu = menu;
    this.quantity = quantity;
  }

  public static OrderItem createOrderItem(Menu menu, Quantity quantity) {
    return new OrderItem(menu, quantity);
  }

  public Price calculateSubTotal() {
    Price menuPrice = menu.getPrice();
    return quantity.times(menuPrice);
  }
}
