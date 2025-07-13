package com.skcc.oversea.eplatonframework.business.repository;

import com.skcc.oversea.eplatonframework.business.entity.CashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Cash Card Repository
 */
@Repository
public interface CashCardRepository extends JpaRepository<CashCard, Long> {

    /**
     * Find by card number
     */
    Optional<CashCard> findByCardNo(String cardNo);

    /**
     * Find by account number
     */
    List<CashCard> findByAccountNo(String accountNo);

    /**
     * Find by customer ID
     */
    List<CashCard> findByCustomerId(String customerId);

    /**
     * Find by card status
     */
    List<CashCard> findByCardStatus(String cardStatus);

    /**
     * Find by card type
     */
    List<CashCard> findByCardType(String cardType);

    /**
     * Find by expiry date range
     */
    @Query("SELECT c FROM CashCard c WHERE c.expiryDate BETWEEN :startDate AND :endDate")
    List<CashCard> findByExpiryDateRange(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Find expired cards
     */
    @Query("SELECT c FROM CashCard c WHERE c.expiryDate < :currentDate")
    List<CashCard> findExpiredCards(@Param("currentDate") LocalDate currentDate);

    /**
     * Find by balance range
     */
    @Query("SELECT c FROM CashCard c WHERE c.currentBalance BETWEEN :minBalance AND :maxBalance")
    List<CashCard> findByBalanceRange(@Param("minBalance") BigDecimal minBalance,
            @Param("maxBalance") BigDecimal maxBalance);

    /**
     * Find by currency code
     */
    List<CashCard> findByCurrencyCode(String currencyCode);

    /**
     * Find by card holder name (partial match)
     */
    @Query("SELECT c FROM CashCard c WHERE c.cardHolderName LIKE %:name%")
    List<CashCard> findByCardHolderNameContaining(@Param("name") String name);

    /**
     * Find active cards by customer ID
     */
    @Query("SELECT c FROM CashCard c WHERE c.customerId = :customerId AND c.cardStatus = 'AC'")
    List<CashCard> findActiveCardsByCustomerId(@Param("customerId") String customerId);

    /**
     * Count cards by status
     */
    long countByCardStatus(String cardStatus);

    /**
     * Find cards expiring soon (within days)
     */
    @Query("SELECT c FROM CashCard c WHERE c.expiryDate BETWEEN :currentDate AND :expiryDate")
    List<CashCard> findCardsExpiringSoon(@Param("currentDate") LocalDate currentDate,
            @Param("expiryDate") LocalDate expiryDate);

    /**
     * Find by daily limit range
     */
    @Query("SELECT c FROM CashCard c WHERE c.dailyLimit BETWEEN :minLimit AND :maxLimit")
    List<CashCard> findByDailyLimitRange(@Param("minLimit") BigDecimal minLimit,
            @Param("maxLimit") BigDecimal maxLimit);
}