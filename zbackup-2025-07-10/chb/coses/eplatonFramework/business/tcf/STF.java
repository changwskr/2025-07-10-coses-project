package com.chb.coses.eplatonFramework.business.tcf;

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
import java.io.*;
import javax.ejb.*;
import javax.naming.*;
import javax.transaction.*;

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

import com.chb.coses.eplatonFramework.transfer.*;
import com.chb.coses.eplatonFramework.business.helper.TPMSVCAPI;
import com.chb.coses.eplatonFramework.business.helper.CommonUtil;
import com.chb.coses.eplatonFramework.business.operation.IBizOperation;
import com.chb.coses.eplatonFramework.business.helper.CommonUtil;
import com.chb.coses.eplatonFramework.business.helper.EJBUtils;
import com.chb.coses.eplatonFramework.business.dao.EPlatonDelegateDAO;

public class STF implements ISTF {
  private static STF instance;
  private EPlatonEvent eplevent;
  private EPlatonCommonDTO commonDTO;
  private TPSVCINFODTO tpsvcinfo;
  private String bankCode;
  private String branchCode;
  private String channelType;
  private String businessDate;
  private String eventNo;
  private String transactionNo;
  private String baseCurrency;
  private ICommonManagementSB commonManagementSB;
  private IReferenceManagementSB referenceManagementSB;
  private UserTransaction tx;
  private int giTXInfoflag = 0;

  public static synchronized STF getInstance() {
    if (instance == null) {
      try{
        instance = new STF();
        }catch(Exception igex){}
    }
    return instance;
  }

  public STF(){
  }

  public STF(UserTransaction tx){
    this.tx = tx;
  }

  public EPlatonEvent getEPlatonEvent(){
    return eplevent;
  }

  public EPlatonEvent execute(EPlatonEvent pevent){

    try{
      eplevent = pevent;

      System.out.println("==================[STF_SPinit] start");
      STF_SPinit();

      switch ( tpsvcinfo.getErrorcode().charAt(0) )
      {
        case 'E' :
          System.out.println("STF_SPinit error");
          System.out.println("==================[STF_SPinit] end");
          System.out.println("==================[STF_SPend] start");
          STF_SPend();
          System.out.println("==================[STF_SPend] end");
          return eplevent;
        case 'I' :
          System.out.println("STF_SPinit success");
          System.out.println("==================[STF_SPinit] end");
          System.out.println("==================[STF_SPmiddle] start");
          STF_SPmiddle();
          System.out.println("==================[STF_SPmiddle] end");
          break;
      }
      System.out.println("==================[STF_SPend] start");
      STF_SPend();
      System.out.println("==================[STF_SPend] end");
    }
    catch(Exception ex){
      ex.printStackTrace();
      this.STF_SPerror("EBD200","execute 에러발생");
    }

    return eplevent ;
  }

  public boolean STF_SPinit()
  {
    try{
      Log.DelegateLogger.debug("[STF] 영업일자/뱅크코드/브렌치코드/채널타입 셋===========");
      commonDTO = (EPlatonCommonDTO)eplevent.getCommon();
      tpsvcinfo = eplevent.getTPSVCINFODTO();
      bankCode = commonDTO.getBankCode();
      branchCode = commonDTO.getBranchCode();
      channelType = commonDTO.getChannelType();
      businessDate = commonDTO.getBusinessDate();
      eventNo = commonDTO.getEventNo();
      commonManagementSB = EJBUtils.getCommonEJB();
      referenceManagementSB = EJBUtils.getReferenceEJB();

      /*************************************************************************
       * 에러코드정보를 초기화한다.
       ************************************************************************/
      tpsvcinfo.setErrorcode("IZZ000");

      /*************************************************************************
       * 트랜잭션의 시작정보를 알기위한 FLAG SET.
       * 1 - already transaction start
       * 0 - transaction not start
       ************************************************************************/
      giTXInfoflag = 0;

      /*************************************************************************
       * 시스탬날짜을 설정한다.
       ************************************************************************/
      Log.DelegateLogger.debug("[STF] 시스템일자 셋팅 : ");
      /*
      TimeProcess timeProcess = TimeProcess.getInstance();
      String systemDateAndTime = timeProcess.getSystemDate(commonDTO.getTimeZone());
      commonDTO.setSystemDate(systemDateAndTime.substring(0, 8));
      */
      String systemDateAndTime = CommonUtil.GetSysDate();
      commonDTO.setSystemDate(systemDateAndTime.substring(0, 8));
      Log.DelegateLogger.debug("["+systemDateAndTime.substring(0, 8)+"]");

      /*************************************************************************
       * 시스탬시간을 설정한다.
       ************************************************************************/
      Log.DelegateLogger.debug("[STF] 시스템 트랜잭션 시작시간 셋팅 : ");
      //commonDTO.setSystemInTime(systemDateAndTime.substring(8));
      systemDateAndTime = CommonUtil.GetSysTime();
      commonDTO.setSystemInTime(systemDateAndTime.substring(8));
      Log.DelegateLogger.debug("["+systemDateAndTime.substring(8)+"]");

      /*************************************************************************
       * Base Currency
       ************************************************************************/
      baseCurrency = commonManagementSB.getBaseCurrency(commonDTO.getBankCode());
      commonDTO.setBaseCurrency(baseCurrency);

      /*************************************************************************
       * 영업일자을 설정한다.
       ************************************************************************/
      Log.DelegateLogger.debug("[STF] 영업일자 셋팅 : ");
      //businessDate = commonManagementSB.getBusinessDate(commonDTO.getBankCode());
      if( (businessDate=STF_SPgetbusinessdate()) == null )
        STF_SPerror("EBD001","영업일자 구할시 에러");
      else
        commonDTO.setBusinessDate(businessDate);

      Log.DelegateLogger.debug("["+businessDate+"]");

      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror("EBD001","STFinit() 초기화시 에러");
      return false;
    }
  }

