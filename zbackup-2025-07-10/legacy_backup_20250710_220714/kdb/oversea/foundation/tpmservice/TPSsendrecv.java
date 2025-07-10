package com.kdb.oversea.foundation.tpmservice;


import java.io.*;
import java.util.*;
import java.rmi.*;
import javax.naming.*;
import javax.ejb.*;
import javax.transaction.*;
import java.math.BigDecimal;
import javax.rmi.PortableRemoteObject;
import java.sql.*;

import com.chb.coses.framework.transfer.*;
import com.chb.coses.foundation.db.DBService;
import com.kdb.oversea.foundation.config.Config;
import com.kdb.oversea.foundation.convert.Convert;
import com.kdb.oversea.eplatonframework.transfer.*;
import com.kdb.oversea.eplatonframework.business.delegate.action.*;
import com.kdb.oversea.foundation.utility.*;
import com.kdb.oversea.foundation.logej.LOGEJ;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
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
        System.out.println(igex);
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
      request_tpsvcinfoDTO.setSystem_name(TPMSVCAPI.getInstance().TPgetcallsystemname(requestName) );
      request_tpsvcinfoDTO.setReqName(requestName);
      request_commonDTO.setReqName(requestName);
      request_event.setAction(otpsvcinfoDTO.getReqName());

      String systemName = TPMSVCAPI.getInstance().TPgetcallsystemname(requestName) ; // Deposit
      String actionClassName =  TPMSVCAPI.getInstance().TPgetactionclassname(request_event)  ; //actionClassName="com.kdb.oversea.eplatonframework.business.delegate.action.DepositBizAction"
      String operationName = TPMSVCAPI.getInstance().TPgetoperationclassname(request_event)  ; // com.kdb.oversea.eplatonframework.othersystem.operation.DED0021000
      String operationMethod = TPMSVCAPI.getInstance().TPgetinvokemethodname(request_event)  ; // com.kdb.oversea.eplatonframework.othersystem.operation.DED0021000
      String operationMethodCDTOName = TPMSVCAPI.getInstance().TPgetparametertypename(request_event);

      request_tpsvcinfoDTO.setAction_name(actionClassName);
      request_tpsvcinfoDTO.setOperation_name(operationName);
      request_tpsvcinfoDTO.setOperation_method(operationMethod);
      request_tpsvcinfoDTO.setCdto_name(operationMethodCDTOName);
      request_tpsvcinfoDTO.setTpfq("100");
      request_tpsvcinfoDTO.setErrorcode("IZZ000");
      request_commonDTO.setTimeZone("GMT+09:00");
      request_commonDTO.setFxRateCount(1);

      Object tobj = request_event.getRequest();
      if(!(tobj instanceof IDTO) )
      {
        otpsvcinfoDTO.setErrorcode("EFWK0042");
        otpsvcinfoDTO.setError_message(this.getClass().getName()+ ".TPSsendrecv():IDTO not valid");
        LOGEJ.getInstance().printf(5,oevent,"TPMAPI.TPSsendrecv():EFWK0042:exception");
        tpmsvcinfo.setError_code("EFWK0042");
        tpmsvcinfo.setCall_tpm_out_time(CommonUtil.GetSysTime());
        return oevent;
      }

      LOGEJ.getInstance().printf(5,oevent,"==================[TPSsendrecv() START]");
      LOGEJ.getInstance().printf(5,oevent,"SYSTEM         :"+systemName);
      LOGEJ.getInstance().printf(5,oevent,"actionClassName:"+actionClassName);
      LOGEJ.getInstance().printf(5,oevent,"operationName  :"+operationName);
      LOGEJ.getInstance().printf(5,oevent,"operationMethod  :"+operationMethod);
      LOGEJ.getInstance().printf(5,oevent,"TPM(SEND)-"+com.chb.coses.foundation.utility.Reflector.objectToString(request_event));

      EPlatonBizAction action = (EPlatonBizAction)(Class.forName(actionClassName).newInstance());
      reponse_event = (EPlatonEvent)action.act(request_event);

    }
    catch( ClassNotFoundException ex ){
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setErrorcode("EFWK0043");
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setError_message(this.getClass().getName()+ ".TPSsendrecv():"+ex.toString() );
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)oevent,ex);
    }
    catch ( IllegalAccessException ex ){
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setErrorcode("EFWK0044");
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setError_message(this.getClass().getName()+ ".TPSsendrecv():"+ex.toString() );
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)oevent,ex);
    }
    catch ( InstantiationException ex){
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setErrorcode("EFWK0045");
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setError_message(this.getClass().getName()+ ".TPSsendrecv():"+ex.toString() );
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)oevent,ex);
    }
    catch ( Exception ex){
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setErrorcode("EFWK0046");
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setError_message(this.getClass().getName()+ ".TPSsendrecv():"+ex.toString() );
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)oevent,ex);
    }

    //////////////////////////////////////////////////////////////////////////
    // TPM SEND / RECV 정보관리
    //////////////////////////////////////////////////////////////////////////
    {
      reponse_tpsvcinfoDTO = ((EPlatonEvent)reponse_event).getTPSVCINFODTO();

      switch( reponse_tpsvcinfoDTO.getErrorcode().charAt(0) )
      {
        case 'I' :
          LOGEJ.getInstance().printf(5,oevent,"TPM(RECV)-"+com.chb.coses.foundation.utility.Reflector.objectToString(reponse_event));
          LOGEJ.getInstance().printf(5,oevent,"TPSsendrecv() Call Success");
          break;
        case 'E' :
          LOGEJ.getInstance().printf(5,oevent,"TPM(RECV)-"+com.chb.coses.foundation.utility.Reflector.objectToString(reponse_event));
          LOGEJ.getInstance().printf(5,oevent,"TPSsendrecv() Call Fail");
          break;
      }
    }

    LOGEJ.getInstance().printf(5,oevent,"==================[TPSsendrecv() END]");

    ////////////////////////////////////////////////////////////////////////////
    // TPMSVCINFO 정보를 셋팅하고 이정보는 TCF에서 이용한다.
    // TPSsendrecv()통해서 타시스템으로의 호출이 정상적으로 마쳤다. 그러므로 TPMSVC정보를
    // 갱신한다.
    ////////////////////////////////////////////////////////////////////////////
    try{
      tpmsvcinfo.setError_code(reponse_tpsvcinfoDTO.getErrorcode());
      tpmsvcinfo.setCall_tpm_out_time(CommonUtil.GetSysTime());
      tpmsvcinfo.setCall_tpme_interval(CommonUtil.Int2Str( CommonUtil.ItvSec(tpmsvcinfo.getCall_tpm_in_time(),tpmsvcinfo.getCall_tpm_out_time()) ));

      tpmsvcinfo.setCall_tpm_etf_in_time(reponse_tpsvcinfoDTO.getETF_intime());
      tpmsvcinfo.setCall_tpm_etf_out_time(reponse_tpsvcinfoDTO.getETF_outtime());

      tpmsvcinfo.setCall_tpm_stf_in_time(reponse_tpsvcinfoDTO.getSTF_intime());
      tpmsvcinfo.setCall_tpm_stf_out_time(reponse_tpsvcinfoDTO.getSTF_outtime());

      tpmsvcinfo.setCall_location(((EPlatonEvent)reponse_event).getTPSVCINFODTO().getLogic_level());

      ////////////////////////////////////////////////////////////////////////////
      // 클라이언트로부터 올라온 원본데이타에 저장한다.
      ////////////////////////////////////////////////////////////////////////////
      otpsvcinfoDTO.addtpmsvcinfo(tpmsvcinfo);
      reponse_tpsvcinfoDTO.setTpmsvcinfolist(otpsvcinfoDTO.getTpmsvcinfolist());
    }
    catch ( Exception ex){
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setErrorcode("EFWK0048");
      ((EPlatonEvent)reponse_event).getTPSVCINFODTO().setError_message(this.getClass().getName()+ ".TPMSVCINFO:"+ex.toString() );
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)oevent,ex);
    }

    //////////////////////////////////////////////////////////////////////////
    // TPM 서비스 관리
    //////////////////////////////////////////////////////////////////////////
    {
      reponse_tpsvcinfoDTO = ((EPlatonEvent)reponse_event).getTPSVCINFODTO();

      switch( reponse_tpsvcinfoDTO.getErrorcode().charAt(0) )
      {
        case 'I' :
          LOGEJ.getInstance().printf(5,oevent,"TPM 모듈 성공");
          break;
        case 'E' :
          LOGEJ.getInstance().printf(5,oevent,"TPM 모듈 실패");
          break;
      }
    }

    ////////////////////////////////////////////////////////////////////////////
    // 클라이언트로부터 올라온 원본데이타에 저장한다.
    ////////////////////////////////////////////////////////////////////////////
    otpsvcinfoDTO.addtpmsvcinfo(tpmsvcinfo);
    reponse_tpsvcinfoDTO.setTpmsvcinfolist(otpsvcinfoDTO.getTpmsvcinfolist());

    return (EPlatonEvent)reponse_event;

  }

}