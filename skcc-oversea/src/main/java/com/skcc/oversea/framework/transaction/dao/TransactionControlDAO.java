package com.skcc.oversea.framework.transaction.dao;

import java.io.*;
import java.rmi.*;
import java.sql.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.naming.*;
import javax.ejb.*;
import javax.transaction.*;
import java.math.BigDecimal;
import javax.rmi.PortableRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;

import com.skcc.oversea.foundation.log.Log;
import com.skcc.oversea.framework.business.dao.AbstractDAO;
import com.skcc.oversea.foundation.db.DBService;
import com.skcc.oversea.foundation.config.Config;
import com.skcc.oversea.framework.transaction.helper.DTOConverter;
import com.skcc.oversea.eplatonframework.transfer.*;
import com.skcc.oversea.framework.transaction.model.TransactionUpDownDDTO;
import com.skcc.oversea.framework.transaction.model.TransactionLogDDTO;

import com.skcc.oversea.foundation.utility.*;
import com.skcc.oversea.foundation.logej.*;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 *   TCF(Transaction Control Framwork)에서 DAO 정보를 처리하기 위한 클래스
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

public class TransactionControlDAO extends AbstractDAO implements ITransactionControlDAO
{

  /**
   * STF(Start Transaction Framework)에서 트랜잭션 입력전문을 남기기 위한 로직
   *
   * @param event
   * @return
   */

  public boolean DB_INSERTinlog(EPlatonEvent event)
  {
    Connection con = null;
    try {

      TransactionUpDownDDTO logDDTO = new DTOConverter().getTransactionUpDownDDTO(event);
      con = getConnection();

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,
                                 "TransactionUpDownDDTO:"+com.chb.coses.foundation.utility.Reflector.objectToString(logDDTO));

      TransactionUpDownEntity.insertTransactionInLog(con,event);

    } catch (NamingException e) {
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,e);
      releaseConnection(con);
      return false;
    } catch (SQLException e) {
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,e);
      releaseConnection(con);
      return false;
    }

    releaseConnection(con);

    return true;

  }

  /**
   * TPMDBSrecv()모듈에서 이용되는 모듈러서 입력전문을 관리하기 위해서 사용된다.
   *
   * @param event
   * @return
   */

  public boolean DB_INSERT_tpminlog(EPlatonEvent event)
  {
    Connection con = null;
    EPlatonCommonDTO commonDTO = event.getCommon();
    TPSVCINFODTO tpsvcinfoDTO = event.getTPSVCINFODTO();

    try
    {
      con = getConnection();
      TransactionLogEntity.insertTransactionInLog(con,event);

    } catch (NamingException e) {
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,e);
      releaseConnection(con);
      return false;
    } catch (SQLException e) {
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,e);
      releaseConnection(con);
      return false;
    }
    releaseConnection(con);
    return true;

  }


  public boolean DB_INSERTtpmoutlog(EPlatonEvent event,long interval_seconds)
  {
    Connection con = null;
    try {
      con = getConnection();
      TransactionLogEntity.insertTransactionOutLog(con,interval_seconds,event);

    } catch (NamingException e) {
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,e);
      releaseConnection(con);
      return false;
    } catch (SQLException e) {
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,e);
      releaseConnection(con);
      return false;
    }

    releaseConnection(con);

    return true;

  }


  public boolean DB_INSERToutlog(EPlatonEvent event)
  {
    Connection con = null;
    try {

      TransactionUpDownDDTO logDDTO = new DTOConverter().getTransactionUpDownDDTO(event);
      con = getConnection();

      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,
                                 "TransactionUpDownDDTO:"+com.chb.coses.foundation.utility.Reflector.objectToString(logDDTO));

      TransactionUpDownEntity.insertTransactionOutLog(con,event);

    } catch (NamingException e) {
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,e);
      releaseConnection(con);
      return false;
    } catch (SQLException e) {
      LOGEJ.getInstance().eprintf(5,(EPlatonEvent)event,e);
      releaseConnection(con);
      return false;
    }

    releaseConnection(con);

    return true;

  }



  public void releaseConnection(Connection con)
  {
    if (con != null) {
      try {
        con.close();
        } catch (SQLException e) { /* ignored */ }
    }
  }

  public String queryForBusinessDate(String bankCode)
  {
    StringBuffer queryBuffer = new StringBuffer();
    String businessDate = null;

    queryBuffer.append("SELECT DATE_VALUE1 FROM SYSTEM_PARAMETER WHERE SYSTEM='COR' AND KIND='BIZDATE'");

    Connection con = null;
    PreparedStatement psmt = null;
    ResultSet rs = null;

    try
    {
      con = this.getConnection();
      psmt = con.prepareStatement(queryBuffer.toString());
      rs = psmt.executeQuery();
      if(rs.next())
      {
        businessDate = rs.getString("BUSINESS_DATE");
      }
    }
    catch(NamingException ne)
    {
      ne.printStackTrace();
    }
    catch(SQLException se)
    {
      se.printStackTrace();
    }
    finally
    {
      releaseResource(con, psmt, rs);
    }
    return businessDate;

  }




  public String GetBizDate()
  {
    String 	 bizdate=null;
    Statement  stmt=null;
    ResultSet  rs=null;
    Connection conn = null;

    try {
      conn = this.getConnection();
      String tSQL = "SELECT DATE_VALUE1 FROM SYSTEM_PARAMETER WHERE SYSTEM='COR' AND KIND='BIZDATE'";
      stmt = conn.createStatement( );
      rs = stmt.executeQuery(tSQL);
      rs.next();
      bizdate=CommonUtil.Int2Str(rs.getInt(1));
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    try{
      if(rs != null){ this.release(rs);}
      if(stmt != null){ this.release(stmt);}
      if(conn != null){ this.release(conn);}
    }catch(SQLException sqle){
      sqle.printStackTrace();
    }

    return(bizdate);
  }

  /**
   * 자원을 반환한다
   * @param     Connection
   * @param     PreparedStatement
   * @param     ResultSet
   * @return    void
   * @throws    CosesAppException
   */
  private void releaseResource(Connection con, PreparedStatement ps,
                               ResultSet rs)
  {

    try{
      if(rs != null){ this.release(rs);}
      if(ps != null){ this.release(ps);}
      if(con != null){ this.release(con);}
    }catch(SQLException sqle){
      sqle.printStackTrace();
    }

  }//end method

  public boolean isLogabled()
  {
    return Log.DelegateLogger.isDebugEnabled();
  }


}


