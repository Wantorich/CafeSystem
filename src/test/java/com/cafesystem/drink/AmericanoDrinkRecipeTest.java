package com.cafesystem.drink;

import static com.cafesystem.drink.DrinkType.AMERICANO;
import static com.cafesystem.drink.Temperature.HOT;
import static com.cafesystem.drink.Temperature.ICED;
import static org.assertj.core.api.Assertions.assertThat;

import com.cafesystem.drink.recipe.DrinkRecipe;
import com.cafesystem.drink.recipe.DrinkRecipeRegistry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AmericanoDrinkRecipeTest {

  private DrinkRecipe americanoDrinkRecipe;

  @BeforeEach
  void setUp() {
    americanoDrinkRecipe = DrinkRecipeRegistry.get(AMERICANO);
  }

  @Test
  void ICE_아메리카노_기본값으로_제조한다() {
    Drink americano = americanoDrinkRecipe.produce(ICED);

    assertThat(americano.getDrinkType()).isEqualTo(AMERICANO);
    assertThat(americano.getTemperature()).isEqualTo(ICED);
    assertThat(americano.getEspressoAmount()).isEqualTo(2);
  }

  @Test
  void 지정한_샷_개수로_HOT_아메리카노를_제조한다() {
    Drink americano = americanoDrinkRecipe.produce(HOT, 3);

    assertThat(americano.getDrinkType()).isEqualTo(AMERICANO);
    assertThat(americano.getTemperature()).isEqualTo(HOT);
    assertThat(americano.getEspressoAmount()).isEqualTo(3);
  }

  @Test
  void 샷0개의_음료를_요청하면_예외가_발생한다() {
    Assertions.assertThatThrownBy(() -> americanoDrinkRecipe.produce(ICED, 0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("에스프레소 샷은 최소 1개 이상이어야 합니다.");
  }

}