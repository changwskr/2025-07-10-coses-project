package com.skcc.oversea.cashCard.business.cashCard.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Cash Card Entity for SKCC Oversea
 * JPA entity replacing EJB entity bean
 */
@Entity
@Table(name = "CASH_CARD")
public class CashCard {

    @EmbeddedId
    private CashCardPK id;

    @Column(name = "BRANCH_CODE", length = 10)
    private String branchCode;

    @Column(name = "TYPE", length = 2)
    private String type;

    @Column(name = "CIF_NO", length = 20)
    private String cifNo;

    @Column(name = "PASSWORD_NO", length = 100)
    private String passwordNo;

    @Column(name = "INVALID_ATTEMPT_CNT")
    private Integer invalidAttemptCnt;

    @Column(name = "SECONDARY_ACCOUNT_NO", length = 20)
    private String secondaryAccountNo;

    @Column(name = "TERNARY_ACCOUNT_NO", length = 20)
    private String ternaryAccountNo;

    @Column(name = "DAILY_LIMIT_CCY", length = 3)
    private String dailyLimitCcy;

    @Column(name = "DAILY_LIMIT_AMOUNT", precision = 15, scale = 2)
    private BigDecimal dailyLimitAmount;

    @Column(name = "DAILY_ACCUM_AMOUNT", precision = 15, scale = 2)
    private BigDecimal dailyAccumAmount;

    @Column(name = "DAILY_TRF_LIMIT_CCY", length = 3)
    private String dailyTrfLimitCcy;

    @Column(name = "DAILY_TRF_LIMIT_AMOUNT", precision = 15, scale = 2)
    private BigDecimal dailyTrfLimitAmount;

    @Column(name = "DAILY_TRF_ACCUM_AMOUNT", precision = 15, scale = 2)
    private BigDecimal dailyTrfAccumAmount;

    @Column(name = "DAILY_ACCUM_RESET_DATE", length = 8)
    private String dailyAccumResetDate;

    @Column(name = "DAILY_ACCUM_RESET_TIME", length = 6)
    private String dailyAccumResetTime;

    @Column(name = "EFFECTIVE_DATE", length = 8)
    private String effectiveDate;

    @Column(name = "EXPIRY_DATE", length = 8)
    private String expiryDate;

    @Column(name = "STATUS", length = 2)
    private String status;

    @Column(name = "INCIDENT_CODE", length = 10)
    private String incidentCode;

    @Column(name = "FEE_WAIVE", length = 1)
    private String feeWaive;

    @Column(name = "FEE_CCY", length = 3)
    private String feeCcy;

    @Column(name = "FEE_AMOUNT", precision = 10, scale = 2)
    private BigDecimal feeAmount;

    @Column(name = "REGISTER_DATE", length = 8)
    private String registerDate;

    @Column(name = "REGISTER_TIME", length = 6)
    private String registerTime;

    @Column(name = "REGISTER_BY", length = 20)
    private String registerBy;

    @Column(name = "REMARK", length = 200)
    private String remark;

    @Column(name = "LAST_UPDATE_DATE", length = 8)
    private String lastUpdateDate;

    @Column(name = "LAST_UPDATE_TIME", length = 6)
    private String lastUpdateTime;

    @Column(name = "LAST_UPDATE_USER_ID", length = 20)
    private String lastUpdateUserId;

    @Column(name = "MIS_SEND_DATE", length = 8)
    private String misSendDate;

    @Column(name = "CIF_NAME", length = 100)
    private String cifName;

    @Column(name = "ISSUE_DATE", length = 8)
    private String issueDate;

    // Constructors
    public CashCard() {
    }

    public CashCard(CashCardPK id) {
        this.id = id;
    }

    // Getters and Setters
    public CashCardPK getId() {
        return id;
    }

