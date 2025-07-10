package com.ims.oversea.framework.transaction.tcf;


import java.rmi.*;
import java.sql.*;
import java.net.*;
import java.text.*;
import java.util.*;

import javax.ejb.*;
import javax.naming.*;

import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.foundation.tpmservice.*;
import com.ims.oversea.foundation.utility.CommonUtil;


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

