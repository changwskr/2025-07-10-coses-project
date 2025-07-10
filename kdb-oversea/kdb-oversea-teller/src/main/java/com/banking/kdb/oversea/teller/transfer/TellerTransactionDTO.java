package com.banking.kdb.oversea.teller.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Teller Transaction DTO for KDB Oversea
 * 
 * Data transfer object for teller transaction information.
 */
public class TellerTransactionDTO {

    private Long id;

    @JsonProperty("transactionId")
    @NotBlank(message = "Transaction ID is required")
    @Size(max = 20, message = "Transaction ID cannot exceed 20 characters")
    private String transactionId;

    @JsonProperty("sessionId")
    @NotBlank(message = "Session ID is required")
    @Size(max = 20, message = "Session ID cannot exceed 20 characters")
    private String sessionId;

    @JsonProperty("transactionType")
    @NotBlank(message = "Transaction type is required")
    @Size(max = 50, message = "Transaction type cannot exceed 50 characters")
    private String transactionType;

    @JsonProperty("description")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @JsonProperty("reference")
    @Size(max = 100, message = "Reference cannot exceed 100 characters")
    private String reference;

    @JsonProperty("transactionDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDate;

    @JsonProperty("status")
    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status cannot exceed 20 characters")
    private String status;

    @JsonProperty("failureReason")
    @Size(max = 500, message = "Failure reason cannot exceed 500 characters")
    private String failureReason;

    @JsonProperty("createdBy")
    @Size(max = 50, message = "Created by cannot exceed 50 characters")
    private String createdBy;

    @JsonProperty("createdDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    @JsonProperty("modifiedBy")
    @Size(max = 50, message = "Modified by cannot exceed 50 characters")
    private String modifiedBy;

    @JsonProperty("modifiedDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDateTime;

    public TellerTransactionDTO() {
        this.status = "PENDING";
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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
    public String toString() {
        return "TellerTransactionDTO{" +
                "id=" + id +
                ", transactionId='" + transactionId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}