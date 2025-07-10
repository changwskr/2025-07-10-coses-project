package com.banking.kdb.oversea.common.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Customer DTO for KDB Oversea Common
 * 
 * Data transfer object for customer information.
 */
public class CustomerDTO {

    private Long id;

    @JsonProperty("customerId")
    @NotBlank(message = "Customer ID is required")
    @Size(max = 20, message = "Customer ID cannot exceed 20 characters")
    private String customerId;

    @JsonProperty("name")
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @JsonProperty("email")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @JsonProperty("phoneNumber")
    @Pattern(regexp = "^[0-9\\-\\+\\s\\(\\)]+$", message = "Phone number should contain only digits, spaces, hyphens, plus signs, and parentheses")
    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    private String phoneNumber;

    @JsonProperty("dateOfBirth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @JsonProperty("address")
    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    @JsonProperty("city")
    @Size(max = 100, message = "City cannot exceed 100 characters")
    private String city;

    @JsonProperty("state")
    @Size(max = 100, message = "State cannot exceed 100 characters")
    private String state;

    @JsonProperty("country")
    @Size(max = 100, message = "Country cannot exceed 100 characters")
    private String country;

    @JsonProperty("postalCode")
    @Size(max = 20, message = "Postal code cannot exceed 20 characters")
    private String postalCode;

    @JsonProperty("nationality")
    @Size(max = 100, message = "Nationality cannot exceed 100 characters")
    private String nationality;

    @JsonProperty("idType")
    @Size(max = 50, message = "ID type cannot exceed 50 characters")
    private String idType;

    @JsonProperty("idNumber")
    @Size(max = 50, message = "ID number cannot exceed 50 characters")
    private String idNumber;

    @JsonProperty("status")
    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status cannot exceed 20 characters")
    private String status;

    @JsonProperty("customerType")
    @Size(max = 50, message = "Customer type cannot exceed 50 characters")
    private String customerType;

    @JsonProperty("riskLevel")
    @Size(max = 20, message = "Risk level cannot exceed 20 characters")
    private String riskLevel;

    @JsonProperty("kycStatus")
    @Size(max = 20, message = "KYC status cannot exceed 20 characters")
    private String kycStatus;

    @JsonProperty("kycDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime kycDate;

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

    public CustomerDTO() {
        this.status = "ACTIVE";
        this.customerType = "INDIVIDUAL";
        this.riskLevel = "LOW";
        this.kycStatus = "PENDING";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }

    public LocalDateTime getKycDate() {
        return kycDate;
    }

    public void setKycDate(LocalDateTime kycDate) {
        this.kycDate = kycDate;
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
        return "CustomerDTO{" +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}