package com.skcc.oversea.cashCard.transfer;

import com.skcc.oversea.framework.transfer.DTO;
import com.skcc.oversea.framework.constants.Constants;

public class HotCardPageItemCDTO extends DTO {
    private String cardNumber = Constants.BLANK;
    private String primaryAccountNo = Constants.BLANK;
    private String CIFName = Constants.BLANK;
    private String incidentCode = Constants.BLANK;
    private String status = Constants.BLANK;
    private String registerDate = Constants.BLANK;
    private String releasedDate = Constants.BLANK;

    public HotCardPageItemCDTO() {
    }

    // Getter Method
    public String getCardNumber() {
        return cardNumber;
    }

    public String getCIFName() {
        return CIFName;
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

    public String getReleasedDate() {
        return releasedDate;
    }

    // Setter Method
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCIFName(String CIFName) {
        this.CIFName = CIFName;
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

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
        result = prime * result + ((primaryAccountNo == null) ? 0 : primaryAccountNo.hashCode());
        result = prime * result + ((CIFName == null) ? 0 : CIFName.hashCode());
        result = prime * result + ((incidentCode == null) ? 0 : incidentCode.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((registerDate == null) ? 0 : registerDate.hashCode());
        result = prime * result + ((releasedDate == null) ? 0 : releasedDate.hashCode());
        return result;
    }
}
