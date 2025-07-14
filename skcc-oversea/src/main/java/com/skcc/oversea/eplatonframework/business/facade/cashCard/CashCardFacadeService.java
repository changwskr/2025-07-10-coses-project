package com.skcc.oversea.eplatonframework.business.facade.cashCard;

import com.skcc.oversea.cashCard.business.cashCard.entity.CashCard;
import com.skcc.oversea.eplatonframework.business.service.CashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashCardFacadeService {

    @Autowired
    private CashCardService cashCardService;

    public List<CashCard> getAllCashCards() {
        return cashCardService.findAll();
    }

    public CashCard getCashCardById(String cardId) {
        return cashCardService.findById(cardId);
    }

    public CashCard createCashCard(CashCard cashCard) {
        return cashCardService.save(cashCard);
    }

    public void deleteCashCard(String cardId) {
        cashCardService.deleteById(cardId);
    }

    public boolean existsCashCard(String cardId) {
        return cashCardService.existsById(cardId);
    }
}