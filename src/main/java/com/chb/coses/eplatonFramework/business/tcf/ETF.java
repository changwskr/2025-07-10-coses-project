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

public class ETF implements IETF {
  private static ETF instance;
  private EPlatonEvent eplevent;
  private EPlatonCommonDTO commonDTO;
  private TPSVCINFODTO tpsvcinfo;
  private int giTXInfoflag = 0;
  private UserTransaction tx;

  public static synchronized ETF getInstance() {
    if (instance == null) {
      try{
        instance = new ETF();
        }catch(Exception igex){}
    }
    return instance;
  }

  public ETF(){
  }

  public ETF(UserTransaction ex,int tx_start_info){
    this.tx = tx;
    this.giTXInfoflag = tx_start_info;
  }

  public EPlatonEvent getEPlatonEvent(){
    return this.eplevent;
  }

  public EPlatonEvent execute(EPlatonEvent pevent){
    try{
      eplevent = pevent;

      System.out.println("=================================[ETF_SPinit] start");
      ETF_SPinit();
      System.out.println("=================================[ETF_SPinit] end");

      System.out.println("=================================[ETF_SPmiddle] start");
      ETF_SPmiddle();
      System.out.println("=================================[ETF_SPmiddle] end");

      System.out.println("=================================[ETF_SPend] start");
      ETF_SPend();
      System.out.println("=================================[ETF_SPend] end");

    }
    catch(Exception ex){
      this.ETF_SPerror("EBD200","execute 에러발생");
    }

    return eplevent ;
  }

  public boolean ETF_SPinit()
  {
    try{
      /*************************************************************************
       * 기본정보를 가져온다.
       ************************************************************************/
      Log.DelegateLogger.debug("[ETF] ETF_SPinit START ==============================");
      commonDTO = (EPlatonCommonDTO)eplevent.getCommon();
      tpsvcinfo = (TPSVCINFODTO)eplevent.getTPSVCINFODTO();
      Log.DelegateLogger.debug("[ETF] ETF_SPinit END ==============================");
      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      this.ETF_SPerror("EBD200","ETF_SPinit() 예외발생");
      return false;
    }
  }


  public boolean ETF_SPmiddle()
  {
    try{
      Log.DelegateLogger.debug("[ETF] ETF_SPmiddle START ==============================");

      /*************************************************************************
       * 웹컴포넌단 - EJB 서버단과의 TIMEOUT 관리
       *************************************************************************
       * 이 모듈은 TPSVCINFO의 TXTIMER을 기준으로서 관리한다
       * 즉 웹단에서 시작시간을 기초로 해서 현재시간을 기초로 해서 INTERVAL이 넘어서면
       * 다음 업무단의 로직을 처리하지 않고 바로 에러로 처리
       ************************************************************************/
      Log.DelegateLogger.debug("[ETF] 웹컴포넌트-EJB단과의 트랜잭션 타임아웃 정보 시작 ===========");
      ETF_SPwebtxtimer();
      Log.DelegateLogger.debug("[ETF] 웹컴포넌트-EJB단과의 트랜잭션 타임아웃 정보 종료 : [" + tpsvcinfo.getTx_timer() + "]"  );

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
      ETF_SPsettpmsvcinfo();
      Log.DelegateLogger.debug("[STF] TPMSVCINFO 정보를 셋팅 종료 ===========");

      Log.DelegateLogger.debug("[ETF] ETF_SPmiddle END ==============================");
      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      this.ETF_SPerror("EBD200","ETF_SPmiddle() 예외발생");
      return false;
    }
  }

