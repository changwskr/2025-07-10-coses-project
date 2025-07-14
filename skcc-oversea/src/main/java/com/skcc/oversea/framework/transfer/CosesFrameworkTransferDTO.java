package com.skcc.oversea.framework.transfer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CosesFrameworkTransferDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String transferId;
    private String transferType;
    private String status;
    private String message;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public CosesFrameworkTransferDTO() {
    }

    public CosesFrameworkTransferDTO(String transferId, String transferType) {
        this.transferId = transferId;
        this.transferType = transferType;
        this.createDate = LocalDateTime.now();
    }

    // Getters and Setters
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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