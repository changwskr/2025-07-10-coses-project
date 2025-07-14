package com.skcc.oversea.cashCard.repository;

import com.skcc.oversea.cashCard.business.cashCard.entity.HotCard;
import com.skcc.oversea.cashCard.business.cashCard.entity.HotCardPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotCardRepository extends JpaRepository<HotCard, HotCardPK> {

    /**
     * Find hot card by primary key
     */
    Optional<HotCard> findByPrimaryKey(HotCardPK primaryKey);

    /**
     * Find hot cards by card number
     */
    @Query("SELECT h FROM HotCard h WHERE h.cardNumber = :cardNumber")
    List<HotCard> findByCardNumber(@Param("cardNumber") String cardNumber);

    /**
     * Find hot cards by bank code
     */
    @Query("SELECT h FROM HotCard h WHERE h.bankCode = :bankCode")
    List<HotCard> findByBankCode(@Param("bankCode") String bankCode);

    /**
     * Find hot cards by status
     */
    @Query("SELECT h FROM HotCard h WHERE h.status = :status")
    List<HotCard> findByStatus(@Param("status") String status);
}