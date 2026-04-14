package com.cafesystem.discount;

import static org.assertj.core.api.Assertions.assertThat;

import com.cafesystem.common.Price;
import com.cafesystem.discount.CouponDiscount;
import com.cafesystem.discount.NoDiscount;
import com.cafesystem.discount.DiscountPolicy;
import com.cafesystem.discount.HappyHourDiscount;
import com.cafesystem.discount.MembershipDiscount;
import com.cafesystem.discount.SeasonDiscount;
import org.junit.jupiter.api.Test;

class DiscountPolicyTest {

  @Test
  void 총액_10000원에_멤버십_할인_적용시_최종금액은_9000원이다() {
    DiscountPolicy discount = new MembershipDiscount();
    assertThat(discount.apply(Price.of(10000))).isEqualTo(Price.of(9000));
  }

  @Test
  void 총액_10000원에_쿠폰_할인_적용시_최종금액은_9000원이다() {
    DiscountPolicy discount = new CouponDiscount();
    assertThat(discount.apply(Price.of(10000))).isEqualTo(Price.of(9000));
  }

  @Test
  void 총액_10000원에_해피아워_할인_적용시_최종금액은_8000원이다() {
    DiscountPolicy discount = new HappyHourDiscount();
    assertThat(discount.apply(Price.of(10000))).isEqualTo(Price.of(8000));
  }

  @Test
  void 총액_10000원에_할인_미적용시_10000원이다() {
    DiscountPolicy discount = new NoDiscount();
    assertThat(discount.apply(Price.of(10000))).isEqualTo(Price.of(10000));
  }

  @Test
  void 총액_10000원에_시즌_할인_적용시_최종금액은_8500원이다() {
    DiscountPolicy discount = new SeasonDiscount();
    assertThat(discount.apply(Price.of(10000))).isEqualTo(Price.of(8500));
  }

  @Test
  void 총액_900원에_쿠폰_할인_적용시_최종금액은_1원이다() {
    DiscountPolicy discount = new CouponDiscount();
    assertThat(discount.apply(Price.of(900))).isEqualTo(Price.of(1));
  }
}