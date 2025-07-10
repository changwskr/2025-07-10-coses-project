package com.ims.oversea.framework.transaction.tpmutil;


import java.io.*;
import java.util.*;
import java.rmi.*;
import javax.naming.*;
import javax.ejb.*;
import javax.transaction.*;
import java.math.BigDecimal;
import javax.rmi.PortableRemoteObject;
import java.sql.*;


import com.chb.coses.foundation.db.DBService;

import com.chb.coses.framework.transfer.*;
//import com.ims.oversea.foundation.config.Config;
import com.ims.oversea.framework.transaction.helper.Convert;
import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.framework.transaction.delegate.action.*;
import com.ims.oversea.foundation.utility.*;
import com.ims.oversea.foundation.logej.LOGEJ;
import com.ims.oversea.framework.transaction.constant.TCFConstants;
import com.ims.oversea.framework.transaction.constant.TCFConstantErrcode;


/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 *
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


public class TPSsendrecv {
  private static TPSsendrecv instance;

  public static synchronized TPSsendrecv getInstance() {
    if (instance == null) {
      try{
        instance = new TPSsendrecv();
      }
      catch(Exception igex)
      {
        //System.out.println(igex);
        igex.printStackTrace();
        return null;
      }
    }
    return instance;
  }

  public TPSsendrecv() {
  }

  /**
   * 타시스템을 호출하기 위한 서비스이다. 이 메소드는 서버상에서 로컬에 있는 서비스만을 전담적으로
   * 관리하는 시스템이 될 것이다.
   * 만약 타머신의 시스템을 호출할 경우에 business을 통해서 호출한다.
   *
   * @param porgSystem  : 호출하는 시스템
   * @param requestName : "cashCard-listCashCard"
   * @param pevent      : 클라이언트단에서 부터 올라온 객체
   * @param event       : 다른시스템을 호출하기 위해서 만든 객체
   * @return
   */

