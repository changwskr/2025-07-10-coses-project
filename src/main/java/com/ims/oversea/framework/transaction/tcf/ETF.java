package com.ims.oversea.framework.transaction.tcf;

import java.rmi.*;
import java.sql.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.io.*;
import javax.ejb.*;
import javax.naming.*;
import javax.transaction.*;

import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.foundation.utility.CommonUtil;
import com.ims.oversea.foundation.utility.CommonUtil;
import com.ims.oversea.eplatonframework.business.helper.EJBUtils;
import com.ims.oversea.framework.transaction.dao.TransactionControlDAO;
import com.ims.oversea.foundation.logej.LOGEJ;
import com.ims.oversea.framework.transaction.tpmutil.*;

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

public class ETF implements IETF {
  private static ETF instance;
  private String transaction_type=TCFConstants.CONTAINER_TRANSACTION_MSG;
  private EPlatonEvent eplevent;
  private EPlatonCommonDTO commonDTO;
  private TPSVCINFODTO tpsvcinfoDTO;
  private int giTXInfoflag = TCFConstants.GLOABL_TX_INITIAL_MODE;
  private UserTransaction tx;
  private SessionContext ctx;

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

  /**
   * 생성자함수
   * @트랜잭션타입은 크게 usertransaction 과 container type을 지원한다
   *  여기서 STF 클래스는 usertransaction을 지원하며 만약 container type으로
   *  하고 싶다면 생성자함수에서 "container" 타입임을 명시해서 stf을 생성해야된다.
   * @param transaction_type
   */
  public ETF(String transaction_type,int tx_start_info,SessionContext ctx){
    this.transaction_type = transaction_type;
    this.giTXInfoflag = tx_start_info;
    this.ctx = ctx;
  }

  public ETF(String transaction_type,UserTransaction tx,int tx_start_info){
    this.transaction_type = transaction_type;
    this.tx = tx;
    this.giTXInfoflag = tx_start_info;
  }

  public EPlatonEvent getEPlatonEvent(){
    return this.eplevent;
  }

