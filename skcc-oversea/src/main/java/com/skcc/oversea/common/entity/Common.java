package com.skcc.oversea.common.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "COMMON")
public class Common {

    @Id
    @Column(name = "COMMON_ID", length = 20)
    private String commonId;

    @Column(name = "COMMON_NAME", length = 100)
    private String commonName;

    @Column(name = "COMMON_VALUE", length = 500)
    private String commonValue;

    @Column(name = "STATUS", length = 1)
    private String status;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    // Constructors
    public Common() {
    }

    public Common(String commonId, String commonName, String commonValue) {
        this.commonId = commonId;
        this.commonName = commonName;
        this.commonValue = commonValue;
        this.createDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getCommonId() {
        return commonId;
    }

    public void setCommonId(String commonId) {
        this.commonId = commonId;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getCommonValue() {
        return commonValue;
    }

    public void setCommonValue(String commonValue) {
        this.commonValue = commonValue;
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