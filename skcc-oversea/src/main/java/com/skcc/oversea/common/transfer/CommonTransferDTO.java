package com.skcc.oversea.common.transfer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CommonTransferDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String transferId;
    private String transferType;
    private String status;
    private LocalDateTime createDate;

    public CommonTransferDTO() {
    }

    public CommonTransferDTO(String transferId, String transferType) {
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}