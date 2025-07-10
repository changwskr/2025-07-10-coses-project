package com.kdb.oversea.foundation.tpmservice;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import java.io.*;
import java.rmi.*;
import java.sql.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.naming.*;
import javax.ejb.*;
import javax.transaction.*;
import java.math.BigDecimal;
import javax.rmi.PortableRemoteObject;

import com.chb.coses.foundation.log.*;
import com.chb.coses.foundation.base.*;
import com.chb.coses.foundation.config.*;
import com.chb.coses.foundation.jndi.*;
import com.chb.coses.foundation.utility.*;

import com.chb.coses.framework.transfer.*;
import com.chb.coses.foundation.db.DBService;
import com.kdb.oversea.foundation.config.Config;
import com.chb.coses.framework.constants.*;
import com.kdb.oversea.eplatonframework.transfer.*;
import com.kdb.oversea.eplatonframework.business.delegate.action.*;
import com.kdb.oversea.foundation.utility.*;
import com.kdb.oversea.foundation.logej.LOGEJ;
import com.kdb.oversea.framework.transaction.tcf.STF;
import com.kdb.oversea.framework.transaction.model.TransactionLogDDTO;
import com.kdb.oversea.framework.transaction.dao.*;

/*******************************************************************************
 * bizactionmap파일의 추가 태그
 * operation-class
 *******************************************************************************
<cashCard-listCashCard>
        <bizaction-class>com.chb.coses.cashCard.business.delegate.action.CashCardBizAction</bizaction-class>
        <transactionable>Y</transactionable>
        <bizaction-method>listCashCard</bizaction-method>
        <bizaction-parametertype>com.chb.coses.cashCard.transfer.CashCardConditionCDTO</bizaction-parametertype>
        <operation-class>com.kdb.oversea.cashCard.business.facade.CashCardManageMent</operation-class>
</cashCard-listCashCard>

********************************************************************************/

public class TPMSVCAPI extends AbstractTPMSVCAPI implements ITPMSVCAPI
{
  private static TPMSVCAPI instance;

  public static synchronized TPMSVCAPI getInstance(String ip,String port) {
    if (instance == null) {
      try{
        instance = new TPMSVCAPI(ip,port);
      }
      catch(Exception igex)
      {
        System.out.println(igex);
        return null;
      }
    }
    return instance;
  }

