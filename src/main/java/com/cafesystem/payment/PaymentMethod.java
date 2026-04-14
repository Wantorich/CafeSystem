package com.cafesystem.payment;

import com.cafesystem.common.Price;

public interface PaymentMethod {

  void pay(Price price);
}
