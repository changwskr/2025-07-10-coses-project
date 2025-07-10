package com.ims.eplaton.cashCard.business.cashCardRule;

import com.chb.coses.cosesFramework.transfer.CosesCommonDTO;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.transfer.ModifyDTO;

import com.ims.eplaton.cashCard.business.cashCard.model.*;

public interface ICashCardRuleSB
{
    public CashCardDDTO getCashCardInfo(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public CashCardDDTO getCashCardForRegister(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public CashCardDDTO getCashCardByCardNo(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public CashCardDDTO makeCashCard(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public CashCardDDTO setCashCard(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public CashCardDDTO modifyCashCard(ModifyDTO modifyDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public HotCardDDTO getHotCardInfo(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public HotCardDDTO registerHotCard(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;

    public HotCardDDTO releaseHotCard(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException;
}