package com.cafesystem.drink;

import com.cafesystem.menu.Category;

public enum DrinkType {
  AMERICANO(Category.COFFEE),
  CAFE_LATTE(Category.COFFEE);

  private Category category;

  DrinkType(Category category) {
    this.category = category;
  }

  public Category getCategory() {
    return category;
  }
}
