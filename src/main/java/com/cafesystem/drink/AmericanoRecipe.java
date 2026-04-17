package com.cafesystem.drink;

public class AmericanoRecipe extends AbstractDrinkRecipe {
  private static final int DEFAULT_AMERICANO_ESPRESSO_AMOUNT = 2;

  @Override
  protected int defaultEspressoAmount() {
    return DEFAULT_AMERICANO_ESPRESSO_AMOUNT;
  }

  @Override
  protected DrinkType drinkType() {
    return DrinkType.AMERICANO;
  }
}
