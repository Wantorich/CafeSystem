package com.cafesystem.order;

import com.cafesystem.common.Price;
import com.cafesystem.common.Quantity;
import com.cafesystem.drink.DrinkOption;
import com.cafesystem.drink.DrinkOptions;
import com.cafesystem.menu.SingleMenu;
import java.util.Set;

public class OrderItem {
  private final SingleMenu singleMenu;
  private final Quantity quantity;
  private final DrinkOptions drinkOptions;

  private OrderItem(SingleMenu singleMenu, Quantity quantity, Set<DrinkOption> drinkOptions) {
    this.singleMenu = singleMenu;
    this.quantity = quantity;
    this.drinkOptions = DrinkOptions.of(drinkOptions);
  }

  public static OrderItem createOrderItem(SingleMenu singleMenu, Quantity quantity, Set<DrinkOption> drinkOptions) {
    return new OrderItem(singleMenu, quantity, drinkOptions);
  }

  public Price calculateSubTotal() {
    Price menuPrice = singleMenu.getPrice();
    int optionPrice = drinkOptions.getTotalOptionPrice();
    Price unitPrice = menuPrice.add(optionPrice);
    return quantity.times(unitPrice);
  }
}
