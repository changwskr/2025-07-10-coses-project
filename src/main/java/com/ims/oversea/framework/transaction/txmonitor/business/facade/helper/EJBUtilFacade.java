package com.ims.oversea.framework.transaction.txmonitor.business.facade.helper;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.naming.NamingException;
import com.chb.coses.foundation.jndi.*;
import com.chb.coses.reference.business.facade.*;
import com.ims.oversea.framework.transaction.txmonitor.business.rule.*;

public class EJBUtilFacade
{
    public static ISPtxmonitorRuleSB getISPtxmonitorRuleSB()
    {
        ISPtxmonitorRuleSB iSPtxmonitorRuleSB = null;
        SPtxmonitorRuleSBHome sptxmonitorRuleSBHome = null;
        try
        {
            sptxmonitorRuleSBHome = (SPtxmonitorRuleSBHome)
                    JNDIService.getInstance().lookup(
                    JNDINamesFacade.SPTXMONITOR_RULE_HOME);
            iSPtxmonitorRuleSB = (ISPtxmonitorRuleSB)sptxmonitorRuleSBHome.create();
        }
        catch (CreateException cEx)
        {
            cEx.printStackTrace();
            throw new EJBException(cEx);
        }
        catch (NamingException nEx)
        {
            nEx.printStackTrace();
            throw new EJBException(nEx);
        }
        catch (RemoteException rEx)
        {
            rEx.printStackTrace();
            throw new EJBException(rEx);
        }

        return iSPtxmonitorRuleSB;
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
