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