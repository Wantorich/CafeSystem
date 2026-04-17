package com.cafesystem.drink;

public abstract class AbstractDrinkRecipe implements DrinkRecipe {

  protected abstract int defaultEspressoAmount();
  protected abstract DrinkType drinkType();

  @Override
  public Drink produce(Temperature temperature) {
    return Drink.create(drinkType(), temperature, defaultEspressoAmount());
  }

  @Override
  public Drink produce(Temperature temperature, int espressoAmount) {
    return Drink.create(drinkType(), temperature, espressoAmount);
  }
}
