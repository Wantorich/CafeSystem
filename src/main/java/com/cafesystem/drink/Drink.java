package com.cafesystem.drink;

import lombok.Getter;

@Getter
public class Drink {
  private final DrinkType drinkType;
  private final Temperature temperature;
  private final int espressoAmount;

  private Drink(DrinkType drinkType, Temperature temperature, int espressoAmount) {
    this.drinkType = drinkType;
    this.temperature = temperature;
    this.espressoAmount = espressoAmount;
  }

  public static Drink create(DrinkType drinkType, Temperature temperature, int espressoAmount) {
    validateEspressoAmount(espressoAmount);
    return new Drink(drinkType, temperature, espressoAmount);
  }

  private static void validateEspressoAmount(int espressoAmount) {
    if (espressoAmount <= 0) {
      throw new IllegalArgumentException("에스프레소 샷은 최소 1개 이상이어야 합니다.");
    }
  }
}
