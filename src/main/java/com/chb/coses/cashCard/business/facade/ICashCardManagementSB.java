package com.chb.coses.cashCard.business.facade;

import com.chb.coses.cosesFramework.transfer.BatchJobProcessorResultDTO;
import com.chb.coses.framework.transfer.CosesCommonDTO;
import com.chb.coses.framework.transfer.CosesEvent;

/**
 * Interface for Cash Card Management Session Bean
 */
public interface ICashCardManagementSB {

    /**
     * Process cash card transaction
     */
    CosesCommonDTO processCashCardTransaction(CosesEvent event) throws Exception;

    /**
     * Get cash card information
     */
    CosesCommonDTO getCashCardInfo(CosesEvent event) throws Exception;

    /**
     * Update cash card status
     */
    CosesCommonDTO updateCashCardStatus(CosesEvent event) throws Exception;

    /**
     * Process batch job
     */
    BatchJobProcessorResultDTO processBatchJob(CosesEvent event) throws Exception;

    /**
     * Get batch job status
     */
    BatchJobProcessorResultDTO getBatchJobStatus(CosesEvent event) throws Exception;
}