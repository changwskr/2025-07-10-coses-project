package com.kdb.oversea.framework.transaction.tcf;



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
import com.chb.coses.framework.constants.*;
import com.kdb.oversea.foundation.config.Config;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.exception.CosesExceptionDetail;
import com.chb.coses.foundation.utility.MessageUtils;
import com.chb.coses.foundation.jndi.JNDIService;
import com.kdb.oversea.framework.transaction.helper.Convert;
import com.chb.coses.common.business.constants.*;
import com.chb.coses.common.business.facade.*;
import com.chb.coses.common.transfer.*;
import com.chb.coses.user.business.facade.*;
import com.chb.coses.user.transfer.UserCDTO;
import com.chb.coses.reference.business.facade.*;

import com.kdb.oversea.eplatonframework.transfer.*;

import com.kdb.oversea.framework.transaction.tpmutil.*;
import com.kdb.oversea.foundation.utility.CommonUtil;

import com.kdb.oversea.foundation.logej.LOGEJ;


/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 * 업무시스템으로 라우팅이 이루어진다.
 *
 * 각 업무시스템의 대표 operation class 명에 대한 정보는 TPSVCINFODTO의 operation_name의 클래스명이고
 * 이클래스(tpsvcinfodto.operation_name)에서 호출한 업무서비스 메소드는 tpsvcinfodto.operation_method
 * 이다.
 * 다음은 클라이언트에서 호출할 업무시스템으로의 라우팅정보이다
 *  1. 호출시스템명
 *  tpsvcinfo.setSystem_name("CashCard");
 *  2. business delegate에서 호출할 action 명
 *  tpsvcinfo.setAction_name("com.kdb.oversea.eplatonframework.business.delegate.action.CashCardBizAction");
 *  3. tcf의 btf에서 각 업무시스템으로의 연계를 위한 클래스 셋팅
 *  tpsvcinfo.setOperation_name("com.kdb.oversea.eplatonframework.othersystem.operation.COMMO1000");
 *  4. operation_name 클래스에서 호출할 메소스명을 명시
 *  tpsvcinfo.setOperation_method("act");
 *  5. operatin_name 클래스의 operation_method의 인자로 넘겨줄 cdto명에 대한 정보
 *  tpsvcinfo.setCdto_name("com.chb.coses.CashCard.transfer.CashCardCDTO");
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

public class BTF implements IBTF {
  private static BTF instance;
  private EPlatonEvent oevent;
  private EPlatonCommonDTO ocommonDTO;
  private TPSVCINFODTO otpsvcinfoDTO;

  private EPlatonEvent transfer_event_object;
  private EPlatonEvent response_event_object;
  private EPlatonCommonDTO transfer_commonDTO;
  private TPSVCINFODTO transfer_tpsvcinfoDTO;


  /**
   * BTF에 대한 인스턴스를 생성한다
   * @return
   */
  public static synchronized BTF getInstance() {
    if (instance == null) {
      try{
        instance = new BTF();
        }catch(Exception igex){}
    }
    return instance;
  }


  /**
   * EPlatonEvent을 가져오는 함수
   * @return
   */
  public EPlatonEvent getEPlatonEvent(){
    return oevent;
  }

