package com.banking.kdb.oversea.framework.transfer.dao;

import com.banking.kdb.oversea.framework.transfer.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Transfer DAO for KDB Oversea Framework
 * 
 * Provides data access methods for Transfer entity.
 */
@Repository
public interface TransferDAO extends JpaRepository<Transfer, Long> {

    /**
     * Find transfer by transfer ID
     */
    Optional<Transfer> findByTransferId(String transferId);

    /**
     * Find transfers by status
     */
    List<Transfer> findByStatus(String status);

    /**
     * Find transfers by transfer type
     */
    List<Transfer> findByTransferType(String transferType);

    /**
     * Find transfers by source customer ID
     */
    List<Transfer> findBySourceCustomerId(String sourceCustomerId);

    /**
     * Find transfers by destination customer ID
     */
    List<Transfer> findByDestinationCustomerId(String destinationCustomerId);

    /**
     * Find transfers by source account
     */
    List<Transfer> findBySourceAccount(String sourceAccount);

    /**
     * Find transfers by destination account
     */
    List<Transfer> findByDestinationAccount(String destinationAccount);

    /**
     * Find transfers by transfer method
     */
    List<Transfer> findByTransferMethod(String transferMethod);

    /**
     * Find transfers by authorization status
     */
    List<Transfer> findByAuthorizationStatus(String authorizationStatus);

    /**
     * Find transfers by validation status
     */
    List<Transfer> findByValidationStatus(String validationStatus);

    /**
     * Find transfers by routing status
     */
    List<Transfer> findByRoutingStatus(String routingStatus);

    /**
     * Find transfers by queue status
     */
    List<Transfer> findByQueueStatus(String queueStatus);

    /**
     * Find transfers by priority
     */
    List<Transfer> findByPriority(String priority);

    /**
     * Find transfers by error code
     */
    List<Transfer> findByErrorCode(String errorCode);

    /**
     * Find transfers by currency
     */
    List<Transfer> findByCurrency(String currency);

    /**
     * Find transfers by source currency
     */
    List<Transfer> findBySourceCurrency(String sourceCurrency);

    /**
     * Find transfers by destination currency
     */
    List<Transfer> findByDestinationCurrency(String destinationCurrency);

    /**
     * Find transfers by amount range
     */
    @Query("SELECT t FROM Transfer t WHERE t.amount BETWEEN :minAmount AND :maxAmount")
    List<Transfer> findByAmountRange(@Param("minAmount") BigDecimal minAmount,
            @Param("maxAmount") BigDecimal maxAmount);

