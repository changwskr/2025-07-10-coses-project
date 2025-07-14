package com.skcc.oversea.cashCard.business.cashCard.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class HotCardPK implements Serializable {
    private static final long serialVersionUID = 1L;

    private int sequenceNo;
    private String cardNumber;

    public HotCardPK() {
    }

    public HotCardPK(int sequenceNo, String cardNumber) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        HotCardPK hotCardPK = (HotCardPK) obj;

        if (sequenceNo != hotCardPK.sequenceNo)
            return false;
        return cardNumber != null ? cardNumber.equals(hotCardPK.cardNumber) : hotCardPK.cardNumber == null;
    }

    @Override
    public int hashCode() {
        int result = sequenceNo;
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        return result;
    }
}