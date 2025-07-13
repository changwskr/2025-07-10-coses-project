package com.skcc.oversea.cashCard.business.facade.dao;

import com.skcc.oversea.framework.business.dao.IDAO;
import com.skcc.oversea.framework.exception.CosesAppException;
import com.skcc.oversea.framework.transfer.CosesCommonDTO;

import com.skcc.oversea.cashCard.transfer.*;

public interface ICashCardDAO extends IDAO
{
    public int getLastSequenceNoForRegisterCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public CashCardCDTO queryForCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public CashCardPageCDTO listForCashCardNumber(CashCardConditionCDTO conditionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public CashCardPageCDTO listForCashCard(CashCardConditionCDTO conditionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public HotCardListCDTO queryForHotCardList(HotCardCDTO hotCardCDTO, CosesCommonDTO
            commonDTO) throws CosesAppException;

    public int getLastSequenceNoForRegisterHotCard(HotCardCDTO hotCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public HotCardPageCDTO listForHotCard(HotCardQueryConditionCDTO conditionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public HotCardListCDTO selectHotCardForRegister(HotCardCDTO hotCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public CashCardPageCDTO listInvalidAttemptCard(CashCardConditionCDTO conditionCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public void testBatch(CashCardCDTO cashCardCDTO, CosesCommonDTO commonDTO)
            throws CosesAppException;

    public void updateAccumBalance(CashCardCDTO cashCardCDTO, CosesCommonDTO commonDTO)
            throws CosesAppException;
}

