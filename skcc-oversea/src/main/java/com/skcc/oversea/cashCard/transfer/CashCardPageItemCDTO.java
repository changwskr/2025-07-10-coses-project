package com.skcc.oversea.cashCard.transfer;

import java.math.BigDecimal;

import com.skcc.oversea.framework.transfer.DTO;
import com.skcc.oversea.framework.constants.Constants;

public class CashCardPageItemCDTO extends DTO {
    private String cardNumber = Constants.BLANK;
    private String primaryAccountNo = Constants.BLANK;
    private String CIFName = Constants.BLANK;
    private String currency = Constants.BLANK;
    private BigDecimal dailyAccumAmount = Constants.ZERO;
    private String status = Constants.BLANK;
    private String incidentCode = Constants.BLANK;
    private String registerDate = Constants.BLANK;
    private String issueDate = Constants.BLANK;
    private String effectiveDate = Constants.BLANK;
    private String expiryDate = Constants.BLANK;
    private int invalidAttemptCnt = 0;

    public CashCardPageItemCDTO() {
        cardNumber = Constants.BLANK;
        primaryAccountNo = Constants.BLANK;
        CIFName = Constants.BLANK;
    }

    // Getter Method
    public String getCardNumber() {
        return cardNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCIFName() {
        return CIFName;
    }

    public BigDecimal getDailyAccumAmount() {
        return dailyAccumAmount;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getIncidentCode() {
        return incidentCode;
    }

    public String getPrimaryAccountNo() {
        return primaryAccountNo;
    }

    public String getStatus() {
        return status;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public int getInvaildAttemptCnt() {
        return invalidAttemptCnt;
    }

    // Setter Method
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCIFName(String CIFName) {
        this.CIFName = CIFName;
    }

    public void setDailyAccumAmount(BigDecimal dailyAccumAmount) {
        this.dailyAccumAmount = dailyAccumAmount;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setIncidentCode(String incidentCode) {
        this.incidentCode = incidentCode;
    }

    public void setPrimaryAccountNo(String primaryAccountNo) {
        this.primaryAccountNo = primaryAccountNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public void setInvalidAttemptCnt(int invalidAttemptCnt) {
        this.invalidAttemptCnt = invalidAttemptCnt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CashCardPageItemCDTO other = (CashCardPageItemCDTO) obj;
        if (cardNumber == null) {
            if (other.cardNumber != null)
                return false;
        } else if (!cardNumber.equals(other.cardNumber))
            return false;
        if (primaryAccountNo == null) {
            if (other.primaryAccountNo != null)
                return false;
        } else if (!primaryAccountNo.equals(other.primaryAccountNo))
            return false;
        if (CIFName == null) {
            if (other.CIFName != null)
                return false;
        } else if (!CIFName.equals(other.CIFName))
            return false;
        if (currency == null) {
            if (other.currency != null)
                return false;
        } else if (!currency.equals(other.currency))
            return false;
        if (dailyAccumAmount == null) {
            if (other.dailyAccumAmount != null)
                return false;
        } else if (!dailyAccumAmount.equals(other.dailyAccumAmount))
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
        if (issueDate == null) {
            if (other.issueDate != null)
                return false;
        } else if (!issueDate.equals(other.issueDate))
            return false;
        if (effectiveDate == null) {
            if (other.effectiveDate != null)
                return false;
        } else if (!effectiveDate.equals(other.effectiveDate))
            return false;
        if (expiryDate == null) {
            if (other.expiryDate != null)
                return false;
        } else if (!expiryDate.equals(other.expiryDate))
            return false;
        if (invalidAttemptCnt != other.invalidAttemptCnt)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
        result = prime * result + ((primaryAccountNo == null) ? 0 : primaryAccountNo.hashCode());
        result = prime * result + ((CIFName == null) ? 0 : CIFName.hashCode());
        result = prime * result + ((currency == null) ? 0 : currency.hashCode());
        result = prime * result + ((dailyAccumAmount == null) ? 0 : dailyAccumAmount.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((incidentCode == null) ? 0 : incidentCode.hashCode());
        result = prime * result + ((registerDate == null) ? 0 : registerDate.hashCode());
        result = prime * result + ((issueDate == null) ? 0 : issueDate.hashCode());
        result = prime * result + ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
        result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
        result = prime * result + invalidAttemptCnt;
        return result;
    }

    @Override
    public String toString() {
        return "CashCardPageItemCDTO{" +
                "cardNumber='" + cardNumber + '\'' +
                ", primaryAccountNo='" + primaryAccountNo + '\'' +
                ", CIFName='" + CIFName + '\'' +
                ", currency='" + currency + '\'' +
                ", dailyAccumAmount=" + dailyAccumAmount +
                ", status='" + status + '\'' +
                ", incidentCode='" + incidentCode + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", invalidAttemptCnt=" + invalidAttemptCnt +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
