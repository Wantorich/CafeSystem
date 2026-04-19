package com.cafesystem.order;

public class FakeOrderNotifier implements OrderNotifier {
  private boolean isNotified;

  @Override
  public void notify(Order order) {
    isNotified = true;
  }

  public boolean isNotified() {
    return isNotified;
  }
}
