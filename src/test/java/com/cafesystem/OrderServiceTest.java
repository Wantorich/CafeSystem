package com.cafesystem;

import static org.assertj.core.api.Assertions.assertThat;

import com.cafesystem.discount.MembershipDiscount;
import com.cafesystem.payment.FakePaymentMethod;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderServiceTest {
  private OrderItemList orderItems;

  @BeforeEach
  void setUp() {
    Menu americano = Menu.createMenu("아메리카노", Price.of(4500), Category.COFFEE);
    OrderItem orderItem = OrderItem.createOrderItem(americano, Quantity.of(2));
    orderItems = OrderItemList.of(List.of(orderItem));
  }

  @Test
  void 카드결제_방식으로_멤버십할인을_적용하여_결제를_처리한다() {
    Order order = Order.createOrder(orderItems, new MembershipDiscount());
    OrderService orderService = new OrderService(new CardPaymentMethod());

    orderService.processPayment(order);
  }

  @Test
  void 카카오페이_결제_방식으로_멤버십할인을_적용하여_결제를_처리한다() {
    Order order = Order.createOrder(orderItems, new MembershipDiscount());
    OrderService orderService = new OrderService(new KakaoPaymentMethod());

    orderService.processPayment(order);
  }

  @Test
  void 가짜_결제_방식으로_멤버십할인을_적용하여_결제를_처리한다() {
    Order order = Order.createOrder(orderItems, new MembershipDiscount());
    FakePaymentMethod payment = new FakePaymentMethod();
    OrderService orderService = new OrderService(payment);

    orderService.processPayment(order);

    assertThat(payment.getPaidAmount()).isEqualTo(Price.of(8100));
    assertThat(payment.isPaid()).isTrue();
  }
}