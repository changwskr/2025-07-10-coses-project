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
import com.kdb.oversea.framework.transaction.constant.TCFConstants;
import com.kdb.oversea.foundation.logej.LOGEJ;
import com.kdb.oversea.foundation.utility.CommonUtil;
import com.kdb.oversea.foundation.utility.FPrintf;
import com.kdb.oversea.eplatonframework.transfer.EPlatonEvent;
import com.kdb.oversea.framework.transaction.model.TransactionLogDDTO;

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

public class TransactionLogEntity extends AbstractEntity
{

  public TransactionLogEntity()
  {
  }

  public static void insertTransactionInLog(
      Connection con ,
      EPlatonEvent event)
      throws SQLException
  {
    String transactionId = event.getTPSVCINFODTO().getBp_sequence()  ;
    String methodName    = event.getTPSVCINFODTO().getOperation_method();
    String systemName    = event.getTPSVCINFODTO().getSystem_name();
    String hostName      = CommonUtil.GetHostName();
    String bankCode      = event.getCommon().getBankCode();
    String branchCode    = event.getCommon().getBranchCode();
    String userId        = event.getCommon().getUserID();
    String channelType   = event.getCommon().getChannelType();
    String businessDate  = event.getCommon().getBusinessDate();
    String eventNo       = event.getCommon().getEventNo();
    String IPAddress     = event.getCommon().getIPAddress();
    String org_seq       = event.getTPSVCINFODTO().getOrgseq();
    String transaction_no  = event.getTPSVCINFODTO().getHostseq();
    String tpfq       = event.getTPSVCINFODTO().getTpfq();

    /**
     * 클라이언트에서 올라온 시퀀스는 서버에서 작업시 테이블 무결성 제약조건에
     * 걸려서 다음과 같은 포맷으로 재셋팅해서 사용합니다.
     * 타입 : bp_sequence + "-" + orgseq + "-" + hostseq + "-" + tpfq
     *       10101010-00000001-00000001-200,10101010-00000001-00000002-100
     */
    transactionId = transactionId + "-" + org_seq + "-" + transaction_no + "-" + tpfq;

    LOGEJ.getInstance().printf(1,event,"  =====================================");
    LOGEJ.getInstance().printf(1,event,"TransactionID : " + transactionId);
    LOGEJ.getInstance().printf(1,event,"MethodName : " + methodName);
    LOGEJ.getInstance().printf(1,event,"SystemName : " + systemName);
    LOGEJ.getInstance().printf(1,event,"HostName : " + hostName);
    LOGEJ.getInstance().printf(1,event,"UserID : " + userId);
    LOGEJ.getInstance().printf(1,event,"ChannelType : " + channelType);
    LOGEJ.getInstance().printf(1,event,"BusinessDate : " + businessDate);
    LOGEJ.getInstance().printf(1,event,"EventNo : " + eventNo);
    LOGEJ.getInstance().printf(1,event,"IP Address : " + IPAddress);
    LOGEJ.getInstance().printf(1,event,"transaction_no : " + transaction_no);
    LOGEJ.getInstance().printf(1,event,"=====================================");

    PreparedStatement pstmt = null;
    StringBuffer query = new StringBuffer();
    query.append("INSERT INTO transaction_info (                                   ");
    query.append("                             transaction_id  ,                  ");
    query.append("                             host_name       ,                  ");
    query.append("                             system_name     ,                  ");
    query.append("                             method_name     ,                  ");
    query.append("                             bank_code       ,                  ");
    query.append("                             branch_code     ,                  ");
    query.append("                             user_id         ,                  ");
    query.append("                             channel_type    ,                  ");
    query.append("                             business_date   ,                  ");
    query.append("                             register_date   ,                  ");
    query.append("                             in_time         ,                  ");
    query.append("                             out_time        ,                  ");
    query.append("                             event_no        ,                  ");
    query.append("                             transaction_no  ,                  ");
    query.append("                             org_seq         ,                  ");
    query.append("                             tpfq            ,                  ");
    query.append("                             ip_address                         ");
    query.append("                            )                                   ");
    query.append("VALUES (                                                        ");
    query.append("        ?                            ,                          ");
    query.append("        ?                            ,                          ");
    query.append("        ?                            ,                          ");
    query.append("        ?                            ,                          ");
    query.append("        ?                            ,                          ");
    query.append("        ?                            ,                          ");
    query.append("        ?                            ,                          ");
    query.append("        ?                            ,                          ");
    query.append("        ?                            ,                          ");
    query.append("        to_char(sysdate, 'yyyyMMdd') ,                          ");
    query.append("        to_char(sysdate,'HH24MIss')  ,                          ");
    query.append("        'XXXXXX'                     ,                          ");
    query.append("        ?                            ,                          ");
    query.append("        ?                            ,                           ");
    query.append("        ?                            ,                           ");
    query.append("        ?                            ,                           ");
    query.append("        ?                                                      ");
    query.append("      )                                                         ");
    try
    {
      pstmt = con.prepareStatement(query.toString());
      int index = 0;
      pstmt.setString(++index, transactionId );
      pstmt.setString(++index, hostName      );
      pstmt.setString(++index, systemName    );
      pstmt.setString(++index, methodName    );
      pstmt.setString(++index, bankCode      );
      pstmt.setString(++index, branchCode    );
      pstmt.setString(++index, userId        );
      pstmt.setString(++index, channelType   );
      pstmt.setString(++index, businessDate  );
      pstmt.setString(++index, eventNo       );
      pstmt.setString(++index, transaction_no    );
      pstmt.setString(++index, org_seq       );
      pstmt.setString(++index, tpfq     );
      pstmt.setString(++index, IPAddress     );

      LOGEJ.getInstance().printf(1,event,"insertTransactionInLog Query : " + query.toString());

      pstmt.executeUpdate();
      con.commit();
    }
    finally
    {
      releaseResource(pstmt, null);
    }
  }

