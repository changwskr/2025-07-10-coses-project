package com.banking.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CashCard Entity
 * 
 * JPA entity representing a cash card in the banking system.
 */
@Entity
@Table(name = "cash_cards")
public class CashCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", unique = true, nullable = false, length = 20)
    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @Column(name = "customer_id", nullable = false, length = 50)
    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @Column(name = "card_type", nullable = false, length = 20)
    @NotBlank(message = "Card type is required")
    private String cardType;

    @Column(name = "balance", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Balance is required")
    @Positive(message = "Balance must be positive")
    private BigDecimal balance;

    @Column(name = "status", nullable = false, length = 20)
    @NotBlank(message = "Status is required")
    private String status; // ACTIVE, INACTIVE, SUSPENDED, EXPIRED

    @Column(name = "bank_code", nullable = false, length = 10)
    @NotBlank(message = "Bank code is required")
    private String bankCode;

    @Column(name = "branch_code", nullable = false, length = 10)
    @NotBlank(message = "Branch code is required")
    private String branchCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "daily_limit", precision = 15, scale = 2)
    private BigDecimal dailyLimit;

    @Column(name = "monthly_limit", precision = 15, scale = 2)
    private BigDecimal monthlyLimit;

    // Constructors
    public CashCard() {
        this.createdAt = LocalDateTime.now();
        this.status = "ACTIVE";
    }

    public CashCard(String cardNumber, String customerId, String cardType,
            BigDecimal balance, String bankCode, String branchCode) {
        this();
        this.cardNumber = cardNumber;
        this.customerId = customerId;
        this.cardType = cardType;
        this.balance = balance;
        this.bankCode = bankCode;
        this.branchCode = branchCode;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public BigDecimal getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(BigDecimal monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}