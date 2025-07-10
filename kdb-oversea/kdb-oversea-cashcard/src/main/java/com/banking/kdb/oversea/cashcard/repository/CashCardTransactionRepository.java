package com.banking.kdb.oversea.cashcard.repository;

import com.banking.kdb.oversea.cashcard.model.CashCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * CashCard Transaction Repository for KDB Oversea
 * 
 * Provides data access methods for CashCardTransaction entity.
 */
@Repository
public interface CashCardTransactionRepository extends JpaRepository<CashCardTransaction, Long> {

    /**
     * Find cash card transaction by transaction ID
     */
    CashCardTransaction findByTransactionId(String transactionId);

    /**
     * Find cash card transactions by card number
     */
    List<CashCardTransaction> findByCardNumber(String cardNumber);

    /**
     * Find cash card transactions by card number ordered by transaction date
     */
    List<CashCardTransaction> findByCardNumberOrderByTransactionDateDesc(String cardNumber);

    /**
     * Find cash card transactions by transaction type
     */
    List<CashCardTransaction> findByTransactionType(String transactionType);

    /**
     * Find cash card transactions by status
     */
    List<CashCardTransaction> findByStatus(String status);

    /**
     * Find cash card transactions by card number and transaction type
     */
    List<CashCardTransaction> findByCardNumberAndTransactionType(String cardNumber, String transactionType);

    /**
     * Find cash card transactions by card number and status
     */
    List<CashCardTransaction> findByCardNumberAndStatus(String cardNumber, String status);

    /**
     * Check if transaction exists by transaction ID
     */
    boolean existsByTransactionId(String transactionId);

    /**
     * Count transactions by card number
     */
    long countByCardNumber(String cardNumber);

    /**
     * Count transactions by transaction type
     */
    long countByTransactionType(String transactionType);

    /**
     * Count transactions by status
     */
    long countByStatus(String status);

    /**
     * Count transactions by card number and transaction type
     */
    long countByCardNumberAndTransactionType(String cardNumber, String transactionType);

    /**
     * Get daily transaction total for a card
     */
    @Query("SELECT COALESCE(SUM(cct.amount), 0) FROM CashCardTransaction cct " +
            "WHERE cct.cardNumber = :cardNumber " +
            "AND cct.transactionDate >= :startOfDay " +
            "AND cct.transactionDate < :endOfDay " +
            "AND cct.status = 'COMPLETED'")
    BigDecimal getDailyTransactionTotal(@Param("cardNumber") String cardNumber,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    /**
     * Get monthly transaction total for a card
     */
    @Query("SELECT COALESCE(SUM(cct.amount), 0) FROM CashCardTransaction cct " +
            "WHERE cct.cardNumber = :cardNumber " +
            "AND cct.transactionDate >= :startOfMonth " +
            "AND cct.transactionDate < :endOfMonth " +
            "AND cct.status = 'COMPLETED'")
    BigDecimal getMonthlyTransactionTotal(@Param("cardNumber") String cardNumber,
            @Param("startOfMonth") LocalDateTime startOfMonth,
            @Param("endOfMonth") LocalDateTime endOfMonth);

    /**
     * Find cash card transactions by date range
     */
    @Query("SELECT cct FROM CashCardTransaction cct WHERE cct.transactionDate BETWEEN :startDate AND :endDate")
    List<CashCardTransaction> findByDateRange(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find cash card transactions by card number and date range
     */
    @Query("SELECT cct FROM CashCardTransaction cct WHERE cct.cardNumber = :cardNumber AND cct.transactionDate BETWEEN :startDate AND :endDate")
    List<CashCardTransaction> findByCardNumberAndDateRange(@Param("cardNumber") String cardNumber,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find cash card transactions by transaction type and date range
     */
    @Query("SELECT cct FROM CashCardTransaction cct WHERE cct.transactionType = :transactionType AND cct.transactionDate BETWEEN :startDate AND :endDate")
    List<CashCardTransaction> findByTransactionTypeAndDateRange(@Param("transactionType") String transactionType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find completed transactions
     */
    @Query("SELECT cct FROM CashCardTransaction cct WHERE cct.status = 'COMPLETED'")
    List<CashCardTransaction> findCompletedTransactions();

    /**
     * Find failed transactions
     */
    @Query("SELECT cct FROM CashCardTransaction cct WHERE cct.status = 'FAILED'")
    List<CashCardTransaction> findFailedTransactions();

    /**
     * Find pending transactions
     */
    @Query("SELECT cct FROM CashCardTransaction cct WHERE cct.status = 'PENDING'")
    List<CashCardTransaction> findPendingTransactions();

    /**
     * Find cash card transactions by search criteria
     */
    @Query("SELECT cct FROM CashCardTransaction cct WHERE " +
            "(:cardNumber IS NULL OR cct.cardNumber = :cardNumber) AND " +
            "(:transactionType IS NULL OR cct.transactionType = :transactionType) AND " +
            "(:status IS NULL OR cct.status = :status)")
    List<CashCardTransaction> findBySearchCriteria(@Param("cardNumber") String cardNumber,
            @Param("transactionType") String transactionType,
            @Param("status") String status);

    /**
     * Get transaction summary by card number
     */
    @Query("SELECT cct.transactionType, COUNT(cct) as count, cct.status FROM CashCardTransaction cct " +
            "WHERE cct.cardNumber = :cardNumber GROUP BY cct.transactionType, cct.status")
    List<Object[]> getTransactionSummaryByCardNumber(@Param("cardNumber") String cardNumber);

    /**
     * Get transaction count by type for a card
     */
    @Query("SELECT COUNT(cct) FROM CashCardTransaction cct WHERE cct.cardNumber = :cardNumber AND cct.transactionType = :transactionType")
    long countByCardNumberAndTransactionType(@Param("cardNumber") String cardNumber,
            @Param("transactionType") String transactionType);

    /**
     * Find transactions by merchant ID
     */
    List<CashCardTransaction> findByMerchantId(String merchantId);

    /**
     * Find transactions by terminal ID
     */
    List<CashCardTransaction> findByTerminalId(String terminalId);

    /**
     * Find transactions by authorization code
     */
    List<CashCardTransaction> findByAuthorizationCode(String authorizationCode);

    /**
     * Get daily transaction total (simplified version for service use)
     */
    @Query("SELECT COALESCE(SUM(cct.amount), 0) FROM CashCardTransaction cct " +
            "WHERE cct.cardNumber = :cardNumber " +
            "AND DATE(cct.transactionDate) = CURRENT_DATE " +
            "AND cct.status = 'COMPLETED'")
    BigDecimal getDailyTransactionTotal(@Param("cardNumber") String cardNumber);

    /**
     * Get monthly transaction total (simplified version for service use)
     */
    @Query("SELECT COALESCE(SUM(cct.amount), 0) FROM CashCardTransaction cct " +
            "WHERE cct.cardNumber = :cardNumber " +
            "AND YEAR(cct.transactionDate) = YEAR(CURRENT_DATE) " +
            "AND MONTH(cct.transactionDate) = MONTH(CURRENT_DATE) " +
            "AND cct.status = 'COMPLETED'")
    BigDecimal getMonthlyTransactionTotal(@Param("cardNumber") String cardNumber);
}