package com.cafesystem.menu;


import com.cafesystem.common.Category;
import com.cafesystem.common.Price;
import java.util.Objects;

public class SingleMenu implements Menu {
  private final String name;
  private final Price price;
  private final Category category;

  private SingleMenu(String name, Price price, Category category) {
    this.name = name;
    this.price = price;
    this.category = category;
  }

  public static SingleMenu createMenu(String name, Price price, Category category) {
    if (name == null || name.isEmpty())
      throw new IllegalArgumentException("메뉴 이름은 필수입니다.");
    Objects.requireNonNull(price, "price is null");
    Objects.requireNonNull(category, "category is null");
    return new SingleMenu(name, price, category);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Price getPrice() {
    return price;
  }
}