  public static void insertTransactionOutLog(
      Connection con       ,
      long interval_seconds    ,
      EPlatonEvent event   )
      throws SQLException
  {
    String transactionId = event.getTPSVCINFODTO().getBp_sequence();
    String transactionNo = event.getTPSVCINFODTO().getHostseq();
    String errorCode = event.getTPSVCINFODTO().getErrorcode();
    String org_seq       = event.getTPSVCINFODTO().getOrgseq();
    String transaction_no  = event.getTPSVCINFODTO().getHostseq();
    String tpfq       = event.getTPSVCINFODTO().getTpfq();

    /**
     * 클라이언트에서 올라온 시퀀스는 서버에서 작업시 테이블 무결성 제약조건에
     * 걸려서 다음과 같은 포맷으로 재셋팅해서 사용합니다.
     * 타입 : bp_sequence + "-" + orgseq + "-" + hostseq + "-" + tpfq
     *       10101010-00000001-00000001-200,10101010-00000001-00000002-100
     */
    transactionId = transactionId + "-" + org_seq + "-" + transaction_no + "-" + tpfq;

    LOGEJ.getInstance().printf(1,event,"=====================================");
    LOGEJ.getInstance().printf(1,event,"TransactionID : " + transactionId);
    LOGEJ.getInstance().printf(1,event,"TransactionNo : " + transactionNo);
    LOGEJ.getInstance().printf(1,event,"ResponseTime : " + interval_seconds);
    LOGEJ.getInstance().printf(1,event,"ErrorCode : " + errorCode);
    LOGEJ.getInstance().printf(1,event,"=====================================");


    PreparedStatement pstmt = null;
    StringBuffer query = new StringBuffer();
    query.append("UPDATE transaction_info                                  ");
    query.append("SET                                                     ");
    query.append("    out_time        = to_char(sysdate,'HH24MIss')  ,   ");
    query.append("    response_time   = ?                             ,   ");
    query.append("    error_code      = ?                                 ");
    query.append("WHERE transaction_id = ?                                ");
    try
    {
      pstmt = con.prepareStatement(query.toString());
      int index = 0;

      pstmt.setString(++index, CommonUtil.Float2Str( interval_seconds/1000.0f )  );
      pstmt.setString(++index, errorCode     );
      pstmt.setString(++index, transactionId );

      LOGEJ.getInstance().printf(1,event,"insertTransactionOutLog Query : " + query.toString());

      pstmt.executeUpdate();

      con.commit();

    }
    finally
    {
      releaseResource(pstmt, null);
    }
  }