    /**
     * Find transfers by created date range
     */
    @Query("SELECT t FROM Transfer t WHERE t.createdDateTime BETWEEN :startDate AND :endDate")
    List<Transfer> findByCreatedDateRange(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find transfers by authorization date range
     */
    @Query("SELECT t FROM Transfer t WHERE t.authorizationDate BETWEEN :startDate AND :endDate")
    List<Transfer> findByAuthorizationDateRange(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find transfers by expected settlement date range
     */
    @Query("SELECT t FROM Transfer t WHERE t.expectedSettlementDate BETWEEN :startDate AND :endDate")
    List<Transfer> findByExpectedSettlementDateRange(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find transfers by actual settlement date range
     */
    @Query("SELECT t FROM Transfer t WHERE t.actualSettlementDate BETWEEN :startDate AND :endDate")
    List<Transfer> findByActualSettlementDateRange(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find transfers by multiple criteria
     */
    @Query("SELECT t FROM Transfer t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:transferType IS NULL OR t.transferType = :transferType) AND " +
            "(:sourceCustomerId IS NULL OR t.sourceCustomerId = :sourceCustomerId) AND " +
            "(:transferMethod IS NULL OR t.transferMethod = :transferMethod) AND " +
            "(:currency IS NULL OR t.currency = :currency)")
    List<Transfer> findByMultipleCriteria(@Param("status") String status,
            @Param("transferType") String transferType,
            @Param("sourceCustomerId") String sourceCustomerId,
            @Param("transferMethod") String transferMethod,
            @Param("currency") String currency);

    /**
     * Find pending transfers for retry
     */
    @Query("SELECT t FROM Transfer t WHERE t.status = 'FAILED' AND t.retryCount < t.maxRetryAttempts AND " +
            "(t.nextRetryTime IS NULL OR t.nextRetryTime <= :currentTime)")
    List<Transfer> findPendingRetryTransfers(@Param("currentTime") LocalDateTime currentTime);

    /**
     * Find transfers requiring authorization
     */
    @Query("SELECT t FROM Transfer t WHERE t.authorizationStatus = 'PENDING' OR t.authorizationStatus = 'REQUIRED'")
    List<Transfer> findTransfersRequiringAuthorization();

    /**
     * Find high priority transfers
     */
    @Query("SELECT t FROM Transfer t WHERE t.priority = 'HIGH' AND t.status IN ('INITIATED', 'PROCESSING')")
    List<Transfer> findHighPriorityTransfers();

    /**
     * Find slow transfers (processing time > threshold)
     */
    @Query("SELECT t FROM Transfer t WHERE t.processingTimeMs > :thresholdMs")
    List<Transfer> findSlowTransfers(@Param("thresholdMs") Long thresholdMs);

    /**
     * Find transfers by reference number
     */
    Optional<Transfer> findByReferenceNumber(String referenceNumber);

    /**
     * Find transfers by external reference
     */
    List<Transfer> findByExternalReference(String externalReference);

    /**
     * Find transfers by authorized by
     */
    List<Transfer> findByAuthorizedBy(String authorizedBy);

    /**
     * Find transfers by created by
     */
    List<Transfer> findByCreatedBy(String createdBy);

    /**
     * Find transfers by modified by
     */
    List<Transfer> findByModifiedBy(String modifiedBy);

    /**
     * Find transfers by exchange rate
     */
    @Query("SELECT t FROM Transfer t WHERE t.exchangeRate = :exchangeRate")
    List<Transfer> findByExchangeRate(@Param("exchangeRate") BigDecimal exchangeRate);

    /**
     * Find transfers by fee amount
     */
    @Query("SELECT t FROM Transfer t WHERE t.feeAmount = :feeAmount")
    List<Transfer> findByFeeAmount(@Param("feeAmount") BigDecimal feeAmount);

    /**
     * Find transfers by fee amount range
     */
    @Query("SELECT t FROM Transfer t WHERE t.feeAmount BETWEEN :minFee AND :maxFee")
    List<Transfer> findByFeeAmountRange(@Param("minFee") BigDecimal minFee,
            @Param("maxFee") BigDecimal maxFee);

    /**
     * Find international transfers
     */
    @Query("SELECT t FROM Transfer t WHERE t.transferType = 'INTERNATIONAL'")
    List<Transfer> findInternationalTransfers();

    /**
     * Find domestic transfers
     */
    @Query("SELECT t FROM Transfer t WHERE t.transferType = 'DOMESTIC'")
    List<Transfer> findDomesticTransfers();

    /**
     * Find internal transfers
     */
    @Query("SELECT t FROM Transfer t WHERE t.transferType = 'INTERNAL'")
    List<Transfer> findInternalTransfers();

    /**
     * Find external transfers
     */
    @Query("SELECT t FROM Transfer t WHERE t.transferType = 'EXTERNAL'")
    List<Transfer> findExternalTransfers();

    /**
     * Find transfers by description containing
     */
    @Query("SELECT t FROM Transfer t WHERE t.description LIKE %:description%")
    List<Transfer> findByDescriptionContaining(@Param("description") String description);

    /**
     * Find transfers by error message containing
     */
    @Query("SELECT t FROM Transfer t WHERE t.errorMessage LIKE %:errorMessage%")
    List<Transfer> findByErrorMessageContaining(@Param("errorMessage") String errorMessage);

    /**
     * Find transfers by validation message containing
     */
    @Query("SELECT t FROM Transfer t WHERE t.validationMessage LIKE %:validationMessage%")
    List<Transfer> findByValidationMessageContaining(@Param("validationMessage") String validationMessage);

    /**
     * Count transfers by status
     */
    long countByStatus(String status);

    /**
     * Count transfers by transfer type
     */
    long countByTransferType(String transferType);

    /**
     * Count transfers by source customer ID
     */
    long countBySourceCustomerId(String sourceCustomerId);

    /**
     * Count transfers by destination customer ID
     */
    long countByDestinationCustomerId(String destinationCustomerId);

    /**
     * Count transfers by transfer method
     */
    long countByTransferMethod(String transferMethod);

    /**
     * Count transfers by currency
     */
    long countByCurrency(String currency);

    /**
     * Count transfers by source currency
     */
    long countBySourceCurrency(String sourceCurrency);

    /**
     * Count transfers by destination currency
     */
    long countByDestinationCurrency(String destinationCurrency);

    /**
     * Count transfers by authorization status
     */
    long countByAuthorizationStatus(String authorizationStatus);

    /**
     * Count transfers by validation status
     */
    long countByValidationStatus(String validationStatus);

    /**
     * Count transfers by routing status
     */
    long countByRoutingStatus(String routingStatus);

    /**
     * Count transfers by queue status
     */
    long countByQueueStatus(String queueStatus);

    /**
     * Count transfers by priority
     */
    long countByPriority(String priority);

    /**
     * Count transfers by error code
     */
    long countByErrorCode(String errorCode);

    /**
     * Sum transfer amounts by status
     */
    @Query("SELECT SUM(t.amount) FROM Transfer t WHERE t.status = :status")
    BigDecimal sumAmountByStatus(@Param("status") String status);

    /**
     * Sum transfer amounts by transfer type
     */
    @Query("SELECT SUM(t.amount) FROM Transfer t WHERE t.transferType = :transferType")
    BigDecimal sumAmountByTransferType(@Param("transferType") String transferType);

    /**
     * Sum transfer amounts by source customer ID
     */
    @Query("SELECT SUM(t.amount) FROM Transfer t WHERE t.sourceCustomerId = :sourceCustomerId")
    BigDecimal sumAmountBySourceCustomerId(@Param("sourceCustomerId") String sourceCustomerId);

    /**
     * Sum transfer amounts by destination customer ID
     */
    @Query("SELECT SUM(t.amount) FROM Transfer t WHERE t.destinationCustomerId = :destinationCustomerId")
    BigDecimal sumAmountByDestinationCustomerId(@Param("destinationCustomerId") String destinationCustomerId);

    /**
     * Sum transfer amounts by currency
     */
    @Query("SELECT SUM(t.amount) FROM Transfer t WHERE t.currency = :currency")
    BigDecimal sumAmountByCurrency(@Param("currency") String currency);

    /**
     * Sum fee amounts by status
     */
    @Query("SELECT SUM(t.feeAmount) FROM Transfer t WHERE t.status = :status AND t.feeAmount IS NOT NULL")
    BigDecimal sumFeeAmountByStatus(@Param("status") String status);

    /**
     * Sum fee amounts by transfer type
     */
    @Query("SELECT SUM(t.feeAmount) FROM Transfer t WHERE t.transferType = :transferType AND t.feeAmount IS NOT NULL")
    BigDecimal sumFeeAmountByTransferType(@Param("transferType") String transferType);

    /**
     * Find transfers with processing time statistics
     */
    @Query("SELECT AVG(t.processingTimeMs), MIN(t.processingTimeMs), MAX(t.processingTimeMs) FROM Transfer t " +
            "WHERE t.processingTimeMs IS NOT NULL AND t.status = :status")
    Object[] getProcessingTimeStatistics(@Param("status") String status);

    /**
     * Find transfers by amount and status
     */
    @Query("SELECT t FROM Transfer t WHERE t.amount = :amount AND t.status = :status")
    List<Transfer> findByAmountAndStatus(@Param("amount") BigDecimal amount,
            @Param("status") String status);

    /**
     * Find transfers by source and destination accounts
     */
    @Query("SELECT t FROM Transfer t WHERE t.sourceAccount = :sourceAccount AND t.destinationAccount = :destinationAccount")
    List<Transfer> findBySourceAndDestinationAccounts(@Param("sourceAccount") String sourceAccount,
            @Param("destinationAccount") String destinationAccount);

    /**
     * Check if transfer exists by transfer ID
     */
    boolean existsByTransferId(String transferId);

    /**
     * Check if transfer exists by reference number
     */
    boolean existsByReferenceNumber(String referenceNumber);

    /**
     * Check if transfer exists by external reference
     */
    boolean existsByExternalReference(String externalReference);

    /**
     * Delete transfers by status
     */
    void deleteByStatus(String status);

    /**
     * Delete transfers by source customer ID
     */
    void deleteBySourceCustomerId(String sourceCustomerId);

    /**
     * Delete transfers by created date before
     */
    @Query("DELETE FROM Transfer t WHERE t.createdDateTime < :date")
    void deleteByCreatedDateBefore(@Param("date") LocalDateTime date);
}