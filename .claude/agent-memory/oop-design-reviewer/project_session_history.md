---
name: 세션 이력 및 설계 결정 누적
description: 각 세션에서 효재가 적용한 패턴, 범한 실수, OOP 개념 습득 여부
type: project
---

## session_01 — 기본 주문 생성 (2026-04-08)

**도메인**: Menu, OrderItem, Order

**적용된 패턴**:
- Static Factory Method: Menu.createMenu(), OrderItem.createOrderItem(), Order.createOrder()
- 의식적으로 적용했는지는 추궁 필요

**발견된 설계 결함**:
1. 제약 조건 위반 — Spring의 CollectionUtils를 순수 Java 프로젝트에서 사용
2. 생성자 캡슐화 불일치 — OrderItem은 static factory method를 두면서 public 생성자도 병존 (테스트에서 public 생성자로 직접 생성)
3. Menu.name 필드에 @Getter 누락 — FR-01에서 이름 확인이 필요하나 외부 접근 불가
4. 계산 시점 설계 문제 — calculateTotalPrice()가 생성 시 1회만 실행되어 OrderItem 추가/변경 시 불일치 가능성 (현재는 불변이라 무해하나 확장 시 위험)
5. Order.totalPrice 필드가 @Getter + 비final — 불변이어야 할 필드가 재할당 가능 상태
6. Collection<OrderItem> 타입 노출 — 내부 컬렉션 타입이 파라미터로 노출되어 캡슐화 약화

**아직 경험하지 못한 OOP 개념**:
- 인터페이스 추상화 (Repository, Service 계층)
- OCP/DIP
- LSP/ISP
- 디자인 패턴 (Strategy, Decorator 등)

**다음 세션 확장 방향**:
- 할인 정책 추가 (Strategy 패턴 도입 기회)
- 포장/매장 구분 (다형성 적용 기회)

---

## session_02 — Value Object: Money(Price)와 Quantity (2026-04-08)

**도메인**: Price, Quantity (신규), Menu/OrderItem/Order (확장)

**적용된 패턴**:
- Static Factory Method: 일관성 향상 (session_01 대비 public 생성자 병존 문제 해결)
- Value Object: 부분 구현 — Quantity는 final 필드로 불변성 확보, Price는 final 없음 + add()에서 상태 변경

**session_01 대비 개선된 사항**:
- Spring CollectionUtils 제거 → java.util.Collection으로 교체
- 모든 클래스 생성자를 private으로 통일

**발견된 설계 결함**:
1. Price.money 필드에 final 없음 — Value Object의 불변성 계약 위반
2. Price.add(Price)가 money += ... 로 내부 상태를 변경하면서 int를 반환 — 이중 위반 (불변성 파괴 + 타입 탈출)
3. equals()/hashCode() 미구현 — Price, Quantity 모두 값 동등성(value equality) 계약 없음
4. Quantity.isZero(), isNegative()는 생성자 검증 이후 절대 true가 될 수 없는 dead code
5. Menu.createMenu()의 price.isZero() 검사도 동일한 dead code — Price.of()가 이미 차단
6. 도메인 경계에서 int로 탈출: Quantity.times(), Price.add(), Order.totalPrice 모두 int — Value Object 이점 소멸
7. Order.totalPrice 비final, Order.calculateTotalPrice() 수동 호출 구조 — session_01 미개선 지속
8. Collection<OrderItem> 파라미터 노출 — session_01 미개선 지속

**OOP 개념 습득 여부**:
- Value Object 개념은 인지했으나 "불변 + 값 동등성 + 의미 있는 행동" 삼박자를 완전히 구현하지 못함
- equals/hashCode를 Value Object 계약으로 인식하지 못하는 패턴 확인 — 다음 세션에서 재확인 필요

**아직 경험하지 못한 OOP 개념**:
- 인터페이스 추상화 (Repository, Service 계층)
- OCP/DIP
- LSP/ISP
- 디자인 패턴 (Strategy, Decorator 등)

**다음 세션 확장 방향**:
- 할인 정책 추가 (Strategy 패턴 도입 기회) — Price를 올바르게 불변 Value Object로 완성한 뒤 진행 권장
- Price.add()가 Price를 반환하는 구조로의 개선 후 Order 집계 흐름 재설계

---

## session_03 — 일급 컬렉션: OrderItemList (2026-04-08)

**도메인**: OrderItemList (신규), Price/Quantity/Order (개선)

**적용된 패턴**:
- First-Class Collection: OrderItemList — 컬렉션 외 다른 필드 없음, 컬렉션 관련 책임 집중, 불변성 보장 (List.copyOf())
- Static Factory Method: private 생성자 + of() 일관되게 적용. session_02 public 생성자 병존 문제 해결됨

**session_02 대비 개선된 사항**:
- Price.add()가 불변 Value Object 방식으로 완성됨 (money += ... 제거, Price 반환)
- @EqualsAndHashCode Lombok 적용으로 값 동등성 계약 도입
- Order가 OrderItemList에 위임하며 단순화 — 컬렉션 관련 로직 제거됨
- Price.of() / Quantity.of() 생성자 일관성 유지

**발견된 설계 결함**:
1. orElseThrow() dead code — calculateTotalPrice()에서 reduce(Price::add)의 Optional이 비어있을 수 없으나 orElseThrow() 사용. 생성자 검증으로 이미 보장된 불변식에 대한 중복 방어. session_02 dead code 패턴 반복
2. null과 emptyList를 동일 if 조건으로 묶음 — 두 케이스의 의미가 다름에도 같은 에러 메시지로 처리
3. Order.createOrder(null)에 대한 null 방어 없음 — OrderItemList가 null을 막았다고 해서 Order도 안전한 것은 아님
4. Menu.name 필드 @Getter 미적용 — session_01부터 3회 연속 방치

**OOP 개념 습득 여부**:
- 일급 컬렉션의 구조적 조건(다른 필드 없음, 책임 집중)을 올바르게 구현
- 방어적 복사 vs unmodifiableList 중 List.copyOf() 선택 — 구조적으로 올바름
- 불변식 보장과 메서드 내부 방어 중복 문제는 아직 인식 못함 (dead code 패턴 3회째)

**아직 경험하지 못한 OOP 개념**:
- 인터페이스 추상화 (Repository, Service 계층)
- OCP/DIP (OrderItemList 구체 클래스에 대한 Order 의존)
- LSP/ISP
- 디자인 패턴 (Strategy, Decorator 등)

**다음 세션 확장 방향**:
- 할인 정책 추가 (Strategy 패턴 도입 기회) — Price, OrderItemList가 정비된 상태이므로 진행 가능
- OrderItemList 인터페이스 추출 여부 논의 (OCP/DIP 도입 시점)
