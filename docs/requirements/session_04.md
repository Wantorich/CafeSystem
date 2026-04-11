# 요구사항: 카페 주문 시스템 — 음료 종류 확장

## 배경

카페가 성장하면서 메뉴가 다양해졌다.
기존에는 모든 메뉴가 동일한 방식으로 처리되었지만,
이제 음료 종류에 따라 시스템이 다르게 동작해야 한다.

개발팀은 우선 빠르게 구현하기로 했고, 다음과 같은 코드가 작성되었다.

```java
public String getDescription(Menu menu) {
    if (menu.getCategory().equals("COFFEE")) {
        return "에스프레소 기반 음료: " + menu.getName();
    } else if (menu.getCategory().equals("TEA")) {
        return "차 종류: " + menu.getName();
    } else if (menu.getCategory().equals("ADE")) {
        return "에이드 종류: " + menu.getName();
    } else {
        return "기타: " + menu.getName();
    }
}

public int getPreparationTime(Menu menu) {
    if (menu.getCategory().equals("COFFEE")) {
        return 3;
    } else if (menu.getCategory().equals("TEA")) {
        return 2;
    } else if (menu.getCategory().equals("ADE")) {
        return 1;
    } else {
        return 5;
    }
}
```

신메뉴 카테고리가 추가될 때마다 이 조건문들을 모두 찾아서 수정해야 한다.
이미 서비스 곳곳에 비슷한 if-else가 퍼져 있다.

---

## 기능 요구사항

- FR-01: 메뉴는 카테고리(커피, 티, 에이드)를 가진다.
- FR-02: 각 카테고리는 고유한 설명 문구를 가진다.
- FR-03: 각 카테고리는 고유한 준비 시간(분)을 가진다.
- FR-04: 새로운 카테고리가 추가될 때 기존 코드를 수정하지 않아야 한다.
- FR-05: 기존 주문 생성, 총액 계산 기능은 그대로 동작해야 한다.

---

## 제약 조건

- 위의 if-else 구조를 그대로 사용하지 않는다.
- 순수 Java로만 구현한다.

---

## 검증 시나리오

**시나리오 1 — 카테고리별 설명**
- 아메리카노(커피)의 설명은 "에스프레소 기반 음료: 아메리카노"이다.
- 얼그레이(티)의 설명은 "차 종류: 얼그레이"이다.
- 레몬에이드(에이드)의 설명은 "에이드 종류: 레몬에이드"이다.

**시나리오 2 — 카테고리별 준비 시간**
- 커피 준비 시간은 3분이다.
- 티 준비 시간은 2분이다.
- 에이드 준비 시간은 1분이다.

**시나리오 3 — 신규 카테고리 추가**
- 스무디 카테고리를 추가할 때 기존 커피/티/에이드 관련 코드를 수정하지 않아도 된다.

---

## 구현 시작 전 스스로 답해볼 것

1. 위 if-else 코드의 문제가 무엇인지 정확히 설명할 수 있는가?
2. 새 카테고리가 추가될 때 "기존 코드를 수정하지 않는다"는 것을 코드 구조로 어떻게 보장할 수 있는가?
3. `getDescription()`과 `getPreparationTime()`이 같은 조건을 반복하고 있다. 이 둘의 관계를 어떻게 표현할 수 있는가?
