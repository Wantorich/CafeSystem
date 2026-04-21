package com.cafesystem.menu;

import com.cafesystem.common.Category;
import com.cafesystem.common.Price;
import com.cafesystem.drink.DrinkPriceRegistry;
import com.cafesystem.drink.DrinkSize;
import com.cafesystem.drink.DrinkType;
import java.util.Objects;

public class DrinkMenu {

  private DrinkMenu() {}

  public static SingleMenu create(DrinkType drinkType, DrinkSize drinkSize) {
    Objects.requireNonNull(drinkType, "drinkType is null");
    Objects.requireNonNull(drinkSize, "drinkSize is null");
    String name = drinkType.getDrinkName();
    Category category = drinkType.getCategory();
    Price price = DrinkPriceRegistry.lookupPrice(drinkType, drinkSize);
    return SingleMenu.createMenu(name, price, category);
  }
}
