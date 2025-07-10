package com.banking.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction Response DTO
 * 
 * Represents the response data for transaction operations.
 */
public class TransactionResponse {

    private String transactionId;
    private String cardNumber;
    private String transactionType;
    private BigDecimal amount;
    private BigDecimal balanceAfterTransaction;
    private String description;
    private String status;
    private LocalDateTime transactionDate;

    // Constructors
    public TransactionResponse() {
    }

    public TransactionResponse(String transactionId, String cardNumber, String transactionType,
            BigDecimal amount, BigDecimal balanceAfterTransaction,
            String description, String status, LocalDateTime transactionDate) {
        this.transactionId = transactionId;
        this.cardNumber = cardNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
        this.description = description;
        this.status = status;
        this.transactionDate = transactionDate;
    }

    // Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}