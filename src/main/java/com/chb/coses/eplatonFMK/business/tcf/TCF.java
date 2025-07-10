package com.chb.coses.eplatonFMK.business.tcf;

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
/*
import com.chb.coses.cosesFramework.business.delegate.action.*;
import com.chb.coses.cosesFramework.business.delegate.dao.*;
import com.chb.coses.cosesFramework.business.delegate.helper.*;
import com.chb.coses.cosesFramework.business.delegate.constants.*;
*/
import com.chb.coses.common.business.constants.*;
import com.chb.coses.common.business.facade.*;
import com.chb.coses.common.transfer.*;
import com.chb.coses.user.business.facade.*;
import com.chb.coses.user.transfer.UserCDTO;
//import com.chb.coses.cosesFramework.business.delegate.model.TransactionLogDDTO;

import com.chb.coses.reference.business.facade.*;
import com.chb.coses.cosesFramework.action.ActionManager;

import com.chb.coses.eplatonFMK.transfer.*;
import com.chb.coses.eplatonFMK.business.helper.TPMSVCAPI;
import com.chb.coses.eplatonFMK.business.helper.CommonUtil;
import com.chb.coses.eplatonFMK.business.operation.IBizOperation;
import com.chb.coses.eplatonFMK.business.helper.logej.LOGEJ;

public class TCF extends AbstractTCF implements ITCF
{
  private static TCF instance;
  private STF stf;
  private ETF etf;
  private BTF btf;
  private String packetinfo;
  private String senddata;
  private Connection tcfconn;
  private SessionContext tx;
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

      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================================[TCF] start");
      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"[UserTransaction Start]");
      this.tx = tx;

      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================[STF] start");
      eplevent = (stf=new STF(tx)).execute(eplevent);

      switch( eplevent.getTPSVCINFODTO().getErrorcode().charAt(0) )
      {
        case 'e':
        case 's':
        case 'E':
        case 'S':
          LOGEJ.getInstance().printf((EPlatonEvent)pevent,"===============================[STF] error");
          LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================[STF] end");
          break;
        case 'I':
          LOGEJ.getInstance().printf((EPlatonEvent)pevent,"===============================[STF] success");
          LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================[STF] end");
          LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================[BTF] start");
          eplevent = (btf=new BTF()).execute(eplevent);
          if( isErr() )
            LOGEJ.getInstance().printf((EPlatonEvent)pevent,"===============================[BTF] error");
          else
            LOGEJ.getInstance().printf((EPlatonEvent)pevent,"===============================[BTF] success");
          LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================[BTF] end");
          break;
        case '*':
        default:
          TCF_SPerror("EFWK0034",this.getClass().getName()+ ".execute():error code not set");
          break;
      }

      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================[ETF] start");
      eplevent = (etf=new ETF(tx,stf.getSTF_SPtxinfo())).execute(eplevent);
      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================[ETF] end");

    }
    catch(Exception ex){
      this.tx.setRollbackOnly();
      TCF_SPerror("EFWK0032",this.getClass().getName()+ ".execute():"+ex.toString());
      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"TCF_execute() exception:[EFWK032]");
      LOGEJ.getInstance().eprintf((EPlatonEvent)eplevent,ex);
      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================================[TCF] end");
      return eplevent;
    }

    LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================================[TCF] end");
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

  public EPlatonEvent getEPlatonEvent(){
    return eplevent;
  }


}