  public EPlatonEvent CallEJB(    String requestName, //action클래스명
                                  EPlatonEvent oevent, //최초클라이언트부터 받은 데이타
                                  IDTO request_cdto //요청할 CDTO객체
                                  )
  {
    EPlatonEvent request_event = null;
    EPlatonEvent reponse_event = null;

    TPSVCINFODTO otpsvcinfoDTO = null;
    EPlatonCommonDTO ocommonDTO = null;

    TPSVCINFODTO request_tpsvcinfoDTO = null;
    EPlatonCommonDTO request_commonDTO = null;

    TPSVCINFODTO reponse_tpsvcinfoDTO = null;
    EPlatonCommonDTO reponse_commonDTO = null;

    TPMSVCINFO tpmsvcinfo = null;

    try{
      request_event = Convert.createEPlatonEvent(oevent) ;
      otpsvcinfoDTO = oevent.getTPSVCINFODTO();
      ocommonDTO = oevent.getCommon();
      request_tpsvcinfoDTO = request_event.getTPSVCINFODTO();
      request_commonDTO = request_event.getCommon();

      /**
       * TPMSVCINFO 모듈의 경우 TPSsendrecv에서 사용된다.
       * TPSsendrecv 모듈이 호출될때마다, TPMSVCINFO 객체가 생성되면서 EPlatonEvent 객체에
       * 저장된다.
       * TPMSVCINFO 모듈 셋팅값은
       *     setCall_hostseq(tpmdto.getHostseq());  // 상대방머신의 hostseq의 값을 셋팅
       *                                            // TPCsendrecv이후 가지고 오는 eplatonevent객체의 값을 셋팅
       *     setCall_orgseq(tpmdto.getOrgseq());    // 상대방머신의 orgseq의 값을 셋팅한다.
       *                                            // TPCsendrecv이후 가지고 오는 eplatonevent객체의 값을 셋팅
       *     setCall_service_name(tarsystem);       // 호출할 상대방 머신의 시스템명을 명시한다.여기서 이값은 request_name이다
       *                                            // 이 값은 TPCsendrecv에 전달되는 requestname이다.
       *     setCall_tpm_in_time(CommonUtil.GetSysTime());      // TPSsendrecv시작시간
       *     setCall_tpm_out_time(this.getCall_tpm_in_time() ); //TPSsendrecv종료시간
       *     setError_code("IZZ000");                           //TPCsendrecv시 상대방에러코드
       *     setOffset('0');                                    //TPCsendrecv 시작 flag
       *
       */
      tpmsvcinfo = new TPMSVCINFO(requestName,oevent);

      /*************************************************************************
       * 호출시스템정보 셋팅
       *************************************************************************/
      request_event.setRequest(request_cdto);
      request_tpsvcinfoDTO.setSystem_name(TPMSutil.getInstance().TPgetcallsystemname(requestName) );
      request_tpsvcinfoDTO.setReqName(requestName);
      request_commonDTO.setReqName(requestName);
      request_event.setAction(otpsvcinfoDTO.getReqName());

      String systemName = TPMSutil.getInstance().TPgetcallsystemname(requestName) ; // Deposit
      String actionClassName =  TPMSutil.getInstance().TPgetactionclassname(request_event)  ; //actionClassName="com.ims.oversea.eplatonframework.business.delegate.action.DepositBizAction"
      String operationName = TPMSutil.getInstance().TPgetoperationclassname(request_event)  ; // com.ims.oversea.eplatonframework.othersystem.operation.DED0021000
      String operationMethod = TPMSutil.getInstance().TPgetinvokemethodname(request_event)  ; // com.ims.oversea.eplatonframework.othersystem.operation.DED0021000
      String operationMethodCDTOName = TPMSutil.getInstance().TPgetparametertypename(request_event);

      request_tpsvcinfoDTO.setAction_name(actionClassName);
      request_tpsvcinfoDTO.setOperation_name(operationName);
      request_tpsvcinfoDTO.setOperation_method(operationMethod);
      request_tpsvcinfoDTO.setCdto_name(operationMethodCDTOName);
      request_tpsvcinfoDTO.setTpfq(TCFConstants.SERVER_SERVER_INTERCHANGE_LOC);
      request_tpsvcinfoDTO.setErrorcode(TCFConstants.SUCCESS_ERRCODE);
      request_commonDTO.setTimeZone(TCFConstants.TIME_ZONE);
      request_commonDTO.setFxRateCount(TCFConstants.FX_RATE_COUNT);

      Object tobj = request_event.getRequest();
      if(!(tobj instanceof IDTO) )
      {
        otpsvcinfoDTO.setErrorcode(TCFConstantErrcode.ETPM010);
        otpsvcinfoDTO.setError_message(TCFConstantErrcode.ETPM010_MSG);
        tpmsvcinfo.setError_code(TCFConstantErrcode.ETPM010);
        tpmsvcinfo.setCall_tpm_out_time(CommonUtil.GetSysTime());

        LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL ,oevent,"TPMAPI.TPSsendrecv():EFWK0042:exception");
        return oevent;
      }

      LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL ,oevent,"==================[TPSsendrecv() START]");
      LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,oevent,"SYSTEM           :"+systemName);
      LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,oevent,"actionClassName  :"+actionClassName);
      LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,oevent,"operationName    :"+operationName);
      LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,oevent,"operationMethod  :"+operationMethod);
      LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,oevent,"TPM(SEND)-"+com.chb.coses.foundation.utility.Reflector.objectToString(request_event));

      /**
       * set TPMSVCINFO
       */
      tpmsvcinfo.setCall_service_name(requestName);
      tpmsvcinfo.setCall_orgseq(request_tpsvcinfoDTO.getOrgseq());
      tpmsvcinfo.setCall_tpm_in_time(CommonUtil.GetSysTime());
      tpmsvcinfo.setCall_tpm_out_time(tpmsvcinfo.getCall_tpm_etf_in_time());

      EPlatonBizAction action = (EPlatonBizAction)(Class.forName(actionClassName).newInstance());
      reponse_event = (EPlatonEvent)action.act(request_event);

    }
    catch( ClassNotFoundException ex ){
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setErrorcode(TCFConstantErrcode.ETPM011);
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setError_message(TCFConstantErrcode.ETPM011_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.FATAL_LOG_LEVEL ,(EPlatonEvent)oevent,ex);
    }
    catch ( IllegalAccessException ex ){
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setErrorcode(TCFConstantErrcode.ETPM012);
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setError_message(TCFConstantErrcode.ETPM012_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)oevent,ex);
    }
    catch ( InstantiationException ex){
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setErrorcode(TCFConstantErrcode.ETPM013);
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setError_message(TCFConstantErrcode.ETPM013_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)oevent,ex);
    }
    catch ( Exception ex){
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setErrorcode(TCFConstantErrcode.ETPM014);
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setError_message(TCFConstantErrcode.ETPM014_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)oevent,ex);
    }

    //////////////////////////////////////////////////////////////////////////
    // TPM SEND / RECV 정보관리
    //////////////////////////////////////////////////////////////////////////
    {
      reponse_tpsvcinfoDTO = ((EPlatonEvent)reponse_event).getTPSVCINFODTO();

      switch( reponse_tpsvcinfoDTO.getErrorcode().charAt(0) )
      {
        case 'I' :
          LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,oevent,"\n");
          LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,oevent,"TPM(RECV)-"+com.chb.coses.foundation.utility.Reflector.objectToString(reponse_event));
          LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,oevent,"TPSsendrecv() Call Success [" + reponse_tpsvcinfoDTO.getErrorcode() + "]" );
          break;
        case 'E' :
          LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,oevent,"TPM(RECV)-"+com.chb.coses.foundation.utility.Reflector.objectToString(reponse_event));
          LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,oevent,"TPSsendrecv() Call Fail [" + reponse_tpsvcinfoDTO.getErrorcode() + "]" );
          break;
      }
    }

    LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,oevent,"==================[TPSsendrecv() END]");

    ////////////////////////////////////////////////////////////////////////////
    // TPMSVCINFO 정보를 셋팅하고 이정보는 TCF에서 이용한다.
    // TPSsendrecv()통해서 타시스템으로의 호출이 정상적으로 마쳤다. 그러므로 TPMSVC정보를
    // 갱신한다.
    ////////////////////////////////////////////////////////////////////////////
    try{
      //TPSsendrecv 시작-종료-인터벌
      tpmsvcinfo.setError_code(reponse_tpsvcinfoDTO.getErrorcode());
      tpmsvcinfo.setCall_tpm_out_time(CommonUtil.GetSysTime());
      tpmsvcinfo.setCall_tpm_interval(TPMSutil.getInstance().TPMinterval(tpmsvcinfo.getCall_tpm_in_time(),tpmsvcinfo.getCall_tpm_out_time()));


      //상대방시스템 stf-etf-interval
      tpmsvcinfo.setCall_tpm_etf_in_time(reponse_tpsvcinfoDTO.getETF_intime());
      tpmsvcinfo.setCall_tpm_etf_out_time(reponse_tpsvcinfoDTO.getETF_outtime());
      tpmsvcinfo.setCall_tpm_stf_in_time(reponse_tpsvcinfoDTO.getSTF_intime());
      tpmsvcinfo.setCall_tpm_stf_out_time(reponse_tpsvcinfoDTO.getSTF_outtime());
      tpmsvcinfo.setCall_tpm_service_interval(TPMSutil.getInstance().TPMinterval(tpmsvcinfo.getCall_tpm_stf_in_time(),tpmsvcinfo.getCall_tpm_etf_out_time() ) );

      //상대방 로직위치
      tpmsvcinfo.setCall_location(((EPlatonEvent)reponse_event).getTPSVCINFODTO().getLogic_level());

      //상대방시스템의 HOSTSEQ ORGSEQ
      tpmsvcinfo.setCall_hostseq(reponse_tpsvcinfoDTO.getHostseq());
      tpmsvcinfo.setCall_orgseq(reponse_tpsvcinfoDTO.getOrgseq());

      //상대방시스템의 errcode
      tpmsvcinfo.setError_code(reponse_tpsvcinfoDTO.getErrorcode() );
      ////////////////////////////////////////////////////////////////////////////
      // 클라이언트로부터 올라온 원본데이타에 저장한다.
      ////////////////////////////////////////////////////////////////////////////
      otpsvcinfoDTO.addtpmsvcinfo(tpmsvcinfo);
      reponse_tpsvcinfoDTO.setTpmsvcinfolist(otpsvcinfoDTO.getTpmsvcinfolist());
    }
    catch ( Exception ex){
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setErrorcode(TCFConstantErrcode.ETPM015);
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setError_message(TCFConstantErrcode.ETPM015_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)oevent,ex);
    }

    ////////////////////////////////////////////////////////////////////////////
    // 클라이언트로부터 올라온 원본데이타에 저장한다.
    ////////////////////////////////////////////////////////////////////////////
    otpsvcinfoDTO.addtpmsvcinfo(tpmsvcinfo);
    reponse_tpsvcinfoDTO.setTpmsvcinfolist(otpsvcinfoDTO.getTpmsvcinfolist());

    return (EPlatonEvent)reponse_event;

  }

/*
  public IDTO CallEJB(  Object remote,
                        String operation_class_name,
                        String operation_method_name,
                        EPlatonEvent oevent,
                        IDTO request_ddto )
  {
    try{
      remote.execute(operation_class_name,operation_method_name,request_ddto,oevent);
    }
    catch ( Exception ex){
      LOGEJ.getInstance().eprintf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)oevent,ex);
    }
    return null;
  }
*/
}