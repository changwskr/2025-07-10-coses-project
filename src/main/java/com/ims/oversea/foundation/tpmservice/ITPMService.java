package com.ims.oversea.foundation.tpmservice;

import com.chb.coses.framework.transfer.CosesCommonDTO;
import com.chb.coses.framework.transfer.CosesEvent;

/**
 * Interface for TPM (Transaction Processing Monitor) Service
 */
public interface ITPMService {

    /**
     * Initialize TPM service
     */
    void initialize() throws Exception;

    /**
     * Start TPM service
     */
    void start() throws Exception;

    /**
     * Stop TPM service
     */
    void stop() throws Exception;

    /**
     * Process transaction
     */
    CosesCommonDTO processTransaction(CosesEvent event) throws Exception;

    /**
     * Get transaction status
     */
    CosesCommonDTO getTransactionStatus(String transactionId) throws Exception;

    /**
     * Monitor transaction
     */
    CosesCommonDTO monitorTransaction(String transactionId) throws Exception;

    /**
     * Get service statistics
     */
    CosesCommonDTO getServiceStatistics() throws Exception;

    /**
     * Check service health
     */
    boolean isHealthy() throws Exception;

    /**
     * Get service configuration
     */
    CosesCommonDTO getServiceConfiguration() throws Exception;
}