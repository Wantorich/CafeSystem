package com.cafesystem.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.cafesystem.common.Category;
import com.cafesystem.common.Price;
import com.cafesystem.common.Quantity;
import com.cafesystem.drink.DrinkOption;
import com.cafesystem.drink.DrinkSize;
import com.cafesystem.drink.DrinkType;
import com.cafesystem.menu.DrinkMenu;
import com.cafesystem.menu.Menu;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class OrderItemTest {

  @Test
  void 가격이_4500원인_메뉴를_3개_주문하면_소계는_13500원이다() {
    Menu menu = Menu.createMenu("카페라떼", Price.of(4500),  Category.COFFEE);
    OrderItem orderItem = OrderItem.createOrderItem(menu, Quantity.of(3), Collections.emptySet());

    assertThat(orderItem.calculateSubTotal()).isEqualTo(Price.of(13500));
  }

  @Test
  void 음료에_크기를_포함해서_주문한다() {
    Menu americano = DrinkMenu.create(DrinkType.AMERICANO, DrinkSize.MEDIUM);
    OrderItem orderItem = OrderItem.createOrderItem(americano, Quantity.of(1), Collections.emptySet());
    assertThat(orderItem.calculateSubTotal()).isEqualTo(Price.of(3000));
  }

  @Test
  void 음료에_옵션_1개를_추가시_기본가격에_옵션가격이_포함된다() {
    Menu cafeLatte = DrinkMenu.create(DrinkType.CAFE_LATTE, DrinkSize.LARGE);
    Set<DrinkOption> options = Set.of(DrinkOption.EXTRA_SYRUP);
    OrderItem orderItem = OrderItem.createOrderItem(cafeLatte, Quantity.of(1), options);
    assertThat(orderItem.calculateSubTotal()).isEqualTo(Price.of(5500));
  }

  @Test
  void 음료에_옵션_2개를_추가할시_기본가격에_옵션_합산가격이_포함된다() {
    Menu americano = DrinkMenu.create(DrinkType.AMERICANO, DrinkSize.SMALL);
    Set<DrinkOption> options = Set.of(DrinkOption.SOYBEAN_MILK, DrinkOption.EXTRA_SHOT);
    OrderItem orderItem = OrderItem.createOrderItem(americano, Quantity.of(1), options);
    assertThat(orderItem.calculateSubTotal()).isEqualTo(Price.of(2800));
  }

  @Test
  void 음료를_옵션없이_주문하면_기본가격으로만_계산된다() {
    Menu cafeLatte = DrinkMenu.create(DrinkType.CAFE_LATTE, DrinkSize.SMALL);
    OrderItem orderItem = OrderItem.createOrderItem(cafeLatte, Quantity.of(1), Collections.emptySet());
    assertThat(orderItem.calculateSubTotal()).isEqualTo(Price.of(3000));
  }
}
