package com.ims.oversea.framework.transaction.tpmutil;

import java.io.*;
import java.util.*;
import java.rmi.*;
import javax.naming.*;
import javax.ejb.*;
import javax.rmi.PortableRemoteObject;
import java.math.BigDecimal;

import com.ims.oversea.eplatonframework.business.EPlatonBizDelegateSB;
import com.ims.oversea.eplatonframework.business.EPlatonBizDelegateSBHome;
import com.ims.oversea.eplatonframework.business.EPlatonBizDelegateSBBean;
import com.ims.oversea.eplatonframework.transfer.EPlatonCommonDTO;
import com.ims.oversea.eplatonframework.transfer.EPlatonEvent;
import com.ims.oversea.eplatonframework.transfer.TPSVCINFODTO;
import com.ims.oversea.foundation.utility.CommonUtil;
import com.chb.coses.cosesFramework.exception.*;
import com.chb.coses.framework.exception.*;
import com.chb.coses.framework.transfer.*;
import com.ims.oversea.framework.transaction.constant.TCFConstants;
import com.ims.oversea.framework.transaction.constant.TCFConstantErrcode;

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


public class TPCsendrecv {
  private static TPCsendrecv instance;
  public static EPlatonBizDelegateSBHome bizHome = null;
  public static EPlatonBizDelegateSB remote = null;
  public int isUse = 0;

  private Context ctx;
  public String url = com.ims.oversea.foundation.constant.Constants.call_url;
  public String initial_context = "weblogic.jndi.WLInitialContextFactory";

  public static synchronized TPCsendrecv getInstance(String ip,String port) {
    if (instance == null) {
      try{
        instance = new TPCsendrecv(ip,port);
        System.out.println(" -- url:"+instance.url);
      }
      catch(Exception igex)
      {
        System.out.println(igex);
        return null;
      }
    }
    return instance;
  }