  public boolean STF_SPmiddle()
  {
    try{
      /*************************************************************************
       * 클라이언트에서 요청한 트랜잭션 정보를 구한다
       ************************************************************************/
      Log.DelegateLogger.debug("[STF] 트랜잭션 정보를 셋팅===========");
      switch (TPMSVCAPI.getInstance().TPinfo(tx))
      {
        case -1 :
          STF_SPerror("EBD100","TPinfo EX 뱔생");
          break;
        case 1 :
          /*********************************************************************
           * 이미 트랜잭션을 시작한 상태이므로 트랜잭션을 시작하지 않고 현재의 트랜잭션
           * 을 유지해 준다
           ********************************************************************/
          break;
        case 0 :
          /*********************************************************************
           * 클라이언트 화면에서 올라온 Transaction Timer (seconds) 값을 가지고
           * UserTransaction을 시작한다.
           ********************************************************************/
          if( TPMSVCAPI.getInstance().TPbegin(tx,CommonUtil.Str2Int(tpsvcinfo.getTx_timer()))){
            setSTF_SPtxinfo(1);
          }
          else{
            setSTF_SPtxinfo(0);
          }
          break;
      }
      Log.DelegateLogger.debug("[STF] 트랜잭션 정보를 셋팅 종료 : [" + this.giTXInfoflag + "]"  );
      if( isErr() ){
        return false;
      }


      /*************************************************************************
       * SEQUENCE를 채번한후 거래별 트랜잭션 번호를 셋팅한다.
       ************************************************************************/
      Log.DelegateLogger.debug("[STF] 전체 트랜잭션 번호 채번 시작 ===========");
      transactionNo = referenceManagementSB.getTransactionNumber(commonDTO.getBankCode(), businessDate);
      commonDTO.setTransactionNo(transactionNo);
      tpsvcinfo.setHostseq(transactionNo);
      if( ! tpsvcinfo.getTpfq().equals("100") )
        tpsvcinfo.setOrgseq(transactionNo);
      Log.DelegateLogger.debug("[STF] 전체 트랜잭션 번호 채번 종료 : [" + transactionNo + "]"  );
      if( isErr() ){
        return false;
      }

      /*************************************************************************
       * 마감전후 구분 필드를 세운다.
       *************************************************************************
       * 자동화기기 마감전후를 구분하여 마감구분 필드를 세운다.
       * 토요일 : 13시 * 평  일 : 16시30
       * 향후 이 로직을 위한 추가 로직을 구성한다.
       ***********************************************************************/
      Log.DelegateLogger.debug("[STF] 마감전후필드 셋팅 시작 ===========");
      STF_SPeod();
      Log.DelegateLogger.debug("[STF] 마감전후필드 셋팅 종료 : [" + tpsvcinfo.getTrclass() + "]"  );
      if( isErr() ){
        return false;
      }

      /*************************************************************************
       * 거래제어정보를 관리한다
       *************************************************************************
       * 뱅크코드별 거래 제어
       * 브렌치별 거래 제어
       * 거래코드별 거래제어
       * 텔러별 거래 제어
       * 온라인 거래 제어
       * 배치 거래 제어
       * 온라인 거래만 허용
       * 배치 거래만 허용
       ************************************************************************/
       Log.DelegateLogger.debug("[STF] 거래 제어 정보 시작 ===========");
       STF_SPtxctl();
       Log.DelegateLogger.debug("[STF] 거래 제어 정보 종료 : [" + " ]"  );
       if( isErr() ){
         return false;
       }

       /*************************************************************************
        * 웹컴포넌단 - EJB 서버단과의 TIMEOUT 관리
        *************************************************************************
        * 이 모듈은 TPSVCINFO의 TXTIMER을 기준으로서 관리한다
        * 즉 웹단에서 시작시간을 기초로 해서 현재시간을 기초로 해서 INTERVAL이 넘어서면
        * 다음 업무단의 로직을 처리하지 않고 바로 에러로 처리
        ************************************************************************/
       Log.DelegateLogger.debug("[STF] 웹컴포넌트-EJB단과의 트랜잭션 타임아웃 정보 시작 ===========");
       STF_SPwebtxtimer();
       Log.DelegateLogger.debug("[STF] 웹컴포넌트-EJB단과의 트랜잭션 타임아웃 정보 종료 : [" + tpsvcinfo.getTx_timer() + "]"  );
       if( isErr() ){
         return false;
       }

       /*************************************************************************
        * 클라이언트의 호출정보를 보여준다. (TPFQ)
        *************************************************************************
        * 200 - 온라인 클라이언트
        * 100 - 온라인 서버에서 호출
        * 300 - 대외기관에서 호출
        * 400 - 배치에서 호출
        ************************************************************************/
       Log.DelegateLogger.debug("[STF] 클라이언트의 호출위치 정보를 가지고 온다 ===========");
       switch( CommonUtil.Str2Int(tpsvcinfo.getTpfq()) )
       {
         case 100 :
           Log.DelegateLogger.debug("TPFQ : 100 - 온라인서버에서 호출");
           break;
         case 200 :
           Log.DelegateLogger.debug("TPFQ : 200 - 웹단에서 호출");
           break;
         case 300 :
           Log.DelegateLogger.debug("TPFQ : 300 - 대외기관에서 호출");
           break;
         case 400 :
           Log.DelegateLogger.debug("TPFQ : 400 - 배치에서 호출");
           break;
         default :
           STF_SPerror("EBD002","TPFQ Information Not Valid");
         break;
       }
       Log.DelegateLogger.debug("[STF] 클라이언트의 호출위치 정보를 가지고 온다 : [" + tpsvcinfo.getTpfq() + "]"  );
       if( isErr() ){
         return false;
       }

       /*************************************************************************
        * TPMSVCINFO 정보를 셋팅한다.
        *************************************************************************
        * call_service_name
        * call_tpm_in_time
        * call_tpm_out_time
        * call_tpme_interval
        * call_tpm_stf_in_time
        * call_tpm_stf_out_time
        * call_tpm_etf_in_time
        * call_tpm_etf_out_time
        * call_tpme_service_interval
        * error_code
        * call_hostseq
        * call_orgseq
        * call_location
        ************************************************************************/
       Log.DelegateLogger.debug("[STF] TPMSVCINFO 정보를 셋팅 시작 ===========");
       STF_SPsettpmsvcinfo();
       Log.DelegateLogger.debug("[STF] TPMSVCINFO 정보를 셋팅 종료 ===========");

       return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror("EBD100","거래제어EX");
      return false;
    }
  }

