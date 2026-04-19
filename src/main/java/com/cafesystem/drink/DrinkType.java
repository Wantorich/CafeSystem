package com.cafesystem.drink;

import static com.cafesystem.drink.DrinkSize.LARGE;
import static com.cafesystem.drink.DrinkSize.MEDIUM;
import static com.cafesystem.drink.DrinkSize.SMALL;

import com.cafesystem.common.Category;
import com.cafesystem.common.Price;
import java.util.Map;

public enum DrinkType {
  AMERICANO(Category.COFFEE, "아메리카노", Map.of(SMALL, Price.of(2000), MEDIUM, Price.of(3000), LARGE, Price.of(4000))),
  CAFE_LATTE(Category.COFFEE, "카페라떼", Map.of(SMALL, Price.of(3000), MEDIUM, Price.of(4000), LARGE, Price.of(5000)));

  private final Category category;
  private final String drinkName;
  private final Map<DrinkSize, Price> priceMap;

  DrinkType(Category category, String drinkName,  Map<DrinkSize, Price> priceMap) {
    this.category = category;
    this.drinkName = drinkName;
    this.priceMap = priceMap;
  }

  public Category getCategory() {
    return category;
  }

  public String getDrinkName() {
    return drinkName;
  }

  public Map<DrinkSize, Price> getPriceMap() {
    return priceMap;
  }
}
