package com.banking.kdb.oversea.cashcard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * CashCard entity for KDB Oversea
 * 
 * Represents cash card information in the banking system.
 */
@Entity
@Table(name = "CASH_CARDS")
public class CashCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CARD_NUMBER", unique = true, nullable = false, length = 20)
    @NotBlank(message = "Card number is required")
    @Size(max = 20, message = "Card number cannot exceed 20 characters")
    private String cardNumber;

    @Column(name = "CUSTOMER_ID", nullable = false, length = 20)
    @NotBlank(message = "Customer ID is required")
    @Size(max = 20, message = "Customer ID cannot exceed 20 characters")
    private String customerId;

    @Column(name = "CARD_TYPE", nullable = false, length = 20)
    @NotBlank(message = "Card type is required")
    @Size(max = 20, message = "Card type cannot exceed 20 characters")
    private String cardType;

    @Column(name = "CARD_STATUS", nullable = false, length = 20)
    @NotBlank(message = "Card status is required")
    @Size(max = 20, message = "Card status cannot exceed 20 characters")
    private String cardStatus;

    @Column(name = "DAILY_LIMIT", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Daily limit is required")
    @DecimalMin(value = "0.0", message = "Daily limit cannot be negative")
    private BigDecimal dailyLimit;

    @Column(name = "MONTHLY_LIMIT", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "Monthly limit is required")
    @DecimalMin(value = "0.0", message = "Monthly limit cannot be negative")
    private BigDecimal monthlyLimit;

    @Column(name = "CURRENCY", nullable = false, length = 3)
    @NotBlank(message = "Currency is required")
    @Size(max = 3, message = "Currency cannot exceed 3 characters")
    private String currency;

    @Column(name = "EXPIRY_DATE")
    private LocalDate expiryDate;

    @Column(name = "PIN", length = 10)
    @Size(max = 10, message = "PIN cannot exceed 10 characters")
    private String pin;

    @Column(name = "CREATED_BY", length = 50)
    @Size(max = 50, message = "Created by cannot exceed 50 characters")
    private String createdBy;

    @Column(name = "CREATED_DATETIME")
    private LocalDateTime createdDateTime;

    @Column(name = "MODIFIED_BY", length = 50)
    @Size(max = 50, message = "Modified by cannot exceed 50 characters")
    private String modifiedBy;

    @Column(name = "MODIFIED_DATETIME")
    private LocalDateTime modifiedDateTime;

    public CashCard() {
        this.cardStatus = "ACTIVE";
        this.createdDateTime = LocalDateTime.now();
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

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        CashCard that = (CashCard) obj;
        return cardNumber != null ? cardNumber.equals(that.cardNumber) : that.cardNumber == null;
    }

    @Override
    public int hashCode() {
        return cardNumber != null ? cardNumber.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CashCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", customerId='" + customerId + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardStatus='" + cardStatus + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}