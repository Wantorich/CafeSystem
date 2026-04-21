package com.cafesystem.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cafesystem.common.Category;
import com.cafesystem.common.Price;
import com.cafesystem.common.Quantity;
import com.cafesystem.discount.CouponDiscount;
import com.cafesystem.discount.MembershipDiscount;
import com.cafesystem.menu.SingleMenu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class OrderTest {

  @Test
  void 단일_메뉴_주문에_성공한다() {
    SingleMenu singleMenu = SingleMenu.createMenu("아메리카노", Price.of(4500),  Category.COFFEE);
    OrderItem orderItem = OrderItem.createOrderItem(singleMenu, Quantity.of(3), Collections.emptySet());
    List<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(orderItem);
    OrderItemList orderItemList = OrderItemList.of(orderItems);
    Order order = Order.createOrder(orderItemList);

    assertThat(order.calculateTotalPrice()).isEqualTo(Price.of(13500));
  }

  @Test
  void 복수_메뉴_주문에_성공한다() {
    SingleMenu cafeLatte = SingleMenu.createMenu("카페라떼", Price.of(5000), Category.COFFEE);
    SingleMenu cheeseCake = SingleMenu.createMenu("치즈케이크", Price.of(6500), Category.DESSERT);
    OrderItem orderItem = OrderItem.createOrderItem(cafeLatte, Quantity.of(1), Collections.emptySet());
    OrderItem orderItem2 = OrderItem.createOrderItem(cheeseCake, Quantity.of(2), Collections.emptySet());
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

  @Test
  void 단일_메뉴_주문에_멤버십할인이_적용된다() {
    SingleMenu singleMenu = SingleMenu.createMenu("아메리카노", Price.of(4500),  Category.COFFEE);
    OrderItem orderItem = OrderItem.createOrderItem(singleMenu, Quantity.of(3), Collections.emptySet());
    List<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(orderItem);
    OrderItemList orderItemList = OrderItemList.of(orderItems);
    Order order = Order.createOrder(orderItemList, new MembershipDiscount());

    assertThat(order.calculateTotalPrice()).isEqualTo(Price.of(13500 - 1350));
  }

  @Test
  void 단일_메뉴_주문에_쿠폰할인이_적용후_0원이하가되어_1원이_반환된다() {
    SingleMenu singleMenu = SingleMenu.createMenu("마카롱", Price.of(900),  Category.DESSERT);
    OrderItem orderItem = OrderItem.createOrderItem(singleMenu, Quantity.of(1), Collections.emptySet());
    List<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(orderItem);
    OrderItemList orderItemList = OrderItemList.of(orderItems);
    Order order = Order.createOrder(orderItemList, new CouponDiscount());

    assertThat(order.calculateTotalPrice()).isEqualTo(Price.of(1));
  }
}
