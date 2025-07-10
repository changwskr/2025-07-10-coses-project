package com.ims.eplaton.eplatonFWK.business.delegate.action;

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
import com.ims.eplaton.eplatonFWK.business.delegate.action.EPlatonBizAction;
import com.chb.coses.framework.business.delegate.action.*;
import com.chb.coses.framework.transfer.IEvent;
import com.chb.coses.framework.exception.BizActionException;
import com.chb.coses.framework.transfer.*;

import com.ims.eplaton.eplatonFWK.business.facade.ecommon.*;


/**
 * EPlatonBizAction을 상속받아 생성된 클래스로서
 * 클라이언트단에서 정한 request_name에 의해서 서버에서 각 특정시스템으로의 라우팅을 받아
 * 들이는 부분이다.
 * 실제 EPlatonBizDelegate에서 호출하며, 호출되는 메소드는 act()메소드를 호출한다
 * [수행]
 *    각 업무시스템의 facade단의 세션빈의 객체를 생성한다. 즉 home 객체와 remote 객체를
 *    생성해고 상속한 doAct()메소드로 remote객체와 클라이언트에서 올라온 event 객체를
 *    전송한다.
 *    실제 facade단의 메소드가 호출되는 곳은 EPlatonBizAction의 doAct()메소드에서
 *    전달되어온 facade단의 리모트객체를 가지고 리모트객체속에 포함된 클래스를 dynamic
 *    invoke을 함으로서 실질적인 처리가 이루어지도록 한다.
 *
 * BizAction 클래스를 둠으로서 delegate와 각 시스템간의 결합도를 낮추는 방안을 마련된다
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

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