# Joohyuk Commerce

> Java 개발자의 Kotlin & MSA 환경에서의 대용량 데이터 처리 실습 프로젝트

## 프로젝트 소개

이 프로젝트는 Java 백엔드 개발자가 **Kotlin**과 **MSA(Microservices Architecture)** 환경에서 **대용량 데이터 처리**를 학습하기 위한 실습 프로젝트입니다.

전자상거래(Commerce) 도메인을 기반으로 실무에서 마주칠 수 있는 다양한 기술적 과제들을 직접 구현하며 학습합니다.

## 기술 스택

### Core
- **Language**: Kotlin 1.9.25
- **Framework**: Spring Boot 3.5.6
- **Build Tool**: Gradle (Kotlin DSL)
- **JDK**: Java 21

### Infrastructure
- **Database**: MySQL
- **Architecture**: Hexagonal Architecture (Clean Architecture)

### Libraries
- Spring Data JPA
- Spring Web

## 아키텍처

### MSA (Microservices Architecture)
서비스별로 독립적인 모듈로 분리하여 확장성과 유지보수성을 확보합니다.

```
joohyuk-commerce/
├── common/                    # 공통 모듈
│   ├── snowflake/            # 분산 ID 생성기
│   └── pagination/           # 페이징 유틸리티
└── service/                  # 마이크로서비스
    └── article/              # 게시글 서비스
```

### Hexagonal Architecture
도메인 로직을 외부 기술로부터 분리하여 테스트 가능성과 유연성을 높입니다.

```
article/
├── adapter/
│   ├── in/
│   │   └── web/             # REST API Controller, DTO
│   └── out/
│       └── persistence/     # JPA Repository, Entity
├── application/
│   ├── port/
│   │   ├── in/              # Use Case Interface
│   │   └── out/             # Repository Interface
│   └── service/             # Business Logic
└── domain/                  # Domain Model
```

## 주요 기능 및 학습 포인트

### 1. 대용량 데이터 처리
- **Snowflake 알고리즘** 기반 분산 ID 생성
  - 시간순 정렬 가능한 64bit ID
  - 분산 환경에서의 중복 없는 ID 생성

- **무한 스크롤(Infinite Scroll) 페이징**
  - Cursor 기반 페이징으로 대용량 데이터 효율적 조회
  - Offset 기반 페이징의 성능 문제 해결

- **쿼리 최적화**
  - Native Query를 활용한 커버링 인덱스 활용
  - N+1 문제 해결

### 2. Kotlin 특화 기능
- **Kotlin JPA Plugin**을 통한 Entity 기본 생성자 자동 생성
- **Data Class**를 활용한 DTO 간결성
- **Null Safety**를 통한 안전한 코드
- **Extension Function**을 통한 가독성 향상

### 3. Clean Code & Architecture
- **Hexagonal Architecture**로 계층 간 의존성 명확히 분리
- **Port & Adapter 패턴**으로 비즈니스 로직과 인프라 분리
- **Domain Driven Design** 원칙 적용

## 실행 방법

### 1. 데이터베이스 설정
```bash
# MySQL 데이터베이스 생성
mysql -u root -p
CREATE DATABASE article CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. 애플리케이션 설정
`service/article/src/main/resources/application.yml` 파일에서 데이터베이스 연결 정보를 수정합니다.

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/article
    username: root
    password: your_password
```

### 3. 빌드 및 실행
```bash
# 빌드
./gradlew clean build

# Article 서비스 실행
./gradlew :service:article:bootRun
```

애플리케이션은 `http://localhost:9000`에서 실행됩니다.

## API 명세

### Article Service (포트: 9000)

#### 게시글 생성
```http
POST /api/v1/articles
Content-Type: application/json

{
  "boardId": 1,
  "writerId": 1,
  "title": "제목",
  "content": "내용"
}
```

#### 게시글 조회
```http
GET /api/v1/articles/{id}
```

#### 게시글 목록 조회 (무한 스크롤)
```http
GET /api/v1/articles/infinite-scroll?boardId=1&size=20&lastArticleId=12345
```

#### 게시글 수정
```http
PUT /api/v1/articles/{id}
Content-Type: application/json

{
  "requesterId": 1,
  "title": "수정된 제목",
  "content": "수정된 내용"
}
```

#### 게시글 삭제
```http
DELETE /api/v1/articles/{id}?requesterId=1
```

## 모듈 구조

### Common Modules

#### `common:snowflake`
Twitter의 Snowflake 알고리즘을 구현한 분산 ID 생성기
- 64bit 정수형 ID (시간순 정렬 가능)
- Node ID 기반 분산 환경 지원

#### `common:pagination`
대용량 데이터를 위한 페이징 유틸리티
- 다음 페이지 존재 여부 판단을 위한 limit 계산

### Service Modules

#### `service:article`
게시글 관리 서비스
- CRUD 기본 기능
- 무한 스크롤 페이징
- 권한 기반 수정/삭제

## 학습 목표

- [ ] Kotlin 언어의 실무 활용법 습득
- [ ] MSA 환경에서의 서비스 설계 및 구현
- [ ] Hexagonal Architecture를 통한 계층 분리
- [ ] 대용량 데이터 처리를 위한 쿼리 최적화
- [ ] 분산 환경에서의 ID 생성 전략
- [ ] RESTful API 설계 및 구현

## 향후 계획

- [ ] 추가 서비스 구현 (상품, 주문, 회원 등)
- [ ] Redis를 활용한 캐싱 전략
- [ ] Kafka를 통한 이벤트 기반 아키텍처
- [ ] 서비스 간 통신 (Feign Client, gRPC)
- [ ] API Gateway 구현
- [ ] 분산 트랜잭션 처리
- [ ] 모니터링 및 로깅 (ELK Stack)
- [ ] 컨테이너화 (Docker, Kubernetes)

## License

이 프로젝트는 개인 학습용 프로젝트입니다.