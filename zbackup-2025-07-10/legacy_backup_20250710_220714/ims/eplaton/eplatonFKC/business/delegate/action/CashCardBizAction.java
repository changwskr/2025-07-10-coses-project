package com.ims.eplaton.eplatonFKC.business.delegate.action;

import java.rmi.RemoteException;
import javax.naming.NamingException;
import javax.ejb.CreateException;
import java.lang.reflect.*;

import com.chb.coses.foundation.jndi.JNDIService;
import com.chb.coses.framework.business.delegate.action.*;
import com.chb.coses.framework.transfer.IEvent;
import com.chb.coses.framework.exception.BizActionException;
import com.chb.coses.framework.transfer.*;

import com.ims.eplaton.eplatonFKC.business.facade.cashCard.*;
import com.ims.eplaton.eplatonFWK.transfer.*;
import com.ims.eplaton.foundation.helper.*;
import com.ims.eplaton.foundation.helper.logej.*;
import com.ims.eplaton.eplatonFKC.business.delegate.action.EPlatonBizAction;

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
public class CashCardBizAction extends EPlatonBizAction
{
  private EPlatonCommonDTO commonDTO;
  private TPSVCINFODTO tpsvcinfo;
  private IEvent resevent;

  /**
   * 생성자함수
   */
  public CashCardBizAction()
  {
  }

  /**
   * 각 업무시스템의 facade단의 세션빈의 홈객체와 리모트객체를 생성해
   * EPlatonBizAction 클래스의 doAct()메소드를 호출하는 역할 을 한다
   *
   * @param event
   * @return
   */

  public IEvent act(IEvent event)
  {
    try
    {
      resevent = event;
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"=================================================CashCardBizAction.act() start");

      /*
       * 업무시스템의 facade단을 정의한다
       * 홈객체를 생성한다
       */
      CashCardManagementSBHome cashCardHome =
          (CashCardManagementSBHome)JNDIService.getInstance()
                                         .lookup(CashCardManagementSBHome.class);
      /*
       * 리모트객체를 생성한다
       */
      ICashCardManagementSB iCashCardManagementSB = cashCardHome.create();

      /*
       * EPlatonBizAction 클래스의 doAct()메소드를 호출하는 역할 을 한다
       */
      resevent = this.doAct(iCashCardManagementSB, event);

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"CashCardBizAction success");
    }
    catch(Exception _e)
    {
      SET_SPerror("EFWK0035",this.getClass().getName()+ ".act():"+_e.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,_e);
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"CashCardBizAction error:[EFWK0035]"+_e.toString() );
    }
    finally{
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"=================================================CashCardBizAction.act() end");
    }

    return resevent;
  }

  /**
   * 에러코드와 에러메시지를 셋팅한다.
   *
   * @param errorcode
   * @param message
   */
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