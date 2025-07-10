package com.ims.eplaton.framework.tcf;

// ERRORCODE FORMAT  => ERROR_OFFSET(1) + SYSTEM(3) + ERRORNO(4)
// ERRORCODE MESSAGE => CLASSNAME+"."+METHODNAME + ":" + MESSAGE

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
 * STF
 * 트랜잭션의 시작정보를 관리한다.
 *
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
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
   * @param tx : 사용자 트랜잭션 정의
   */
  public STF(UserTransaction tx){
    this.tx = tx;
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

      LOGEJ.getInstance().printf(1,(EPlatonEvent)pevent,"==================[STF_SPinit] start");
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
      switch ( tpsvcinfo.getErrorcode().charAt(0) )
      {
        case 'E' :
          /* 에러가 난경우 STF의 마지막 로직을 가져온다
           */
          LOGEJ.getInstance().printf(1,(EPlatonEvent)pevent,"STF_SPinit error");
          LOGEJ.getInstance().printf(1,(EPlatonEvent)pevent,"==================[STF_SPinit] end");
          LOGEJ.getInstance().printf(1,(EPlatonEvent)pevent,"==================[STF_SPend] start");
          STF_SPend();
          LOGEJ.getInstance().printf(1,(EPlatonEvent)pevent,"==================[STF_SPend] end");
          return eplevent;
        case 'I' :
          /* 정상인경우 다음 처리를 위한 준비를 한다
           */
          LOGEJ.getInstance().printf(1,(EPlatonEvent)pevent,"STF_SPinit success");
          LOGEJ.getInstance().printf(1,(EPlatonEvent)pevent,"==================[STF_SPinit] end");
          LOGEJ.getInstance().printf(1,(EPlatonEvent)pevent,"==================[STF_SPmiddle] start");
          STF_SPmiddle();
          LOGEJ.getInstance().printf(1,(EPlatonEvent)pevent,"==================[STF_SPmiddle] end");
          break;
      }
      LOGEJ.getInstance().printf(1,(EPlatonEvent)pevent,"==================[STF_SPend] start");


      /*
       * 시작트랜잭션의 마무리를 담당한다
       */
      STF_SPend();
      LOGEJ.getInstance().printf(1,(EPlatonEvent)pevent,"==================[STF_SPend] end");
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror("EFWK0001",this.getClass().getName()+ ".execute():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)pevent,ex);
      LOGEJ.getInstance().printf(1,(EPlatonEvent)pevent,"STF.execute:EXCEPTION:"+ex.toString());
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
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"ErrorCode set :: IZZ000");
      tpsvcinfo.setErrorcode("IZZ000");

      /*************************************************************************
       * 트랜잭션의 시작정보를 알기위한 FLAG SET.
       * 1 - already transaction start
       * 0 - transaction not start
       ************************************************************************/
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"gitxInfoflag set :: 0");
      giTXInfoflag = 0;

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
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"System Date set :: "+systemDateAndTime.substring(0, 8));

      /*************************************************************************
       * 시스탬시간을 설정한다.
       ************************************************************************/
      //commonDTO.setSystemInTime(systemDateAndTime.substring(8));
      systemDateAndTime = CommonUtil.GetSysTime();
      commonDTO.setSystemInTime(systemDateAndTime.substring(0,8));
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"System Time set :: "+systemDateAndTime.substring(0,8));

      /*************************************************************************
       * Base Currency
       ************************************************************************/
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Base Currency set :: 11 ");
      baseCurrency = commonManagementSB.getBaseCurrency("11");
      commonDTO.setBaseCurrency(baseCurrency);

      /*************************************************************************
       * 영업일자을 설정한다.
       ************************************************************************/
      if( (businessDate=STF_SPgetbusinessdate()) == null ){
        STF_SPerror("EFWK0016",this.getClass().getName()+ ".STF_SPinit()::get business date error");
        LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"error to get businessDate");
      }
      else
        commonDTO.setBusinessDate(businessDate);

     /*
      * 기존방식
      if( (businessDate = commonManagementSB.getBusinessDate("11")) == null ){
        STF_SPerror("EFWK0005",this.getClass().getName()+ ".STF_SPinit()::get business date error");
        LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"STF_SPinit()::get business date error");
      }
      else
        commonDTO.setBusinessDate(businessDate);
      */

      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Business Date :: "+businessDate);

      return true;
    }
    catch(Exception ex){
      STF_SPerror("EFWK0002",this.getClass().getName()+ ".STF_SPinit():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
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
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Get Transaction Level::");
      switch (TPMSVCAPI.getInstance().TPinfo(tx))
      {
        case -1 :
          STF_SPerror("EFWK0006",this.getClass().getName()+ ".STF_SPmiddle()::TPinfo() Exception");
          break;
        case 1 :
          /*********************************************************************
           * 이미 트랜잭션을 시작한 상태이므로 트랜잭션을 시작하지 않고 현재의 트랜잭션
           * 을 유지해 준다
           ********************************************************************/
          LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Already Transaction started");
          break;
        case 0 :
          /*********************************************************************
           * 클라이언트 화면에서 올라온 Transaction Timer (seconds) 값을 가지고
           * UserTransaction을 시작한다.
           ********************************************************************/
          LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Transaction Not started");
          if( TPMSVCAPI.getInstance().TPbegin(tx,CommonUtil.Str2Int(tpsvcinfo.getTx_timer())))
            setSTF_SPtxinfo(1);
          else
            setSTF_SPtxinfo(0);
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
      //LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Fetch Transaction No :: ");
      //transactionNo = referenceManagementSB.getTransactionNumber(commonDTO.getBankCode(), businessDate);
      //commonDTO.setTransactionNo(transactionNo);
      //tpsvcinfo.setHostseq(transactionNo);

      if( ! tpsvcinfo.getTpfq().equals("100") )
        tpsvcinfo.setOrgseq(tpsvcinfo.getHostseq());

      commonDTO.setTransactionNo(tpsvcinfo.getHostseq() );
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"HOSTSEQ:" + tpsvcinfo.getHostseq()  + " ORGSEQ:"+tpsvcinfo.getOrgseq() );
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
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Set EOD Field ::");
      STF_SPeod();
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"[" + tpsvcinfo.getTrclass() + "]"  );
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
       LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Set Control Transaction ::");
       STF_SPtxctl();
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
       LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Check Transaction Timeout between WAF and EJB Tier ::");
       STF_SPwebtxtimer();
       if( isErr() ){
         LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"error"  );
         return false;
       }

       /*************************************************************************
        * 클라이언트의 호출정보를 보여준다. (TPFQ)
        *************************************************************************
        * 200 - 온라인 클라이언트
        * 100 - 온라인 서버에서 호출
        * 300 - 대외기관에서 호출
        * 400 - 배치에서 호출
        *
        ************************************************************************/
       LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Check Call Transaction Location :: ");
       switch( CommonUtil.Str2Int(tpsvcinfo.getTpfq()) )
       {
         case 100 :
           LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"TPFQ : 100 ");
           break;
         case 200 :
           LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"TPFQ : 200 ");
           break;
         case 300 :
           LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"TPFQ : 300 ");
           break;
         case 400 :
           LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"TPFQ : 400 ");
           break;
         default :
           LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"TPFQ : not valid ");
           STF_SPerror("EFWK0007",this.getClass().getName()+ ".STF_SPmiddle():TPFQ NOT VALID");
         break;
       }
       LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"클라이언트의 호출위치 정보를 가지고 온다 : [" + tpsvcinfo.getTpfq() + "]"  );
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
       LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Set TPMSVCINFO ::");
       STF_SPsettpmsvcinfo();

       return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror("EFWK0003",this.getClass().getName()+ ".STF_SPmiddle():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
      return false;
    }
  }

  public boolean STF_SPend()
  {
    try{

      /*************************************************************************
       * 기본정보를 재셋팅한다.
       ************************************************************************/
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Set SystemOuttime ::");
      commonDTO.setSystemOutTime(CommonUtil.GetSysTime());

      /*************************************************************************
       * TPMSVCINFO 정보를 재셋팅한다.
       ************************************************************************/
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Set TPMSVCINFO ::");
      tpsvcinfo.setSystemInTime(commonDTO.getSystemInTime());

      /*************************************************************************
       * 패킷정보를 재셋팅한다.
       ************************************************************************/
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"Move Packet Rebuild ::");
      STF_SPmovepacket();

      /*************************************************************************
       * 공통정보 로깅작을 실시한다.
       ************************************************************************/
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[STF_SPdbInLog() START]");
      if( !STF_SPdbInLog(eplevent) ){
        LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"STF_SPdbInLog() error");
      }
      else{
        LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"STF_SPdbInLog() success");
      }
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[STF_SPdbInLog() END] (true)");

      /*************************************************************************
       * 공통정보 로깅작을 실시한다.
       ************************************************************************/
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[STF_SPcommonLog START]");
      if( !STF_SPcommonLog() ){
        LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"STF_SPcommonLog() error");
      }
      else{
        LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"STF_SPcommonLog() success");
      }
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[STF_SPcommonLog() END] (true)");

      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror("EFWK0004",this.getClass().getName()+ ".STF_SPend():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
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

      if( !dao.DB_INSERTinlog(event) ){
        STF_SPerror("EFWK0008",this.getClass().getName()+ ".STF_SPdbInLog():INPUT PACKET EXCEPTION");
        return false;
      }
    }
    catch(Exception ex){
      ex.printStackTrace();
      STF_SPerror("EFWK0009",this.getClass().getName()+ ".STF_SPdbInLog():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
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
        STF_SPerror("EFWK0010",this.getClass().getName()+ ".STF_SPcommonLog():"+e.toString());
        LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,e);
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

      /*
       * 모든 배치거래에 대한 제어 여부를 판별한다
       */

      /*
       * ATM 거래에 대한 제어여부를 관리한다
       */

      /*
       * 인터넷뱅 거래에 대한 제어여부를 관리한다
       */

      /*
       * 거래코드에 대한 제어여부를 관리한다
       * 10개의 거래코드 버럭을 지정
       * 1000 - 1002
       * 1010 - 1023
       * 1099 - 1100
       * ....
       */

      /*
       * 뱅크코드에 대한 텔러별 제어 정보를 관리한다
       */

      return 0;
    }
    catch(Exception ex){
      STF_SPerror("EFWK0011",this.getClass().getName()+ ".STF_SPtxctl():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
      return -1;
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
        tpsvcinfo.setTrclass("1");
      else
        tpsvcinfo.setTrclass("0");
      return;
    }
    catch(Exception ex){
      STF_SPerror("EFWK0012",this.getClass().getName()+ ".STF_SPeod():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
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
   ************************************************************************/
  private void STF_SPwebtxtimer()
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
        STF_SPerror("EFWK0014",this.getClass().getName()+ ".STF_SPwebtxtimer():Transaction-timeout error");
    }
    catch(Exception ex){
      STF_SPerror("EFWK0013",this.getClass().getName()+ ".STF_SPwebtxtimer():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
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
    */

    //service_name : 클라이언트에서 셋팅
    tpsvcinfo.setSystem_date(commonDTO.getSystemDate());
    tpsvcinfo.setSystemInTime(commonDTO.getSystemInTime());
    tpsvcinfo.setSystemOutTime(commonDTO.getSystemOutTime());
    //tpfq : 클라이언트에서 셋팅
    //operation_name : 클라이언트에서 셋팅
    //action_name : 클라이언트에서 셋팅


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
      aa = dao.GetBizDate();
    }
    catch(Exception ex){
      STF_SPerror("EFWK0015",this.getClass().getName()+ ".STF_SPgetbusinessdate():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
      return aa;
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