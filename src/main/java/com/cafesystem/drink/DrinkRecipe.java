package com.cafesystem.drink;

public interface DrinkRecipe {
  Drink produce(Temperature temperature);
  Drink produce(Temperature temperature, int espressoAmount);
}
