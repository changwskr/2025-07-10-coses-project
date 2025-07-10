package com.kdb.oversea.framework.transaction.tcf;

import java.rmi.*;
import java.sql.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.io.*;
import javax.ejb.*;
import javax.naming.*;
import javax.transaction.*;
import weblogic.transaction.TxHelper;
import weblogic.transaction.TxHelper;


import com.kdb.oversea.framework.transaction.tpmutil.*;
import com.kdb.oversea.eplatonframework.transfer.*;
import com.kdb.oversea.foundation.utility.CommonUtil;
import com.kdb.oversea.foundation.logej.LOGEJ;
import com.kdb.oversea.foundation.utility.CommonUtil;
import com.kdb.oversea.framework.transaction.dao.*;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 * 실제 트랜잭션을 관리하면서 각 업무시스템으로의 주 업무 클래스를 호출한다
 * 전체 구조는 3개의 부분으로 구성되어져 있다
 * 1. STF
 *    트랜잭션을 시작하기 전의 기본적인 정보를 관리한다
 * 2. BTF
 *    업무로직을 처리하기 위해서 관리한다
 * 3. ETF
 *    트랜잭션의 완료를 처리하기 위해서 관리되는 모듈이다.
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

public class TCF extends AbstractTCF implements ITCF
{
  private static TCF instance;
  private STF stf;
  private ETF etf;
  private BTF btf;
  private String packetinfo;
  private String senddata;
  private Connection tcfconn;

  public  String transaction_type;
  private UserTransaction tx;
  private SessionContext ctx;
  private EPlatonEvent eplevent;
  private String url = "t3://localhost:10000";

  private TransactionManager tm;
  private String STF_intime="XXXXXXXX";
  private String STF_outtime="XXXXXXXX";
  private String BTF_intime="XXXXXXXX";
  private String BTF_outtime="XXXXXXXX";
  private String ETF_intime="XXXXXXXX";
  private String ETF_outtime="XXXXXXXX";
  private String LOGIC_LEVEL="XXXX";

  /**
   * TCF 인스턴스를 반환하는 함수
   *
   * @return
   */
  public static synchronized TCF getInstance() {
    if (instance == null) {
      try{
        instance = new TCF();
      }catch(Exception igex){
        igex.printStackTrace();
      }
    }
    return instance;
  }

  /**
   * 생성자 함수
   *
   * @throws NamingException
   */

  public TCF() throws NamingException{

  }

