package com.ims.eplaton.cashCard.business.facade.dao;

import java.sql.*;
import java.util.ArrayList;
import javax.ejb.EJBException;
import javax.naming.NamingException;

import com.chb.coses.framework.business.dao.AbstractDAO;
import com.chb.coses.foundation.log.Log;
import com.chb.coses.cosesFramework.transfer.CosesCommonDTO;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.exception.CosesExceptionDetail;

import com.ims.eplaton.cashCard.business.constants.CashCardConstants;
import com.ims.eplaton.cashCard.transfer.CashCardCDTO;
import com.ims.eplaton.cashCard.transfer.CashCardPageCDTO;
import com.ims.eplaton.cashCard.transfer.CashCardPageItemCDTO;
import com.ims.eplaton.cashCard.transfer.CashCardConditionCDTO;
import com.ims.eplaton.cashCard.transfer.HotCardCDTO;
import com.ims.eplaton.cashCard.transfer.HotCardListCDTO;
import com.ims.eplaton.cashCard.transfer.HotCardPageCDTO;
import com.ims.eplaton.cashCard.transfer.HotCardPageItemCDTO;
import com.ims.eplaton.cashCard.transfer.HotCardQueryConditionCDTO;

public class CashCardDAO extends AbstractDAO implements ICashCardDAO
{

    public CashCardDAO()
    {
    }

    public int getLastSequenceNoForRegisterCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        StringBuffer queryBuffer = new StringBuffer();

        queryBuffer.append("SELECT ");
        queryBuffer.append(" MAX(SEQUENCE_NO)");
        queryBuffer.append(" FROM");
        queryBuffer.append(" CASH_CARD");
        queryBuffer.append(" WHERE");
        queryBuffer.append(" BANK_CODE");
        queryBuffer.append(" = ?");
        queryBuffer.append(" AND");
        queryBuffer.append(" BRANCH_CODE");
        queryBuffer.append(" = ?");
        queryBuffer.append(" AND");
        queryBuffer.append(" PRIMARY_ACCOUNT_NO");
        queryBuffer.append(" = ?");

        Log.SDBLogger.debug("Query : " + queryBuffer.toString());

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int lastSequenceNo = 1;
        try
        {
            con = this.getConnection();
            psmt = con.prepareStatement(queryBuffer.toString());
            psmt.setString(1, commonDTO.getBankCode());
            psmt.setString(2, commonDTO.getBranchCode());
            psmt.setString(3, cashCardCDTO.getPrimaryAccountNo());

            rs = psmt.executeQuery();

            if(rs.next())
            {
                lastSequenceNo = rs.getInt(1);

                if(lastSequenceNo == 0)
                {
                    lastSequenceNo = 1;
                }
                else
                {
                    lastSequenceNo = lastSequenceNo + 1;
                }
            }
        }
        catch(NamingException ne)
        {
            throw new EJBException(ne);
        }
        catch(SQLException se)
        {
            throw new EJBException(se);
        }
        finally
        {
            releaseResource(con, psmt, rs);
        }

        Log.SDBLogger.debug("LastSequenceNo : " + lastSequenceNo);

