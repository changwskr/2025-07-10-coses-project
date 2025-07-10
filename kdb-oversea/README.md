# KDB Oversea Banking System

KDB Oversea Banking System은 Spring Boot 기반의 현대적인 해외은행 시스템입니다. 기존의 레거시 Java EE/EJB 기반 시스템을 Spring Boot 환경으로 마이그레이션하여 제공합니다.

## 📋 목차

- [개요](#개요)
- [프로젝트 구조](#프로젝트-구조)
- [주요 기능](#주요-기능)
- [아키텍처](#아키텍처)
- [설치 및 사용법](#설치-및-사용법)
- [API 문서](#api-문서)
- [마이그레이션 가이드](#마이그레이션-가이드)
- [예제](#예제)
- [기여](#기여)

## 🎯 개요

KDB Oversea Banking System은 다음과 같은 특징을 가집니다:

- **Spring Boot 기반**: 현대적인 Spring Boot 3.x 기반으로 구축
- **다중 모듈 구조**: 재사용 가능한 모듈로 구성
- **타입 안전성**: Java 11의 강력한 타입 시스템 활용
- **확장성**: 마이크로서비스 아키텍처 지원
- **성능**: 비동기 처리 및 캐싱 지원

## 🏗️ 프로젝트 구조

```
kdb-oversea/
├── kdb-oversea-foundation/     # Foundation 라이브러리
├── kdb-oversea-framework/      # Framework 라이브러리
├── kdb-oversea-eplaton/        # EPlaton Framework
├── kdb-oversea-common/         # 공통 모듈
├── kdb-oversea-cashcard/       # 현금카드 모듈
├── kdb-oversea-deposit/        # 예금 모듈
├── kdb-oversea-teller/         # 창구 모듈
└── kdb-oversea-app/            # 메인 애플리케이션
```

### **모듈별 설명**

#### **1. kdb-oversea-foundation**

- 설정 관리 (`KdbConfig`)
- 유틸리티 클래스들
- 상수 정의
- 로깅 및 에러 처리

#### **2. kdb-oversea-framework**

- 트랜잭션 관리
- 프레임워크 상수
- 전송 객체들

#### **3. kdb-oversea-eplaton**

- EPlaton 비즈니스 델리게이트 (`EPlatonBizDelegateService`)
- EPlaton 비즈니스 액션 (`EPlatonBizAction`)
- EPlaton 이벤트 (`EPlatonEvent`)
- EPlaton DTO들 (`EPlatonCommonDTO`, `TPSVCINFODTO`)

#### **4. kdb-oversea-common**

- 공통 비즈니스 로직
- 공통 DTO들
- 공통 유틸리티

#### **5. kdb-oversea-cashcard**

- 현금카드 관리
- 카드 발급/해지
- 카드 거래 처리

#### **6. kdb-oversea-deposit**

- 예금 계좌 관리
- 입출금 처리
- 이자 계산

#### **7. kdb-oversea-teller**

- 창구 업무 처리
- 고객 서비스
- 거래 승인

#### **8. kdb-oversea-app**

- 메인 애플리케이션
- REST API 엔드포인트
- 설정 및 초기화

## ✨ 주요 기능

### **1. 현금카드 관리**

- 카드 발급 및 해지
- 카드 상태 관리
- 거래 내역 조회
- 한도 관리

### **2. 예금 관리**

- 계좌 개설 및 해지
- 입출금 처리
- 이자 계산 및 지급
- 계좌 상태 관리

### **3. 창구 업무**

- 고객 정보 관리
- 거래 승인
- 서류 처리
- 보고서 생성

### **4. 시스템 관리**

- 사용자 관리
- 권한 관리
- 시스템 모니터링
- 로그 관리

## 🏗️ 아키텍처

### **레이어 구조**

```
┌─────────────────────────────────────┐
│           REST API Layer            │
├─────────────────────────────────────┤
│         Business Layer              │
├─────────────────────────────────────┤
│         Service Layer               │
├─────────────────────────────────────┤
│         Repository Layer            │
├─────────────────────────────────────┤
│         Database Layer              │
└─────────────────────────────────────┘
```

### **기술 스택**

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 11
- **Build Tool**: Maven
- **Database**: (설정에 따라)
- **Logging**: SLF4J + Logback
- **Validation**: Jakarta Validation
- **JSON**: Jackson
- **Testing**: JUnit 5 + Mockito

## 🚀 설치 및 사용법

### **1. 사전 요구사항**

- Java 11 이상
- Maven 3.6 이상
- (선택) IDE (IntelliJ IDEA, Eclipse 등)

### **2. 프로젝트 빌드**

```bash
# 전체 프로젝트 빌드
mvn clean install

# 특정 모듈만 빌드
mvn clean install -pl kdb-oversea-foundation

# 테스트 제외하고 빌드
mvn clean install -DskipTests
```

### **3. 애플리케이션 실행**

```bash
# 메인 애플리케이션 실행
cd kdb-oversea-app
mvn spring-boot:run

# 또는 JAR 파일로 실행
java -jar target/kdb-oversea-app-1.0.0.jar
```

### **4. 설정 파일**

`application.yml` 예제:

```yaml
server:
  port: 8080

spring:
  application:
    name: kdb-oversea

kdb:
  oversea:
    machine:
      mode: DEV
    environment: development
    config:
      file: classpath:config/kdb-oversea.properties
      xml:
        file: classpath:config/kdb-oversea.xml

logging:
  level:
    com.banking.kdb.oversea: DEBUG
    com.banking.foundation: INFO
```

## 📚 API 문서

### **EPlaton 이벤트 처리**

```java
// 이벤트 생성
EPlatonEvent event = new EPlatonEvent("CASH_CARD_CREATE", "CREATE_CARD");
event.setRequest(cardRequestDTO);
event.setSourceSystem("WEB");
event.setTargetSystem("DATABASE");

// 비즈니스 델리게이트로 실행
EPlatonBizDelegateService delegateService = applicationContext.getBean(EPlatonBizDelegateService.class);
IEvent resultEvent = delegateService.execute(event);
```

### **비즈니스 액션 구현**

```java
@Service
public class CashCardCreateAction extends EPlatonBizAction {

    @Override
    public IEvent act(IEvent event) throws BizActionException {
        // 입력 검증
        validateInput(event);

        // 로그 기록
        logActionExecution(event, "CREATE_CASH_CARD");

        try {
            // 비즈니스 로직 처리
            EPlatonCommonDTO request = getRequest(event);
            // ... 카드 생성 로직

            // 성공 응답 설정
            setSuccessResponse(event, responseDTO);
            logActionCompletion(event, "CREATE_CASH_CARD", true);

        } catch (Exception e) {
            // 에러 응답 설정
            setErrorResponse(event, "ECARD001", "카드 생성 실패: " + e.getMessage());
            logActionCompletion(event, "CREATE_CASH_CARD", false);
            throw new BizActionException("ECARD001", "카드 생성 실패", e);
        }

        return event;
    }
}
```

### **REST API 엔드포인트**

```java
@RestController
@RequestMapping("/api/v1/cashcard")
public class CashCardController {

    @Autowired
    private EPlatonBizDelegateService delegateService;

    @PostMapping("/create")
    public ResponseEntity<EPlatonEvent> createCard(@RequestBody EPlatonEvent event) {
        try {
            IEvent resultEvent = delegateService.execute(event);
            return ResponseEntity.ok((EPlatonEvent) resultEvent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
```

## 🔄 마이그레이션 가이드

### **레거시에서 Spring Boot로 마이그레이션**

#### **1. EJB Session Bean → Spring Service**

**레거시 코드:**

```java
public class CashCardSessionBean extends AbstractSessionBean {
    // EJB 기반 코드
}
```

**Spring Boot 코드:**

```java
@Service
@Transactional
public class CashCardService extends AbstractSessionBean {
    // Spring Boot 기반 코드
}
```

#### **2. DTO 마이그레이션**

**레거시 코드:**

```java
public class CashCardDTO extends com.kdb.oversea.eplatonframework.transfer.EPlatonCommonDTO {
    // 레거시 DTO
}
```

**Spring Boot 코드:**

```java
public class CashCardDTO extends com.banking.kdb.oversea.eplaton.transfer.EPlatonCommonDTO {
    // 새로운 DTO
}
```

#### **3. 설정 마이그레이션**

**레거시 코드:**

```java
String value = Config.getInstance().getValue("service", "element");
```

**Spring Boot 코드:**

```java
@Autowired
private KdbConfig kdbConfig;

String value = kdbConfig.getServiceValue("service", "element");
```

## 💡 예제

### **1. 현금카드 생성 예제**

```java
// 요청 DTO 생성
CashCardCreateRequestDTO request = new CashCardCreateRequestDTO();
request.setUserId("USER001");
request.setCardType("DEBIT");
request.setLimit(1000000);

// 이벤트 생성
EPlatonEvent event = new EPlatonEvent("CASH_CARD_CREATE", "CREATE_CARD");
event.setRequest(request);
event.setSourceSystem("WEB");
event.setTargetSystem("DATABASE");

// TPSVCINFO 설정
TPSVCINFODTO tpsvcInfo = event.getTPSVCINFODTO();
tpsvcInfo.setUser_id("USER001");
tpsvcInfo.setBranch_code("001");
tpsvcInfo.setTeller_id("TELLER001");

// 비즈니스 로직 실행
EPlatonBizDelegateService delegateService = applicationContext.getBean(EPlatonBizDelegateService.class);
IEvent resultEvent = delegateService.execute(event);

// 결과 확인
EPlatonEvent result = (EPlatonEvent) resultEvent;
if (result.hasErrors()) {
    System.out.println("Error: " + result.getErrorMessage());
} else {
    System.out.println("Success: Card created");
}
```

### **2. 예금 계좌 개설 예제**

```java
// 요청 DTO 생성
DepositAccountCreateRequestDTO request = new DepositAccountCreateRequestDTO();
request.setUserId("USER001");
request.setAccountType("SAVINGS");
request.setInitialAmount(1000000);

// 이벤트 생성 및 실행
EPlatonEvent event = new EPlatonEvent("DEPOSIT_ACCOUNT_CREATE", "CREATE_ACCOUNT");
event.setRequest(request);

EPlatonBizDelegateService delegateService = applicationContext.getBean(EPlatonBizDelegateService.class);
IEvent resultEvent = delegateService.execute(event);
```

## 🤝 기여

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 `LICENSE` 파일을 참조하세요.

## 📞 지원

- **이슈 리포트**: GitHub Issues 사용
- **문서**: 이 README 및 코드 주석 참조
- **이메일**: support@kdb.co.kr

---

**KDB Oversea Banking System** - 현대적인 Spring Boot 기반 해외은행 시스템
