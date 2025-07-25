package com.chb.coses.eplatonFWK.business.tcf;

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

import com.chb.coses.eplatonFWK.transfer.*;
import com.chb.coses.eplatonFWK.business.helper.TPMSVCAPI;
import com.chb.coses.eplatonFWK.business.helper.CommonUtil;
import com.chb.coses.eplatonFWK.business.operation.IBizOperation;
import com.chb.coses.eplatonFWK.business.helper.logej.LOGEJ;
import com.chb.coses.eplatonFWK.business.helper.CommonUtil;
import com.chb.coses.eplatonFWK.business.dao.*;

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

  private String STF_intime="XXXXXXXX";
  private String STF_outtime="XXXXXXXX";
  private String BTF_intime="XXXXXXXX";
  private String BTF_outtime="XXXXXXXX";
  private String ETF_intime="XXXXXXXX";
  private String ETF_outtime="XXXXXXXX";
  private String LOGIC_LEVEL="XXXX";

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

  public void level(String Loc,String module)
  {
    switch( module.charAt(0) )
    {
      case 'S': //STF
        if( Loc.charAt(0)=='B' ){  //BEFORE
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

  public EPlatonEvent execute(EPlatonEvent pevent){
    try {
      eplevent = pevent;

      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================================[TCF] start");
      level("Before","STF");
      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"[UserTransaction Start]");
      //향후 url은 환경파일로 대체
      tx = TPMSVCAPI.getInstance().TPJNDIUserTransaction(this.url);
      if( tx == null ){
        eplevent.getTPSVCINFODTO().setErrorcode("IZZ000");
        TCF_SPerror("EBD100","UserTransaction Get Ex");
        LOGEJ.getInstance().printf((EPlatonEvent)pevent,"UserTransaction Errror");
        LOGEJ.getInstance().printf((EPlatonEvent)pevent,"[UserTransaction End]");
        LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================================[TCF] end");
        return eplevent;
      }
      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"UserTransaction Success");
      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"[UserTransaction END]");

      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================[STF] start");
      eplevent = (stf=new STF(tx)).execute(eplevent);
      level("After","STF");

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
          level("Before","BTF");
          eplevent = (btf=new BTF()).execute(eplevent);
          level("After","BTF");
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
      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"UserTransactionInfo : " + tx);
      level("Before","ETF");
      eplevent = (etf=new ETF(tx,stf.getSTF_SPtxinfo())).execute(eplevent);
      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================[ETF] end");

    }
    catch(Exception ex){
      TCF_SPerror("EFWK0032",this.getClass().getName()+ ".execute():"+ex.toString());
      LOGEJ.getInstance().printf((EPlatonEvent)pevent,"TCF_execute() exception:[EFWK032]");
      LOGEJ.getInstance().eprintf((EPlatonEvent)eplevent,ex);
    }

    level("After","ETF");
    LOGEJ.getInstance().printf((EPlatonEvent)pevent,"====================================================================[TCF] end");

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
      LOGEJ.getInstance().eprintf((EPlatonEvent)eplevent,ex);
    }
  }

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

      TxTimeProcDAO tdao = new TxTimeProcDAO();
      tdao.TRANSACTION_INFO(eplevent);

    }
    catch(Exception ex){
      LOGEJ.getInstance().eprintf((EPlatonEvent)eplevent,ex);
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
    }
    catch(Exception ex){
      LOGEJ.getInstance().eprintf((EPlatonEvent)eplevent,ex);
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

  public EPlatonEvent getEPlatonEvent(){
    return eplevent;
  }


}