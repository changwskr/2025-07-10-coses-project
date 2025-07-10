package com.ims.oversea.framework.transaction.bcf;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.rmi.*;
import java.sql.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.io.*;
import javax.ejb.*;
import javax.naming.*;
import java.lang.reflect.*;

import com.chb.coses.framework.exception.BizActionException;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.exception.CosesExceptionDetail;
import com.chb.coses.foundation.utility.MessageUtils;
import com.chb.coses.foundation.jndi.JNDIService;
import com.ims.oversea.framework.transaction.helper.Convert;
import com.chb.coses.common.business.constants.*;
import com.chb.coses.common.business.facade.*;
import com.chb.coses.common.transfer.*;
import com.chb.coses.user.business.facade.*;
import com.chb.coses.user.transfer.UserCDTO;
import com.chb.coses.reference.business.facade.*;
import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.framework.transaction.tpmutil.*;
import com.ims.oversea.foundation.utility.CommonUtil;
import com.ims.oversea.foundation.logej.LOGEJ;
import com.ims.oversea.framework.transaction.constant.TCFConstants;
import com.ims.oversea.framework.transaction.constant.TCFConstantErrcode;
import com.ims.oversea.framework.transaction.bean.AbstractBIZBCF;
import com.ims.oversea.framework.transaction.bean.TransactionInfoBIZTCF;
import com.chb.coses.framework.transfer.IDTO;
import com.chb.coses.framework.transfer.IEvent;
import com.chb.coses.framework.transfer.DTO;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 *
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

public class BCF  {

  private String call_operation_class;
  private String call_operation_method;
  private EPlatonEvent oevent;
  private EPlatonEvent rtn_obj;
  private IDTO  transferDDTO;
  private IDTO  responseDDTO;
  private TPSVCINFODTO otpsvcinfoDTO;
  private EPlatonCommonDTO ocommonDTO;

  /**
   * 실행함수
   * @param pevent
   * @return
   */
  public Object execute(String call_operation_class,
                        String call_operation_method,
                        IDTO transferDDTO,
                        EPlatonEvent event)
  {
    try
    {
      this.call_operation_class = call_operation_class;
      this.call_operation_method = call_operation_method;
      this.oevent = event;
      this.transferDDTO = transferDDTO;
      this.otpsvcinfoDTO = event.getTPSVCINFODTO();
      this.ocommonDTO = event.getCommon();

      LOGEJ.getInstance().printf(1,event,"Rule/Thing call operation class  : " + call_operation_class);
      LOGEJ.getInstance().printf(1,event,"Rule/Thing call operation method : " + call_operation_method);

      if( BCF_SPprocessing() ){
        LOGEJ.getInstance().printf(1,event,"success to call BCF_SPprocessing()");
      }
      else {
        LOGEJ.getInstance().printf(TCFConstants.TCF_LOG_LEVEL,event,"fail to call BCF_SPprocessing()");
      }
    }
    catch(Exception ex){
      ex.printStackTrace();
      LOGEJ.getInstance().eprintf(TCFConstants.TCF_LOG_LEVEL,(EPlatonEvent)event,ex);
      BCF_SPerror(TCFConstantErrcode.EBTF001,TCFConstantErrcode.EBTF001_MSG);
    }

    return responseDDTO ;

  }

