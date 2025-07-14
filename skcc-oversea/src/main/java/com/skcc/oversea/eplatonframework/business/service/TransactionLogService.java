package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;
import com.skcc.oversea.eplatonframework.business.entity.TransactionLog;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Transaction Log Service Interface for SKCC Oversea
 * 
 * Defines transaction log business operations
 * using Spring Boot and modern Java patterns.
 */
public interface TransactionLogService {

    /**
     * Get transaction log
     */
    EPlatonEvent getTransactionLog(EPlatonEvent event);

    /**
     * Create transaction log
     */
    EPlatonEvent createTransactionLog(EPlatonEvent event);

    /**
     * Update transaction log
     */
    EPlatonEvent updateTransactionLog(EPlatonEvent event);

    /**
     * Delete transaction log
     */
    EPlatonEvent deleteTransactionLog(EPlatonEvent event);

    /**
     * Search transaction logs
     */
    EPlatonEvent searchTransactionLogs(EPlatonEvent event);

    // Additional methods for controller
    List<TransactionLog> getAllTransactionLogs();

    TransactionLog getTransactionLogById(Long id);

    TransactionLog getTransactionLogByTransactionId(String transactionId);

    List<TransactionLog> getTransactionLogsByUserId(String userId);

    List<TransactionLog> getTransactionLogsBySystemName(String systemName);

    List<TransactionLog> getTransactionLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<TransactionLog> getTransactionLogsByBusinessDate(String businessDate);

    List<TransactionLog> getTransactionLogsByErrorCode(String errorCode);

    List<TransactionLog> getTransactionLogsByChannelType(String channelType);

    List<TransactionLog> getTransactionLogsByBankAndBranch(String bankCode, String branchCode);

    List<TransactionLog> getSlowTransactions(Long threshold);

    Long getTransactionCountBySystemAndDateRange(String systemName, LocalDateTime startDate, LocalDateTime endDate);

    List<TransactionLog> getLatestTransactionsByUserId(String userId);
}