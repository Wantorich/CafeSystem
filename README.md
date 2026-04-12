# Cafe Order System

카페 주문 시스템 도메인을 통해 객체지향 설계 원칙(SOLID)과 디자인 패턴을 체득하는 학습 프로젝트.

요구사항이 세션마다 점진적으로 확장되며, 초기 설계의 한계가 드러날 때마다 리팩토링을 통해 원칙을 적용한다.

---

## 기술 스택

- Java 17
- Gradle
- JUnit 5 + AssertJ

---

## 도메인 구조

```
Menu          — 메뉴 (이름, 가격, 카테고리)
Category      — 메뉴 카테고리 Enum (커피/티/에이드/디저트)
Price         — 금액 Value Object
Quantity      — 수량 Value Object
OrderItem     — 주문 항목 (메뉴 + 수량)
OrderItemList — 주문 항목 목록 (일급 컬렉션)
Order         — 주문
```

---

## 학습 커리큘럼

### Phase 1 — 단일 책임과 캡슐화

| 세션 | 핵심 개념 | 상태 |
|------|----------|------|
| 01 | 클래스 책임 분리, Static Factory Method | ✅ 완료 |
| 02 | 불변 객체, Value Object | ✅ 완료 |
| 03 | 일급 컬렉션 | ✅ 완료 |

### Phase 2 — 다형성과 인터페이스 설계

| 세션 | 핵심 개념 | 상태 |
|------|----------|------|
| 04 | OCP — 조건문을 다형성으로 대체 | ✅ 완료 |
| 05 | Strategy 패턴 | 🔜 예정 |
| 06 | Template Method vs Strategy | 🔜 예정 |

### Phase 3 — 의존성 역전과 조합

| 세션 | 핵심 개념 | 상태 |
|------|----------|------|
| 07 | DIP — 의존성 주입 수동 구현 | 🔜 예정 |
| 08 | Factory / Abstract Factory | 🔜 예정 |
| 09 | Decorator 패턴 | 🔜 예정 |

### Phase 4 — 실전 설계

| 세션 | 핵심 개념 |
|------|----------|
| 10+ | 전체 도메인 리팩토링 |
| 심화 | Observer, Composite |

---

## 세션별 핵심 설계 인사이트

**Session 01** — 방어 로직은 우회 불가능해야 의미가 있다. private 생성자 + Static Factory Method로 유일한 생성 진입점을 강제한다.

**Session 02** — Value Object의 3가지 조건: 불변성(`final` + 새 객체 반환), 동등성(`equals`/`hashCode`), 의미 있는 행동. VO 인스턴스의 존재 자체가 유효성의 증명이다.

**Session 03** — 일급 컬렉션으로 컬렉션 관련 책임을 한 곳에 모은다. `List.copyOf()`로 외부 변경으로부터 내부 상태를 보호한다.

**Session 04** — 고정된 목록은 Enum, 런타임 교체가 필요한 동작은 인터페이스. 타입 선언이 곧 동작의 선언이다.

---

## 실행 방법

```bash
./gradlew test
```
