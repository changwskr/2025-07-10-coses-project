package com.kdb.oversea.cashCard.business.cashCard.entity;

import javax.ejb.*;
import java.util.*;
import java.math.*;

public interface CashCardEB extends javax.ejb.EJBLocalObject
{
    public String getBankType();
    public String getBankCode();
    public String getPrimaryAccountNo();
    public int getSequenceNo();
    public String getCardNumber();
    public void setBranchCode(String branchCode);
    public String getBranchCode();
    public void setType(String type);
    public String getType();
    public void setCifNo(String cifNo);
    public String getCifNo();
    public void setPasswordNo(String passwordNo);
    public String getPasswordNo();
    public void setInvalidAttemptCnt(int invalidAttemptCnt);
    public int getInvalidAttemptCnt();
    public void setSecondaryAccountNo(String secondaryAccountNo);
    public String getSecondaryAccountNo();
    public void setTernaryAccountNo(String ternaryAccountNo);
    public String getTernaryAccountNo();
    public void setDailyLimitCcy(String dailyLimitCcy);
    public String getDailyLimitCcy();
    public void setDailyLimitAmount(BigDecimal dailyLimitAmount);
    public BigDecimal getDailyLimitAmount();
    public void setDailyAccumAmount(BigDecimal dailyAccumAmount);
    public BigDecimal getDailyAccumAmount();
    public void setDailyTrfLimitCcy(String dailyTrfLimitCcy);
    public String getDailyTrfLimitCcy();
    public void setDailyTrfLimitAmount(BigDecimal dailyTrfLimitAmount);
    public BigDecimal getDailyTrfLimitAmount();
    public void setDailyTrfAccumAmount(BigDecimal dailyTrfAccumAmount);
    public BigDecimal getDailyTrfAccumAmount();
    public void setDailyAccumResetDate(String dailyAccumResetDate);
    public String getDailyAccumResetDate();
    public void setDailyAccumResetTime(String dailyAccumResetTime);
    public String getDailyAccumResetTime();
    public void setEffectiveDate(String effectiveDate);
    public String getEffectiveDate();
    public void setExpiryDate(String expiryDate);
    public String getExpiryDate();
    public void setStatus(String status);
    public String getStatus();
    public void setIncidentCode(String incidentCode);
    public String getIncidentCode();
    public void setFeeWaive(String feeWaive);
    public String getFeeWaive();
    public void setFeeCcy(String feeCcy);
    public String getFeeCcy();
    public void setFeeAmount(BigDecimal feeAmount);
    public BigDecimal getFeeAmount();
    public void setRegisterDate(String registerDate);
    public String getRegisterDate();
    public void setRegisterTime(String registerTime);
    public String getRegisterTime();
    public void setRegisterBy(String registerBy);
    public String getRegisterBy();
    public void setRemark(String remark);
    public String getRemark();
    public void setLastUpdateDate(String lastUpdateDate);
    public String getLastUpdateDate();
    public void setLastUpdateTime(String lastUpdateTime);
    public String getLastUpdateTime();
    public void setLastUpdateUserId(String lastUpdateUserId);
    public String getLastUpdateUserId();
    public void setMisSendDate(String misSendDate);
    public String getMisSendDate();
    public void setCifName(String cifName);
    public String getCifName();
    public void setIssueDate(String issueDate);
    public String getIssueDate();
}

/*
6. 다음 단계 옵션
나머지 모듈 구현: cashcard, deposit, teller 모듈 상세 구현
데이터베이스 연동: JPA/Hibernate 설정 및 엔티티 생성
REST API 구현: 각 모듈별 REST 컨트롤러 작성
보안 설정: Spring Security 통합
테스트 작성: JUnit 5 기반 테스트 코드 작성
문서화: Swagger/OpenAPI 문서 생성
모니터링: Micrometer 기반 메트릭 추가
배포 설정: Docker, Kubernetes 설정


프로젝트 빌드 및 실행
나머지 모듈 구현 (Deposit, Teller)
통합 테스트 작성
CI/CD 파이프라인 설정
성능 최적화
보안 강화
 */

kdb-oversea-common 프로젝트에 대한 마이그레이션 작업이 잘못되었네 다시 확인하고 컨트롤러 서비스 레포지토리 엔티티 컨트롤러까지 확인해줘