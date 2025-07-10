# Foundation Common Library

Foundation Common Library는 은행 애플리케이션을 위한 공통 라이브러리입니다. 기존의 `com.chb.coses.foundation` 패키지를 Spring Boot 기반의 모듈화된 구조로 마이그레이션했습니다.

## 📁 프로젝트 구조

```
foundation-common/
├── pom.xml                          # 메인 POM 파일
├── README.md                        # 프로젝트 문서
├── foundation-core/                 # 핵심 기능 모듈
│   ├── pom.xml
│   └── src/main/java/com/banking/foundation/core/
│       └── BaseHelper.java
├── foundation-utility/              # 유틸리티 모듈
│   ├── pom.xml
│   └── src/main/java/com/banking/foundation/utility/
│       └── StringUtils.java
├── foundation-logging/              # 로깅 모듈
│   ├── pom.xml
│   └── src/main/java/com/banking/foundation/log/
│       └── FoundationLogger.java
├── foundation-config/               # 설정 관리 모듈
│   ├── pom.xml
│   └── src/main/java/com/banking/foundation/config/
│       └── FoundationConfig.java
├── foundation-security/             # 보안 모듈
│   └── pom.xml
├── foundation-database/             # 데이터베이스 모듈
│   └── pom.xml
├── foundation-calendar/             # 캘린더 모듈
│   └── pom.xml
└── foundation-jndi/                 # JNDI 모듈
    └── pom.xml
```

## 🚀 주요 기능

### Foundation Core

- **BaseHelper**: 기본 헬퍼 클래스
- 시스템 정보, 메모리 정보, 스레드 정보 제공
- UUID 생성, 타임스탬프 관리

### Foundation Utility

- **StringUtils**: 문자열 처리 유틸리티
- 이메일, 전화번호 검증
- 케이스 변환 (camelCase, snake_case, kebab-case)
- 랜덤 문자열 생성

### Foundation Logging

- **FoundationLogger**: 향상된 로깅 기능
- 구조화된 로깅
- 성능 모니터링
- 감사 추적 (Audit Trail)
- 보안 이벤트 로깅

### Foundation Config

- **FoundationConfig**: 설정 관리
- 다중 설정 소스 지원
- 환경별 설정
- 동적 설정 업데이트
- Spring Boot 통합

## 📦 모듈별 의존성

### Foundation Core

```xml
<dependency>
    <groupId>com.banking.foundation</groupId>
    <artifactId>foundation-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Foundation Utility

```xml
<dependency>
    <groupId>com.banking.foundation</groupId>
    <artifactId>foundation-utility</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Foundation Logging

```xml
<dependency>
    <groupId>com.banking.foundation</groupId>
    <artifactId>foundation-logging</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Foundation Config

```xml
<dependency>
    <groupId>com.banking.foundation</groupId>
    <artifactId>foundation-config</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 🔧 사용 방법

### 1. 프로젝트 빌드

```bash
cd foundation-common
mvn clean install
```

### 2. 개별 모듈 빌드

```bash
cd foundation-core
mvn clean install

cd ../foundation-utility
mvn clean install
```

### 3. Spring Boot 애플리케이션에서 사용

#### BaseHelper 사용

```java
import com.banking.foundation.core.BaseHelper;

// UUID 생성
String id = BaseHelper.generateId();

// 시스템 정보 조회
Map<String, String> sysInfo = BaseHelper.getSystemInfo();

// 메모리 정보 조회
Map<String, Long> memInfo = BaseHelper.getMemoryInfo();
```

#### StringUtils 사용

```java
import com.banking.foundation.utility.StringUtils;

// 문자열 검증
boolean isValid = StringUtils.isValidEmail("test@example.com");
boolean isNumeric = StringUtils.isNumeric("12345");

// 케이스 변환
String camelCase = StringUtils.toCamelCase("user_name");
String snakeCase = StringUtils.toSnakeCase("userName");
```

#### FoundationLogger 사용

```java
import com.banking.foundation.log.FoundationLogger;

FoundationLogger logger = new FoundationLogger(MyClass.class);

logger.info("Application started");
logger.debug("Processing data: {}", data);
logger.audit("USER_LOGIN", "user123", "Login successful");
logger.security("FAILED_LOGIN", "user123", "192.168.1.1");
```

#### FoundationConfig 사용

```java
import com.banking.foundation.config.FoundationConfig;

@Autowired
private FoundationConfig config;

// 설정 값 조회
String dbUrl = config.getProperty("database.url");
int timeout = config.getIntProperty("app.timeout", 30000);

// 동적 설정 설정
config.setDynamicProperty("temp.setting", "value");

// 설정 요약 조회
Map<String, Object> summary = config.getConfigSummary();
```

## 🛠️ 개발 환경

- **Java**: 11+
- **Maven**: 3.6+
- **Spring Boot**: 3.2.0
- **Spring Framework**: 6.1.0

## 📋 마이그레이션 가이드

### 기존 코드에서 새로운 라이브러리로 변경

#### 1. 패키지 변경

```java
// 기존
import com.chb.coses.foundation.utility.StringUtils;
import com.chb.coses.foundation.log.Log;
import com.chb.coses.foundation.config.Config;

// 새로운 라이브러리
import com.banking.foundation.utility.StringUtils;
import com.banking.foundation.log.FoundationLogger;
import com.banking.foundation.config.FoundationConfig;
```

#### 2. 로깅 변경

```java
// 기존
Log.info("Message");

// 새로운 라이브러리
FoundationLogger logger = new FoundationLogger(MyClass.class);
logger.info("Message");
```

#### 3. 설정 변경

```java
// 기존
String value = Config.getProperty("key");

// 새로운 라이브러리
@Autowired
private FoundationConfig config;
String value = config.getProperty("key");
```

## 🔍 테스트

### 단위 테스트 실행

```bash
mvn test
```

### 특정 모듈 테스트

```bash
cd foundation-utility
mvn test
```

## 📝 라이센스

이 프로젝트는 내부 사용을 위한 라이브러리입니다.

## 🤝 기여

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📞 지원

문의사항이 있으시면 개발팀에 연락해주세요.
