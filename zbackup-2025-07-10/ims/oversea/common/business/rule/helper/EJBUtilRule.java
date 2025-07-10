package com.ims.oversea.common.business.rule.helper;

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
import com.ims.oversea.eplatonframework.business.thing.spcommo.*;

public class EJBUtilRule
{
  public static SPcommoSB getISPcommoSB()
  {
    SPcommoSB iSPcommoSB = null;
    SPcommoSBHome spcommoRuleSBHome = null;
    try
    {
      spcommoRuleSBHome = (SPcommoSBHome)
                              JNDIService.getInstance().lookup(
                              JNDINamesRule.SPCOMMO_HOME);
      iSPcommoSB = (SPcommoSB)spcommoRuleSBHome.create();
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

    return iSPcommoSB;
  }


}
