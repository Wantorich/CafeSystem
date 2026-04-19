package com.cafesystem.drink.recipe;

import com.cafesystem.drink.Drink;
import com.cafesystem.drink.Temperature;

public interface DrinkRecipe {
  Drink produce(Temperature temperature);
  Drink produce(Temperature temperature, int espressoAmount);
}
