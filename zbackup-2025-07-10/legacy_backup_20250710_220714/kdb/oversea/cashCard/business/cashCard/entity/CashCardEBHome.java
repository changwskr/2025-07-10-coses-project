package com.kdb.oversea.cashCard.business.cashCard.entity;

import javax.ejb.*;
import java.util.*;
import java.math.*;

public interface CashCardEBHome extends javax.ejb.EJBLocalHome
{
    public CashCardEB create(String bankType, String bankCode, String primaryAccountNo, int sequenceNo, String cardNumber, String branchCode, String type) throws CreateException;
    public CashCardEB findByCardNumber(String bankCode, String cardNumber) throws FinderException;
    public CashCardEB findByPrimaryKey(CashCardEBPK pk) throws FinderException;
}