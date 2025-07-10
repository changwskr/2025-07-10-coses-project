package com.chb.coses.reference.transfer;

import com.chb.coses.framework.transfer.DTO;

/**
 * Reference Data Transfer Object
 */
public class ReferenceDTO extends DTO {

    private String referenceCode;
    private String referenceName;
    private String referenceType;
    private String description;
    private String status;

    public ReferenceDTO() {
        super();
    }

    public ReferenceDTO(String referenceCode, String referenceName) {
        this.referenceCode = referenceCode;
        this.referenceName = referenceName;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
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

    @Override
    public String toString() {
        return "ReferenceDTO{" +
                "referenceCode='" + referenceCode + '\'' +
                ", referenceName='" + referenceName + '\'' +
                ", referenceType='" + referenceType + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}