  public EPlatonEvent execute(EPlatonEvent pevent){
    try{
      eplevent = pevent;

      LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,":*** Biz return errorcode *** [" + pevent.getTPSVCINFODTO().getErrorcode() +"]"  );

      LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"=================================[ETF_SPinit] start");
      ETF_SPinit();
      LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"=================================[ETF_SPinit] end");

      LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"=================================[ETF_SPmiddle] start");
      ETF_SPmiddle();
      LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"=================================[ETF_SPmiddle] end");

      LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"=================================[ETF_SPend] start");

      ETF_SPend();
      LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"=================================[ETF_SPend] end");

    }
    catch(Exception ex){
      ETF_SPerror(TCFConstantErrcode.EETF001,TCFConstantErrcode.EETF001_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
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
      tpsvcinfoDTO = (TPSVCINFODTO)eplevent.getTPSVCINFODTO();
      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      ETF_SPerror(TCFConstantErrcode.EETF002,TCFConstantErrcode.EETF002_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
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
      LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"Check Transaction Timeout between WAF and EJB Tier ::");
      //ETF_SPwebtxtimer();
      LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"ending : [" + tpsvcinfoDTO.getTx_timer() + "]"  );

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
      LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"Set TPMSVCINFO ::");
      ETF_SPsettpmsvcinfo();

      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      ETF_SPerror(TCFConstantErrcode.EETF003,TCFConstantErrcode.EETF003_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
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
       * 데이타베이스에 남기는 INPUT/OUTPUT 정보는 에러인경우에는 남기는 것이 어렵다.
       * 왜냐하면 USERTRANSACTION,CONTAINER 두가지 타입에 있어서 rollback()을 하는것이
       *  기본이므로 만약 에러발생시 여기서 DB 작업시 예외를 만들어 낼것이다.
       * 그러므로 에러가 발생시는 입력/출력 패킷을 남기지 않고 대신 파일로 남겨주는 것으로
       * 한다.
       **************************************************************************/
      if( this.eplevent.getTPSVCINFODTO().getErrorcode().charAt(0) != 'E' ){

        LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"==================[ETF_SPdbOutLog() START]");
        if( !ETF_SPdbOutLog(eplevent) ){
          LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"ETF_SPdbOutLog() error");
        }
        else{
          LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"ETF_SPdbOutLog() success");
        }
        LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"==================[ETF_SPdbOutLog() END] (true)");

      }


      /***************************************************************************
       * 트랜잭션에대한 로깅정보를 관리한다.
       **************************************************************************/
      LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"==================[ETF_SPcommonLog START]");

      if( !ETF_SPcommonLog() ){
        LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"ETF_SPcommonLog() error");
      }
      else{
        LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"ETF_SPcommonLog() success");
      }
      LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"==================[ETF_SPcommonLog() END] (true)");

      /***************************************************************************
       * 트랜잭션에 대한 Commit / Rollback 을 관리한다
       * 현재 UserTransaction을 걸기위한 Tx Data Source 부분을 STF,ETF부분에서사용할수
       * 있는 구조를 설계해야 된다.
       * 일단은 구조만 가지고 간다. (향후변경)
       **************************************************************************/
      if( giTXInfoflag == TCFConstants.GLOABL_TX_ALREADY_MODE )
      {
        LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"==================[TProllback/TPcommit START]");
        LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"Business Error Code : [" + tpsvcinfoDTO.getErrorcode() + "]");

        if( (! TCFConstants.USER_TRANSACTION_MSG.equals(transaction_type)) )
        {
          /*********************************************************************
           * 트랜잭션 타입이 container 타입인경우의 트랜잭션을 완료하기 위한 로직
           *******************************7**************************************/
          switch( tpsvcinfoDTO.getErrorcode().charAt(0) )
          {
            case 'E' :
              LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"Call - container TProllback()");
              if( TPMSutil.getInstance().TProllback(ctx,eplevent) )
                LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"TProllback-success");
              else{
                LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"TProllback call error ctx-" + ctx);
                ETF_SPerror(TCFConstantErrcode.EETF005,TCFConstantErrcode.EETF005_MSG);
              }
              break;
            case 'I' :
              LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"Call - TPcommit()");
              if( TPMSutil.getInstance().TPcommit() )
                LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"TPcommit-success");
              else{
                LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"TPcommit-error");
                ETF_SPerror(TCFConstantErrcode.EETF006,TCFConstantErrcode.EETF006_MSG);
              }
              break;
            default :
              LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"DEFAULT Call - TProllback() - default");
              if( TPMSutil.getInstance().TProllback(ctx) )
                LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"TProllback-success");
              else{
                LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"TProllback-error");
                ETF_SPerror(TCFConstantErrcode.EETF005,TCFConstantErrcode.EETF005_MSG);
              }
            break;
          }

        }
        else{
          /*********************************************************************
           * 트랜잭션 타입이 usertransaction 타입인경우의 트랜잭션을 완료하기 위한 로직
           *********************************************************************/
          switch( tpsvcinfoDTO.getErrorcode().charAt(0) )
          {
            case 'E' :
              LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"Call - TProllback()");
              if( TPMSutil.getInstance().TProllback(tx) )
                LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"TProllback-success");
              else{
                LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"TProllback-error");
                ETF_SPerror(TCFConstantErrcode.EETF005,TCFConstantErrcode.EETF005_MSG);
              }
              break;
            case 'I' :
              LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"Call - TPcommit()");
              if( TPMSutil.getInstance().TPcommit(tx) )
                LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"TPcommit-success");
              else{
                LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"TPcommit-error");
                ETF_SPerror(TCFConstantErrcode.EETF006,TCFConstantErrcode.EETF006_MSG);
              }
              break;
            default :
              LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"Call - TProllback() - default");
              if( TPMSutil.getInstance().TProllback(tx) )
                LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"TProllback-success-default");
              else{
                LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"TProllback-error-default");
                ETF_SPerror(TCFConstantErrcode.EETF005,TCFConstantErrcode.EETF005_MSG);
              }
            break;
          }
        }
        LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"==================[TProllback/TPcommit END]-("+this.giTXInfoflag +")");

      }

      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      ETF_SPerror(TCFConstantErrcode.EETF004,TCFConstantErrcode.EETF004_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
      return false;
    }
  }

  private boolean ETF_SPdbOutLog(EPlatonEvent event) {
    try{
      TransactionControlDAO dao = new  TransactionControlDAO();
      if( !dao.DB_INSERToutlog(event)){
        ETF_SPerror(TCFConstantErrcode.EETF007,TCFConstantErrcode.EETF007_MSG);
        return false;
      }
    }
    catch(Exception ex){
      ex.printStackTrace();
      ETF_SPerror(TCFConstantErrcode.EETF008,TCFConstantErrcode.EETF008_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
      return false;
    }
    return true;
  }

  private boolean ETF_SPcommonLog() {
    String LOGFILENAME = null;
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      LOGFILENAME = TCFConstants.OUTPUT_LOGFILENAME
                    + CommonUtil.GetHostName() + "."
                    + eplevent.getTPSVCINFODTO().getSystem_name()  + "." + "out" + "." + CommonUtil.GetSysDate();
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
        ETF_SPerror(TCFConstantErrcode.EETF009,TCFConstantErrcode.EETF009_MSG);
        LOGEJ.getInstance().eprintf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,e);
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
        ETF_SPerror(TCFConstantErrcode.EETF010,TCFConstantErrcode.EETF010_MSG);
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
    String psStartTimer=tpsvcinfoDTO.getWeb_intime();
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

      LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"WEB Timer : " + CommonUtil.Str2Int(tpsvcinfoDTO.getTx_timer()));
      LOGEJ.getInstance().printf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,"CUR Timer : " + (endsec-startsec) );
      if( (CommonUtil.Str2Int(tpsvcinfoDTO.getTx_timer())-TCFConstants.WEB_TRANSACTION_MINUS_SECONDS)<=(endsec-startsec) )
        ETF_SPerror(TCFConstantErrcode.EETF012,TCFConstantErrcode.EETF012_MSG);
    }
    catch(Exception ex){
      ETF_SPerror(TCFConstantErrcode.EETF011,TCFConstantErrcode.EETF011_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
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
    tpsvcinfoDTO.setSystemOutTime(commonDTO.getSystemOutTime());
    //tpfq : 클라이언트에서 셋팅
    //operation_name : 클라이언트에서 셋팅
    //action_name : 클라이언트에서 셋팅

    return;
  }

  private void ETF_SPmovepacket()
  {
    try{
      this.eplevent.setTPSVCINFO(this.tpsvcinfoDTO);
      this.eplevent.setCommon(this.commonDTO);
      //////////////////////////////////////////////////////////////////////////
      // 각 필드에 대한 정보를 재셋팅한다.
      //////////////////////////////////////////////////////////////////////////
    }
    catch(Exception ex){
      ETF_SPerror(TCFConstantErrcode.EETF001,TCFConstantErrcode.EETF001_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.ETF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
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