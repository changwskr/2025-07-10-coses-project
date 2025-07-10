package com.banking.kdb.oversea.teller.repository;

import com.banking.kdb.oversea.teller.model.TellerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Teller Transaction Repository for KDB Oversea
 * 
 * Provides data access methods for TellerTransaction entity.
 */
@Repository
public interface TellerTransactionRepository extends JpaRepository<TellerTransaction, Long> {

    /**
     * Find teller transaction by transaction ID
     */
    TellerTransaction findByTransactionId(String transactionId);

    /**
     * Find teller transactions by session ID
     */
    List<TellerTransaction> findBySessionId(String sessionId);

    /**
     * Find teller transactions by session ID ordered by transaction date
     */
    List<TellerTransaction> findBySessionIdOrderByTransactionDateDesc(String sessionId);

    /**
     * Find teller transactions by transaction type
     */
    List<TellerTransaction> findByTransactionType(String transactionType);

    /**
     * Find teller transactions by status
     */
    List<TellerTransaction> findByStatus(String status);

    /**
     * Find teller transactions by session ID and transaction type
     */
    List<TellerTransaction> findBySessionIdAndTransactionType(String sessionId, String transactionType);

    /**
     * Find teller transactions by session ID and status
     */
    List<TellerTransaction> findBySessionIdAndStatus(String sessionId, String status);

    /**
     * Check if transaction exists by transaction ID
     */
    boolean existsByTransactionId(String transactionId);

    /**
     * Count transactions by session ID
     */
    long countBySessionId(String sessionId);

    /**
     * Count transactions by transaction type
     */
    long countByTransactionType(String transactionType);

    /**
     * Count transactions by status
     */
    long countByStatus(String status);

    /**
     * Count transactions by session ID and transaction type
     */
    long countBySessionIdAndTransactionType(String sessionId, String transactionType);

    /**
     * Find teller transactions by date range
     */
    @Query("SELECT tt FROM TellerTransaction tt WHERE tt.transactionDate BETWEEN :startDate AND :endDate")
    List<TellerTransaction> findByDateRange(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find teller transactions by session ID and date range
     */
    @Query("SELECT tt FROM TellerTransaction tt WHERE tt.sessionId = :sessionId AND tt.transactionDate BETWEEN :startDate AND :endDate")
    List<TellerTransaction> findBySessionIdAndDateRange(@Param("sessionId") String sessionId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find teller transactions by transaction type and date range
     */
    @Query("SELECT tt FROM TellerTransaction tt WHERE tt.transactionType = :transactionType AND tt.transactionDate BETWEEN :startDate AND :endDate")
    List<TellerTransaction> findByTransactionTypeAndDateRange(@Param("transactionType") String transactionType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find completed transactions
     */
    @Query("SELECT tt FROM TellerTransaction tt WHERE tt.status = 'COMPLETED'")
    List<TellerTransaction> findCompletedTransactions();

    /**
     * Find failed transactions
     */
    @Query("SELECT tt FROM TellerTransaction tt WHERE tt.status = 'FAILED'")
    List<TellerTransaction> findFailedTransactions();

    /**
     * Find pending transactions
     */
    @Query("SELECT tt FROM TellerTransaction tt WHERE tt.status = 'PENDING'")
    List<TellerTransaction> findPendingTransactions();

    /**
     * Find teller transactions by search criteria
     */
    @Query("SELECT tt FROM TellerTransaction tt WHERE " +
            "(:sessionId IS NULL OR tt.sessionId = :sessionId) AND " +
            "(:transactionType IS NULL OR tt.transactionType = :transactionType) AND " +
            "(:status IS NULL OR tt.status = :status)")
    List<TellerTransaction> findBySearchCriteria(@Param("sessionId") String sessionId,
            @Param("transactionType") String transactionType,
            @Param("status") String status);

    /**
     * Get transaction summary by session ID
     */
    @Query("SELECT tt.transactionType, COUNT(tt) as count, tt.status FROM TellerTransaction tt " +
            "WHERE tt.sessionId = :sessionId GROUP BY tt.transactionType, tt.status")
    List<Object[]> getTransactionSummaryBySessionId(@Param("sessionId") String sessionId);

    /**
     * Get transaction count by type for a session
     */
    @Query("SELECT COUNT(tt) FROM TellerTransaction tt WHERE tt.sessionId = :sessionId AND tt.transactionType = :transactionType")
    long countBySessionIdAndTransactionType(@Param("sessionId") String sessionId,
            @Param("transactionType") String transactionType);
}