  public boolean BCF_SPprocessing()
  {
    String methodName = "";

    try{

      String operationName = this.call_operation_class;

      /*
       * 클라이언트에서 세팅한 클래스를 인스턴스화 시킨다
       * 클라이언트 셋팅정보 :
       */
      Object ptarget = Class.forName( operationName).newInstance();

      /*
       * 업무서버의 호출정보를 찾아서 셋팅해준다.
       * 아래의 정보는 업무단에게 EPlatonEvent 객체를 전달하지 않고 CDTO 객체만 전달할시
       * 현재 트랜잭션 정보를 주기 위해서 사용한다.
       */
      AbstractBIZBCF abiztcf = (AbstractBIZBCF) ptarget;
      abiztcf.setEvent(oevent);

      /*
       * 메소드명을 정한다.
       */
      methodName = call_operation_method;


      if( oevent.getMessageParameter().getRule_operation_class_name().equals(
          TCFConstants.RULE_THING_INITIAL_VAL) )
      {
        oevent.getMessageParameter().setRule_intime(CommonUtil.GetSysTime());
        oevent.getMessageParameter().setRule_operation_class_name(operationName);
        oevent.getMessageParameter().setRule_operation_method_name(methodName);
        oevent.getMessageParameter().setRule_outtime(CommonUtil.GetSysTime());
      }
      else
      {
        oevent.getMessageParameter().setThing_intime(CommonUtil.GetSysTime());
        oevent.getMessageParameter().setThing_operation_class_name(operationName);
        oevent.getMessageParameter().setThing_operation_method_name(methodName);
        oevent.getMessageParameter().setThing_outtime(CommonUtil.GetSysTime());
      }

      /*
       * 메소드에 대한 정보를 가지고와서 실행시킨다.
       */
      Method meth = (ptarget.getClass()).getMethod(methodName,new Class[]{IDTO.class});
      LOGEJ.getInstance().printf(TCFConstants.BTF_LOG_LEVEL,(EPlatonEvent)oevent,"CALL METHOD INFO-[" + meth.toString() + "]" );
      responseDDTO = (IDTO)meth.invoke(ptarget,new Object[]{(IDTO)transferDDTO });

    }
    catch(Throwable ex){
      ex.printStackTrace();

      /**
       * 업무예외에 의한 기본에러는 BCF003으로 되며
       * 만약 업무단에서 발생시킨 CosesAppException의 구체적인 정보는 BCF_SPexception()에서 처리한다.
       * 그리고 구체적인 에러는 EBIZ0000형식으로 나올것이다.
       */
      BCF_SPerror(TCFConstantErrcode.EBTF003,TCFConstantErrcode.EBTF003_MSG);
      BCF_SPexception(ex);
      LOGEJ.getInstance().printf(TCFConstants.BTF_LOG_LEVEL,(EPlatonEvent)oevent,"####################################[BCF_SPprocessing() END]");

      oevent.getMessageParameter().setRule_operation_method_name(methodName);
      oevent.getMessageParameter().setRule_outtime(CommonUtil.GetSysTime());

      return false;
    }
    LOGEJ.getInstance().printf(TCFConstants.BTF_LOG_LEVEL,(EPlatonEvent)oevent,"####################################[BCF_SPprocessing() END]");
    oevent.getMessageParameter().setRule_operation_method_name(methodName);
    oevent.getMessageParameter().setRule_outtime(CommonUtil.GetSysTime());

    return true;

  }