  /**
   * 실행함수
   * @param pevent
   * @return
   */
  public EPlatonEvent execute(EPlatonEvent pevent){
    try {
      oevent = pevent;

      BTF_SPinit();


      switch( otpsvcinfoDTO.getErrorcode().charAt(0) )
      {
        case 'E':
          break;
        case 'I':
          BTF_SPmiddle();
          break;
      }

      BTF_SPend();

    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EFWK0017",getClass().getName()+ ".execute():"+ex.toString() );
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)oevent,ex);
    }

    return oevent ;
  }


  /**
   * 기본정보를 셋팅하는 메소드
   * @return
   */
  public boolean BTF_SPinit()
  {
    try{
      LOGEJ.getInstance().printf(5,(EPlatonEvent)oevent,"==================[BTF_SPinit START]");

      /*************************************************************************
       * 기본정보를 가져온다.
       ************************************************************************/
      ocommonDTO = (EPlatonCommonDTO)oevent.getCommon();
      otpsvcinfoDTO = oevent.getTPSVCINFODTO();

      /*************************************************************************
       * 업무단으로 넘겨줄 transfer_event_object 객체를 생성한다
       ************************************************************************/
      transfer_event_object = Convert.createEPlatonEvent(oevent);
      transfer_commonDTO = transfer_event_object.getCommon();
      transfer_tpsvcinfoDTO = transfer_event_object.getTPSVCINFODTO();

      /*************************************************************************
       * TPMSVCINFO의 경우에는 이값이 TCPsendrecv 모듈에 까지 전달되어야 되므로 그대로
       * tranfer_event객체에 전달해준다.
       ************************************************************************/
      transfer_tpsvcinfoDTO.setTpmsvcinfolist( oevent.getTPSVCINFODTO().getTpmsvcinfolist() ) ;

    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EFWK0018",getClass().getName()+ ".BTF_SPinit():"+ex.toString() );
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)oevent,ex);
      LOGEJ.getInstance().printf(5,(EPlatonEvent)oevent,"==================[BTF_SPinit END]");
      return false;
    }

    LOGEJ.getInstance().printf(5,(EPlatonEvent)oevent,"==================[BTF_SPinit END]");

    return true;

  }


  /**
   * 업무시스템으로 라우팅이 이루어진다.
   *
   * 각 업무시스템의 대표 operation class 명에 대한 정보는 TPSVCINFODTO의 operation_name의 클래스명이고
   * 이클래스(tpsvcinfodto.operation_name)에서 호출한 업무서비스 메소드는 tpsvcinfodto.operation_method
   * 이다.
   * 다음은 클라이언트에서 호출할 업무시스템으로의 라우팅정보이다
   *  1. 호출시스템명
   *  tpsvcinfo.setSystem_name("CashCard");
   *  2. business delegate에서 호출할 action 명
   *  tpsvcinfo.setAction_name("com.kdb.oversea.eplatonframework.business.delegate.action.CashCardBizAction");
   *  3. tcf의 btf에서 각 업무시스템으로의 연계를 위한 클래스 셋팅
   *  tpsvcinfo.setOperation_name("com.kdb.oversea.eplatonframework.othersystem.operation.COMMO1000");
   *  tpsvcinfo.setOperation_name("com.kdb.oversea.cashCard.business.facade.CashCardManagementSBBean")
   *  4. operation_name 클래스에서 호출할 메소스명을 명시
   *  tpsvcinfo.setOperation_method("act");
   *  tpsvcinfo.setOperation_method("queryForRegisterCashCard");
   *  5. operatin_name 클래스의 operation_method의 인자로 넘겨줄 cdto명에 대한 정보
   *  tpsvcinfo.setCdto_name("com.chb.coses.CashCard.transfer.CashCardCDTO");
   *  tpsvcinfo.setCdto_name("com.kdb.oversea.cashCard.transfer.CashCardCDTO");
   *
   * @return
   */
  public boolean BTF_SPmiddle()
  {
    try{

      LOGEJ.getInstance().printf(5,(EPlatonEvent)oevent,"####################################[BTF_SPmiddle() START]");
      /*
       * 클라이언트 셋팅정보
       * tpsvcinfo.setOperation_name("com.kdb.oversea.eplatonframework.othersystem.operation.COMMO1000")
       * tpsvcinfo.setOperation_name("com.kdb.oversea.cashCard.business.facade.CashCardManagementSBBean")
       */
      String operationName = otpsvcinfoDTO.getOperation_name();

      /*
       * 클라이언트에서 세팅한 클래스를 인스턴스화 시킨다
       * 클라이언트 셋팅정보 :
       * otpsvcinfoDTO.setOperation_name("com.kdb.oversea.eplatonframework.othersystem.operation.COMMO1000")
       * otpsvcinfoDTO.setOperation_name("com.kdb.oversea.cashCard.business.facade.CashCardManagementSBBean")
       */
      Object ptarget = Class.forName( operationName).newInstance();

      /*
       * 클라이언트 셋팅정보
       * otpsvcinfoDTO.setOperation_method("act");
       * otpsvcinfoDTO.setOperation_method("queryForRegisterCashCard");
       */
      String methodName = oevent.getTPSVCINFODTO().getOperation_method();

      /*
       * 메소드에 대한 정보를 가지고와서 실행시킨다.
       */
      Method meth = (ptarget.getClass() ).getMethod(methodName,new Class[]{EPlatonEvent.class});
      LOGEJ.getInstance().printf(5,(EPlatonEvent)oevent,"CALL METHOD INFO-[" + meth.toString() + "]" );
      response_event_object = (EPlatonEvent)meth.invoke(ptarget,new Object[]{(EPlatonEvent)transfer_event_object });

      /*
       * 업무단에서의 정보를 갱신한다.
       */
      oevent.getTPSVCINFODTO().setErrorcode(response_event_object.getTPSVCINFODTO().getErrorcode() );
      oevent.getTPSVCINFODTO().setError_message(response_event_object.getTPSVCINFODTO().getError_message());
      oevent.getTPSVCINFODTO().setTpmsvcinfolist(response_event_object.getTPSVCINFODTO().getTpmsvcinfolist());
      oevent.setResponse(response_event_object.getResponse() );

    }
    catch(Throwable ex){
      ex.printStackTrace();
      BTF_SPerror("EFWK0019",getClass().getName()+ ".BTF_SPmiddle()업무단에러발생:"+ex.toString() );
      BTF_SPexception(ex);
      LOGEJ.getInstance().printf(5,(EPlatonEvent)oevent,"####################################[BTF_SPmiddle() END]");
      return false;
    }
    LOGEJ.getInstance().printf(5,(EPlatonEvent)oevent,"####################################[BTF_SPmiddle() END]");

    return true;

  }

  public boolean BTF_SPend()
  {
    try{
      LOGEJ.getInstance().printf(5,(EPlatonEvent)oevent,"==================[BTF_SPend START]");
    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EFWK0020",getClass().getName()+ ".BTF_SPmiddle():"+ex.toString() );
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)oevent,ex);
      LOGEJ.getInstance().printf(5,(EPlatonEvent)oevent,"==================[BTF_SPend END]");
      return false;
    }
    LOGEJ.getInstance().printf(5,(EPlatonEvent)oevent,"==================[BTF_SPend END]");
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
        oevent.getTPSVCINFODTO().setErrorcode("EBD001");
        oevent.getTPSVCINFODTO().setError_message("error code not set");
        return true;
    }
  }

  private void BTF_SPerror(String errorcode,String message)
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


  private void BTF_SPexception(Throwable _e)
  {
    LOGEJ.getInstance().printf(5,oevent,
                               "===============================================[BTF_SPexception() Start");
    LOGEJ.getInstance().printf(5,oevent,"Throwable Type : " + _e.getClass() );
    String transactionNo = "No Transaction Number";
    String header = Constants.LINE_SEPARATOR + "|" + transactionNo + "| : " + Constants.LINE_SEPARATOR;

    if (_e instanceof CosesAppException)
    {
      _e.printStackTrace();

      // Throwable객체를 CosesAppException객테타입으로 캐스팅한다.
      CosesAppException _eee = (CosesAppException)_e;

      LOGEJ.getInstance().eprintf(5,oevent,_eee);

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
        exceptionMsg = exceptionMessage( ocommonDTO.getNation(), exceptionCode);

        StringBuffer errorMsg = new StringBuffer();
        errorMsg.append(exceptionMsg);
        if (detail.getMessages() != null)
        {
          errorMsg.append(Constants.LINE_SEPARATOR);
          errorMsg.append("Input Data     : ");
          errorMsg.append(MessageUtils.getMessage(detail.getMessages() ));
        }

        if (detail.getArguments() != null && detail.getArguments().length >= 2)
        {
          errorMsg.append(Constants.LINE_SEPARATOR);
          errorMsg.append("Error Location : ");
          StringBuffer sb = new StringBuffer();
          sb.append("System={");
          sb.append(detail.getArguments().length - 2);
          sb.append("}");
          sb.append(", Method={");
          sb.append(detail.getArguments().length - 1);
          sb.append("}");
          sb.append(Constants.LINE_SEPARATOR);
          errorMsg.append(MessageUtils.getMessage(sb.toString(), detail.getArguments() ));
        }
        exceptionMsg = errorMsg.toString();

        // Output log에 사용할 문자열을 만든다.
        output.append(exceptionOutput(exceptionCode, exceptionMsg) );

      }

      LOGEJ.getInstance().printf(5,oevent,
                               "-------------------------------------------------");
      LOGEJ.getInstance().eprintf(5,oevent,_eee);
      LOGEJ.getInstance().printf(5,oevent,header + _eee.toDetail() );
      LOGEJ.getInstance().printf(5,oevent,output.toString() );

    }
    // 업무 EJB에서 system exception이 발생하면
    else if (_e instanceof RemoteException)
    {
      _e.printStackTrace();
      LOGEJ.getInstance().printf(5,oevent,"RemoteException 발생");
      Exception ex = (Exception)_e;
      LOGEJ.getInstance().eprintf(5,oevent,ex);

      RemoteException _eee = (RemoteException)_e;
      String exceptionCode = CommonErrorMessageConstants.ERR_9000_SYSTEM_ERROR;
      String exceptionMsg = exceptionMessage( ocommonDTO.getNation(), exceptionCode);
      String devMsg = "System exception occurred at BizEJB.";
      ////////////////////////////////////////////////////////////////////////////////////////// END
      manageFatalException(header, devMsg, _eee.detail.toString(),
                                exceptionCode, exceptionMsg);
    }
    // BizAction class에서 발생한 exception 중 CosesAppException이 아닌 경우
    else
    {
      _e.printStackTrace();
      Exception ex = (Exception)_e;

      LOGEJ.getInstance().eprintf(5,oevent,ex);
      LOGEJ.getInstance().printf(5,oevent,"Other Exception 발생["+_e.toString() );

      String exceptionCode = CommonErrorMessageConstants.ERR_9000_SYSTEM_ERROR;
      String exceptionMsg = exceptionMessage( ocommonDTO.getNation(), exceptionCode );
      String devMsg = "Exception occurred at BizAction.";
      manageFatalExceptionWithStackTrace(header, devMsg, _e, exceptionCode, exceptionMsg);

    }
    LOGEJ.getInstance().printf(5,oevent,
                               "===============================================[BTF_SPexception() End");
  }

  private String exceptionMessage(String nationCode, String exceptionCode)
  {
      String exceptionMsg = "No message.";

      try
      {
          CommonManagementSBHome commonHome = (CommonManagementSBHome)JNDIService.getInstance().lookup(CommonManagementSBHome.class);
          ICommonManagementSB commonMgt = commonHome.create();
          //ErrorMessageCDTO msg = commonMgt.getErrorMessage(nationCode, exceptionCode);
          exceptionMsg = commonMgt.getErrorMessage(nationCode, exceptionCode);
          //msg.getLocalDescription();
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
   * Method exceptionOutput.
   * @param exceptionCode
   * @param exceptionMessage
   * @return String
   */
  private String exceptionOutput(String exceptionCode,
                                 String exceptionMessage) {
      return "[Excpetion Code] : " + exceptionCode +
          " / [Excpetion Message] : " + exceptionMessage +
          Constants.LINE_SEPARATOR;
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
    LOGEJ.getInstance().printf(5,oevent,header + exceptionDetail);
    LOGEJ.getInstance().printf(5,oevent,header +
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
      LOGEJ.getInstance().printf(5,oevent,header +
                                 exceptionOutput(exceptionCode, exceptionMessage) );
  }

}