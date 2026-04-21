package com.cafesystem.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cafesystem.common.Category;
import com.cafesystem.common.Price;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SetMenuTest {

  private Menu croissantSetMenu;

  @BeforeEach
  void setUp() {
    Menu americano = SingleMenu.createMenu("아메리카노", Price.of(4500), Category.COFFEE);
    Menu croissant = SingleMenu.createMenu("크로와상", Price.of(3500), Category.DESSERT);
    croissantSetMenu = SetMenu.create("크로와상 세트", List.of(americano, croissant), 1000);
  }

  @Test
  void 세트메뉴에_특정_금액_할인을_적용한다() {
    assertThat(croissantSetMenu.getPrice()).isEqualTo(Price.of(7000));
  }

  @Test
  void 세트안의_세트의_최종_가격을_계산한다() {
    Menu cake = SingleMenu.createMenu("케이크", Price.of(5000), Category.DESSERT);
    Menu parentSetMenu = SetMenu.create("케이크 세트", List.of(croissantSetMenu, cake), 500);
    assertThat(parentSetMenu.getPrice()).isEqualTo(Price.of(11500));
  }

  @Test
  void 할인_금액이_0원인_세트는_구성항목_가격의_합과_같다() {
    Menu americano = SingleMenu.createMenu("아메리카노", Price.of(4500), Category.COFFEE);
    Menu croissant = SingleMenu.createMenu("크로와상", Price.of(3500), Category.DESSERT);
    Menu setMenu = SetMenu.create("크로와상 세트", List.of(americano, croissant), 0);
    assertThat(setMenu.getPrice()).isEqualTo(americano.getPrice().add(croissant.getPrice()));
  }

  @Test
  void 세트메뉴_할인가격은_0원이상이어야한다() {
    Menu americano = SingleMenu.createMenu("아메리카노", Price.of(4500), Category.COFFEE);
    Menu croissant = SingleMenu.createMenu("크로와상", Price.of(3500), Category.DESSERT);
    List<Menu> menuList = List.of(americano, croissant);
    assertThatThrownBy(() -> SetMenu.create("크로와상 세트", menuList, -1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("할인 가격은 0원 이상이어야 합니다");
  }

  @Test
  void 세트메뉴는_2개이상의_품목으로_이루어져야한다() {
    Menu americano = SingleMenu.createMenu("아메리카노", Price.of(4500), Category.COFFEE);
    List<Menu> menuList = List.of(americano);
    assertThatThrownBy(() -> SetMenu.create("크로와상 세트", menuList, 0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("세트 메뉴는 2가지 이상의 항목으로 구성되어야합니다");
  }
}