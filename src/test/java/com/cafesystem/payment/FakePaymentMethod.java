package com.cafesystem.payment;

import com.cafesystem.PaymentMethod;
import com.cafesystem.Price;

public class FakePaymentMethod implements PaymentMethod {
  private boolean isPaid;
  private Price paidAmount;

  @Override
  public void pay(Price price) {
    isPaid = true;
    paidAmount = price;
  }

  public boolean isPaid() {
    return isPaid;
  }

  public Price getPaidAmount() {
    return paidAmount;
  }
}
