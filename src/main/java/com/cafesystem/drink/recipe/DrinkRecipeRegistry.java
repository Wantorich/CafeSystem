package com.cafesystem.drink.recipe;

import com.cafesystem.drink.DrinkType;
import java.util.Map;

public class DrinkRecipeRegistry {

  private DrinkRecipeRegistry() {}

  private static final Map<DrinkType, DrinkRecipe> recipeMap = Map.of(
      DrinkType.AMERICANO, new AmericanoRecipe(),
      DrinkType.CAFE_LATTE, new CafeLatteRecipe());

  public static DrinkRecipe get(DrinkType drinkType) {
    DrinkRecipe drinkRecipe = recipeMap.get(drinkType);
    if (drinkRecipe == null) {
      throw new IllegalArgumentException("지원하지 않는 음료입니다");
    }
    return drinkRecipe;
  }

}
