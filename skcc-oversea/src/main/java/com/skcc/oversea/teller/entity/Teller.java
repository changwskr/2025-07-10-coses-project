package com.skcc.oversea.teller.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TELLER")
public class Teller {

    @Id
    @Column(name = "TELLER_ID", length = 20)
    private String tellerId;

    @Column(name = "TELLER_NAME", length = 100)
    private String tellerName;

    @Column(name = "BRANCH_CODE", length = 10)
    private String branchCode;

    @Column(name = "STATUS", length = 1)
    private String status;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    // Constructors
    public Teller() {
    }

    public Teller(String tellerId, String tellerName, String branchCode) {
        this.tellerId = tellerId;
        this.tellerName = tellerName;
        this.branchCode = branchCode;
        this.createDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getTellerId() {
        return tellerId;
    }

    public void setTellerId(String tellerId) {
        this.tellerId = tellerId;
    }

    public String getTellerName() {
        return tellerName;
    }

    public void setTellerName(String tellerName) {
        this.tellerName = tellerName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}