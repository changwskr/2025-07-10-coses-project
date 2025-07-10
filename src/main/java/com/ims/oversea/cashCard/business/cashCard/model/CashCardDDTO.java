package com.ims.oversea.cashCard.business.cashCard.model;

import java.math.BigDecimal;

import com.chb.coses.framework.transfer.DTO;
import com.chb.coses.framework.constants.Constants;

public class CashCardDDTO extends DTO
{
    private String bankType = Constants.BLANK;
    private String bankCode = Constants.BLANK;
    private String primaryAccountNo = Constants.BLANK;
    private String type = Constants.BLANK;
    private int sequenceNo = 0;
    private String cardNumber = Constants.BLANK;
    private String branchCode = Constants.BLANK;
    private String CIFNo = Constants.BLANK;
    private String CIFName = Constants.BLANK;
    //private String cardHolderName = Constants.BLANK;
    private String passwordNo = Constants.BLANK;
    private int invalidAttemptCnt = 0;
    private String secondaryAccountNo = Constants.BLANK;
    private String ternaryAccountNo = Constants.BLANK;
    private String dailyLimitCcy = Constants.BLANK;
    private BigDecimal dailyLimitAmount = Constants.ZERO;
    private BigDecimal dailyAccumAmount = Constants.ZERO;
    private String dailyTrfLimitCcy = Constants.BLANK;
    private BigDecimal dailyTrfLimitAmount = Constants.ZERO;
    private BigDecimal dailyTrfAccumAmount = Constants.ZERO;
    private String dailyAccumResetDate = Constants.BLANK;
    private String dailyAccumResetTime = Constants.BLANK;
    private String effectiveDate = Constants.BLANK;
    private String expiryDate = Constants.BLANK;
    private String status = Constants.BLANK;
    private String incidentCode = Constants.BLANK;
    private String feeWaive = Constants.BLANK;
    private String feeCcy = Constants.BLANK;
    private BigDecimal feeAmount = Constants.ZERO;
    private String registerDate = Constants.BLANK;
    private String registerTime = Constants.BLANK;
    private String registerBy = Constants.BLANK;
    private String remark = Constants.BLANK;
    private String lastUpdateDate = Constants.BLANK;
    private String lastUpdateTime = Constants.BLANK;
    private String lastUpdateUserID = Constants.BLANK;
    private String MISSendDate = Constants.BLANK;
    private String issueDate = Constants.BLANK;

    public CashCardDDTO()
    {
    }

