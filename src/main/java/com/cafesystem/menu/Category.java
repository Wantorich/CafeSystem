package com.cafesystem.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Category {
  COFFEE("에스프레소 기반 음료", 3),
  TEA("차 종류", 2),
  ADE("에이드 종류", 1),
  DESSERT("디저트 종류", 1);

  private final String prefix;
  private final int preparationTime;

  public String getDescription(String menuName) {
    return String.format("%s: %s", prefix, menuName);
  }
}
