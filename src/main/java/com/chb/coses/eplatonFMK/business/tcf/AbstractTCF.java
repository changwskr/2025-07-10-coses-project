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

import com.chb.coses.eplatonFMK.transfer.*;
import com.chb.coses.eplatonFMK.business.helper.TPMSVCAPI;
import com.chb.coses.eplatonFMK.business.helper.CommonUtil;


public abstract class AbstractTCF
{
  protected String ctx;

  /**
   * @see java.lang.Object#Object()
   */
  public AbstractTCF()
  {
    ctx = null;
  }

  /**
   * STF 로직에서 추가로 구성해야 될 부분을 관리
   */
  public void stfActive01(EPlatonEvent evnet) throws Exception
  {
    return;
  }

  public void stfActive02(EPlatonEvent evnet) throws Exception
  {
    return;
  }

  public void stfActive03(EPlatonEvent evnet) throws Exception
  {
    return;
  }

  /**
   * ROUTE 로직에서 추가로 구성해야 될 부분을 관리
   */

  public void routeActive01(EPlatonEvent evnet) throws Exception
  {
    return;
  }

  public void routeActive02(EPlatonEvent evnet) throws Exception
  {
    return;
  }

  public void routeActive03(EPlatonEvent evnet) throws Exception
  {
    return;
  }

  /**
   * ETF 로직에서 추가로 구성해야 될 부분을 관리
   */

  public void etfActive01(EPlatonEvent evnet) throws Exception
  {
    return;
  }

  public void etfActive02(EPlatonEvent evnet) throws Exception
  {
    return;
  }

  public void etfActive03(EPlatonEvent evnet) throws Exception
  {
    return;
  }

}

