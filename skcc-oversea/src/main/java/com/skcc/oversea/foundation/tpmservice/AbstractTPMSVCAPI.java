package com.skcc.oversea.foundation.tpmservice;

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
import java.math.BigDecimal;
import javax.rmi.PortableRemoteObject;

import com.skcc.oversea.framework.transfer.*;
import com.skcc.oversea.eplatonframework.transfer.*;

public abstract class AbstractTPMSVCAPI
{
  public Context ctx;
  public String url = com.kdb.oversea.foundation.constant.Constants.call_url ;
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
        System.out.println(ne);
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
        System.out.println(ne);
        throw ne;
      }
    }
    return ctx;
  }

}

