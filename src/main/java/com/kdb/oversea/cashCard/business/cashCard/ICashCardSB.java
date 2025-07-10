package com.kdb.oversea.cashCard.business.cashCard;

import com.chb.coses.cosesFramework.transfer.CosesCommonDTO;
import com.chb.coses.cosesFramework.exception.CosesAppException;

import com.kdb.oversea.cashCard.business.cashCard.model.*;

public interface ICashCardSB
{
    public CashCardDDTO getCashCardInfo(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public CashCardDDTO findCashCardInfoByCardNo(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public CashCardDDTO makeCashCard(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public CashCardDDTO setCashCard(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public HotCardDDTO getHotCardInfo(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public HotCardDDTO makeHotCard(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public HotCardDDTO releaseHotCard(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;
}