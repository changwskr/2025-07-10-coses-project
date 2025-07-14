package com.skcc.oversea.framework.transfer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ModifyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String operation;
    private String status;
    private String message;
    private LocalDateTime modifyDate;
    private String modifiedBy;

    public ModifyDTO() {
    }

    public ModifyDTO(String id, String operation) {
        this.id = id;
        this.operation = operation;
        this.modifyDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}