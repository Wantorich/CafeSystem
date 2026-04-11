package com.cafesystem;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MenuTest {

  @Test
  void 가격이_0원인_메뉴를_생성할수_없다() {
    Assertions.assertThatThrownBy(() -> Price.of(0))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
