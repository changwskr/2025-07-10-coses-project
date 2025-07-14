package com.skcc.oversea.cashCard.transfer;

import java.math.BigDecimal;

import com.skcc.oversea.framework.transfer.DTO;
import com.skcc.oversea.framework.constants.Constants;

public class HotCardCDTO extends DTO {
    private String cardNumber = Constants.BLANK;
    private int sequenceNo = 0;
    private String primaryAccountNo = Constants.BLANK;
    private String CIFNo = Constants.BLANK;
    private String CIFName = Constants.BLANK;
    // private String cardHolderName = Constants.BLANK;
    private String status = Constants.BLANK;
    private String incidentCode = Constants.BLANK;
    // private String issueDate = Constants.BLANK;
    // private String effectiveDate = Constants.BLANK;
    // private String expiryDate = Constants.BLANK;
    private String registerDate = Constants.BLANK;
    private String registerTime = Constants.BLANK;
    private String registerBy = Constants.BLANK;
    private String releasedDate = Constants.BLANK;
    private String releasedTime = Constants.BLANK;
    private String releasedBy = Constants.BLANK;
    private String remark = Constants.BLANK;

    public HotCardCDTO() {
    }

    // Getter Method
    /*
     * public String getCardHolderName()
     * {
     * return cardHolderName;
     * }
     */
    public String getCardNumber() {
        return cardNumber;
    }

    public String getCIFNo() {
        return CIFNo;
    }

    public String getCIFName() {
        return CIFName;
    }

    /*
     * public String getEffectiveDate()
     * {
     * return effectiveDate;
     * }
     * public String getExpiryDate()
     * {
     * return expiryDate;
     * }
     */
    public String getIncidentCode() {
        return incidentCode;
    }

    public String getPrimaryAccountNo() {
        return primaryAccountNo;
    }

    public String getRegisterBy() {
        return registerBy;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public String getReleasedBy() {
        return releasedBy;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public String getReleasedTime() {
        return releasedTime;
    }

    public String getRemark() {
        return remark;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public String getStatus() {
        return status;
    }
    /*
     * public String getIssueDate()
     * {
     * return issueDate;
     * }
     */

    // Setter Method
    /*
     * public void setCardHolderName(String cardHolderName)
     * {
     * this.cardHolderName = cardHolderName;
     * }
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCIFNo(String CIFNo) {
        this.CIFNo = CIFNo;
    }

    public void setCIFName(String CIFName) {
        this.CIFName = CIFName;
    }

    /*
     * public void setEffectiveDate(String effectiveDate)
     * {
     * this.effectiveDate = effectiveDate;
     * }
     * public void setExpiryDate(String expiryDate)
     * {
     * this.expiryDate = expiryDate;
     * }
     */
    public void setIncidentCode(String incidentCode) {
        this.incidentCode = incidentCode;
    }

    public void setPrimaryAccountNo(String primaryAccountNo) {
        this.primaryAccountNo = primaryAccountNo;
    }

    public void setRegisterBy(String registerBy) {
        this.registerBy = registerBy;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public void setReleasedBy(String releasedBy) {
        this.releasedBy = releasedBy;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }

    public void setReleasedTime(String releasedTime) {
        this.releasedTime = releasedTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    /*
     * public void setIssueDate(String issueDate)
     * {
     * this.issueDate = issueDate;
     * }
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
        result = prime * result + sequenceNo;
        result = prime * result + ((primaryAccountNo == null) ? 0 : primaryAccountNo.hashCode());
        result = prime * result + ((CIFNo == null) ? 0 : CIFNo.hashCode());
        result = prime * result + ((CIFName == null) ? 0 : CIFName.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((incidentCode == null) ? 0 : incidentCode.hashCode());
        result = prime * result + ((registerDate == null) ? 0 : registerDate.hashCode());
        result = prime * result + ((registerTime == null) ? 0 : registerTime.hashCode());
        result = prime * result + ((registerBy == null) ? 0 : registerBy.hashCode());
        result = prime * result + ((releasedDate == null) ? 0 : releasedDate.hashCode());
        result = prime * result + ((releasedTime == null) ? 0 : releasedTime.hashCode());
        result = prime * result + ((releasedBy == null) ? 0 : releasedBy.hashCode());
        result = prime * result + ((remark == null) ? 0 : remark.hashCode());
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
        HotCardCDTO other = (HotCardCDTO) obj;
        if (cardNumber == null) {
            if (other.cardNumber != null)
                return false;
        } else if (!cardNumber.equals(other.cardNumber))
            return false;
        if (sequenceNo != other.sequenceNo)
            return false;
        if (primaryAccountNo == null) {
            if (other.primaryAccountNo != null)
                return false;
        } else if (!primaryAccountNo.equals(other.primaryAccountNo))
            return false;
        if (CIFNo == null) {
            if (other.CIFNo != null)
                return false;
        } else if (!CIFNo.equals(other.CIFNo))
            return false;
        if (CIFName == null) {
            if (other.CIFName != null)
                return false;
        } else if (!CIFName.equals(other.CIFName))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (incidentCode == null) {
            if (other.incidentCode != null)
                return false;
        } else if (!incidentCode.equals(other.incidentCode))
            return false;
        if (registerDate == null) {
            if (other.registerDate != null)
                return false;
        } else if (!registerDate.equals(other.registerDate))
            return false;
        if (registerTime == null) {
            if (other.registerTime != null)
                return false;
        } else if (!registerTime.equals(other.registerTime))
            return false;
        if (registerBy == null) {
            if (other.registerBy != null)
                return false;
        } else if (!registerBy.equals(other.registerBy))
            return false;
        if (releasedDate == null) {
            if (other.releasedDate != null)
                return false;
        } else if (!releasedDate.equals(other.releasedDate))
            return false;
        if (releasedTime == null) {
            if (other.releasedTime != null)
                return false;
        } else if (!releasedTime.equals(other.releasedTime))
            return false;
        if (releasedBy == null) {
            if (other.releasedBy != null)
                return false;
        } else if (!releasedBy.equals(other.releasedBy))
            return false;
        if (remark == null) {
            if (other.remark != null)
                return false;
        } else if (!remark.equals(other.remark))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "HotCardCDTO{" +
                "cardNumber='" + cardNumber + '\'' +
                ", sequenceNo=" + sequenceNo +
                ", primaryAccountNo='" + primaryAccountNo + '\'' +
                ", CIFNo='" + CIFNo + '\'' +
                ", CIFName='" + CIFName + '\'' +
                ", status='" + status + '\'' +
                ", incidentCode='" + incidentCode + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", registerBy='" + registerBy + '\'' +
                ", releasedDate='" + releasedDate + '\'' +
                ", releasedTime='" + releasedTime + '\'' +
                ", releasedBy='" + releasedBy + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
