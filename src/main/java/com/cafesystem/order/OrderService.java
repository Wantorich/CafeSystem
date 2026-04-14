package com.cafesystem.order;

import com.cafesystem.common.Price;
import com.cafesystem.payment.PaymentMethod;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderService {
  private final PaymentMethod paymentMethod;

  public void processPayment(Order order) {
    Price orderAmount = order.calculateTotalPrice();
    paymentMethod.pay(orderAmount);
  }
}