  private boolean isErr(){
    switch (oevent.getTPSVCINFODTO().getErrorcode().charAt(0) )
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
        oevent.getTPSVCINFODTO().setErrorcode(TCFConstantErrcode.EBTF005);
        oevent.getTPSVCINFODTO().setError_message(TCFConstantErrcode.EBTF005_MSG);
        return true;
    }
  }

  private void BCF_SPerror(String errorcode,String message)
  {
    switch( otpsvcinfoDTO.getErrorcode().charAt(0) )
    {
      case 'I' :
        otpsvcinfoDTO.setErrorcode(errorcode);
        otpsvcinfoDTO.setError_message(message);
        return;
      case 'E' :
        errorcode = errorcode+"|"+otpsvcinfoDTO.getErrorcode();
        otpsvcinfoDTO.setErrorcode(errorcode);
        otpsvcinfoDTO.setError_message(message);
        return;
    }
  }


  private void BCF_SPexception(Throwable _e)
  {

    LOGEJ.getInstance().printf(TCFConstants.BTF_LOG_LEVEL,oevent,
                               "===============================================[BCF_SPexception() Start");
    Throwable cause = _e;
    int count = 0;
    while (cause != null) {
      if( count > 100 ) break;
      if (cause instanceof InvocationTargetException) {
        //System.out.println(++count + "1:" + cause.getClass().getName());
        cause = ((InvocationTargetException) cause).getTargetException();
      }
      else if (cause instanceof CosesAppException) {
        //System.out.println(++count + "2:" + cause.getClass().getName());
        cause = ((CosesAppException) cause);
        break;
      }
      else {
        //System.out.println(++count + "3:" + cause.getClass().getName());
        cause = null;
      }
    }


    //LOGEJ.getInstance().printf(TCFConstants.BTF_LOG_LEVEL,oevent,"Throwable Type : " + _e.getClass() );
    String transactionNo = "No Transaction Number";
    String header = "|" + oevent.getTPSVCINFODTO().getHostseq() + "| : " ;

    if (cause instanceof CosesAppException  )
    {
      //System.out.println("coses app exception ======================:::" + cause.getClass().getName() );
      cause.printStackTrace();

      // Throwable객체를 CosesAppException객테타입으로 캐스팅한다.
      CosesAppException _eee = (CosesAppException)cause;

      LOGEJ.getInstance().eprintf(TCFConstants.BTF_LOG_LEVEL,oevent,_eee);

      // Output log에 찍을 내용
      StringBuffer output = new StringBuffer(header);

      /**
       * 업무단에서 발생시킨 CosesAppException들은 Thorwable 객체로 해서 전달되어져
       * 온다.
       * _eee 인스턴스는 업무단에서 발생한 CosesExceptionDetail의 정보가 담겨져 있다.
       *
       */
      List details = _eee.getDetails();
      CosesExceptionDetail detail = null;
      String exceptionCode = null;
      String exceptionMsg = null;

      for (Iterator i=details.iterator(); i.hasNext() ; )
      {

        detail = (CosesExceptionDetail)i.next();
        exceptionCode = detail.getExceptionCode();
        exceptionMsg = exceptionMessage( ocommonDTO.getNation(), exceptionCode );

        StringBuffer errorMsg = new StringBuffer();
        errorMsg.append(exceptionMsg);
        if (detail.getMessages() != null)
        {
          errorMsg.append(",");
          errorMsg.append("Input Data     : ");
          errorMsg.append(MessageUtils.getMessage(detail.getMessages() ));
        }

        if (detail.getArguments() != null && detail.getArguments().length >= 2)
        {
          errorMsg.append(",");
          errorMsg.append("Error Location : ");
          StringBuffer sb = new StringBuffer();
          sb.append("System={");
          sb.append(detail.getArguments().length - 2);
          sb.append("}");
          sb.append(", Method={");
          sb.append(detail.getArguments().length - 1);
          sb.append("}");
          sb.append(TCFConstants.LINE_SEPARATOR);
          errorMsg.append(MessageUtils.getMessage(sb.toString(), detail.getArguments() ));
        }
        exceptionMsg = errorMsg.toString();

        // Output log에 사용할 문자열을 만든다.
        output.append(exceptionOutput(exceptionCode, exceptionMsg) );

      }
    }
    // 업무 EJB에서 system exception이 발생하면
    else if (cause instanceof RemoteException)
    {
      cause.printStackTrace();
      LOGEJ.getInstance().printf(TCFConstants.BTF_LOG_LEVEL,oevent,"RemoteException 발생");
      Exception ex = (Exception)cause;
      LOGEJ.getInstance().eprintf(TCFConstants.BTF_LOG_LEVEL,oevent,ex);

      RemoteException _eee = (RemoteException)_e;
      String exceptionCode = CommonErrorMessageConstants.ERR_9000_SYSTEM_ERROR;
      String exceptionMsg = exceptionMessage( ocommonDTO.getNation(), exceptionCode);
      String devMsg = "System exception occurred at BizEJB.";

      /**
       * remote exception 발생
       */
      BCF_SPerror("EBTF"+exceptionCode,exceptionMsg);
      ////////////////////////////////////////////////////////////////////////////////////////// END
      //manageFatalException(header, devMsg, _eee.detail.toString(),exceptionCode, exceptionMsg);
    }
    // BizAction class에서 발생한 exception 중 CosesAppException이 아닌 경우
    else
    {
      cause.printStackTrace();
      Exception ex = (Exception)cause;

      LOGEJ.getInstance().eprintf(TCFConstants.BTF_LOG_LEVEL,oevent,ex);
      LOGEJ.getInstance().printf(TCFConstants.BTF_LOG_LEVEL,oevent,"Other Exception 발생["+_e.toString() );

      String exceptionCode = CommonErrorMessageConstants.ERR_9000_SYSTEM_ERROR;
      String exceptionMsg = exceptionMessage( ocommonDTO.getNation(), exceptionCode );
      String devMsg = "Exception occurred at BizAction.";

      /**
       * other exception 발생
       */
      BCF_SPerror("EBTF"+exceptionCode,exceptionMsg);

      //manageFatalExceptionWithStackTrace(header, devMsg, _e, exceptionCode, exceptionMsg);

    }
    LOGEJ.getInstance().printf(TCFConstants.BTF_LOG_LEVEL,oevent,
                               "===============================================[BCF_SPexception() End");
  }

  private String exceptionMessage(String nationCode, String exceptionCode)
  {
      String exceptionMsg = "No message.";

      try
      {
          CommonManagementSBHome commonHome = (CommonManagementSBHome)JNDIService.getInstance().lookup(CommonManagementSBHome.class);
          ICommonManagementSB commonMgt = commonHome.create();
          exceptionMsg = commonMgt.getErrorMessage(nationCode, exceptionCode);
      }
      // Exception message를 가져오다 문제가 생기면
      // Log만 남기고 무시한다.
      catch (Throwable _e)
      {
          //Log.DelegateLogger.debug(header, _e);
      }

      return exceptionMsg;
  }


  /**
   * 업무단에서 발생한 에러를 에러코드와 메시지를 셋팅한다.
   * 형식 : EBIZ + 에러코드 + 메시지
   *
   * Method exceptionOutput.
   * @param exceptionCode
   * @param exceptionMessage
   * @return String
   */
  private String exceptionOutput(String exceptionCode,String exceptionMessage)
  {
      BCF_SPerror("EBIZ"+exceptionCode,exceptionMessage);
      LOGEJ.getInstance().printf(5,oevent,"EBIZ"+exceptionCode + "-" + exceptionMessage);

      return "[Excpetion Code] : " + exceptionCode +
          " / [Excpetion Message] : " + exceptionMessage + "^";
  }

  /**
   * Method manageFatalException.
   * @param developerMessage
   * @param exceptionDetail
   * @param exceptionCode
   * @param exceptionMessage
   * @throws BizDelegateException
   */
  private void manageFatalException(String header, String developerMessage,
                                    String exceptionDetail, String exceptionCode,
                                    String exceptionMessage)
  {
    LOGEJ.getInstance().printf(TCFConstants.BTF_LOG_LEVEL,oevent,header + exceptionDetail);
    LOGEJ.getInstance().printf(TCFConstants.BTF_LOG_LEVEL,oevent,header +
                               exceptionOutput(exceptionCode, exceptionMessage) );
  }

  /**
   * Method manageFatalExceptionWithStackTrace.
   * @param developerMessage
   * @param t
   * @param exceptionCode
   * @param exceptionMessage
   * @throws BizDelegateException
   */
  private void manageFatalExceptionWithStackTrace(String header, String developerMessage,
      Throwable t, String exceptionCode, String exceptionMessage)
  {
      LOGEJ.getInstance().printf(TCFConstants.BTF_LOG_LEVEL,oevent,header +
                                 exceptionOutput(exceptionCode, exceptionMessage) );
  }

}
