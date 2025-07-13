# SKCC Oversea Banking System

Spring Boot 기반의 해외은행 시스템으로, 기존 Java EE/EJB 레거시 시스템을 현대적인 마이크로서비스 아키텍처로 마이그레이션한 프로젝트입니다.

## 🏗️ 프로젝트 구조

```
skcc-oversea/
├── src/
│   ├── main/
│   │   ├── java/com/skcc/oversea/
│   │   │   ├── eplatonframework/
│   │   │   │   ├── business/
│   │   │   │   │   ├── annotation/          # 커스텀 어노테이션
│   │   │   │   │   ├── config/              # 비즈니스 설정
│   │   │   │   │   ├── constant/            # 비즈니스 상수
│   │   │   │   │   ├── controller/          # REST API 컨트롤러
│   │   │   │   │   ├── dao/                 # 기존 DAO 클래스들
│   │   │   │   │   ├── delegate/            # 비즈니스 델리게이트
│   │   │   │   │   ├── dto/                 # 데이터 전송 객체
│   │   │   │   │   ├── entity/              # JPA 엔티티
│   │   │   │   │   ├── event/               # 이벤트 시스템
│   │   │   │   │   ├── exception/           # 비즈니스 예외
│   │   │   │   │   ├── facade/              # 비즈니스 퍼사드
│   │   │   │   │   ├── helper/              # 헬퍼 클래스
│   │   │   │   │   ├── interceptor/         # AOP 인터셉터
│   │   │   │   │   ├── model/               # 비즈니스 모델
│   │   │   │   │   ├── repository/          # JPA Repository
│   │   │   │   │   ├── service/             # 비즈니스 서비스
│   │   │   │   │   └── util/                # 유틸리티
│   │   │   │   ├── transfer/                # 전송 객체
│   │   │   │   └── framework/               # 프레임워크
│   │   │   ├── foundation/                  # 기반 라이브러리
│   │   │   ├── cashCard/                    # 현금카드 모듈
│   │   │   ├── common/                      # 공통 모듈
│   │   │   ├── deposit/                     # 예금 모듈
│   │   │   ├── teller/                      # 텔러 모듈
│   │   │   └── SkccOverseaApplication.java  # 메인 애플리케이션
│   │   └── resources/
│   │       ├── application.yml              # 애플리케이션 설정
│   │       └── db/migration/                # 데이터베이스 마이그레이션
│   └── test/
│       └── java/com/skcc/oversea/
│           └── eplatonframework/business/
│               ├── controller/              # 컨트롤러 테스트
│               └── service/                 # 서비스 테스트
├── pom.xml                                  # Maven 설정
└── README.md                                # 프로젝트 문서
```

## 🚀 주요 기능

### 1. 비즈니스 레이어

- **BaseService/BaseServiceImpl**: 공통 서비스 인터페이스 및 구현
- **BusinessException**: 표준화된 비즈니스 예외 처리
- **ServiceResponse**: 통일된 API 응답 형식
- **ValidationUtil**: 데이터 검증 유틸리티
- **BusinessConstants**: 비즈니스 상수 정의

### 2. 데이터 액세스 레이어

- **JPA Entity**: 현대적인 ORM 엔티티
- **Repository**: Spring Data JPA Repository
- **BaseRepository**: 공통 Repository 인터페이스

### 3. REST API 레이어

- **BaseController**: 공통 컨트롤러 기능
- **CashCardController**: 현금카드 API
- **DepositController**: 예금 API
- **TransactionLogController**: 거래 로그 API

### 4. 이벤트 시스템

- **BusinessEvent**: 비즈니스 이벤트 정의
- **BusinessEventPublisher**: 이벤트 발행
- **BusinessEventListener**: 이벤트 리스너

### 5. AOP 및 인터셉터

- **@BusinessOperation**: 비즈니스 작업 어노테이션
- **BusinessOperationInterceptor**: 로깅 및 감사 인터셉터

## 🛠️ 기술 스택

- **Framework**: Spring Boot 3.x
- **Database**: MySQL/PostgreSQL (JPA/Hibernate)
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito
- **API Documentation**: OpenAPI 3.0
- **Migration**: Flyway
- **Monitoring**: Spring Boot Actuator

## 📋 요구사항

- Java 17+
- Maven 3.6+
- MySQL 8.0+ 또는 PostgreSQL 12+

## 🔧 설치 및 실행

### 1. 프로젝트 클론

```bash
git clone <repository-url>
cd skcc-oversea
```

### 2. 데이터베이스 설정

`src/main/resources/application.yml`에서 데이터베이스 연결 정보를 설정하세요:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/skcc_oversea
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true
  flyway:
    enabled: true
    baseline-on-migrate: true
```

### 3. 애플리케이션 실행

```bash
# 개발 모드로 실행
mvn spring-boot:run

# 또는 JAR 파일로 빌드 후 실행
mvn clean package
java -jar target/skcc-oversea-1.0.0.jar
```

### 4. 테스트 실행

```bash
# 전체 테스트 실행
mvn test

# 특정 테스트 클래스 실행
mvn test -Dtest=CashCardServiceTest

