package com.cafesystem.drink;

import com.cafesystem.common.Price;

public class DrinkPriceRegistry {

  public static Price lookupPrice(DrinkType drinkType, DrinkSize drinkSize) {
    return drinkType.getPriceMap().get(drinkSize);
  }

}