    // Getter Method
    public String getBankCode()
    {
        return bankCode;
    }
    public String getBankType()
    {
        return bankType;
    }
    public String getBranchCode()
    {
        return branchCode;
    }
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
    public BigDecimal getDailyAccumAmount()
    {
        return dailyAccumAmount;
    }
    public String getDailyAccumResetDate()
    {
        return dailyAccumResetDate;
    }
    public String getDailyAccumResetTime()
    {
        return dailyAccumResetTime;
    }
    public BigDecimal getDailyLimitAmount()
    {
        return dailyLimitAmount;
    }
    public String getDailyLimitCcy()
    {
        return dailyLimitCcy;
    }
    public BigDecimal getDailyTrfAccumAmount()
    {
        return dailyTrfAccumAmount;
    }
    public BigDecimal getDailyTrfLimitAmount()
    {
        return dailyTrfLimitAmount;
    }
    public String getDailyTrfLimitCcy()
    {
        return dailyTrfLimitCcy;
    }
    public String getEffectiveDate()
    {
        return effectiveDate;
    }
    public String getExpiryDate()
    {
        return expiryDate;
    }
    public BigDecimal getFeeAmount()
    {
        return feeAmount;
    }
    public String getFeeCcy()
    {
        return feeCcy;
    }
    public String getFeeWaive()
    {
        return feeWaive;
    }
    public String getIncidentCode()
    {
        return incidentCode;
    }
    public int getInvalidAttemptCnt()
    {
        return invalidAttemptCnt;
    }
    public String getLastUpdateDate()
    {
        return lastUpdateDate;
    }
    public String getLastUpdateTime()
    {
        return lastUpdateTime;
    }
    public String getLastUpdateUserID()
    {
        return lastUpdateUserID;
    }
    public String getMISSendDate()
    {
        return MISSendDate;
    }
    public String getPasswordNo()
    {
        return passwordNo;
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
    public String getRemark()
    {
        return remark;
    }
    public String getSecondaryAccountNo()
    {
        return secondaryAccountNo;
    }
    public int getSequenceNo()
    {
        return sequenceNo;
    }
    public String getStatus()
    {
        return status;
    }
    public String getTernaryAccountNo()
    {
        return ternaryAccountNo;
    }
    public String getType()
    {
        return type;
    }
    public String getCIFName()
    {
        return CIFName;
    }
    public String getIssueDate()
    {
        return issueDate;
    }

    // Setter Method
    public void setBankCode(String bankCode)
    {
        this.bankCode = bankCode;
    }
    public void setBankType(String bankType)
    {
        this.bankType = bankType;
    }
    public void setBranchCode(String branchCode)
    {
        this.branchCode = branchCode;
    }
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
    public void setDailyAccumAmount(BigDecimal dailyAccumAmount)
    {
        this.dailyAccumAmount = dailyAccumAmount;
    }
    public void setDailyAccumResetDate(String dailyAccumResetDate)
    {
        this.dailyAccumResetDate = dailyAccumResetDate;
    }
    public void setDailyAccumResetTime(String dailyAccumResetTime)
    {
        this.dailyAccumResetTime = dailyAccumResetTime;
    }
    public void setDailyLimitAmount(BigDecimal dailyLimitAmount)
    {
        this.dailyLimitAmount = dailyLimitAmount;
    }
    public void setDailyLimitCcy(String dailyLimitCcy)
    {
        this.dailyLimitCcy = dailyLimitCcy;
    }
    public void setDailyTrfAccumAmount(BigDecimal dailyTrfAccumAmount)
    {
        this.dailyTrfAccumAmount = dailyTrfAccumAmount;
    }
    public void setDailyTrfLimitAmount(BigDecimal dailyTrfLimitAmount)
    {
        this.dailyTrfLimitAmount = dailyTrfLimitAmount;
    }
    public void setDailyTrfLimitCcy(String dailyTrfLimitCcy)
    {
        this.dailyTrfLimitCcy = dailyTrfLimitCcy;
    }
    public void setEffectiveDate(String effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }
    public void setExpiryDate(String expiryDate)
    {
        this.expiryDate = expiryDate;
    }
    public void setFeeAmount(BigDecimal feeAmount)
    {
        this.feeAmount = feeAmount;
    }
    public void setFeeCcy(String feeCcy)
    {
        this.feeCcy = feeCcy;
    }
    public void setFeeWaive(String feeWaive)
    {
        this.feeWaive = feeWaive;
    }
    public void setIncidentCode(String incidentCode)
    {
        this.incidentCode = incidentCode;
    }
    public void setInvalidAttemptCnt(int invalidAttemptCnt)
    {
        this.invalidAttemptCnt = invalidAttemptCnt;
    }
    public void setLastUpdateDate(String lastUpdateDate)
    {
        this.lastUpdateDate = lastUpdateDate;
    }
    public void setLastUpdateTime(String lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }
    public void setLastUpdateUserID(String lastUpdateUserID)
    {
        this.lastUpdateUserID = lastUpdateUserID;
    }
    public void setMISSendDate(String MISSendDate)
    {
        this.MISSendDate = MISSendDate;
    }
    public void setPasswordNo(String passwordNo)
    {
        this.passwordNo = passwordNo;
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
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    public void setSecondaryAccountNo(String secondaryAccountNo)
    {
        this.secondaryAccountNo = secondaryAccountNo;
    }
    public void setSequenceNo(int sequenceNo)
    {
        this.sequenceNo = sequenceNo;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public void setTernaryAccountNo(String ternaryAccountNo)
    {
        this.ternaryAccountNo = ternaryAccountNo;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    public void setCIFName(String CIFName)
    {
        this.CIFName = CIFName;
    }
    public void setIssueDate(String issueDate)
    {
        this.issueDate = issueDate;
    }
}