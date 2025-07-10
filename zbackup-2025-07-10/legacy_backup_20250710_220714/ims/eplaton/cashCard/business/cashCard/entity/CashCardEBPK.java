package com.ims.eplaton.cashCard.business.cashCard.entity;

import java.io.*;
import java.math.*;

public class CashCardEBPK implements Serializable
{
    public int sequenceNo;
    public String cardNumber;

    public CashCardEBPK()
    {
    }

    public CashCardEBPK(int sequenceNo, String cardNumber, String bankCode, String primaryAccountNo)
    {
        this.sequenceNo = sequenceNo;
        this.cardNumber = cardNumber;
        this.bankCode = bankCode;
        this.primaryAccountNo = primaryAccountNo;
    }
    public boolean equals(Object obj)
    {
        if (obj != null)
        {
            if (this.getClass().equals(obj.getClass()))
            {
                CashCardEBPK that = (CashCardEBPK) obj;
                return (((this.bankCode == null) && (that.bankCode == null)) || (this.bankCode != null && this.bankCode.equals(that.bankCode))) && (((this.primaryAccountNo == null) && (that.primaryAccountNo == null)) || (this.primaryAccountNo != null && this.primaryAccountNo.equals(that.primaryAccountNo))) && this.sequenceNo == that.sequenceNo && (((this.cardNumber == null) && (that.cardNumber == null)) || (this.cardNumber != null && this.cardNumber.equals(that.cardNumber)));
            }
        }
        return false;
    }
    public int hashCode()
    {
        return (bankCode + primaryAccountNo + sequenceNo + "" +cardNumber).hashCode();
    }
















    public String bankCode;
    public String primaryAccountNo;
}