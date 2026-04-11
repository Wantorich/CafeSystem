# 세션 로그

## 2026-04-08 — 기본 주문 생성

- **도메인**: 카페 주문 시스템 — 메뉴, 주문 항목, 주문 (Menu / OrderItem / Order)
- **적용/발견된 OOP 개념**:
  - Static Factory Method 패턴 (이유를 모른 채 적용 → 세션 중 이유 학습)
  - 객체 생성 제어: public 생성자를 private으로 강제 → factory method를 유일한 진입점으로
  - 불변 설계 의도 표현: `final` 필드 활용
  - 방어 로직은 우회 불가능해야 의미가 있다는 원칙
- **핵심 실수 또는 설계 결함**:
  - `OrderItem`에 `public` 생성자와 `createOrderItem()`을 동시에 열어둬 수량 검증 우회 가능
  - 제약 조건(순수 Java) 미확인 후 Spring `CollectionUtils` 사용
  - `calculateTotalPrice()`를 생성 시 1회 호출 후 `final`이 아닌 필드에 저장 — 확장 시 stale 값 위험
  - 테스트를 요구사항의 실제 도메인 수치가 아닌 임의 값으로 작성
- **다음 세션 확장 방향**:
  - 할인 정책 또는 메뉴 옵션 추가 → OCP/DIP 필요성 자연스럽게 등장 예정
  - 인터페이스 분리와 의존성 역전 체험

## 2026-04-11 — 금액과 수량을 객체로 (Value Object)

- **도메인**: Price, Quantity Value Object 도입 — Menu/OrderItem/Order에 적용
- **적용/발견된 OOP 개념**:
  - Value Object: 불변성(`final` + 새 객체 반환), 동등성(`equals`/`hashCode` 재정의), 의미 있는 행동
  - 불변식(invariant)을 생성 시점에 보장 → 이후 재검증 불필요
  - VO 인스턴스의 존재 자체가 유효성의 증명
- **핵심 실수 또는 설계 결함**:
  - `Price.add()`가 `money +=`로 내부 상태를 변경 → 가변 객체로 구현 (Value Object 아님)
  - `Price.add()`의 반환 타입을 `int`로 설계 → 결과를 다시 Price로 다룰 수 없음
  - `Quantity.isZero()`, `Quantity.isNegative()` 등 절대 실행되지 않는 dead code 작성
  - `equals`/`hashCode` 미재정의 (초기)
- **다음 세션 확장 방향**:
  - 주문 항목 목록을 일급 컬렉션(First-Class Collection)으로 표현

## 2026-04-11 — 주문 항목 목록을 객체로 (일급 컬렉션)

- **도메인**: OrderItemList 일급 컬렉션 도입 — Order 단순화
- **적용/발견된 OOP 개념**:
  - 일급 컬렉션: 컬렉션을 단일 필드로 감싸고, 관련 책임을 한 곳에 모음
  - 방어적 복사(`List.copyOf()`) vs `unmodifiableList()` 차이 — 완전한 복사본으로 불변성 보장
  - 책임 이동: 총액 계산이 `Order`에서 `OrderItemList`로 이동 → Order 단순화
  - 불변식 보장 후 재검증 불필요 — orElseThrow() dead code 인식 후 mapToInt().sum()으로 개선
- **핵심 실수 또는 설계 결함**:
  - `orElseThrow()` 작성 — 불변식이 보장된 상황에서의 dead code (session_02 패턴 반복)
  - `Order.createOrder(null)` NPE 미처리 — `Objects.requireNonNull()` 누락
  - `Menu.name` 3회 연속 외부 접근 불가 방치
- **다음 세션 확장 방향**:
  - 음료 종류 추가 → 조건문 폭발 유도 → OCP/다형성으로 대체