  public TPCsendrecv(String ip,String port) throws Exception{
    try{
      this.url = "t3://"+ip+":"+port;
      System.out.println(" 1-- url:"+url);
      this.ctx = this.getInitialContext();
      System.out.println(" 2-- url:"+url);
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
      System.out.println(" 5-- url:"+url);
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



  /**
   * 2004/03/08
   * 최종버전으로 만듬
   * TPCsendrecv("cashCard-listCashCard","20",EPlatonEvent)
   *
   * @param request_name
   * @param call_timeout
   * @param pevent
   * @return
   */
  public synchronized Object callEJB(String request_name,
                                     String call_timeout,
                                     EPlatonEvent pevent)
  {
    IEvent response_event = pevent;


    try {
      EPlatonEvent cevent = pevent;
      TPSVCINFODTO tpsvcinfoDTO = pevent.getTPSVCINFODTO();
      EPlatonCommonDTO commonDTO = pevent.getCommon();

      Object tobj = pevent.getRequest();
      if(!(tobj instanceof IDTO) ) {
        tpsvcinfoDTO.setErrorcode(TCFConstantErrcode.ETPM019);
        tpsvcinfoDTO.setError_message(TCFConstantErrcode.ETPM019_MSG);
        return cevent;
      }

      tpsvcinfoDTO.setSystem_name(TPMSutil.getInstance().TPgetcallsystemname(request_name) );
      commonDTO.setReqName(request_name );
      tpsvcinfoDTO.setReqName(request_name);
      tpsvcinfoDTO.setTpfq(TCFConstants.NATIVE_SERVER_INTERCHANGE_LOC);
      tpsvcinfoDTO.setErrorcode(TCFConstants.SUCCESS_ERRCODE);
      tpsvcinfoDTO.setTx_timer(call_timeout);
      tpsvcinfoDTO.setWeb_timeout(call_timeout);
      tpsvcinfoDTO.setWeb_intime(CommonUtil.GetSysTime());
      tpsvcinfoDTO.setSystem_date(CommonUtil.GetSysDate() );

      System.out.println(("호출시스템명 : " + tpsvcinfoDTO.getSystem_name() ));
      System.out.println(("호출request-name : " + tpsvcinfoDTO.getReqName() ));

      commonDTO.setTimeZone(TCFConstants.TIME_ZONE);
      commonDTO.setFxRateCount(1);
      pevent.setAction(tpsvcinfoDTO.getReqName());

      bizHome = (EPlatonBizDelegateSBHome) getHome();
      remote = (EPlatonBizDelegateSB) getRemote();

      response_event = remote.execute(cevent);

      System.out.println(com.chb.coses.foundation.utility.Reflector.objectToString(response_event));

      System.out.println("TPCsendrecv errorcode : " + ((EPlatonEvent)response_event).getTPSVCINFODTO().getErrorcode());
      System.out.println("            errormesg : " + ((EPlatonEvent)response_event).getTPSVCINFODTO().getError_message() );


      if( response_event.getResponse() == null ){
        System.out.println(("호출request-name : " + tpsvcinfoDTO.getReqName() ));
        System.out.println(("TPCsendrecv fail - 리턴된 CDTO 객체가 NULL입니다."));
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setWeb_outtime(CommonUtil.GetSysTime() );
        return (Object)response_event;
      }
      else{
        System.out.println(("TPCsendrecv success"));
        ((EPlatonEvent)response_event).getTPSVCINFODTO().setWeb_outtime(CommonUtil.GetSysTime() );
        return (Object)response_event;
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
      ((EPlatonEvent)response_event).getTPSVCINFODTO().setWeb_outtime(CommonUtil.GetSysTime() );
      ((EPlatonEvent)response_event).getTPSVCINFODTO().setErrorcode(TCFConstantErrcode.ETPM020);
      ((EPlatonEvent)response_event).getTPSVCINFODTO().setError_message(TCFConstantErrcode.ETPM020_MSG);
      return (Object)response_event;
    }
  }


  /**
   * 2004/03/08
   * 최종버전으로 만듬
   * TPCsendrecv("cashCard-listCashCard","20",EPlatonEvent)
   *
   * @param request_name
   * @param call_timeout
   * @param pevent
   * @return
   */
  public synchronized Object callEJB(String request_name,
                                     String call_timeout,
                                     IDTO request_cdto, //요청할 CDTO객체
                                     EPlatonEvent pevent)
  {
    IEvent response_event = pevent;

    try {
      EPlatonEvent cevent = pevent;
      TPSVCINFODTO tpsvcinfoDTO = pevent.getTPSVCINFODTO();
      EPlatonCommonDTO commonDTO = pevent.getCommon();

      Object tobj = request_cdto;
      if(!(tobj instanceof IDTO) ) {
        tpsvcinfoDTO.setErrorcode("EBDL001");
        tpsvcinfoDTO.setError_message("IDTO 객체가 아니다");
        return cevent;
      }
      else{
        cevent.setRequest(request_cdto);
      }

      tpsvcinfoDTO.setSystem_name(TPMSutil.getInstance().TPgetcallsystemname(request_name) );
      commonDTO.setReqName(request_name );
      tpsvcinfoDTO.setReqName(request_name);
      tpsvcinfoDTO.setTpfq("200");
      tpsvcinfoDTO.setErrorcode("IZZ000");
      tpsvcinfoDTO.setTx_timer(call_timeout);
      tpsvcinfoDTO.setWeb_timeout(call_timeout);
      tpsvcinfoDTO.setWeb_intime(CommonUtil.GetSysTime());
      tpsvcinfoDTO.setSystem_date(CommonUtil.GetSysDate() );

      System.out.println(("호출시스템명 : " + tpsvcinfoDTO.getSystem_name() ));
      System.out.println(("호출request-name : " + tpsvcinfoDTO.getReqName() ));

      commonDTO.setTimeZone("GMT+09:00");
      commonDTO.setFxRateCount(1);
      pevent.setAction(tpsvcinfoDTO.getReqName());

      bizHome = (EPlatonBizDelegateSBHome) getHome();
      remote = (EPlatonBizDelegateSB) getRemote();

      response_event = remote.execute(cevent);

      tpsvcinfoDTO.setWeb_outtime(CommonUtil.GetSysTime() );

      if( ((EPlatonEvent)response_event).getTPSVCINFODTO().getErrorcode().substring(0,6).equals("IZZ000") )
        System.out.println(("TPCsendrecv Success"));
      else
        System.out.println(("TPCsendrecv Fail"));

      System.out.println(com.chb.coses.foundation.utility.Reflector.objectToString(response_event));

      if( response_event.getResponse() == null ){
        System.out.println(("TPCsendrecv fail - 리턴된 CDTO 객체가 NULL입니다."));
        tpsvcinfoDTO.setWeb_outtime(CommonUtil.GetSysTime() );
        return (Object)response_event;
      }
      else{
        tpsvcinfoDTO.setWeb_outtime(CommonUtil.GetSysTime() );
        return (Object)response_event;
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
      ((EPlatonEvent)response_event).getTPSVCINFODTO().setErrorcode("EBLD0001");
      ((EPlatonEvent)response_event).getTPSVCINFODTO().setError_message("Call TPCsendrecv() error");
      return (Object)response_event;
    }
  }

}

