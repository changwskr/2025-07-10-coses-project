# COSES Framework

COSES Framework는 Spring Boot 기반의 현대적인 비즈니스 프레임워크 라이브러리입니다. 기존의 레거시 Java EE/EJB 기반 COSES Framework를 Spring Boot 환경으로 마이그레이션하여 제공합니다.

## 📋 목차

- [개요](#개요)
- [주요 기능](#주요-기능)
- [아키텍처](#아키텍처)
- [설치 및 사용법](#설치-및-사용법)
- [API 문서](#api-문서)
- [마이그레이션 가이드](#마이그레이션-가이드)
- [예제](#예제)
- [기여](#기여)

## 🎯 개요

COSES Framework는 다음과 같은 특징을 가집니다:

- **Spring Boot 기반**: 현대적인 Spring Boot 3.x 기반으로 구축
- **모듈화된 구조**: 재사용 가능한 컴포넌트로 구성
- **타입 안전성**: Java 11의 강력한 타입 시스템 활용
- **확장성**: 플러그인 방식의 확장 가능한 아키텍처
- **성능**: 비동기 처리 및 캐싱 지원

## ✨ 주요 기능

### 1. Transfer Objects (DTO)

- **IDTO**: 모든 DTO의 기본 인터페이스
- **DTO**: 공통 DTO 기능을 제공하는 추상 클래스
- **IEvent**: 이벤트 객체의 기본 인터페이스
- **CosesEvent**: 이벤트 처리 클래스

### 2. Business Layer

- **AbstractSessionBean**: 세션 관리 기본 클래스
- **AbstractEntityBean**: 엔티티 관리 기본 클래스
- **트랜잭션 관리**: Spring의 선언적 트랜잭션 지원

### 3. Exception Handling

- **CosesAppException**: 기본 예외 클래스
- **BizActionException**: 비즈니스 액션 예외
- **BizDelegateException**: 비즈니스 델리게이트 예외

### 4. Constants

- **Constants**: 일반 상수
- **EventConstants**: 이벤트 관련 상수

## 🏗️ 아키텍처

```
coses-framework/
├── src/main/java/com/banking/coses/framework/
│   ├── business/           # 비즈니스 로직 레이어
│   │   ├── AbstractSessionBean.java
│   │   └── AbstractEntityBean.java
│   ├── transfer/           # 데이터 전송 객체
│   │   ├── IDTO.java
│   │   ├── DTO.java
│   │   ├── IEvent.java
│   │   └── CosesEvent.java
│   ├── exception/          # 예외 처리
│   │   ├── CosesAppException.java
│   │   ├── BizActionException.java
│   │   └── BizDelegateException.java
│   ├── constants/          # 상수 정의
│   │   ├── Constants.java
│   │   └── EventConstants.java
│   └── config/             # 설정
│       └── CosesFrameworkConfig.java
```

## 🚀 설치 및 사용법

### 1. Maven 의존성 추가

```xml
<dependency>
    <groupId>com.banking.coses</groupId>
    <artifactId>coses-framework</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 2. Foundation Common Library 의존성

```xml
<dependency>
    <groupId>com.banking.foundation</groupId>
    <artifactId>foundation-core</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.banking.foundation</groupId>
    <artifactId>foundation-utility</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.banking.foundation</groupId>
    <artifactId>foundation-logging</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.banking.foundation</groupId>
    <artifactId>foundation-config</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 3. Spring Boot 설정

```java
@SpringBootApplication
@Import(CosesFrameworkConfig.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## 📚 API 문서

### DTO 사용법

```java
public class UserDTO extends DTO {
    private String name;
    private String email;

    // getters and setters
}

// 사용 예제
UserDTO user = new UserDTO();
user.setName("홍길동");
user.setEmail("hong@example.com");
user.validate(); // 유효성 검사
```

### Event 사용법

```java
CosesEvent event = new CosesEvent("USER_CREATE", "CREATE_USER");
event.setRequest(userDTO);
event.setSourceSystem("WEB");
event.setTargetSystem("DATABASE");

// 이벤트 처리
if (event.isValid()) {
    event.markProcessing();
    // 비즈니스 로직 처리
    event.markSuccess();
}
```

### Session Bean 사용법

```java
@Service
public class UserService extends AbstractSessionBean {

    public UserDTO createUser(UserDTO userDTO) throws CosesAppException {
        validateSession();
        logSessionActivity("CREATE_USER", "Creating new user: " + userDTO.getName());

        // 비즈니스 로직
        return userDTO;
    }
}
```

### Entity Bean 사용법

```java
@Service
public class UserEntityBean extends AbstractEntityBean<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        setRepository(userRepository);
    }

    @Override
    protected Long getEntityId(User entity) {
        return entity.getId();
    }
}
```

## 🔄 마이그레이션 가이드

### 레거시에서 Spring Boot로 마이그레이션

#### 1. EJB Session Bean → Spring Service

**레거시 코드:**

```java
public class UserSessionBean extends AbstractSessionBean {
    // EJB 기반 코드
}
```

**Spring Boot 코드:**

```java
@Service
@Transactional
public class UserService extends AbstractSessionBean {
    // Spring Boot 기반 코드
}
```

#### 2. DTO 마이그레이션

**레거시 코드:**

```java
public class UserDTO extends com.chb.coses.framework.transfer.DTO {
    // 레거시 DTO
}
```

**Spring Boot 코드:**

```java
public class UserDTO extends com.banking.coses.framework.transfer.DTO {
    // 새로운 DTO
}
```

#### 3. 예외 처리 마이그레이션

**레거시 코드:**

```java
throw new com.chb.coses.framework.exception.CosesAppException("ERROR", "Message");
```

**Spring Boot 코드:**

```java
throw new com.banking.coses.framework.exception.CosesAppException("ERROR", "Message");
```

## 💡 예제

### 1. 간단한 비즈니스 서비스

```java
@Service
@Transactional
public class CashCardService extends AbstractSessionBean {

    @Autowired
    private CashCardRepository cashCardRepository;

    public CashCardDTO createCard(CashCardDTO cardDTO) throws CosesAppException {
        validateSession();
        logSessionActivity("CREATE_CARD", "Creating card for user: " + cardDTO.getUserId());

        // 비즈니스 로직
        CashCard card = new CashCard();
        card.setCardNumber(generateCardNumber());
        card.setUserId(cardDTO.getUserId());
        card.setStatus("ACTIVE");

        CashCard savedCard = cashCardRepository.save(card);

        // DTO 변환
        CashCardDTO result = new CashCardDTO();
        result.copyFrom(savedCard);

        return result;
    }
}
```

### 2. 이벤트 기반 처리

```java
@Component
public class EventProcessor {

    public void processEvent(CosesEvent event) {
        try {
            event.validate();
            event.markProcessing();

            switch (event.getAction()) {
                case "CREATE_USER":
                    processUserCreation(event);
                    break;
                case "UPDATE_USER":
                    processUserUpdate(event);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown action: " + event.getAction());
            }

            event.markSuccess();
        } catch (Exception e) {
            event.markFailed("PROCESSING_ERROR", e.getMessage());
            throw e;
        }
    }
}
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
- **이메일**: support@banking.com

---

**COSES Framework** - 현대적인 Spring Boot 기반 비즈니스 프레임워크
