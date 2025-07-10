package com.banking.kdb.oversea.deposit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Deposit Transaction entity for KDB Oversea
 * 
 * Represents deposit transaction information in the banking system.
 */
@Entity
@Table(name = "DEPOSIT_TRANSACTIONS")
public class DepositTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRANSACTION_ID", unique = true, nullable = false, length = 20)
    @NotBlank(message = "Transaction ID is required")
    @Size(max = 20, message = "Transaction ID cannot exceed 20 characters")
    private String transactionId;

    @Column(name = "ACCOUNT_NUMBER", nullable = false, length = 20)
    @NotBlank(message = "Account number is required")
    @Size(max = 20, message = "Account number cannot exceed 20 characters")
    private String accountNumber;

    @Column(name = "TRANSACTION_TYPE", nullable = false, length = 20)
    @NotBlank(message = "Transaction type is required")
    @Size(max = 20, message = "Transaction type cannot exceed 20 characters")
    private String transactionType;

    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @Column(name = "CURRENCY", nullable = false, length = 3)
    @NotBlank(message = "Currency is required")
    @Size(max = 3, message = "Currency cannot exceed 3 characters")
    private String currency;

    @Column(name = "BALANCE_BEFORE", precision = 19, scale = 2)
    private BigDecimal balanceBefore;

    @Column(name = "BALANCE_AFTER", precision = 19, scale = 2)
    private BigDecimal balanceAfter;

    @Column(name = "DESCRIPTION", length = 500)
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Column(name = "REFERENCE_NUMBER", length = 50)
    @Size(max = 50, message = "Reference number cannot exceed 50 characters")
    private String referenceNumber;

    @Column(name = "TRANSACTION_DATE", nullable = false)
    @NotNull(message = "Transaction date is required")
    private LocalDateTime transactionDate;

    @Column(name = "STATUS", nullable = false, length = 20)
    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status cannot exceed 20 characters")
    private String status;

    @Column(name = "FAILURE_REASON", length = 500)
    @Size(max = 500, message = "Failure reason cannot exceed 500 characters")
    private String failureReason;

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

    public DepositTransaction() {
        this.transactionDate = LocalDateTime.now();
        this.status = "PENDING";
        this.createdDateTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
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

        DepositTransaction that = (DepositTransaction) obj;
        return transactionId != null ? transactionId.equals(that.transactionId) : that.transactionId == null;
    }

    @Override
    public int hashCode() {
        return transactionId != null ? transactionId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DepositTransaction{" +
                "id=" + id +
                ", transactionId='" + transactionId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}