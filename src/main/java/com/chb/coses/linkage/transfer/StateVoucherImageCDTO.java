package com.chb.coses.linkage.transfer;

import com.chb.coses.framework.transfer.DTO;

public class StateVoucherImageCDTO extends DTO {
    private String voucherId;
    private byte[] imageData;

    public StateVoucherImageCDTO() {
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}