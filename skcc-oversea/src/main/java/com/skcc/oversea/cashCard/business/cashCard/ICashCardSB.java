package com.skcc.oversea.cashCard.business.cashCard;

import com.skcc.oversea.framework.transfer.CosesCommonDTO;
import com.skcc.oversea.framework.exception.CosesAppException;

import com.skcc.oversea.cashCard.business.cashCard.model.*;

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

