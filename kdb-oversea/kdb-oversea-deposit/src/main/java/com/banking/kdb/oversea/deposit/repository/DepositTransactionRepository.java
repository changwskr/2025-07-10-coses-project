package com.banking.kdb.oversea.deposit.repository;

import com.banking.kdb.oversea.deposit.model.DepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Deposit Transaction Repository for KDB Oversea
 * 
 * Provides data access methods for DepositTransaction entity.
 */
@Repository
public interface DepositTransactionRepository extends JpaRepository<DepositTransaction, Long> {

    /**
     * Find deposit transaction by transaction ID
     */
    DepositTransaction findByTransactionId(String transactionId);

    /**
     * Find deposit transactions by account number
     */
    List<DepositTransaction> findByAccountNumber(String accountNumber);

    /**
     * Find deposit transactions by account number ordered by transaction date
     */
    List<DepositTransaction> findByAccountNumberOrderByTransactionDateDesc(String accountNumber);

    /**
     * Find deposit transactions by transaction type
     */
    List<DepositTransaction> findByTransactionType(String transactionType);

    /**
     * Find deposit transactions by status
     */
    List<DepositTransaction> findByStatus(String status);

    /**
     * Find deposit transactions by account number and transaction type
     */
    List<DepositTransaction> findByAccountNumberAndTransactionType(String accountNumber, String transactionType);

    /**
     * Find deposit transactions by account number and status
     */
    List<DepositTransaction> findByAccountNumberAndStatus(String accountNumber, String status);

    /**
     * Check if transaction exists by transaction ID
     */
    boolean existsByTransactionId(String transactionId);

    /**
     * Count transactions by account number
     */
    long countByAccountNumber(String accountNumber);

    /**
     * Count transactions by transaction type
     */
    long countByTransactionType(String transactionType);

    /**
     * Count transactions by status
     */
    long countByStatus(String status);

    /**
     * Count transactions by account number and transaction type
     */
    long countByAccountNumberAndTransactionType(String accountNumber, String transactionType);

    /**
     * Get daily transaction total for an account
     */
    @Query("SELECT COALESCE(SUM(dt.amount), 0) FROM DepositTransaction dt " +
            "WHERE dt.accountNumber = :accountNumber " +
            "AND dt.transactionDate >= :startOfDay " +
            "AND dt.transactionDate < :endOfDay " +
            "AND dt.status = 'COMPLETED'")
    BigDecimal getDailyTransactionTotal(@Param("accountNumber") String accountNumber,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    /**
     * Get monthly transaction total for an account
     */
    @Query("SELECT COALESCE(SUM(dt.amount), 0) FROM DepositTransaction dt " +
            "WHERE dt.accountNumber = :accountNumber " +
            "AND dt.transactionDate >= :startOfMonth " +
            "AND dt.transactionDate < :endOfMonth " +
            "AND dt.status = 'COMPLETED'")
    BigDecimal getMonthlyTransactionTotal(@Param("accountNumber") String accountNumber,
            @Param("startOfMonth") LocalDateTime startOfMonth,
            @Param("endOfMonth") LocalDateTime endOfMonth);

    /**
     * Find deposit transactions by date range
     */
    @Query("SELECT dt FROM DepositTransaction dt WHERE dt.transactionDate BETWEEN :startDate AND :endDate")
    List<DepositTransaction> findByDateRange(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find deposit transactions by account number and date range
     */
    @Query("SELECT dt FROM DepositTransaction dt WHERE dt.accountNumber = :accountNumber AND dt.transactionDate BETWEEN :startDate AND :endDate")
    List<DepositTransaction> findByAccountNumberAndDateRange(@Param("accountNumber") String accountNumber,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find deposit transactions by transaction type and date range
     */
    @Query("SELECT dt FROM DepositTransaction dt WHERE dt.transactionType = :transactionType AND dt.transactionDate BETWEEN :startDate AND :endDate")
    List<DepositTransaction> findByTransactionTypeAndDateRange(@Param("transactionType") String transactionType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find completed transactions
     */
    @Query("SELECT dt FROM DepositTransaction dt WHERE dt.status = 'COMPLETED'")
    List<DepositTransaction> findCompletedTransactions();

    /**
     * Find failed transactions
     */
    @Query("SELECT dt FROM DepositTransaction dt WHERE dt.status = 'FAILED'")
    List<DepositTransaction> findFailedTransactions();

    /**
     * Find pending transactions
     */
    @Query("SELECT dt FROM DepositTransaction dt WHERE dt.status = 'PENDING'")
    List<DepositTransaction> findPendingTransactions();

    /**
     * Find deposit transactions by search criteria
     */
    @Query("SELECT dt FROM DepositTransaction dt WHERE " +
            "(:accountNumber IS NULL OR dt.accountNumber = :accountNumber) AND " +
            "(:transactionType IS NULL OR dt.transactionType = :transactionType) AND " +
            "(:status IS NULL OR dt.status = :status)")
    List<DepositTransaction> findBySearchCriteria(@Param("accountNumber") String accountNumber,
            @Param("transactionType") String transactionType,
            @Param("status") String status);

    /**
     * Get transaction summary by account number
     */
    @Query("SELECT dt.transactionType, COUNT(dt) as count, dt.status FROM DepositTransaction dt " +
            "WHERE dt.accountNumber = :accountNumber GROUP BY dt.transactionType, dt.status")
    List<Object[]> getTransactionSummaryByAccountNumber(@Param("accountNumber") String accountNumber);

    /**
     * Get transaction count by type for an account
     */
    @Query("SELECT COUNT(dt) FROM DepositTransaction dt WHERE dt.accountNumber = :accountNumber AND dt.transactionType = :transactionType")
    long countByAccountNumberAndTransactionType(@Param("accountNumber") String accountNumber,
            @Param("transactionType") String transactionType);

    /**
     * Get daily transaction total (simplified version for service use)
     */
    @Query("SELECT COALESCE(SUM(dt.amount), 0) FROM DepositTransaction dt " +
            "WHERE dt.accountNumber = :accountNumber " +
            "AND DATE(dt.transactionDate) = CURRENT_DATE " +
            "AND dt.status = 'COMPLETED'")
    BigDecimal getDailyTransactionTotal(@Param("accountNumber") String accountNumber);

    /**
     * Get monthly transaction total (simplified version for service use)
     */
    @Query("SELECT COALESCE(SUM(dt.amount), 0) FROM DepositTransaction dt " +
            "WHERE dt.accountNumber = :accountNumber " +
            "AND YEAR(dt.transactionDate) = YEAR(CURRENT_DATE) " +
            "AND MONTH(dt.transactionDate) = MONTH(CURRENT_DATE) " +
            "AND dt.status = 'COMPLETED'")
    BigDecimal getMonthlyTransactionTotal(@Param("accountNumber") String accountNumber);
}