package com.chb.coses.eplatonFramework.business.delegate.action;

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
import com.chb.coses.eplatonFramework.business.delegate.action.EPlatonBizAction;

import com.chb.coses.framework.business.delegate.action.*;
import com.chb.coses.framework.transfer.IEvent;
import com.chb.coses.framework.exception.BizActionException;
import com.chb.coses.framework.transfer.*;

import com.chb.coses.cashCard.business.facade.*;


public class CashCardBizAction extends EPlatonBizAction
{

  public CashCardBizAction()
  {
  }

  public IEvent act(IEvent event) throws com.chb.coses.framework.exception.BizActionException
  {
    IEvent resevent = null;
    try
    {
      CashCardManagementSBHome cashCardHome =
          (CashCardManagementSBHome)JNDIService.getInstance()
          .lookup(CashCardManagementSBHome.class);
      ICashCardManagementSB iCashCardManagementSB = cashCardHome.create();
      resevent = this.doAct(iCashCardManagementSB, event);
    }
    catch(Throwable _e)
    {
      this.throwBizActionException(_e);
    }
    return resevent;
  }
}