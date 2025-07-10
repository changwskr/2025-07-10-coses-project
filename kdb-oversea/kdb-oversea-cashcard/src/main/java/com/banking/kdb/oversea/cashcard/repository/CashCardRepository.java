package com.banking.kdb.oversea.cashcard.repository;

import com.banking.kdb.oversea.cashcard.model.CashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * CashCard Repository for KDB Oversea
 * 
 * Provides data access methods for CashCard entity.
 */
@Repository
public interface CashCardRepository extends JpaRepository<CashCard, Long> {

    /**
     * Find cash card by card number
     */
    Optional<CashCard> findByCardNumber(String cardNumber);

    /**
     * Find cash cards by customer ID
     */
    List<CashCard> findByCustomerId(String customerId);

    /**
     * Find cash cards by customer ID and status
     */
    List<CashCard> findByCustomerIdAndCardStatus(String customerId, String cardStatus);

    /**
     * Find cash cards by card type
     */
    List<CashCard> findByCardType(String cardType);

    /**
     * Find cash cards by card status
     */
    List<CashCard> findByCardStatus(String cardStatus);

    /**
     * Find cash cards by currency
     */
    List<CashCard> findByCurrency(String currency);

    /**
     * Check if cash card exists by card number
     */
    boolean existsByCardNumber(String cardNumber);

    /**
     * Count cash cards by customer ID
     */
    long countByCustomerId(String customerId);

    /**
     * Count cash cards by card type
     */
    long countByCardType(String cardType);

    /**
     * Count cash cards by card status
     */
    long countByCardStatus(String cardStatus);

    /**
     * Count cash cards by customer ID and card status
     */
    long countByCustomerIdAndCardStatus(String customerId, String cardStatus);

    /**
     * Find active cash cards
     */
    @Query("SELECT cc FROM CashCard cc WHERE cc.cardStatus = 'ACTIVE'")
    List<CashCard> findActiveCards();

    /**
     * Find cash cards expiring soon
     */
    @Query("SELECT cc FROM CashCard cc WHERE cc.expiryDate BETWEEN :startDate AND :endDate")
    List<CashCard> findCardsExpiringBetween(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Find cash cards expiring within specified days
     */
    @Query("SELECT cc FROM CashCard cc WHERE cc.expiryDate <= :expiryDate")
    List<CashCard> findCardsExpiringBy(@Param("expiryDate") LocalDate expiryDate);

    /**
     * Find cash cards by search criteria
     */
    @Query("SELECT cc FROM CashCard cc WHERE " +
            "(:customerId IS NULL OR cc.customerId = :customerId) AND " +
            "(:cardType IS NULL OR cc.cardType = :cardType) AND " +
            "(:cardStatus IS NULL OR cc.cardStatus = :cardStatus) AND " +
            "(:currency IS NULL OR cc.currency = :currency)")
    List<CashCard> findBySearchCriteria(@Param("customerId") String customerId,
            @Param("cardType") String cardType,
            @Param("cardStatus") String cardStatus,
            @Param("currency") String currency);

    /**
     * Get card summary by customer ID
     */
    @Query("SELECT cc.cardType, COUNT(cc) as count, cc.cardStatus FROM CashCard cc " +
            "WHERE cc.customerId = :customerId GROUP BY cc.cardType, cc.cardStatus")
    List<Object[]> getCardSummaryByCustomerId(@Param("customerId") String customerId);

    /**
     * Get card count by type for a customer
     */
    @Query("SELECT COUNT(cc) FROM CashCard cc WHERE cc.customerId = :customerId AND cc.cardType = :cardType")
    long countByCustomerIdAndCardType(@Param("customerId") String customerId,
            @Param("cardType") String cardType);

    /**
     * Find cash cards with high daily limits
     */
    @Query("SELECT cc FROM CashCard cc WHERE cc.dailyLimit > :limit")
    List<CashCard> findCardsWithHighDailyLimit(@Param("limit") java.math.BigDecimal limit);

    /**
     * Find cash cards with high monthly limits
     */
    @Query("SELECT cc FROM CashCard cc WHERE cc.monthlyLimit > :limit")
    List<CashCard> findCardsWithHighMonthlyLimit(@Param("limit") java.math.BigDecimal limit);
}