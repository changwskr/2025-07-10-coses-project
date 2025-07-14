package com.skcc.oversea.framework.transfer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountPostingDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountNumber;
    private String transactionType;
    private BigDecimal amount;
    private String currency;
    private String description;
    private LocalDateTime postingDate;
    private String status;

    public AccountPostingDTO() {
    }

    public AccountPostingDTO(String accountNumber, String transactionType, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.postingDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(LocalDateTime postingDate) {
        this.postingDate = postingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}