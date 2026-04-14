package com.cafesystem.order;

import static org.assertj.core.api.Assertions.*;

import com.cafesystem.common.Price;
import com.cafesystem.common.Quantity;
import com.cafesystem.menu.Category;
import com.cafesystem.menu.Menu;
import org.junit.jupiter.api.Test;

public class OrderItemTest {

  @Test
  void 수량_0으로_주문항목을_만들수없다() {
    assertThatThrownBy(() -> Quantity.of(0))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 가격이_4500원인_메뉴를_3개_주문하면_소계는_13500원이다() {
    Menu menu = Menu.createMenu("카페라떼", Price.of(4500),  Category.COFFEE);
    OrderItem orderItem = OrderItem.createOrderItem(menu, Quantity.of(3));

    assertThat(orderItem.calculateSubTotal()).isEqualTo(Price.of(13500));
  }
}
