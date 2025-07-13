package com.skcc.oversea.eplatonframework.business.repository;

import com.skcc.oversea.eplatonframework.business.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Transaction Log Repository
 */
@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

    /**
     * Find by transaction ID
     */
    Optional<TransactionLog> findByTransactionId(String transactionId);

    /**
     * Find by transaction number
     */
    Optional<TransactionLog> findByTransactionNo(String transactionNo);

    /**
     * Find by user ID
     */
    List<TransactionLog> findByUserId(String userId);

    /**
     * Find by system name
     */
    List<TransactionLog> findBySystemName(String systemName);

    /**
     * Find by business date
     */
    List<TransactionLog> findByBusinessDate(String businessDate);

    /**
     * Find by date range
     */
    @Query("SELECT t FROM TransactionLog t WHERE t.createdDate BETWEEN :startDate AND :endDate")
    List<TransactionLog> findByDateRange(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find by error code
     */
    List<TransactionLog> findByErrorCode(String errorCode);

    /**
     * Find by channel type
     */
    List<TransactionLog> findByChannelType(String channelType);

    /**
     * Find by bank code and branch code
     */
    List<TransactionLog> findByBankCodeAndBranchCode(String bankCode, String branchCode);

    /**
     * Find transactions with response time greater than threshold
     */
    @Query("SELECT t FROM TransactionLog t WHERE t.responseTime > :threshold")
    List<TransactionLog> findByResponseTimeGreaterThan(@Param("threshold") Long threshold);

    /**
     * Count transactions by system name and date range
     */
    @Query("SELECT COUNT(t) FROM TransactionLog t WHERE t.systemName = :systemName AND t.createdDate BETWEEN :startDate AND :endDate")
    long countBySystemNameAndDateRange(@Param("systemName") String systemName,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find latest transaction by user ID
     */
    @Query("SELECT t FROM TransactionLog t WHERE t.userId = :userId ORDER BY t.createdDate DESC")
    List<TransactionLog> findLatestByUserId(@Param("userId") String userId);
}