  public boolean ETF_SPend()
  {
    try{
      Log.DelegateLogger.debug("[ETF] ETF_SPend START ==============================");

      /*************************************************************************
       * 패킷정보를 재셋팅한다.
       ************************************************************************/
      ETF_SPmovepacket();

      /***************************************************************************
       * 트랜잭션에대한 로깅정보 데이타베이스에 저장한다.
       **************************************************************************/
      ETF_SPdbOutLog(eplevent);

      /***************************************************************************
       * 트랜잭션에대한 로깅정보를 관리한다.
       **************************************************************************/
      Log.DelegateLogger.debug("==================[ETF_SPcommonLog START]");
      ETF_SPcommonLog();
      Log.DelegateLogger.debug("==================[ETF_SPcommonLog() END] (true)");

      /***************************************************************************
       * 트랜잭션에 대한 Commit / Rollback 을 관리한다
       **************************************************************************/
      Log.DelegateLogger.debug("==================[Transaction 정보를 완료한다 START]");
      if( this.giTXInfoflag == 1 ){
        switch( tpsvcinfo.getErrorcode().charAt(0) )
        {
          case 'E' :
            TPMSVCAPI.getInstance().TProllback(tx);
            break;
          case 'S' :
            TPMSVCAPI.getInstance().TPcommit(tx);
            break;
        }
      }
      Log.DelegateLogger.debug("==================[Transaction 정보를 완료한다 END]-("+this.giTXInfoflag +")");

      Log.DelegateLogger.debug("[ETF] ETF_SPend END ==============================");
      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      this.ETF_SPerror("EBD200","ETF_SPend() 예외발생");
      return false;
    }
  }

  private boolean ETF_SPdbOutLog(EPlatonEvent event) {
    try{
      EPlatonDelegateDAO dao = new  EPlatonDelegateDAO();
      dao.DB_INSERToutlog(event);
    }
    catch(Exception ex){
      ex.printStackTrace();
      ETF_SPerror("EBD800","ETF_SPdbOutLog()예외발생");
      return false;
    }
    return true;
  }

  private  synchronized boolean ETF_SPcommonLog() {
    String LOGFILENAME = null;
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      LOGFILENAME = "/home/coses/log/output/" + CommonUtil.GetHostName() + "." +
                    this.eplevent.getCommon().getEventNo() + "." +
                    "out" + "." +
                    CommonUtil.GetSysDate();
      fos = new FileOutputStream(LOGFILENAME, true);
      ps = new PrintStream(fos);

      ps.println(this.eplevent.toString());

      ps.flush();
      ps.close();
      fos.close();

    }catch(Exception e)
    {
      try
      {
        if( fos != null )
          fos.close();
        if( ps != null )
          ps.close();
        }
        catch(Exception ex){}
      this.ETF_SPerror("EBD200","ETF_SPcommonLog() 예외발생");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  private boolean isErr(){
    switch (this.eplevent.getTPSVCINFODTO().getErrorcode().charAt(0))
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
        this.ETF_SPerror("EBD200","에러코드가 셋팅안되어 있음");
        return true;
    }
  }

  private void ETF_SPerror(String errorcode,String message)
  {
    switch( this.eplevent.getTPSVCINFODTO().getErrorcode().charAt(0) )
    {
      case 'I' :
        this.eplevent.getTPSVCINFODTO().setErrorcode(errorcode);
        this.eplevent.getTPSVCINFODTO().setError_message(message);
        return;
      case 'E' :
        errorcode = errorcode+"|"+this.eplevent.getTPSVCINFODTO().getErrorcode();
        this.eplevent.getTPSVCINFODTO().setErrorcode(errorcode);
        this.eplevent.getTPSVCINFODTO().setError_message(message);
        return;
    }
  }

  private void ETF_SPwebtxtimer()
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
      ETF_SPerror("EBD010","트랜잭션 웹타임아웃이 발생");

    return;
  }

  private void ETF_SPsettpmsvcinfo()
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
    tpsvcinfo.setSystemOutTime(commonDTO.getSystemOutTime());
    //tpfq : 클라이언트에서 셋팅
    //operation_name : 클라이언트에서 셋팅
    //action_name : 클라이언트에서 셋팅

    return;
  }

  private void ETF_SPmovepacket()
  {
    this.eplevent.setTPSVCINFO(this.tpsvcinfo);
    this.eplevent.setCommon(this.commonDTO);
    //////////////////////////////////////////////////////////////////////////
    // 각 필드에 대한 정보를 재셋팅한다.
    //////////////////////////////////////////////////////////////////////////
  }

  public void setETF_SPtxinfo(int offset){
     this.giTXInfoflag = offset;
   }

   public int getETF_SPtxinfo(){
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