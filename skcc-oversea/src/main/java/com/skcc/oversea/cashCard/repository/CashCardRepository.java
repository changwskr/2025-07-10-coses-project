package com.skcc.oversea.cashCard.repository;

import com.skcc.oversea.cashCard.business.cashCard.entity.CashCard;
import com.skcc.oversea.cashCard.business.cashCard.entity.CashCardPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CashCardRepository extends JpaRepository<CashCard, CashCardPK> {

    /**
     * Find cash card by primary key
     */
    Optional<CashCard> findByPrimaryKey(CashCardPK primaryKey);

    /**
     * Find cash card by card number and bank code
     */
    @Query("SELECT c FROM CashCard c WHERE c.cardNumber = :cardNumber AND c.bankCode = :bankCode")
    Optional<CashCard> findByCardNumber(@Param("bankCode") String bankCode, @Param("cardNumber") String cardNumber);

    /**
     * Create a new cash card
     */
    @Query("SELECT c FROM CashCard c WHERE c.bankType = :bankType AND c.bankCode = :bankCode " +
            "AND c.primaryAccountNo = :primaryAccountNo AND c.sequenceNo = :sequenceNo " +
            "AND c.cardNumber = :cardNumber AND c.branchCode = :branchCode AND c.type = :type")
    Optional<CashCard> findByCreateCriteria(@Param("bankType") String bankType,
            @Param("bankCode") String bankCode,
            @Param("primaryAccountNo") String primaryAccountNo,
            @Param("sequenceNo") int sequenceNo,
            @Param("cardNumber") String cardNumber,
            @Param("branchCode") String branchCode,
            @Param("type") String type);

    /**
     * Create method for cash card
     */
    default CashCard create(String bankType, String bankCode, String primaryAccountNo,
            int sequenceNo, String cardNumber, String branchCode, String type) {
        CashCard cashCard = new CashCard(sequenceNo, cardNumber, bankCode, primaryAccountNo);
        cashCard.setBankType(bankType);
        cashCard.setBranchCode(branchCode);
        cashCard.setType(type);
        return save(cashCard);
    }
}