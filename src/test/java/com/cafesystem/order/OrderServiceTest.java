package com.cafesystem.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.cafesystem.common.Category;
import com.cafesystem.common.Price;
import com.cafesystem.common.Quantity;
import com.cafesystem.discount.MembershipDiscount;
import com.cafesystem.discount.NoDiscount;
import com.cafesystem.menu.SingleMenu;
import com.cafesystem.payment.FakePaymentMethod;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderServiceTest {
  private OrderItemList orderItems;

  @BeforeEach
  void setUp() {
    SingleMenu americano = SingleMenu.createMenu("아메리카노", Price.of(4500), Category.COFFEE);
    OrderItem orderItem = OrderItem.createOrderItem(americano, Quantity.of(2), Collections.emptySet());
    orderItems = OrderItemList.of(List.of(orderItem));
  }

  @Test
  void 가짜_결제_방식으로_멤버십할인을_적용하여_결제를_처리한다() {
    Order order = Order.createOrder(orderItems, new MembershipDiscount());
    FakePaymentMethod payment = new FakePaymentMethod();
    OrderService orderService = new OrderService(payment, List.of(new FakeOrderNotifier()));

    orderService.processPayment(order);

    assertThat(payment.getPaidAmount()).isEqualTo(Price.of(8100));
    assertThat(payment.isPaid()).isTrue();
  }

  @Test
  void 주문이_접수되면_알림이_등록된곳에서_알림을_받는다() {
    FakeOrderNotifier notifier = new FakeOrderNotifier();
    OrderService orderService = new OrderService(new FakePaymentMethod(), List.of(notifier));

    orderService.processPayment(Order.createOrder(orderItems, new NoDiscount()));

    assertThat(notifier.isNotified()).isTrue();
  }

  @Test
  void 알림_수신자가_없어도_주문이_정상적으로_완료된다() {
    FakePaymentMethod paymentMethod = new FakePaymentMethod();
    OrderService orderService = new OrderService(paymentMethod, Collections.emptyList());

    orderService.processPayment(Order.createOrder(orderItems, new NoDiscount()));

    assertThat(paymentMethod.isPaid()).isTrue();
  }

  @Test
  void 주문이_완료후_특정_수신자를_제외하면_이후_주문에서_해당수신자는_알림을_받지_않는다() {
    FakeOrderNotifier notifier = new FakeOrderNotifier();
    FakeOrderNotifier excludedNotifier = new FakeOrderNotifier();
    OrderService orderService = new OrderService(new FakePaymentMethod(), List.of(notifier));
    Order order = Order.createOrder(orderItems, new NoDiscount());

    orderService.processPayment(order);

    assertThat(notifier.isNotified()).isTrue();
    assertThat(excludedNotifier.isNotified()).isFalse();
  }
}