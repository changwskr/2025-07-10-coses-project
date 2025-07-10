package com.kdb.oversea.cashCard.transfer;

import java.math.BigDecimal;

import com.chb.coses.framework.transfer.DTO;
import com.chb.coses.framework.constants.Constants;

public class CashCardPageItemCDTO extends DTO
{
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

    public CashCardPageItemCDTO()
    {
    }

    // Getter Method
    public String getCardNumber()
    {
        return cardNumber;
    }
    public String getCurrency()
    {
        return currency;
    }
    public String getCIFName()
    {
        return CIFName;
    }
    public BigDecimal getDailyAccumAmount()
    {
        return dailyAccumAmount;
    }
    public String getEffectiveDate()
    {
        return effectiveDate;
    }
    public String getExpiryDate()
    {
        return expiryDate;
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
    public String getIssueDate()
    {
        return issueDate;
    }
    public int getInvaildAttemptCnt()
    {
        return invalidAttemptCnt;
    }

    // Setter Method
    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }
    public void setCurrency(String currency)
    {
        this.currency = currency;
    }
    public void setCIFName(String CIFName)
    {
        this.CIFName = CIFName;
    }
    public void setDailyAccumAmount(BigDecimal dailyAccumAmount)
    {
        this.dailyAccumAmount = dailyAccumAmount;
    }
    public void setEffectiveDate(String effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }
    public void setExpiryDate(String expiryDate)
    {
        this.expiryDate = expiryDate;
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
    public void setIssueDate(String issueDate)
    {
        this.issueDate = issueDate;
    }
    public void setInvalidAttemptCnt(int invalidAttemptCnt)
    {
        this.invalidAttemptCnt = invalidAttemptCnt;
    }
}