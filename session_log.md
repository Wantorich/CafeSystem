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

## 2026-04-17 — 음료 생성 표준화 (Registry + Template Method)

- **도메인**: Drink / DrinkType / Temperature / DrinkRecipe / AbstractDrinkRecipe / AmericanoRecipe / CafeLatteRecipe / DrinkRecipeRegistry
- **적용/발견된 OOP 개념**:
  - Factory Method 패턴: `DrinkRecipe` 인터페이스(Creator) + 구체 구현체(ConcreteCreator) 분리
  - Template Method 패턴: `AbstractDrinkRecipe`가 공통 흐름(`produce`) 보유, 구현체는 `drinkType()`과 `defaultEspressoAmount()`만 선언
  - Registry 패턴: `DrinkRecipeRegistry`가 `DrinkType → DrinkRecipe` 매핑을 단일 진입점으로 관리 — 클라이언트가 구체 클래스명에 의존하지 않아도 되는 구조
  - Factory vs Registry 구분: 호출 시 새 객체를 만들면 Factory, 등록된 인스턴스를 찾아 반환하면 Registry
  - 패턴의 구조와 의도 구분: 골격을 구현해도 패턴의 목적(클라이언트 분리)이 실현되지 않으면 절반만 완성된 것
- **핵심 실수 또는 설계 결함**:
  - `AmericanoRecipe` / `CafeLatteRecipe` 구현이 상수만 다르고 100% 동일한 구조 — session_06과 동일한 DRY 위반 패턴 반복 (낯선 도메인에서 기존 신호를 인식하지 못함)
  - `Temperature.ICE` → 도메인 언어 불일치 (`ICED`로 수정)
  - `CafeLatteRecipeTest`에서 `new AmericanoRecipe()`로 잘못 초기화 — 테스트가 검증 대상 클래스와 무관한 버그
  - `Drink.createDrink()` — 클래스명 중복 포함 (→ `create()`로 수정)
  - re-aliasing 상수: `private static final DrinkType AMERICANO = DrinkType.AMERICANO` — 의미 없는 재선언
  - if문 중괄호 생략, 오류 메시지 오탈자
- **다음 세션 확장 방향**:
  - 음료에 사이즈(Size) 옵션 추가 → 조합 폭발 문제 → Abstract Factory 또는 Builder 패턴

## 2026-04-19 — 음료 옵션 구성 (Rich Enum + Registry + 불변 설계)

- **도메인**: DrinkSize / DrinkOption / DrinkOptions(일급 컬렉션) / DrinkPriceRegistry / DrinkType(Rich Enum) / DrinkMenu / OrderItem 확장
- **적용/발견된 OOP 개념**:
  - Rich Enum: `DrinkType`이 `Category`, `drinkName`, `Map<DrinkSize, Price>` 를 직접 보유 — 음료 타입이 자신의 가격 정보를 선언
  - Registry 패턴 개선: `DrinkPriceRegistry`가 데이터를 직접 보유하지 않고 `DrinkType`에 위임 — 새 음료 추가 시 Registry 수정 불필요
  - 일급 컬렉션: `DrinkOptions`가 `Set<DrinkOption>` 래핑, 총 옵션 가격 계산 책임 보유
  - 불변 객체 설계: `addOptions()` 제거 → 생성 시점에 `Set<DrinkOption>` 수신, `OrderItem` 완전 불변
  - `Price.add(int)` 오버로드: 0원 추가를 분기 없이 처리 — 도메인 제약(`Price > 0`)을 유지하면서 보일러플레이트 제거
  - 순환 의존 해소: `Category`를 `common` 패키지로 이동 — `drink ↔ singleMenu` 순환 의존 제거
- **핵심 실수 또는 설계 결함**:
  - `OrderItem.addOptions()` 작성 — 불변 제약 위반, 생성 시점 주입으로 수정
  - `DrinkPriceRegistry` 초기 설계에서 모든 음료 가격을 static 블록에 하드코딩 — OCP 위반, Rich Enum으로 해결
  - `DrinkOptions.totalOptionPrice` Lazy Initialization + non-final 필드 — 불변 객체에서 side effect 발생, 생성 시 계산으로 수정
  - `OrderItem.calculateSubTotal()`에서 `Price.of(optionPrice)` 호출 — 옵션 없을 때 0원으로 예외 발생, `add(int)` 오버로드로 해결
  - `boolean hasOption` 필드 — `drinkOptions != null`과 동일한 정보를 두 곳에서 표현
