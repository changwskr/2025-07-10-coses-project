package com.ims.oversea.cashCard.transfer;

import com.chb.coses.framework.constants.Constants;

import com.ims.oversea.cashCard.transfer.CardParentQueryConditionCDTO;

public class CashCardConditionCDTO extends CardParentQueryConditionCDTO
{
    private String CIFNo = Constants.BLANK;
    private String CIFName = Constants.BLANK;
    private String primaryAccountNo = Constants.BLANK;
    private String status = Constants.BLANK;
    private String fromDate = Constants.BLANK;
    private String toDate = Constants.BLANK;

    public CashCardConditionCDTO()
    {
        super("CASH_CARD", "CARD_NUMBER", "ASC",
              "CARD_NUMBER, PRIMARY_ACCOUNT_NO, CIF_NAME, DAILY_LIMIT_CCY, " +
              "DAILY_ACCUM_AMOUNT, STATUS, INCIDENT_CODE, EFFECTIVE_DATE, " +
              "EXPIRY_DATE, REGISTER_DATE, ISSUE_DATE, INVALID_ATTEMPT_CNT");
    }

    public CashCardConditionCDTO(String orderByMethod)
    {
        super("CASH_CARD", "CARD_NUMBER", orderByMethod,
              "CARD_NUMBER, PRIMARY_ACCOUNT_NO, CIF_NAME, DAILY_LIMIT_CCY, " +
              "DAILY_ACCUM_AMOUNT, STATUS, INCIDENT_CODE, EFFECTIVE_DATE, " +
              "EXPIRY_DATE, REGISTER_DATE, ISSUE_DATE, INVALID_ATTEMPT_CNT");
    }

    // Getter Method
    public String getCIFNo()
    {
        return CIFNo;
    }
    public String getCIFName()
    {
        return CIFName;
    }
    public String getPrimaryAccountNo()
    {
        return primaryAccountNo;
    }
    public String getStatus()
    {
        return status;
    }
    public String getFromDate()
    {
        return fromDate;
    }
    public String getToDate()
    {
        return toDate;
    }

    // Setter Method
    public void setCIFNo(String CIFNo)
    {
        this.CIFNo = CIFNo;
    }
    public void setCIFName(String CIFName)
    {
        this.CIFName = CIFName;
    }
    public void setPrimaryAccountNo(String primaryAccountNo)
    {
        this.primaryAccountNo = primaryAccountNo;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public void setFromDate(String fromDate)
    {
        this.fromDate = fromDate;
    }
    public void setToDate(String toDate)
    {
        this.toDate = toDate;
    }
}