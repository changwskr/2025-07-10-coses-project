package com.chb.coses.eplatonFramework.business.helper;

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

import com.chb.coses.cosesFramework.exception.*;
import com.chb.coses.framework.exception.*;
import com.chb.coses.framework.exception.BizDelegateException;
import com.chb.coses.framework.transfer.*;
import com.chb.coses.cosesFramework.transfer.*;
import com.chb.coses.cosesFramework.business.delegate.*;

import com.chb.coses.foundation.jndi.JNDIService;
import com.chb.coses.eplatonFramework.transfer.*;
import com.chb.coses.eplatonFramework.business.delegate.action.EPlatonBizAction;
import com.chb.coses.eplatonFramework.transfer.EPlatonEvent;

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
      String actionClassName = ((EPlatonEvent)event).getTPSVCINFODTO().getAction_name(); //actionClassName="com.chb.coses.eplatonFramework.business.delegate.action.CashCardBizAction"
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
    catch ( BizActionException ex){
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

  public int TPinfo(UserTransaction tx)
  {
    try{
      switch( tx.getStatus() )
      {
        case 1:
          return 1;
        case 0:
          return 0;
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
      tx.setTransactionTimeout(second);
      tx.begin();
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

  public EPlatonEvent TPSrecv(EPlatonEvent event)
  {
    return null;
  }

  public EPlatonEvent TPSsend(EPlatonEvent event)
  {
    return null;
  }






}
