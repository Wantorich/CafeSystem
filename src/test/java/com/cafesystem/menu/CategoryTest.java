package com.cafesystem.menu;

import static org.assertj.core.api.Assertions.assertThat;

import com.cafesystem.common.Category;
import org.junit.jupiter.api.Test;

class CategoryTest {

  @Test
  void 카테고리별_설명() {
    Category americano = Category.COFFEE;
    Category earlGrey = Category.TEA;
    Category lemonAde = Category.ADE;

    assertThat(americano.getDescription("아메리카노")).isEqualTo("에스프레소 기반 음료: 아메리카노");
    assertThat(earlGrey.getDescription("얼그레이")).isEqualTo("차 종류: 얼그레이");
    assertThat(lemonAde.getDescription("레몬에이드")).isEqualTo("에이드 종류: 레몬에이드");
  }

  @Test
  void 카테고리별_준비시간() {
    Category americano = Category.COFFEE;
    Category earlGrey = Category.TEA;
    Category lemonAde = Category.ADE;

    assertThat(americano.getPreparationTime()).isEqualTo(3);
    assertThat(earlGrey.getPreparationTime()).isEqualTo(2);
    assertThat(lemonAde.getPreparationTime()).isEqualTo(1);
  }
}