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

## 2026-04-12 — 음료 종류 확장 (OCP / Enum 다형성)

- **도메인**: Category Enum 도입 — Menu에 카테고리 추가, if-else 제거
- **적용/발견된 OOP 개념**:
  - OCP: 확장에 열려 있고 수정에 닫혀 있어야 한다
  - Enum vs 인터페이스+클래스 선택 기준: 고정된 목록(컴파일 타임) → Enum, 런타임 교체 필요 → 인터페이스
  - 타입 선언이 곧 동작의 선언 — Enum 상수에 데이터와 행동을 함께 정의
- **핵심 실수 또는 설계 결함**:
  - 테스트를 요구사항이 아닌 구현에 맞춰 작성 (공백 포맷 불일치) — 3회 반복 패턴
  - Menu 유효성 검증 누락 (name/price/category null 체크)
  - Static Factory Method 네이밍 불일치: Menu는 `of()`, OrderItem은 `createOrderItem()` — 프로젝트 내 일관성 부재
- **추가 변경사항**:
  - Clean Code 리뷰 에이전트(`clean-code-reviewer`) 추가 — 세션 완료 시 OOP 리뷰와 병렬 진행
- **다음 세션 확장 방향**:
  - 할인 정책 다양화 (멤버십/쿠폰/시간대) → Strategy 패턴

## 2026-04-13 — 할인 정책 (Strategy 패턴)

- **도메인**: DiscountPolicy 인터페이스 + 구현체 4종 도입 — Order에 할인 적용
- **적용/발견된 OOP 개념**:
  - Strategy 패턴: 변하는 것(할인 계산 방식)을 인터페이스로 추상화, 변하지 않는 것(할인 적용 프로세스)은 Order가 보유
  - Enum vs 인터페이스 선택 기준 실전 적용 — 런타임 교체 필요 → 인터페이스
  - 오버로딩으로 기본 할인(NoDiscount) 자연스럽게 처리
- **핵심 실수 또는 설계 결함**:
  - `DiscountPolicy.min()` default 메서드 — 호출되지 않는 dead contract, 인터페이스에 불필요한 계약 추가
  - `DiscountPolicy` null 체크 누락
  - 0원 하한 제약이 각 구현체에 분산 — 새 구현자가 규칙을 모르면 제약 깨짐
  - OrderTest 멤버십 할인 테스트에서 기대값을 계산식으로 작성 (구현 복사)
- **다음 세션 확장 방향**:
  - 0원 하한 제약을 추상 클래스로 통합 → Template Method 패턴

## 2026-04-14 — 할인 정책 구조 개선 (Template Method 패턴)

- **도메인**: AbstractDiscount 추상 클래스 도입 — 0원 하한 제약 단일화
- **적용/발견된 OOP 개념**:
  - Template Method 패턴: `apply()` final로 흐름 고정, `discount()` protected abstract로 위임
  - `final` 키워드의 역할 — 하위 클래스가 흐름을 우회하지 못하도록 강제
  - Strategy + Template Method 계층적 결합 — 두 패턴이 충돌 없이 보완적으로 작동
  - 공통 제약을 추상 클래스가 보장 → 구현자가 몰라도 자동 적용
- **핵심 실수 또는 설계 결함**:
  - 비율 할인 3개 구현체(Membership/HappyHour/Season)의 `discount()` 구현 중복 — RateDiscount 중간 추상 클래스로 해결 가능
  - 매직 넘버 `1` 상수 미추출 (리뷰 후 수정)
  - 에이전트가 DiscountPolicyTest.java를 읽지 않고 테스트 없다고 잘못 지적 — 리뷰 오류
- **다음 세션 확장 방향**:
  - Phase 3: DIP — 결제 모듈을 의존성 역전으로 교체 가능하게

## 2026-04-15 — 결제 모듈 의존성 역전 (DIP / Constructor Injection)

- **도메인**: PaymentMethod 인터페이스 + OrderService DIP 적용 — Test Double(FakePaymentMethod) 도입
- **적용/발견된 OOP 개념**:
  - DIP: OrderService가 구체 클래스(CardPaymentMethod)가 아닌 PaymentMethod 인터페이스에 의존
  - Constructor Injection: 의존성을 외부에서 주입하여 OrderService의 결합도를 낮춤
  - Test Double (Fake Object): FakePaymentMethod로 실제 결제 없이 행동 검증 (isPaid/paidAmount 상태 확인)
  - Package by Feature 디렉토리 구조: 기술 유형이 아닌 기능 단위로 패키지 분리
- **핵심 실수 또는 설계 결함**:
  - (세션 중 설계·구현 진행, 리뷰 생략하고 세션 종료)
- **다음 세션 확장 방향**:
  - Factory / Abstract Factory — 음료·사이즈·옵션 조합 생성