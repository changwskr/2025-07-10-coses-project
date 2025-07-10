package com.chb.coses.deposit.transfer;

import com.chb.coses.framework.transfer.DTO;

/**
 * Deposit Data Transfer Object
 */
public class DepositDTO extends DTO {

    private String accountNumber;
    private String accountType;
    private double balance;
    private String currency;
    private String status;
    private String customerId;

    public DepositDTO() {
        super();
    }

    public DepositDTO(String accountNumber, String accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "DepositDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}