- **다음 세션 확장 방향**:
  - Decorator 패턴 — 음료에 동적으로 옵션을 감싸는 구조, 또는 Builder 패턴으로 복잡한 음료 객체 조립

## 2026-04-19 — 주문 알림 (Observer 패턴)

- **도메인**: OrderNotifier 인터페이스 / KitchenDisplay / PosDisplay / OrderService 확장 / FakeOrderNotifier(Test Double)
- **적용/발견된 OOP 개념**:
  - Observer 패턴: `OrderService`(Subject) → `OrderNotifier` 인터페이스(Observer) → `KitchenDisplay`/`PosDisplay`(ConcreteObserver)
  - 패턴을 몰랐지만 요구사항을 분석해서 동일한 구조에 자연스럽게 도달 — 문제 인식이 패턴 암기보다 중요하다는 것을 체험
  - DIP 재적용: `OrderService`가 구체 수신자를 모르고 `OrderNotifier` 인터페이스에만 의존
  - Constructor Injection: 수신자 목록을 생성 시점에 주입, `List.copyOf()`로 방어적 복사
  - Test Double(Fake): `FakeOrderNotifier`로 알림 전달 여부를 `isNotified()` 상태로 검증
- **핵심 실수 또는 설계 결함**:
  - `CardPaymentMethod`, `KakaoPaymentMethod`를 테스트에 직접 사용 — 외부 시스템 의존, assertion 없는 테스트 작성 → `FakePaymentMethod`로 교체
  - 시나리오 3 테스트에서 불필요한 첫 번째 `processPayment` + `reset()` 호출 — 처음부터 제외된 수신자 없이 서비스 생성으로 단순화 가능
  - `reset()` 메서드 존재 — 테스트 간 상태 공유를 암시, 새 인스턴스 생성으로 대체
- **다음 세션 확장 방향**:
  - Composite 패턴 — 세트 메뉴(단품 + 묶음) 동일 인터페이스로 처리, 또는 전체 도메인 리팩토링

## 2026-04-22 — 세트 메뉴 구성 (Composite 패턴)

- **도메인**: Menu 인터페이스 / SingleMenu(Leaf) / SetMenu(Composite) — 단품과 세트를 동일 인터페이스로 처리
- **적용/발견된 OOP 개념**:
  - Composite 패턴: `Menu` 인터페이스(Component) → `SingleMenu`(Leaf) + `SetMenu`(Composite) 구조
  - 인터페이스 기반 다형성: 호출 코드가 단품/세트를 구분하지 않고 동일하게 사용
  - 재귀 구조: `SetMenu`가 `List<Menu>`를 보유 — 세트 안의 세트 자동 처리
  - 기존 클래스를 인터페이스로 교체 — 의도적인 설계 결정(의존성 역전 방향)
- **핵심 실수 또는 설계 결함**:
  - `getCount()`를 `Menu` 인터페이스에 선언 — `SingleMenu`(고정 1)와 `SetMenu`(항목 수)가 다른 개념을 같은 메서드명으로 표현, 요구사항에 없는 계약을 인터페이스에 추가 → 삭제로 수정
  - FR-02(2건 이상 구성) 검증 누락 — `SetMenu.of()`에 size < 2 가드 추가로 수정
  - 새로 추가한 테스트에서 size 검증용 `discountAmount`에 `-1` 사용 — 의도와 무관한 값, `0`이 맞음
  - 파라미터 재할당(`menuList = ...stream().toList()`) — 새 변수(`menus`)로 분리
- **다음 세션 확장 방향**:
  - Strategy 패턴 — `discountAmount: int` 를 `DiscountPolicy` 인터페이스로 교체 (고정 금액 외 비율 할인 등 확장)