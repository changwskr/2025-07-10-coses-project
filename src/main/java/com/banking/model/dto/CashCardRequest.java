package com.banking.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * CashCard Request DTO
 * 
 * Represents the request data for cash card operations.
 */
public class CashCardRequest {

    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotBlank(message = "Card type is required")
    private String cardType;

    @NotNull(message = "Initial balance is required")
    @Positive(message = "Initial balance must be positive")
    private BigDecimal initialBalance;

    @NotBlank(message = "Bank code is required")
    private String bankCode;

    @NotBlank(message = "Branch code is required")
    private String branchCode;

    // Constructors
    public CashCardRequest() {
    }

    public CashCardRequest(String customerId, String cardType, BigDecimal initialBalance,
            String bankCode, String branchCode) {
        this.customerId = customerId;
        this.cardType = cardType;
        this.initialBalance = initialBalance;
        this.bankCode = bankCode;
        this.branchCode = branchCode;
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
}