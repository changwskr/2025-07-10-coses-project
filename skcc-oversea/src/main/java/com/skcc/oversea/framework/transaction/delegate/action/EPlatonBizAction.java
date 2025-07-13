package com.skcc.oversea.framework.transaction.delegate.action;

import java.lang.reflect.*;
import java.util.*;
import java.rmi.*;

import com.skcc.oversea.framework.transfer.*;
import com.skcc.oversea.framework.exception.*;

import com.skcc.oversea.eplatonframework.transfer.*;
import com.skcc.oversea.foundation.utility.*;
import com.skcc.oversea.foundation.logej.*;
import com.skcc.oversea.framework.transaction.tpmutil.TPMSVCAPI;
import com.skcc.oversea.framework.transaction.constant.TCFConstants;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 * 이 클래스는 System component를 찾아 알맞은 operation을 호출하는 역할을 하는
 * 모든 BizAction class의 최상위 class이다.
 *
 * 각 클라이언트에서 호출할 action 명을 정한후 이 action명의 클래스를 통해서
 * facade단의 세션빈의 홈객체가 생성되고 이 홈객체를 통해서 리모트 객체를 생성한후
 * 이 리모트객체에서 정한 메소드를 invoke을 함으로서 실질적으로 각 시스템으로의
 * 서비스가 실시되는 부분이다.
 *
 * 실제 각 업무시스템의 facade단의 세션빈에 대한 메소드는 하나로 통일되어 있다.
 * 그래서 클라이언트에서는 하나의 공통적인 메소드만을 호출하고, 실제 이 메소드내에서
 * (execute) 각 업무시스템으로의 클래스가 호출된다.
 * =============================================================================
 * 변경내역 정보:
 * =============================================================================
 *  2004년 03월 16일 1차버전 release
 *
 *
 * =============================================================================
 *                                                        @author : 장우승(WooSungJang)
 *                                                        @company: IMS SYSTEM
 *                                                        @email  : changwskr@yahoo.co.kr
 *                                                        @version 1.0
 *  =============================================================================
 */

public abstract class EPlatonBizAction  implements IEPlatonBizAction
{
  IEvent cresevent ;


  /**
   * 이 method는 호출해야 하는 EJB의 remote interface와
   * 정보를 전달하는 IEvent 객체를 parameter로 받아
   * reflection을 사용하여 system component를 호출한다.<br>
   * 이 때, system component의 operation에 IEvent 객체의 request attribute를
   * parameter로 전달한다. <br>
   * 또, system component의 return를 IEvent 객체의 response attribute에 저장하여
   * BizDelegateEJB에 반환한다.<br>
   *
   * @param target 사용해야 하는 System component의 remote interface
   * @param event business layer와 application layer 사이의 정보를 전달하는 IEvent type의 객체
   * @throws BizActionException
   */

  protected IEvent doAct(Object target, IEvent event)
  {
    this.cresevent = event;
    IEvent resevent = null;
    TPSVCINFODTO tpsvcinfo = ((EPlatonEvent)event).getTPSVCINFODTO();
    String methodName = TCFConstants.BIZDELEGATE_CALL_METHOD_NAME ;
    String parameterTypeName = TCFConstants.BIZDELEGATE_CALL_PARAMETER_TYPE_NAME;
    long startcurrentTimeMillis = System.currentTimeMillis();
    long endcurrentTimeMillis = System.currentTimeMillis() - startcurrentTimeMillis;

    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"=================================================EPlatonBizAction.doAct() start");

