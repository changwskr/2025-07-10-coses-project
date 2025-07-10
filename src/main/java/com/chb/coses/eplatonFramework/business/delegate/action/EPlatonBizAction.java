package com.chb.coses.eplatonFramework.business.delegate.action;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.lang.reflect.*;
import java.util.*;
import java.rmi.*;
import com.chb.coses.framework.business.delegate.action.*;
import com.chb.coses.framework.transfer.*;
import com.chb.coses.framework.exception.*;
import com.chb.coses.foundation.config.*;
import com.chb.coses.foundation.base.*;
import com.chb.coses.foundation.log.*;
import com.chb.coses.cosesFramework.transfer.*;
import com.chb.coses.framework.constants.*;

import com.chb.coses.eplatonFramework.transfer.*;
import com.chb.coses.eplatonFramework.business.helper.TPMSVCAPI;
import com.chb.coses.eplatonFramework.business.helper.CommonUtil;

/**
 * @version 1.30
 *
 * 이 클래스는 System component를 찾아 알맞은 operation을 호출하는 역할을 하는 모든 BizAction class의 최상위 class이다.
 */

public abstract class EPlatonBizAction  implements IEPlatonBizAction
{
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

  protected IEvent doAct(Object target, IEvent event) throws BizActionException
  {
    TPSVCINFODTO tpsvcinfo = ((EPlatonEvent)event).getTPSVCINFODTO();
    String methodName = "execute";
    //String parameterTypeName = tpsvcinfo.getCdto_name();
    String parameterTypeName = "com.chb.coses.eplatonFramework.transfer.EPlatonEvent" ;
    IEvent resevent = null;

    Log.DelegateLogger.debug(Constants.LINE_SEPARATOR + "|" + ((EPlatonEvent)event).getCommon().getTransactionNo() + "| : "
                             + Constants.LINE_SEPARATOR + "(Action=" + "TCF"
                             + ")(ActionClassName=" + this.getClass().getName()
                             + ")(MethodName=" + methodName
                             + ")(ParameterTypeName=" + parameterTypeName);

    try
    {
      Object[] paramArray = {event.getRequest(), ((EPlatonEvent)event)};
      Class[] typeArray = {Class.forName(parameterTypeName), ((EPlatonEvent)event).getClass()};

      Log.DelegateLogger.debug(Constants.LINE_SEPARATOR + "|" + ((EPlatonEvent)event).getCommon().getTransactionNo() + "| : "
                               + Constants.LINE_SEPARATOR + "(parameterArray=" + Arrays.asList(paramArray)
                               + ")(typeArray=" + Arrays.asList(typeArray));

      Method method = target.getClass().getDeclaredMethod(methodName, typeArray);

      Log.DelegateLogger.debug(Constants.LINE_SEPARATOR + "|" + method.toString()
                               + "+++ Method Name ++++");

      resevent =  (IEvent)method.invoke(target, paramArray);

      if(resevent != null)
      {
        Log.DelegateLogger.debug("Return DTO : " + resevent.toString());
      }

    }
    catch(Throwable _e)
    {
      this.throwBizActionException(_e);
    }
    return resevent;
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