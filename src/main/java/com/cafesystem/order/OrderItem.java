package com.cafesystem.order;

import com.cafesystem.common.Price;
import com.cafesystem.common.Quantity;
import com.cafesystem.drink.DrinkOption;
import com.cafesystem.drink.DrinkOptions;
import com.cafesystem.menu.Menu;
import java.util.Set;

public class OrderItem {
  private final Menu menu;
  private final Quantity quantity;
  private final DrinkOptions drinkOptions;

  private OrderItem(Menu menu, Quantity quantity, Set<DrinkOption> drinkOptions) {
    this.menu = menu;
    this.quantity = quantity;
    this.drinkOptions = DrinkOptions.of(drinkOptions);
  }

  public static OrderItem createOrderItem(Menu menu, Quantity quantity, Set<DrinkOption> drinkOptions) {
    return new OrderItem(menu, quantity, drinkOptions);
  }

  public Price calculateSubTotal() {
    Price menuPrice = menu.getPrice();
    int optionPrice = drinkOptions.getTotalOptionPrice();
    Price unitPrice = menuPrice.add(optionPrice);
    return quantity.times(unitPrice);
  }
}
