package com.cafesystem.drink;

public class CafeLatteRecipe extends AbstractDrinkRecipe {
  private static final int DEFAULT_CAFE_LATTE_ESPRESSO_AMOUNT = 1;

  @Override
  protected int defaultEspressoAmount() {
    return DEFAULT_CAFE_LATTE_ESPRESSO_AMOUNT;
  }

  @Override
  protected DrinkType drinkType() {
    return DrinkType.CAFE_LATTE;
  }
}
