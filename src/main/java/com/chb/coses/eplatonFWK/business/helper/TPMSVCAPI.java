package com.chb.coses.eplatonFWK.business.helper;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import java.io.*;
import java.util.*;
import java.rmi.*;
import javax.naming.*;
import javax.ejb.*;
import javax.transaction.*;
import java.math.BigDecimal;
import javax.rmi.PortableRemoteObject;
import java.sql.*;

import com.chb.coses.cosesFramework.exception.*;
import com.chb.coses.framework.exception.*;
import com.chb.coses.framework.exception.BizDelegateException;
import com.chb.coses.framework.transfer.*;
import com.chb.coses.cosesFramework.transfer.*;
import com.chb.coses.cosesFramework.business.delegate.*;

import com.chb.coses.foundation.jndi.JNDIService;
import com.chb.coses.eplatonFWK.transfer.*;
import com.chb.coses.eplatonFWK.business.delegate.action.EPlatonBizAction;
import com.chb.coses.eplatonFWK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFWK.business.helper.*;
import com.chb.coses.eplatonFWK.business.helper.logej.LOGEJ;

import com.chb.coses.foundation.db.DBService;
import com.chb.coses.foundation.config.Config;

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
      event = new CosesEvent();
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
      url = "t3://localhost:7001";
      System.out.println("TCPCallEJB URL : "+ url );
      ctx = this.getInitialContext();
      event = new CosesEvent();
    }catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }
  }

  public EPlatonEvent TPSsendrecv(EPlatonEvent event)
  {
    IEvent resevent = null;

    try{
      resevent = event;
      String actionClassName = ((EPlatonEvent)event).getTPSVCINFODTO().getAction_name(); //actionClassName="com.chb.coses.eplatonFWK.business.delegate.action.CashCardBizAction"
      EPlatonBizAction action = (EPlatonBizAction)(Class.forName(actionClassName).newInstance());
      resevent = action.act(event);
    }
    catch( ClassNotFoundException ex ){
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setErrorcode("EBD300");
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setError_message("TPSsendrecv() 에러");
      ex.printStackTrace();
    }
    catch ( IllegalAccessException ex ){
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setErrorcode("EBD300");
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setError_message("TPSsendrecv() 에러");
      ex.printStackTrace();
    }
    catch ( InstantiationException ex){
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setErrorcode("EBD300");
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setError_message("TPSsendrecv() 에러");
      ex.printStackTrace();
    }
    catch ( Exception ex){
      switch( ((EPlatonEvent)resevent).getTPSVCINFODTO().getErrorcode().charAt(0) )
      {
        case 'E' :
          System.out.println("TPSsendrecv()시에 타시스템에서 예외발생");
          break;
        case 'I' :
          break;
      }
      ex.printStackTrace();
    }

    return (EPlatonEvent)resevent;
  }

  public EPlatonEvent TPSsendrecv(String porgSystem,
                                  String psystemName,
                                  String pactionClassName,
                                  String poperationName,
                                  EPlatonEvent pevent,
                                  EPlatonEvent event)
  {
    IEvent resevent = null;
    TPSVCINFODTO tpsvcinfoDTO = null;
    EPlatonCommonDTO commonDTO = null;
    TPMSVCINFO tpmsvcinfo = null;

    try{
      resevent = event;
      tpsvcinfoDTO = event.getTPSVCINFODTO();
      commonDTO = event.getCommon();
      tpmsvcinfo = new TPMSVCINFO(psystemName,event);

      String systemName = psystemName; // Deposit
      String actionClassName =  pactionClassName; //actionClassName="com.chb.coses.eplatonFWK.business.delegate.action.DepositBizAction"
      String operationName = poperationName; // com.chb.coses.eplatonFWK.othersystem.operation.DED0021000
      String operationMethod = tpsvcinfoDTO.getOperation_method(); // com.chb.coses.eplatonFWK.othersystem.operation.DED0021000

      Object tobj = event.getRequest();
      if(!(tobj instanceof IDTO) ) {
        tpsvcinfoDTO.setErrorcode("EFWK0042");
        tpsvcinfoDTO.setError_message(this.getClass().getName()+ ".TPSsendrecv():IDTO not valid");
        LOGEJ.getInstance().printf(porgSystem,commonDTO.getEventNo(),tpsvcinfoDTO.getOrgseq(),"TPMAPI.TPSsendrecv():EFWK0042:exception");

        tpmsvcinfo.setError_code("EFWK0042");
        tpmsvcinfo.setCall_tpm_out_time(CommonUtil.GetSysTime());

        return event;
      }

      tpsvcinfoDTO.setSystem_name(systemName);
      tpsvcinfoDTO.setAction_name(actionClassName);
      tpsvcinfoDTO.setOperation_name(operationName);
      tpsvcinfoDTO.setOperation_method(tpsvcinfoDTO.getOperation_method());
      tpsvcinfoDTO.setTpfq("100");

      commonDTO.setTimeZone("GMT+09:00");
      commonDTO.setFxRateCount(1);

      LOGEJ.getInstance().printf(porgSystem,commonDTO.getEventNo(), tpsvcinfoDTO.getOrgseq(),"==================[TPSsendrecv() START]");
      LOGEJ.getInstance().printf(porgSystem,commonDTO.getEventNo(),tpsvcinfoDTO.getOrgseq(),"SYSTEM         :"+systemName);
      LOGEJ.getInstance().printf(porgSystem,commonDTO.getEventNo(),tpsvcinfoDTO.getOrgseq(),"actionClassName:"+actionClassName);
      LOGEJ.getInstance().printf(porgSystem,commonDTO.getEventNo(),tpsvcinfoDTO.getOrgseq(),"operationName  :"+operationName);
      LOGEJ.getInstance().printf(porgSystem,commonDTO.getEventNo(),tpsvcinfoDTO.getOrgseq(),"operationMethod  :"+operationMethod);
      LOGEJ.getInstance().printf(porgSystem,commonDTO.getEventNo(),tpsvcinfoDTO.getOrgseq(),"TPM(SEND)-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));

      EPlatonBizAction action = (EPlatonBizAction)(Class.forName(actionClassName).newInstance());
      resevent = action.act(event);

    }
    catch( ClassNotFoundException ex ){
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setErrorcode("EFWK0043");
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setError_message(this.getClass().getName()+ ".TPSsendrecv():"+ex.toString() );
      LOGEJ.getInstance().eprintf((EPlatonEvent)event,ex);
      tpmsvcinfo.setError_code("EFWK0043");
    }
    catch ( IllegalAccessException ex ){
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setErrorcode("EFWK0044");
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setError_message(this.getClass().getName()+ ".TPSsendrecv():"+ex.toString() );
      LOGEJ.getInstance().eprintf((EPlatonEvent)event,ex);
      tpmsvcinfo.setError_code("EFWK0044");
      tpmsvcinfo.setCall_tpm_out_time(CommonUtil.GetSysTime());
    }
    catch ( InstantiationException ex){
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setErrorcode("EFWK0045");
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setError_message(this.getClass().getName()+ ".TPSsendrecv():"+ex.toString() );
      LOGEJ.getInstance().eprintf((EPlatonEvent)event,ex);
      tpmsvcinfo.setError_code("EFWK0045");
      tpmsvcinfo.setCall_tpm_out_time(CommonUtil.GetSysTime());

    }
    catch ( Exception ex){
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setErrorcode("EFWK0046");
      ((EPlatonEvent)resevent).getTPSVCINFODTO().setError_message(this.getClass().getName()+ ".TPSsendrecv():"+ex.toString() );
      LOGEJ.getInstance().eprintf((EPlatonEvent)event,ex);
      tpmsvcinfo.setError_code("EFWK0046");
      tpmsvcinfo.setCall_tpm_out_time(CommonUtil.GetSysTime());

    }

    //////////////////////////////////////////////////////////////////////////
    // TPM SEND / RECV 정보관리
    //////////////////////////////////////////////////////////////////////////
    {
      TPSVCINFODTO tpsvcinfo = ((EPlatonEvent)resevent).getTPSVCINFODTO();

      switch( tpsvcinfo.getErrorcode().charAt(0) )
      {
        case 'I' :
          LOGEJ.getInstance().printf(porgSystem,commonDTO.getEventNo(),tpsvcinfoDTO.getOrgseq(),"TPM(RECV)-"+com.chb.coses.foundation.utility.Reflector.objectToString(resevent));
          LOGEJ.getInstance().printf(porgSystem,commonDTO.getEventNo(),tpsvcinfoDTO.getOrgseq(),"TPSsendrecv() Call Success");
          break;
        case 'E' :
          LOGEJ.getInstance().printf(porgSystem,commonDTO.getEventNo(),tpsvcinfoDTO.getOrgseq(),"TPM(RECV)-"+com.chb.coses.foundation.utility.Reflector.objectToString(resevent));
          LOGEJ.getInstance().printf(porgSystem,commonDTO.getEventNo(),tpsvcinfoDTO.getOrgseq(),"TPSsendrecv() Call Fail");
          break;
      }
    }
    //////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////
    // TPMSVCINFO 정보를 셋팅하고 이정보는 TCF에서 이용한다.
    ////////////////////////////////////////////////////////////////////////////
    LOGEJ.getInstance().printf(porgSystem,commonDTO.getEventNo(),tpsvcinfoDTO.getOrgseq(),"==================[TPSsendrecv() END]");
    tpmsvcinfo.setError_code(((EPlatonEvent)resevent).getTPSVCINFODTO().getErrorcode());
    tpmsvcinfo.setCall_tpm_out_time(CommonUtil.GetSysTime());
    tpmsvcinfo.setCall_tpm_etf_in_time(((EPlatonEvent)resevent).getTPSVCINFODTO().getETF_intime());
    tpmsvcinfo.setCall_tpm_etf_out_time(((EPlatonEvent)resevent).getTPSVCINFODTO().getETF_outtime());
    tpmsvcinfo.setCall_tpm_stf_in_time(((EPlatonEvent)resevent).getTPSVCINFODTO().getSTF_intime());
    tpmsvcinfo.setCall_tpm_stf_out_time(((EPlatonEvent)resevent).getTPSVCINFODTO().getSTF_outtime());
    tpmsvcinfo.setCall_tpme_interval(CommonUtil.Int2Str( CommonUtil.ItvSec(tpmsvcinfo.getCall_tpm_in_time(),tpmsvcinfo.getCall_tpm_out_time()) ));
    tpmsvcinfo.setCall_location(((EPlatonEvent)resevent).getTPSVCINFODTO().getLogic_level());
    pevent.getTPSVCINFODTO().addtpmsvcinfo(tpmsvcinfo);

    return (EPlatonEvent)resevent;
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

  /*
  * 1. 클라이언트로부터 최초의 데이타를 받는다
  * 2. 기본데이타 검증을 한다
  * 3. 트랜잭션의 번호를 채번한다.
  */
  public EPlatonEvent TPSrecv(EPlatonEvent event)
  {
    Connection con = null;
    String hostseq = null;
    EPlatonCommonDTO commonDTO = (EPlatonCommonDTO)event.getCommon();
    TPSVCINFODTO tpsvcinfo = (TPSVCINFODTO)event.getTPSVCINFODTO();

    try
    {
      if( commonDTO.getEventNo() == null )
      {
        tpsvcinfo.setErrorcode("EDEL0001");
        tpsvcinfo.setError_message("EVENT NO IS NULL");
        commonDTO.setEventNo("********");
        LOGEJ.getInstance().printf(event,"EVENT NO IS NULL");
      }

      if( tpsvcinfo.getTpfq() != null )
      {
        if( !tpsvcinfo.getTpfq().equals("100") ){
          tpsvcinfo.setHostseq("********");
          tpsvcinfo.setOrgseq("********");
        }
        else{
          tpsvcinfo.setHostseq("********");
        }
      }
      else
      {
        tpsvcinfo.setErrorcode("EDEL0002");
        tpsvcinfo.setError_message("TPFQ IS NULL");
        LOGEJ.getInstance().printf(event,"TPFQ IS NULL");
      }

      con = DBService.getInstance().getConnection(Config.getInstance().getElement("connection_pool").getChild("name").getTextTrim());
      hostseq = CommonUtil.gethostseq(con);

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
        LOGEJ.getInstance().printf(event,"==============================================================================");
        LOGEJ.getInstance().printf((EPlatonEvent)event,"TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
        LOGEJ.getInstance().printf(event,"EDL999에러가 발생");
      }

    }
    catch(Exception ex){
      ex.printStackTrace();
      tpsvcinfo.setErrorcode("EDL997");
      tpsvcinfo.setError_message("TPSrecv Connection get error");
      LOGEJ.getInstance().eprintf(event,ex);
      LOGEJ.getInstance().printf(event,"==============================================================================");
      LOGEJ.getInstance().printf((EPlatonEvent)event,"TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
      LOGEJ.getInstance().printf(event,"EDL997 에러가 발생");
    }

    try
    {
      con.close();
    }
    catch(Exception connex){
      connex.printStackTrace();
    }

    LOGEJ.getInstance().printf(event,"==============================================================================");
    LOGEJ.getInstance().printf((EPlatonEvent)event,"TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
    return event;
  }


  public EPlatonEvent TPSrecv_org(EPlatonEvent event)
  {
    Connection con = null;
    String hostseq = null;
    EPlatonCommonDTO commonDTO = (EPlatonCommonDTO)event.getCommon();
    TPSVCINFODTO tpsvcinfo = (TPSVCINFODTO)event.getTPSVCINFODTO();

    try{
      if( commonDTO.getEventNo() == null )
      {
        tpsvcinfo.setErrorcode("EDEL0001");
        tpsvcinfo.setError_message("EVENT NO IS NULL");
        commonDTO.setEventNo("********");
        LOGEJ.getInstance().printf(event,"EVENT NO IS NULL");
        return event;
      }
      if( tpsvcinfo.getTpfq()!=null )
      {
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
        LOGEJ.getInstance().printf(event,"TPFQ IS NULL");
        return event;
      }

      con = DBService.getInstance().getConnection(Config.getInstance().getElement("connection_pool").getChild("name").getTextTrim());
      hostseq = CommonUtil.gethostseq(con);
    }
    catch(Exception ex){
      ex.printStackTrace();
      tpsvcinfo.setErrorcode("EDL997");
      tpsvcinfo.setError_message("TPSrecv Connection get error");
      LOGEJ.getInstance().eprintf(event,ex);
      LOGEJ.getInstance().printf(event,"==============================================================================");
      LOGEJ.getInstance().printf((EPlatonEvent)event,"TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
      LOGEJ.getInstance().printf(event,"EDL997 에러가 발생");
      try
      {
        con.close();
      }
      catch(Exception connex){
        connex.printStackTrace();
      }
      return event;
    }

    if( con != null ){
      try
      {
        con.close();
      }
      catch(Exception connex){
        connex.printStackTrace();
        tpsvcinfo.setErrorcode("EDL111");
      }
    }

    if( con != null && hostseq != null)
    {
      try
      {
        if( !tpsvcinfo.getTpfq().equals("100") ){
          tpsvcinfo.setHostseq(hostseq);
          tpsvcinfo.setOrgseq(hostseq);
        }
        else{
          tpsvcinfo.setHostseq(hostseq);
        }
      }catch(Exception ex){
        ex.printStackTrace();
        tpsvcinfo.setErrorcode("EDL998");
        tpsvcinfo.setError_message("TPSrecv gethostseq error");
        LOGEJ.getInstance().printf(event,"==============================================================================");
        LOGEJ.getInstance().printf((EPlatonEvent)event,"TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
        LOGEJ.getInstance().printf(event,"EDL998에러가 발생");
        LOGEJ.getInstance().eprintf(event,ex);
        return event;
      }
    }
    else
    {
      tpsvcinfo.setErrorcode("EDL999");
      tpsvcinfo.setError_message("TPSrecv gethostseq() error");
      LOGEJ.getInstance().printf(event,"==============================================================================");
      LOGEJ.getInstance().printf((EPlatonEvent)event,"TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
      LOGEJ.getInstance().printf(event,"EDL999에러가 발생");
      return event;
    }

    LOGEJ.getInstance().printf(event,"==============================================================================");
    LOGEJ.getInstance().printf((EPlatonEvent)event,"TPSrecv()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
    return event;
  }

  public EPlatonEvent TPSsend(EPlatonEvent event)
  {
    EPlatonCommonDTO commonDTO = (EPlatonCommonDTO)event.getCommon();
    TPSVCINFODTO tpsvcinfo = (TPSVCINFODTO)event.getTPSVCINFODTO();
    LOGEJ.getInstance().printf(event,"클라이언트로 데이타를 리턴합니다:["+tpsvcinfo.getErrorcode()+"]");
    LOGEJ.getInstance().printf((EPlatonEvent)event,"TPSsend()-"+com.chb.coses.foundation.utility.Reflector.objectToString(event));
    LOGEJ.getInstance().printf(event,"==============================================================================\n\n");
    return event;
  }

}