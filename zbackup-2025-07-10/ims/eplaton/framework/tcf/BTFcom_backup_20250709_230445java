package com.ims.eplaton.framework.tcf;

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
import java.lang.reflect.*;

import com.ims.eplaton.eplatonFWK.transfer.*;
import com.ims.eplaton.foundation.helper.TPMSVCAPI;
import com.ims.eplaton.foundation.helper.CommonUtil;
import com.ims.eplaton.eplatonFWK.business.operation.IBizOperation;
import com.ims.eplaton.foundation.helper.logej.LOGEJ;
import com.ims.eplaton.eplatonFWK.othersystem.operation.*;


/**
 * 업무시스템으로 라우팅이 이루어진다.
 *
 * 각 업무시스템의 대표 operation class 명에 대한 정보는 TPSVCINFODTO의 operation_name의 클래스명이고
 * 이클래스(tpsvcinfodto.operation_name)에서 호출한 업무서비스 메소드는 tpsvcinfodto.operation_method
 * 이다.
 * 다음은 클라이언트에서 호출할 업무시스템으로의 라우팅정보이다
 *  1. 호출시스템명
 *  tpsvcinfo.setSystem_name("CashCard");
 *  2. business delegate에서 호출할 action 명
 *  tpsvcinfo.setAction_name("com.ims.eplaton.eplatonFWK.business.delegate.action.CashCardBizAction");
 *  3. tcf의 btf에서 각 업무시스템으로의 연계를 위한 클래스 셋팅
 *  tpsvcinfo.setOperation_name("com.ims.eplaton.eplatonFWK.othersystem.operation.COMMO1000");
 *  4. operation_name 클래스에서 호출할 메소스명을 명시
 *  tpsvcinfo.setOperation_method("act");
 *  5. operatin_name 클래스의 operation_method의 인자로 넘겨줄 cdto명에 대한 정보
 *  tpsvcinfo.setCdto_name("com.chb.coses.CashCard.transfer.CashCardCDTO");
 *
 * @return
 */

public class BTF implements IBTF {
  private static BTF instance;
  private EPlatonEvent eplevent;
  private EPlatonCommonDTO commonDTO;
  private TPSVCINFODTO tpsvcinfo;


  /**
   * BTF에 대한 인스턴스를 생성한다
   * @return
   */
  public static synchronized BTF getInstance() {
    if (instance == null) {
      try{
        instance = new BTF();
        }catch(Exception igex){}
    }
    return instance;
  }


  /**
   * EPlatonEvent을 가져오는 함수
   * @return
   */
  public EPlatonEvent getEPlatonEvent(){
    return this.eplevent;
  }

  /**
   * 실행함수
   * @param pevent
   * @return
   */
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
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
    }

    return this.eplevent ;
  }


  /**
   * 기본정보를 셋팅하는 메소드
   * @return
   */
  public boolean BTF_SPinit()
  {
    try{
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[BTF_SPinit START]");

      /*************************************************************************
       * 기본정보를 가져온다.
       ************************************************************************/
      commonDTO = (EPlatonCommonDTO)eplevent.getCommon();
      tpsvcinfo = eplevent.getTPSVCINFODTO();

    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EFWK0018",this.getClass().getName()+ ".BTF_SPinit():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[BTF_SPinit END]");
      return false;
    }

    LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[BTF_SPinit END]");

    return true;

  }


  /**
   * 업무시스템으로 라우팅이 이루어진다.
   *
   * 각 업무시스템의 대표 operation class 명에 대한 정보는 TPSVCINFODTO의 operation_name의 클래스명이고
   * 이클래스(tpsvcinfodto.operation_name)에서 호출한 업무서비스 메소드는 tpsvcinfodto.operation_method
   * 이다.
   * 다음은 클라이언트에서 호출할 업무시스템으로의 라우팅정보이다
   *  1. 호출시스템명
   *  tpsvcinfo.setSystem_name("CashCard");
   *  2. business delegate에서 호출할 action 명
   *  tpsvcinfo.setAction_name("com.ims.eplaton.eplatonFWK.business.delegate.action.CashCardBizAction");
   *  3. tcf의 btf에서 각 업무시스템으로의 연계를 위한 클래스 셋팅
   *  tpsvcinfo.setOperation_name("com.ims.eplaton.eplatonFWK.othersystem.operation.COMMO1000");
   *  4. operation_name 클래스에서 호출할 메소스명을 명시
   *  tpsvcinfo.setOperation_method("act");
   *  5. operatin_name 클래스의 operation_method의 인자로 넘겨줄 cdto명에 대한 정보
   *  tpsvcinfo.setCdto_name("com.chb.coses.CashCard.transfer.CashCardCDTO");
   *
   * @return
   */
  public boolean BTF_SPmiddle()
  {
    try{

      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"####################################[BTF_SPmiddle() START]");
      /*
       * 클라이언트 셋팅정보
       * tpsvcinfo.setOperation_name("com.ims.eplaton.eplatonFWK.othersystem.operation.COMMO1000")
       */
      String operationName = tpsvcinfo.getOperation_name();

      /*
       * 클라이언트에서 세팅한 클래스를 인스턴스화 시킨다
       * 클라이언트 셋팅정보 :
       * tpsvcinfo.setOperation_name("com.ims.eplaton.eplatonFWK.othersystem.operation.COMMO1000")
       */
      Object ptarget = Class.forName(operationName).newInstance();

      /*
       * 클라이언트 셋팅정보
       * tpsvcinfo.setOperation_method("act");
       */
      String methodName = eplevent.getTPSVCINFODTO().getOperation_method();

      /*
       * 메소드에 대한 정보를 가지고와서 실행시킨다.
       */
      Method meth = (ptarget.getClass()).getMethod(methodName,new Class[]{EPlatonEvent.class});
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"CALL METHOD INFO-[" + meth.toString() + "]" );
      eplevent = (EPlatonEvent)meth.invoke(ptarget,new Object[]{(EPlatonEvent)eplevent});

    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EFWK0019",this.getClass().getName()+ ".BTF_SPmiddle():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"####################################[BTF_SPmiddle() END]");

      return false;
    }
    LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"####################################[BTF_SPmiddle() END]");

    return true;

  }

  public boolean BTF_SPmiddle(int type2)
  {
    try{

      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"####################################[BTF_SPmiddle() START]");
      String operationName = tpsvcinfo.getOperation_name();

      Object ptarget = Class.forName(operationName).newInstance();
      String methodName = eplevent.getTPSVCINFODTO().getOperation_method(); //queryForRegisterCashCard
      Method meth = (ptarget.getClass()).getMethod(methodName,new Class[]{EPlatonEvent.class});
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"CALL METHOD INFO-[" + meth.toString() + "]" );
      eplevent = (EPlatonEvent)meth.invoke(ptarget,new Object[]{(EPlatonEvent)eplevent});

    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EFWK0019",this.getClass().getName()+ ".BTF_SPmiddle():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"####################################[BTF_SPmiddle() END]");

      return false;
    }
    LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"####################################[BTF_SPmiddle() END]");

    return true;

  }


  public boolean BTF_SPend()
  {
    try{
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[BTF_SPend START]");

    }
    catch(Exception ex){
      ex.printStackTrace();
      BTF_SPerror("EFWK0020",this.getClass().getName()+ ".BTF_SPmiddle():"+ex.toString());
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)eplevent,ex);
      LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[BTF_SPend END]");

      return false;
    }
    LOGEJ.getInstance().printf(1,(EPlatonEvent)eplevent,"==================[BTF_SPend END]");
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