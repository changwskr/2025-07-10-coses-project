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
      BTF_SPerror("EBD300","BTF execute() 예외");
    }

    return this.eplevent ;
  }

  public boolean BTF_SPinit()
  {
    try{
      System.out.println("==================[BTF_SPinit START]");

      /*************************************************************************
       * 기본정보를 가져온다.
       ************************************************************************/
      commonDTO = (EPlatonCommonDTO)eplevent.getCommon();
      tpsvcinfo = eplevent.getTPSVCINFODTO();

      System.out.println("==================[BTF_SPinit END]");

      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EBD300","BTF_SPinit() 예외");
      return false;
    }
  }

  public boolean BTF_SPmiddle()
  {
    try{

      System.out.println("==================[BTF_SPmiddle() START]");

      /*************************************************************************
       * operation_name 는 기존시스의 Facade 단의 세션빈의 operation 이름을
       * 말한다 (예, listCashCardNumber.)
       ************************************************************************/
      String operationName = tpsvcinfo.getOperation_name();
      System.out.println("업무호출 OPERATION : [" + operationName + "]");

      IBizOperation operation = (IBizOperation)(Class.forName(operationName).newInstance());
      eplevent = operation.act(eplevent);

      System.out.println("==================[BTF_SPmiddle() END]");

      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EBD300","BTF_SPmiddle() 예외");
      return false;
    }
  }

  public boolean BTF_SPmiddle(String type)
  {
    try{

      System.out.println("==================[BTF_SPmiddle() START]");

      /*************************************************************************
       * operation_name 는 기존시스의 Facade 단의 세션빈의 operation 이름을
       * 말한다 (예, listCashCardNumber.)
       ************************************************************************/
      String operationName = tpsvcinfo.getOperation_name();
      System.out.println("업무호출 OPERATION : [" + operationName + "]");

      IBizOperation operation = (IBizOperation)(Class.forName(operationName).newInstance());
      eplevent = operation.act(eplevent);

      System.out.println("==================[BTF_SPmiddle() END]");

      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EBD300","BTF_SPmiddle() 예외");
      return false;
    }
  }

  public boolean BTF_SPend()
  {
    try{
      System.out.println("==================[BTF_SPend START]");

      System.out.println("==================[BTF_SPend START]");
      return true;
    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EBD300","BTF_SPend() 예외");
      return false;
    }
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