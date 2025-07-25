package com.chb.coses.eplatonFMK.business.delegate.action;

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
import com.chb.coses.eplatonFMK.business.delegate.action.EPlatonBizAction;
import com.chb.coses.framework.business.delegate.action.*;
import com.chb.coses.framework.transfer.IEvent;
import com.chb.coses.framework.exception.BizActionException;
import com.chb.coses.framework.transfer.*;

import com.chb.coses.cashCard.business.facade.*;
import com.chb.coses.eplatonFMK.transfer.*;

public class CommonBizAction extends EPlatonBizAction
{

  public CommonBizAction()
  {
  }

  public IEvent act(IEvent event)
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
    catch(Exception _e)
    {
      _e.printStackTrace();
      EPlatonEvent eplevent = (EPlatonEvent)event;
      TPSVCINFODTO tpsvcinfo = eplevent.getTPSVCINFODTO();
      tpsvcinfo.setErrorcode("EDEL002");
      tpsvcinfo.setError_message("CALL iCashCardManagementSB lookup ERROR");
      resevent = eplevent;
    }
    finally{
    }

    return resevent;
  }
}