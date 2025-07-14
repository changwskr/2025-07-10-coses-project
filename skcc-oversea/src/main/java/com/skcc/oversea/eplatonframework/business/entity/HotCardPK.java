package com.skcc.oversea.eplatonframework.business.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HotCardPK implements Serializable {

    @Column(name = "sequence_no", nullable = false)
    private int sequenceNo;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    public HotCardPK() {
    }

    public HotCardPK(int sequenceNo, String cardNumber) {
        this.sequenceNo = sequenceNo;
        this.cardNumber = cardNumber;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        HotCardPK hotCardPK = (HotCardPK) o;
        return sequenceNo == hotCardPK.sequenceNo &&
                Objects.equals(cardNumber, hotCardPK.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequenceNo, cardNumber);
    }

    @Override
    public String toString() {
        return "HotCardPK{" +
                "sequenceNo=" + sequenceNo +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}