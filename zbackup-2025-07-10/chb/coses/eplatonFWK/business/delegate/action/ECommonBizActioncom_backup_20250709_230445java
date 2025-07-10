package com.chb.coses.eplatonFWK.business.delegate.action;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */


// java
import java.rmi.RemoteException;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import java.lang.reflect.*;

// JNDI
import com.chb.coses.foundation.jndi.JNDIService;

// Framework
import com.chb.coses.eplatonFWK.business.delegate.action.EPlatonBizAction;
import com.chb.coses.framework.business.delegate.action.*;
import com.chb.coses.framework.transfer.IEvent;
import com.chb.coses.framework.exception.BizActionException;
import com.chb.coses.framework.transfer.*;

import com.chb.coses.eplatonFWK.business.facade.ecommon.*;

public class ECommonBizAction extends EPlatonBizAction
{

  public ECommonBizAction()
  {
  }

  public IEvent act(IEvent event)
  {
    try
    {
      ECommonManagementSBHome cashCardHome =
          (ECommonManagementSBHome)JNDIService.getInstance()
          .lookup(ECommonManagementSBHome.class);
      IECommonManagementSB iECommonManagementSB = cashCardHome.create();
      this.doAct(iECommonManagementSB, event);
    }
    catch(Throwable _e)
    {

    }
    return null;
  }
}