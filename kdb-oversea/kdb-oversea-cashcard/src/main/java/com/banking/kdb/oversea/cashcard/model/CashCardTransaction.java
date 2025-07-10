package com.banking.kdb.oversea.cashcard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CashCard Transaction entity for KDB Oversea
 * 
 * Represents cash card transaction information in the banking system.
 */
@Entity
@Table(name = "CASH_CARD_TRANSACTIONS")
public class CashCardTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRANSACTION_ID", unique = true, nullable = false, length = 20)
    @NotBlank(message = "Transaction ID is required")
    @Size(max = 20, message = "Transaction ID cannot exceed 20 characters")
    private String transactionId;

    @Column(name = "CARD_NUMBER", nullable = false, length = 20)
    @NotBlank(message = "Card number is required")
    @Size(max = 20, message = "Card number cannot exceed 20 characters")
    private String cardNumber;

    @Column(name = "TRANSACTION_TYPE", nullable = false, length = 20)
    @NotBlank(message = "Transaction type is required")
    @Size(max = 20, message = "Transaction type cannot exceed 20 characters")
    private String transactionType;

    @Column(name = "AMOUNT", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @Column(name = "CURRENCY", nullable = false, length = 3)
    @NotBlank(message = "Currency is required")
    @Size(max = 3, message = "Currency cannot exceed 3 characters")
    private String currency;

    @Column(name = "MERCHANT_ID", length = 50)
    @Size(max = 50, message = "Merchant ID cannot exceed 50 characters")
    private String merchantId;

    @Column(name = "MERCHANT_NAME", length = 100)
    @Size(max = 100, message = "Merchant name cannot exceed 100 characters")
    private String merchantName;

    @Column(name = "TERMINAL_ID", length = 50)
    @Size(max = 50, message = "Terminal ID cannot exceed 50 characters")
    private String terminalId;

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

    @Column(name = "AUTHORIZATION_CODE", length = 20)
    @Size(max = 20, message = "Authorization code cannot exceed 20 characters")
    private String authorizationCode;

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

    public CashCardTransaction() {
        this.status = "PENDING";
        this.transactionDate = LocalDateTime.now();
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
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

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
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

        CashCardTransaction that = (CashCardTransaction) obj;
        return transactionId != null ? transactionId.equals(that.transactionId) : that.transactionId == null;
    }

    @Override
    public int hashCode() {
        return transactionId != null ? transactionId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CashCardTransaction{" +
                "id=" + id +
                ", transactionId='" + transactionId + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}