    public void setId(CashCardPK id) {
        this.id = id;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCifNo() {
        return cifNo;
    }

    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    public String getPasswordNo() {
        return passwordNo;
    }

    public void setPasswordNo(String passwordNo) {
        this.passwordNo = passwordNo;
    }

    public Integer getInvalidAttemptCnt() {
        return invalidAttemptCnt;
    }

    public void setInvalidAttemptCnt(Integer invalidAttemptCnt) {
        this.invalidAttemptCnt = invalidAttemptCnt;
    }

    public String getSecondaryAccountNo() {
        return secondaryAccountNo;
    }

    public void setSecondaryAccountNo(String secondaryAccountNo) {
        this.secondaryAccountNo = secondaryAccountNo;
    }

    public String getTernaryAccountNo() {
        return ternaryAccountNo;
    }

    public void setTernaryAccountNo(String ternaryAccountNo) {
        this.ternaryAccountNo = ternaryAccountNo;
    }

    public String getDailyLimitCcy() {
        return dailyLimitCcy;
    }

    public void setDailyLimitCcy(String dailyLimitCcy) {
        this.dailyLimitCcy = dailyLimitCcy;
    }

    public BigDecimal getDailyLimitAmount() {
        return dailyLimitAmount;
    }

    public void setDailyLimitAmount(BigDecimal dailyLimitAmount) {
        this.dailyLimitAmount = dailyLimitAmount;
    }

    public BigDecimal getDailyAccumAmount() {
        return dailyAccumAmount;
    }

    public void setDailyAccumAmount(BigDecimal dailyAccumAmount) {
        this.dailyAccumAmount = dailyAccumAmount;
    }

    public String getDailyTrfLimitCcy() {
        return dailyTrfLimitCcy;
    }

    public void setDailyTrfLimitCcy(String dailyTrfLimitCcy) {
        this.dailyTrfLimitCcy = dailyTrfLimitCcy;
    }

    public BigDecimal getDailyTrfLimitAmount() {
        return dailyTrfLimitAmount;
    }

    public void setDailyTrfLimitAmount(BigDecimal dailyTrfLimitAmount) {
        this.dailyTrfLimitAmount = dailyTrfLimitAmount;
    }

    public BigDecimal getDailyTrfAccumAmount() {
        return dailyTrfAccumAmount;
    }

    public void setDailyTrfAccumAmount(BigDecimal dailyTrfAccumAmount) {
        this.dailyTrfAccumAmount = dailyTrfAccumAmount;
    }

    public String getDailyAccumResetDate() {
        return dailyAccumResetDate;
    }

    public void setDailyAccumResetDate(String dailyAccumResetDate) {
        this.dailyAccumResetDate = dailyAccumResetDate;
    }

    public String getDailyAccumResetTime() {
        return dailyAccumResetTime;
    }

    public void setDailyAccumResetTime(String dailyAccumResetTime) {
        this.dailyAccumResetTime = dailyAccumResetTime;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIncidentCode() {
        return incidentCode;
    }

    public void setIncidentCode(String incidentCode) {
        this.incidentCode = incidentCode;
    }

    public String getFeeWaive() {
        return feeWaive;
    }

    public void setFeeWaive(String feeWaive) {
        this.feeWaive = feeWaive;
    }

    public String getFeeCcy() {
        return feeCcy;
    }

    public void setFeeCcy(String feeCcy) {
        this.feeCcy = feeCcy;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterBy() {
        return registerBy;
    }

    public void setRegisterBy(String registerBy) {
        this.registerBy = registerBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(String lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    public String getMisSendDate() {
        return misSendDate;
    }

    public void setMisSendDate(String misSendDate) {
        this.misSendDate = misSendDate;
    }

    public String getCifName() {
        return cifName;
    }

    public void setCifName(String cifName) {
        this.cifName = cifName;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        return "CashCard{" +
                "id=" + id +
                ", branchCode='" + branchCode + '\'' +
                ", type='" + type + '\'' +
                ", cifNo='" + cifNo + '\'' +
                ", status='" + status + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}