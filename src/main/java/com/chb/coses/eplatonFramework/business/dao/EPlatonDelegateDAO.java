package com.chb.coses.eplatonFramework.business.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;

import com.chb.coses.foundation.log.Log;
import com.chb.coses.framework.business.dao.AbstractDAO;
import com.chb.coses.eplatonFramework.transfer.EPlatonEvent;
import com.chb.coses.eplatonFramework.business.model.TransactionLogDDTO;
import com.chb.coses.eplatonFramework.business.helper.*;
import com.chb.coses.foundation.db.DBService;
import com.chb.coses.foundation.config.Config;
/**
 * Delegate DAO(Data Access Object) 구현이다.
 *
 * @author  <a href="mailto:ghyu@imssystem.com">Gwanghyeok Yu</a>
 * @version 1.0, 2002/10/08
 */
public class EPlatonDelegateDAO extends AbstractDAO implements IEPlatonDelegateDAO
{
    public void DB_INSERTinlog(EPlatonEvent event)
            throws DAOException
    {
        Log.DelegateLogger.debug("<DAO - transactionInLog-method:start>");
        Connection con = null;
        try {
            Log.DelegateLogger.debug(com.chb.coses.foundation.utility.Reflector.objectToString(event));
            DTOConverter converter = new DTOConverter();
            TransactionLogDDTO logDDTO = converter.getTransactionLogDDTO(event);

            con = getConnection();
            //con = DBService.getInstance().getConnection(Config.getInstance().getElement("connection_pool").getChild("name").getTextTrim());

            Log.DelegateLogger.debug(com.chb.coses.foundation.utility.Reflector.objectToString(logDDTO));
            TransactionLogEntity.insertTransactionInLog(
                                                        con                        ,
                                                        logDDTO.getTransactionId() ,
                                                        logDDTO.getMethodName()    ,
                                                        logDDTO.getSystemName()    ,
                                                        logDDTO.getHostName()      ,
                                                        logDDTO.getBankCode()      ,
                                                        logDDTO.getBranchCode()    ,
                                                        logDDTO.getUserId()        ,
                                                        logDDTO.getChannelType()   ,
                                                        logDDTO.getBusinessDate()  ,
                                                        logDDTO.getEventNo()       ,
                                                        logDDTO.getIPAddress()
                                                        );

        } catch (NamingException e) {
            throw new DAOException(e);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            releaseConnection(con);
        }
        Log.DelegateLogger.debug("<DAO - transactionInLog-method:end>");
    }

    public void DB_INSERToutlog(EPlatonEvent event)
               throws DAOException
       {
           Connection con = null;
           Log.DelegateLogger.debug(com.chb.coses.foundation.utility.Reflector.objectToString(event));
           try {
               DTOConverter converter = new DTOConverter();
               TransactionLogDDTO logDDTO = converter.getTransactionLogDDTO(event);

               con = getConnection();
               TransactionLogEntity.insertTransactionOutLog(
                                                           con                        ,
                                                           logDDTO.getTransactionId() ,
                                                           logDDTO.getTransactionNo() ,
                                                           logDDTO.getResponseTime()  ,
                                                           logDDTO.getErrorCode()
                                                           );
               TransactionLogEntity.insertTransactionLog2File(getLogLine(logDDTO));

           } catch (NamingException e) {
               throw new DAOException(e);
           } catch (SQLException e) {
               throw new DAOException(e);
           } finally {
               releaseConnection(con);
           }
    }
    private static String getLogLine(TransactionLogDDTO logDDTO){
       String TAB = " ";
       return logDDTO.getTransactionId() + TAB +
               logDDTO.getHostName()     + TAB +
               logDDTO.getSystemName()   + TAB +
               logDDTO.getMethodName()   + TAB +
               logDDTO.getBankCode()     + TAB +
               logDDTO.getBranchCode()   + TAB +
               logDDTO.getUserId()       + TAB +
               logDDTO.getChannelType()  + TAB +
               logDDTO.getBusinessDate() + TAB +
               logDDTO.getRegisterDate() + TAB +
               logDDTO.getInTime()       + TAB +
               logDDTO.getEventNo()      + TAB +
               logDDTO.getOutTime()      + TAB +
               logDDTO.getResponseTime() + TAB +
               logDDTO.getErrorCode()    + TAB +
               logDDTO.getIPAddress()    + "\n";
    }
    // ------------------------------------------------ helper method

    public void releaseConnection(Connection con)
    {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) { /* ignored */ }
        }
    }

    // --------------------------------------------------- log method

    public String queryForBusinessDate(String bankCode)
    {
        StringBuffer queryBuffer = new StringBuffer();
        String businessDate = null;

        queryBuffer.append("SELECT ");
        queryBuffer.append(" BUSINESS_DATE");
        queryBuffer.append(" FROM");
        queryBuffer.append(" DUAL");
        queryBuffer.append(" WHERE");
        queryBuffer.append(" BANK_CODE");
        queryBuffer.append(" = ?");

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try
        {
            con = this.getConnection();
            psmt = con.prepareStatement(queryBuffer.toString());
            psmt.setString(1, bankCode);

            Log.SDBLogger.debug("Query : " + queryBuffer.toString());

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

    public void log(Object message)
    {
        Log.DelegateLogger.debug(message);
    }

    public void log(Object message, Throwable throwable)
    {
        Log.DelegateLogger.debug(message, throwable);
    }
}

