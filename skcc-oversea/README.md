# SKCC Oversea Banking System

## 프로젝트 개요

SKCC Oversea는 레거시 Java EE/EJB 기반의 뱅킹 시스템을 현대적인 Spring Boot 마이크로서비스 아키텍처로 완전히 마이그레이션한 프로젝트입니다.

## 🎯 마이그레이션 완료 상태

### ✅ 완료된 작업

1. **EJB 요소 완전 제거**

   - `javax.ejb.*` import 모두 제거
   - `@Stateless`, `@Stateful`, `@MessageDriven` 어노테이션 제거
   - `SessionBean`, `EJBObject`, `EJBHome` 인터페이스 제거
   - `ejbCreate`, `ejbRemove` 등 생명주기 메서드 제거

2. **Spring Boot 구조로 완전 변환**

   - 모든 비즈니스 로직을 `@Service`, `@Component`로 변환
   - JPA 엔티티로 완전 변환 (`@Entity`, `@Table`, `@Id`)
   - Spring Data JPA Repository 구조 적용
   - REST API 컨트롤러 구현 (`@RestController`)

3. **아키텍처 개선**

   - Controller-Service-Repository 패턴 적용
   - 의존성 주입 (DI) 기반 구조
   - 트랜잭션 관리 (`@Transactional`)
   - 이벤트 기반 아키텍처 구현

4. **코드 품질 향상**
   - 중복 클래스 제거 및 구조 정리
   - 일관된 패키지 구조 (`com.skcc.oversea`)
   - 표준화된 예외 처리
   - 로깅 및 모니터링 개선

## 🏗️ 프로젝트 구조

```
skcc-oversea/
├── src/main/java/com/skcc/oversea/
│   ├── SkccOverseaApplication.java          # 메인 애플리케이션
│   ├── eplatonframework/
│   │   ├── business/
│   │   │   ├── controller/                  # REST API 컨트롤러
│   │   │   ├── service/                     # 비즈니스 서비스
│   │   │   ├── repository/                  # 데이터 액세스
│   │   │   ├── entity/                      # JPA 엔티티
│   │   │   ├── delegate/                    # 비즈니스 델리게이트
│   │   │   ├── event/                       # 이벤트 시스템
│   │   │   ├── interceptor/                 # AOP 인터셉터
│   │   │   └── helper/                      # 유틸리티
│   │   └── transfer/                        # DTO 및 전송 객체
│   ├── cashCard/                            # 현금카드 모듈
│   ├── deposit/                             # 예금 모듈
│   ├── teller/                              # 텔러 모듈
│   ├── common/                              # 공통 모듈
│   ├── foundation/                          # 기반 모듈
│   └── framework/                           # 프레임워크 모듈
└── src/main/resources/
    ├── application.yml                      # Spring Boot 설정
    ├── db/migration/                        # 데이터베이스 마이그레이션
    └── config/                              # 설정 파일
```

## 🚀 주요 기능

### 1. 현금카드 관리

- 카드 발급/해지/재발급
- 잔액 조회 및 거래 내역
- 보안 인증 및 승인

### 2. 예금 관리

- 계좌 개설/해지
- 입출금 처리
- 이자 계산 및 지급

### 3. 텔러 시스템

- 고객 정보 관리
- 거래 처리 및 승인
- 세션 관리

### 4. 공통 기능

- 사용자 인증/인가
- 시스템 설정 관리
- 감사 로그

## 🛠️ 기술 스택

- **Framework**: Spring Boot 3.x
- **Database**: H2 (개발), Oracle (운영)
- **ORM**: Spring Data JPA
- **API**: RESTful API
- **Build Tool**: Maven
- **Java Version**: 17+

## 📋 API 엔드포인트

### 현금카드 API

- `GET /api/cashcard` - 모든 카드 조회
- `GET /api/cashcard/{id}` - 카드 상세 조회
- `POST /api/cashcard` - 카드 발급
- `PUT /api/cashcard/{id}` - 카드 정보 수정
- `DELETE /api/cashcard/{id}` - 카드 해지

### 예금 API

- `GET /api/deposit` - 모든 계좌 조회
- `GET /api/deposit/{id}` - 계좌 상세 조회
- `POST /api/deposit` - 계좌 개설
- `PUT /api/deposit/{id}` - 계좌 정보 수정

### 거래 로그 API

- `GET /api/transaction-log` - 거래 내역 조회
- `GET /api/transaction-log/{id}` - 거래 상세 조회
- `POST /api/transaction-log` - 거래 로그 생성

## 🔧 실행 방법

### 1. 개발 환경 설정

```bash
# 프로젝트 클론
git clone <repository-url>
cd skcc-oversea

# 의존성 설치
mvn clean install
```

### 2. 애플리케이션 실행

```bash
# Spring Boot 애플리케이션 실행
mvn spring-boot:run
```

### 3. API 테스트

```bash
# 현금카드 API 테스트
curl http://localhost:8080/api/cashcard

# 예금 API 테스트
curl http://localhost:8080/api/deposit
```

## 🧪 테스트

### 단위 테스트

```bash
mvn test
```

### 통합 테스트

```bash
mvn verify
```

## 📊 모니터링

- **Health Check**: `GET /actuator/health`
- **Metrics**: `GET /actuator/metrics`
- **Logs**: 애플리케이션 로그 확인

## 🔒 보안

- Spring Security 기반 인증/인가
- JWT 토큰 기반 세션 관리
- 데이터 암호화 및 마스킹
- 감사 로그 및 추적

## 📈 성능 최적화

- 데이터베이스 커넥션 풀링
- 캐싱 전략 적용
- 비동기 처리
- 배치 처리 지원

## 🚀 배포

### Docker 배포

```bash
# Docker 이미지 빌드
docker build -t skcc-oversea .

# 컨테이너 실행
docker run -p 8080:8080 skcc-oversea
```

### Kubernetes 배포

```bash
# Kubernetes 배포
kubectl apply -f k8s/
```

## 📝 변경 이력

### v1.0.0 (2025-01-XX)

- ✅ EJB에서 Spring Boot로 완전 마이그레이션
- ✅ RESTful API 구현
- ✅ JPA 엔티티 구조 적용
- ✅ 마이크로서비스 아키텍처 구현
- ✅ 이벤트 기반 시스템 구축

## 🤝 기여

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 SKCC 내부 사용을 위한 프로젝트입니다.

## 📞 문의

- **개발팀**: SKCC Overseas Banking Team
- **이메일**: overseas-dev@skcc.com
- **문서**: [내부 위키](http://wiki.skcc.com/oversea)

---

**🎉 마이그레이션 완료!**  
레거시 EJB 시스템에서 현대적인 Spring Boot 마이크로서비스로 성공적으로 전환되었습니다.
