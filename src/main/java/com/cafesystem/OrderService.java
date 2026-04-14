package com.cafesystem;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderService {
  private final PaymentMethod paymentMethod;

  public void processPayment(Order order) {
    Price orderAmount = order.calculateTotalPrice();
    paymentMethod.pay(orderAmount);
  }
}
