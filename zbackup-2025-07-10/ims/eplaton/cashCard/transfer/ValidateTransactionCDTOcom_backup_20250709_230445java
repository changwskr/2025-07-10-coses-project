package com.ims.eplaton.cashCard.transfer;

import java.math.BigDecimal;

import com.chb.coses.framework.transfer.DTO;
import com.chb.coses.framework.constants.Constants;

import com.ims.eplaton.cashCard.business.constants.CashCardConstants;

public class ValidateTransactionCDTO extends DTO
{
    private String bankCode = Constants.BLANK;
    private String branchCode = Constants.BLANK;
    private String primaryAccountNo = Constants.BLANK;
    private String cardNumber = Constants.BLANK;
    private String passwordNo = Constants.BLANK;
    private BigDecimal transactionAmount = Constants.ZERO;
    private String cancelYN = CashCardConstants.BOOLEAN_FALSE;

    public ValidateTransactionCDTO()
    {
    }

    // Getter Method
    public String getBankCode()
    {
        return bankCode;
    }
    public String getBranchCode()
    {
        return branchCode;
    }
    public String getCardNumber()
    {
        return cardNumber;
    }
    public String getPrimaryAccountNo()
    {
        return primaryAccountNo;
    }
    public BigDecimal getTransactionAmount()
    {
        return transactionAmount;
    }
    public String getPasswordNo()
    {
        return passwordNo;
    }
    public String getCancelYN()
    {
        return cancelYN;
    }

    // Setter Method
    public void setBankCode(String bankCode)
    {
        this.bankCode = bankCode;
    }
    public void setBranchCode(String branchCode)
    {
        this.branchCode = branchCode;
    }
    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }
    public void setPrimaryAccountNo(String primaryAccountNo)
    {
        this.primaryAccountNo = primaryAccountNo;
    }
    public void setTransactionAmount(BigDecimal transactionAmount)
    {
        this.transactionAmount = transactionAmount;
    }
    public void setPasswordNo(String passwordNo)
    {
        this.passwordNo = passwordNo;
    }
    public void setCancelYN(String cancelYN)
    {
        this.cancelYN = cancelYN;
    }
}