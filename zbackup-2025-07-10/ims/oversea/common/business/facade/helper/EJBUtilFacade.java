package com.ims.oversea.common.business.facade.helper;

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
import com.ims.oversea.eplatonframework.business.rule.spcommoRule.*;
import com.ims.oversea.eplatonframework.business.rule.spcommoRule.*;

public class EJBUtilFacade
{
  public static SPcommoRuleSB getISPcommoRuleSB()
  {
    SPcommoRuleSB iSPcommoRuleSB = null;
    SPcommoRuleSBHome spcommoRuleSBHome = null;
    try
    {
      spcommoRuleSBHome = (SPcommoRuleSBHome)
                              JNDIService.getInstance().lookup(
                              JNDINamesFacade.SPCOMMO_RULE_HOME);
      iSPcommoRuleSB = (SPcommoRuleSB)spcommoRuleSBHome.create();
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

    return iSPcommoRuleSB;
  }


}