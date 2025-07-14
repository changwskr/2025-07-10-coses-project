package com.skcc.oversea.cashCard.business.cashCard.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 * CashCardPK - Primary Key for CashCard entity
 * 
 * Composite primary key class for CashCard entity
 * in the Spring Boot migration.
 */
@Embeddable
public class CashCardPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private int sequenceNo;
    private String cardNumber;

    public CashCardPK() {
    }

    public CashCardPK(int sequenceNo, String cardNumber) {
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        CashCardPK that = (CashCardPK) obj;

        if (sequenceNo != that.sequenceNo)
            return false;
        return cardNumber != null ? cardNumber.equals(that.cardNumber) : that.cardNumber == null;
    }

    @Override
    public int hashCode() {
        int result = sequenceNo;
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        return result;
    }
}