package com.banking.kdb.oversea.framework.transaction.model;

import com.banking.kdb.oversea.framework.constant.FrameworkConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction entity for KDB Oversea Framework
 * 
 * Represents a financial transaction in the banking system.
 */
@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRANSACTION_ID", unique = true, nullable = false, length = 50)
    @NotNull(message = "Transaction ID is required")
    @Size(max = 50, message = "Transaction ID cannot exceed 50 characters")
    private String transactionId;

    @Column(name = "TRANSACTION_TYPE", nullable = false, length = 20)
    @NotNull(message = "Transaction type is required")
    @Size(max = 20, message = "Transaction type cannot exceed 20 characters")
    private String transactionType;

    @Column(name = "TRANSACTION_STATUS", nullable = false, length = 20)
    @NotNull(message = "Transaction status is required")
    @Size(max = 20, message = "Transaction status cannot exceed 20 characters")
    private String status;

    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @Column(name = "CURRENCY", nullable = false, length = 3)
    @NotNull(message = "Currency is required")
    @Size(max = 3, message = "Currency cannot exceed 3 characters")
    private String currency;

    @Column(name = "SOURCE_ACCOUNT", length = 50)
    @Size(max = 50, message = "Source account cannot exceed 50 characters")
    private String sourceAccount;

    @Column(name = "DESTINATION_ACCOUNT", length = 50)
    @Size(max = 50, message = "Destination account cannot exceed 50 characters")
    private String destinationAccount;

    @Column(name = "CUSTOMER_ID", length = 20)
    @Size(max = 20, message = "Customer ID cannot exceed 20 characters")
    private String customerId;

    @Column(name = "TELLER_ID", length = 20)
    @Size(max = 20, message = "Teller ID cannot exceed 20 characters")
    private String tellerId;

    @Column(name = "CHANNEL", length = 20)
    @Size(max = 20, message = "Channel cannot exceed 20 characters")
    private String channel;

    @Column(name = "DESCRIPTION", length = 500)
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Column(name = "REFERENCE_NUMBER", length = 50)
    @Size(max = 50, message = "Reference number cannot exceed 50 characters")
    private String referenceNumber;

    @Column(name = "EXTERNAL_REFERENCE", length = 100)
    @Size(max = 100, message = "External reference cannot exceed 100 characters")
    private String externalReference;

    @Column(name = "AUTHORIZATION_STATUS", length = 20)
    @Size(max = 20, message = "Authorization status cannot exceed 20 characters")
    private String authorizationStatus;

    @Column(name = "AUTHORIZATION_LEVEL", length = 20)
    @Size(max = 20, message = "Authorization level cannot exceed 20 characters")
    private String authorizationLevel;

    @Column(name = "AUTHORIZED_BY", length = 50)
    @Size(max = 50, message = "Authorized by cannot exceed 50 characters")
    private String authorizedBy;

    @Column(name = "AUTHORIZATION_DATE")
    private LocalDateTime authorizationDate;

    @Column(name = "VALIDATION_STATUS", length = 20)
    @Size(max = 20, message = "Validation status cannot exceed 20 characters")
    private String validationStatus;

    @Column(name = "VALIDATION_MESSAGE", length = 500)
    @Size(max = 500, message = "Validation message cannot exceed 500 characters")
    private String validationMessage;

    @Column(name = "ROUTING_STATUS", length = 20)
    @Size(max = 20, message = "Routing status cannot exceed 20 characters")
    private String routingStatus;

    @Column(name = "ROUTING_TYPE", length = 20)
    @Size(max = 20, message = "Routing type cannot exceed 20 characters")
    private String routingType;

    @Column(name = "QUEUE_STATUS", length = 20)
    @Size(max = 20, message = "Queue status cannot exceed 20 characters")
    private String queueStatus;

    @Column(name = "QUEUE_TYPE", length = 20)
    @Size(max = 20, message = "Queue type cannot exceed 20 characters")
    private String queueType;

    @Column(name = "PRIORITY", length = 10)
    @Size(max = 10, message = "Priority cannot exceed 10 characters")
    private String priority;

    @Column(name = "RETRY_COUNT")
    private Integer retryCount;

    @Column(name = "MAX_RETRY_ATTEMPTS")
    private Integer maxRetryAttempts;

    @Column(name = "NEXT_RETRY_TIME")
    private LocalDateTime nextRetryTime;

    @Column(name = "PROCESSING_TIME_MS")
    private Long processingTimeMs;

    @Column(name = "ERROR_CODE", length = 20)
    @Size(max = 20, message = "Error code cannot exceed 20 characters")
    private String errorCode;

    @Column(name = "ERROR_MESSAGE", length = 500)
    @Size(max = 500, message = "Error message cannot exceed 500 characters")
    private String errorMessage;

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

    public Transaction() {
        this.status = FrameworkConstants.TRANSACTION_STATUS_INITIATED;
        this.retryCount = 0;
        this.maxRetryAttempts = FrameworkConstants.DEFAULT_MAX_RETRY_ATTEMPTS;
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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTellerId() {
        return tellerId;
    }

    public void setTellerId(String tellerId) {
        this.tellerId = tellerId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public String getAuthorizationStatus() {
        return authorizationStatus;
    }

    public void setAuthorizationStatus(String authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
    }

    public String getAuthorizationLevel() {
        return authorizationLevel;
    }

    public void setAuthorizationLevel(String authorizationLevel) {
        this.authorizationLevel = authorizationLevel;
    }

    public String getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(String authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public LocalDateTime getAuthorizationDate() {
        return authorizationDate;
    }

    public void setAuthorizationDate(LocalDateTime authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    public String getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public String getRoutingStatus() {
        return routingStatus;
    }

    public void setRoutingStatus(String routingStatus) {
        this.routingStatus = routingStatus;
    }

    public String getRoutingType() {
        return routingType;
    }

    public void setRoutingType(String routingType) {
        this.routingType = routingType;
    }

    public String getQueueStatus() {
        return queueStatus;
    }

    public void setQueueStatus(String queueStatus) {
        this.queueStatus = queueStatus;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Integer getMaxRetryAttempts() {
        return maxRetryAttempts;
    }

    public void setMaxRetryAttempts(Integer maxRetryAttempts) {
        this.maxRetryAttempts = maxRetryAttempts;
    }

    public LocalDateTime getNextRetryTime() {
        return nextRetryTime;
    }

    public void setNextRetryTime(LocalDateTime nextRetryTime) {
        this.nextRetryTime = nextRetryTime;
    }

    public Long getProcessingTimeMs() {
        return processingTimeMs;
    }

    public void setProcessingTimeMs(Long processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
        Transaction that = (Transaction) obj;
        return transactionId != null ? transactionId.equals(that.transactionId) : that.transactionId == null;
    }

    @Override
    public int hashCode() {
        return transactionId != null ? transactionId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transactionId='" + transactionId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}