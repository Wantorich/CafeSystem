package com.cafesystem;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class QuantityTest {

  @Test
  void 수량은_1미만이_될수없다() {
    Assertions.assertThatThrownBy(() -> Quantity.of(0))
        .isInstanceOf(IllegalArgumentException.class);
  }
}