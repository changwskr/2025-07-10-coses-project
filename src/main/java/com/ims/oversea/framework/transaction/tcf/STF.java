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

import com.chb.coses.common.business.constants.*;
import com.chb.coses.common.business.facade.*;
import com.chb.coses.common.transfer.*;
import com.chb.coses.user.business.facade.*;
import com.chb.coses.user.transfer.UserCDTO;
import com.chb.coses.reference.business.facade.*;
import com.chb.coses.foundation.log.*;
import com.chb.coses.foundation.base.*;
import com.chb.coses.foundation.config.*;
import com.chb.coses.foundation.jndi.*;
import com.chb.coses.foundation.utility.*;

import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.framework.transaction.tpmutil.TPMSutil;
import com.ims.oversea.foundation.utility.CommonUtil;
import com.ims.oversea.foundation.utility.CommonUtil;
import com.ims.oversea.eplatonframework.business.helper.EJBUtils;
import com.ims.oversea.framework.transaction.dao.TransactionControlDAO;
import com.ims.oversea.foundation.logej.LOGEJ;
import com.ims.oversea.framework.transaction.config.TCFConfig;
import com.ims.oversea.framework.transaction.blocking.TXBLXMLconfig;
import com.ims.oversea.foundation.utility.FILEapi;
import com.ims.oversea.framework.transaction.constant.TCFConstants;
import com.ims.oversea.framework.transaction.constant.TCFConstantErrcode;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 * STF
 * 트랜잭션의 시작정보를 관리한다.
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
public class STF implements ISTF {
  private static STF instance;
  private String transaction_type=TCFConstants.CONTAINER_TRANSACTION_MSG;
  private EPlatonEvent eplevent;
  private EPlatonCommonDTO commonDTO;
  private TPSVCINFODTO tpsvcinfoDTO;
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
  private SessionContext ctx;
  private int giTXInfoflag = TCFConstants.GLOABL_TX_INITIAL_MODE;
  public static HashMap hm_system_log_level;


  /**
   * STF 인스턴스를 반환하는 함수
   * @return
   */
  public static synchronized STF getInstance() {
    if (instance == null) {
      try{
        instance = new STF();
        }catch(Exception igex){}
    }
    return instance;
  }

  /**
   * 생성자 함수
   */
  public STF(){
  }

  /**
   * 생성자함수
   * @트랜잭션타입은 크게 usertransaction 과 container type을 지원한다
   *  여기서 STF 클래스는 usertransaction을 지원하며 만약 container type으로
   *  하고 싶다면 생성자함수에서 "container" 타입임을 명시해서 stf을 생성해야된다.
   * @param transaction_type
   */
  public STF(String transaction_type,SessionContext ctx){
    this.transaction_type = transaction_type;
    this.ctx = ctx;
  }

  /**
   * 생성자함수
   * @param tx : 사용자 트랜잭션 정의
   */
  public STF(UserTransaction tx,String transaction_type,SessionContext ctx){
    this.transaction_type = transaction_type;
    this.tx = tx;
    this.ctx = ctx;
  }

  /**
   * EPlatonEvent 객체를 반환하는 함수
   * @return
   */
  public EPlatonEvent getEPlatonEvent(){
    return eplevent;
  }


  /**
   * 실행모듈
   * @param pevent
   * @return
   */
  public EPlatonEvent execute(EPlatonEvent pevent){

    try{
      eplevent = pevent;

      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)pevent,"==================[STF_SPinit] start");
      /*
       * 기본적인 정보를 셋팅하는 함수
       * 에러코드를 IZZ000으로 맞춘다
       * 트랜잭션의 시작을 알리기위한 플래그를 셋팅한다
       * 시스템일자를 셋팅한다
       * 시스템시간을 셋팅한다
       * 영업일자를 셋팅한다.
       * 통화코드를 셋팅한다.
       */
      STF_SPinit();


