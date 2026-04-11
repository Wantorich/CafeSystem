package com.cafesystem;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PriceTest {

  @Test
  void 금액은_0원_이하일_수_없다() {
    Assertions.assertThatThrownBy(() -> Price.of(0))
        .isInstanceOf(IllegalArgumentException.class);

    Assertions.assertThatThrownBy(() -> Price.of(-1))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 금액_4500원과_5000원을_더하면_9500원이다() {
    Price price = Price.of(4500);
    Price added = Price.of(5000);
    Assertions.assertThat(price.add(added)).isEqualTo(Price.of(9500));
  }
}