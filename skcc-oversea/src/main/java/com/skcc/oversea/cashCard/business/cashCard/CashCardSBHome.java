package com.skcc.oversea.cashCard.business.cashCard;

// 1 TEST ADD
// 2 TEST ADD

import javax.ejb.*;
import java.util.*;

public interface CashCardSBHome extends javax.ejb.EJBLocalHome
{
    public CashCardSB create() throws CreateException;
}

