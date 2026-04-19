package com.cafesystem.drink;

import java.util.Set;

public class DrinkOptions {
  private final Set<DrinkOption> options;
  private final int totalOptionPrice;

  private DrinkOptions(Set<DrinkOption> options) {
    this.options = options;
    this.totalOptionPrice = computeOptionsPrice();
  }

  public static DrinkOptions of(Set<DrinkOption> drinkOption) {
    if (drinkOption == null || drinkOption.isEmpty())
      return new DrinkOptions(Set.of());
    Set<DrinkOption> drinkOptionView = Set.copyOf(drinkOption);
    return new DrinkOptions(drinkOptionView);
  }

  public int getTotalOptionPrice() {
    return totalOptionPrice;
  }

  private int computeOptionsPrice() {
    return options.stream().mapToInt(DrinkOption::getOptionPrice).sum();
  }
}
