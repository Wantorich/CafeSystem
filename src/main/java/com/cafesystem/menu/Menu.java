package com.cafesystem.menu;


import com.cafesystem.common.Category;
import com.cafesystem.common.Price;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Menu {
  private final String name;
  private final Price price;
  private final Category category;

  public static Menu createMenu(String name, Price price, Category category) {
    if (name == null || name.isEmpty())
      throw new IllegalArgumentException("메뉴 이름은 필수입니다.");
    Objects.requireNonNull(price, "price is null");
    Objects.requireNonNull(category, "category is null");
    return new Menu(name, price, category);
  }
}
