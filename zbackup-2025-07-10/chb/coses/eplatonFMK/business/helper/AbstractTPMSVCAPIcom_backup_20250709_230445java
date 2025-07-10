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
import java.math.BigDecimal;
import javax.rmi.PortableRemoteObject;

import com.chb.coses.cosesFramework.exception.*;
import com.chb.coses.framework.exception.*;
import com.chb.coses.framework.exception.BizDelegateException;
import com.chb.coses.framework.transfer.*;
import com.chb.coses.cosesFramework.transfer.*;
import com.chb.coses.cosesFramework.business.delegate.*;

import com.chb.coses.eplatonFMK.transfer.*;

public abstract class AbstractTPMSVCAPI
{
  public Context ctx;
  public String url = "t3://localhost:7001";
  public String initial_context = "weblogic.jndi.WLInitialContextFactory";
  public CosesCommonDTO cosescommon;
  public CosesEvent event;
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