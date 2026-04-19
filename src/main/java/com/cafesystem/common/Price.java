package com.cafesystem.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Price {

  @Getter
  private final int money;

  private Price(int money) {
    this.money = money;
  }

  public static Price of(int money) {
    if (money <= 0) throw new IllegalArgumentException("금액은 0원 이하일 수 없습니다.");
    return new Price(money);
  }

  public Price add(Price amount) {
    return Price.of(this.money + amount.getMoney());
  }

  public Price add(int amount) {
    if (amount == 0) return this;
    return Price.of(this.money + amount);
  }
}
