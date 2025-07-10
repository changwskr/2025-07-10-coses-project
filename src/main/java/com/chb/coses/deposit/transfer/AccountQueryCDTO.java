package com.chb.coses.deposit.transfer;

import com.chb.coses.framework.transfer.DTO;

/**
 * Account Query Client Data Transfer Object
 */
public class AccountQueryCDTO extends DTO {

    private String accountNumber;
    private String customerId;
    private String accountType;
    private String queryType;
    private String currency;
    private String status;
    private String branchCode;
    private String productCode;

    public AccountQueryCDTO() {
        super();
    }

    public AccountQueryCDTO(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * Validate account number format
     */
    public boolean isValidAccountNumber() {
        return accountNumber != null && accountNumber.length() >= 10;
    }

    /**
     * Validate customer ID format
     */
    public boolean isValidCustomerId() {
        return customerId != null && customerId.length() >= 8;
    }

    @Override
    public String toString() {
        return "AccountQueryCDTO{" +
                "accountNumber='" + accountNumber + '\'' +
                ", customerId='" + customerId + '\'' +
                ", accountType='" + accountType + '\'' +
                ", queryType='" + queryType + '\'' +
                ", currency='" + currency + '\'' +
                ", status='" + status + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", productCode='" + productCode + '\'' +
                '}';
    }
}