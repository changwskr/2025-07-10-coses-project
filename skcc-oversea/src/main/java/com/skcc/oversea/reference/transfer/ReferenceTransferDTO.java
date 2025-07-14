package com.skcc.oversea.reference.transfer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReferenceTransferDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String referenceId;
    private String referenceType;
    private String referenceValue;
    private String description;
    private String status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ReferenceTransferDTO() {
    }

    public ReferenceTransferDTO(String referenceId, String referenceType, String referenceValue) {
        this.referenceId = referenceId;
        this.referenceType = referenceType;
        this.referenceValue = referenceValue;
        this.createDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceValue() {
        return referenceValue;
    }

    public void setReferenceValue(String referenceValue) {
        this.referenceValue = referenceValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}