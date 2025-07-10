package com.chb.coses.eplatonFMK.business.helper;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import java.rmi.*;
import javax.naming.*;
import javax.ejb.*;
import javax.rmi.PortableRemoteObject;
import java.math.BigDecimal;

import com.chb.coses.eplatonFMK.business.EPlatonBizDelegateSB;
import com.chb.coses.eplatonFMK.business.EPlatonBizDelegateSBHome;
import com.chb.coses.eplatonFMK.business.EPlatonBizDelegateSBBean;
import com.chb.coses.eplatonFMK.transfer.EPlatonCommonDTO;
import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.transfer.TPSVCINFODTO;
import com.chb.coses.cosesFramework.exception.*;
import com.chb.coses.framework.exception.*;
import com.chb.coses.framework.exception.BizDelegateException;
import com.chb.coses.framework.transfer.*;

public class TPCsendrecv {
  private static TPCsendrecv instance;
  public static EPlatonBizDelegateSBHome bizHome = null;
  public static EPlatonBizDelegateSB remote = null;
  public int isUse = 0;

  private Context ctx;
  public String url = "t3://localhost:7001";
  public String initial_context = "weblogic.jndi.WLInitialContextFactory";

  public TPCsendrecv(String ip,String port) throws Exception{
    try{
      this.url = "t3://"+ip+":"+port;
      this.ctx = this.getInitialContext();
    }catch(Exception ex){
      ex.printStackTrace();
      throw ex;
    }
  }

  public Context getInitialContext()throws NamingException
  {
    if (ctx == null) {
      try {
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY,initial_context);
        p.put(Context.PROVIDER_URL, this.url);
        ctx = new InitialContext(p);
      } catch (NamingException ne) {
        ne.printStackTrace();
        System.out.println(ne);
        throw ne;
      }
    }
    return ctx;
  }

  public Object getHome() throws NamingException
  {
    if(this.bizHome==null) {

      Object obj = ctx.lookup(EPlatonBizDelegateSBHome.class.getName());
      bizHome = (EPlatonBizDelegateSBHome)PortableRemoteObject.narrow(
          obj,EPlatonBizDelegateSBHome.class
          );
      return bizHome;
    }
    else
      return this.bizHome;
  }

  public Object getRemote() throws Exception
  {
    if(this.remote==null) {
      this.remote = this.bizHome.create();
      return this.remote ;
    }
    else
      return this.remote;
  }

  public synchronized Object callEJB(EPlatonEvent pevent)
      throws IOException
  {
    IEvent response_event = pevent;

    try {
      EPlatonEvent cevent = pevent;
      TPSVCINFODTO tpsvcinfoDTO = pevent.getTPSVCINFODTO();
      EPlatonCommonDTO commonDTO = pevent.getCommon();


      Object tobj = pevent.getRequest();
      if(!(tobj instanceof IDTO) ) {
        tpsvcinfoDTO.setErrorcode("EBDL001");
        tpsvcinfoDTO.setError_message("IDTO 객체가 아니다");
        return cevent;
      }

      //ST.1-EPlatonBizDelegateSBBean.execute()
      String CallActionClassName = tpsvcinfoDTO.getAction_name();
      //ST.2-EPlatonBizAction.act()에서 다음을 구현
      //실제적으론 필요없는 것이지만 어떤 시스템이 호출되었는지 알기 위해 셋팅
      String CallFacadeSystemName = tpsvcinfoDTO.getSystem_name();
      //ST.3-BTF.execute()에서 operation명으로 각 업무시스템의 대표 action class 기동
      String CallOperationClassName = tpsvcinfoDTO.getOperation_name();
      //실제적으론 필요없는 것이지만 어떤 CDTO가 호출되었는지 알기 위해 셋팅
      String CallCDTOClassName = tpsvcinfoDTO.getCdto_name();

      commonDTO.setTimeZone("GMT+09:00");
      commonDTO.setFxRateCount(1);

      bizHome = (EPlatonBizDelegateSBHome) getHome();
      remote = (EPlatonBizDelegateSB) getRemote();

      response_event = remote.execute(cevent);
      if( response_event.getResponse() == null ){
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setErrorcode("EBLD0001");
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setError_message("Call TPCsendrecv() error");
        return (Object)response_event;
      }
      else{
        System.out.println(("TPCsendrecv success"));
        return (Object)response_event;
      }
    }
    catch(Exception e)
    {
      if(e instanceof BizActionException)
      {
        System.out.println(e);
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setErrorcode("EBLD0001");
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setError_message("Call TPCsendrecv() error");
      }
      else if(e instanceof RemoteException)
      {
        System.out.println(e);
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setErrorcode("EBLD0001");
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setError_message("Call TPCsendrecv() error");
      }
      else if(e instanceof CosesAppException)
      {
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setErrorcode("EBLD0001");
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setError_message("Call TPCsendrecv() error");
      }
      else if(e instanceof BizDelegateException)
      {
        System.out.println(e);
        e.printStackTrace();
        BizDelegateException bdex = (BizDelegateException)e;
        Map map = bdex.getExceptions();
        Set set = map.keySet();
        Iterator iter = set.iterator();
        String exceptionCode = "*";
        String exceptionMsg = "*";
        while(iter.hasNext()){
          exceptionCode = (String)iter.next();
          exceptionMsg = (String)map.get(exceptionCode);
        }
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setErrorcode("EBLD0001");
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setError_message("Call TPCsendrecv() error");
      }
      else
      {
        System.out.println(e);
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setErrorcode("EBLD0001");
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setError_message("Call TPCsendrecv() error");
      }
      return (Object)response_event;
    }
  }

  public synchronized Object callEJB(String call_systemname,
                                     String call_actionclassname,
                                     String call_operationname,
                                     EPlatonEvent pevent)
  {
    IEvent response_event = pevent;

    try {
      EPlatonEvent cevent = pevent;
      TPSVCINFODTO tpsvcinfoDTO = pevent.getTPSVCINFODTO();
      EPlatonCommonDTO commonDTO = pevent.getCommon();

      Object tobj = pevent.getRequest();
      if(!(tobj instanceof IDTO) ) {
        tpsvcinfoDTO.setErrorcode("EBDL001");
        tpsvcinfoDTO.setError_message("IDTO 객체가 아니다");
        return cevent;
      }

      tpsvcinfoDTO.setSystem_name(call_systemname);
      tpsvcinfoDTO.setAction_name(call_actionclassname);
      tpsvcinfoDTO.setOperation_name(call_operationname);

      commonDTO.setTimeZone("GMT+09:00");
      commonDTO.setFxRateCount(1);

      bizHome = (EPlatonBizDelegateSBHome) getHome();
      remote = (EPlatonBizDelegateSB) getRemote();

      response_event = remote.execute(cevent);
      if( response_event.getResponse() == null ){
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setErrorcode("EBLD0001");
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setError_message("Call TPCsendrecv() error");
        return (Object)response_event;
      }
      else{
        System.out.println(("TPCsendrecv success"));
        return (Object)response_event;
      }
    }
    catch(Exception e)
    {
      ((EPlatonEvent)response_event).getTPSVCINFODTO().setErrorcode("EBLD0001");
      ((EPlatonEvent)response_event).getTPSVCINFODTO().setError_message("Call TPCsendrecv() error");
      e.printStackTrace();
      return (Object)response_event;
    }
  }
}

