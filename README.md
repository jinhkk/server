# 테이블 오더 백엔드 서버

## 프로젝트 개요

이 프로젝트는 '테이블 오더' 시스템의 백엔드 서버로, Spring Boot 프레임워크를 기반으로 구축되었습니다. RESTful API를 통해 클라이언트(관리자 웹, 사용자 앱)에 필요한 데이터와 기능을 제공하며, 실시간 주문 처리를 위한 WebSocket 통신을 지원합니다.

## 주요 기능

* **사용자 인증 및 관리**:
   * JWT(JSON Web Token)를 사용한 토큰 기반의 인증 시스템을 구현하여 API를 보호합니다.
   * Spring Security를 통해 회원가입, 로그인, 역할(ADMIN, STAFF) 기반의 접근 제어를 관리합니다.
   * 관리자는 새로운 직원을 등록하고 전체 직원 목록을 조회하거나 삭제할 수 있습니다.
* **메뉴 및 카테고리 관리 (CRUD)**:
   * 메뉴 카테고리를 조회, 추가, 수정, 삭제하는 기능을 제공합니다.
   * 메뉴 아이템의 생성, 조회, 정보 업데이트, 삭제 기능을 제공하며 품절 처리도 가능합니다.
* **주문 처리 및 실시간 알림**:
   * 테이블별 신규 주문 접수 및 특정 메뉴 주문 기능을 제공합니다.
   * 결제가 완료되지 않은 모든 주문을 테이블별로 그룹화하여 조회할 수 있습니다.
   * WebSocket을 통해 신규 주문이 발생했을 때 연결된 모든 클라이언트에게 실시간으로 알림을 전송합니다.
   * 특정 테이블의 모든 주문에 대한 결제 처리 기능을 제공합니다.
* **매출 통계**:
   * 지정된 기간 동안의 일별, 월별 총매출액을 집계하여 제공합니다.
   * 기간별로 가장 많이 팔린 메뉴 순위를 판매 수량과 총매출액 기준으로 집계하여 제공합니다.

## 기술 스택

* **Java 17**
* **Spring Boot 3.2.5**
* **Spring Security**: JWT를 이용한 인증 및 인가 처리
* **Spring Data JPA**: 데이터베이스 연동 및 영속성 관리
* **Spring WebSocket**: 실시간 주문 알림 기능 구현
* **H2 Database**: 인메모리 데이터베이스 (개발 및 테스트용)
* **MySQL Connector/J**: MySQL 데이터베이스 연동 드라이버
* **Lombok**: DTO, Entity 등에서 보일러플레이트 코드를 줄이기 위해 사용
* **JJWT (Java JWT)**: JWT 생성 및 검증 라이브러리
* **Gradle**: 프로젝트 의존성 관리 및 빌드 도구

## API 엔드포인트 명세

주요 API 엔드포인트는 다음과 같습니다. 상세 내용은 각 컨트롤러 파일을 참고하세요.

| Method | URL | 설명 | 권한 | 관련 컨트롤러 |
|--------|-----|------|------|---------------|
| POST | `/api/users/signup` | 직원 회원가입 | 모두 | UserController |
| POST | `/api/users/login` | 로그인 | 모두 | UserController |
| GET | `/api/users` | 모든 유저 정보 조회 | ADMIN | UserController |
| DELETE | `/api/users/{userId}` | 특정 유저 삭제 | ADMIN | UserController |
| GET | `/api/categories` | 모든 카테고리 조회 | 모두 | CategoryController |
| POST | `/api/menu-items` | 새 메뉴 추가 | ADMIN | MenuItemController |
| PUT | `/api/menu-items/{menuId}` | 메뉴 정보 수정 | ADMIN | MenuItemController |
| DELETE | `/api/menu-items/{menuId}` | 메뉴 삭제 | ADMIN | MenuItemController |
| POST | `/api/orders` | 신규 주문 접수 | 모두 | OrderController |
| GET | `/api/orders/unpaid` | 미결제 주문 조회 | STAFF, ADMIN | OrderController |
| POST | `/api/orders/pay/table/{tableNumber}` | 테이블 결제 처리 | STAFF, ADMIN | OrderController |
| GET | `/api/sales/daily` | 일별 매출 조회 | ADMIN | SalesController |
| GET | `/api/sales/monthly` | 월별 매출 조회 | ADMIN | SalesController |
| GET | `/api/sales/by-menu` | 메뉴별 매출 조회 | ADMIN | SalesController |

## 설정 및 실행 방법

1. **데이터베이스 설정**: `src/main/resources/application.properties` 파일에서 본인의 데이터베이스 환경(MySQL 등)에 맞게 `spring.datasource` 관련 설정을 수정합니다.
2. **JWT 비밀 키 설정**: `application.properties` 파일의 `jwt.secret.key` 값을 안전한 문자열로 변경합니다. 이 키는 토큰 서명에 사용됩니다.
3. **프로젝트 빌드**: 프로젝트 루트 디렉토리에서 아래의 Gradle 명령어를 실행하여 프로젝트를 빌드합니다.
```bash
./gradlew build
```

4. **애플리케이션 실행**: 빌드가 완료되면 아래 명령어로 서버를 실행할 수 있습니다.
```bash
java -jar build/libs/server-0.0.1-SNAPSHOT.jar
```

서버는 기본적으로 8080 포트에서 실행됩니다.