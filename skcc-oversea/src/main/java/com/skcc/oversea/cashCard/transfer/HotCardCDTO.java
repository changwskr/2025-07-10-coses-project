package com.skcc.oversea.cashCard.transfer;

import java.math.BigDecimal;

import com.skcc.oversea.framework.transfer.DTO;
import com.skcc.oversea.framework.constants.Constants;

public class HotCardCDTO extends DTO
{
    private String cardNumber = Constants.BLANK;
    private int sequenceNo = 0;
    private String primaryAccountNo = Constants.BLANK;
    private String CIFNo = Constants.BLANK;
    private String CIFName = Constants.BLANK;
    //private String cardHolderName = Constants.BLANK;
    private String status = Constants.BLANK;
    private String incidentCode = Constants.BLANK;
    //private String issueDate = Constants.BLANK;
    //private String effectiveDate = Constants.BLANK;
    //private String expiryDate = Constants.BLANK;
    private String registerDate = Constants.BLANK;
    private String registerTime = Constants.BLANK;
    private String registerBy = Constants.BLANK;
    private String releasedDate = Constants.BLANK;
    private String releasedTime = Constants.BLANK;
    private String releasedBy = Constants.BLANK;
    private String remark = Constants.BLANK;

    public HotCardCDTO()
    {
    }

    // Getter Method
    /*public String getCardHolderName()
    {
        return cardHolderName;
    }*/
    public String getCardNumber()
    {
        return cardNumber;
    }
    public String getCIFNo()
    {
        return CIFNo;
    }
    public String getCIFName()
    {
        return CIFName;
    }
    /*public String getEffectiveDate()
    {
        return effectiveDate;
    }
    public String getExpiryDate()
    {
        return expiryDate;
    }*/
    public String getIncidentCode()
    {
        return incidentCode;
    }
    public String getPrimaryAccountNo()
    {
        return primaryAccountNo;
    }
    public String getRegisterBy()
    {
        return registerBy;
    }
    public String getRegisterDate()
    {
        return registerDate;
    }
    public String getRegisterTime()
    {
        return registerTime;
    }
    public String getReleasedBy()
    {
        return releasedBy;
    }
    public String getReleasedDate()
    {
        return releasedDate;
    }
    public String getReleasedTime()
    {
        return releasedTime;
    }
    public String getRemark()
    {
        return remark;
    }
    public int getSequenceNo()
    {
        return sequenceNo;
    }
    public String getStatus()
    {
        return status;
    }
    /*public String getIssueDate()
    {
        return issueDate;
    }*/

    // Setter Method
    /*public void setCardHolderName(String cardHolderName)
    {
        this.cardHolderName = cardHolderName;
    }*/
    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }
    public void setCIFNo(String CIFNo)
    {
        this.CIFNo = CIFNo;
    }
    public void setCIFName(String CIFName)
    {
        this.CIFName = CIFName;
    }
    /*public void setEffectiveDate(String effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }
    public void setExpiryDate(String expiryDate)
    {
        this.expiryDate = expiryDate;
    }*/
    public void setIncidentCode(String incidentCode)
    {
        this.incidentCode = incidentCode;
    }
    public void setPrimaryAccountNo(String primaryAccountNo)
    {
        this.primaryAccountNo = primaryAccountNo;
    }
    public void setRegisterBy(String registerBy)
    {
        this.registerBy = registerBy;
    }
    public void setRegisterDate(String registerDate)
    {
        this.registerDate = registerDate;
    }
    public void setRegisterTime(String registerTime)
    {
        this.registerTime = registerTime;
    }
    public void setReleasedBy(String releasedBy)
    {
        this.releasedBy = releasedBy;
    }
    public void setReleasedDate(String releasedDate)
    {
        this.releasedDate = releasedDate;
    }
    public void setReleasedTime(String releasedTime)
    {
        this.releasedTime = releasedTime;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    public void setSequenceNo(int sequenceNo)
    {
        this.sequenceNo = sequenceNo;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    /*public void setIssueDate(String issueDate)
    {
        this.issueDate = issueDate;
    }*/
}

