package com.chb.coses.eplatonFramework.business;

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
import com.chb.coses.cosesFramework.action.ActionManager;

import com.chb.coses.eplatonFramework.business.delegate.action.EPlatonBizAction;
import com.chb.coses.eplatonFramework.transfer.EPlatonEvent;

/**
 * @author jongidal
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class EPlatonBizDelegateSBBean extends AbstractSessionBean implements IBizDelegate
{

  public void ejbCreate() throws CreateException
  {
  }

  /**
   * @see com.chb.coses.framework.business.delegate.IBizDelegate#execute(IEvent)
   */
  public IEvent execute(IEvent event) throws BizDelegateException
  {
    IEvent resevent = null;

    try {
      /*************************************************************************
       * 클라이언트에서 요청한 시스템으로 라우팅한다.
       * 대표시스템 CashCard, Deposit ... 등으로
       ************************************************************************/
      String actionClassName = ((EPlatonEvent)event).getTPSVCINFODTO().getAction_name();
      //actionClassName="com.chb.coses.eplatonFramework.business.delegate.action.CashCardBizAction"
      EPlatonBizAction action = (EPlatonBizAction)(Class.forName(actionClassName).newInstance());
      resevent = action.act(event);
    }
    catch (Throwable _e)
    {
      _e.printStackTrace();
    }

    return resevent;
  }

  public void ejbRemove()
  {
  }

}
