package com.cafesystem;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class OrderItemTest {

  @Test
  void 수량_0으로_주문항목을_만들수없다() {
    Menu menu = Menu.createMenu("아메리카노", 1000);
    assertThatThrownBy(() -> OrderItem.createOrderItem(menu, 0))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