  /**
   * @desc : 실제 트랜잭션이 관리되는 부분이다
   * @param peplaton_event   : 클라이언트-서버간 전송객체
   * @param session_context  : SessionContext 정보를 facade단으로부터 가지고 온다.
   * @param transaction_mode : container,usertransaction타입 두가지가 있다. 이값에
   *                           대해선 환경파일로 관리할 것이다.
   * @return
   */
  public EPlatonEvent alltransaction_execute(
      EPlatonEvent peplaton_event,SessionContext session_context,String transaction_mode)
  {
    try {

      eplevent = peplaton_event;
      ctx = session_context;

      LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                 "====================================================================[TCF] start");


      /*
      * STF
      * 트랜잭션을 시작한다
       */
      level("Before","STF");


      if( "usertransaction".equals(transaction_type) )
      {
        /*
        * UserTransaction에 대한 정보를 구하고 시작한다.
         */
        LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"[UserTransaction Start]");
        tx = TPMSVCAPI.getInstance().TPJNDIUserTransaction(this.url);
        if( tx == null ){
          eplevent.getTPSVCINFODTO().setErrorcode("IZZ000");
          TCF_SPerror("EBD100","UserTransaction Get Ex");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"UserTransaction Errror");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"[UserTransaction End]");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                     "====================================================================[TCF] end");
          return eplevent;
        }
        LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"UserTransaction Success");
        LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"[UserTransaction END]");
      }
      else{
        LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"[Container Transaction Start]");
      }

      /*
      * STF 시작한다.
       */
      LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                 "====================================================[STF] start");
      if( "usertransaction".equals(transaction_type) )
      {
        eplevent = (stf=new STF(tx,ctx)).execute(eplevent);
      }
      else{
        eplevent = (stf=new STF(transaction_type,ctx)).execute(eplevent);
      }

      /*
      * STF 을 종료한다.
       */
      level("After","STF");


      /*
      * STF에서의 에러가 발생 했는지 조사한다
      * 정상적일 경우에만 업무로직 BTF을 수행하고 나머지는 에러를 클라이언트로 리턴한다
      *
       */
      switch( eplevent.getTPSVCINFODTO().getErrorcode().charAt(0) )
      {
        case 'e':
        case 's':
        case 'E':
        case 'S':
          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                     "===============================[STF] error");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                     "====================================================[STF] end");
          break;
        case 'I':
          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                     "===============================[STF] success");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                     "====================================================[STF] end");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                     "====================================================[BTF] start");

          /*
          * 업무트랜잭션 로직을 시작한다
           */
          level("Before","BTF");
          eplevent = (btf=new BTF()).execute(eplevent);
          level("After","BTF");

          /*
          * 업무가 정상적으로 호출되었는지 확인한다.
           */
          if( isErr() )
            LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                       "===============================[BTF] error");
          else
            LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                       "===============================[BTF] success");


          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                     "====================================================[BTF] end");
          break;
        case '*':
        default:
          TCF_SPerror("EFWK0034",this.getClass().getName()+ ".execute():error code not set");
        break;
      }


      /*
      * 트랜잭션을 종료한다.
       */
      LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                 "====================================================[ETF] start");
      level("Before","ETF");

      if( "usertransaction".equals(transaction_type) ){
        LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"UserTransactionInfo : " + tx);
        eplevent = (etf=new ETF(tx,stf.getSTF_SPtxinfo())).execute(eplevent);
      }
      else{
        LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"TransactionInfo : " + tx);
        eplevent = (etf=new ETF(transaction_type,stf.getSTF_SPtxinfo(),ctx)).execute(eplevent);
      }
      LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                 "====================================================[ETF] end");

    }
    catch(Exception ex){
      TCF_SPerror("EFWK0032",this.getClass().getName()+ ".execute():"+ex.toString());
      LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"TCF_execute() exception:[EFWK032]");
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
    }

    level("After","ETF");
    LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                               "====================================================================[TCF] end");

    /*
    * 트랜잭션에 대한 로깅을 남긴다.
     */
    TCF_SPcommonLog(eplevent);

    return eplevent;

  }


  /**
   * @desc : 실제 트랜잭션이 관리되는 부분이다
   * @param peplaton_event   : 클라이언트-서버간 전송객체
   * @param session_context  : SessionContext 정보를 facade단으로부터 가지고 온다.
   * @param transaction_mode : container,usertransaction타입 두가지가 있다. 이값에
   *                           대해선 환경파일로 관리할 것이다.
   * @return
   */
  public EPlatonEvent execute(
      EPlatonEvent peplaton_event,SessionContext session_context,String transaction_mode)
  {
    try {
      tm = TxHelper.getTransactionManager();
      eplevent = peplaton_event;
      ctx = session_context;

      LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                 "====================================================================[TCF] start");
      Transaction transaction = tm.getTransaction();

      LOGEJ.getInstance().printf(5,(EPlatonEvent)eplevent,"Transaction TPinfo mode : [" + TxHelper.status2String(tm.getStatus()) +"] tpfq:[" +this.eplevent.getTPSVCINFODTO().getTpfq()+"]"  );

      /*************************************************************************
       * 해당 빈의 트랜잭션 타입을 가져온다.                     spcommo.xml 파일정보
       *************************************************************************
       * <spcommo-callmethod01>
       *   <bizaction-class>com.kdb.oversea.eplatonframework.business.delegate.action.SPcommoBizAction</bizaction-class>
       *   <transactionable>Y</transactionable>
       *   <action-call-bean-type>local</action-call-bean-type>
       *   <bean-transaction-type>container</bean-transaction-type>
       *   <bizaction-method>callmethod01</bizaction-method>
       *   <operation-class>com.kdb.oversea.common.business.facade.CommonManagementSBean</operation-class>
       *   <bizaction-parametertype>com.kdb.oversea.eplatonframework.transfer.EPLcommonCDTO</bizaction-parametertype>
       * </spcommo-callmethod01>
       *
       */
      transaction_type = TPMSVCAPI.getInstance().TPgetbeantransactiontype(peplaton_event);

      /*
      * STF
      * 트랜잭션을 시작한다
       */
      level("Before","STF");


      if( "usertransaction".equals(transaction_type) )
      {
        /*
        * UserTransaction에 대한 정보를 구하고 시작한다.
         */
        LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"[UserTransaction Start]");
        tx = TPMSVCAPI.getInstance().TPJNDIUserTransaction(this.url);
        if( tx == null ){
          eplevent.getTPSVCINFODTO().setErrorcode("IZZ000");
          TCF_SPerror("EBD100","UserTransaction Get Ex");
          LOGEJ.getInstance().printf(10,(EPlatonEvent)peplaton_event,"UserTransaction Errror");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"[UserTransaction End]");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                     "====================================================================[TCF] end");
          return eplevent;
        }
        LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"UserTransaction Success");
        LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"[UserTransaction END]");
      }
      else if( "no_transaction_type".equals(transaction_type) )
      {
        TCF_SPerror("ETCF001","bean no_transaction_type");
        LOGEJ.getInstance().printf(10,(EPlatonEvent)peplaton_event,"bean no_transaction_type value");
        LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                   "====================================================================[TCF] end");
        return eplevent;
      }
      else
      {
        LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"[Container Transaction Start]");
      }

      LOGEJ.getInstance().printf(5,(EPlatonEvent)eplevent,"222Transaction TPinfo mode : [" + TxHelper.status2String(tm.getStatus()) +"] tpfq:[" +this.eplevent.getTPSVCINFODTO().getTpfq()+"]"  );

      /*
      * STF 시작한다.
       */
      LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                 "====================================================[STF] start");
      if( "usertransaction".equals(transaction_type) ){
        eplevent = (stf=new STF(tx,ctx)).execute(eplevent);
      }
      else{
        eplevent = (stf=new STF(transaction_type,ctx)).execute(eplevent);
      }

      LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"ContainerTransactionInfo : " + tx + "-" + stf.getSTF_SPtxinfo() );

      LOGEJ.getInstance().printf(5,(EPlatonEvent)eplevent,"33333Transaction TPinfo mode : [" + TxHelper.status2String(tm.getStatus()) +"] tpfq:[" +this.eplevent.getTPSVCINFODTO().getTpfq()+"]"  );
      /*
      * STF 을 종료한다.
       */
      level("After","STF");


      /*
      * STF에서의 에러가 발생 했는지 조사한다
      * 정상적일 경우에만 업무로직 BTF을 수행하고 나머지는 에러를 클라이언트로 리턴한다
      *
       */
      switch( eplevent.getTPSVCINFODTO().getErrorcode().charAt(0) )
      {
        case 'e':
        case 's':
        case 'E':
        case 'S':
          LOGEJ.getInstance().printf(10,(EPlatonEvent)peplaton_event,
                                     "===============================[STF] error");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                     "====================================================[STF] end");
          break;
        case 'I':
          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                     "===============================[STF] success");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                     "====================================================[STF] end");
          LOGEJ.getInstance().printf(10,(EPlatonEvent)peplaton_event,
                                     "====================================================[BTF] start");

          /*
          * 업무트랜잭션 로직을 시작한다
           */
          level("Before","BTF");
          eplevent = (btf=new BTF()).execute(eplevent);
          level("After","BTF");
          /*
          * 업무가 정상적으로 호출되었는지 확인한다.
           */
          if( isErr() )
            LOGEJ.getInstance().printf(10,(EPlatonEvent)peplaton_event,
                                       "===============================[BTF] error");
          else
            LOGEJ.getInstance().printf(10,(EPlatonEvent)peplaton_event,
                                       "===============================[BTF] success");


          LOGEJ.getInstance().printf(10,(EPlatonEvent)peplaton_event,
                                     "====================================================[BTF] end");
          break;
        case '*':
        default:
          TCF_SPerror("EFWK0034",this.getClass().getName()+ ".execute():error code not set");
        break;
      }


      /*
      * 트랜잭션을 종료한다.
       */
      LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                 "====================================================[ETF] start");
      level("Before","ETF");

      if( "usertransaction".equals(transaction_type) ){
        LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"UserTransactionInfo : " + tx);
        eplevent = (etf=new ETF(tx,stf.getSTF_SPtxinfo())).execute(eplevent);
      }
      else{
        LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,"ContainerTransactionInfo : " + ctx + "-" + stf.getSTF_SPtxinfo() );
        eplevent = (etf=new ETF(transaction_type,stf.getSTF_SPtxinfo(),ctx)).execute(eplevent);
      }
      LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                                 "====================================================[ETF] end");

      LOGEJ.getInstance().printf(4,(EPlatonEvent)eplevent,"666666666Transaction TPinfo mode : [" + TxHelper.status2String(tm.getStatus()) +"] tpfq:[" +this.eplevent.getTPSVCINFODTO().getTpfq()+"]"  );
    }
    catch(Exception ex){
      ex.printStackTrace();
      TCF_SPerror("EFWK0032",this.getClass().getName()+ ".execute():"+ex.toString());
      LOGEJ.getInstance().printf(10,(EPlatonEvent)peplaton_event,"TCF_execute() exception:[EFWK032]");
      LOGEJ.getInstance().eprintf(10,(EPlatonEvent)eplevent,ex);
    }

    level("After","ETF");
    LOGEJ.getInstance().printf(5,(EPlatonEvent)peplaton_event,
                               "====================================================================[TCF] end");

    /*
    * 트랜잭션에 대한 로깅을 남긴다.
     */
    TCF_SPcommonLog(eplevent);

    return eplevent;

  }



  /**
   * 실제 트랜잭션이 관리되는 실행모듈로서
   * 이것은 상위 excute()메소드와 틀린점은 usertransaction,container을 메소드로 나뉘
   * 어서 관리한 것이다.
   * @param pevent
   * @param session_context
   * @param transaction_mode
   *        "container || usertransaction"
   * @return
   */
  public EPlatonEvent execute_sample(EPlatonEvent pevent,SessionContext session_context,String transaction_mode)
  {
    try
    {
      eplevent = pevent;
      ctx = session_context;

      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                 "====================================================================[TCF] start");

      if( "usertransaction".equals(transaction_mode) ){
        eplevent = this.usertransaction_execute(pevent);
      }
      else if( "container".equals(transaction_mode) ) {
        eplevent = this.containertransaction_execute(pevent);

      }
      else {
        TCF_SPerror("EFWK0W01",this.getClass().getName()+ ".execute():");
        LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,"TCF_execute() exception:[EFWKW01]");
      }
    }
    catch(Exception ex){
      TCF_SPerror("EFWK0W02",this.getClass().getName()+ ".execute():"+ex.toString());
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,"TCF_execute() exception:[EFWKW02]");
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
    }

    LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                               "====================================================================[TCF] end");

    return eplevent;

  }


  /**
   * 실제 트랜잭션이 관리되는 실행모듈
   *
   * @param pevent
   * @return
   */
  public EPlatonEvent usertransaction_execute(EPlatonEvent pevent){
    try {
      eplevent = pevent;

      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                 "====================================================================[TCF] start");

      /*
      * STF
      * 트랜잭션을 시작한다
       */
      level("Before","STF");

      /*
      * UserTransaction에 대한 정보를 구하고 시작한다.
       */
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,"[UserTransaction Start]");
      tx = TPMSVCAPI.getInstance().TPJNDIUserTransaction(this.url);
      if( tx == null ){
        eplevent.getTPSVCINFODTO().setErrorcode("IZZ000");
        TCF_SPerror("EBD100","UserTransaction Get Ex");
        LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,"UserTransaction Errror");
        LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,"[UserTransaction End]");
        LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                   "====================================================================[TCF] end");
        return eplevent;
      }
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,"UserTransaction Success");
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,"[UserTransaction END]");


      /*
      * STF 시작한다.
       */
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                 "====================================================[STF] start");
      eplevent = (stf=new STF(tx,ctx)).execute(eplevent);

      /*
      * STF 을 종료한다.
       */
      level("After","STF");


      /*
      * STF에서의 에러가 발생 했는지 조사한다
      * 정상적일 경우에만 업무로직 BTF을 수행하고 나머지는 에러를 클라이언트로 리턴한다
      *
       */
      switch( eplevent.getTPSVCINFODTO().getErrorcode().charAt(0) )
      {
        case 'e':
        case 's':
        case 'E':
        case 'S':
          LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                     "===============================[STF] error");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                     "====================================================[STF] end");
          break;
        case 'I':
          LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                     "===============================[STF] success");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                     "====================================================[STF] end");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                     "====================================================[BTF] start");

          /*
          * 업무트랜잭션 로직을 시작한다
           */
          level("Before","BTF");
          eplevent = (btf=new BTF()).execute(eplevent);
          level("After","BTF");

          /*
          * 업무가 정상적으로 호출되었는지 확인한다.
           */
          if( isErr() )
            LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                       "===============================[BTF] error");
          else
            LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                       "===============================[BTF] success");


          LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                     "====================================================[BTF] end");
          break;
        case '*':
        default:
          TCF_SPerror("EFWK0034",this.getClass().getName()+ ".execute():error code not set");
        break;
      }


      /*
      * 트랜잭션을 종료한다.
       */
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                 "====================================================[ETF] start");
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,"UserTransactionInfo : " + tx);
      level("Before","ETF");
      eplevent = (etf=new ETF(tx,stf.getSTF_SPtxinfo())).execute(eplevent);
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                 "====================================================[ETF] end");

    }
    catch(Exception ex){
      TCF_SPerror("EFWK0032",this.getClass().getName()+ ".execute():"+ex.toString());
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,"TCF_execute() exception:[EFWK032]");
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
    }

    level("After","ETF");
    LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                               "====================================================================[TCF] end");

    /*
    * 트랜잭션에 대한 로깅을 남긴다.
     */
    TCF_SPcommonLog(eplevent);

    return eplevent;

  }


  /**
   * 실제 트랜잭션이 관리되는 실행모듈
   *
   * @param pevent
   * @return
   */
  public EPlatonEvent containertransaction_execute(EPlatonEvent pevent){
    try {
      eplevent = pevent;

      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                 "====================================================================[TCF] start");

      /*
      * STF
      * 트랜잭션을 시작한다
       */
      level("Before","STF");

      /*
      * STF 시작한다.
       */
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                 "====================================================[STF] start");
      eplevent = (stf=new STF("container",ctx)).execute(eplevent);

      /*
      * STF 을 종료한다.
       */
      level("After","STF");


      /*
      * STF에서의 에러가 발생 했는지 조사한다
      * 정상적일 경우에만 업무로직 BTF을 수행하고 나머지는 에러를 클라이언트로 리턴한다
      *
       */
      switch( eplevent.getTPSVCINFODTO().getErrorcode().charAt(0) )
      {
        case 'e':
        case 's':
        case 'E':
        case 'S':
          LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                     "===============================[STF] error");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                     "====================================================[STF] end");
          break;
        case 'I':
          LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                     "===============================[STF] success");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                     "====================================================[STF] end");
          LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                     "====================================================[BTF] start");

          /*
          * 업무트랜잭션 로직을 시작한다
           */
          level("Before","BTF");
          eplevent = (btf=new BTF()).execute(eplevent);
          level("After","BTF");

          /*
          * 업무가 정상적으로 호출되었는지 확인한다.
           */
          if( isErr() )
            LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                       "===============================[BTF] error");
          else
            LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                       "===============================[BTF] success");


          LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                     "====================================================[BTF] end");
          break;
        case '*':
        default:
          TCF_SPerror("EFWK0034",this.getClass().getName()+ ".execute():error code not set");
        break;
      }


      /*
      * 트랜잭션을 종료한다.
       */
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                 "====================================================[ETF] start");
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,"UserTransactionInfo : " + tx);
      level("Before","ETF");
      eplevent = (etf=new ETF("container",stf.getSTF_SPtxinfo(),ctx)).execute(eplevent);
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                                 "====================================================[ETF] end");

    }
    catch(Exception ex){
      TCF_SPerror("EFWK0032",this.getClass().getName()+ ".execute():"+ex.toString());
      LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,"TCF_execute() exception:[EFWK032]");
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
    }

    level("After","ETF");
    LOGEJ.getInstance().printf(5,(EPlatonEvent)pevent,
                               "====================================================================[TCF] end");

    /*
    * 트랜잭션에 대한 로깅을 남긴다.
     */
    TCF_SPcommonLog(eplevent);

    return eplevent;

  }

  private void printtpmsvcinfo()
  {
    try{
      EPlatonCommonDTO commonDTO = (EPlatonCommonDTO)eplevent.getCommon();
      TPSVCINFODTO tpsvcinfo = eplevent.getTPSVCINFODTO();

      StringBuffer format = new StringBuffer();

      String branchcode = commonDTO.getBranchCode();
      String system = tpsvcinfo.getSystem_name();
      String method = tpsvcinfo.getOperation_name();
      String eventno = commonDTO.getEventNo();
      String tx_intime = commonDTO.getSystemInTime();
      String tx_outtime = commonDTO.getSystemOutTime();
      String tx_interval = CommonUtil.Int2Str( CommonUtil.ItvSec(tx_intime,tx_outtime) );
      String tx_errorcode = tpsvcinfo.getErrorcode();
      String tx_level = this.LOGIC_LEVEL;
      String tpfq = tpsvcinfo.getTpfq();

      format.append(branchcode+" ");
      format.append(CommonUtil.SpaceToStr(system,10)+" ");
      format.append(CommonUtil.SpaceToStr(method,60)+" ");
      format.append(eventno+" ");
      format.append(tx_intime+" ");
      format.append(tx_outtime+" ");
      format.append(CommonUtil.ZeroToStr(tx_interval,3)+"/ ");
      format.append(tx_errorcode+" ");
      format.append(tx_level+" ");
      format.append(tpfq+" ||");

      LOGEJ.getInstance().txprint(eplevent,format.toString());

      ArrayList al = tpsvcinfo.getAllTPMSVCINFO();
      boolean offset=true;
      for( int i = 0 ; i < al.size() ; i ++ )
      {
        StringBuffer tpmformat = new StringBuffer();
        TPMSVCINFO tm = (TPMSVCINFO)al.get(i);
        tpmformat.append("--" + CommonUtil.ZeroToStr(CommonUtil.Int2Str(i+1),2) + " ");
        tpmformat.append(CommonUtil.SpaceToStr(tm.getCall_service_name(),10) + " ");
        tpmformat.append(tm.getCall_tpm_in_time() + " ");
        tpmformat.append(tm.getCall_tpm_out_time() + " ");
        tpmformat.append(tm.getError_code() + " ");
        tpmformat.append(CommonUtil.ZeroToStr(tm.getCall_tpme_interval(),3)  + " ");
        tpmformat.append(tm.getCall_location() + " ");
        if( offset ){
          LOGEJ.getInstance().nohead_txprintf(eplevent,tpmformat.toString() );
          offset=false;
        }
        else{
          LOGEJ.getInstance().txprintf(eplevent,"                                                                                                                              "
                                       +tpmformat.toString() );
        }
      }

      StringBuffer timeformat = new StringBuffer();
      timeformat.append("STF["+this.STF_intime);
      timeformat.append("-" + this.STF_outtime +"]");
      timeformat.append("BTF[" + this.BTF_intime);
      timeformat.append("-" + this.BTF_outtime+"]");
      timeformat.append("ETF[" + this.ETF_intime);
      timeformat.append("-" + this.ETF_outtime+"]");
      if( al.size() !=0 )
        LOGEJ.getInstance().txprintf(eplevent,timeformat.toString());
      else
        LOGEJ.getInstance().ftxprintf(eplevent,timeformat.toString());

      if( !tpfq.equals("100") )
        LOGEJ.getInstance().txprintf(eplevent,"-----------------------------------------------------------------------------------------------------------------------------------------------------------------");

    }
    catch(Exception ex){
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
    }
  }


  /**
   * 모든 트랜잭션에 대한 로깅을 관리한다.
   * @param eplevent
   */
  private void TCF_SPcommonLog(EPlatonEvent eplevent)
  {
    try{
      /////////////////////////////////////////////////////////////////////////
      // 트랜잭션에 대한 TRACE LOG 관리
      // TYPE.1 - printtpmsvcinfo (4line)
      // TYPE.2 - TCF_SPtracelogging (1line)
      // TYPE.3 - DataBase logging : TxTimeProcDAO
      /////////////////////////////////////////////////////////////////////////
      //printtpmsvcinfo();
      TCF_SPtracelogging(eplevent);

      /////////////////////////////////////////////////////////////////////////
      // 현재 이부분에 대한 관리는 트랜잭션 관리 모듈이 required  속성에 의해서 관리
      // 되고 있다.
      // 에러가 발생하지 않았을 경우만 현재는 한정한다. 향후 여기서의 모듈은
      // delegate 단으로 옮길것이다. 그리고 여기서의 상세정보를 추가해야 될 것이다.
      // -> TransactionLog로 재정의
      ////////////////////////////////////////////////////////////////////////
      /*
      TxTimeProcDAO tdao = new TxTimeProcDAO();
      tdao.TRANSACTION_INFO(eplevent);
      */

    }
    catch(Exception ex){
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
    }

  }


  private void TCF_SPtracelogging(EPlatonEvent eplevent)
  {
    try{
      EPlatonCommonDTO commonDTO = (EPlatonCommonDTO)eplevent.getCommon();
      TPSVCINFODTO tpsvcinfo = eplevent.getTPSVCINFODTO();
      StringBuffer format = new StringBuffer();
      String branchcode = commonDTO.getBranchCode();
      String system = tpsvcinfo.getSystem_name();
      String method = tpsvcinfo.getOperation_name();
      String eventno = commonDTO.getEventNo();
      String tx_intime = commonDTO.getSystemInTime();
      String tx_outtime = commonDTO.getSystemOutTime();
      String tx_interval = CommonUtil.Int2Str( CommonUtil.ItvSec(tx_intime,tx_outtime) );
      String tx_errorcode = tpsvcinfo.getErrorcode();
      String tx_level = this.LOGIC_LEVEL;
      String tpfq = tpsvcinfo.getTpfq();

      format.append(branchcode+",");
      //format.append(CommonUtil.SpaceToStr(system,10)+" ");
      //format.append(CommonUtil.SpaceToStr(method,60)+" ");
      format.append(eventno+",");
      format.append(tx_intime+",");
      format.append(tx_outtime+",");
      format.append(tx_errorcode+",");
      format.append(tx_level+",");
      format.append(tpfq+",");
      format.append(CommonUtil.ZeroToStr(tx_interval,3)+"/sec!");

      format.append("[STF(" + STF_intime + "-" + STF_outtime + ")" );
      format.append("BTF(" + BTF_intime + "-" + BTF_outtime + ")" );
      format.append("ETF(" + ETF_intime + "-" + ETF_outtime + ")]!" );

      ArrayList al = tpsvcinfo.getAllTPMSVCINFO();

      for( int i = 0 ; i < al.size() ; i ++ )
      {
        TPMSVCINFO tm = (TPMSVCINFO)al.get(i);
        format.append("{"+CommonUtil.ZeroToStr(CommonUtil.Int2Str(i+1),2) + ":");
        format.append(tm.getCall_service_name() + ":");
        format.append(tm.getCall_tpm_in_time() + ":");
        format.append(tm.getCall_tpm_out_time() + ":");
        format.append(tm.getError_code() + ":");
        format.append(CommonUtil.ZeroToStr(tm.getCall_tpme_interval(),3)  + "/sec:");
        format.append(tm.getCall_location() +"}," );
      }

      LOGEJ.getInstance().tcf_txprintf(eplevent,format.toString());

      LOGEJ.getInstance().printf(5,this.eplevent,"              ***** // TPMSVCINFO // ***** ");
      LOGEJ.getInstance().printf(5,this.eplevent,format.toString());

    }
    catch(Exception ex){
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
    }
  }


  private boolean isErr(){
    switch (eplevent.getTPSVCINFODTO().getErrorcode().charAt(0))
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
        TCF_SPerror("EFWK0033",this.getClass().getName()+ ".isErr():error code not set");
      return true;
    }
  }


  private void TCF_SPerror(String errorcode,String message)
  {
    switch( eplevent.getTPSVCINFODTO().getErrorcode().charAt(0) )
    {
      case 'I' :
        eplevent.getTPSVCINFODTO().setErrorcode(errorcode);
        eplevent.getTPSVCINFODTO().setError_message(message);
        return;
      case 'E' :
        errorcode = errorcode+"|"+eplevent.getTPSVCINFODTO().getErrorcode();
        eplevent.getTPSVCINFODTO().setErrorcode(errorcode);
        eplevent.getTPSVCINFODTO().setError_message(message);
        return;
    }
  }

  /**
   * 각 트랜잭션 객체의 위치에 따라서 트랜잭션의 시간을 관리하기 위한 모듈
   *
   * @param Loc
   * @param module
   */
  public void level(String Loc,String module)
  {
    switch( module.charAt(0) )
    {
      case 'S': //STF
        if( Loc.charAt(0)=='B' ){  //BEFORE : BSTF(Before STF)
          eplevent.getTPSVCINFODTO().setSTF_intime( (this.STF_intime = CommonUtil.GetSysTime()));
          eplevent.getTPSVCINFODTO().setLogic_level((this.LOGIC_LEVEL = "BSTF"));
        }
        else{  //AFTER
          eplevent.getTPSVCINFODTO().setSTF_outtime((this.STF_outtime = CommonUtil.GetSysTime()));
          eplevent.getTPSVCINFODTO().setLogic_level((this.LOGIC_LEVEL = "ASTF"));
        }
        break;
      case 'E': //ETF
        if( Loc.charAt(0)=='B' ){  //BEFORE
          eplevent.getTPSVCINFODTO().setETF_intime((this.ETF_intime = CommonUtil.GetSysTime()));
          eplevent.getTPSVCINFODTO().setLogic_level((this.LOGIC_LEVEL = "BETF"));
        }
        else{//AFTER
          eplevent.getTPSVCINFODTO().setETF_outtime(this.ETF_outtime = CommonUtil.GetSysTime());
          eplevent.getTPSVCINFODTO().setLogic_level((this.LOGIC_LEVEL = "AETF"));
        }
        break;
      case 'B':  //BTF
        if( Loc.charAt(0)=='B' ){//BEFORE
          eplevent.getTPSVCINFODTO().setBTF_intime(this.BTF_intime = CommonUtil.GetSysTime());
          eplevent.getTPSVCINFODTO().setLogic_level((this.LOGIC_LEVEL = "BBTF"));
        }
        else{//AFTER
          eplevent.getTPSVCINFODTO().setBTF_outtime(this.BTF_outtime = CommonUtil.GetSysTime());
          eplevent.getTPSVCINFODTO().setLogic_level((this.LOGIC_LEVEL = "ABTF"));
        }
        break;
    }
  }



  public EPlatonEvent getEPlatonEvent(){
    return eplevent;
  }


}