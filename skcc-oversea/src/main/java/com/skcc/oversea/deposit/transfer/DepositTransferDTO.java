package com.skcc.oversea.deposit.transfer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DepositTransferDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long depositId;
    private String accountNumber;
    private String depositType;
    private BigDecimal amount;
    private String currency;
    private BigDecimal interestRate;
    private LocalDateTime maturityDate;
    private String status;
    private String description;
    private LocalDateTime createDate;

    public DepositTransferDTO() {
    }

    public DepositTransferDTO(String accountNumber, String depositType, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.depositType = depositType;
        this.amount = amount;
        this.createDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getDepositId() {
        return depositId;
    }

    public void setDepositId(Long depositId) {
        this.depositId = depositId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
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

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDateTime getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDateTime maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}