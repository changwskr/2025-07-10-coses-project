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

import com.chb.coses.eplatonFWK.business.facade.cashCard.*;
import com.chb.coses.eplatonFWK.transfer.*;
import com.chb.coses.eplatonFWK.business.helper.logej.LOGEJ;

public class CashCardBizAction extends EPlatonBizAction
{
  private EPlatonCommonDTO commonDTO;
  private TPSVCINFODTO tpsvcinfo;
  private IEvent resevent;

  public CashCardBizAction()
  {
  }

  public IEvent act(IEvent event)
  {
    try
    {
      resevent = event;
      LOGEJ.getInstance().printf((EPlatonEvent)event,"=================================================CashCardBizAction.act() start");
      CashCardManagementSBHome cashCardHome =
          (CashCardManagementSBHome)JNDIService.getInstance()
          .lookup(CashCardManagementSBHome.class);
      ICashCardManagementSB iCashCardManagementSB = cashCardHome.create();
      resevent = this.doAct(iCashCardManagementSB, event);
      LOGEJ.getInstance().printf((EPlatonEvent)event,"CashCardBizAction success");
    }
    catch(Exception _e)
    {
      SET_SPerror("EFWK0035",this.getClass().getName()+ ".act():"+_e.toString());
      LOGEJ.getInstance().eprintf((EPlatonEvent)event,_e);
      LOGEJ.getInstance().printf((EPlatonEvent)event,"CashCardBizAction error:[EFWK0035]"+_e.toString() );
    }
    finally{
      LOGEJ.getInstance().printf((EPlatonEvent)event,"=================================================CashCardBizAction.act() end");
    }

    return resevent;
  }

  private void SET_SPerror(String errorcode,String message)
  {
    commonDTO = ((EPlatonEvent)resevent).getCommon();
    tpsvcinfo = ((EPlatonEvent)resevent).getTPSVCINFODTO();

    switch( tpsvcinfo.getErrorcode().charAt(0) )
    {
      case 'I' :
        tpsvcinfo.setErrorcode(errorcode);
        tpsvcinfo.setError_message(message);
        return;
      case 'E' :
        errorcode = errorcode+"|"+tpsvcinfo.getErrorcode();
        tpsvcinfo.setErrorcode(errorcode);
        tpsvcinfo.setError_message(message);
        return;
    }
  }

}