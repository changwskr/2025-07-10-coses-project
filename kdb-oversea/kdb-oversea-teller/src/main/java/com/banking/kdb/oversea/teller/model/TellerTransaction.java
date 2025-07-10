package com.banking.kdb.oversea.teller.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Teller Transaction entity for KDB Oversea
 * 
 * Represents teller transaction information in the banking system.
 */
@Entity
@Table(name = "TELLER_TRANSACTIONS")
public class TellerTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRANSACTION_ID", unique = true, nullable = false, length = 20)
    @NotBlank(message = "Transaction ID is required")
    @Size(max = 20, message = "Transaction ID cannot exceed 20 characters")
    private String transactionId;

    @Column(name = "SESSION_ID", nullable = false, length = 20)
    @NotBlank(message = "Session ID is required")
    @Size(max = 20, message = "Session ID cannot exceed 20 characters")
    private String sessionId;

    @Column(name = "TRANSACTION_TYPE", nullable = false, length = 50)
    @NotBlank(message = "Transaction type is required")
    @Size(max = 50, message = "Transaction type cannot exceed 50 characters")
    private String transactionType;

    @Column(name = "DESCRIPTION", length = 500)
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @Column(name = "REFERENCE", length = 100)
    @Size(max = 100, message = "Reference cannot exceed 100 characters")
    private String reference;

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

    public TellerTransaction() {
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        TellerTransaction that = (TellerTransaction) obj;
        return transactionId != null ? transactionId.equals(that.transactionId) : that.transactionId == null;
    }

    @Override
    public int hashCode() {
        return transactionId != null ? transactionId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TellerTransaction{" +
                "id=" + id +
                ", transactionId='" + transactionId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}