package com.ims.oversea.framework.transaction.tpmutil;


import java.io.*;
import java.util.*;
import java.rmi.*;
import javax.naming.*;
import javax.ejb.*;
import java.math.BigDecimal;
import javax.rmi.PortableRemoteObject;

import com.chb.coses.framework.transfer.*;
import com.ims.oversea.eplatonframework.transfer.*;

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

public abstract class AbstractTPMSVCAPI
{
  public Context ctx;
  public String url = com.ims.oversea.foundation.constant.Constants.call_url ;
  public String initial_context = "weblogic.jndi.WLInitialContextFactory";
  public EPlatonCommonDTO cosescommon;
  public EPlatonEvent event;
  public IEvent result ;

  public AbstractTPMSVCAPI()
  {
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
        //System.out.println(ne);
        ne.printStackTrace();
        throw ne;
      }
    }
    return ctx;
  }

  public Context getInitialContext(String url_port)throws NamingException
  {
    if (ctx == null) {
      try {
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY,initial_context);
        p.put(Context.PROVIDER_URL, url_port);
        ctx = new InitialContext(p);
      } catch (NamingException ne) {
        //System.out.println(ne);
        ne.printStackTrace();
        throw ne;
      }
    }
    return ctx;
  }

}