package com.banking.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CashCard Response DTO
 * 
 * Represents the response data for cash card operations.
 */
public class CashCardResponse {

    private String cardNumber;
    private String customerId;
    private String cardType;
    private BigDecimal balance;
    private String status;
    private String bankCode;
    private String branchCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public CashCardResponse() {
    }

    public CashCardResponse(String cardNumber, String customerId, String cardType,
            BigDecimal balance, String status, String bankCode,
            String branchCode, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.cardNumber = cardNumber;
        this.customerId = customerId;
        this.cardType = cardType;
        this.balance = balance;
        this.status = status;
        this.bankCode = bankCode;
        this.branchCode = branchCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}