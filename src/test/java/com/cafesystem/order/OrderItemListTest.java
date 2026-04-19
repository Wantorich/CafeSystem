package com.cafesystem.order;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cafesystem.common.Category;
import com.cafesystem.common.Price;
import com.cafesystem.common.Quantity;
import com.cafesystem.menu.Menu;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderItemListTest {
  @Test
  void 주문항목_없이_주문을_생성할수_없다() {
    List<OrderItem> emptyList = Collections.emptyList();

    assertThatThrownBy(() -> OrderItemList.of(emptyList))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 주문항목에_null이_전달되면_주문을_생성할수_없다() {
    assertThatThrownBy(() -> OrderItemList.of(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 아메리카노2잔_치즈케이크1개_주문총금액은_15500원이다() {
    OrderItem americano = OrderItem.createOrderItem(Menu.createMenu("아메리카노", Price.of(4500), Category.COFFEE), Quantity.of(2), Collections.emptySet());
    OrderItem cheeseCake = OrderItem.createOrderItem(Menu.createMenu("치즈케이크", Price.of(6500), Category.DESSERT), Quantity.of(1), Collections.emptySet());
    Collection<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(americano);
    orderItems.add(cheeseCake);

    OrderItemList orderItemList = OrderItemList.of(orderItems);

    Assertions.assertThat(orderItemList.sum()).isEqualTo(Price.of(15500));
  }

  @Test
  void 주문항목리스트는_외부에서_변경할수_없다() {
    OrderItem americano = OrderItem.createOrderItem(Menu.createMenu("아메리카노", Price.of(4500), Category.COFFEE), Quantity.of(2), Collections.emptySet());
    Collection<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(americano);

    OrderItemList orderItemList = OrderItemList.of(orderItems);

    OrderItem cheeseCake = OrderItem.createOrderItem(Menu.createMenu("치즈케이크", Price.of(6500), Category.DESSERT), Quantity.of(1), Collections.emptySet());
    orderItems.add(cheeseCake);

    Assertions.assertThat(orderItemList.sum()).isEqualTo(Price.of(9000));
  }
}