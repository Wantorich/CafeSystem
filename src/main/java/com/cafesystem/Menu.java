package com.cafesystem;

import lombok.Getter;

public class Menu {
  private final String name;
  @Getter
  private final Price price;

  private Menu(String name, Price price) {
    this.name = name;
    this.price = price;
  }

  public static Menu createMenu(String name, Price price) {
    return new Menu(name, price);
  }
}