  public TPMSVCAPI(String ip,String port) throws Exception{
    try{
      url = "t3://"+ip+":"+port;
      System.out.println("TCPCallEJB URL : "+ url );
      ctx = this.getInitialContext();
      event = new EPlatonEvent();
    }catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }
  }

  public static synchronized TPMSVCAPI getInstance() {
    if (instance == null) {
      try{
        instance = new TPMSVCAPI();
      }
      catch(Exception igex)
      {
        System.out.println(igex);
        return null;
      }
    }
    return instance;
  }

  public TPMSVCAPI() throws Exception{
    try{
      url = com.kdb.oversea.foundation.constant.Constants.call_url;
      System.out.println("--2 TCPCallEJB URL : "+ url );
      ctx = this.getInitialContext();
      event = new EPlatonEvent();
    }catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }
  }


  /*****************************************************************************
   * 다른 시스템을 호출하기 위한 조건
   *
   * [1] EPlatonCommonDTO의 requestName 필드를 시스템명+호출메소드명 으로 셋팅한다

   *     실예) com.kdb.oversea.cashCard.business.facade.CashCardManagementSBBean의
   *           listCashCard 메소드호출할 경우
   *
   *           (1)cashCard.xml 파일
   *              <cashCard-listCashCard>
   *                <bizaction-class>com.chb.coses.cashCard.business.delegate.action.CashCardBizAction</bizaction-class>
   *                <transactionable>Y</transactionable>
   *                <bizaction-method>listCashCard</bizaction-method>
   *                <bizaction-parametertype>com.chb.coses.cashCard.transfer.CashCardConditionCDTO</bizaction-parametertype>
   *              </cashCard-listCashCard>

   *           (2) com.kdb.oversea.eplatonframework.transfer.EPlatonCommonDTO.requestName 셋팅
   *               com.kdb.oversea.eplatonframework.transfer.EPlatonCommonDTO.requestName = cashCard-listCashCard
   *
   * [2] action 클래스명을 해당 라우팅 xml 파일로부터 bizAction 클래스의 명을 가지고 온다
   *     - 이 값은 Config.xml파일의 "bizaction-map-filename"의 태그값을 가지고 온다
   *
   *     public static final String BIZDELEGATE_TAG = "bizaction-map-filename"
   *     public static final String ACTIONCLASS_TAG = "bizaction-class";
   *
   *     String configFileName = Config.getInstance().getElement(Constants.BIZDELEGATE_TAG).getTextTrim();
   *                           = /weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/bizaction-map.xml
   *                           이다
   *     actionClassName = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(Constants.ACTIONCLASS_TAG);
   *                     = com.chb.coses.cashCard.business.delegate.action.CashCardBizAction
   *
   *    - cashCard.xml
        <cashCard-listCashCard>
          <bizaction-class>com.chb.coses.cashCard.business.delegate.action.CashCardBizAction</bizaction-class>
          <transactionable>Y</transactionable>
          <bizaction-method>listCashCard</bizaction-method>
          <bizaction-parametertype>com.chb.coses.cashCard.transfer.CashCardConditionCDTO</bizaction-parametertype>
          <operation-class>com.kdb.oversea.cashCard.business.facade.CashCardManageMent</operation-class>
        </cashCard-listCashCard>
        *
        * [3] cashCard.xml에 다음을 추가한다.
        *    <operation-class>com.kdb.oversea.cashCard.business.facade.CashCardManageMent</operation-class>
        *****************************************************************************
        *
        * @return
        */

  public String TPgetactionclassname(EPlatonEvent event)
  {

    String configFileName = null;
    String requestName = null;
    String actionClassName = null;

    try{
      /***************************************************************************
       * BizAction을 구성한다.
       * [1] public static final String BIZDELEGATE_TAG = "bizaction-map-filename";
       *     - <bizaction-map-filename>
       *        /weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/bizaction-map.xml
       *       </bizaction-map-filename>
       * [2] requestName = event.getAction() = "cashCard-listCashCard"
       * [3] actionClassName = XMLCache.getInstance().getXML(configFileName).
       *                                getRootElement().getChild(requestName).
       *                                getChildTextTrim(Constants.ACTIONCLASS_TAG)
       *                     = com.chb.coses.cashCard.business.delegate.action.CashCardBizAction
       *
       ***************************************************************************/
      configFileName = Config.getInstance().getElement(Constants.BIZDELEGATE_TAG).getTextTrim();
      requestName = (event.getTPSVCINFODTO()).getReqName() ;
      event.setAction(requestName);
      System.out.println("ConfigFileName : " + configFileName);
      System.out.println("RequestName : " + requestName);

      // BizAction에서 구해온 값들이다.
      actionClassName = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(Constants.ACTIONCLASS_TAG);

      // BizAction에서 구해온 값들이다.
      System.out.println("ActionClassName : " + actionClassName);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      LOGEJ.getInstance().eprintf(5,event,ex);
      return null;
    }
    return actionClassName;
  }

  protected void TPgetrequestinfo(EPlatonEvent event)
  {
    String configFileName = Config.getInstance().getElement(Constants.BIZDELEGATE_TAG).getTextTrim();
    String requestName = event.getAction();
    String actionClassName = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(Constants.ACTIONCLASS_TAG);
    String methodName = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(Constants.METHOD_TAG);
    String parameterTypeName = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(Constants.TYPE_TAG);

    System.out.println("ConfigFileName : " + configFileName);
    System.out.println("RequestName : " + requestName);
    System.out.println("methodName : " + methodName);
    System.out.println("parameterTypeName : " + parameterTypeName);
  }

  public String TPgetinvokemethodname(EPlatonEvent event)
  {

    String configFileName = null;
    String requestName = null;
    String methodName = null;

    try{
      /***************************************************************************
       * BizAction을 구성한다.
       * [1] public static final String BIZDELEGATE_TAG = "bizaction-map-filename";
       *     - <bizaction-map-filename>
       *        /weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/bizaction-map.xml
       *       </bizaction-map-filename>
       * [2] requestName = event.getAction() = "cashCard-listCashCard"
       * [3] actionClassName = XMLCache.getInstance().getXML(configFileName).
       *                                getRootElement().getChild(requestName).
       *                                getChildTextTrim(Constants.ACTIONCLASS_TAG)
       *                     = com.chb.coses.cashCard.business.delegate.action.CashCardBizAction
       *
       ***************************************************************************/
      configFileName = Config.getInstance().getElement(Constants.BIZDELEGATE_TAG).getTextTrim();
      requestName = event.getAction();
      methodName = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(Constants.METHOD_TAG);

      // BizAction에서 구해온 값들이다.
      System.out.println("ConfigFileName : " + configFileName);
      System.out.println("RequestName : " + requestName);
      System.out.println("methodName : " + methodName);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      LOGEJ.getInstance().eprintf(5,event,ex);
      return null;
    }
    return methodName;
  }

  /**
   * request_name = "cashCard-listCashcard"
   *              = "system" + "-" + "method"
   * @param request_name
   * @return
   */
  public String TPgetcallsystemname(String request_name)
  {
    System.out.println("[request_name]-"+request_name);
    if( request_name == null ){
      return null;
    }
    else{
      int lastSlash = request_name.lastIndexOf('-');
      String method_name = request_name.substring(lastSlash+1);
      String operation_classname = request_name.substring(0, lastSlash);
      return operation_classname;
    }
  }

  /**
   * request_name = "cashCard-listCashcard"
   *              = "system" + "-" + "method"
   * @param request_name
   * @return
   */
  public String TPgetcallmethodname(String request_name)
  {
    if( request_name == null ){
      return null;
    }
    else{
      int lastSlash = request_name.lastIndexOf('-');
      String method_name = request_name.substring(lastSlash+1);
      String operation_classname = request_name.substring(0, lastSlash);
      return method_name;
    }
  }

  public static final String OPERATION_TAG = "operation-class";
  public String TPgetoperationclassname(EPlatonEvent event)
  {

    String configFileName = null;
    String requestName = null;
    String operationclass = null;

    try{
      /***************************************************************************
       * BizAction을 구성한다.
       * [1] public static final String BIZDELEGATE_TAG = "bizaction-map-filename";
       *     - <bizaction-map-filename>
       *        /weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/bizaction-map.xml
       *       </bizaction-map-filename>
       * [2] requestName = event.getAction() = "cashCard-listCashCard"
       * [3] actionClassName = XMLCache.getInstance().getXML(configFileName).
       *                                getRootElement().getChild(requestName).
       *                                getChildTextTrim(Constants.ACTIONCLASS_TAG)
       *                     = com.chb.coses.cashCard.business.delegate.action.CashCardBizAction
       *
       ***************************************************************************/
      configFileName = Config.getInstance().getElement(Constants.BIZDELEGATE_TAG).getTextTrim();
      requestName = event.getAction();
      operationclass = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(OPERATION_TAG);

      // BizAction에서 구해온 값들이다.
      System.out.println("ConfigFileName : " + configFileName);
      System.out.println("RequestName : " + requestName);
      System.out.println("operationclass : " + operationclass);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      LOGEJ.getInstance().eprintf(5,event,ex);
      return null;
    }
    return operationclass;
  }

  public String TPgetparametertypename(EPlatonEvent event)
  {

    String configFileName = null;
    String requestName = null;
    String parameterTypeName = null;

    try{
      /***************************************************************************
       * BizAction을 구성한다.
       * [1] public static final String BIZDELEGATE_TAG = "bizaction-map-filename";
       *     - <bizaction-map-filename>
       *        /weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/bizaction-map.xml
       *       </bizaction-map-filename>
       * [2] requestName = event.getAction() = "cashCard-listCashCard"
       * [3] actionClassName = XMLCache.getInstance().getXML(configFileName).
       *                                getRootElement().getChild(requestName).
       *                                getChildTextTrim(Constants.ACTIONCLASS_TAG)
       *                     = com.chb.coses.cashCard.business.delegate.action.CashCardBizAction
       *
       ***************************************************************************/
      configFileName = Config.getInstance().getElement(Constants.BIZDELEGATE_TAG).getTextTrim();
      requestName = event.getAction();
      parameterTypeName = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(Constants.TYPE_TAG);

      // BizAction에서 구해온 값들이다.
      System.out.println("ConfigFileName : " + configFileName);
      System.out.println("RequestName : " + requestName);
      System.out.println("parameterTypeName : " + parameterTypeName);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      LOGEJ.getInstance().eprintf(5,event,ex);
      return null;
    }
    return parameterTypeName;
  }


  public int TPinfo(UserTransaction tx)
  {
    try{
      switch( tx.getStatus() )
      {
        case 0:
          /////////////////////////////////////////////////////////////////////
          // 트랜잭션이 이미 시작 했습니다.
          /////////////////////////////////////////////////////////////////////
          return 1;
        case 6:
          /////////////////////////////////////////////////////////////////////
          // 트랜잭션이 시작전입니다.
          /////////////////////////////////////////////////////////////////////
          return 0;
        default:
          return -1;
      }
    }
    catch(SystemException ex){
      ex.printStackTrace();
    }
    return -1;
  }


  public int TPinfo()
  {
    return 0;
  }


  /**
   * 해당 URL에 대한 UserTransaction에 객체를 구한다.
   *
   * @param url
   * @return
   */


  public UserTransaction TPJNDIUserTransaction (String url) {
    UserTransaction tx = null;
    try{
      tx = (UserTransaction)(getInitialContext(url)).lookup("javax.transaction.UserTransaction");
    }
    catch(NamingException ex){
      ex.printStackTrace();
      return null;
    }
    return tx;
  }



  public boolean TPbegin(UserTransaction tx,int second)
  {
    try{
      System.out.println("----------tpbegin-before-----tx.getStatus():"+tx.getStatus());
      tx.setTransactionTimeout(second);
      tx.begin();
      System.out.println("-----------------tx.getStatus(): after"+tx.getStatus());

    }
    catch(SystemException ex){
      ex.printStackTrace();
      return false;
    }
    catch(NotSupportedException ex){
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean TPbegin(String second) throws Exception
  {
    return true;
  }

  public boolean TPbegin( ) throws Exception
  {
    return true;
  }


  public boolean TPcommit(UserTransaction tx)
  {
    try{
      System.out.println("UserTrasactin Info tx : " + tx);
      tx.commit();
    }
    catch(Exception ex){
      ex.printStackTrace();
      return false;
    }
    return true;

  }

  public boolean TPcommit()
  {
    try{
      System.out.println("ContainerTrasactin Info tx : call"  );
    }
    catch(Exception ex){
      ex.printStackTrace();
      return false;
    }
    return true;

  }

  public boolean TProllback(UserTransaction tx)
  {
    try{
      tx.rollback();
    }
    catch(Exception ex){
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean TProllback(SessionContext ctx)
  {
    try{
      ctx.setRollbackOnly();
    }
    catch(Exception ex){
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  /****************************************************************************
   * 최초 클라이언트(WAF,ATM)등에서 BizDelegate Session Bean을 통해 호출할 경우에는
   * 입력객체의 주요정보와 출력정보의 주요객체를 저장해서 트랜잭션중에 리턴되지 않거나
   * long transaction을 찾는 작업을 위해서 사용한다.
   * 이 메소드와 쌍이되는 메소드는 TPSdbsend() 메듈에서 client로의 return전에 데이타를
   * 받아서 온다.
   * 예외에 대한 처리는 무조건 성공인것으로 한다. 왜냐하면 트랜잭션에 영향을 주면 안되므로
   * 이상
   *
   * @param event
   * @return
   */

  public void TPSDBrecv(EPlatonEvent event) {
    try{
      TransactionControlDAO dao = new  TransactionControlDAO();

      if( !dao.DB_INSERT_tpminlog(event) ){
        LOGEJ.getInstance().printf(5,event,"TPSDBrecv() 에러");
      }
      LOGEJ.getInstance().printf(5,event,"TPSDBrecv() 성공");
    }
    catch(Exception ex){
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,ex);
      ex.printStackTrace();
    }
  }

  /**
   *
   * @param event
   * @param responsetime
   */
  public void TPSDBsend(EPlatonEvent event,long interval_seconds)
  {
      try{
    TransactionControlDAO dao = new  TransactionControlDAO();

    if( !dao.DB_INSERTtpmoutlog(event,interval_seconds) ){
      LOGEJ.getInstance().printf(5,event,"TPSDBrecv() 에러");
    }
    LOGEJ.getInstance().printf(5,event,"TPSDBrecv() 성공");
  }
  catch(Exception ex){
    LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,ex);
    ex.printStackTrace();
  }
}



/**
 * 수행로직
 * 1. 클라이언트로부터 최초의 데이타를 받는다
 * 2. 기본데이타 검증을 한다
 * 3. 트랜잭션의 번호를 채번한다.
 *
 * @param event : 클라이언트와 서버간 메시지송수신 객체
 * @return
 */
public EPlatonEvent TPSrecv(EPlatonEvent event)
{
  Connection con = null;
  String hostseq = null;
  EPlatonCommonDTO commonDTO = (EPlatonCommonDTO)event.getCommon();
  TPSVCINFODTO tpsvcinfo = (TPSVCINFODTO)event.getTPSVCINFODTO();

  try
  {
    /*************************************************************************
     * 모든 업무EJB 서버는 자신의 TPM 정보만을 관리한다.
     *************************************************************************/
    HashMap hm = new HashMap();
    tpsvcinfo.setTpmsvcinfolist(hm);

    /*************************************************************************
     * 호출할 시스템에 대해서 셋팅을 한다.
     *************************************************************************/
    // 호출할 시스템 명
    tpsvcinfo.setSystem_name(TPMSVCAPI.getInstance().TPgetcallsystemname(tpsvcinfo.getReqName() ) );
    // 호출할 시스템으로 라우팅할 action 클래스명
    tpsvcinfo.setAction_name(TPMSVCAPI.getInstance().TPgetactionclassname(event)  );
    // 호출할 시스템 클래스명
    tpsvcinfo.setOperation_name(TPMSVCAPI.getInstance().TPgetoperationclassname(event)  );
    // 호출할 시스템 클래스의 메소드명
    tpsvcinfo.setOperation_method(TPMSVCAPI.getInstance().TPgetinvokemethodname(event)  );
    // 호출할 시스템 클래스의 메소드에 전달할 CDTO 클래스명
    tpsvcinfo.setCdto_name(TPMSVCAPI.getInstance().TPgetparametertypename(event)  );

      /*
    * 클라이언트에서 서버로의 서비스호출시 거래코드가 셋팅되어 있는지 조사해
    * 거래코드가 있을경우에만 서비스를 실시한다.
    * 1차적인 데이타검증을 실시한다.
       */
    if( commonDTO.getEventNo() == null )
    {
      tpsvcinfo.setErrorcode("EDEL0001");
      tpsvcinfo.setError_message("EPlatonCommonDTO.eventno is not set");
      commonDTO.setEventNo("********");
      LOGEJ.getInstance().printf(3,event,"EPlatonCommonDTO.eventno is not set");
    }


      /*
    * 클라이언트에서 서버로의 서비스호출시 클라이언트의 레벨정보가 셋팅되어 있는지 조사해
    * 클라이언트의 레벨정보가 있을경우에만 서비스를 실시한다.
    * 1차적인 데이타검증을 실시한다.
    *
    * TPFQ 데이타 정보                                                        *
    * 100 : Server System - Server System 연동 => 거래번호 신규채번 => OrgSeq은 그대로 유지
    * 200 : WAF - Server System 연동 => 거래번호를 신규채번 => OrgSeq은 신규채번 번호로 대입
    * 300 : ATM - Server System 연동 => 거래번호를 신규채번 => OrgSeq은 신규채번 번호로 대입
    * 400 : ATM - Server System 연동 => 거래번호를 신규채번 => OrgSeq은 신규채번 번호로 대입
       */
    if( tpsvcinfo.getTpfq() != null ){
      if( !tpsvcinfo.getTpfq().equals("100") ){
        tpsvcinfo.setHostseq("********");
        tpsvcinfo.setOrgseq("********");
      }
      else{
        tpsvcinfo.setHostseq("********");
      }
    }
    else{
      tpsvcinfo.setErrorcode("EDEL0002");
      tpsvcinfo.setError_message("TPFQ IS NULL");
      LOGEJ.getInstance().printf(3,event,"TPFQ IS NULL");
    }


      /*
    * WAS로부터 하나의 DB세션을 구한후 거래번호를 채번한다
       */
    con = DBService.getInstance().getConnection(
        Config.getInstance().getElement("connection_pool").getChild("name").getTextTrim());
    hostseq = CommonUtil.gethostseq(con);

      /*
    * DB세션과 거래번호가 정상적으로 채번되었는지 확인한다.
       */
    if( con != null && hostseq != null)
    {
      if( !tpsvcinfo.getTpfq().equals("100") ){
        tpsvcinfo.setHostseq(hostseq);
        tpsvcinfo.setOrgseq(hostseq);
      }
      else{
        tpsvcinfo.setHostseq(hostseq);
      }
    }
    else
    {
      tpsvcinfo.setErrorcode("EDL999");
      tpsvcinfo.setError_message("TPSrecv gethostseq() error");
      LOGEJ.getInstance().printf(3,event,"==============================================================================");
      LOGEJ.getInstance().printf(3,(EPlatonEvent)event,
                                 "TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
      LOGEJ.getInstance().printf(3,event,"EDL999에러가 발생");
    }

  }
  catch(Exception ex){
    ex.printStackTrace();
    tpsvcinfo.setErrorcode("EDL997");
    tpsvcinfo.setError_message("TPSrecv Connection get error");
    LOGEJ.getInstance().eprintf(5,event,ex);
    LOGEJ.getInstance().printf(3,event,"==============================================================================");
    LOGEJ.getInstance().printf(3,(EPlatonEvent)event,"TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
    LOGEJ.getInstance().printf(3,event,"EDL997 에러가 발생");
  }

  try
  {
    con.close();
  }
  catch(Exception connex){
    connex.printStackTrace();
  }


  System.out.println("==============================================================================");
  System.out.println("TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));



  LOGEJ.getInstance().printf(3,event,"==============================================================================");
  LOGEJ.getInstance().printf(3,(EPlatonEvent)event,
                             "TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
  return event;
}


/**
 * 클라이언트로 데이타전송하고 전송정보를 남긴다.
 * @param event : 클라이언트와 서버간 메시지 송수신 객체
 * @return
 */

public EPlatonEvent TPSsend(EPlatonEvent event)
{
  EPlatonCommonDTO commonDTO = (EPlatonCommonDTO)event.getCommon();
  TPSVCINFODTO tpsvcinfo = (TPSVCINFODTO)event.getTPSVCINFODTO();
  LOGEJ.getInstance().printf(3,event,"errorcode:["+tpsvcinfo.getErrorcode()+"]");
  LOGEJ.getInstance().printf(3,(EPlatonEvent)event,"TPSsend()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
  LOGEJ.getInstance().printf(3,event,"==============================================================================\n\n");


  System.out.println("errorcode:["+tpsvcinfo.getErrorcode()+"]");
  System.out.println("TPSsend()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
  System.out.println("==============================================================================\n\n");


  return event;
}

}