package com.skcc.oversea.eplatonframework.business.repository;

import com.skcc.oversea.eplatonframework.business.entity.HotCard;
import com.skcc.oversea.eplatonframework.business.entity.HotCardPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Hot Card Repository
 */
@Repository
public interface HotCardRepository extends JpaRepository<HotCard, HotCardPK> {

        /**
         * Find hot card by primary key
         */
        Optional<HotCard> findByPrimaryKey(HotCardPK primaryKey);

        /**
         * Find by card number
         */
        Optional<HotCard> findByCardNo(String cardNo);

        /**
         * Find by account number
         */
        List<HotCard> findByAccountNo(String accountNo);

        /**
         * Find by customer ID
         */
        List<HotCard> findByCustomerId(String customerId);

        /**
         * Find by card status
         */
        List<HotCard> findByCardStatus(String cardStatus);

        /**
         * Find by card type
         */
        List<HotCard> findByCardType(String cardType);

        /**
         * Find by expiry date range
         */
        @Query("SELECT h FROM HotCard h WHERE h.expiryDate BETWEEN :startDate AND :endDate")
        List<HotCard> findByExpiryDateRange(@Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        /**
         * Find expired cards
         */
        @Query("SELECT h FROM HotCard h WHERE h.expiryDate < :currentDate")
        List<HotCard> findExpiredCards(@Param("currentDate") LocalDate currentDate);

        /**
         * Find by balance range
         */
        @Query("SELECT h FROM HotCard h WHERE h.currentBalance BETWEEN :minBalance AND :maxBalance")
        List<HotCard> findByBalanceRange(@Param("minBalance") BigDecimal minBalance,
                        @Param("maxBalance") BigDecimal maxBalance);

        /**
         * Find by currency code
         */
        List<HotCard> findByCurrencyCode(String currencyCode);

        /**
         * Find by card holder name (partial match)
         */
        @Query("SELECT h FROM HotCard h WHERE h.cardHolderName LIKE %:name%")
        List<HotCard> findByCardHolderNameContaining(@Param("name") String name);

        /**
         * Find active cards by customer ID
         */
        @Query("SELECT h FROM HotCard h WHERE h.customerId = :customerId AND h.cardStatus = 'AC'")
        List<HotCard> findActiveCardsByCustomerId(@Param("customerId") String customerId);

        /**
         * Count cards by status
         */
        long countByCardStatus(String cardStatus);

        /**
         * Find cards expiring soon (within days)
         */
        @Query("SELECT h FROM HotCard h WHERE h.expiryDate BETWEEN :currentDate AND :expiryDate")
        List<HotCard> findCardsExpiringSoon(@Param("currentDate") LocalDate currentDate,
                        @Param("expiryDate") LocalDate expiryDate);

        /**
         * Find by daily limit range
         */
        @Query("SELECT h FROM HotCard h WHERE h.dailyLimit BETWEEN :minLimit AND :maxLimit")
        List<HotCard> findByDailyLimitRange(@Param("minLimit") BigDecimal minLimit,
                        @Param("maxLimit") BigDecimal maxLimit);

        /**
         * Create a new hot card
         */
        default HotCard create(String cardNumber, int sequenceNo) {
                HotCardPK pk = new HotCardPK(sequenceNo, cardNumber);
                HotCard hotCard = new HotCard(pk);
                return save(hotCard);
        }
}