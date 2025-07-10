package com.kdb.oversea.cashCard.business.cashCardRule.helper;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

import com.chb.coses.foundation.jndi.*;

import com.chb.coses.common.business.facade.*;
import com.chb.coses.reference.business.facade.*;

import com.kdb.oversea.cashCard.business.cashCard.*;

public class EJBUtilRule
{
    public static ICashCardSB getICashCardSB()
    {
        ICashCardSB iCashCardSB = null;
        CashCardSBHome cashCardSBHome = null;
        try
        {
            cashCardSBHome = (CashCardSBHome)
                    JNDIService.getInstance().lookup(
                    JNDINamesRule.CASH_CARD_HOME);
            iCashCardSB = cashCardSBHome.create();
        }
        catch (CreateException cEx)
        {
            throw new EJBException(cEx);
        }
        catch (NamingException nEx)
        {
            throw new EJBException(nEx);
        }
        return iCashCardSB;
    }

    public static ICommonManagementSB getICommonManagementSB() throws RemoteException
    {
        ICommonManagementSB iCommonManagermentSB = null;

        try
        {
            CommonManagementSBHome commonManagementSBHome =
                    (CommonManagementSBHome)JNDIService.getInstance().
                    lookup(JNDINamesRule.COMMON_MANAGEMENT_HOME);
            iCommonManagermentSB = commonManagementSBHome.create();
        }
        catch (NamingException nEx)
        {
            throw new EJBException(nEx);
        }

        catch (CreateException ce)
        {
            throw new EJBException(ce);
        }

        catch (RemoteException rEx)
        {
            throw new EJBException(rEx);
        }

        return iCommonManagermentSB;
    }

    public static IReferenceManagementSB getIReferenceManagementSB()
     {
         IReferenceManagementSB iReferenceManagement = null;
         try
         {
             ReferenceManagementSBHome referenceManagementSBHome =
                     (ReferenceManagementSBHome)JNDIService.getInstance().
                   lookup(JNDINamesRule.REFERENCE_MANAGEMENT_HOME);
             iReferenceManagement = referenceManagementSBHome.create();
         }
         catch (RemoteException re)
         {
             throw new EJBException(re);
         }
         catch (NamingException nEx)
         {
             throw new EJBException(nEx);
         }
         catch (CreateException ce)
         {
             throw new EJBException(ce);
         }
         return iReferenceManagement;
    }
}