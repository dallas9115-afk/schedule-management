# 일정 관리 시스템 (Schedule Management System)

## 프로젝트 개요

Spring Boot 기반으로 구축된 일정 관리 백엔드 서버입니다. 일정을 등록, 조회, 수정, 삭제하는 기본적인 CRUD 기능과 댓글 시스템을 포함하고 있습니다.
본 프로젝트는 JPA의 연관관계 매핑(`@ManyToOne`, `@OneToMany`)이나 Bean Validation 라이브러리에 의존하지 않고, 순수 자바 코드를 통해 객체 간의 관계를 설정하고 데이터 유효성을 검증하는 방식을 채택하여 구현되었습니다.

## 기술 스택

* **Java:** JDK 17
* **Framework:** Spring Boot 3.x
* **Database:** MySQL (또는 H2 Console)
* **Build Tool:** Gradle
* **Dependencies:** Spring Web, Spring Data JPA, Lombok

## 주요 기능 구현 사항

### 1. 일정 관리 (Schedule)

일정 엔티티는 작성일과 수정일을 포함하며, `Timestamped` 상속을 통해 자동으로 관리됩니다.

* **일정 등록:** 제목, 내용, 작성자, 비밀번호를 입력받아 저장합니다.
* **전체 조회:** 등록된 모든 일정을 수정일 기준 내림차순으로 정렬하여 조회합니다.
* **선택 조회:** 고유 식별자(ID)를 통해 특정 일정을 조회하며, 해당 일정에 등록된 댓글 목록을 함께 반환합니다.
* **일정 수정:** 비밀번호 검증 후 제목과 작성자명만 수정 가능합니다. (내용 수정 불가)
* **일정 삭제:** 비밀번호 검증 후 해당 데이터를 삭제합니다.

### 2. 댓글 관리 (Comment)

댓글 기능은 일정 엔티티와 논리적으로 연결되어 있으며, 별도의 연관관계 어노테이션 없이 ID 값을 통해 관계를 맺습니다.

* **댓글 등록:** 특정 일정의 ID를 기준으로 댓글을 생성합니다.
* **개수 제한:** 하나의 일정에는 최대 10개의 댓글만 등록 가능하도록 제한 로직이 적용되어 있습니다.

### 3. 데이터 유효성 검증 (Validation)

외부 라이브러리를 사용하지 않고, Service 계층에서 조건문을 통해 입력 데이터를 검증합니다.

* **일정 제목:** 필수값이며, 최대 30자까지 입력 가능합니다.
* **일정 내용:** 필수값이며, 최대 200자까지 입력 가능합니다.
* **댓글 내용:** 필수값이며, 최대 100자까지 입력 가능합니다.
* **작성자 및 비밀번호:** 필수값으로 Null 또는 빈 문자열을 허용하지 않습니다.

## 데이터베이스 설계 특징 (ERD)

본 프로젝트는 물리적인 외래 키(Foreign Key) 제약 조건을 사용하는 대신, 엔티티 내에 ID 값을 보관하는 방식을 사용하였습니다.

* **Schedule (일정):** `id(PK)`, `title`, `contents`, `author`, `password`, `created_at`, `modified_at`
* **Comment (댓글):** `id(PK)`, `contents`, `author`, `password`, `schedule_id`, `created_at`, `modified_at`
* `Comment` 테이블의 `schedule_id` 컬럼은 `Schedule` 테이블의 `id`를 가리키는 논리적 참조값입니다.



## API 명세서

### 일정 (Schedule)

| 기능 | Method | URL | Request Body |
| --- | --- | --- | --- |
| 일정 등록 | POST | `/api/schedules` | title, contents, author, password |
| 전체 조회 | GET | `/api/schedules` | (없음) |
| 선택 조회 | GET | `/api/schedules/{id}` | (없음) |
| 일정 수정 | PUT | `/api/schedules/{id}` | title, author, password |
| 일정 삭제 | DELETE | `/api/schedules/{id}` | password |

### 댓글 (Comment)

| 기능 | Method | URL | Request Body |
| --- | --- | --- | --- |
| 댓글 등록 | POST | `/api/schedules/{scheduleId}/comments` | contents, author, password |

## 실행 및 테스트 방법

1. 프로젝트를 로컬 환경에 클론(Clone) 합니다.
2. `application.properties` 파일에서 데이터베이스 설정을 본인의 환경에 맞게 수정합니다.
3. 프로젝트를 빌드 및 실행합니다.
4. Postman과 같은 API 테스트 도구를 사용하여 위 API 명세에 따라 요청을 전송합니다.
* **주의사항:** `GET /api/schedules/{id}` 요청 시, 삭제된 ID(결번)를 조회하거나 존재하지 않는 ID를 조회할 경우 예외 메시지가 반환됩니다.