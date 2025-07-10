package com.ims.eplaton.cashCard.transfer;

import com.chb.coses.framework.transfer.DTO;
import com.chb.coses.framework.constants.Constants;

public class HotCardPageItemCDTO extends DTO
{
    private String cardNumber = Constants.BLANK;
    private String primaryAccountNo = Constants.BLANK;
    private String CIFName = Constants.BLANK;
    private String incidentCode = Constants.BLANK;
    private String status = Constants.BLANK;
    private String registerDate = Constants.BLANK;
    private String releasedDate = Constants.BLANK;

    public HotCardPageItemCDTO()
    {
    }

    // Getter Method
    public String getCardNumber()
    {
        return cardNumber;
    }
    public String getCIFName()
    {
        return CIFName;
    }
    public String getIncidentCode()
    {
        return incidentCode;
    }
    public String getPrimaryAccountNo()
    {
        return primaryAccountNo;
    }
    public String getStatus()
    {
        return status;
    }
    public String getRegisterDate()
    {
        return registerDate;
    }
    public String getReleasedDate()
    {
        return releasedDate;
    }

    // Setter Method
    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }
    public void setCIFName(String CIFName)
    {
        this.CIFName = CIFName;
    }
    public void setIncidentCode(String incidentCode)
    {
        this.incidentCode = incidentCode;
    }
    public void setPrimaryAccountNo(String primaryAccountNo)
    {
        this.primaryAccountNo = primaryAccountNo;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public void setRegisterDate(String registerDate)
    {
        this.registerDate = registerDate;
    }
    public void setReleasedDate(String releasedDate)
    {
        this.releasedDate = releasedDate;
    }
}