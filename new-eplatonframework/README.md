# EPlaton Framework - Spring Boot Migration

## 개요

EPlaton Framework는 기존 Java EE/EJB 기반의 레거시 뱅킹 시스템을 Spring Boot로 마이그레이션한 프레임워크입니다.
원본 아키텍처를 최대한 유지하면서 Spring Boot의 장점을 활용하여 현대적인 마이크로서비스 아키텍처로 전환했습니다.

## 주요 특징

- **Spring Boot 기반**: 현대적인 Spring Boot 프레임워크 사용
- **JPA/Hibernate**: 데이터 액세스 계층 현대화
- **RESTful API**: REST API 기반의 서비스 제공
- **마이크로서비스**: 모듈화된 서비스 구조
- **보안**: Spring Security 기반 보안 구현
- **모니터링**: Actuator를 통한 애플리케이션 모니터링
- **로깅**: SLF4J/Logback 기반 로깅 시스템

## 프로젝트 구조

```
new-eplatonframework/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/chb/coses/eplatonFMK/
│   │   │       ├── business/
│   │   │       │   ├── constants/          # 상수 정의
│   │   │       │   ├── dao/                # 데이터 액세스 객체
│   │   │       │   ├── delegate/           # 비즈니스 델리게이트
│   │   │       │   ├── facade/             # 퍼사드 패턴 구현
│   │   │       │   │   ├── cashCard/       # 현금카드 관리
│   │   │       │   │   ├── deposit/        # 예금 관리
│   │   │       │   │   └── ecommon/        # 공통 관리
│   │   │       │   ├── helper/             # 헬퍼 클래스
│   │   │       │   ├── model/              # 데이터 모델
│   │   │       │   └── tcf/                # 트랜잭션 제어 프레임워크
│   │   │       └── transfer/               # 데이터 전송 객체
│   │   └── resources/
│   │       └── application.yml             # 애플리케이션 설정
│   └── test/
│       └── java/
│           └── com/chb/coses/eplatonFMK/   # 테스트 코드
├── pom.xml                                 # Maven 설정
└── README.md                               # 프로젝트 문서
```

## 주요 컴포넌트

### 1. TCF (Transaction Control Framework)

- **TCF**: 메인 트랜잭션 제어 프레임워크
- **STF**: 서비스 트랜잭션 프레임워크
- **ETF**: 에러 트랜잭션 프레임워크
- **BTF**: 비즈니스 트랜잭션 프레임워크

### 2. DAO (Data Access Object)

- **EPlatonDelegateDAO**: 메인 DAO 구현
- **TransactionLogEntity**: 트랜잭션 로그 엔티티
- **AbstractEntity**: 추상 엔티티 클래스

### 3. Facade 패턴

- **CashCardManagementSB**: 현금카드 관리 서비스
- **DepositManagementSB**: 예금 관리 서비스
- **ECommonManagementSB**: 공통 관리 서비스

### 4. Helper 클래스

- **CommonUtil**: 공통 유틸리티
- **DTOConverter**: DTO 변환기
- **ERRORUtils**: 에러 처리 유틸리티
- **TPMSVCAPI**: TPM 서비스 API

## 기술 스택

- **Java**: 17+
- **Spring Boot**: 3.x
- **Spring Data JPA**: 데이터 액세스
- **Spring Security**: 보안
- **PostgreSQL**: 데이터베이스
- **Maven**: 빌드 도구
- **JUnit 5**: 테스트 프레임워크
- **SLF4J/Logback**: 로깅

## 설치 및 실행

### 1. 사전 요구사항

- Java 17 이상
- Maven 3.6 이상
- PostgreSQL 12 이상

### 2. 데이터베이스 설정

```sql
CREATE DATABASE eplaton_db;
CREATE USER eplaton_user WITH PASSWORD 'eplaton_pass';
GRANT ALL PRIVILEGES ON DATABASE eplaton_db TO eplaton_user;
```

### 3. 애플리케이션 실행

```bash
# 프로젝트 빌드
mvn clean install

# 애플리케이션 실행
mvn spring-boot:run
```

### 4. 환경 변수 설정 (선택사항)

```bash
export DB_USERNAME=eplaton_user
export DB_PASSWORD=eplaton_pass
export JWT_SECRET=your-secret-key
```

## API 엔드포인트

### 헬스 체크

- `GET /eplaton-framework/actuator/health`

### 메트릭스

- `GET /eplaton-framework/actuator/metrics`

### 애플리케이션 정보

- `GET /eplaton-framework/actuator/info`

## 테스트

### 단위 테스트 실행

```bash
mvn test
```

### 통합 테스트 실행

```bash
mvn verify
```

## 로깅

애플리케이션 로그는 `logs/eplaton-framework.log` 파일에 저장됩니다.

로그 레벨 설정:

```yaml
logging:
  level:
    com.chb.coses.eplatonFMK: DEBUG
```

## 모니터링

Spring Boot Actuator를 통해 애플리케이션 모니터링이 가능합니다:

- **Health Check**: `/actuator/health`
- **Metrics**: `/actuator/metrics`
- **Info**: `/actuator/info`
- **Prometheus**: `/actuator/prometheus`

## 보안

Spring Security를 사용하여 보안을 구현했습니다:

- JWT 기반 인증
- 역할 기반 접근 제어 (RBAC)
- API 엔드포인트 보안

## 마이그레이션 가이드

### 기존 EJB에서 Spring Boot로 전환

1. **EJB Session Bean** → **Spring @Service**
2. **EJB Home Interface** → **Spring Repository**
3. **JNDI Lookup** → **Spring Dependency Injection**
4. **EJB Transaction** → **Spring @Transactional**
5. **EJB Security** → **Spring Security**

### 주요 변경사항

- `@Stateless` → `@Service`
- `@Remote` → REST API
- `@Local` → `@Autowired`
- `@TransactionAttribute` → `@Transactional`
- `@RolesAllowed` → `@PreAuthorize`

## 문제 해결

### 일반적인 문제

1. **데이터베이스 연결 실패**

   - PostgreSQL 서비스 실행 확인
   - 데이터베이스 접속 정보 확인

2. **포트 충돌**

   - `application.yml`에서 포트 변경
   - 기존 프로세스 종료

3. **메모리 부족**
   - JVM 힙 크기 조정
   - `-Xmx2g -Xms1g` 옵션 추가

## 기여 가이드

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 라이선스

이 프로젝트는 내부 사용을 위한 프로젝트입니다.

## 연락처

프로젝트 관련 문의사항이 있으시면 개발팀에 연락해 주세요.
