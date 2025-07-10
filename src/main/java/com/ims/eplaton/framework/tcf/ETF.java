package com.ims.eplaton.framework.tcf;

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

import com.ims.eplaton.eplatonFWK.transfer.*;
import com.ims.eplaton.foundation.helper.TPMSVCAPI;
import com.ims.eplaton.foundation.helper.CommonUtil;
import com.ims.eplaton.eplatonFWK.business.operation.IBizOperation;
import com.ims.eplaton.foundation.helper.CommonUtil;
import com.ims.eplaton.eplatonFWK.business.helper.EJBUtils;
import com.ims.eplaton.eplatonFWK.business.dao.EPlatonDelegateDAO;
import com.ims.eplaton.foundation.helper.logej.LOGEJ;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */


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

  public ETF(UserTransaction tx,int tx_start_info){
    this.tx = tx;
    this.giTXInfoflag = tx_start_info;
  }

  public EPlatonEvent getEPlatonEvent(){
    return this.eplevent;
  }

  public EPlatonEvent execute(EPlatonEvent pevent){
    try{
      eplevent = pevent;

      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"=================================[ETF_SPinit] start");
      ETF_SPinit();
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"=================================[ETF_SPinit] end");

      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"=================================[ETF_SPmiddle] start");
      ETF_SPmiddle();
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"=================================[ETF_SPmiddle] end");

      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"=================================[ETF_SPend] start");

      ETF_SPend();
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"=================================[ETF_SPend] end");

    }
    catch(Exception ex){
      ETF_SPerror("EFWK0021",this.getClass().getName()+ ".execute():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
    }

    return eplevent ;
  }

  public boolean ETF_SPinit()
  {
    try{
      /*************************************************************************
       * 기본정보를 가져온다.
       ************************************************************************/
      commonDTO = (EPlatonCommonDTO)eplevent.getCommon();
      tpsvcinfo = (TPSVCINFODTO)eplevent.getTPSVCINFODTO();
      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      ETF_SPerror("EFWK0022",this.getClass().getName()+ ".execute():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
      return false;
    }
  }


  public boolean ETF_SPmiddle()
  {
    try{

      /*************************************************************************
       * 웹컴포넌단 - EJB 서버단과의 TIMEOUT 관리
       *************************************************************************
       * 이 모듈은 TPSVCINFO의 TXTIMER을 기준으로서 관리한다
       * 즉 웹단에서 시작시간을 기초로 해서 현재시간을 기초로 해서 INTERVAL이 넘어서면
       * 다음 업무단의 로직을 처리하지 않고 바로 에러로 처리
       ************************************************************************/
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Check Transaction Timeout between WAF and EJB Tier ::");
      ETF_SPwebtxtimer();
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"ending : [" + tpsvcinfo.getTx_timer() + "]"  );

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
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Set TPMSVCINFO ::");
      ETF_SPsettpmsvcinfo();

      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      ETF_SPerror("EFWK0023",this.getClass().getName()+ ".execute():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
      return false;
    }
  }

  public boolean ETF_SPend()
  {
    try{

      /*************************************************************************
       * 패킷정보를 재셋팅한다.
       ************************************************************************/
      ETF_SPmovepacket();

      /***************************************************************************
       * 트랜잭션에대한 로깅정보 데이타베이스에 저장한다.
       **************************************************************************/

      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[ETF_SPdbOutLog() START]");
      if( !ETF_SPdbOutLog(eplevent) ){
        LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"ETF_SPdbOutLog() error");
      }
      else{
        LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"ETF_SPdbOutLog() success");
      }
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[ETF_SPdbOutLog() END] (true)");


      /***************************************************************************
       * 트랜잭션에대한 로깅정보를 관리한다.
       **************************************************************************/
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[ETF_SPcommonLog START]");

      if( !ETF_SPcommonLog() ){
        LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"ETF_SPcommonLog() error");
      }
      else{
        LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"ETF_SPcommonLog() success");
      }
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[ETF_SPcommonLog() END] (true)");

      /***************************************************************************
       * 트랜잭션에 대한 Commit / Rollback 을 관리한다
       * 현재 UserTransaction을 걸기위한 Tx Data Source 부분을 STF,ETF부분에서사용할수
       * 있는 구조를 설계해야 된다.
       * 일단은 구조만 가지고 간다. (향후변경)
       **************************************************************************/
      if( this.giTXInfoflag == 1 ){
        LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[TProllback/TPcommit START]");
        LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Business Error Code : [" + tpsvcinfo.getErrorcode() + "]");

        switch( tpsvcinfo.getErrorcode().charAt(0) )
        {
          case 'E' :
            LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Call - TProllback()");
            if( TPMSVCAPI.getInstance().TProllback(tx) )
              LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"success");
            else{
              LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"error");
              ETF_SPerror("EFWK0030",this.getClass().getName()+ ".ETF_SPend():TProllback() Exception");
            }
            break;
          case 'I' :
            LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Call - TPcommit()");
            if( TPMSVCAPI.getInstance().TPcommit(tx) )
              LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"success");
            else{
              LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"error");
              ETF_SPerror("EFWK0031",this.getClass().getName()+ ".ETF_SPend():TPcommit() Exception");
            }
            break;
          default :
            LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Call - TProllback() - default");
            if( TPMSVCAPI.getInstance().TProllback(tx) )
              LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"success");
            else{
              LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"error");
              ETF_SPerror("EFWK0031",this.getClass().getName()+ ".ETF_SPend():TProllback() Exception");
            }
            break;
        }
        LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[TProllback/TPcommit END]-("+this.giTXInfoflag +")");

      }

      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      ETF_SPerror("EFWK0032",this.getClass().getName()+ ".ETF_SPend():"+ex.toString() );
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
      return false;
    }
  }

  private boolean ETF_SPdbOutLog(EPlatonEvent event) {
    try{
      EPlatonDelegateDAO dao = new  EPlatonDelegateDAO();
      if( !dao.DB_INSERToutlog(event)){
        ETF_SPerror("EFWK0027",this.getClass().getName()+ ".ETF_SPdbOutLog():DB_INSERToutlog EXCEPTION");
        return false;
      }
    }
    catch(Exception ex){
      ex.printStackTrace();
      ETF_SPerror("EFWK0028",this.getClass().getName()+ ".ETF_SPdbOutLog():EXCEPTION");
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
      return false;
    }
    return true;
  }

  private boolean ETF_SPcommonLog() {
    String LOGFILENAME = null;
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      LOGFILENAME = "/home/coses/log/output/" + CommonUtil.GetHostName() + "." +
                    eplevent.getTPSVCINFODTO().getSystem_name()  + "." +
                    "out" + "." + CommonUtil.GetSysDate();
      fos = new FileOutputStream(LOGFILENAME, true);
      ps = new PrintStream(fos);

      ps.println(eplevent.getTPSVCINFODTO().getOperation_name()+"|"+eplevent.getTPSVCINFODTO().getOrgseq()+"-"+com.chb.coses.foundation.utility.Reflector.objectToString(eplevent));

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
        ETF_SPerror("EFWK0029",this.getClass().getName()+ ".ETF_SPcommonLog():"+e.toString() );
        LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,e);
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

    try{
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

      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"WEB Timer : " + CommonUtil.Str2Int(tpsvcinfo.getTx_timer()));
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"CUR Timer : " + (endsec-startsec) );
      if( CommonUtil.Str2Int(tpsvcinfo.getTx_timer())<=(endsec-startsec) )
        ETF_SPerror("EFWK0025",this.getClass().getName()+ ".ETF_SPwebtxtimer():Transaction-timeout error");
    }
    catch(Exception ex){
      ETF_SPerror("EFWK0024",this.getClass().getName()+ ".ETF_SPwebtxtimer():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
      return ;
    }

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
    try{
      this.eplevent.setTPSVCINFO(this.tpsvcinfo);
      this.eplevent.setCommon(this.commonDTO);
      //////////////////////////////////////////////////////////////////////////
      // 각 필드에 대한 정보를 재셋팅한다.
      //////////////////////////////////////////////////////////////////////////
    }
    catch(Exception ex){
      ETF_SPerror("EFWK0026",this.getClass().getName()+ ".ETF_SPmovepacket():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
      return ;
    }
    return;
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