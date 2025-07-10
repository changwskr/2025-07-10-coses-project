package com.banking.kdb.oversea.framework.transaction.dao;

import com.banking.kdb.oversea.framework.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Transaction DAO for KDB Oversea Framework
 * 
 * Provides data access methods for Transaction entity.
 */
@Repository
public interface TransactionDAO extends JpaRepository<Transaction, Long> {

    /**
     * Find transaction by transaction ID
     */
    Optional<Transaction> findByTransactionId(String transactionId);

    /**
     * Find transactions by status
     */
    List<Transaction> findByStatus(String status);

    /**
     * Find transactions by transaction type
     */
    List<Transaction> findByTransactionType(String transactionType);

    /**
     * Find transactions by customer ID
     */
    List<Transaction> findByCustomerId(String customerId);

    /**
     * Find transactions by teller ID
     */
    List<Transaction> findByTellerId(String tellerId);

    /**
     * Find transactions by source account
     */
    List<Transaction> findBySourceAccount(String sourceAccount);

    /**
     * Find transactions by destination account
     */
    List<Transaction> findByDestinationAccount(String destinationAccount);

    /**
     * Find transactions by channel
     */
    List<Transaction> findByChannel(String channel);

    /**
     * Find transactions by authorization status
     */
    List<Transaction> findByAuthorizationStatus(String authorizationStatus);

    /**
     * Find transactions by validation status
     */
    List<Transaction> findByValidationStatus(String validationStatus);

    /**
     * Find transactions by routing status
     */
    List<Transaction> findByRoutingStatus(String routingStatus);

    /**
     * Find transactions by queue status
     */
    List<Transaction> findByQueueStatus(String queueStatus);

    /**
     * Find transactions by priority
     */
    List<Transaction> findByPriority(String priority);

    /**
     * Find transactions by error code
     */
    List<Transaction> findByErrorCode(String errorCode);

    /**
     * Find transactions by currency
     */
    List<Transaction> findByCurrency(String currency);

    /**
     * Find transactions by amount range
     */
    @Query("SELECT t FROM Transaction t WHERE t.amount BETWEEN :minAmount AND :maxAmount")
    List<Transaction> findByAmountRange(@Param("minAmount") BigDecimal minAmount,
            @Param("maxAmount") BigDecimal maxAmount);

