package com.ims.eplaton.cashCard.business.facade;

import java.rmi.RemoteException;

import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.transfer.CosesCommonDTO;
import com.chb.coses.cosesFramework.transfer.BatchJobProcessorResultDTO;

import com.ims.eplaton.cashCard.transfer.*;

import com.ims.eplaton.eplatonFWK.transfer.*;
import com.ims.eplaton.foundation.helper.logej.*;

public interface ICashCardManagementSB
{

    public EPlatonEvent queryForRegisterCashCard(EPlatonEvent eplatonevent)
         throws CosesAppException, RemoteException;

    public CashCardCDTO queryForRegisterCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public CashCardCDTO registerCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public CashCardCDTO modifyCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public CashCardCDTO inquiryCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

//    public CashCardCDTO inquiryCashCardForTransfer(CashCardCDTO cashCardCDTO,
//            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public CashCardCDTO cancelCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public HotCardListCDTO registerHotCard(HotCardListCDTO hotCardListCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public HotCardListCDTO releasedHotCard(HotCardListCDTO hotCardListCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public HotCardListCDTO inquiryHotCard(HotCardCDTO hotCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public void validateAccount(ValidateTransactionCDTO validateTransactionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public CashCardCDTO updateInvaildAttemptCount(CashCardCDTO cashCardCDTO, CosesCommonDTO commonDTO)
            throws CosesAppException, RemoteException;

    public void validateCard(ValidateTransactionCDTO validateTransactionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public boolean validateDailyLimitAmount(ValidateTransactionCDTO validateTransactionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public boolean validateDailyAccumAmount(ValidateTransactionCDTO validateTransactionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public boolean validateDailyTrfLimitAmount(ValidateTransactionCDTO validateTransactionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public boolean validateDailyTrfAccumAmount(ValidateTransactionCDTO validateTransactionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public CashCardPageCDTO listCashCard(CashCardConditionCDTO queryCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public CashCardPageCDTO listCashCardNumber(CashCardConditionCDTO queryCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public HotCardPageCDTO listHotCard(HotCardQueryConditionCDTO queryCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public CashCardPageCDTO listInvalidAttemptCard(CashCardConditionCDTO conditionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public BatchJobProcessorResultDTO processBatchTest(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public BatchJobProcessorResultDTO initialDailyAccumBalance(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;
}