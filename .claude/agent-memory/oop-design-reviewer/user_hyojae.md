---
name: 효재 프로필
description: 효재의 학습 배경, OOP 수준, 학습 목표
type: user
---

Java 기반 OOP 설계 원칙(SOLID, 디자인 패턴)을 체득하는 것이 목표인 학습자.
카페 주문 시스템 프로젝트를 통해 설계 역량을 쌓고 포트폴리오를 구축 중.

**현재 수준 (session_01 기준)**:
- 도메인 모델 분리(Menu, OrderItem, Order)를 직관적으로 수행함
- Static factory method 패턴을 의식적 또는 무의식적으로 적용함
- 생성자 내부에서 계산 로직(calculateSubTotal, calculateTotalPrice)을 호출하는 습관 존재
- Spring 유틸리티(CollectionUtils)를 순수 Java 제약 조건 위반으로 사용
- Lombok @Getter 선택적 적용 — name 필드에 @Getter 누락 (Menu 클래스)
- 테스트에서 private 생성자를 우회하기 위해 public 생성자를 남겨두는 일관성 문제 발생
- 검증 로직의 위치(어느 계층에서 검증할 것인가)에 대한 의식이 아직 명확하지 않음
- equals/hashCode를 Value Object의 필수 계약으로 인식하지 못하는 습관 — session_03에서 @EqualsAndHashCode 도입으로 개선됨
- 도메인 경계에서 int로 탈출하는 패턴 반복 — session_03에서 Price 반환으로 개선됨
- dead code를 만드는 경향: 생성자 검증으로 불가능한 상태를 별도 메서드로 다시 검증 — session_02, 03 반복 (orElseThrow() 패턴) / session_04에서 해결됨
- null과 의미가 다른 입력(빈 컬렉션)을 동일 조건으로 묶는 경향 — 에러 의미 구분 인식 부족 (session_03)
- 특정 필드에 선택적으로 @Getter 누락 반복 (Menu.name, session_01~03 연속) — session_04에서 해결됨
- 테스트가 요구사항 명세가 아닌 구현을 복사하는 경향 — session_04에서 포맷 문자열 불일치 발견 (기대값 대신 구현 결과를 그대로 복사)
- Rich Enum의 한계(알고리즘 분기)와 데이터 차이 처리의 구분을 아직 인식하지 못함 (session_04)
