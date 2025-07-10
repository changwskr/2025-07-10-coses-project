package com.banking.kdb.oversea.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Customer entity for KDB Oversea
 * 
 * Represents customer information in the banking system.
 */
@Entity
@Table(name = "CUSTOMERS")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CUSTOMER_ID", unique = true, nullable = false, length = 20)
    @NotBlank(message = "Customer ID is required")
    @Size(max = 20, message = "Customer ID cannot exceed 20 characters")
    private String customerId;

    @Column(name = "NAME", nullable = false, length = 100)
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @Column(name = "EMAIL", length = 100)
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @Column(name = "PHONE", length = 20)
    @Size(max = 20, message = "Phone cannot exceed 20 characters")
    private String phone;

    @Column(name = "ADDRESS", length = 500)
    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

    @Column(name = "NATIONALITY", length = 50)
    @Size(max = 50, message = "Nationality cannot exceed 50 characters")
    private String nationality;

    @Column(name = "PASSPORT_NUMBER", length = 50)
    @Size(max = 50, message = "Passport number cannot exceed 50 characters")
    private String passportNumber;

    @Column(name = "DATE_OF_BIRTH")
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @Column(name = "CITY", length = 100)
    @Size(max = 100, message = "City cannot exceed 100 characters")
    private String city;

    @Column(name = "STATE", length = 100)
    @Size(max = 100, message = "State cannot exceed 100 characters")
    private String state;

    @Column(name = "COUNTRY", length = 100)
    @Size(max = 100, message = "Country cannot exceed 100 characters")
    private String country;

    @Column(name = "POSTAL_CODE", length = 20)
    @Size(max = 20, message = "Postal code cannot exceed 20 characters")
    private String postalCode;

    @Column(name = "ID_TYPE", length = 50)
    @Size(max = 50, message = "ID type cannot exceed 50 characters")
    private String idType;

    @Column(name = "ID_NUMBER", length = 50)
    @Size(max = 50, message = "ID number cannot exceed 50 characters")
    private String idNumber;

    @Column(name = "CUSTOMER_TYPE", length = 50)
    @Size(max = 50, message = "Customer type cannot exceed 50 characters")
    private String customerType;

    @Column(name = "RISK_LEVEL", length = 20)
    @Size(max = 20, message = "Risk level cannot exceed 20 characters")
    private String riskLevel;

    @Column(name = "KYC_STATUS", length = 20)
    @Size(max = 20, message = "KYC status cannot exceed 20 characters")
    private String kycStatus;

    @Column(name = "KYC_DATE")
    private LocalDateTime kycDate;

    @Column(name = "STATUS", nullable = false, length = 20)
    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status cannot exceed 20 characters")
    private String status;

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

    public Customer() {
        this.status = "ACTIVE";
        this.createdDateTime = LocalDateTime.now();
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

        Customer customer = (Customer) obj;
        return customerId != null ? customerId.equals(customer.customerId) : customer.customerId == null;
    }

    @Override
    public int hashCode() {
        return customerId != null ? customerId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", nationality='" + nationality + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}