package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;

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
}