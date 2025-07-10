package com.kdb.oversea.cashCard.business.cashCard.entity;

import javax.ejb.*;

abstract public class CashCardEBBean extends com.chb.coses.framework.business.AbstractEntityBean implements EntityBean{
    public CashCardEBPK ejbCreate(java.lang.String bankType, java.lang.String bankCode, java.lang.String primaryAccountNo, int sequenceNo, java.lang.String cardNumber, java.lang.String branchCode, java.lang.String type) throws CreateException {
        setBankType(bankType);
        setBankCode(bankCode);
        setPrimaryAccountNo(primaryAccountNo);
        setSequenceNo(sequenceNo);
        setCardNumber(cardNumber);
        setBranchCode(branchCode);
        setType(type);
        return null;
    }
    public void ejbPostCreate(java.lang.String bankType, java.lang.String bankCode, java.lang.String primaryAccountNo, int sequenceNo, java.lang.String cardNumber, java.lang.String branchCode, java.lang.String type) throws CreateException {
        /**@todo Complete this method*/
    }

    public abstract void setBankType(java.lang.String bankType);
    public abstract void setBankCode(java.lang.String bankCode);
    public abstract void setPrimaryAccountNo(java.lang.String primaryAccountNo);
    public abstract void setSequenceNo(int sequenceNo);
    public abstract void setCardNumber(java.lang.String cardNumber);
    public abstract void setBranchCode(java.lang.String branchCode);
    public abstract void setType(java.lang.String type);
    public abstract void setCifNo(java.lang.String cifNo);
    public abstract void setPasswordNo(java.lang.String passwordNo);
    public abstract void setInvalidAttemptCnt(int invalidAttemptCnt);
    public abstract void setSecondaryAccountNo(java.lang.String secondaryAccountNo);
    public abstract void setTernaryAccountNo(java.lang.String ternaryAccountNo);
    public abstract void setDailyLimitCcy(java.lang.String dailyLimitCcy);
    public abstract void setDailyLimitAmount(java.math.BigDecimal dailyLimitAmount);
    public abstract void setDailyAccumAmount(java.math.BigDecimal dailyAccumAmount);
    public abstract void setDailyTrfLimitCcy(java.lang.String dailyTrfLimitCcy);
    public abstract void setDailyTrfLimitAmount(java.math.BigDecimal dailyTrfLimitAmount);
    public abstract void setDailyTrfAccumAmount(java.math.BigDecimal dailyTrfAccumAmount);
    public abstract void setDailyAccumResetDate(java.lang.String dailyAccumResetDate);
    public abstract void setDailyAccumResetTime(java.lang.String dailyAccumResetTime);
    public abstract void setEffectiveDate(java.lang.String effectiveDate);
    public abstract void setExpiryDate(java.lang.String expiryDate);
    public abstract void setStatus(java.lang.String status);
    public abstract void setIncidentCode(java.lang.String incidentCode);
    public abstract void setFeeWaive(java.lang.String feeWaive);
    public abstract void setFeeCcy(java.lang.String feeCcy);
    public abstract void setFeeAmount(java.math.BigDecimal feeAmount);
    public abstract void setRegisterDate(java.lang.String registerDate);
    public abstract void setRegisterTime(java.lang.String registerTime);
    public abstract void setRegisterBy(java.lang.String registerBy);
    public abstract void setRemark(java.lang.String remark);
    public abstract void setLastUpdateDate(java.lang.String lastUpdateDate);
    public abstract void setLastUpdateTime(java.lang.String lastUpdateTime);
    public abstract void setLastUpdateUserId(java.lang.String lastUpdateUserId);
    public abstract void setMisSendDate(java.lang.String misSendDate);
    public abstract void setCifName(java.lang.String cifName);
    public abstract void setIssueDate(java.lang.String issueDate);
    public abstract java.lang.String getBankType();
    public abstract java.lang.String getBankCode();
    public abstract java.lang.String getPrimaryAccountNo();
    public abstract int getSequenceNo();
    public abstract java.lang.String getCardNumber();
    public abstract java.lang.String getBranchCode();
    public abstract java.lang.String getType();
    public abstract java.lang.String getCifNo();
    public abstract java.lang.String getPasswordNo();
    public abstract int getInvalidAttemptCnt();
    public abstract java.lang.String getSecondaryAccountNo();
    public abstract java.lang.String getTernaryAccountNo();
    public abstract java.lang.String getDailyLimitCcy();
    public abstract java.math.BigDecimal getDailyLimitAmount();
    public abstract java.math.BigDecimal getDailyAccumAmount();
    public abstract java.lang.String getDailyTrfLimitCcy();
    public abstract java.math.BigDecimal getDailyTrfLimitAmount();
    public abstract java.math.BigDecimal getDailyTrfAccumAmount();
    public abstract java.lang.String getDailyAccumResetDate();
    public abstract java.lang.String getDailyAccumResetTime();
    public abstract java.lang.String getEffectiveDate();
    public abstract java.lang.String getExpiryDate();
    public abstract java.lang.String getStatus();
    public abstract java.lang.String getIncidentCode();
    public abstract java.lang.String getFeeWaive();
    public abstract java.lang.String getFeeCcy();
    public abstract java.math.BigDecimal getFeeAmount();
    public abstract java.lang.String getRegisterDate();
    public abstract java.lang.String getRegisterTime();
    public abstract java.lang.String getRegisterBy();
    public abstract java.lang.String getRemark();
    public abstract java.lang.String getLastUpdateDate();
    public abstract java.lang.String getLastUpdateTime();
    public abstract java.lang.String getLastUpdateUserId();
    public abstract java.lang.String getMisSendDate();
    public abstract java.lang.String getCifName();
    public abstract java.lang.String getIssueDate();
}