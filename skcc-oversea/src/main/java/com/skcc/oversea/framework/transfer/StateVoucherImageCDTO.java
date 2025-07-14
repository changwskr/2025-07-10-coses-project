package com.skcc.oversea.framework.transfer;

import java.io.Serializable;

public class StateVoucherImageCDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String voucherId;
    private String imageData;
    private String imageType;
    private String status;

    public StateVoucherImageCDTO() {
    }

    public StateVoucherImageCDTO(String voucherId, String imageData) {
        this.voucherId = voucherId;
        this.imageData = imageData;
    }

    // Getters and Setters
    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}