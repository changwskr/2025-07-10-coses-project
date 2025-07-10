package com.banking.kdb.oversea.deposit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Deposit Account entity for KDB Oversea
 * 
 * Represents deposit account information in the banking system.
 */
@Entity
@Table(name = "DEPOSIT_ACCOUNTS")
public class DepositAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACCOUNT_NUMBER", unique = true, nullable = false, length = 20)
    @NotBlank(message = "Account number is required")
    @Size(max = 20, message = "Account number cannot exceed 20 characters")
    private String accountNumber;

    @Column(name = "CUSTOMER_ID", nullable = false, length = 20)
    @NotBlank(message = "Customer ID is required")
    @Size(max = 20, message = "Customer ID cannot exceed 20 characters")
    private String customerId;

    @Column(name = "ACCOUNT_TYPE", nullable = false, length = 20)
    @NotBlank(message = "Account type is required")
    @Size(max = 20, message = "Account type cannot exceed 20 characters")
    private String accountType;

    @Column(name = "BALANCE", nullable = false, precision = 19, scale = 2)
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", message = "Balance cannot be negative")
    private BigDecimal balance;

    @Column(name = "CURRENCY", nullable = false, length = 3)
    @NotBlank(message = "Currency is required")
    @Size(max = 3, message = "Currency cannot exceed 3 characters")
    private String currency;

    @Column(name = "INTEREST_RATE", precision = 5, scale = 2)
    private BigDecimal interestRate;

    @Column(name = "MINIMUM_BALANCE", precision = 19, scale = 2)
    private BigDecimal minimumBalance;

    @Column(name = "DAILY_LIMIT", precision = 19, scale = 2)
    private BigDecimal dailyLimit;

    @Column(name = "MONTHLY_LIMIT", precision = 19, scale = 2)
    private BigDecimal monthlyLimit;

    @Column(name = "STATUS", nullable = false, length = 20)
    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status cannot exceed 20 characters")
    private String status;

    @Column(name = "OPEN_DATE", nullable = false)
    @NotNull(message = "Open date is required")
    private LocalDateTime openDate;

    @Column(name = "MATURITY_DATE")
    private LocalDateTime maturityDate;

    @Column(name = "CREATED_BY", length = 50)
    @Size(max = 50, message = "Created by cannot exceed 50 characters")
    private String createdBy;

    @Column(name = "CREATED_DATETIME")
    private LocalDateTime createdDateTime;

    @Column(name = "MODIFIED_BY", length = 50)
    @Size(max = 50, message = "Modified by cannot exceed 50 characters")
    private String modifiedBy;

    @Column(name = "MODIFIED_DATETIME")
    private LocalDateTime modifiedDateTime;

    public DepositAccount() {
        this.balance = BigDecimal.ZERO;
        this.status = "ACTIVE";
        this.openDate = LocalDateTime.now();
        this.createdDateTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public BigDecimal getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(BigDecimal monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDateTime openDate) {
        this.openDate = openDate;
    }

    public LocalDateTime getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDateTime maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        DepositAccount that = (DepositAccount) obj;
        return accountNumber != null ? accountNumber.equals(that.accountNumber) : that.accountNumber == null;
    }

    @Override
    public int hashCode() {
        return accountNumber != null ? accountNumber.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DepositAccount{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", customerId='" + customerId + '\'' +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}