  public boolean STF_SPend()
  {
    try{

      /*************************************************************************
       * 기본정보를 재셋팅한다.
       ************************************************************************/
      commonDTO.setSystemOutTime(CommonUtil.GetSysTime());

      /*************************************************************************
       * TPMSVCINFO 정보를 재셋팅한다.
       ************************************************************************/
      tpsvcinfo.setSystemInTime(commonDTO.getSystemInTime());

      /*************************************************************************
       * 패킷정보를 재셋팅한다.
       ************************************************************************/
      STF_SPmovepacket();

      /*************************************************************************
       * 공통정보 로깅작을 실시한다.
       ************************************************************************/
      System.out.println("==================[STF_SPdbInLog() START]");
      STF_SPdbInLog(eplevent);
      System.out.println("==================[STF_SPdbInLog() END] (true)");

      /*************************************************************************
       * 공통정보 로깅작을 실시한다.
       ************************************************************************/
      System.out.println("==================[STF_SPcommonLog START]");
      STF_SPcommonLog();
      System.out.println("==================[STF_SPcommonLog() END] (true)");

      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror("EBD800","STF_SPend 예외발생");
      return false;
    }
  }

  private boolean isErr(){
    switch (tpsvcinfo.getErrorcode().charAt(0))
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
        STF_SPerror("EBD800","에러코드가 셋팅안됨");
        return true;
    }
  }

  private boolean STF_SPdbInLog(EPlatonEvent event) {
    try{
      EPlatonDelegateDAO dao = new  EPlatonDelegateDAO();
      dao.DB_INSERTinlog(event);
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror("EBD800","STF_SPdbInLog()예외발생");
      return false;
    }
    return true;
  }

  private  boolean STF_SPcommonLog() {
    String LOGFILENAME = null;
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      /*************************************************************************
       * 트랜잭션의 OUTTIME을 다시 초기화한다.
       ************************************************************************/
      LOGFILENAME = "/home/coses/log/input/" + CommonUtil.GetHostName() + "." +
          eplevent.getCommon().getEventNo() + "." +
          "in" + "." +
          CommonUtil.GetSysDate();
      fos = new FileOutputStream(LOGFILENAME, true);
      ps = new PrintStream(fos);

      ps.println(eplevent.toString());

      ps.flush();
      ps.close();
      fos.close();

    }catch(Exception e){
      try
      {
        if( fos != null )
          fos.close();
        if( ps != null )
          ps.close();
        }
        catch(Exception ex){}
        e.printStackTrace();
        STF_SPerror("EBD801","STF_SPcommonLog() 에러");
        return false;
    }
    return true;
  }

  private int STF_SPtxctl()
  {
    /***************************************************************************
     * 일단 데이타베이스의 테이블로 관리 할 것이다
     * 거래코드별, 단말별, 온라인/배치, 텔러별 나누어서
     * 트랜잭션 제어정보를 관리 할 것임
     **************************************************************************/
    try{

      return 0;
    }
    catch(Exception ex){
      STF_SPerror("EBD100","거래제어EX");
      return -1;
    }
  }

  private void STF_SPeod()
  {
    return;
  }

  private void STF_SPwebtxtimer()
  {
    String psStartTimer=commonDTO.getSystemInTime();
    String psEndTimer=CommonUtil.GetSysTime();
    String ss=null;
    String mm=null;
    String ee=null;
    int startsec=99999999;
    int	endsec=99999999;

    /***********************************************************************/
    /* 시작타임                                                             */
    /***********************************************************************/
    ss=psStartTimer.substring(0,2);
    mm=psStartTimer.substring(2,4);
    ee=psStartTimer.substring(4,6);
    startsec = CommonUtil.Str2Int(ss)*60*60 + CommonUtil.Str2Int(mm)*60 + CommonUtil.Str2Int(ee);

    /***********************************************************************/
    /* 종료타임                                                             */
    /***********************************************************************/
    ss=psEndTimer.substring(0,2);
    mm=psEndTimer.substring(2,4);
    ee=psEndTimer.substring(4,6);
    endsec = CommonUtil.Str2Int(ss)*60*60 + CommonUtil.Str2Int(mm)*60 + CommonUtil.Str2Int(ee);

    if( ! (CommonUtil.Str2Int(tpsvcinfo.getTx_timer())<=(endsec-startsec)) )
      STF_SPerror("EBD010","트랜잭션 웹타임아웃이 발생");

    return;
  }

  private void STF_SPsettpmsvcinfo()
  {
    /*
    * call_service_name
    * call_tpm_in_time
    * call_tpm_out_time
    * call_tpme_interval
    * call_tpm_stf_in_time
    * call_tpm_stf_out_time
    * call_tpm_etf_in_time
    * call_tpm_etf_out_time
    * call_tpme_service_interval
    * error_code
    * call_hostseq
    * call_orgseq
    * call_location */
    //service_name : 클라이언트에서 셋팅
    tpsvcinfo.setSystem_date(commonDTO.getSystemDate());
    tpsvcinfo.setSystemInTime(commonDTO.getSystemInTime());
    tpsvcinfo.setSystemOutTime(commonDTO.getSystemOutTime());
    //tpfq : 클라이언트에서 셋팅
    //operation_name : 클라이언트에서 셋팅
    //action_name : 클라이언트에서 셋팅
    tpsvcinfo.setHostseq(commonDTO.getTransactionNo());

    return;
  }

  private void STF_SPerror(String errorcode,String message)
  {
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

  private void STF_SPmovepacket()
  {
    eplevent.setTPSVCINFO(this.tpsvcinfo);
    eplevent.setCommon(this.commonDTO);
    //////////////////////////////////////////////////////////////////////////
    // 각 필드에 대한 정보를 재셋팅한다.
    //////////////////////////////////////////////////////////////////////////

  }

  public String STF_SPgetbusinessdate()
  {
    String aa = null;
    try {
      EPlatonDelegateDAO dao = new  EPlatonDelegateDAO();
      aa = dao.queryForBusinessDate(commonDTO.getBankCode());
    }
    catch(Exception e)
    {
      return null;
    }
    return(aa);
  }


  public void setSTF_SPtxinfo(int offset){
    this.giTXInfoflag = offset;
  }

  public int getSTF_SPtxinfo(){
    return this.giTXInfoflag;
  }

  public UserTransaction getUserTransactin()
  {
    return this.tx;
  }
  public void setUserTransactin(UserTransaction tx)
  {
    this.tx=tx;
  }

}