package com.banking.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Transaction Request DTO
 * 
 * Represents the request data for transaction operations.
 */
public class TransactionRequest {

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotBlank(message = "Transaction type is required")
    private String transactionType; // DEPOSIT, WITHDRAWAL, TRANSFER

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Description is required")
    private String description;

    private String targetCardNumber; // For transfer transactions

    // Constructors
    public TransactionRequest() {
    }

    public TransactionRequest(String cardNumber, String transactionType,
            BigDecimal amount, String description) {
        this.cardNumber = cardNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
    }

    public TransactionRequest(String cardNumber, String transactionType,
            BigDecimal amount, String description, String targetCardNumber) {
        this.cardNumber = cardNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.targetCardNumber = targetCardNumber;
    }

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetCardNumber() {
        return targetCardNumber;
    }

    public void setTargetCardNumber(String targetCardNumber) {
        this.targetCardNumber = targetCardNumber;
    }
}