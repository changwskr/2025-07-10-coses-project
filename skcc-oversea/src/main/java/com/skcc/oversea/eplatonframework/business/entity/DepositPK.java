package com.skcc.oversea.eplatonframework.business.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DepositPK implements Serializable {

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    public DepositPK() {
    }

    public DepositPK(String accountNumber, String customerId) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DepositPK depositPK = (DepositPK) o;
        return Objects.equals(accountNumber, depositPK.accountNumber) &&
                Objects.equals(customerId, depositPK.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, customerId);
    }

    @Override
    public String toString() {
        return "DepositPK{" +
                "accountNumber='" + accountNumber + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}