  public static void insertTransactionLog2File(EPlatonEvent event,long interval_seconds)
  {
    FileWriter writer = null;
    BufferedWriter bufWriter = null;
    try{
      String logLine = getTransactionLogLineInfo(event,interval_seconds);
      String fileName = TCFConstants.TCF_TRANSACTION_INFO_FILE_NAME + "." + CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate() ;
      if(fileName!=null ){
        writer = new FileWriter(fileName, true);
        bufWriter = new BufferedWriter(writer);
        bufWriter.write(logLine);
      }
    }catch(IOException e){
      e.printStackTrace();
    }finally{
      try{
        if(bufWriter !=null && writer !=null){
          bufWriter.close();
          writer.close();
        }
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }

  public static String getTransactionLogLineInfo(EPlatonEvent event,long interval_seconds)
  {
    try{
      String hostName      = CommonUtil.GetHostName();
      String bankCode      = event.getCommon().getBankCode();
      String branchCode    = event.getCommon().getBranchCode();
      String channelType   = event.getCommon().getChannelType();

      String systemName    = event.getTPSVCINFODTO().getSystem_name();
      String methodName    = event.getTPSVCINFODTO().getOperation_method();
      String eventNo       = event.getCommon().getEventNo();
      String cdto_name     = event.getTPSVCINFODTO().getCdto_name();

      String systemDate  = event.getCommon().getSystemDate();
      String businessDate  = event.getCommon().getBusinessDate();
      String transaction_no= event.getTPSVCINFODTO().getHostseq();
      String org_seq       = event.getTPSVCINFODTO().getOrgseq();

      String userId        = event.getCommon().getUserID();
      String IPAddress     = event.getCommon().getIPAddress();
      String tpfq          = event.getTPSVCINFODTO().getTpfq();
      String transactionId = event.getTPSVCINFODTO().getBp_sequence() + "-" + org_seq + "-" + transaction_no + "-" + tpfq;

      String web_intime    = event.getTPSVCINFODTO().getWeb_intime();
      String tx_intime     = event.getTPSVCINFODTO().getSystemInTime();
      String tx_outtime     = event.getTPSVCINFODTO().getSystemOutTime();
      String interval_sends= CommonUtil.Long2Str(interval_seconds/1000);
      String errcode     = event.getTPSVCINFODTO().getErrorcode();
      String errcodemsg     = event.getTPSVCINFODTO().getError_message();

      StringBuffer stringformat = new StringBuffer();

/*
      stringformat.append( new FPrintf("%8.8s").sprintf(hostName      ) );
      stringformat.append( " ," );
      stringformat.append( new FPrintf("%2.2s").sprintf(bankCode      ) );
      stringformat.append( " ," );
      stringformat.append( new FPrintf("%2.2s").sprintf(branchCode    ) );
      stringformat.append( " ," );
      stringformat.append( new FPrintf("%2.2s").sprintf(channelType   ) );
      stringformat.append( " ," );
*/
      stringformat.append( new FPrintf("%10.10s").sprintf(systemName    ) );
      stringformat.append( " ," );
      stringformat.append( new FPrintf("%15.15s").sprintf(methodName    ) );
      stringformat.append( " ," );
      stringformat.append( new FPrintf("%8.8s").sprintf(eventNo       ) );
      stringformat.append( " ," );

      stringformat.append( new FPrintf("%8.8s").sprintf(systemDate  ) );
      stringformat.append( " ," );
      stringformat.append( new FPrintf("%8.8s").sprintf(businessDate  ) );
      stringformat.append( " ," );
      stringformat.append( new FPrintf("%8.8s").sprintf(transactionId ) );
      stringformat.append( " ," );
      stringformat.append( new FPrintf("%8.8s").sprintf(transaction_no) );
      stringformat.append( " ," );
      stringformat.append( new FPrintf("%8.8s").sprintf(org_seq       ) );
      stringformat.append( " ," );

      stringformat.append( new FPrintf("%8.8s").sprintf(userId        ) );
      stringformat.append( " ," );
      stringformat.append( new FPrintf("%19.19s").sprintf(IPAddress     ) );
      stringformat.append( " ," );
      stringformat.append( new FPrintf("%3.3s").sprintf(tpfq          ) );
      stringformat.append( " ," );

      stringformat.append( new FPrintf("%8.8s").sprintf(web_intime          ) );
      stringformat.append( " ," );
      stringformat.append( new FPrintf("%8.8s").sprintf(tx_intime          ) );
      stringformat.append( " ," );
      stringformat.append( new FPrintf("%8.8s").sprintf(tx_outtime          ) );
      stringformat.append( " ," );

      long itvmilsec = interval_seconds;
      float fitv = itvmilsec / 1000.0f;

      stringformat.append( new FPrintf("[%3.3s]").sprintf(CommonUtil.Float2Str(fitv) ));
      stringformat.append( " ," );

      stringformat.append( new FPrintf("%s").sprintf(errcode          ) );
      stringformat.append( " ," );

      stringformat.append( new FPrintf("%s").sprintf(errcodemsg          ) );
      stringformat.append( " ," );


      if( tpfq.equals("200") ) {
        stringformat.append( new FPrintf(" ,--REQ[%s]").sprintf(
            com.chb.coses.foundation.utility.Reflector.objectToString(event.getRequest() )
            ) );
        stringformat.append( new FPrintf(" ,--RES[%s]").sprintf(
            com.chb.coses.foundation.utility.Reflector.objectToString(event.getRequest() )
            ) );
      }

      stringformat.append( "\n" );

      return stringformat.toString();

    }
    catch(Exception ex){
    }
    return null;
  }

}