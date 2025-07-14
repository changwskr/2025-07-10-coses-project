package com.skcc.oversea.cashCard.business.facade;

import java.util.List;
import com.skcc.oversea.framework.transfer.BatchJobProcessorResultDTO;

public interface ICashCardManagementSB {

        List<Object> getAllCashCards();

        Object getCashCardById(String cardId);

        Object createCashCard(Object cashCard);

        void deleteCashCard(String cardId);

        boolean existsCashCard(String cardId);

        BatchJobProcessorResultDTO processBatchJob(String jobId);

        BatchJobProcessorResultDTO getBatchJobStatus(String jobId);

}
