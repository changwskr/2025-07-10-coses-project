package com.ims.oversea.framework.transaction.tpmutil;

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
import weblogic.transaction.TxHelper;

import com.chb.coses.foundation.log.*;
import com.chb.coses.foundation.base.*;
import com.chb.coses.foundation.config.*;
import com.chb.coses.foundation.jndi.*;
import com.chb.coses.foundation.utility.*;

import com.chb.coses.framework.transfer.*;
import com.chb.coses.foundation.db.DBService;
import com.ims.oversea.foundation.config.Config;
//import com.chb.coses.framework.constants.*;
import com.ims.oversea.framework.transaction.constant.TCFConstants;
import com.ims.oversea.framework.transaction.constant.TCFConstantErrcode;
import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.eplatonframework.business.delegate.action.*;
import com.ims.oversea.foundation.utility.*;
import com.ims.oversea.foundation.logej.LOGEJ;
import com.ims.oversea.framework.transaction.tcf.STF;
import com.ims.oversea.framework.transaction.model.TransactionLogDDTO;
import com.ims.oversea.framework.transaction.dao.*;


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


/*******************************************************************************
 * bizactionmap파일의 추가 태그
 * operation-class
 *******************************************************************************
<cashCard-listCashCard>
        <bizaction-class>com.chb.coses.cashCard.business.delegate.action.CashCardBizAction</bizaction-class>
        <transactionable>Y</transactionable>
        <bizaction-method>listCashCard</bizaction-method>
        <bizaction-parametertype>com.chb.coses.cashCard.transfer.CashCardConditionCDTO</bizaction-parametertype>
        <operation-class>com.ims.oversea.cashCard.business.facade.CashCardManageMent</operation-class>
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
        //System.out.println(igex);
        igex.printStackTrace();
        return null;
      }
    }
    return instance;
  }

  public TPMSVCAPI(String ip,String port) throws Exception{
    try{
      url = "t3://"+ip+":"+port;
      //System.out.println("TCPCallEJB URL : "+ url );
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
        //System.out.println(igex);
        igex.printStackTrace();
        return null;
      }
    }
    return instance;
  }

  public TPMSVCAPI() throws Exception{
    try{
      url = com.ims.oversea.foundation.constant.Constants.call_url;
      //System.out.println("--2 TCPCallEJB URL : "+ url );
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

   *     실예) com.ims.oversea.cashCard.business.facade.CashCardManagementSBBean의
   *           listCashCard 메소드호출할 경우
   *
   *           (1)cashCard.xml 파일
   *              <cashCard-listCashCard>
   *                <bizaction-class>com.chb.coses.cashCard.business.delegate.action.CashCardBizAction</bizaction-class>
   *                <transactionable>Y</transactionable>
   *                <bizaction-method>listCashCard</bizaction-method>
   *                <bizaction-parametertype>com.chb.coses.cashCard.transfer.CashCardConditionCDTO</bizaction-parametertype>
   *              </cashCard-listCashCard>

   *           (2) com.ims.oversea.eplatonframework.transfer.EPlatonCommonDTO.requestName 셋팅
   *               com.ims.oversea.eplatonframework.transfer.EPlatonCommonDTO.requestName = cashCard-listCashCard
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
          <operation-class>com.ims.oversea.cashCard.business.facade.CashCardManageMent</operation-class>
        </cashCard-listCashCard>
        *
        * [3] cashCard.xml에 다음을 추가한다.
        *    <operation-class>com.ims.oversea.cashCard.business.facade.CashCardManageMent</operation-class>
        *****************************************************************************
        *
        * @return
        */

