package com.cafesystem.order;

import com.cafesystem.common.Price;
import com.cafesystem.payment.PaymentMethod;
import java.util.List;
import java.util.Objects;

public class OrderService {
  private final PaymentMethod paymentMethod;
  private final List<OrderNotifier> notifiers;

  public OrderService(PaymentMethod paymentMethod, List<OrderNotifier> notifiers) {
    Objects.requireNonNull(paymentMethod, "paymentMethod must not be null");
    Objects.requireNonNull(notifiers, "notifiers must not be null");
    this.paymentMethod = paymentMethod;
    this.notifiers = List.copyOf(notifiers);
  }

  public void processPayment(Order order) {
    Objects.requireNonNull(order, "order must not be null");
    Price orderAmount = order.calculateTotalPrice();
    paymentMethod.pay(orderAmount);
    notify(order);
  }

  private void notify(Order order) {
    notifiers.forEach(notifier -> notifier.notify(order));
  }
}
