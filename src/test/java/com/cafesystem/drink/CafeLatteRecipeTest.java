package com.cafesystem.drink;

import static com.cafesystem.drink.DrinkType.CAFE_LATTE;
import static com.cafesystem.drink.Temperature.HOT;
import static com.cafesystem.drink.Temperature.ICED;
import static org.assertj.core.api.Assertions.assertThat;

import com.cafesystem.drink.recipe.DrinkRecipe;
import com.cafesystem.drink.recipe.DrinkRecipeRegistry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CafeLatteRecipeTest {

  private DrinkRecipe cafeLatteRecipe;

  @BeforeEach
  void setUp() {
    cafeLatteRecipe = DrinkRecipeRegistry.get(CAFE_LATTE);
  }

  @Test
  void HOT_카페라떼_기본값으로_제조한다() {
    Drink cafeLatte = cafeLatteRecipe.produce(HOT);

    assertThat(cafeLatte.getDrinkType()).isEqualTo(DrinkType.CAFE_LATTE);
    assertThat(cafeLatte.getTemperature()).isEqualTo(HOT);
    assertThat(cafeLatte.getEspressoAmount()).isEqualTo(1);
  }

  @Test
  void 샷0개의_음료를_요청하면_예외가_발생한다() {
    Assertions.assertThatThrownBy(() -> cafeLatteRecipe.produce(ICED, 0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("에스프레소 샷은 최소 1개 이상이어야 합니다.");
  }
}