    try
    {
      if( !tpsvcinfo.getTpfq().equals("200") ){

        /*
         * EPlatonBizDelegate을 통하지 않고 서버끼리의 호출시 사용한다
         * target : CashCardManagementSB의 execute()메소드를 호출한다.
         *          여기서 CashCardManagementSB에대한 remote object는 CashCardBizAction에서
         *          생성된다.
         *          그리고 여기서는 (EPlatonEvent)meth.invoke(target,new Object[]{(EPlatonEvent)event});
         *          호출을 통해서 객체의 메소드를 호출한다. reflection 기능
         *
         * 서버끼리의 연동이므로 최초 데이타를 관리하기 위해 TPSrecv 모듈을 호출해
         * 기본에 데이타에 대한 검증작업을 거친다.
         */
        event = TPMSVCAPI.getInstance().TPSrecv((EPlatonEvent)event);
        TPMSVCAPI.getInstance().TPSDBrecv((EPlatonEvent)event);


        /*
         * 실제 각 업무시스템 즉 facade단의 세션빈에 대한 메소드를 호출한다.
         */
        Method meth = (target.getClass()).getMethod(methodName,new Class[]{EPlatonEvent.class});
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"CALL METHOD INFO-[" + meth.toString() + "]" );
        if ( !isErr((EPlatonEvent)event) ){
          /*
           * 실제 메소드가 호출된다
           */
          event = (EPlatonEvent)meth.invoke(target,new Object[]{(EPlatonEvent)event});
        }
      }
      else{
        /*
         * EPlatonBizDelegate을 통해서 호출이 된 경우
         * 실제 각 업무시스템 즉 facade단의 세션빈에 대한 메소드를 호출한다.
         */
        Method meth = (target.getClass()).getMethod(methodName,new Class[]{EPlatonEvent.class});
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"CALL METHOD INFO-[" + meth.toString() + "]" );
        event = (EPlatonEvent)meth.invoke(target,new Object[]{(EPlatonEvent)event});
      }

      /*
       * 서버끼리의 호출의 경우에는 마지막 리턴 데이타를 남긴다.
       */
      if( !tpsvcinfo.getTpfq().equals("200") ){
        endcurrentTimeMillis = System.currentTimeMillis() - startcurrentTimeMillis;
        TPMSVCAPI.getInstance().TPSDBsend((EPlatonEvent)event,endcurrentTimeMillis);
        TPMSVCAPI.getInstance().TPSsend((EPlatonEvent)event);
      }

    }
    catch(Exception _e)
    {
      _e.printStackTrace();
      resevent = cresevent = event;
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"EPlatonBizAction error:[EFWK0036](MethodName=" + methodName + ") 호출 에러");
      SET_SPerror("EFWK0036",this.getClass().getName()+ ".doAct():"+_e.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,_e);
    }
    finally{
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"EPlatonBizAction.doAct() success()");
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"=================================================EPlatonBizAction.doAct() end");
    }

    return event;
  }


  /**
   * 에러코드에 대한 에러메시지 셋팅한다
   * @param errorcode
   * @param message
   */
  private void SET_SPerror(String errorcode,String message)
  {
    EPlatonCommonDTO commonDTO = ((EPlatonEvent)cresevent).getCommon();
    TPSVCINFODTO tpsvcinfo = ((EPlatonEvent)cresevent).getTPSVCINFODTO();

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

  /**
   * 에러발생유무 조사
   * @param eplevent
   * @return
   */
  private boolean isErr(EPlatonEvent eplevent)
  {
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


  /**
   * 이 method는 BizAction class의 act(IEvent event) method에서 발생하는
   * 모든 예외를 BizActionException으로 wrapping해서 던진다.<br>
   *
   * @param _e BizActionException으로 wrapping해서 던져야 하는 예외.
   * @throws BizActionException
   */
  protected void throwBizActionException(Throwable _e) throws BizActionException
  {
    if (_e instanceof BizActionException)
    {
      throw (BizActionException)_e;
    }

    if (_e instanceof InvocationTargetException)
    {
      throw new BizActionException(this.developerMessage(((InvocationTargetException)_e).getTargetException()),
                                   ((InvocationTargetException)_e).getTargetException());
    }

    throw new BizActionException(this.developerMessage(_e), _e);
  }

  /**
   * 이 method는 BizActionException의 developer message를 만든다.<br>
   * @param _e 발생한 예외
   * @return String developer message. "[BizAction class 이름] 발생한 Exception의 class 이름"의 형식이다.
   */
  private String developerMessage(Throwable _e)
  {
    return "[" + this.getClass().getName() + "] " + _e.getClass().getName();
  }

  /**
   * @see com.chb.coses.framework.business.delegate.action.IBizAction#preAct(IEvent)
   */
  public void preAct(IEvent event)
  {
  }

  /**
   * @see com.chb.coses.framework.business.delegate.action.IBizAction#postAct(IEvent)
   */
  public void postAct(IEvent event)
  {
  }
}


