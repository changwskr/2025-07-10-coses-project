package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.eplatonframework.business.entity.CashCard;
import java.math.BigDecimal;
import java.util.List;

public interface CashCardService {
    // Basic CRUD operations
    List<CashCard> findAll();

    CashCard findById(String cardId);

    CashCard save(CashCard cashCard);

    void deleteById(String cardId);

    boolean existsById(String cardId);

    // Controller expected methods
    List<CashCard> getAllCashCards();

    CashCard getCashCardById(Long id);

    CashCard getCashCardByCardNo(String cardNo);

    List<CashCard> getCashCardsByCustomerId(String customerId);

    CashCard createCashCard(CashCard cashCard);

    CashCard updateCashCard(CashCard cashCard);

    boolean deleteCashCard(Long id);

    List<CashCard> getCashCardsByStatus(String status);

    List<CashCard> getExpiredCashCards();

    CashCard updateCardBalance(Long id, BigDecimal newBalance);

    CashCard updateCardStatus(Long id, String status);
}