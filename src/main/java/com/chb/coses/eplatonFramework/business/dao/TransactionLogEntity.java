package com.chb.coses.eplatonFramework.business.dao;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.chb.coses.framework.constants.Constants;
import com.chb.coses.foundation.config.Config;
import com.chb.coses.foundation.log.Log;
import com.chb.coses.cosesFramework.business.delegate.model.TransactionLogDDTO;
import com.chb.coses.foundation.utility.TimeProcess;
import com.chb.coses.foundation.utility.StringUtils;
import java.text.*;
import java.io.*;

public class TransactionLogEntity extends AbstractEntity
{
    public static String TRANSACTION_LOG = "transaction-log-service";
    public static String FILE_LOG = "file-log";
    public TransactionLogEntity()
    {
    }
    public static void insertTransactionInLog(Connection con       ,
                                              String transactionId ,
                                              String methodName    ,
            String systemName    ,
            String hostName      ,
            String bankCode      ,
            String branchCode    ,
            String userId        ,
            String channelType   ,
            String businessDate  ,
            String eventNo       ,
            String IPAddress)
            throws SQLException
    {
        Log.DelegateLogger.debug("=====================================");
        Log.DelegateLogger.debug("TransactionID : " + transactionId);
        Log.DelegateLogger.debug("MethodName : " + methodName);
        Log.DelegateLogger.debug("SystemName : " + systemName);
        Log.DelegateLogger.debug("HostName : " + hostName);
        Log.DelegateLogger.debug("UserID : " + userId);
        Log.DelegateLogger.debug("ChannelType : " + channelType);
        Log.DelegateLogger.debug("BusinessDate : " + businessDate);
        Log.DelegateLogger.debug("EventNo : " + eventNo);
        Log.DelegateLogger.debug("IP Address : " + IPAddress);
        Log.DelegateLogger.debug("=====================================");

        PreparedStatement pstmt = null;
        StringBuffer query = new StringBuffer();
        query.append("INSERT INTO transaction_log (                                   ");
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
        query.append("        ?                                                       ");
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
            pstmt.setString(++index, IPAddress     );

            Log.DelegateLogger.debug("insertTransactionInLog Query : " + query.toString());

            pstmt.executeUpdate();
            con.commit();
        }
        finally
        {
            releaseResource(pstmt, null);
        }
    }

    public static void insertTransactionOutLog(Connection con       ,
            String transactionId ,
            String transactionNo ,
            long responseTime    ,
            String errorCode)
            throws SQLException
    {
        Log.DelegateLogger.debug("=====================================");
        Log.DelegateLogger.debug("TransactionID : " + transactionId);
        Log.DelegateLogger.debug("TransactionNo : " + transactionNo);
        Log.DelegateLogger.debug("ResponseTime : " + responseTime);
        Log.DelegateLogger.debug("ErrorCode : " + errorCode);
        Log.DelegateLogger.debug("=====================================");


        PreparedStatement pstmt = null;
        StringBuffer query = new StringBuffer();
        query.append("UPDATE transaction_log                                  ");
        query.append("SET                                                     ");
        query.append("    transaction_no  = ?                             ,   ");
        query.append("    out_time        = to_char(sysdate,'HH24MIss')  ,   ");
        query.append("    response_time   = ?                             ,   ");
        query.append("    error_code      = ?                                 ");
        query.append("WHERE transaction_id = ?                                ");
        try
        {
            pstmt = con.prepareStatement(query.toString());
            int index = 0;

            pstmt.setString(++index, transactionNo );
            pstmt.setLong  (++index, responseTime  );
            pstmt.setString(++index, errorCode     );
            pstmt.setString(++index, transactionId );

            Log.DelegateLogger.debug("insertTransactionOutLog Query : " + query.toString());

            pstmt.executeUpdate();
            con.commit();
        }
        finally
        {
            releaseResource(pstmt, null);
        }
    }

    public static void insertTransactionLog2File(String logLine){
        String fileLog = Config.getInstance().getElement(TRANSACTION_LOG).getChild(FILE_LOG).getText();
        if(!"disable".equals(fileLog)){
            FileWriter writer = null;
            BufferedWriter bufWriter = null;
            try{
                String fileName = getFileName();
                com.chb.coses.foundation.log.Log.DelegateLogger.debug("<fileName : "+fileName+">");
                if(fileName!=null && !"dummy".equals(fileName)){
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
    }

    public static String getFileName(){
       String dateFormat = "yyyyMMdd";
       Format df = new SimpleDateFormat(dateFormat);
       String filePath = Config.getInstance().getElement(TRANSACTION_LOG).getChild(FILE_LOG).getText();
       String fileName = filePath.substring(filePath.lastIndexOf(File.separator)+1);
       filePath = StringUtils.replace(filePath, fileName,df.format(new java.util.Date()) + "_"+fileName);
       return filePath;

    }
}


