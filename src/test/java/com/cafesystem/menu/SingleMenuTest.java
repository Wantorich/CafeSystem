package com.cafesystem.menu;

import static org.assertj.core.api.Assertions.assertThat;

import com.cafesystem.common.Category;
import com.cafesystem.common.Price;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class SingleMenuTest {

  @Test
  void 가격이_0원인_메뉴를_생성할수_없다() {
    Assertions.assertThatThrownBy(() -> Price.of(0))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 단품_메뉴의_가격을_조회할수있다() {
    Menu americano = SingleMenu.createMenu("아메리카노", Price.of(4500), Category.COFFEE);
    assertThat(americano.getPrice()).isEqualTo(Price.of(4500));
  }
}
