package com.skcc.oversea.cashCard.business.cashCard.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "HOT_CARD_EB")
public class HotCardEB {

    @Id
    @Column(name = "HOT_CARD_ID", length = 20)
    private String hotCardId;

    @Column(name = "CARD_ID", length = 20)
    private String cardId;

    @Column(name = "HOT_CARD_NUMBER", length = 20)
    private String hotCardNumber;

    @Column(name = "HOT_CARD_HOLDER_NAME", length = 100)
    private String hotCardHolderName;

    @Column(name = "BALANCE", precision = 15, scale = 2)
    private BigDecimal balance;

    @Column(name = "STATUS", length = 1)
    private String status;

    @Column(name = "ISSUE_DATE")
    private LocalDateTime issueDate;

    @Column(name = "EXPIRE_DATE")
    private LocalDateTime expireDate;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    // Constructors
    public HotCardEB() {
    }

    public HotCardEB(String hotCardId, String cardId, String hotCardNumber) {
        this.hotCardId = hotCardId;
        this.cardId = cardId;
        this.hotCardNumber = hotCardNumber;
        this.createDate = LocalDateTime.now();
    }

    // Getters and Setters
    public String getHotCardId() {
        return hotCardId;
    }

    public void setHotCardId(String hotCardId) {
        this.hotCardId = hotCardId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getHotCardNumber() {
        return hotCardNumber;
    }

    public void setHotCardNumber(String hotCardNumber) {
        this.hotCardNumber = hotCardNumber;
    }

    public String getHotCardHolderName() {
        return hotCardHolderName;
    }

    public void setHotCardHolderName(String hotCardHolderName) {
        this.hotCardHolderName = hotCardHolderName;
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

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
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