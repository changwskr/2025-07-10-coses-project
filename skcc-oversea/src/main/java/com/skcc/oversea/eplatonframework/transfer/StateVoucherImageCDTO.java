package com.skcc.oversea.eplatonframework.transfer;

import com.skcc.oversea.framework.transfer.DTO;

/**
 * State Voucher Image DTO for legacy compatibility
 */
public class StateVoucherImageCDTO extends DTO {
    private String imageId;
    private String voucherId;
    private String imageType;
    private String imagePath;
    private String imageName;
    private String imageSize;
    private String imageFormat;
    private String uploadDate;
    private String uploadUser;
    private String status;

    public StateVoucherImageCDTO() {
        super();
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((imageId == null) ? 0 : imageId.hashCode());
        result = prime * result + ((voucherId == null) ? 0 : voucherId.hashCode());
        result = prime * result + ((imageType == null) ? 0 : imageType.hashCode());
        result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
        result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
        result = prime * result + ((imageSize == null) ? 0 : imageSize.hashCode());
        result = prime * result + ((imageFormat == null) ? 0 : imageFormat.hashCode());
        result = prime * result + ((uploadDate == null) ? 0 : uploadDate.hashCode());
        result = prime * result + ((uploadUser == null) ? 0 : uploadUser.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StateVoucherImageCDTO other = (StateVoucherImageCDTO) obj;
        if (imageId == null) {
            if (other.imageId != null)
                return false;
        } else if (!imageId.equals(other.imageId))
            return false;
        if (voucherId == null) {
            if (other.voucherId != null)
                return false;
        } else if (!voucherId.equals(other.voucherId))
            return false;
        if (imageType == null) {
            if (other.imageType != null)
                return false;
        } else if (!imageType.equals(other.imageType))
            return false;
        if (imagePath == null) {
            if (other.imagePath != null)
                return false;
        } else if (!imagePath.equals(other.imagePath))
            return false;
        if (imageName == null) {
            if (other.imageName != null)
                return false;
        } else if (!imageName.equals(other.imageName))
            return false;
        if (imageSize == null) {
            if (other.imageSize != null)
                return false;
        } else if (!imageSize.equals(other.imageSize))
            return false;
        if (imageFormat == null) {
            if (other.imageFormat != null)
                return false;
        } else if (!imageFormat.equals(other.imageFormat))
            return false;
        if (uploadDate == null) {
            if (other.uploadDate != null)
                return false;
        } else if (!uploadDate.equals(other.uploadDate))
            return false;
        if (uploadUser == null) {
            if (other.uploadUser != null)
                return false;
        } else if (!uploadUser.equals(other.uploadUser))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "StateVoucherImageCDTO{" +
                "imageId='" + imageId + '\'' +
                ", voucherId='" + voucherId + '\'' +
                ", imageType='" + imageType + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", imageName='" + imageName + '\'' +
                ", imageSize='" + imageSize + '\'' +
                ", imageFormat='" + imageFormat + '\'' +
                ", uploadDate='" + uploadDate + '\'' +
                ", uploadUser='" + uploadUser + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public DTO clone() {
        try {
            return (DTO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }
}
