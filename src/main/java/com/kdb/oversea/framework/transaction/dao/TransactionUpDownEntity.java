package com.kdb.oversea.framework.transaction.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.io.*;

import com.kdb.oversea.foundation.config.Config;
import com.chb.coses.foundation.utility.StringUtils;
import com.chb.coses.foundation.utility.Reflector;

import com.kdb.oversea.foundation.logej.LOGEJ;
import com.kdb.oversea.foundation.utility.CommonUtil;

import com.kdb.oversea.foundation.utility.FPrintf;
import com.kdb.oversea.eplatonframework.transfer.*;
import com.chb.coses.framework.transfer.*;


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

public class TransactionUpDownEntity extends AbstractEntity
{
  public TransactionUpDownEntity()
  {
  }

  public static boolean insertTransactionInLog(Connection con , EPlatonEvent event)
      throws SQLException
  {

    TPSVCINFODTO tpsvcinfoDTO = event.getTPSVCINFODTO();
    EPlatonCommonDTO commonDTO = (EPlatonCommonDTO)event.getCommon();

    PreparedStatement pstmt = null;
    StringBuffer query = new StringBuffer();

    query.append("INSERT INTO transaction_input (  ");
    query.append(" bankCode          ");
    query.append(",baseCurrency      ");
    query.append(",branchCode        ");
    query.append(",businessDate      ");
    query.append(",channelType       ");
    query.append(",eventNo           ");
    query.append(",fxRateCount       ");
    query.append(",glPostBranchCode  ");
    query.append(",IPAddress         ");
    query.append(",multiPL           ");
    query.append(",systemInTime      ");
    query.append(",systemDate        ");
    query.append(",reqName           ");
    query.append(",regionCode        ");
    query.append(",terminalType      ");
    query.append(",transactionNo     ");
    query.append(",userID            ");
    query.append(",userLevel         ");
    query.append(",xmlSeq            ");
    query.append(",timeZone          ");
    query.append(",terminalID        ");
    query.append(",systemOutTime     ");
    query.append(",nation            ");
    query.append(",action_name       ");
    query.append(",cdto_name         ");
    query.append(",error_message     ");
    query.append(",errorcode         ");
    query.append(",hostseq           ");
    query.append(",operation_method  ");
    query.append(",operation_name    ");
    query.append(",orgseq            ");
    query.append(",system_name       ");
    query.append(",tpfq              ");
    query.append(",trclass           ");
    query.append(",tx_timer          ");
    query.append(",web_intime        ");
    query.append(",web_outtime       ");
    query.append(",web_timeout       ");
    query.append(",input_dto         ");
    query.append(",output_dto        ");
    query.append(",bp_sequence       ");
    query.append(")                  ");
    query.append("VALUES(            ");
    query.append(" ?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");

    query.append(")");

    try
    {
      pstmt = con.prepareStatement(query.toString());
      int index = 0;

      pstmt.setString(++index,commonDTO.getBankCode());
      pstmt.setString(++index,commonDTO.getBaseCurrency());
      pstmt.setString(++index,commonDTO.getBranchCode());
      pstmt.setString(++index,commonDTO.getBusinessDate());
      pstmt.setString(++index,commonDTO.getChannelType());
      pstmt.setString(++index,commonDTO.getEventNo());
      pstmt.setString(++index,CommonUtil.Int2Str(commonDTO.getFxRateCount()));
      pstmt.setString(++index,commonDTO.getGlPostBranchCode());
      pstmt.setString(++index,commonDTO.getIPAddress());
      pstmt.setString(++index,commonDTO.getMultiPL());
      pstmt.setString(++index,commonDTO.getSystemInTime());
      pstmt.setString(++index,commonDTO.getSystemDate());
      pstmt.setString(++index,commonDTO.getReqName());
      pstmt.setString(++index,commonDTO.getRegionCode());
      pstmt.setString(++index,commonDTO.getTerminalType());
      pstmt.setString(++index,commonDTO.getTransactionNo());
      pstmt.setString(++index,commonDTO.getUserID());
      pstmt.setString(++index,CommonUtil.Int2Str(commonDTO.getUserLevel()));
      pstmt.setString(++index,commonDTO.getXmlSeq());
      pstmt.setString(++index,commonDTO.getTimeZone());
      pstmt.setString(++index,commonDTO.getTerminalID());
      pstmt.setString(++index,commonDTO.getSystemOutTime());
      pstmt.setString(++index,commonDTO.getNation());
      pstmt.setString(++index,tpsvcinfoDTO.getAction_name());
      pstmt.setString(++index,tpsvcinfoDTO.getCdto_name());
      pstmt.setString(++index,tpsvcinfoDTO.getError_message());
      pstmt.setString(++index,tpsvcinfoDTO.getErrorcode());
      pstmt.setString(++index,tpsvcinfoDTO.getHostseq());
      pstmt.setString(++index,tpsvcinfoDTO.getOperation_method());
      pstmt.setString(++index,tpsvcinfoDTO.getOperation_name());
      pstmt.setString(++index,tpsvcinfoDTO.getOrgseq());
      pstmt.setString(++index,tpsvcinfoDTO.getSystem_name());
      pstmt.setString(++index,tpsvcinfoDTO.getTpfq());
      pstmt.setString(++index,tpsvcinfoDTO.getTrclass());
      pstmt.setString(++index,tpsvcinfoDTO.getTx_timer());
      pstmt.setString(++index,tpsvcinfoDTO.getWeb_intime());
      pstmt.setString(++index,tpsvcinfoDTO.getWeb_outtime());
      pstmt.setString(++index,tpsvcinfoDTO.getWeb_timeout());

      String reqcdto = "request-cdto-not-set-cdto";
      String rescdto = "request-cdto-not-set-cdto";

      IDTO objcdto = event.getRequest();
      if( objcdto != null )
        reqcdto = Reflector.objectToString(event.getRequest());

      objcdto = event.getResponse();
      if( objcdto != null )
        rescdto = Reflector.objectToString(event.getResponse());

      if( reqcdto.length() > 4000 ){
        reqcdto = reqcdto.substring(0,39998);
      }
      if( rescdto.length() > 4000 ){
        rescdto = rescdto.substring(0,39998);
      }

      pstmt.setString(++index,reqcdto );
      pstmt.setString(++index,rescdto );
      pstmt.setString(++index,tpsvcinfoDTO.getBp_sequence() );
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"insertTransactionInLog Query : " + query.toString());

      pstmt.executeUpdate();
    }
    finally{
      releaseResource(pstmt, null);
    }
    return true;
  }


  public static boolean insertTransactionOutLog(Connection con , EPlatonEvent event)
      throws SQLException
  {

    TPSVCINFODTO tpsvcinfoDTO = event.getTPSVCINFODTO();
    EPlatonCommonDTO commonDTO = (EPlatonCommonDTO)event.getCommon();

    PreparedStatement pstmt = null;
    StringBuffer query = new StringBuffer();
    query.append("INSERT INTO transaction_output (  ");
    query.append(" bankCode          ");
    query.append(",baseCurrency      ");
    query.append(",branchCode        ");
    query.append(",businessDate      ");
    query.append(",channelType       ");
    query.append(",eventNo           ");
    query.append(",fxRateCount       ");
    query.append(",glPostBranchCode  ");
    query.append(",IPAddress         ");
    query.append(",multiPL           ");
    query.append(",systemInTime      ");
    query.append(",systemDate        ");
    query.append(",reqName           ");
    query.append(",regionCode        ");
    query.append(",terminalType      ");
    query.append(",transactionNo     ");
    query.append(",userID            ");
    query.append(",userLevel         ");
    query.append(",xmlSeq            ");
    query.append(",timeZone          ");
    query.append(",terminalID        ");
    query.append(",systemOutTime     ");
    query.append(",nation            ");
    query.append(",action_name       ");
    query.append(",cdto_name         ");
    query.append(",error_message     ");
    query.append(",errorcode         ");
    query.append(",hostseq           ");
    query.append(",operation_method  ");
    query.append(",operation_name    ");
    query.append(",orgseq            ");
    query.append(",system_name       ");
    query.append(",tpfq              ");
    query.append(",trclass           ");
    query.append(",tx_timer          ");
    query.append(",web_intime        ");
    query.append(",web_outtime       ");
    query.append(",web_timeout       ");
    query.append(",input_dto         ");
    query.append(",output_dto        ");
    query.append(",bp_sequence       ");
    query.append(")                  ");
    query.append("VALUES(            ");
    query.append(" ?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");query.append(",?");query.append(",?");query.append(",?");
    query.append(",?");

    query.append(")");


    try
    {
      pstmt = con.prepareStatement(query.toString());
      int index = 0;

      pstmt.setString(++index,commonDTO.getBankCode());
      pstmt.setString(++index,commonDTO.getBaseCurrency());
      pstmt.setString(++index,commonDTO.getBranchCode());
      pstmt.setString(++index,commonDTO.getBusinessDate());
      pstmt.setString(++index,commonDTO.getChannelType());
      pstmt.setString(++index,commonDTO.getEventNo());
      pstmt.setString(++index,CommonUtil.Int2Str(commonDTO.getFxRateCount()));
      pstmt.setString(++index,commonDTO.getGlPostBranchCode());
      pstmt.setString(++index,commonDTO.getIPAddress());
      pstmt.setString(++index,commonDTO.getMultiPL());
      pstmt.setString(++index,commonDTO.getSystemInTime());
      pstmt.setString(++index,commonDTO.getSystemDate());
      pstmt.setString(++index,commonDTO.getReqName());
      pstmt.setString(++index,commonDTO.getRegionCode());
      pstmt.setString(++index,commonDTO.getTerminalType());
      pstmt.setString(++index,commonDTO.getTransactionNo());
      pstmt.setString(++index,commonDTO.getUserID());
      pstmt.setString(++index,CommonUtil.Int2Str(commonDTO.getUserLevel()));
      pstmt.setString(++index,commonDTO.getXmlSeq());
      pstmt.setString(++index,commonDTO.getTimeZone());
      pstmt.setString(++index,commonDTO.getTerminalID());
      pstmt.setString(++index,commonDTO.getSystemOutTime());
      pstmt.setString(++index,commonDTO.getNation());
      pstmt.setString(++index,tpsvcinfoDTO.getAction_name());
      pstmt.setString(++index,tpsvcinfoDTO.getCdto_name());
      pstmt.setString(++index,tpsvcinfoDTO.getError_message());
      pstmt.setString(++index,tpsvcinfoDTO.getErrorcode());
      pstmt.setString(++index,tpsvcinfoDTO.getHostseq());
      pstmt.setString(++index,tpsvcinfoDTO.getOperation_method());
      pstmt.setString(++index,tpsvcinfoDTO.getOperation_name());
      pstmt.setString(++index,tpsvcinfoDTO.getOrgseq());
      pstmt.setString(++index,tpsvcinfoDTO.getSystem_name());
      pstmt.setString(++index,tpsvcinfoDTO.getTpfq());
      pstmt.setString(++index,tpsvcinfoDTO.getTrclass());
      pstmt.setString(++index,tpsvcinfoDTO.getTx_timer());
      pstmt.setString(++index,tpsvcinfoDTO.getWeb_intime());
      pstmt.setString(++index,tpsvcinfoDTO.getWeb_outtime());
      pstmt.setString(++index,tpsvcinfoDTO.getWeb_timeout());

      String reqcdto = "request-cdto-not-set-cdto";
      String rescdto = "request-cdto-not-set-cdto";

      IDTO objcdto = event.getRequest();
      if( objcdto != null )
        reqcdto = Reflector.objectToString(event.getRequest());

      objcdto = event.getResponse();
      if( objcdto != null )
        rescdto = Reflector.objectToString(event.getResponse());

      if( reqcdto.length() > 4000 ){
        reqcdto = reqcdto.substring(0,39998);
      }
      if( rescdto.length() > 4000 ){
        rescdto = rescdto.substring(0,39998);
      }

      pstmt.setString(++index,reqcdto );
      pstmt.setString(++index,rescdto );
      pstmt.setString(++index,tpsvcinfoDTO.getBp_sequence() );
      LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"insertTransactionOutLog Query : " + query.toString());

      pstmt.executeUpdate();
    }
    finally{
      releaseResource(pstmt, null);
    }
    return true;
  }

}