package com.skcc.oversea.cashCard.transfer;

import java.math.BigDecimal;

import com.skcc.oversea.framework.transfer.DTO;
import com.skcc.oversea.framework.constants.Constants;

import com.skcc.oversea.cashCard.business.constants.CashCardConstants;

public class ValidateTransactionCDTO extends DTO {
    private String bankCode = Constants.BLANK;
    private String branchCode = Constants.BLANK;
    private String primaryAccountNo = Constants.BLANK;
    private String cardNumber = Constants.BLANK;
    private String passwordNo = Constants.BLANK;
    private BigDecimal transactionAmount = Constants.ZERO;
    private String cancelYN = CashCardConstants.BOOLEAN_FALSE;

    public ValidateTransactionCDTO() {
    }

    // Getter Method
    public String getBankCode() {
        return bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPrimaryAccountNo() {
        return primaryAccountNo;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public String getPasswordNo() {
        return passwordNo;
    }

    public String getCancelYN() {
        return cancelYN;
    }

    // Setter Method
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setPrimaryAccountNo(String primaryAccountNo) {
        this.primaryAccountNo = primaryAccountNo;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public void setPasswordNo(String passwordNo) {
        this.passwordNo = passwordNo;
    }

    public void setCancelYN(String cancelYN) {
        this.cancelYN = cancelYN;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bankCode == null) ? 0 : bankCode.hashCode());
        result = prime * result + ((branchCode == null) ? 0 : branchCode.hashCode());
        result = prime * result + ((primaryAccountNo == null) ? 0 : primaryAccountNo.hashCode());
        result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
        result = prime * result + ((passwordNo == null) ? 0 : passwordNo.hashCode());
        result = prime * result + ((transactionAmount == null) ? 0 : transactionAmount.hashCode());
        result = prime * result + ((cancelYN == null) ? 0 : cancelYN.hashCode());
        return result;
    }
}
