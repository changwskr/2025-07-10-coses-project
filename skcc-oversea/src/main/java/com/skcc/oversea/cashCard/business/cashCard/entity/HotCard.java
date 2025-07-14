package com.skcc.oversea.cashCard.business.cashCard.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "HOT_CARD")
@IdClass(HotCardPK.class)
public class HotCard {

    @Id
    @Column(name = "SEQUENCE_NO")
    private int sequenceNo;

    @Id
    @Column(name = "CARD_NUMBER", length = 20)
    private String cardNumber;

    @Column(name = "PRIMARY_ACCOUNT_NO", length = 20)
    private String primaryAccountNo;

    @Column(name = "CIF_NO", length = 20)
    private String cifNo;

    @Column(name = "CIF_NAME", length = 100)
    private String cifName;

    @Column(name = "STATUS", length = 1)
    private String status;

    @Column(name = "INCIDENT_CODE", length = 10)
    private String incidentCode;

    @Column(name = "REGISTER_DATE", length = 8)
    private String registerDate;

    @Column(name = "REGISTER_TIME", length = 6)
    private String registerTime;

    @Column(name = "REGISTER_BY", length = 20)
    private String registerBy;

    @Column(name = "RELEASED_DATE", length = 8)
    private String releasedDate;

    @Column(name = "RELEASED_TIME", length = 6)
    private String releasedTime;

    @Column(name = "RELEASED_BY", length = 20)
    private String releasedBy;

    @Column(name = "REMARK", length = 200)
    private String remark;

    // Constructors
    public HotCard() {
    }

    public HotCard(int sequenceNo, String cardNumber) {
        this.sequenceNo = sequenceNo;
        this.cardNumber = cardNumber;
    }

    // Getters and Setters
    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPrimaryAccountNo() {
        return primaryAccountNo;
    }

    public void setPrimaryAccountNo(String primaryAccountNo) {
        this.primaryAccountNo = primaryAccountNo;
    }

    public String getCifNo() {
        return cifNo;
    }

    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    public String getCifName() {
        return cifName;
    }

    public void setCifName(String cifName) {
        this.cifName = cifName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIncidentCode() {
        return incidentCode;
    }

    public void setIncidentCode(String incidentCode) {
        this.incidentCode = incidentCode;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterBy() {
        return registerBy;
    }

    public void setRegisterBy(String registerBy) {
        this.registerBy = registerBy;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }

    public String getReleasedTime() {
        return releasedTime;
    }

    public void setReleasedTime(String releasedTime) {
        this.releasedTime = releasedTime;
    }

    public String getReleasedBy() {
        return releasedBy;
    }

    public void setReleasedBy(String releasedBy) {
        this.releasedBy = releasedBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}