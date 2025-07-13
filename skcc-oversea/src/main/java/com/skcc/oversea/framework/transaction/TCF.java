package com.skcc.oversea.framework.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.skcc.oversea.foundation.logej.LOGEJ;
import com.skcc.oversea.foundation.constant.Constants;
import com.skcc.oversea.framework.constant.FrameworkConstants;

/**
 * Transaction Control Framework (TCF) for SKCC Oversea
 * 
 * Provides transaction control and management functionality.
 */
@Component
public class TCF {

    private static final Logger logger = LoggerFactory.getLogger(TCF.class);

    @Autowired
    private STF stf;

    @Autowired
    private ETF etf;

    @Autowired
    private BTF btf;

    /**
     * Execute transaction with TCF control
     */
    @Transactional
    public Object execute(String transactionId, String transactionType, Object request) {
        LOGEJ.startTransaction(transactionId, transactionType, "TCF_EXECUTE");

        try {
            logger.info("{} Starting TCF execution for transaction: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

            // STF: Service Transaction Framework
            Object stfResult = stf.process(transactionId, transactionType, request);
            if (stfResult == null) {
                throw new RuntimeException("STF processing failed");
            }

            // BTF: Business Transaction Framework
            Object btfResult = btf.process(transactionId, transactionType, stfResult);
            if (btfResult == null) {
                throw new RuntimeException("BTF processing failed");
            }

            // ETF: Error Transaction Framework (success path)
            Object etfResult = etf.processSuccess(transactionId, transactionType, btfResult);

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_SUCCESS, "TCF execution completed successfully");
            logger.info("{} TCF execution completed successfully for transaction: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

            return etfResult;

        } catch (Exception e) {
            LOGEJ.logError(FrameworkConstants.ERROR_CODE_TRANSACTION_ERROR,
                    "TCF execution failed: " + e.getMessage(),
                    "TCF_EXECUTE", e);

            // ETF: Error Transaction Framework (error path)
            Object errorResult = etf.processError(transactionId, transactionType, e);

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_FAILED,
                    "TCF execution failed: " + e.getMessage());

            logger.error("{} TCF execution failed for transaction: {} - Error: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId, e.getMessage(), e);

            return errorResult;
        }
    }

    /**
     * Execute read-only transaction
     */
    @Transactional(readOnly = true)
    public Object executeReadOnly(String transactionId, String transactionType, Object request) {
        LOGEJ.startTransaction(transactionId, transactionType, "TCF_READ_ONLY");

        try {
            logger.info("{} Starting TCF read-only execution for transaction: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

            // STF: Service Transaction Framework
            Object stfResult = stf.process(transactionId, transactionType, request);
            if (stfResult == null) {
                throw new RuntimeException("STF processing failed");
            }

            // BTF: Business Transaction Framework (read-only)
            Object btfResult = btf.processReadOnly(transactionId, transactionType, stfResult);
            if (btfResult == null) {
                throw new RuntimeException("BTF read-only processing failed");
            }

            // ETF: Error Transaction Framework (success path)
            Object etfResult = etf.processSuccess(transactionId, transactionType, btfResult);

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_SUCCESS,
                    "TCF read-only execution completed successfully");
            logger.info("{} TCF read-only execution completed successfully for transaction: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId);

            return etfResult;

        } catch (Exception e) {
            LOGEJ.logError(FrameworkConstants.ERROR_CODE_TRANSACTION_ERROR,
                    "TCF read-only execution failed: " + e.getMessage(),
                    "TCF_READ_ONLY", e);

            // ETF: Error Transaction Framework (error path)
            Object errorResult = etf.processError(transactionId, transactionType, e);

            LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_FAILED,
                    "TCF read-only execution failed: " + e.getMessage());

            logger.error("{} TCF read-only execution failed for transaction: {} - Error: {}",
                    FrameworkConstants.LOG_PREFIX_TRANSACTION, transactionId, e.getMessage(), e);

            return errorResult;
        }
    }

    /**
     * Get STF instance
     */
    public STF getStf() {
        return stf;
    }

    /**
     * Get ETF instance
     */
    public ETF getEtf() {
        return etf;
    }

    /**
     * Get BTF instance
     */
    public BTF getBtf() {
        return btf;
    }
}