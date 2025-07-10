package com.kdb.oversea.cashCard.business.cashCard.entity;

import java.io.*;
import java.math.*;

public class HotCardEBPK implements Serializable
{




























    public int sequenceNo;

    public HotCardEBPK()
    {
    }

    public HotCardEBPK(int sequenceNo, String cardNumber)
    {
        this.sequenceNo = sequenceNo;
        this.cardNumber = cardNumber;
    }
    public boolean equals(Object obj)
    {
        if (obj != null)
        {
            if (this.getClass().equals(obj.getClass()))
            {
                HotCardEBPK that = (HotCardEBPK) obj;
                return (((this.cardNumber == null) && (that.cardNumber == null)) || (this.cardNumber != null && this.cardNumber.equals(that.cardNumber))) && this.sequenceNo == that.sequenceNo;
            }
        }
        return false;
    }
    public int hashCode()
    {
        return (cardNumber + sequenceNo).hashCode();
    }


    public String cardNumber;


}