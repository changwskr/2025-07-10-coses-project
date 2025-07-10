package com.chb.coses.eplatonFWK.business;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.rmi.*;
import java.sql.*;
import java.net.*;
import java.text.*;
import java.util.*;

import javax.ejb.*;
import javax.naming.*;

import com.chb.coses.foundation.log.*;
import com.chb.coses.foundation.base.*;
import com.chb.coses.foundation.config.*;
import com.chb.coses.foundation.jndi.*;
import com.chb.coses.foundation.utility.*;

import com.chb.coses.framework.business.*;
import com.chb.coses.framework.business.delegate.IBizDelegate;
import com.chb.coses.framework.transfer.*;
import com.chb.coses.framework.exception.*;
import com.chb.coses.framework.constants.*;

import com.chb.coses.common.business.constants.*;
import com.chb.coses.common.business.facade.*;
import com.chb.coses.common.transfer.*;
import com.chb.coses.user.business.facade.*;
import com.chb.coses.user.transfer.UserCDTO;
import com.chb.coses.reference.business.facade.*;

import com.ims.eplaton.eplatonFWK.business.delegate.action.EPlatonBizAction;
import com.ims.eplaton.eplatonFWK.business.delegate.action.*;
import com.ims.eplaton.eplatonFWK.transfer.EPlatonEvent;
import com.ims.eplaton.eplatonFWK.transfer.*;
import com.ims.eplaton.foundation.helper.logej.LOGEJ;
import com.ims.eplaton.foundation.helper.TPMSVCAPI;

public class EPlatonBizDelegateSBBean extends AbstractSessionBean
    implements IBizDelegate
{

  public void ejbCreate() throws CreateException
  {
    String machine_mode = null;
    try {
      machine_mode = Config.getInstance().getElement("machine-service-mode").getTextTrim();
      if( machine_mode.equals("DEV") ){
        // BizAction map generator service start
        //ActionManager.getInstance().getActionMapService().startService();
        System.out.println("-------------------------------["+machine_mode+"]");
      }
      System.out.println("-------------------------------["+machine_mode+"]");
    } catch (Exception e) {
      System.out.println("##############################");
      e.printStackTrace();
      String str = "Action map generation service start failed.";
      Log.DelegateLogger.warn(str, e);
    }

  }

  /**
   * @see com.chb.coses.framework.business.delegate.IBizDelegate#execute(IEvent)
   */
  public IEvent execute(IEvent event) throws BizDelegateException
  {
    IEvent resevent = null;

    try
    {
      /*************************************************************************
       * 클라이언트에서 요청한 시스템으로 라우팅 한다.
       * 대표시스템 CashCard, Deposit ... 등으로
       ************************************************************************/
      resevent = event;
      resevent = event = TPMSVCAPI.getInstance().TPSrecv((EPlatonEvent)event);
      if ( !isErr((EPlatonEvent)event) )
      {
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"actionClassName : " + ((EPlatonEvent)event).getTPSVCINFODTO().getAction_name());
        String actionClassName = ((EPlatonEvent)event).getTPSVCINFODTO().getAction_name();
        //actionClassName="com.chb.coses.eplatonFWK.business.delegate.action.CashCardBizAction"
        actionClassName = ((EPlatonEvent)event).getCommon().getReqName();
        EPlatonBizAction action = (EPlatonBizAction)(Class.forName(actionClassName).newInstance());
        resevent = action.act(event);
      }
    }
    catch (Exception _e)
    {
      ((EPlatonEvent)event).getTPSVCINFODTO().setErrorcode("EDEL001");
      ((EPlatonEvent)event).getTPSVCINFODTO().setError_message("CALL INITIAL ERROR");
      _e.printStackTrace();
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"EPlatonBizDelegateSBBean-execute()에서 에러발생:[EDEL001]");
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,_e);
    }
    finally {
    }

    TPMSVCAPI.getInstance().TPSsend((EPlatonEvent)event);
    return resevent;
  }

  public void ejbRemove()
  {
  }

  private boolean isErr(EPlatonEvent eplevent){
    switch (eplevent.getTPSVCINFODTO().getErrorcode().charAt(0))
    {
      case 'e':
      case 's':
      case 'E':
      case 'S':
        return true;
      case 'I':
        return false;
      case '*':
      default:
      return true;
    }
  }

}
