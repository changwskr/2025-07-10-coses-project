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

import com.chb.coses.eplatonFMK.transfer.*;
import com.chb.coses.eplatonFMK.business.helper.TPMSVCAPI;
import com.chb.coses.eplatonFMK.business.helper.CommonUtil;
import com.chb.coses.eplatonFMK.business.operation.IBizOperation;
import com.chb.coses.eplatonFMK.business.helper.logej.LOGEJ;
import com.chb.coses.eplatonFMK.othersystem.operation.*;

public class BTF implements IBTF {
  private static BTF instance;
  private EPlatonEvent eplevent;
  private EPlatonCommonDTO commonDTO;
  private TPSVCINFODTO tpsvcinfo;

  public static synchronized BTF getInstance() {
    if (instance == null) {
      try{
        instance = new BTF();
        }catch(Exception igex){}
    }
    return instance;
  }

  public EPlatonEvent getEPlatonEvent(){
    return this.eplevent;
  }

  public EPlatonEvent execute(EPlatonEvent pevent){
    try {
      this.eplevent = pevent;

      BTF_SPinit();

      switch( tpsvcinfo.getErrorcode().charAt(0) )
      {
        case 'E':
          break;
        case 'I':
          BTF_SPmiddle();
          break;
      }

      BTF_SPend();

    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EFWK0017",this.getClass().getName()+ ".execute():"+ex.toString());
      LOGEJ.getInstance().eprintf((EPlatonEvent)eplevent,ex);
    }

    return this.eplevent ;
  }

  public boolean BTF_SPinit()
  {
    try{
      LOGEJ.getInstance().printf((EPlatonEvent)eplevent,"==================[BTF_SPinit START]");

      /*************************************************************************
       * 기본정보를 가져온다.
       ************************************************************************/
      commonDTO = (EPlatonCommonDTO)eplevent.getCommon();
      tpsvcinfo = eplevent.getTPSVCINFODTO();

    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EFWK0018",this.getClass().getName()+ ".BTF_SPinit():"+ex.toString());
      LOGEJ.getInstance().eprintf((EPlatonEvent)eplevent,ex);
      LOGEJ.getInstance().printf((EPlatonEvent)eplevent,"==================[BTF_SPinit END]");
      return false;
    }

    LOGEJ.getInstance().printf((EPlatonEvent)eplevent,"==================[BTF_SPinit END]");

    return true;

  }

  public boolean BTF_SPmiddle()
  {
    try{

      LOGEJ.getInstance().printf((EPlatonEvent)eplevent,"####################################[BTF_SPmiddle() START]");

      /*************************************************************************
       * operation_name 는 기존시스의 Facade 단의 세션빈의 operation 이름을
       * 말한다 (예, listCashCardNumber.)
       ************************************************************************/
      String operationName = tpsvcinfo.getOperation_name();
      LOGEJ.getInstance().printf((EPlatonEvent)eplevent,"업무호출 OPERATION : [" + operationName + "]");
      IBizOperation operation = (IBizOperation)(Class.forName(operationName).newInstance());
      eplevent = operation.act(eplevent);

    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EFWK0019",this.getClass().getName()+ ".BTF_SPmiddle():"+ex.toString());
      LOGEJ.getInstance().eprintf((EPlatonEvent)eplevent,ex);
      LOGEJ.getInstance().printf((EPlatonEvent)eplevent,"####################################[BTF_SPmiddle() END]");

      return false;
    }
    LOGEJ.getInstance().printf((EPlatonEvent)eplevent,"####################################[BTF_SPmiddle() END]");

    return true;

  }


  public boolean BTF_SPend()
  {
    try{
      LOGEJ.getInstance().printf((EPlatonEvent)eplevent,"==================[BTF_SPend START]");

    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EFWK0020",this.getClass().getName()+ ".BTF_SPmiddle():"+ex.toString());
      LOGEJ.getInstance().eprintf((EPlatonEvent)eplevent,ex);
      LOGEJ.getInstance().printf((EPlatonEvent)eplevent,"==================[BTF_SPend END]");

      return false;
    }
    LOGEJ.getInstance().printf((EPlatonEvent)eplevent,"==================[BTF_SPend END]");
    return true;

  }

  private boolean isErr(){
    switch (this.eplevent.getTPSVCINFODTO().getErrorcode().charAt(0))
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
        this.eplevent.getTPSVCINFODTO().setErrorcode("EBD001");
        this.eplevent.getTPSVCINFODTO().setError_message("-------1");
        return true;
    }
  }

  private void BTF_SPerror(String errorcode,String message)
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


}