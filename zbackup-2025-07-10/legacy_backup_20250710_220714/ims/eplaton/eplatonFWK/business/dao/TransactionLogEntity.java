package com.ims.eplaton.eplatonFWK.business.dao;

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
import com.chb.coses.foundation.utility.TimeProcess;
import com.chb.coses.foundation.utility.StringUtils;
import java.text.*;
import java.io.*;

import com.ims.eplaton.foundation.helper.logej.LOGEJ;
import com.ims.eplaton.foundation.helper.CommonUtil;
import com.ims.eplaton.eplatonFWK.transfer.EPlatonEvent;

public class TransactionLogEntity extends AbstractEntity
{
    public static String TRANSACTION_LOG = "transaction-log-service";
    public static String FILE_LOG = "file-log";
    private EPlatonEvent event;
    public TransactionLogEntity()
    {
    }
    public TransactionLogEntity(EPlatonEvent event)
    {
      this.event = event;
    }

    public void insertTransactionInLog(Connection con       ,
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
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"=====================================");
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"TransactionID : " + transactionId);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"MethodName : " + methodName);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"SystemName : " + systemName);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"HostName : " + hostName);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"UserID : " + userId);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"ChannelType : " + channelType);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"BusinessDate : " + businessDate);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"EventNo : " + eventNo);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"IP Address : " + IPAddress);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"=====================================");

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

            LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"insertTransactionInLog Query : " + query.toString());

            pstmt.executeUpdate();
            con.commit();
        }
        finally
        {
            releaseResource(pstmt, null);
        }
    }

    public  void insertTransactionOutLog(Connection con       ,
            String transactionId ,
            String transactionNo ,
            long responseTime    ,
            String errorCode)
            throws SQLException
    {
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"=====================================");
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"TransactionID : " + transactionId);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"TransactionNo : " + transactionNo);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"ResponseTime : " + responseTime);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"ErrorCode : " + errorCode);
        LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"=====================================");


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

            LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"insertTransactionOutLog Query : " + query.toString());

            pstmt.executeUpdate();
            con.commit();
        }
        finally
        {
            releaseResource(pstmt, null);
        }
    }

    public  void insertTransactionLog2File(String logLine){
        String fileLog = Config.getInstance().getElement(TRANSACTION_LOG).getChild(FILE_LOG).getText();
        if(!"disable".equals(fileLog)){
            FileWriter writer = null;
            BufferedWriter bufWriter = null;
            try{
                String fileName = getFileName();
                LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"<fileName : "+fileName+">");
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


