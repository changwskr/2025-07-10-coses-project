package com.banking.kdb.oversea.framework.transfer.model;

import com.banking.kdb.oversea.framework.constant.FrameworkConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transfer entity for KDB Oversea Framework
 * 
 * Represents a fund transfer transaction in the banking system.
 */
@Entity
@Table(name = "TRANSFERS")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRANSFER_ID", unique = true, nullable = false, length = 50)
    @NotNull(message = "Transfer ID is required")
    @Size(max = 50, message = "Transfer ID cannot exceed 50 characters")
    private String transferId;

    @Column(name = "TRANSFER_TYPE", nullable = false, length = 20)
    @NotNull(message = "Transfer type is required")
    @Size(max = 20, message = "Transfer type cannot exceed 20 characters")
    private String transferType;

    @Column(name = "TRANSFER_STATUS", nullable = false, length = 20)
    @NotNull(message = "Transfer status is required")
    @Size(max = 20, message = "Transfer status cannot exceed 20 characters")
    private String status;

    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @Column(name = "CURRENCY", nullable = false, length = 3)
    @NotNull(message = "Currency is required")
    @Size(max = 3, message = "Currency cannot exceed 3 characters")
    private String currency;

    @Column(name = "SOURCE_ACCOUNT", nullable = false, length = 50)
    @NotNull(message = "Source account is required")
    @Size(max = 50, message = "Source account cannot exceed 50 characters")
    private String sourceAccount;

    @Column(name = "DESTINATION_ACCOUNT", nullable = false, length = 50)
    @NotNull(message = "Destination account is required")
    @Size(max = 50, message = "Destination account cannot exceed 50 characters")
    private String destinationAccount;

    @Column(name = "SOURCE_CUSTOMER_ID", length = 20)
    @Size(max = 20, message = "Source customer ID cannot exceed 20 characters")
    private String sourceCustomerId;

    @Column(name = "DESTINATION_CUSTOMER_ID", length = 20)
    @Size(max = 20, message = "Destination customer ID cannot exceed 20 characters")
    private String destinationCustomerId;

    @Column(name = "TRANSFER_METHOD", length = 20)
    @Size(max = 20, message = "Transfer method cannot exceed 20 characters")
    private String transferMethod;

    @Column(name = "DESCRIPTION", length = 500)
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Column(name = "REFERENCE_NUMBER", length = 50)
    @Size(max = 50, message = "Reference number cannot exceed 50 characters")
    private String referenceNumber;

    @Column(name = "EXTERNAL_REFERENCE", length = 100)
    @Size(max = 100, message = "External reference cannot exceed 100 characters")
    private String externalReference;

    @Column(name = "FEE_AMOUNT", precision = 19, scale = 2)
    private BigDecimal feeAmount;

    @Column(name = "EXCHANGE_RATE", precision = 19, scale = 6)
    private BigDecimal exchangeRate;

    @Column(name = "SOURCE_CURRENCY", length = 3)
    @Size(max = 3, message = "Source currency cannot exceed 3 characters")
    private String sourceCurrency;

    @Column(name = "DESTINATION_CURRENCY", length = 3)
    @Size(max = 3, message = "Destination currency cannot exceed 3 characters")
    private String destinationCurrency;

    @Column(name = "EXPECTED_SETTLEMENT_DATE")
    private LocalDateTime expectedSettlementDate;

    @Column(name = "ACTUAL_SETTLEMENT_DATE")
    private LocalDateTime actualSettlementDate;

    @Column(name = "AUTHORIZATION_STATUS", length = 20)
    @Size(max = 20, message = "Authorization status cannot exceed 20 characters")
    private String authorizationStatus;

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

    public Transfer() {
        this.status = FrameworkConstants.TRANSFER_STATUS_INITIATED;
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

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
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

    public String getSourceCustomerId() {
        return sourceCustomerId;
    }

    public void setSourceCustomerId(String sourceCustomerId) {
        this.sourceCustomerId = sourceCustomerId;
    }

    public String getDestinationCustomerId() {
        return destinationCustomerId;
    }

    public void setDestinationCustomerId(String destinationCustomerId) {
        this.destinationCustomerId = destinationCustomerId;
    }

    public String getTransferMethod() {
        return transferMethod;
    }

    public void setTransferMethod(String transferMethod) {
        this.transferMethod = transferMethod;
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

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getDestinationCurrency() {
        return destinationCurrency;
    }

    public void setDestinationCurrency(String destinationCurrency) {
        this.destinationCurrency = destinationCurrency;
    }

    public LocalDateTime getExpectedSettlementDate() {
        return expectedSettlementDate;
    }

    public void setExpectedSettlementDate(LocalDateTime expectedSettlementDate) {
        this.expectedSettlementDate = expectedSettlementDate;
    }

    public LocalDateTime getActualSettlementDate() {
        return actualSettlementDate;
    }

    public void setActualSettlementDate(LocalDateTime actualSettlementDate) {
        this.actualSettlementDate = actualSettlementDate;
    }

    public String getAuthorizationStatus() {
        return authorizationStatus;
    }

    public void setAuthorizationStatus(String authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
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
        Transfer that = (Transfer) obj;
        return transferId != null ? transferId.equals(that.transferId) : that.transferId == null;
    }

    @Override
    public int hashCode() {
        return transferId != null ? transferId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", transferId='" + transferId + '\'' +
                ", transferType='" + transferType + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}