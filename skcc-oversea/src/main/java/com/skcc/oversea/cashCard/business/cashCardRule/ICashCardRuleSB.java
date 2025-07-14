package com.skcc.oversea.cashCard.business.cashCardRule;

import java.util.List;
import com.skcc.oversea.framework.transfer.CosesCommonDTO;
import com.skcc.oversea.framework.exception.CosesAppException;
import com.skcc.oversea.framework.transfer.ModifyDTO;

import com.skcc.oversea.cashCard.business.cashCard.model.*;

public interface ICashCardRuleSB {
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
