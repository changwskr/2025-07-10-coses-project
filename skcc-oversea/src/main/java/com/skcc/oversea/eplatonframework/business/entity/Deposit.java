package com.skcc.oversea.eplatonframework.business.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deposits")
public class Deposit {

    @EmbeddedId
    private DepositPK primaryKey;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "deposit_type")
    private String depositType;

    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", length = 3)
    private String currency;

    @Column(name = "interest_rate", precision = 5, scale = 4)
    private BigDecimal interestRate;

    @Column(name = "maturity_date")
    private LocalDateTime maturityDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "teller_id")
    private String tellerId;

    public Deposit() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    public Deposit(DepositPK primaryKey) {
        this();
        this.primaryKey = primaryKey;
    }

    public DepositPK getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(DepositPK primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getAccountNumber() {
        return primaryKey != null ? primaryKey.getAccountNumber() : null;
    }

    public String getCustomerId() {
        return primaryKey != null ? primaryKey.getCustomerId() : null;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setAmount(double amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDateTime getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDateTime maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getTellerId() {
        return tellerId;
    }

    public void setTellerId(String tellerId) {
        this.tellerId = tellerId;
    }

    // Additional methods for compatibility
    public String getAccountNo() {
        return getAccountNumber();
    }

    public void setAccountNo(String accountNo) {
        if (primaryKey == null) {
            primaryKey = new DepositPK(accountNo, getCustomerId());
        } else {
            primaryKey.setAccountNumber(accountNo);
        }
    }

    public DepositPK getId() {
        return primaryKey;
    }

    public void setId(DepositPK id) {
        this.primaryKey = id;
    }

    public BigDecimal getBalance() {
        return amount;
    }

    public void setBalance(BigDecimal balance) {
        this.amount = balance;
    }

    public String getAccountStatus() {
        return isActive != null && isActive ? "ACTIVE" : "INACTIVE";
    }

    public void setAccountStatus(String status) {
        this.isActive = "ACTIVE".equalsIgnoreCase(status);
    }

    public String getAccountType() {
        return depositType;
    }

    public void setAccountType(String accountType) {
        this.depositType = accountType;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "primaryKey=" + primaryKey +
                ", branchCode='" + branchCode + '\'' +
                ", depositType='" + depositType + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", interestRate=" + interestRate +
                ", maturityDate=" + maturityDate +
                ", isActive=" + isActive +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", tellerId='" + tellerId + '\'' +
                '}';
    }
}