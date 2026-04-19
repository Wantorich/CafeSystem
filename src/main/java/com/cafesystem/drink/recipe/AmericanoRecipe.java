package com.cafesystem.drink.recipe;

import com.cafesystem.drink.DrinkType;

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
