package com.cafesystem.menu;

import com.cafesystem.common.Price;
import java.util.List;
import java.util.Objects;

public class SetMenu implements Menu {
  private final String name;
  private final List<Menu> menuList;
  private final int discountAmount;
  private final Price price;

  private SetMenu(String name, List<Menu> menuList, int discountAmount) {
    this.name = name;
    this.menuList = menuList;
    this.discountAmount = discountAmount;
    this.price = calculatePrice();
  }

  public static SetMenu create(String name, List<Menu> menuList, int discountAmount) {
    Objects.requireNonNull(menuList, "menuList must not be null");
    List<Menu> filteredMenuList = menuList.stream().filter(Objects::nonNull).toList();
    if (filteredMenuList.size() < 2) {
      throw new IllegalArgumentException("세트 메뉴는 2가지 이상의 항목으로 구성되어야합니다");
    }
    if (discountAmount < 0 ) {
      throw new IllegalArgumentException("할인 가격은 0원 이상이어야 합니다");
    }
    return new SetMenu(name, List.copyOf(filteredMenuList), discountAmount);
  }

  private Price calculatePrice() {
    int menuPriceSum = menuList.stream().map(Menu::getPrice).mapToInt(Price::getMoney).sum();
    int totalSetMenuPrice = menuPriceSum - discountAmount;
    return Price.of(totalSetMenuPrice);
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