/*
 * spcashcard.xml 정보

 <spcashcard-callmethod01>
        <bizaction-class>com.ims.oversea.eplatonframework.business.delegate.action.SPcashcardBizAction</bizaction-class>
        <transactionable>Y</transactionable>
        <action-call-bean-type>local</action-call-bean-type>
        <bean-transaction-type>container</bean-transaction-type>
        <bizaction-method>callmethod01</bizaction-method>
        <operation-class>com.ims.oversea.cashCard.business.facade.CashCardManagementSBBean</operation-class>
        <bizaction-parametertype>com.ims.oversea.eplatonframework.transfer.EPLcommonCDTO</bizaction-parametertype>
</spcashcard-callmethod01>


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
      configFileName = Config.getInstance().getElement(TCFConstants.BIZDELEGATE_TAG).getTextTrim();
      requestName = (event.getTPSVCINFODTO()).getReqName() ;
      event.setAction(requestName);
      //System.out.println("ConfigFileName : " + configFileName);
      //System.out.println("RequestName : " + requestName);

      // BizAction에서 구해온 값들이다.
      actionClassName = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(TCFConstants.ACTIONCLASS_TAG);

      // BizAction에서 구해온 값들이다.
      //System.out.println("ActionClassName : " + actionClassName);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,event,ex);
      return null;
    }
    return actionClassName;
  }

  public String TPgetcallbeantype(EPlatonEvent event)
  {
    String configFileName = null;
    String requestName = null;
    String xml_rtn_value = null;
    try{
      configFileName = Config.getInstance().getElement(TCFConstants.BIZDELEGATE_TAG).getTextTrim();
      requestName = (event.getTPSVCINFODTO()).getReqName() ;
      xml_rtn_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(TCFConstants.CALL_BEAN_TYPE_TAG);
      //System.out.println("TPgetcallbeantype()-xml_rtn_value:[" + xml_rtn_value + "]");
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,event,ex);
      return TCFConstants.BEAN_TYPE;
    }
    return xml_rtn_value;
  }

  public String TPgetbeantransactiontype(EPlatonEvent event)
  {
    String configFileName = null;
    String requestName = null;
    String xml_rtn_value = null;
    try{
      configFileName = Config.getInstance().getElement(TCFConstants.BIZDELEGATE_TAG).getTextTrim();
      requestName = (event.getTPSVCINFODTO()).getReqName() ;
      xml_rtn_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(TCFConstants.BEAN_TRANSACTION_TYPE_TAG);
      //System.out.println("TPgetbeantransactiontype()-xml_rtn_value:[" + xml_rtn_value + "]");
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,event,ex);
      return TCFConstants.NO_TX_BEAN_TYPE;
    }
    return xml_rtn_value;
  }


  protected void TPgetrequestinfo(EPlatonEvent event)
  {
    String configFileName = Config.getInstance().getElement(TCFConstants.BIZDELEGATE_TAG).getTextTrim();
    String requestName = event.getAction();
    String actionClassName = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(TCFConstants.ACTIONCLASS_TAG);
    String methodName = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(TCFConstants.METHOD_TAG);
    String parameterTypeName = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(TCFConstants.TYPE_TAG);

    //System.out.println("ConfigFileName : " + configFileName);
    //System.out.println("RequestName : " + requestName);
    //System.out.println("methodName : " + methodName);
    //System.out.println("parameterTypeName : " + parameterTypeName);
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
      configFileName = Config.getInstance().getElement(TCFConstants.BIZDELEGATE_TAG).getTextTrim();
      requestName = event.getAction();
      methodName = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(TCFConstants.METHOD_TAG);

      // BizAction에서 구해온 값들이다.
      //System.out.println("ConfigFileName : " + configFileName);
      //System.out.println("RequestName : " + requestName);
      //System.out.println("methodName : " + methodName);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,event,ex);
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
  public String TPgetcallsystemname(String request_name) throws Exception
  {
    try{
      //System.out.println("[request_name]-"+request_name);
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
    catch(Exception ex){
      throw ex;
    }
  }

  /**
   * request_name = "cashCard-listCashcard"
   *              = "system" + "-" + "method"
   * @param request_name
   * @return
   */
  public String TPgetcallmethodname(String request_name) throws Exception
  {
    try{
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
    catch(Exception ex){
      throw ex;
    }

  }


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
      configFileName = Config.getInstance().getElement(TCFConstants.BIZDELEGATE_TAG).getTextTrim();
      requestName = event.getAction();
      operationclass = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(TCFConstants.OPERATION_TAG);

      // BizAction에서 구해온 값들이다.
      //System.out.println("ConfigFileName : " + configFileName);
      //System.out.println("RequestName : " + requestName);
      //System.out.println("operationclass : " + operationclass);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,event,ex);
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
      configFileName = Config.getInstance().getElement(TCFConstants.BIZDELEGATE_TAG).getTextTrim();
      requestName = event.getAction();
      parameterTypeName = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(requestName).getChildTextTrim(TCFConstants.TYPE_TAG);

      // BizAction에서 구해온 값들이다.
      //System.out.println("ConfigFileName : " + configFileName);
      //System.out.println("RequestName : " + requestName);
      //System.out.println("parameterTypeName : " + parameterTypeName);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,event,ex);
      return null;
    }
    return parameterTypeName;
  }

  public int TPinfo()
  {
    TransactionManager tm = null;

    try
    {
      tm = TxHelper.getTransactionManager();
      return  tm.getStatus();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return -1;
    }

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
      //System.out.println("----------tpbegin-before-----tx.getStatus():"+tx.getStatus());
      tx.setTransactionTimeout(second);
      tx.begin();
      //System.out.println("-----------------tx.getStatus(): after"+tx.getStatus());

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
    try{
      TransactionManager tm = TxHelper.getTransactionManager();
      tm.setTransactionTimeout(CommonUtil.Str2Int(second) );
    }
    catch(Exception ex){
      ex.printStackTrace();
      return false;
    }
    return true;
  }

  public boolean TPbegin( ) throws Exception
  {
    return true;
  }


  public boolean TPcommit(UserTransaction tx)
  {
    try{
      //System.out.println("UserTrasactin Info tx : " + tx);
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
      //System.out.println("ContainerTrasactin Info tx : call"  );
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

  public boolean TProllback(SessionContext ctx,EPlatonEvent event)
  {
    try{
      ctx.setRollbackOnly();
    }
    catch(Exception ex){
      ex.printStackTrace();
      LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"tprollback()에러가 발생합니다.-" + ex.toString() );
      LOGEJ.getInstance().eprintf(TCFConstants.FATAL_LOG_LEVEL,event,ex);
      return false;
    }
    return true;
  }


  public Transaction TPSsuspend()
  {
    try{
      TransactionManager gtm = null;
      Transaction gtx = null;
      Transaction sustx = null;
      gtm = TxHelper.getTransactionManager();
      gtx = gtm.getTransaction();
      sustx = gtm.suspend();
      return gtx;
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return null;
    }
  }

  public boolean TPSresume(Transaction gtx)
  {
    try{
      TransactionManager gtm = null;
      gtm = TxHelper.getTransactionManager();
      gtm.resume(gtx);
      return true;
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      return false;
    }
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
    Transaction gtx = null;
    int resume_mode = 0;

    try{

      LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"1-----Transaction TPinfo mode : [" + TxHelper.status2String(TPinfo()) +"] tpfq:[" +event.getTPSVCINFODTO().getTpfq()+"]"  );
      switch( TPinfo() )
      {
        case -1: // 에러발생시
          return;
        case 6:  // No Transaction

          break;
        case 0:  // Active Transaction
          LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"2-----Transaction TPinfo mode : [" + TxHelper.status2String(TPinfo()) +"] tpfq:[" +event.getTPSVCINFODTO().getTpfq()+"]"  );

          gtx = TPSsuspend();
          if( gtx == null ){
            LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"TPSDBrecv()-TPSsuspend() 실패");
            return;
          }
          else{
            LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"--->>> TPSDBrecv()-TPSsuspend() 성공");
            resume_mode = 1;
          }
          break;
        default:
          LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"TPSDBrecv()-TPinfo() nothing");
        return;
      }

      TransactionControlDAO dao = new  TransactionControlDAO();

      if( !dao.DB_INSERT_tpminlog(event) ){
        LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"TPSDBrecv() 에러");
      }
      else{
        LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"TPSDBrecv() 성공");
      }

      LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"3-----Transaction TPinfo mode : [" + TxHelper.status2String(TPinfo()) +"] tpfq:[" +event.getTPSVCINFODTO().getTpfq()+"]"  );

      if( resume_mode == 1 ){
        if( TPSresume(gtx) ){
          LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"------>>>> TPSresume() 성공");
        }
        else{
          LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"TPSresume() 실패");
        }
      }
    }
    catch(Exception ex){
      LOGEJ.getInstance().eprintf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)event,ex);
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
    Transaction gtx = null;
    int resume_mode = 0;

    try{

      LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"1-----Transaction TPinfo mode : ["
                                 + TxHelper.status2String(TPinfo()) + "-" + TPinfo()
                                 + "] tpfq:[" +event.getTPSVCINFODTO().getTpfq()+"]"  );
      switch( TPinfo() )
      {
        case -1: // 에러발생시
          return;
        case 6:  // No Transaction
          break;
        case 1:  // Marked Rollback
          // transaction이 marked rollback 상태에서는 resume 모드를 사용할수가 없다.
          gtx = TPSsuspend();
          if( gtx == null ){
            LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"TPSDBsend()-TPSsuspend() 실패");
            return;
          }
          else{
            LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"--->>> TPSDBsend()-TPSsuspend() 성공");
          }
          break;
        case 0:  // Active Transaction
          gtx = TPSsuspend();
          if( gtx == null ){
            LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"TPSDBsend()-TPSsuspend() 실패");
            return;
          }
          else{
            LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"--->>> TPSDBsend()-TPSsuspend() 성공");
            resume_mode = 1 ;
          }
          break;
        default:
          LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"TPSDBsend()-TPinfo() nothing");
        return;
      }


      TransactionControlDAO dao = new  TransactionControlDAO();

      /**
       * 트랜잭션의 정보를 주요정보만 데이타베이스에 남긴다.
       * 최초의 200인 클라이언트의 경우 not support인 biz delegate을 타므로서
       * 무조건 최초의 패킷은 저장된다.
       * 하지만 100인 서버끼리의 연동은 required이므로 정상일경우만 처리된다.
       * 그러나 파일정보는 남을 것이다.
       */
      if( !dao.DB_INSERTtpmoutlog(event,interval_seconds) ){
        LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"TPSDBsend() 에러");
      }
      else{
        LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"TPSDBsend() 성공");
      }

      if( resume_mode == 1 ){
        if( TPSresume(gtx) ){
          LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"------>>>> TPSresume() 성공");
        }
        else{
          LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL ,event,"TPSresume() 실패");
        }
      }

      TransactionLogEntity.insertTransactionLog2File(event,interval_seconds);
    }
    catch(Exception ex){
      LOGEJ.getInstance().eprintf(TCFConstants.FATAL_LOG_LEVEL,(EPlatonEvent)event,ex);
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
      String action_name = TPMSVCAPI.getInstance().TPgetactionclassname(event);
      if( action_name == null ){
        tpsvcinfo.setErrorcode(TCFConstantErrcode.ETPM001);
        tpsvcinfo.setError_message(TCFConstantErrcode.ETPM001_MSG);
        LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL ,event,"ERROR-Cannot get action_class_name");
      }
      else{
        tpsvcinfo.setAction_name(action_name);
        String operation_class = TPMSVCAPI.getInstance().TPgetoperationclassname(event);
        if ( operation_class == null ){
          tpsvcinfo.setErrorcode(TCFConstantErrcode.ETPM002);
          tpsvcinfo.setError_message(TCFConstantErrcode.ETPM002_MSG);
          LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,event,"ERROR-Cannot get operation_class_name");
        }
        else{
          // 호출할 시스템 클래스명
          tpsvcinfo.setOperation_name(operation_class );
          // 호출할 시스템 클래스의 메소드명
          String method_name = TPMSVCAPI.getInstance().TPgetinvokemethodname(event);
          if( method_name == null ){
            tpsvcinfo.setErrorcode(TCFConstantErrcode.ETPM003);
            tpsvcinfo.setError_message(TCFConstantErrcode.ETPM003_MSG);
            LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,event,"ERROR-Cannot get operation_method_name");
          }
          else{
            tpsvcinfo.setOperation_method( method_name );
            // 호출할 시스템 클래스의 메소드에 전달할 CDTO 클래스명
            String parametertype = TPMSVCAPI.getInstance().TPgetparametertypename(event);
            if( parametertype == null ) {
              tpsvcinfo.setErrorcode(TCFConstantErrcode.ETPM004);
              tpsvcinfo.setError_message(TCFConstantErrcode.ETPM004_MSG);
              LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,event,"ERROR-Cannot get parametertype");
            }
            else{
              tpsvcinfo.setCdto_name( parametertype );
            }
          }
        }
      }

      /*
      * 클라이언트에서 서버로의 서비스호출시 거래코드가 셋팅되어 있는지 조사해
      * 거래코드가 있을경우에만 서비스를 실시한다.
      * 1차적인 데이타검증을 실시한다.
       */
      if( commonDTO.getEventNo() == null || commonDTO.getEventNo() == "" )
      {
        tpsvcinfo.setErrorcode(TCFConstantErrcode.ETPM005);
        tpsvcinfo.setError_message(TCFConstantErrcode.ETPM005_MSG);
        commonDTO.setEventNo(TCFConstants.EVENTNO_INITIAL_STR);
        LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,event,"EPlatonCommonDTO.eventno is not set");
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
      if( tpsvcinfo.getTpfq() != null && tpsvcinfo.getTpfq() != "" ){
        if( !tpsvcinfo.getTpfq().equals(TCFConstants.SERVER_SERVER_INTERCHANGE_LOC) ){
          tpsvcinfo.setHostseq(TCFConstants.HOSTSEQ_INITIAL_STR);
          tpsvcinfo.setOrgseq(TCFConstants.HOSTSEQ_INITIAL_STR);
        }
        else{
          tpsvcinfo.setHostseq(TCFConstants.HOSTSEQ_INITIAL_STR);
        }
      }
      else{
        tpsvcinfo.setErrorcode(TCFConstantErrcode.ETPM006);
        tpsvcinfo.setError_message(TCFConstantErrcode.ETPM006_MSG);
        LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,event,"ERROR-not set tpfq:100,200,300...800");
      }


      /*
      * WAS로부터 하나의 DB세션을 구한후 거래번호를 채번한다
       */
      con = DBService.getInstance().getConnection(
          Config.getInstance().getElement("connection_pool").getChild("name").getTextTrim());
      hostseq = CommonUtil.gethostseq(con);

      /**
       * 기본정보를 초기화 시킨다.
       * 이것은 TCF의 STF 모듈을 제되로 수행하지 못했을시 경우 예상       */

      tpsvcinfo.setErrorcode(TCFConstants.SUCCESS_ERRCODE);
      tpsvcinfo.setSystemInTime( CommonUtil.GetSysTime() );
      tpsvcinfo.setSystemOutTime( tpsvcinfo.getSystemInTime() );
      tpsvcinfo.setSystem_date( CommonUtil.GetSysDate() );
      commonDTO.setSystemInTime( tpsvcinfo.getSystemInTime() );
      commonDTO.setSystemOutTime( tpsvcinfo.getSystemInTime() );
      commonDTO.setSystemDate( CommonUtil.GetSysDate() );

      /*
      * DB세션과 거래번호가 정상적으로 채번되었는지 확인한다.
       */
      if( con != null && hostseq != null)
      {
        if( !tpsvcinfo.getTpfq().equals(TCFConstants.SERVER_SERVER_INTERCHANGE_LOC) ){
          tpsvcinfo.setHostseq(hostseq);
          tpsvcinfo.setOrgseq(hostseq);
          commonDTO.setTransactionNo(hostseq);
        }
        else{
          tpsvcinfo.setHostseq(hostseq);
          commonDTO.setTransactionNo(hostseq);
        }
      }
      else
      {
        tpsvcinfo.setErrorcode(TCFConstantErrcode.ETPM007);
        tpsvcinfo.setError_message(TCFConstantErrcode.ETPM007_MSG);
        LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,event,"==============================================================================");
        LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,(EPlatonEvent)event,
                                   "TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
        LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,event,"TPSrecv()-error");
      }
    }
    catch(Exception ex){
      ex.printStackTrace();
      tpsvcinfo.setErrorcode(TCFConstantErrcode.ETPM008);
      tpsvcinfo.setError_message(TCFConstantErrcode.ETPM008_MSG);
      LOGEJ.getInstance().printf(TCFConstants.FATAL_LOG_LEVEL,event,ex);
      LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,event,"==============================================================================");
      LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,(EPlatonEvent)event,"TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
      LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,(EPlatonEvent)event,"TPSrecv()-error code : [" + tpsvcinfo.getErrorcode() + "]");
      try{
        con.close();
      }
      catch(Exception connex){
        connex.printStackTrace();
      }
      return event;
    }

    try
    {
      con.close();
    }
    catch(Exception connex){
      connex.printStackTrace();
    }

    if( tpsvcinfo.getHostseq().equals(TCFConstants.HOSTSEQ_INITIAL_STR) )
    {
      tpsvcinfo.setErrorcode(TCFConstantErrcode.ETPM009);
      tpsvcinfo.setError_message(TCFConstantErrcode.ETPM009_MSG);
      LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,event,"TPSrecv()-error-hostseq is ******:errcode["+tpsvcinfo.getErrorcode()+"]" );
    }

    LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,event,"==============================================================================");
    LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,(EPlatonEvent)event,
                               "TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
    LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,(EPlatonEvent)event,"error code : [" + tpsvcinfo.getErrorcode() + "]");
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
    LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,event,"TPSsend()-errorcode:["+tpsvcinfo.getErrorcode()+"]");
    LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,(EPlatonEvent)event,"TPSsend()-return data["+com.chb.coses.foundation.utility.Reflector.objectToString(event) +"]");
    LOGEJ.getInstance().printf(TCFConstants.TPM_LOG_LEVEL,event,"==============================================================================\n\n");

    return event;
  }


}