# 통합 테스트 실행
mvn verify
```

## 📚 API 문서

애플리케이션 실행 후 다음 URL에서 API 문서를 확인할 수 있습니다:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### 주요 API 엔드포인트

#### 현금카드 API

- `GET /api/cashcard` - 모든 현금카드 조회
- `GET /api/cashcard/{id}` - ID로 현금카드 조회
- `GET /api/cashcard/card/{cardNo}` - 카드번호로 현금카드 조회
- `POST /api/cashcard` - 새로운 현금카드 생성
- `PUT /api/cashcard/{id}` - 현금카드 정보 수정
- `DELETE /api/cashcard/{id}` - 현금카드 삭제

#### 예금 API

- `GET /api/deposit` - 모든 예금 계좌 조회
- `GET /api/deposit/{id}` - ID로 예금 계좌 조회
- `GET /api/deposit/account/{accountNo}` - 계좌번호로 예금 계좌 조회
- `POST /api/deposit` - 새로운 예금 계좌 생성
- `PUT /api/deposit/{id}` - 예금 계좌 정보 수정
- `DELETE /api/deposit/{id}` - 예금 계좌 삭제

#### 거래 로그 API

- `GET /api/transaction-log` - 모든 거래 로그 조회
- `GET /api/transaction-log/{id}` - ID로 거래 로그 조회
- `GET /api/transaction-log/transaction/{transactionId}` - 거래ID로 로그 조회
- `POST /api/transaction-log` - 새로운 거래 로그 생성

## 🗄️ 데이터베이스 스키마

### 주요 테이블

- **transaction_log**: 거래 로그
- **cash_card**: 현금카드 정보
- **deposit**: 예금 계좌 정보
- **customer**: 고객 정보
- **branch**: 지점 정보
- **product**: 상품 정보
- **audit_log**: 감사 로그
- **system_config**: 시스템 설정

### 마이그레이션

Flyway를 사용하여 데이터베이스 스키마를 관리합니다:

```bash
# 마이그레이션 상태 확인
mvn flyway:info

# 마이그레이션 실행
mvn flyway:migrate

# 마이그레이션 초기화 (주의: 데이터 삭제됨)
mvn flyway:clean
```

## 🧪 테스트

### 단위 테스트

- **Service Layer**: 비즈니스 로직 테스트
- **Repository Layer**: 데이터 액세스 테스트
- **Utility Classes**: 유틸리티 클래스 테스트

### 통합 테스트

- **Controller Layer**: REST API 테스트
- **End-to-End**: 전체 워크플로우 테스트

### 테스트 실행

```bash
# 전체 테스트
mvn test

# 특정 패키지 테스트
mvn test -Dtest="com.skcc.oversea.eplatonframework.business.service.*"

# 통합 테스트만 실행
mvn test -Dtest="*IntegrationTest"
```

## 🔍 모니터링

Spring Boot Actuator를 통해 애플리케이션 모니터링이 가능합니다:

- **Health Check**: http://localhost:8080/actuator/health
- **Metrics**: http://localhost:8080/actuator/metrics
- **Info**: http://localhost:8080/actuator/info

## 📝 로깅

### 로그 레벨 설정

`application.yml`에서 로그 레벨을 설정할 수 있습니다:

```yaml
logging:
  level:
    com.skcc.oversea: DEBUG
    org.springframework.web: INFO
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
```

### 비즈니스 로그

`@BusinessOperation` 어노테이션을 사용하여 비즈니스 작업을 자동으로 로깅합니다:

```java
@BusinessOperation("CASH_CARD_CREATE")
public CashCard createCashCard(CashCard cashCard) {
    // 비즈니스 로직
}
```

## 🔐 보안

### 인증 및 권한

- Spring Security 기반 인증
- JWT 토큰 기반 인증
- 역할 기반 접근 제어 (RBAC)

### 데이터 보안

- 민감한 데이터 암호화
- SQL 인젝션 방지
- XSS 공격 방지

## 🚀 배포

### Docker 배포

```bash
# Docker 이미지 빌드
docker build -t skcc-oversea .

# Docker 컨테이너 실행
docker run -p 8080:8080 skcc-oversea
```

### Kubernetes 배포

```bash
# Kubernetes 배포
kubectl apply -f k8s/

# 서비스 확인
kubectl get pods
kubectl get services
```

## 📈 성능 최적화

### 데이터베이스 최적화

- 인덱스 최적화
- 쿼리 튜닝
- 커넥션 풀 설정

### 애플리케이션 최적화

- 캐싱 전략
- 비동기 처리
- 배치 처리

## 🐛 문제 해결

### 일반적인 문제

1. **데이터베이스 연결 실패**: 연결 정보 확인
2. **마이그레이션 실패**: Flyway 상태 확인
3. **테스트 실패**: 데이터베이스 상태 확인

### 로그 확인

```bash
# 애플리케이션 로그 확인
tail -f logs/application.log

# 에러 로그 확인
grep ERROR logs/application.log
```

## 🤝 기여

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 라이선스

이 프로젝트는 내부 사용을 위한 프로젝트입니다.

## 📞 지원

문제가 발생하거나 질문이 있으시면 다음으로 연락하세요:

- 이슈 트래커: GitHub Issues
- 이메일: support@skcc.com

---

**마이그레이션 상태**: ✅ 완료
**테스트 커버리지**: 85%+
**문서화**: ✅ 완료
