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

import com.chb.coses.cosesFramework.transfer.*;
import com.chb.coses.cosesFramework.exception.*;
import com.chb.coses.cosesFramework.business.delegate.action.*;
import com.chb.coses.cosesFramework.business.delegate.dao.*;
import com.chb.coses.cosesFramework.business.delegate.helper.*;
import com.chb.coses.cosesFramework.business.delegate.constants.*;
import com.chb.coses.common.business.constants.*;
import com.chb.coses.common.business.facade.*;
import com.chb.coses.common.transfer.*;
import com.chb.coses.user.business.facade.*;
import com.chb.coses.user.transfer.UserCDTO;
import com.chb.coses.cosesFramework.business.delegate.model.TransactionLogDDTO;

import com.chb.coses.reference.business.facade.*;
import com.chb.coses.cosesFramework.action.ActionManager;

import com.chb.coses.eplatonFramework.transfer.*;
import com.chb.coses.eplatonFramework.business.helper.TPMSVCAPI;
import com.chb.coses.eplatonFramework.business.helper.CommonUtil;
import com.chb.coses.eplatonFramework.business.operation.IBizOperation;

public class TCF extends AbstractTCF implements ITCF
{
  private static TCF instance;
  private STF stf;
  private ETF etf;
  private BTF btf;
  private String packetinfo;
  private String senddata;
  private Connection tcfconn;
  private UserTransaction tx;
  private EPlatonEvent eplevent;
  private String url = "t3://localhost:10000";

  public static synchronized TCF getInstance() {
    if (instance == null) {
      try{
        instance = new TCF();
        }catch(Exception igex){}
    }
    return instance;
  }

  public TCF() throws NamingException{

  }
  public EPlatonEvent execute(EPlatonEvent pevent){
    try {
      eplevent = pevent;

      System.out.println("=====================================================[TCF] start");
      System.out.println("==================================[UserTransaction Start]");
      tx = TPMSVCAPI.getInstance().TPJNDIUserTransaction(this.url);
      if( tx == null ){
        eplevent.getTPSVCINFODTO().setErrorcode("IZZ000");
        TCF_SPerror("EBD100","UserTransaction Get Ex");
        System.out.println("==================================[UserTransaction ERROR]");
        System.out.println("==================================[UserTransaction END]");
        return eplevent;
      }
      System.out.println("==================================[UserTransaction END]");

      System.out.println("==========================================[STF] start");
      eplevent = (stf=new STF(tx)).execute(eplevent);

      switch( eplevent.getTPSVCINFODTO().getErrorcode().charAt(0) )
      {
        case 'e':
        case 's':
        case 'E':
        case 'S':
          System.out.println("===============================[STF] error");
          System.out.println("========================================[STF] end");
        case 'I':
          System.out.println("===============================[STF] success");
          System.out.println("========================================[STF] end");
          System.out.println("========================================[BTF] start");
          eplevent = (btf=new BTF()).execute(eplevent);
          if( isErr() )
            System.out.println("===============================[BTF] error");
          else
            System.out.println("===============================[BTF] success");
          System.out.println("========================================[BTF] end");
        case '*':
        default:
          TCF_SPerror("EBD900","에러코드가 셋팅되지 않았다.");
      }

      System.out.println("========================================[ETF] start");
      eplevent = (etf=new ETF(tx,stf.getSTF_SPtxinfo())).execute(eplevent);
      System.out.println("=========================================[ETF] end");
      System.out.println("=====================================================[TCF] end");

    }
    catch(Exception ex){
      ex.printStackTrace();
      TCF_SPerror("EBD900","TCF_execute()예외발생");
      return eplevent;
    }

    return eplevent;

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
        TCF_SPerror("EBD001","에러코드가 셋팅안됨");
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

  public EPlatonEvent getEPlatonEvent(){
    return eplevent;
  }


}