    /**
     * Find transactions by created date range
     */
    @Query("SELECT t FROM Transaction t WHERE t.createdDateTime BETWEEN :startDate AND :endDate")
    List<Transaction> findByCreatedDateRange(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find transactions by authorization date range
     */
    @Query("SELECT t FROM Transaction t WHERE t.authorizationDate BETWEEN :startDate AND :endDate")
    List<Transaction> findByAuthorizationDateRange(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find transactions by multiple criteria
     */
    @Query("SELECT t FROM Transaction t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:transactionType IS NULL OR t.transactionType = :transactionType) AND " +
            "(:customerId IS NULL OR t.customerId = :customerId) AND " +
            "(:channel IS NULL OR t.channel = :channel) AND " +
            "(:currency IS NULL OR t.currency = :currency)")
    List<Transaction> findByMultipleCriteria(@Param("status") String status,
            @Param("transactionType") String transactionType,
            @Param("customerId") String customerId,
            @Param("channel") String channel,
            @Param("currency") String currency);

    /**
     * Find pending transactions for retry
     */
    @Query("SELECT t FROM Transaction t WHERE t.status = 'FAILED' AND t.retryCount < t.maxRetryAttempts AND " +
            "(t.nextRetryTime IS NULL OR t.nextRetryTime <= :currentTime)")
    List<Transaction> findPendingRetryTransactions(@Param("currentTime") LocalDateTime currentTime);

    /**
     * Find transactions requiring authorization
     */
    @Query("SELECT t FROM Transaction t WHERE t.authorizationStatus = 'PENDING' OR t.authorizationStatus = 'REQUIRED'")
    List<Transaction> findTransactionsRequiringAuthorization();

    /**
     * Find high priority transactions
     */
    @Query("SELECT t FROM Transaction t WHERE t.priority = 'HIGH' AND t.status IN ('INITIATED', 'PROCESSING')")
    List<Transaction> findHighPriorityTransactions();

    /**
     * Find slow transactions (processing time > threshold)
     */
    @Query("SELECT t FROM Transaction t WHERE t.processingTimeMs > :thresholdMs")
    List<Transaction> findSlowTransactions(@Param("thresholdMs") Long thresholdMs);

    /**
     * Find transactions by reference number
     */
    Optional<Transaction> findByReferenceNumber(String referenceNumber);

    /**
     * Find transactions by external reference
     */
    List<Transaction> findByExternalReference(String externalReference);

    /**
     * Find transactions by authorized by
     */
    List<Transaction> findByAuthorizedBy(String authorizedBy);

    /**
     * Find transactions by created by
     */
    List<Transaction> findByCreatedBy(String createdBy);

    /**
     * Find transactions by modified by
     */
    List<Transaction> findByModifiedBy(String modifiedBy);

    /**
     * Count transactions by status
     */
    long countByStatus(String status);

    /**
     * Count transactions by transaction type
     */
    long countByTransactionType(String transactionType);

    /**
     * Count transactions by customer ID
     */
    long countByCustomerId(String customerId);

    /**
     * Count transactions by channel
     */
    long countByChannel(String channel);

    /**
     * Count transactions by currency
     */
    long countByCurrency(String currency);

    /**
     * Count transactions by authorization status
     */
    long countByAuthorizationStatus(String authorizationStatus);

    /**
     * Count transactions by validation status
     */
    long countByValidationStatus(String validationStatus);

    /**
     * Count transactions by routing status
     */
    long countByRoutingStatus(String routingStatus);

    /**
     * Count transactions by queue status
     */
    long countByQueueStatus(String queueStatus);

    /**
     * Count transactions by priority
     */
    long countByPriority(String priority);

    /**
     * Count transactions by error code
     */
    long countByErrorCode(String errorCode);

    /**
     * Sum transaction amounts by status
     */
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.status = :status")
    BigDecimal sumAmountByStatus(@Param("status") String status);

    /**
     * Sum transaction amounts by transaction type
     */
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.transactionType = :transactionType")
    BigDecimal sumAmountByTransactionType(@Param("transactionType") String transactionType);

    /**
     * Sum transaction amounts by customer ID
     */
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.customerId = :customerId")
    BigDecimal sumAmountByCustomerId(@Param("customerId") String customerId);

    /**
     * Sum transaction amounts by currency
     */
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.currency = :currency")
    BigDecimal sumAmountByCurrency(@Param("currency") String currency);

    /**
     * Find transactions with processing time statistics
     */
    @Query("SELECT AVG(t.processingTimeMs), MIN(t.processingTimeMs), MAX(t.processingTimeMs) FROM Transaction t " +
            "WHERE t.processingTimeMs IS NOT NULL AND t.status = :status")
    Object[] getProcessingTimeStatistics(@Param("status") String status);

    /**
     * Find transactions by amount and status
     */
    @Query("SELECT t FROM Transaction t WHERE t.amount = :amount AND t.status = :status")
    List<Transaction> findByAmountAndStatus(@Param("amount") BigDecimal amount,
            @Param("status") String status);

    /**
     * Find transactions by source and destination accounts
     */
    @Query("SELECT t FROM Transaction t WHERE t.sourceAccount = :sourceAccount AND t.destinationAccount = :destinationAccount")
    List<Transaction> findBySourceAndDestinationAccounts(@Param("sourceAccount") String sourceAccount,
            @Param("destinationAccount") String destinationAccount);

    /**
     * Find transactions by description containing
     */
    @Query("SELECT t FROM Transaction t WHERE t.description LIKE %:description%")
    List<Transaction> findByDescriptionContaining(@Param("description") String description);

    /**
     * Find transactions by error message containing
     */
    @Query("SELECT t FROM Transaction t WHERE t.errorMessage LIKE %:errorMessage%")
    List<Transaction> findByErrorMessageContaining(@Param("errorMessage") String errorMessage);

    /**
     * Check if transaction exists by transaction ID
     */
    boolean existsByTransactionId(String transactionId);

    /**
     * Check if transaction exists by reference number
     */
    boolean existsByReferenceNumber(String referenceNumber);

    /**
     * Check if transaction exists by external reference
     */
    boolean existsByExternalReference(String externalReference);

    /**
     * Delete transactions by status
     */
    void deleteByStatus(String status);

    /**
     * Delete transactions by customer ID
     */
    void deleteByCustomerId(String customerId);

    /**
     * Delete transactions by created date before
     */
    @Query("DELETE FROM Transaction t WHERE t.createdDateTime < :date")
    void deleteByCreatedDateBefore(@Param("date") LocalDateTime date);
}