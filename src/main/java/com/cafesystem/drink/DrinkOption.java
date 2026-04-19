package com.cafesystem.drink;

public enum DrinkOption {
  EXTRA_SHOT(500),
  EXTRA_SYRUP(500),
  SOYBEAN_MILK(300)
  ;

  private final int optionPrice;

  DrinkOption(int optionPrice) {
    this.optionPrice = optionPrice;
  }

  public int getOptionPrice() {
    return optionPrice;
  }
}
