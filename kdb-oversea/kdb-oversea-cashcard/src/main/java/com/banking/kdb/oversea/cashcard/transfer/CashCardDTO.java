package com.banking.kdb.oversea.cashcard.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * CashCard DTO for KDB Oversea
 * 
 * Data transfer object for cash card information.
 */
public class CashCardDTO {

    private Long id;

    @JsonProperty("cardNumber")
    @NotBlank(message = "Card number is required")
    @Size(max = 20, message = "Card number cannot exceed 20 characters")
    private String cardNumber;

    @JsonProperty("customerId")
    @NotBlank(message = "Customer ID is required")
    @Size(max = 20, message = "Customer ID cannot exceed 20 characters")
    private String customerId;

    @JsonProperty("cardType")
    @NotBlank(message = "Card type is required")
    @Size(max = 20, message = "Card type cannot exceed 20 characters")
    private String cardType;

    @JsonProperty("cardStatus")
    @NotBlank(message = "Card status is required")
    @Size(max = 20, message = "Card status cannot exceed 20 characters")
    private String cardStatus;

    @JsonProperty("dailyLimit")
    @NotNull(message = "Daily limit is required")
    @DecimalMin(value = "0.0", message = "Daily limit cannot be negative")
    private BigDecimal dailyLimit;

    @JsonProperty("monthlyLimit")
    @NotNull(message = "Monthly limit is required")
    @DecimalMin(value = "0.0", message = "Monthly limit cannot be negative")
    private BigDecimal monthlyLimit;

    @JsonProperty("currency")
    @NotBlank(message = "Currency is required")
    @Size(max = 3, message = "Currency cannot exceed 3 characters")
    private String currency;

    @JsonProperty("expiryDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    @JsonProperty("pin")
    @Size(max = 10, message = "PIN cannot exceed 10 characters")
    private String pin;

    @JsonProperty("createdBy")
    @Size(max = 50, message = "Created by cannot exceed 50 characters")
    private String createdBy;

    @JsonProperty("createdDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    @JsonProperty("modifiedBy")
    @Size(max = 50, message = "Modified by cannot exceed 50 characters")
    private String modifiedBy;

    @JsonProperty("modifiedDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDateTime;

    public CashCardDTO() {
        this.cardStatus = "ACTIVE";
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
    public String toString() {
        return "CashCardDTO{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", customerId='" + customerId + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardStatus='" + cardStatus + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}