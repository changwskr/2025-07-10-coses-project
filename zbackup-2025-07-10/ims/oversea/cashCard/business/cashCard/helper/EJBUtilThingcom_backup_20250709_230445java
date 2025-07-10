package com.ims.oversea.cashCard.business.cashCard.helper;
//test 1
//test 2

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

import com.chb.coses.foundation.jndi.*;

import com.ims.oversea.cashCard.business.cashCard.model.*;
import com.ims.oversea.cashCard.business.cashCard.entity.*;

public class EJBUtilThing
{
    public static CashCardEBHome getCashCardEBHome()
    {
        CashCardEBHome cashCardEBHome = null;

        try
        {
            cashCardEBHome = (CashCardEBHome)
                    JNDIService.getInstance().lookup(
                    JNDINamesThing.CASH_CARDEB_HOME);
        }
        catch (NamingException nEx)
        {
            throw new EJBException(nEx);
        }
        return cashCardEBHome;
    }

    public static HotCardEBHome getHotCardEBHome()
    {
        HotCardEBHome hotCardEBHome = null;

        try
        {
            hotCardEBHome = (HotCardEBHome)
                    JNDIService.getInstance().lookup(
                    JNDINamesThing.HOT_CARDEB_HOME);
        }
        catch (NamingException nEx)
        {
            throw new EJBException(nEx);
        }
        return hotCardEBHome;
    }
}
