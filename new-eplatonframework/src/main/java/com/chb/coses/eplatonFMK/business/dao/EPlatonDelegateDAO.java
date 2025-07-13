package com.chb.coses.eplatonFMK.business.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.business.model.TransactionLogDDTO;
import com.chb.coses.eplatonFMK.business.helper.DTOConverter;

/**
 * EPlaton Delegate DAO 구현 클래스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@Repository
public class EPlatonDelegateDAO implements IEPlatonDelegateDAO {

    private static final Logger logger = LoggerFactory.getLogger(EPlatonDelegateDAO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void DB_INSERTinlog(EPlatonEvent event) throws DAOException {
        logger.debug("<DAO - transactionInLog-method:start>");

        try {
            logger.debug("Event: {}", event);
            DTOConverter converter = new DTOConverter();
            TransactionLogDDTO logDDTO = converter.getTransactionLogDDTO(event);

            logger.debug("Log DTO: {}", logDDTO);

            String sql = "INSERT INTO TRANSACTION_IN_LOG (TRANSACTION_ID, METHOD_NAME, SYSTEM_NAME, " +
                    "HOST_NAME, BANK_CODE, BRANCH_CODE, USER_ID, CHANNEL_TYPE, BUSINESS_DATE, " +
                    "EVENT_NO, IP_ADDRESS, REGISTER_DATE, IN_TIME) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, SYSDATE)";

            jdbcTemplate.update(sql,
                    logDDTO.getTransactionId(),
                    logDDTO.getMethodName(),
                    logDDTO.getSystemName(),
                    logDDTO.getHostName(),
                    logDDTO.getBankCode(),
                    logDDTO.getBranchCode(),
                    logDDTO.getUserId(),
                    logDDTO.getChannelType(),
                    logDDTO.getBusinessDate(),
                    logDDTO.getEventNo(),
                    logDDTO.getIPAddress());

        } catch (Exception e) {
            logger.error("Error inserting transaction in log", e);
            throw new DAOException("Error inserting transaction in log", e);
        }

        logger.debug("<DAO - transactionInLog-method:end>");
    }

    @Override
    public void DB_INSERToutlog(EPlatonEvent event) throws DAOException {
        logger.debug("Event: {}", event);

        try {
            DTOConverter converter = new DTOConverter();
            TransactionLogDDTO logDDTO = converter.getTransactionLogDDTO(event);

            String sql = "UPDATE TRANSACTION_IN_LOG SET TRANSACTION_NO = ?, RESPONSE_TIME = ?, " +
                    "ERROR_CODE = ?, OUT_TIME = SYSDATE WHERE TRANSACTION_ID = ?";

            int updatedRows = jdbcTemplate.update(sql,
                    logDDTO.getTransactionNo(),
                    logDDTO.getResponseTime(),
                    logDDTO.getErrorCode(),
                    logDDTO.getTransactionId());

            logger.debug("Updated {} rows for transaction out log", updatedRows);

            // 파일 로그 기록
            TransactionLogEntity.insertTransactionLog2File(getLogLine(logDDTO));

        } catch (Exception e) {
            logger.error("Error inserting transaction out log", e);
            throw new DAOException("Error inserting transaction out log", e);
        }
    }

    private static String getLogLine(TransactionLogDDTO logDDTO) {
        String TAB = " ";
        return logDDTO.getTransactionId() + TAB +
                logDDTO.getHostName() + TAB +
                logDDTO.getSystemName() + TAB +
                logDDTO.getMethodName() + TAB +
                logDDTO.getBankCode() + TAB +
                logDDTO.getBranchCode() + TAB +
                logDDTO.getUserId() + TAB +
                logDDTO.getChannelType() + TAB +
                logDDTO.getBusinessDate() + TAB +
                logDDTO.getRegisterDate() + TAB +
                logDDTO.getInTime() + TAB +
                logDDTO.getEventNo() + TAB +
                logDDTO.getOutTime() + TAB +
                logDDTO.getResponseTime() + TAB +
                logDDTO.getErrorCode() + TAB +
                logDDTO.getIPAddress() + "\n";
    }

    @Override
    public String queryForBusinessDate(String bankCode) {
        String sql = "SELECT BUSINESS_DATE FROM DUAL WHERE BANK_CODE = ?";

        try {
            List<String> results = jdbcTemplate.queryForList(sql, String.class, bankCode);
            if (!results.isEmpty()) {
                return results.get(0);
            }
        } catch (Exception e) {
            logger.error("Error querying business date for bank code: {}", bankCode, e);
        }

        return null;
    }

    @Override
    public boolean isLogabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void log(Object message) {
        logger.debug("{}", message);
    }

    @Override
    public void log(Object message, Throwable throwable) {
        logger.debug("{}", message, throwable);
    }
}