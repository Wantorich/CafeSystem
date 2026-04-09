package com.cafesystem;

import lombok.Getter;

public class Menu {
  private final String name;
  @Getter
  private final int price;

  private Menu(String name, int price) {
    this.name = name;
    this.price = price;
  }

  public static Menu createMenu(String name, int price) {
    if (price <= 0)
      throw new IllegalArgumentException("가격은 0보다 커야합니다");
    return new Menu(name, price);
  }
}
