package com.ims.oversea.framework.transaction.txmonitor.business.rule.helper;

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
import com.ims.oversea.framework.transaction.txmonitor.business.thing.*;

public class EJBUtilRule {

  public static SPtxmonitorSB getISPtxmonitorSB()
  {
      SPtxmonitorSB iSPtxmonitorSB = null;
      SPtxmonitorSBHome sptxmonitorRuleSBHome = null;
      try
      {
          sptxmonitorRuleSBHome = (SPtxmonitorSBHome)
                  JNDIService.getInstance().lookup(
                  JNDINamesRule.SPTXMONITOR_HOME);
          iSPtxmonitorSB = (SPtxmonitorSB)sptxmonitorRuleSBHome.create();
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

      return iSPtxmonitorSB;
  }
}