      /*
       * 에러가 발생하지 않은 경우만 로직을 진행시킨다
       */
      switch ( tpsvcinfoDTO.getErrorcode().charAt(0) )
      {
        case 'E' :
          /* 에러가 난경우 STF의 마지막 로직을 가져온다
           */
          LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)pevent,"STF_SPinit error");
          LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)pevent,"==================[STF_SPinit] end");
          LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)pevent,"==================[STF_SPend] start");
          STF_SPend();
          LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)pevent,"==================[STF_SPend] end");
          return eplevent;
        case 'I' :
          /* 정상인경우 다음 처리를 위한 준비를 한다
           */
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)pevent,"STF_SPinit success");
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)pevent,"==================[STF_SPinit] end");
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)pevent,"==================[STF_SPmiddle] start");
          STF_SPmiddle();
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)pevent,"==================[STF_SPmiddle] end");
          break;
      }
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)pevent,"==================[STF_SPend] start");


      /*
       * 시작트랜잭션의 마무리를 담당한다
       */
      STF_SPend();
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)pevent,"==================[STF_SPend] end");
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror(TCFConstantErrcode.ESTF003,TCFConstantErrcode.ESTF003_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.TCF_LOG_LEVEL,(EPlatonEvent)pevent,ex);
      LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)pevent,"STF.execute:EXCEPTION:"+ex.toString());
    }

    return eplevent ;
  }


  /**
   * 기본적인 정보를 셋팅하는 함수
   * 에러코드를 IZZ000으로 맞춘다
   * 트랜잭션의 시작을 알리기위한 플래그를 셋팅한다
   * 시스템일자를 셋팅한다
   * 시스템시간을 셋팅한다
   * 영업일자를 셋팅한다.
   * 통화코드를 셋팅한다.
   * @return
   */
  public boolean STF_SPinit()
  {
    try{

      commonDTO = (EPlatonCommonDTO)eplevent.getCommon();
      tpsvcinfoDTO = eplevent.getTPSVCINFODTO();
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
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"ErrorCode set :: IZZ000");
      tpsvcinfoDTO.setErrorcode(TCFConstants.SUCCESS_ERRCODE);

      /*************************************************************************
       * 트랜잭션의 시작정보를 알기위한 FLAG SET.
       * 1 - already transaction start
       * 0 - transaction not start
       ************************************************************************/
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"gitxInfoflag set :: 0");
      giTXInfoflag = TCFConstants.GLOABL_TX_INITIAL_MODE;

      /*************************************************************************
       * 시스탬날짜을 설정한다.
       ************************************************************************/
      /*
      TimeProcess timeProcess = TimeProcess.getInstance();
      String systemDateAndTime = timeProcess.getSystemDate(commonDTO.getTimeZone());
      commonDTO.setSystemDate(systemDateAndTime.substring(0, 8));
      */
      String systemDateAndTime = CommonUtil.GetSysDate();
      commonDTO.setSystemDate(systemDateAndTime.substring(0, 8));
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"System Date set :: "+systemDateAndTime.substring(0, 8));

      /*************************************************************************
       * 시스탬시간을 설정한다.
       ************************************************************************/
      //commonDTO.setSystemInTime(systemDateAndTime.substring(8));
      systemDateAndTime = CommonUtil.GetSysTime();
      commonDTO.setSystemInTime(systemDateAndTime.substring(0,8));
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"System Time set :: "+systemDateAndTime.substring(0,8));

      /*************************************************************************
       * Base Currency
       ************************************************************************/
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Base Currency set :: 11 ");
      baseCurrency = commonManagementSB.getBaseCurrency(commonDTO.getBankCode());
      commonDTO.setBaseCurrency(baseCurrency);

      /*************************************************************************
       * 영업일자을 설정한다.
       ************************************************************************/
      /*
      if( (businessDate=STF_SPgetbusinessdate()) == null ){
        STF_SPerror("EFWK0016",this.getClass().getName()+ ".STF_SPinit()::get business date error");
        LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"error to get businessDate");
      }
      else
        commonDTO.setBusinessDate(businessDate);
      */

      if( (businessDate = commonManagementSB.getBusinessDate(commonDTO.getBankCode())) == null ){
        STF_SPerror(TCFConstantErrcode.ESTF001,TCFConstantErrcode.ESTF001_MSG);
        LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"STF_SPinit()::get business date error");
      }
      else
        commonDTO.setBusinessDate(businessDate);

      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Business Date :: "+businessDate);

      return true;
    }
    catch(Exception ex){
      STF_SPerror(TCFConstantErrcode.ESTF002,TCFConstantErrcode.ESTF002_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.TCF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
      return false;
    }
  }


  /**
   * 현재 클라이언트에서나 서버에서의 트랜잭션의 시작정보를 관리한다.
   * 만약 트랜잭션을 시작 했다면 기존 트랜잭션에 합류시키며 그렇지 않은 경우에는
   * 트랜잭션을 새로이 시작한다.
   *
   *
   * @return
   */
  public boolean STF_SPmiddle()
  {
    try{

      /*************************************************************************
       * 클라이언트에서 요청한 트랜잭션 정보를 구한다
       ************************************************************************/
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Get Transaction Level::");

      int tpinfomode = 99;

      /*************************************************************************
       * container의 경우에는 트랜잭션에 상관없는 구조로 한다.
       * tpinfomode
       *   - 6 트랜잭션 모드가 없는 경우 - No Transaction
       *   - 0 이미 트랜잭션 모드가 있는 경우 - Active
       *   - -1 에러가 발생한 경우
       ************************************************************************/
      if( ! TCFConstants.USER_TRANSACTION_MSG.equals(transaction_type) ){
        tpinfomode = TPMSutil.getInstance().TPinfo();
      }
      else{
        tpinfomode = TPMSutil.getInstance().TPinfo(tx);
      }

      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"#################################");
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Transaction Level : " + tpinfomode );
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Transaction type : " + this.transaction_type  );
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"#################################");

      switch (tpinfomode)
      {
        case -1 :
          STF_SPerror(TCFConstantErrcode.ESTF004,TCFConstantErrcode.ESTF004_MSG);
          break;
        case 0 : // already transactio active
          /*********************************************************************
           * 이미 트랜잭션을 시작한 상태이므로 트랜잭션을 시작하지 않고 현재의 트랜잭션
           * 을 유지해 준다
           * 주의 : CMP인경우에는 TPinfo()메소드는 항상 0을 반환할 것이다(세션빈의 메소드가 required
           * 라면) 그러므로 각 세션별로 각 빈별로 commit/rollback을 관리해주어도 된다.
           *
           ********************************************************************/
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Transaction Level       : [Already Transaction started]" );
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Transaction TPinfo mode : [" + tpinfomode +"] timer" + tpsvcinfoDTO.getTx_timer() );

          //최초클라이언트에서 올라온 경우에는 타임아웃을 정해준다.
          //tpfq가 100이 아닌 최초의 ejb 서버만 트랜잭션의 commit/rollback을 관리한다.
          //container transaction processing 경우에는 method 시작과 함께 트랜잭션이
          //시작할 것이므로 begin을 여기서 잡고 tpfq의 정보를 기준으로해서
          //타임아웃값을 준다.
          if( !TCFConstants.USER_TRANSACTION_MSG.equals(transaction_type) )
          {
            if( !tpsvcinfoDTO.getTpfq().equals(TCFConstants.SERVER_SERVER_INTERCHANGE_LOC) ){

              if( TPMSutil.getInstance().TPbegin(tpsvcinfoDTO.getTx_timer()) ){
                setSTF_SPtxinfo(1);
                LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPbegin() 성공" );
              }
              else{
                STF_SPerror(TCFConstantErrcode.ESTF005,TCFConstantErrcode.ESTF005_MSG);
                LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPbegin() 실패" );
              }
            }
          }
          break;
        case 1 : //Marked Rollback
          STF_SPerror(TCFConstantErrcode.ESTF006,TCFConstantErrcode.ESTF006_MSG);
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPinfo() Marked Rollback " );
          break;
        case 4 : //Rolledback
          STF_SPerror(TCFConstantErrcode.ESTF030,TCFConstantErrcode.ESTF030_MSG);
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPinfo() already Rolledback " );
          break;
        case 6 :
          /*********************************************************************
           * 클라이언트 화면에서 올라온 Transaction Timer (seconds) 값을 가지고
           * UserTransaction을 시작한다.
           ********************************************************************/
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Transaction Level       : [Not started]" );
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Transaction TPinfo mode : [" + tpinfomode +"]" );

          if( !TCFConstants.USER_TRANSACTION_MSG.equals(transaction_type) )
          {
            LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Transaction type      : [container]" );
            if( TPMSutil.getInstance().TPbegin(tpsvcinfoDTO.getTx_timer()) ){
              setSTF_SPtxinfo(1);
              LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPbegin() 성공" );
            }
            else{
              STF_SPerror(TCFConstantErrcode.ESTF007,TCFConstantErrcode.ESTF007_MSG);
              LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPbegin() 실패" );
            }
          }
          else
          {
            LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Transaction type ###  : [bean]" );
            if( TPMSutil.getInstance().TPbegin(tx,CommonUtil.Str2Int(tpsvcinfoDTO.getTx_timer()))){
              LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPbegin() 성공" );
              setSTF_SPtxinfo(1);
            }
            else{
              STF_SPerror(TCFConstantErrcode.ESTF007,TCFConstantErrcode.ESTF007_MSG);
              LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPbegin() 실패" );
            }
          }
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Transaction TPbegin mode       : [" + this.giTXInfoflag + "]" );
          break;
        default:
          STF_SPerror(TCFConstantErrcode.ESTF008,TCFConstantErrcode.ESTF008_MSG);
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPinfo() Marked Nothing " );
          break;
      }

      if( isErr() ){
        return false;
      }

      /*************************************************************************
       *  전체 트랜잭션 번호 관리
       * ***********************************************************************
       * SEQUENCE를 채번한후 거래별 트랜잭션 번호를 셋팅한다.
       * 이 작업은 로깅작업을 최초 TPSrecv()시에 트랜잭션에 대한 번호를 채번하는 것으로 한다.
       * 참고사항
       * 향후 트랜잭션에 채번은 TPSrecv() TPM 모듈에서 실시한다
       * 이는 로깅관련 작업을 위해..
       * 이로직은 전체 트랜잭션번호외의 타 레퍼런스를 구하기 위한 것으로 바꾼다.
       * 단지 여기서는 TPSrecv모듈에서 구한 시퀀스를 확인하고 TPSVCINFODTO 객체 셋팅하는 것
       * 으로 한다.
       ************************************************************************/
      if( ! tpsvcinfoDTO.getTpfq().equals(TCFConstants.SERVER_SERVER_INTERCHANGE_LOC) )
        tpsvcinfoDTO.setOrgseq(tpsvcinfoDTO.getHostseq());
      commonDTO.setTransactionNo(tpsvcinfoDTO.getHostseq() );
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"HOSTSEQ:" + tpsvcinfoDTO.getHostseq()  + " ORGSEQ:"+tpsvcinfoDTO.getOrgseq() );

      if( isErr() ){
        return false;
      }

      /*************************************************************************
       * 마감전후 구분 필드를 세운다.
       *************************************************************************
       * = 시간상 관리
       * 자동화기기 마감전후를 구분하여 마감구분 필드를 세운다.
       * 토요일 : 13시 * 평  일 : 16시30
       * 향후 이 로직을 위한 추가 로직을 구성한다.
       *************************************************************************
       * = 거래별 제어관리
       * 시스템파라미터의 상태코드가 10인경우 : 모든 트랜잭션을 허용
       * 시스템파라미터의 상태코드가 20인경우 : 모든 트랜잭션을 금지
       * 시스템파라미터의 상태코드가 50인경우 : ATM/IBANK 관련된 거래만 허용
       *
       ***********************************************************************/
      LOGEJ.getInstance().print(1,(EPlatonEvent)eplevent,"Set EOD Field ::");
      STF_SPeod();
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"[" + tpsvcinfoDTO.getTrclass() + "]"  );
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
       LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Set Control Transaction ::");
       STF_SPtxblocking();
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
       LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Check Transaction Timeout between WAF and EJB Tier ::");

       //STF_SPwebtxtimer();
       if( isErr() ){
         LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"error"  );
         return false;
       }

       /*************************************************************************
        * 클라이언트의 호출정보를 보여준다. (TPFQ)
        *************************************************************************
        * 100 - 온라인 서버 - 서버 호출
        * 200 - WAF 클라이언트 - 서버
        * 300 - NATIVE 클라이언트 - 서버  호출
        * 400 - ATM - 서버 호출
        * 500 - INTERNET BANK - 서버 호출
        * 600 - BATCH - 서버 호출
        ************************************************************************/
       LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Check Call Transaction Location :: ");
       switch( CommonUtil.Str2Int(tpsvcinfoDTO.getTpfq()) )
       {
         case 100 : /** server-server interchange */
           LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPFQ : 100 ");
           break;
         case 200 : /** waf clinet-server interchange*/
           LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPFQ : 200 ");
           break;
         case 300 : /** native-server interchange*/
           LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPFQ : 300 ");
           break;
         case 400 : /** atm client-server interchange*/
           LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPFQ : 400 ");
           break;
         case 500 : /** internetbank-server interchange*/
           LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPFQ : 500 ");
           break;
         case 600 : /** batch-server interchange*/
           LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"TPFQ : 600 ");
           break;
         default :
           LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"TPFQ : not valid ");
           STF_SPerror(TCFConstantErrcode.ESTF009,TCFConstantErrcode.ESTF009_MSG);
         break;
       }
       LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"클라이언트의 호출위치 정보를 가지고 온다 : [" + tpsvcinfoDTO.getTpfq() + "]"  );
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
       LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Set TPMSVCINFO ::");
       STF_SPsettpmsvcinfo();


       return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror(TCFConstantErrcode.ESTF010,TCFConstantErrcode.ESTF010_MSG);
      LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,".STF_SPmiddle():"+ex.toString());
      LOGEJ.getInstance().eprintf(TCFConstants.TCF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);

      return false;
    }
  }


  public boolean STF_SPend()
  {
    try{

      /*************************************************************************
       * 기본정보를 재셋팅한다.
       ************************************************************************/
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Set SystemOuttime ::");
      commonDTO.setSystemOutTime(CommonUtil.GetSysTime());

      /*************************************************************************
       * TPMSVCINFO 정보를 재셋팅한다.
       ************************************************************************/
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Set TPMSVCINFO ::");
      tpsvcinfoDTO.setSystemInTime(commonDTO.getSystemInTime());

      /*************************************************************************
       * 패킷정보를 재셋팅한다.
       ************************************************************************/
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"Move Packet Rebuild ::");
      STF_SPmovepacket();

      /*************************************************************************
       * 공통정보 로깅작을 실시한다.
       ************************************************************************/
      /***************************************************************************
       * 트랜잭션에대한 로깅정보 데이타베이스에 저장한다.
       * 데이타베이스에 남기는 INPUT/OUTPUT 정보는 에러인경우에는 남기는 것이 어렵다.
       * 왜냐하면 USERTRANSACTION,CONTAINER 두가지 타입에 있어서 rollback()을 하는것이
       *  기본이므로 만약 에러발생시 여기서 DB 작업시 예외를 만들어 낼것이다.
       * 그러므로 에러가 발생시는 입력/출력 패킷을 남기지 않고 대신 파일로 남겨주는 것으로
       * 한다.
       **************************************************************************/
      if( this.eplevent.getTPSVCINFODTO().getErrorcode().charAt(0) != 'E' ){

        LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"==================[STF_SPdbInLog() START]");
        if( !STF_SPdbInLog(eplevent) ){
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"STF_SPdbInLog() error");
        }
        else{
          LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"STF_SPdbInLog() success");
        }
        LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"==================[STF_SPdbInLog() END] (true)");

      }

      /*************************************************************************
       * 공통정보 로깅작을 실시한다.
       ************************************************************************/
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"==================[STF_SPcommonLog START]");
      if( !STF_SPcommonLog() ){
        LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)eplevent,"STF_SPcommonLog() error");
      }
      else{
        LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"STF_SPcommonLog() success");
      }
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"==================[STF_SPcommonLog() END] (true)");

      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror(TCFConstantErrcode.ESTF011,TCFConstantErrcode.ESTF011_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.TCF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
      return false;
    }
  }

  private boolean isErr(){
    switch (tpsvcinfoDTO.getErrorcode().charAt(0))
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
        STF_SPerror(TCFConstantErrcode.ESTF012,TCFConstantErrcode.ESTF012_MSG);
        return true;
    }
  }


  /**
   *
   *
drop table TRANSACTION_UPDOWN;

CREATE TABLE TRANSACTION_UPDOWN (
         terminalID 			VARCHAR2(100),
         terminalType 		VARCHAR2(100),
         xmlSeq 					VARCHAR2(100),
         bankCode 				VARCHAR2(100),
         branchCode 			VARCHAR2(100),
         glPostBranchCode VARCHAR2(100),
         channelType 			VARCHAR2(100),
         userID 					VARCHAR2(100),
         eventNo 					VARCHAR2(100),
         nation 					VARCHAR2(100),
         regionCode 			VARCHAR2(100),
         timeZone 				VARCHAR2(100),
         fxRateCount 			VARCHAR2(100),
         reqName 					VARCHAR2(100),
         systemDate 			VARCHAR2(100),
         businessDate 		VARCHAR2(100),
         transactionNo 		VARCHAR2(100),
         baseCurrency 		VARCHAR2(100),
         multiPL 					VARCHAR2(100),
         userLevel 				VARCHAR2(100),
         IPAddress 				VARCHAR2(100),
   req_name 				VARCHAR2(100),
   system_name 			VARCHAR2(100),
          operation_name 	VARCHAR2(100),
         operation_method VARCHAR2(100),
         cdto_name 				VARCHAR2(100),
         action_name 			VARCHAR2(100),
         hostseq 					VARCHAR2(100),
         orgseq 					VARCHAR2(100),
         tx_timer 				VARCHAR2(100),
         tpfq 						VARCHAR2(100),
         errorcode 				VARCHAR2(100),
         trclass 					VARCHAR2(100),
         web_timeout 			VARCHAR2(100),
         web_intime 			VARCHAR2(100),
         web_outtime 			VARCHAR2(100),
         systemInTime 		VARCHAR2(100),
         systemOutTime 		VARCHAR2(100),
         system_date 			VARCHAR2(100),
         error_message 		VARCHAR2(100),
         logic_level 			VARCHAR2(100),
         STF_intime 			VARCHAR2(100),
         STF_outtime 			VARCHAR2(100),
         BTF_intime 			VARCHAR2(100),
         BTF_outtime 			VARCHAR2(100),
         ETF_intime 			VARCHAR2(100),
         ETF_outtime 			VARCHAR2(100),
         INPUT_DTO        VARCHAR2(4000),
         OUTPUT_DTO       VARCHAR2(4000),
   CONSTRAINT TRANSACTION_UPDOWN PRIMARY KEY ( hostseq )
   USING INDEX TABLESPACE TSP_CORE_IND )
   TABLESPACE TSP_CORE
;

CREATE SYNONYM TRANSACTION_UPDOWN FOR CORE.TRANSACTION_UPDOWN;
grant select,insert,update,delete on TRANSACTION_UPDOWN to eplaton;

   *
   *
   *
   *
   *
   * @param event
   * @return
   */
  private boolean STF_SPdbInLog(EPlatonEvent event) {
    try{
      TransactionControlDAO dao = new  TransactionControlDAO();
      if( !dao.DB_INSERTinlog(event) ){
        STF_SPerror(TCFConstantErrcode.ESTF013,TCFConstantErrcode.ESTF013_MSG);
        return false;
      }
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror(TCFConstantErrcode.ESTF014,TCFConstantErrcode.ESTF014_MSG);
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
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
      LOGFILENAME = TCFConstants.INPUT_LOGFILENAME  + CommonUtil.GetHostName() + "." +
          eplevent.getTPSVCINFODTO().getSystem_name()  + "." +
          "in" + "." +
          CommonUtil.GetSysDate();
      fos = new FileOutputStream(LOGFILENAME, true);
      ps = new PrintStream(fos);

      ps.println(eplevent.getTPSVCINFODTO().getOperation_name()+"|"+eplevent.getTPSVCINFODTO().getOrgseq()+"-"+ com.chb.coses.foundation.utility.Reflector.objectToString(eplevent));

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
        STF_SPerror(TCFConstantErrcode.ESTF015,TCFConstantErrcode.ESTF015_MSG);
        LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,e);
        return false;
    }
    return true;
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
  private int STF_SPtxctl()
  {
    /**********************************************************************
     * 일단 데이타베이스의 테이블로 관리 할 것이다
     * 거래코드별, 단말별, 온라인/배치, 텔러별 나누어서
     * 트랜잭션 제어정보를 관리 할 것임
     **********************************************************************/
    try{

      /*
       * 모든 온라인거래에 대한 제어 여부를 판별한다.
       */
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"모든 온라인거래에 대한 제어 여부를 판별한다");

      /*
       * 모든 배치거래에 대한 제어 여부를 판별한다
       */
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"모든 배치거래에 대한 제어 여부를 판별한다");

      /*
       * ATM 거래에 대한 제어여부를 관리한다
       */
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"ATM 거래에 대한 제어여부를 관리한다");

      /*
       * 인터넷뱅 거래에 대한 제어여부를 관리한다
       */
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"인터넷뱅 거래에 대한 제어여부를 관리한다");

      /*
       * 거래코드에 대한 제어여부를 관리한다
       * 10개의 거래코드 버럭을 지정
       * 1000 - 1002
       * 1010 - 1023
       * 1099 - 1100
       * ....
       */
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"거래코드에 대한 제어여부를 관리한다");

      /*
       * 뱅크코드에 대한 텔러별 제어 정보를 관리한다
       */

      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"뱅크코드에 대한 텔러별 제어 정보를 관리한다");

      return 0;
    }
    catch(Exception ex){
      STF_SPerror(TCFConstantErrcode.ESTF016,TCFConstantErrcode.ESTF016_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
      return -1;
    }
  }

  private void STF_SPtxblocking()
  {
    try{
      if( STF_SPbankcodeblock() )
        return ;
      if (STF_SPtxcodeblock() )
        return ;
      if (STF_SPsystemblock() )
        return;
      if ( STF_SPtpfqblock() )
        return ;
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror(TCFConstantErrcode.ESTF017,TCFConstantErrcode.ESTF017_MSG);
      LOGEJ.getInstance().eprintf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
    }
  }


  private boolean STF_SPtxcodeblock()
  {
    String configFileName = null;
    String block_txcode_tag = "block-txcode";
    String count_tag = "count";
    String mode_tag = "mode";
    String start_tag = "s";
    String mode_value = null;
    String count_value = null;
    int icount_value = 0;

    try{
      int ctxcode = CommonUtil.atoi( commonDTO.getEventNo() );
      configFileName = TCFConfig.getInstance().getElement(TCFConstants.BIZDELEGATE_TAG).getTextTrim();
      mode_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(block_txcode_tag).getChildTextTrim(mode_tag);
      count_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(block_txcode_tag).getChildTextTrim(count_tag);
      if( mode_value != null
          && count_value != null
          && mode_value.equals("on")
          && CommonUtil.CHECKisdigit(count_value,count_value.length())  )
      {
        for( int i = 1 ; i <= CommonUtil.atoi(count_value) ; i++ )
        {
          String tag = start_tag + 1;
          String val = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(block_txcode_tag).getChildTextTrim(tag);
          int sv = CommonUtil.atoi(CommonUtil.catchSTRINGseq(val,1,"-"));
          int ev = CommonUtil.atoi(CommonUtil.catchSTRINGseq(val,2,"-"));
          if( ctxcode >= sv && ctxcode <= ev ){
            STF_SPerror(TCFConstantErrcode.ESTF018,TCFConstantErrcode.ESTF018_MSG);
            LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,this.eplevent,"Transaction Block -- txcode ");
            return true;
          }
        }
      }
      return false;
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror(TCFConstantErrcode.ESTF019,TCFConstantErrcode.ESTF019_MSG);
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
      return false;
    }
  }

  private boolean STF_SPsystemblock()
  {
    String configFileName = null;
    String block_txcode_tag = "block-system";
    String count_tag = "count";
    String mode_tag = "mode";
    String bank_tag = "bank";
    String start_tag = "s";
    String mode_value = null;
    String count_value = null;
    String bank_value = null;
    int icount_value = 0;

    try{
      int cbank_code = CommonUtil.atoi( commonDTO.getBankCode());
      String system = tpsvcinfoDTO.getSystem_name();

      configFileName = TCFConfig.getInstance().getElement(TCFConstants.BIZDELEGATE_TAG).getTextTrim();
      mode_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(block_txcode_tag).getChildTextTrim(mode_tag);
      count_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(block_txcode_tag).getChildTextTrim(count_tag);
      bank_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(block_txcode_tag).getChildTextTrim(bank_tag);

      if ( cbank_code != CommonUtil.atoi(bank_value) )
        return false;

      if( mode_value != null
          && count_value != null
          && mode_value.equals("on")
          && CommonUtil.CHECKisdigit(count_value,count_value.length())  )
      {
        for( int i = 1 ; i <= CommonUtil.atoi(count_value) ; i++ )
        {
          String tag = start_tag + 1;
          String val = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(block_txcode_tag).getChildTextTrim(tag);
          if( val.equals(system) ){
            STF_SPerror(TCFConstantErrcode.ESTF020,TCFConstantErrcode.ESTF020_MSG);
            LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,this.eplevent,"Transaction Block -- system ");
            return true;
          }
        }
      }
      return false;
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror(TCFConstantErrcode.ESTF021,TCFConstantErrcode.ESTF021_MSG);
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
      return false;
    }
  }


  private boolean STF_SPbankcodeblock()
  {
    String configFileName = null;
    String block_txcode_tag = "block-bankcode";
    String count_tag = "count";
    String mode_tag = "mode";
    String start_tag = "s";
    String mode_value = null;
    String count_value = null;
    int icount_value = 0;

    try{
      //1.bankcode에대한 거래제여부를 검증한다.
      int cbankcode = CommonUtil.atoi( commonDTO.getEventNo() );

      configFileName = TCFConfig.getInstance().getElement(TCFConstants.BIZDELEGATE_TAG).getTextTrim();
      mode_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(block_txcode_tag).getChildTextTrim(mode_tag);
      count_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(block_txcode_tag).getChildTextTrim(count_tag);
      if( mode_value != null
          && count_value != null
          && mode_value.equals("on")
          && CommonUtil.CHECKisdigit(count_value,count_value.length())  )
      {
        for( int i = 1 ; i <= CommonUtil.atoi(count_value) ; i++ )
        {
          String tag = start_tag + 1;
          String val = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(block_txcode_tag).getChildTextTrim(tag);
          int sv = CommonUtil.atoi(CommonUtil.catchSTRINGseq(val,1,"-"));
          int ev = CommonUtil.atoi(CommonUtil.catchSTRINGseq(val,2,"-"));
          if( cbankcode >= sv && cbankcode <= ev ){
            STF_SPerror(TCFConstantErrcode.ESTF022,TCFConstantErrcode.ESTF022_MSG);
            LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,this.eplevent,"Transaction Block -- Bank ");
            return true;
          }
        }
      }
      return false;
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror(TCFConstantErrcode.ESTF023,TCFConstantErrcode.ESTF023_MSG);
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
      return false;
    }
  }

  private boolean STF_SPtpfqblock()
  {
    String configFileName = null;
    String block_txcode_tag = "block-tpfq";
    String count_tag = "count";
    String mode_tag = "mode";
    String start_tag = "s";
    String mode_value = null;
    String count_value = null;
    int icount_value = 0;

    try{
      //1.tpfq에대한 거래제여부를 검증한다.
      int ctpfq = CommonUtil.atoi( commonDTO.getEventNo() );

      configFileName = TCFConfig.getInstance().getElement(TCFConstants.BIZDELEGATE_TAG).getTextTrim();
      mode_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(block_txcode_tag).getChildTextTrim(mode_tag);
      count_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(block_txcode_tag).getChildTextTrim(count_tag);
      if( mode_value != null
          && count_value != null
          && mode_value.equals("on")
          && CommonUtil.CHECKisdigit(count_value,count_value.length())  )
      {
        for( int i = 1 ; i <= CommonUtil.atoi(count_value) ; i++ )
        {
          String tag = start_tag + 1;
          String val = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(block_txcode_tag).getChildTextTrim(tag);
          int sv = CommonUtil.atoi(CommonUtil.catchSTRINGseq(val,1,"-"));
          int ev = CommonUtil.atoi(CommonUtil.catchSTRINGseq(val,2,"-"));
          if( ctpfq >= sv && ctpfq <= ev ){
            STF_SPerror(TCFConstantErrcode.ESTF024,TCFConstantErrcode.ESTF024_MSG);
            LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,this.eplevent,"Transaction Block -- tpfq ");
            return true;
          }
        }
      }
      return false;
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror(TCFConstantErrcode.ESTF025,TCFConstantErrcode.ESTF025_MSG);
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
      return false;
    }
  }



  /*************************************************************************
   * 마감전후 구분 필드를 세운다.
   *************************************************************************
   * = 시간상 관리의 경우
   * 자동화기기 마감전후를 구분하여 마감구분 필드를 세운다.
   * 토요일 : 13시 * 평  일 : 16시30
   * 향후 이 로직을 위한 추가 로직을 구성한다.
   *************************************************************************
   * = 거래별 제어관리의 경우
   * 시스템파라미터의 상태코드가 10인경우 : 모든 트랜잭션을 허용
   * 시스템파라미터의 상태코드가 20인경우 : 모든 트랜잭션을 금지
   * 시스템파라미터의 상태코드가 50인경우 : ATM/IBANK 관련된 거래만 허용
   *
   ***********************************************************************/
  private void STF_SPeod()
  {
    //임시로 마감전으로 셋팅한다
    try{
      if( (CommonUtil.Str2Int(CommonUtil.GetSysTime().substring(0,6)) >= 170000)  )
        tpsvcinfoDTO.setTrclass("1");
      else
        tpsvcinfoDTO.setTrclass("0");
      return;
    }
    catch(Exception ex){
      STF_SPerror(TCFConstantErrcode.ESTF026,TCFConstantErrcode.ESTF026_MSG);
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
      return ;
    }

  }

  /*************************************************************************
   * 웹컴포넌단 - EJB 서버단과의 TIMEOUT 관리
   * 현재는 단위가 초이지만 향후 미리세컨드로 바꾸는 과정이 필요하다.
   *************************************************************************
   * 이 모듈은 TPSVCINFO의 TXTIMER을 기준으로서 관리한다
   * 즉 웹단에서 시작시간을 기초로 해서 현재시간을 기초로 해서 INTERVAL이 넘어서면
   * 다음 업무단의 로직을 처리하지 않고 바로 에러로 처리
   *
   * 서버큐에서 장시간 대기간 메시지는 리턴하는 기능을 추가한다.
   ************************************************************************/
  private void STF_SPwebtxtimer()
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

      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"WEB Timer : " + CommonUtil.Str2Int(tpsvcinfoDTO.getTx_timer()));
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,"CUR Timer : " + (endsec-startsec) );


      if( (CommonUtil.Str2Int(tpsvcinfoDTO.getTx_timer())-TCFConstants.WEB_TRANSACTION_MINUS_SECONDS)<=(endsec-startsec) )
        STF_SPerror(TCFConstantErrcode.ESTF028,TCFConstantErrcode.ESTF028_MSG);
    }
    catch(Exception ex){
      STF_SPerror(TCFConstantErrcode.ESTF027,TCFConstantErrcode.ESTF027_MSG);
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
      return ;
    }

    return;
  }

  //////////////////////////////////////////////////////////////////////////////
  // TPMSVCINFO 정보는 STF 및 ETF BTF의 중간 중간에 끼워서 시간을 셋팅하면서 진행 해야
  // 된다. 일단 한곳에서 하는 것으로 한다.
  // 변경추가부분이다
  //////////////////////////////////////////////////////////////////////////////
  private void STF_SPsettpmsvcinfo()
  {
    /*
    * call_service_name
    * call_tpm_in_time
    * call_tpm_out_time
    * call_tpm_interval
    * call_tpm_stf_in_time
    * call_tpm_stf_out_time
    * call_tpm_etf_in_time
    * call_tpm_etf_out_time
    * call_tpme_service_interval
    * error_code
    * call_hostseq
    * call_orgseq
    * call_location
    */

    //service_name : 클라이언트에서 셋팅
    tpsvcinfoDTO.setSystem_date(commonDTO.getSystemDate());
    tpsvcinfoDTO.setSystemInTime(commonDTO.getSystemInTime());
    tpsvcinfoDTO.setSystemOutTime(commonDTO.getSystemOutTime());
    //tpfq : 클라이언트에서 셋팅
    //operation_name : 클라이언트에서 셋팅
    //action_name : 클라이언트에서 셋팅


    return;
  }

  private void STF_SPerror(String errorcode,String message)
  {
    switch( tpsvcinfoDTO.getErrorcode().charAt(0) )
    {
      case 'I' :
        tpsvcinfoDTO.setErrorcode(errorcode);
        tpsvcinfoDTO.setError_message(message);
        return;
      case 'E' :
        errorcode = errorcode+"|"+tpsvcinfoDTO.getErrorcode();
        tpsvcinfoDTO.setErrorcode(errorcode);
        tpsvcinfoDTO.setError_message(message);
        return;
    }
  }

  private void STF_SPmovepacket()
  {
    eplevent.setTPSVCINFO(this.tpsvcinfoDTO);
    eplevent.setCommon(this.commonDTO);
    //////////////////////////////////////////////////////////////////////////
    // 각 필드에 대한 정보를 재셋팅한다.
    //////////////////////////////////////////////////////////////////////////

  }

  public String STF_SPgetbusinessdate()
  {
    String aa = null;
    try {
      TransactionControlDAO dao = new  TransactionControlDAO();
      aa = dao.queryForBusinessDate(commonDTO.getBankCode());
      aa = dao.GetBizDate();
    }
    catch(Exception ex){
      STF_SPerror(TCFConstantErrcode.ESTF029,TCFConstantErrcode.ESTF029_MSG);
      LOGEJ.getInstance().printf(TCFConstants.STF_LOG_LEVEL,(EPlatonEvent)eplevent,ex);
      return aa;
    }
    return(aa);
  }


  /**
   1.epllogej.xml 파일정보

   <epllogej-common>
        <print-mode>3</print-mode>
        <error-mode>5</error-mode>
   </epllogej-common>

   <epllogej-deposit>
        <print-mode>3</print-mode>
        <error-mode>5</error-mode>
   </epllogej-deposit>

   * @return
   */

  public static boolean STF_SPmanageloglvel(EPlatonEvent event)
  {
    String configFileName = null;
    String call_operation_tag = null;
    String actionClassName = null;
    String log_system_tag = TCFConstants.LOG_SYSTEM_TAG;
    String print_mode_tag = TCFConstants.PRINT_MODE_TAG;
    String error_mode_tag = TCFConstants.ERROR_MODE_TAG;
    String key_print_mode_name_value = null;
    String key_error_mode_name_value = null;
    String key_print_mode_name = null;
    String key_error_mode_name = null;
    String system_name = event.getTPSVCINFODTO().getSystem_name();

    try{
      //epllogej.xml파일을 읽기위한 기본준비를 한다.
      configFileName = TCFConfig.getInstance().getElement(TCFConstants.BIZDELEGATE_TAG).getTextTrim();
      call_operation_tag = log_system_tag + "-" +  system_name;

      key_print_mode_name_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(call_operation_tag).getChildTextTrim(print_mode_tag);
      key_error_mode_name_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(call_operation_tag).getChildTextTrim(error_mode_tag);

      key_print_mode_name = call_operation_tag + "." + print_mode_tag;
      key_error_mode_name = call_operation_tag + "." + error_mode_tag;

      System.out.println("key_print_mode_name_value " + key_print_mode_name_value + ":" + key_print_mode_name);
      System.out.println("key_error_mode_name_value " + key_error_mode_name_value + ":" + key_error_mode_name);

      if( hm_system_log_level == (HashMap) null )
        hm_system_log_level = new HashMap();

      if( hm_system_log_level.containsKey(key_print_mode_name) ){
        String val = (String)hm_system_log_level.get(key_print_mode_name);
        val = key_print_mode_name_value;
        System.out.println("hash key_print_mode_val " + val );
      }
      else{
        hm_system_log_level.put(key_print_mode_name,key_print_mode_name_value);
      }

      if( hm_system_log_level.containsKey(key_error_mode_name) ){
        String val = (String)hm_system_log_level.get(key_error_mode_name);
        val = key_error_mode_name_value;
        System.out.println("hash key_error_mode_val " + val );
      }
      else{
        hm_system_log_level.put(key_error_mode_name,key_error_mode_name_value);
      }

    }
    catch(Exception ex){
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  public static String STF_SPgetloglvel(String key)
  {
    try{
      if( hm_system_log_level.containsKey(key) ){
        String val = (String)hm_system_log_level.get(key);
        return val;
      }
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return "1";
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