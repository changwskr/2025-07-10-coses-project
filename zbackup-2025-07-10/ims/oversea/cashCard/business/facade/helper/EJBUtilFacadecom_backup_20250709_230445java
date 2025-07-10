package com.ims.oversea.cashCard.business.facade.helper;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;

import com.chb.coses.foundation.jndi.*;

import com.ims.oversea.cashCard.business.cashCardRule.*;
import com.ims.oversea.cashCard.business.facade.helper.JNDINamesFacade;

import com.chb.coses.deposit.business.facade.*;
import com.chb.coses.reference.business.facade.*;

public class EJBUtilFacade
{
    public static ICashCardRuleSB getICashCardRuleSB()
    {
        ICashCardRuleSB iCashCardRuleSB = null;
        CashCardRuleSBHome cashCardRuleSBHome = null;
        try
        {
            cashCardRuleSBHome = (CashCardRuleSBHome)
                    JNDIService.getInstance().lookup(
                    JNDINamesFacade.CASH_CARD_RULE_HOME);
            iCashCardRuleSB = cashCardRuleSBHome.create();
        }
        catch (CreateException cEx)
        {
            throw new EJBException(cEx);
        }
        catch (NamingException nEx)
        {
            throw new EJBException(nEx);
        }
        return iCashCardRuleSB;
    }

    public static IAccountManagement getIAccountManagement()
    {
        IAccountManagement iDepFacade = null;
        AccountManagementSBHome accountHome = null;

        try
        {
            accountHome = (AccountManagementSBHome)JNDIService.getInstance().lookup(
                    JNDINamesFacade.ACCOUNT_MANAGEMENT_HOME);
            iDepFacade = accountHome.create();
        }
        catch(CreateException ce)
        {
            throw new EJBException(ce);
        }
        catch(NamingException ne)
        {
            throw new EJBException(ne);
        }
        catch(RemoteException re)
        {
            throw new EJBException(re);
        }
        return iDepFacade;
    }

    public static IReferenceManagementSB getIReferenceManagementSB()
     {
         IReferenceManagementSB iReferenceManagement = null;
         try
         {
             ReferenceManagementSBHome referenceManagementSBHome =
                     (ReferenceManagementSBHome)JNDIService.getInstance().
                   lookup(JNDINamesFacade.REFERENCE_MANAGEMENT_HOME);
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