        return lastSequenceNo;
    }

    public int getLastSequenceNoForRegisterHotCard(HotCardCDTO hotCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        StringBuffer queryBuffer = new StringBuffer();

        queryBuffer.append("SELECT ");
        queryBuffer.append(" MAX(SEQUENCE_NO)");
        queryBuffer.append(" FROM");
        queryBuffer.append(" HOT_CARD");
        queryBuffer.append(" WHERE");
        queryBuffer.append(" CARD_NUMBER");
        queryBuffer.append(" = ?");

        Log.SDBLogger.debug("Query For RegisterHotCard : " + queryBuffer.toString());

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int lastSequenceNo = 1;
        try
        {
            con = this.getConnection();
            psmt = con.prepareStatement(queryBuffer.toString());
            psmt.setString(1, hotCardCDTO.getCardNumber());

            rs = psmt.executeQuery();

            if(rs.next())
            {
                lastSequenceNo = rs.getInt(1);

                if(lastSequenceNo == 0)
                {
                    lastSequenceNo = 1;
                }
                else
                {
                    lastSequenceNo = lastSequenceNo + 1;
                }
            }
        }
        catch(NamingException ne)
        {
            throw new EJBException(ne);
        }
        catch(SQLException se)
        {
            throw new EJBException(se);
        }
        finally
        {
            releaseResource(con, psmt, rs);
        }

        Log.SDBLogger.debug("LastSequenceNo : " + lastSequenceNo);

        return lastSequenceNo;
    }

    public CashCardCDTO queryForCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        StringBuffer queryBuffer = new StringBuffer();

        queryBuffer.append("SELECT ");
        queryBuffer.append(" BANK_TYPE,");
        queryBuffer.append(" BANK_CODE,");
        queryBuffer.append(" PRIMARY_ACCOUNT_NO,");
        queryBuffer.append(" SEQUENCE_NO,");
        queryBuffer.append(" CARD_NUMBER,");
        queryBuffer.append(" BRANCH_CODE,");
        queryBuffer.append(" TYPE,");
        queryBuffer.append(" CIF_NO,");
        queryBuffer.append(" CIF_NAME,");
        queryBuffer.append(" PASSWORD_NO,");
        queryBuffer.append(" INVALID_ATTEMPT_CNT,");
        queryBuffer.append(" SECONDARY_ACCOUNT_NO,");
        queryBuffer.append(" TERNARY_ACCOUNT_NO,");
        queryBuffer.append(" DAILY_LIMIT_CCY,");
        queryBuffer.append(" DAILY_LIMIT_AMOUNT,");
        queryBuffer.append(" DAILY_ACCUM_AMOUNT,");
        queryBuffer.append(" DAILY_TRF_LIMIT_CCY,");
        queryBuffer.append(" DAILY_TRF_LIMIT_AMOUNT,");
        queryBuffer.append(" DAILY_TRF_ACCUM_AMOUNT,");
        queryBuffer.append(" DAILY_ACCUM_RESET_DATE,");
        queryBuffer.append(" DAILY_ACCUM_RESET_TIME,");
        queryBuffer.append(" ISSUE_DATE,");
        queryBuffer.append(" EFFECTIVE_DATE,");
        queryBuffer.append(" EXPIRY_DATE,");
        queryBuffer.append(" STATUS,");
        queryBuffer.append(" INCIDENT_CODE,");
        queryBuffer.append(" FEE_WAIVE,");
        queryBuffer.append(" FEE_CCY,");
        queryBuffer.append(" FEE_AMOUNT,");
        queryBuffer.append(" REGISTER_DATE,");
        queryBuffer.append(" REGISTER_TIME,");
        queryBuffer.append(" REGISTER_BY,");
        queryBuffer.append(" REMARK,");
        queryBuffer.append(" LAST_UPDATE_DATE,");
        queryBuffer.append(" LAST_UPDATE_TIME,");
        queryBuffer.append(" LAST_UPDATE_USER_ID,");
        queryBuffer.append(" MIS_SEND_DATE");
        queryBuffer.append(" FROM");
        queryBuffer.append(" CASH_CARD");
        queryBuffer.append(" WHERE");
        queryBuffer.append(" BANK_CODE");
        queryBuffer.append(" = ?");
        //queryBuffer.append(" AND");
        //queryBuffer.append(" BRANCH_CODE");
        //queryBuffer.append(" = ?");
        queryBuffer.append(" AND");
        queryBuffer.append(" CARD_NUMBER");
        queryBuffer.append(" = ?");

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try
        {
            con = this.getConnection();
            psmt = con.prepareStatement(queryBuffer.toString());
            psmt.setString(1, commonDTO.getBankCode());
            //psmt.setString(2, commonDTO.getBranchCode());
            psmt.setString(2, cashCardCDTO.getCardNumber());

            Log.SDBLogger.debug("Query : " + queryBuffer.toString());

            rs = psmt.executeQuery();

            if(rs.next())
            {
                cashCardCDTO.setBankType(rs.getString("BANK_TYPE"));
                cashCardCDTO.setBankCode(rs.getString("BANK_CODE"));
                cashCardCDTO.setBranchCode(rs.getString("BRANCH_CODE"));
                //cashCardCDTO.setCardHolderName(rs.getString("CARD_HOLDER_NAME"));
                //cashCardCDTO.setCardNumber(rs.getString("CARD_NUMBER"));
                cashCardCDTO.setCIFNo(rs.getString("CIF_NO"));
                cashCardCDTO.setCIFName(rs.getString("CIF_NAME"));
                cashCardCDTO.setDailyAccumAmount(rs.getBigDecimal("DAILY_ACCUM_AMOUNT"));
                cashCardCDTO.setDailyAccumResetDate(rs.getString("DAILY_ACCUM_RESET_DATE"));
                cashCardCDTO.setDailyAccumResetTime(rs.getString("DAILY_ACCUM_RESET_TIME"));
                cashCardCDTO.setDailyLimitAmount(rs.getBigDecimal("DAILY_LIMIT_AMOUNT"));
                cashCardCDTO.setDailyLimitCcy(rs.getString("DAILY_LIMIT_CCY"));
                cashCardCDTO.setDailyTrfAccumAmount(rs.getBigDecimal("DAILY_TRF_ACCUM_AMOUNT"));
                cashCardCDTO.setDailyTrfLimitAmount(rs.getBigDecimal("DAILY_TRF_LIMIT_AMOUNT"));
                cashCardCDTO.setDailyTrfLimitCcy(rs.getString("DAILY_TRF_LIMIT_CCY"));
                cashCardCDTO.setEffectiveDate(rs.getString("EFFECTIVE_DATE"));
                cashCardCDTO.setExpiryDate(rs.getString("EXPIRY_DATE"));
                cashCardCDTO.setFeeAmount(rs.getBigDecimal("FEE_AMOUNT"));
                cashCardCDTO.setFeeCcy(rs.getString("FEE_CCY"));
                cashCardCDTO.setFeeWaive(rs.getString("FEE_WAIVE"));
                cashCardCDTO.setIssueDate(rs.getString("ISSUE_DATE"));
                cashCardCDTO.setIncidentCode(rs.getString("INCIDENT_CODE"));
                cashCardCDTO.setInvalidAttemptCnt(rs.getInt("INVALID_ATTEMPT_CNT"));
                cashCardCDTO.setLastUpdateDate(rs.getString("LAST_UPDATE_DATE"));
                cashCardCDTO.setLastUpdateTime(rs.getString("LAST_UPDATE_TIME"));
                cashCardCDTO.setLastUpdateUserID(rs.getString("LAST_UPDATE_USER_ID"));
                cashCardCDTO.setMISSendDate(rs.getString("MIS_SEND_DATE"));
                cashCardCDTO.setPasswordNo(rs.getString("PASSWORD_NO"));
                cashCardCDTO.setPrimaryAccountNo(rs.getString("PRIMARY_ACCOUNT_NO"));
                cashCardCDTO.setRegisterBy(rs.getString("REGISTER_BY"));
                cashCardCDTO.setRegisterDate(rs.getString("REGISTER_DATE"));
                cashCardCDTO.setRegisterTime(rs.getString("REGISTER_TIME"));
                cashCardCDTO.setRemark(rs.getString("REMARK"));
                cashCardCDTO.setSecondaryAccountNo(rs.getString("SECONDARY_ACCOUNT_NO"));
                cashCardCDTO.setSequenceNo(rs.getInt("SEQUENCE_NO"));
                cashCardCDTO.setStatus(rs.getString("STATUS"));
                cashCardCDTO.setTernaryAccountNo(rs.getString("TERNARY_ACCOUNT_NO"));
                cashCardCDTO.setType(rs.getString("TYPE"));
            }
        }
        catch(NamingException ne)
        {
            throw new EJBException(ne);
        }
        catch(SQLException se)
        {
            throw new EJBException(se);
        }
        finally
        {
            releaseResource(con, psmt, rs);
        }
        return cashCardCDTO;
    }

    public CashCardPageCDTO listForCashCardNumber(CashCardConditionCDTO conditionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        Connection con = null;
        PreparedStatement ps= null;
        ResultSet rs = null;

        int totalLineCount = getRecordCount(conditionCDTO, commonDTO);
        CashCardPageCDTO page = null;

        if (conditionCDTO.getLinePerPage() > 0 ) {
            page = new CashCardPageCDTO(new ArrayList(),
                    conditionCDTO.getPageNumber(), totalLineCount,
                    conditionCDTO.getLinePerPage());
        }
        else
        {
            page = new CashCardPageCDTO(new ArrayList(),
                    conditionCDTO.getPageNumber(), totalLineCount, totalLineCount);
        }

        StringBuffer query = new StringBuffer();
        query.append("SELECT * FROM (SELECT Temp.*, ROWNUM num FROM (SELECT ");
        query.append(conditionCDTO.getSelectValue());
        query.append(" FROM ");
        query.append(conditionCDTO.getTableName());
        query.append(" WHERE ");
        query.append(" BANK_CODE = ?");
        query.append(" AND BRANCH_CODE = ?");
        if(!"".equals(conditionCDTO.getCIFNo()))
        {
            query.append(" AND CIF_NO = '" + conditionCDTO.getCIFNo() + "'");
        }
        else if(!"".equals(conditionCDTO.getCIFName()))
        {
            query.append(" AND upper(" + conditionCDTO.getTableName() +
                               ".CIF_NAME) LIKE '%" + conditionCDTO.getCIFName().toUpperCase() + "%' ");
        }
        else if(!"".equals(conditionCDTO.getPrimaryAccountNo()))
        {
            query.append(" AND PRIMARY_ACCOUNT_NO = '" + conditionCDTO.getPrimaryAccountNo() + "'");
        }
        query = query.append(") Temp) WHERE num >= ? AND num <= ?");

        Log.SDBLogger.debug("listCashCardNumber Query : " + query.toString());

        try {
            con = this.getConnection();
            ps = con.prepareStatement(query.toString());
            ps.setString(1, commonDTO.getBankCode());
            ps.setString(2, commonDTO.getBranchCode());
            ps.setInt(3, page.startLineNumber());
            ps.setInt(4, page.endLineNumber());
            rs = ps.executeQuery();

            while(rs.next())
            {
                CashCardPageItemCDTO pageItemCDTO = new CashCardPageItemCDTO();
                pageItemCDTO = makeCashCardPageItem(pageItemCDTO, rs);
                page.add(pageItemCDTO);
            }
        }
        catch(NamingException ne)
        {
            throw new EJBException();
        }
        catch(SQLException sqle)
        {
            // throw new CosesAppException(new CosesExceptionDetail("sqlException"));
            throw new EJBException(sqle);
        }
        finally
        {
            releaseResource(con, ps, rs);
        }
        return page;
    }

    public HotCardPageCDTO listForHotCard(HotCardQueryConditionCDTO conditionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        Connection con = null;
        PreparedStatement ps= null;
        ResultSet rs = null;

        int totalLineCount = getRecordCountForHotCard(conditionCDTO, commonDTO);
        HotCardPageCDTO page = null;

        if (conditionCDTO.getLinePerPage() > 0 ) {
            page = new HotCardPageCDTO(new ArrayList(),
                                       conditionCDTO.getPageNumber(), totalLineCount,
                                       conditionCDTO.getLinePerPage());
        }
        else
        {
            page = new HotCardPageCDTO(new ArrayList(),
                                       conditionCDTO.getPageNumber(), totalLineCount, totalLineCount);
        }


        StringBuffer query = new StringBuffer();
        query.append("SELECT * FROM (SELECT Temp.*, ROWNUM num FROM (SELECT ");
        query.append(conditionCDTO.getSelectValue());
        query.append(" FROM ");
        query.append(conditionCDTO.getTableName());
        if(!"".equals(conditionCDTO.getPrimaryAccountNo()))
        {
            query.append(" WHERE ");
            query.append(" PRIMARY_ACCOUNT_NO = '" + conditionCDTO.getPrimaryAccountNo() + "'");
        }
        query = query.append(") Temp) WHERE num >= ? AND num <= ?");

        try {
            con = this.getConnection();
            ps = con.prepareStatement(query.toString());
            ps.setInt(1, page.startLineNumber());
            ps.setInt(2, page.endLineNumber());

            Log.SDBLogger.debug("listForHotCard Query : " + query.toString());

            rs = ps.executeQuery();

            while(rs.next())
            {
                HotCardPageItemCDTO pageItemCDTO = new HotCardPageItemCDTO();
                pageItemCDTO = makeHotCardPageItem(pageItemCDTO, rs);
                page.add(pageItemCDTO);
            }
        }
        catch(NamingException ne)
        {
            throw new EJBException();
        }
        catch(SQLException sqle)
        {
            throw new EJBException(sqle);
        }
        finally
        {
            releaseResource(con, ps, rs);
        }
        return page;
    }

    public CashCardPageCDTO listForCashCard(CashCardConditionCDTO conditionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        Connection con = null;
        PreparedStatement ps= null;
        ResultSet rs = null;

        int totalLineCount = getRecordCount(conditionCDTO, commonDTO);
        CashCardPageCDTO page = null;

        if (conditionCDTO.getLinePerPage() > 0 ) {
            page = new CashCardPageCDTO(new ArrayList(),
                    conditionCDTO.getPageNumber(), totalLineCount,
                    conditionCDTO.getLinePerPage());
        }
        else
        {
            page = new CashCardPageCDTO(new ArrayList(),
                    conditionCDTO.getPageNumber(), totalLineCount, totalLineCount);
        }

        StringBuffer query = new StringBuffer();
        query.append("SELECT * FROM (SELECT Temp.*, ROWNUM num FROM (SELECT ");
        query.append(conditionCDTO.getSelectValue());
        query.append(" FROM ");
        query.append(conditionCDTO.getTableName());
        query.append(" WHERE ");
        query.append(" BANK_CODE = ?");
        query.append(" AND BRANCH_CODE = ?");
        query.append(" AND STATUS = ?");
        if(!"".equals(conditionCDTO.getToDate()))
        {
            query.append(" AND (REGISTER_DATE > ?");
            query.append(" AND REGISTER_DATE <= ?)");
        }

        if(conditionCDTO.getOrderByMethod().equals(CashCardConstants.ORDER_BY_ASC))
        {
            query.append(" ORDER BY CARD_NUMBER ASC");
        }
        else
        {
            query.append(" ORDER BY CARD_NUMBER DESC");
        }
        query = query.append(") Temp) WHERE num >= ? AND num <= ?");

        try {
            con = this.getConnection();
            ps = con.prepareStatement(query.toString());
            ps.setString(1, commonDTO.getBankCode());
            ps.setString(2, commonDTO.getBranchCode());
            ps.setString(3, conditionCDTO.getStatus());
            if(!"".equals(conditionCDTO.getToDate()))
            {
                ps.setString(4, conditionCDTO.getFromDate());
                ps.setString(5, conditionCDTO.getToDate());
                ps.setInt(6, page.startLineNumber());
                ps.setInt(7, page.endLineNumber());
            }
            else
            {
                ps.setInt(4, page.startLineNumber());
                ps.setInt(5, page.endLineNumber());
            }

            Log.SDBLogger.debug("listCashCard Query : " + query.toString());

            rs = ps.executeQuery();

            while(rs.next())
            {
                CashCardPageItemCDTO pageItemCDTO = new CashCardPageItemCDTO();
                pageItemCDTO = makeCashCardPageItem(pageItemCDTO, rs);
                page.add(pageItemCDTO);
            }
        }
        catch(NamingException ne)
        {
            throw new EJBException();
        }
        catch(SQLException sqle)
        {
            throw new EJBException(sqle);
        }
        finally
        {
            releaseResource(con, ps, rs);
        }
        return page;
    }

    public CashCardPageCDTO listInvalidAttemptCard(CashCardConditionCDTO conditionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        Connection con = null;
        PreparedStatement ps= null;
        ResultSet rs = null;

        int totalLineCount = getRecordCountForInvalidAttemptCard(conditionCDTO, commonDTO);
        CashCardPageCDTO page = null;

        if (conditionCDTO.getLinePerPage() > 0 ) {
            page = new CashCardPageCDTO(new ArrayList(),
                                        conditionCDTO.getPageNumber(), totalLineCount,
                                        conditionCDTO.getLinePerPage());
        }
        else
        {
            page = new CashCardPageCDTO(new ArrayList(),
                                        conditionCDTO.getPageNumber(), totalLineCount, totalLineCount);
        }

        StringBuffer query = new StringBuffer();
        query.append("SELECT * FROM (SELECT Temp.*, ROWNUM num FROM (SELECT ");
        query.append(conditionCDTO.getSelectValue());
        query.append(" FROM ");
        query.append(conditionCDTO.getTableName());
        query.append(" WHERE ");
        query.append(" BANK_CODE = ?");
        query.append(" AND BRANCH_CODE = ?");
        query.append(" AND MIS_SEND_DATE = ?");
        query.append(" AND INVALID_ATTEMPT_CNT > 3");
        query = query.append(") Temp) WHERE num >= ? AND num <= ?");

        Log.SDBLogger.debug("listCashCardNumber Query : " + query.toString());

        try {
            con = this.getConnection();
            ps = con.prepareStatement(query.toString());
            ps.setString(1, commonDTO.getBankCode());
            ps.setString(2, commonDTO.getBranchCode());
            ps.setString(3, commonDTO.getBusinessDate());
            ps.setInt(4, page.startLineNumber());
            ps.setInt(5, page.endLineNumber());
            rs = ps.executeQuery();

            while(rs.next())
            {
                CashCardPageItemCDTO pageItemCDTO = new CashCardPageItemCDTO();
                pageItemCDTO = makeCashCardPageItemForInvalid(pageItemCDTO, rs);
                page.add(pageItemCDTO);
            }
        }
        catch(NamingException ne)
        {
            throw new EJBException();
        }
        catch(SQLException sqle)
        {
            // throw new CosesAppException(new CosesExceptionDetail("sqlException"));
            throw new EJBException(sqle);
        }
        finally
        {
            releaseResource(con, ps, rs);
        }
        return page;
    }

    public HotCardListCDTO selectHotCardForRegister(HotCardCDTO hotCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        StringBuffer query = new StringBuffer();

        query.append("SELECT");
        query.append(" CARD_NUMBER, SEQUENCE_NO, PRIMARY_ACCOUNT_NO,");
        query.append(" CIF_NAME, STATUS, INCIDENT_CODE");
        query.append(" FROM HOT_CARD");
        query.append(" WHERE PRIMARY_ACCOUNT_NO = ?");

        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        HotCardListCDTO hotCardListCDTO = new HotCardListCDTO();

        try
        {
            con = this.getConnection();
            psmt = con.prepareStatement(query.toString());
            psmt.setString(1, hotCardCDTO.getPrimaryAccountNo());

            Log.SDBLogger.debug("Query : " + query.toString());

            rs = psmt.executeQuery();

            while(rs.next())
            {
                HotCardCDTO cdto = new HotCardCDTO();

                cdto = makeHotCardCDTO(cdto, rs);

                hotCardListCDTO.add(cdto);
            }
        }
        catch (NamingException ne)
        {
            throw new EJBException(ne);
        }
        catch (SQLException se)
        {
            throw new EJBException(se);
        }
        return hotCardListCDTO;
    }

    private HotCardCDTO makeHotCardCDTO(HotCardCDTO cdto, ResultSet rs)
            throws SQLException
    {
        cdto.setCardNumber(rs.getString(1));
        cdto.setSequenceNo(rs.getInt(2));
        cdto.setPrimaryAccountNo(rs.getString(3));
        cdto.setCIFName(rs.getString(4));
        cdto.setStatus(rs.getString(5));
        cdto.setIncidentCode(rs.getString(6));

        return cdto;
    }

    public void testBatch(CashCardCDTO cashCardCDTO, CosesCommonDTO commonDTO)
            throws CosesAppException
    {
        StringBuffer queryBuffer = new StringBuffer();

        queryBuffer.append("INSERT INTO BATCH_TEST");
        queryBuffer.append(" (ID1, ID2, ID3)");
        queryBuffer.append(" values(?, ?, ?)");

        Connection con = null;
        PreparedStatement psmt = null;
        String[] insertArray = {"1", "2", "3"};
        try
        {
            con = this.getConnection();
            psmt = con.prepareStatement(queryBuffer.toString());
            psmt.setString(1, insertArray[0]);
            psmt.setString(2, insertArray[1]);
            psmt.setString(3, insertArray[2]);

            Log.SDBLogger.debug("Query : " + queryBuffer.toString());

            for(int i=0; i < 10; i++)
            {
                psmt.executeUpdate();
            }
        }
        catch(NamingException ne)
        {
            throw new EJBException(ne);
        }
        catch(SQLException se)
        {
            throw new EJBException(se);
        }
        finally
        {
            releaseResource(con, psmt, null);
        }
    }

    public void updateAccumBalance(CashCardCDTO cashCardCDTO, CosesCommonDTO commonDTO)
            throws CosesAppException
    {
        StringBuffer queryBuffer = new StringBuffer();

        queryBuffer.append("update cash_card set DAILY_ACCUM_AMOUNT =")
                .append(" (select FLOAT_VALUE1 from system_parameter")
                .append(" where system = 'CMS' and kind = 'CARDACCUM'),")
                .append(" DAILY_TRF_ACCUM_AMOUNT =")
                .append(" (select FLOAT_VALUE2 from system_parameter")
                .append(" where system = 'CMS' and kind = 'CARDACCUM'),")
                .append(" DAILY_ACCUM_RESET_DATE =")
                .append(" (select DATE_VALUE1 from system_parameter")
                .append(" where system = 'COR' and kind = 'BIZDATE')")
                .append(" where status = ?")
                .append(" and branch_code = ?");

        Connection con = null;
        PreparedStatement psmt = null;

        try
        {
            con = this.getConnection();
            psmt = con.prepareStatement(queryBuffer.toString());
            psmt.setString(1, CashCardConstants.NORMAL_STATUS);
            psmt.setString(2, commonDTO.getBranchCode());

            Log.SDBLogger.debug("Query : " + queryBuffer.toString());

            psmt.executeUpdate();

            Log.SDBLogger.debug("InitialDailyAccumBalanceRuleProcessor end!!!");
        }
        catch(NamingException ne)
        {
            throw new EJBException(ne);
        }
        catch(SQLException se)
        {
            throw new EJBException(se);
        }
        finally
        {
            releaseResource(con, psmt, null);
        }
    }

    public HotCardListCDTO queryForHotCardList(HotCardCDTO hotCardCDTO, CosesCommonDTO
            commonDTO) throws CosesAppException
    {
        StringBuffer queryBuffer = new StringBuffer();

        queryBuffer.append("SELECT ");
        queryBuffer.append(" CARD_NUMBER,");
        queryBuffer.append(" SEQUENCE_NO,");
        queryBuffer.append(" PRIMARY_ACCOUNT_NO,");
        queryBuffer.append(" CIF_NO,");
        queryBuffer.append(" CIF_NAME,");
        //queryBuffer.append(" CARD_HOLDER_NAME,");
        queryBuffer.append(" STATUS,");
        queryBuffer.append(" INCIDENT_CODE,");
        queryBuffer.append(" REGISTER_DATE,");
        queryBuffer.append(" REGISTER_TIME,");
        queryBuffer.append(" REGISTER_BY,");
        //queryBuffer.append(" EFFECTIVE_DATE,");
        //queryBuffer.append(" EXPIRY_DATE,");
        queryBuffer.append(" RELEASED_DATE,");
        queryBuffer.append(" RELEASED_TIME,");
        queryBuffer.append(" RELEASED_BY,");
        queryBuffer.append(" REMARK");
        queryBuffer.append(" FROM");
        queryBuffer.append(" HOT_CARD");
        queryBuffer.append(" WHERE");
        queryBuffer.append(" CARD_NUMBER = ?");


        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        HotCardListCDTO hotCardListCDTO = new HotCardListCDTO();

        try
        {
            con = this.getConnection();
            psmt = con.prepareStatement(queryBuffer.toString());
            psmt.setString(1, hotCardCDTO.getCardNumber());

            Log.SDBLogger.debug("Query : " + queryBuffer.toString());

            rs = psmt.executeQuery();

            while(rs.next())
            {
                hotCardCDTO.setCardNumber(rs.getString("CARD_NUMBER"));
                hotCardCDTO.setCIFNo(rs.getString("CIF_NO"));
                hotCardCDTO.setCIFName(rs.getString("CIF_NAME"));
                hotCardCDTO.setPrimaryAccountNo(rs.getString("PRIMARY_ACCOUNT_NO"));
                //hotCardCDTO.setCardHolderName(rs.getString("CARD_HOLDER_NAME"));
                hotCardCDTO.setReleasedBy(rs.getString("RELEASED_BY"));
                hotCardCDTO.setReleasedDate(rs.getString("RELEASED_DATE"));
                hotCardCDTO.setReleasedTime(rs.getString("RELEASED_TIME"));
                hotCardCDTO.setStatus(rs.getString("STATUS"));
                //hotCardCDTO.setEffectiveDate(rs.getString("EFFECTIVE_DATE"));
                //hotCardCDTO.setExpiryDate(rs.getString("EXPIRY_DATE"));
                hotCardCDTO.setIncidentCode(rs.getString("INCIDENT_CODE"));
                hotCardCDTO.setRegisterBy(rs.getString("REGISTER_BY"));
                hotCardCDTO.setRegisterDate(rs.getString("REGISTER_DATE"));
                hotCardCDTO.setRegisterTime(rs.getString("REGISTER_TIME"));
                hotCardCDTO.setRemark(rs.getString("REMARK"));
                hotCardCDTO.setSequenceNo(rs.getInt("SEQUENCE_NO"));

                hotCardListCDTO.add(hotCardCDTO);
            }
        }
        catch(NamingException ne)
        {
            throw new EJBException(ne);
        }
        catch(SQLException se)
        {
            throw new EJBException(se);
        }
        finally
        {
            releaseResource(con, psmt, rs);
        }
        return hotCardListCDTO;
    }

    private HotCardPageItemCDTO makeHotCardPageItem(HotCardPageItemCDTO
            pageItemCDTO, ResultSet rs) throws CosesAppException
    {
        try
        {
            pageItemCDTO.setCardNumber(rs.getString(1));
            pageItemCDTO.setPrimaryAccountNo(rs.getString(2));
            pageItemCDTO.setCIFName(rs.getString(3));
            pageItemCDTO.setIncidentCode(rs.getString(4));
            pageItemCDTO.setStatus(rs.getString(5));
            pageItemCDTO.setRegisterDate(rs.getString(6));
            pageItemCDTO.setReleasedDate(rs.getString(7));

            Log.SDBLogger.debug("PageItemCDTO : " + pageItemCDTO.toString());
        }
        catch (SQLException ex)
        {
            throw new EJBException(ex);
        }
        return pageItemCDTO;
    }

    private CashCardPageItemCDTO makeCashCardPageItem(CashCardPageItemCDTO
            pageItemCDTO, ResultSet rs) throws CosesAppException
    {
        try
        {
            pageItemCDTO.setCardNumber(rs.getString(1));
            pageItemCDTO.setPrimaryAccountNo(rs.getString(2));
            pageItemCDTO.setCIFName(rs.getString(3));
            pageItemCDTO.setCurrency(rs.getString(4));
            pageItemCDTO.setDailyAccumAmount(rs.getBigDecimal(5));
            pageItemCDTO.setStatus(rs.getString(6));
            pageItemCDTO.setIncidentCode(rs.getString(7));
            pageItemCDTO.setEffectiveDate(rs.getString(8));
            pageItemCDTO.setExpiryDate(rs.getString(9));
            pageItemCDTO.setRegisterDate(rs.getString(10));
            pageItemCDTO.setIssueDate(rs.getString(11));

            Log.SDBLogger.debug("PageItemCDTO : " + pageItemCDTO.toString());
        }
        catch (SQLException ex)
        {
            throw new EJBException(ex);
        }
        return pageItemCDTO;
    }

    private CashCardPageItemCDTO makeCashCardPageItemForInvalid(CashCardPageItemCDTO
            pageItemCDTO, ResultSet rs) throws CosesAppException
    {
        try
        {
            pageItemCDTO.setCardNumber(rs.getString(1));
            pageItemCDTO.setPrimaryAccountNo(rs.getString(2));
            pageItemCDTO.setCIFName(rs.getString(3));
            pageItemCDTO.setCurrency(rs.getString(4));
            pageItemCDTO.setDailyAccumAmount(rs.getBigDecimal(5));
            pageItemCDTO.setStatus(rs.getString(6));
            pageItemCDTO.setIncidentCode(rs.getString(7));
            pageItemCDTO.setEffectiveDate(rs.getString(8));
            pageItemCDTO.setExpiryDate(rs.getString(9));
            pageItemCDTO.setRegisterDate(rs.getString(10));
            pageItemCDTO.setIssueDate(rs.getString(11));
            pageItemCDTO.setInvalidAttemptCnt(rs.getInt(12));

            Log.SDBLogger.debug("PageItemCDTO : " + pageItemCDTO.toString());
        }
        catch (SQLException ex)
        {
            throw new EJBException(ex);
        }
        return pageItemCDTO;
    }

    private int getRecordCountForHotCard(HotCardQueryConditionCDTO conditionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = null;
        int totalLineCount = 1;

        StringBuffer queryBuffer = new StringBuffer();

        queryBuffer.append("SELECT count(*) FROM ");
        queryBuffer.append(conditionCDTO.getTableName());
        if(!"".equals(conditionCDTO.getPrimaryAccountNo()))
        {
            queryBuffer.append(" WHERE ");
            queryBuffer.append(" PRIMARY_ACCOUNT_NO = '" + conditionCDTO.getPrimaryAccountNo() + "'");
        }

        try
        {
            con = this.getConnection();

            query = queryBuffer.toString();

            //get PreparedStatement
            ps = con.prepareStatement(query);

            Log.SDBLogger.debug("Count Query : " + query);

            rs = ps.executeQuery();

            //get totalLineCount
            rs.next();
            totalLineCount = rs.getInt(1);

        }
        catch(NamingException ne)
        {
            throw new EJBException();
        }
        catch(SQLException sqle)
        {
            //throw new CosesAppException(new CosesExceptionDetail("no data"));
            throw new EJBException(sqle);
        }
        finally
        {
            releaseResource(con, ps, rs);
        } //end try-catch-finally
        return totalLineCount;
    }

    private int getRecordCount(CashCardConditionCDTO conditionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = null;
        int totalLineCount = 1;

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("ConditionCDTO for query CashCard : "
                                + conditionCDTO.toString());
        }

        StringBuffer queryBuffer = new StringBuffer();

        queryBuffer.append("SELECT count(*) FROM ");
        queryBuffer.append(conditionCDTO.getTableName());
        queryBuffer.append(" WHERE ");
        queryBuffer.append(" BANK_CODE = ?");
        queryBuffer.append(" AND BRANCH_CODE = ?");
        if(!"".equals(conditionCDTO.getCIFNo()))
        {
            queryBuffer.append(" AND CIF_NO = '" + conditionCDTO.getCIFNo() + "'");
        }
        else if(!"".equals(conditionCDTO.getCIFName()))
        {
            queryBuffer.append(" AND upper(" + conditionCDTO.getTableName() +
                         ".CIF_NAME) LIKE '%" + conditionCDTO.getCIFName().toUpperCase() + "%' ");
        }
        else if(!"".equals(conditionCDTO.getPrimaryAccountNo()))
        {
            queryBuffer.append(" AND PRIMARY_ACCOUNT_NO = '" + conditionCDTO.getPrimaryAccountNo() + "'");
        }
        else if(!"".equals(conditionCDTO.getStatus()))
        {
            queryBuffer.append(" AND STATUS = '" + conditionCDTO.getStatus() + "'");
        }

        if(!"".equals(conditionCDTO.getFromDate()))
        {
            queryBuffer.append(" AND REGISTER_DATE > '" + conditionCDTO.getFromDate() + "'");
        }

        if(!"".equals(conditionCDTO.getToDate()))
        {
            queryBuffer.append(" AND REGISTER_DATE <= '" + conditionCDTO.getToDate() + "'");
        }

        try
        {
            con = this.getConnection();

            query = queryBuffer.toString();

            //get PreparedStatement
            ps = con.prepareStatement(query);
            ps.setString(1, commonDTO.getBankCode());
            ps.setString(2, commonDTO.getBranchCode());

            Log.SDBLogger.debug("Count Query : " + query);

            rs = ps.executeQuery();

            //get totalLineCount
            rs.next();
            totalLineCount = rs.getInt(1);

        }
        catch(NamingException ne)
        {
            throw new EJBException();
        }
        catch(SQLException sqle)
        {
            //throw new CosesAppException(new CosesExceptionDetail("no data"));
            throw new EJBException(sqle);
        }
        finally
        {
            releaseResource(con, ps, rs);
        } //end try-catch-finally
        return totalLineCount;
    }

    private int getRecordCountForInvalidAttemptCard(CashCardConditionCDTO conditionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = null;
        int totalLineCount = 1;

        StringBuffer queryBuffer = new StringBuffer();

        queryBuffer.append("SELECT count(*) FROM ");
        queryBuffer.append(conditionCDTO.getTableName());
        queryBuffer.append(" WHERE ");
        queryBuffer.append(" BANK_CODE = ?");
        queryBuffer.append(" AND BRANCH_CODE = ?");
        queryBuffer.append(" AND MIS_SEND_DATE = ?");
        queryBuffer.append(" AND INVALID_ATTEMPT_CNT > 3");

        try
        {
            con = this.getConnection();

            query = queryBuffer.toString();

            //get PreparedStatement
            ps = con.prepareStatement(query);
            ps.setString(1, commonDTO.getBankCode());
            ps.setString(2, commonDTO.getBranchCode());
            ps.setString(3, commonDTO.getBusinessDate());

            Log.SDBLogger.debug("Count Query : " + query);

            rs = ps.executeQuery();

            //get totalLineCount
            rs.next();
            totalLineCount = rs.getInt(1);

        }
        catch(NamingException ne)
        {
            throw new EJBException();
        }
        catch(SQLException sqle)
        {
            //throw new CosesAppException(new CosesExceptionDetail("no data"));
            throw new EJBException(sqle);
        }
        finally
        {
            releaseResource(con, ps, rs);
        } //end try-catch-finally
        return totalLineCount;
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
                                 ResultSet rs) throws CosesAppException{

        try{
            if(rs != null){ this.release(rs);}
            if(ps != null){ this.release(ps);}
            if(con != null){ this.release(con);}
        }catch(SQLException sqle){
            //throw new CosesAppException(new CosesExceptionDetail("sqlException"));
            throw new EJBException(sqle);
        }

    }//end method
}