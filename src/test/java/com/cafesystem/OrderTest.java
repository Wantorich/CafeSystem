package com.cafesystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class OrderTest {

  @Test
  void 단일_메뉴_주문에_성공한다() {
    Menu menu = Menu.createMenu("아메리카노", Price.of(4500));
    OrderItem orderItem = OrderItem.createOrderItem(menu, Quantity.of(3));
    List<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(orderItem);
    OrderItemList orderItemList = OrderItemList.of(orderItems);
    Order order = Order.createOrder(orderItemList);

    assertThat(order.calculateTotalPrice()).isEqualTo(Price.of(13500));
  }

  @Test
  void 복수_메뉴_주문에_성공한다() {
    Menu cafeLatte = Menu.createMenu("카페라떼", Price.of(5000));
    Menu cheeseCake = Menu.createMenu("치즈케이크", Price.of(6500));
    OrderItem orderItem = OrderItem.createOrderItem(cafeLatte, Quantity.of(1));
    OrderItem orderItem2 = OrderItem.createOrderItem(cheeseCake, Quantity.of(2));
    List<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(orderItem);
    orderItems.add(orderItem2);
    OrderItemList orderItemList = OrderItemList.of(orderItems);
    Order order = Order.createOrder(orderItemList);

    assertThat(order.calculateTotalPrice()).isEqualTo(Price.of(18000));
  }

  @Test
  void 주문항목리스트에_null이_전달되면_주문을_생성할수_없다() {
    assertThatThrownBy(() -> Order.createOrder(null))
        .isInstanceOf(NullPointerException.class);
  }
}
