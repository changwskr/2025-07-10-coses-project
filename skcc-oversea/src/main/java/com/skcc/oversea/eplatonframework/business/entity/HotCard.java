package com.skcc.oversea.eplatonframework.business.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hot_cards")
public class HotCard {

    @EmbeddedId
    private HotCardPK primaryKey;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "card_status")
    private String cardStatus;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @Column(name = "current_balance", precision = 15, scale = 2)
    private BigDecimal currentBalance;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "daily_limit", precision = 15, scale = 2)
    private BigDecimal dailyLimit;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "register_time")
    private String registerTime;

    @Column(name = "register_by")
    private String registerBy;

    @Column(name = "released_date")
    private LocalDateTime releasedDate;

    @Column(name = "released_time")
    private String releasedTime;

    @Column(name = "released_by")
    private String releasedBy;

    @Column(name = "remark")
    private String remark;

    public HotCard() {
        this.registerDate = LocalDateTime.now();
    }

    public HotCard(HotCardPK primaryKey) {
        this();
        this.primaryKey = primaryKey;
    }

    public HotCardPK getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(HotCardPK primaryKey) {
        this.primaryKey = primaryKey;
    }

    public int getSequenceNo() {
        return primaryKey != null ? primaryKey.getSequenceNo() : 0;
    }

    public String getCardNumber() {
        return primaryKey != null ? primaryKey.getCardNumber() : null;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
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

    public LocalDateTime getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(LocalDateTime releasedDate) {
        this.releasedDate = releasedDate;
    }

    public String getReleasedTime() {
        return releasedTime;
    }

    public void setReleasedTime(String releasedTime) {
        this.releasedTime = releasedTime;
    }

    public String getReleasedBy() {
        return releasedBy;
    }

    public void setReleasedBy(String releasedBy) {
        this.releasedBy = releasedBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @PreUpdate
    public void preUpdate() {
        this.releasedDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "HotCard{" +
                "primaryKey=" + primaryKey +
                ", accountNo='" + accountNo + '\'' +
                ", customerId='" + customerId + '\'' +
                ", cardStatus='" + cardStatus + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", currentBalance=" + currentBalance +
                ", currencyCode='" + currencyCode + '\'' +
                ", dailyLimit=" + dailyLimit +
                ", expiryDate=" + expiryDate +
                ", registerDate=" + registerDate +
                ", registerTime='" + registerTime + '\'' +
                ", registerBy='" + registerBy + '\'' +
                ", releasedDate=" + releasedDate +
                ", releasedTime='" + releasedTime + '\'' +
                ", releasedBy='" + releasedBy + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}