package com.banking.kdb.oversea.teller.repository;

import com.banking.kdb.oversea.teller.model.TellerSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Teller Session Repository for KDB Oversea
 * 
 * Provides data access methods for TellerSession entity.
 */
@Repository
public interface TellerSessionRepository extends JpaRepository<TellerSession, Long> {

    /**
     * Find teller session by session ID
     */
    Optional<TellerSession> findBySessionId(String sessionId);

    /**
     * Find active teller session by teller ID
     */
    Optional<TellerSession> findByTellerIdAndStatus(String tellerId, String status);

    /**
     * Find teller sessions by teller ID
     */
    List<TellerSession> findByTellerId(String tellerId);

    /**
     * Find teller sessions by branch code
     */
    List<TellerSession> findByBranchCode(String branchCode);

    /**
     * Find teller sessions by status
     */
    List<TellerSession> findByStatus(String status);

    /**
     * Find teller sessions by teller ID and status
     */
    List<TellerSession> findByTellerIdAndStatusOrderByStartTimeDesc(String tellerId, String status);

    /**
     * Check if teller has active session
     */
    boolean existsByTellerIdAndStatus(String tellerId, String status);

    /**
     * Count active sessions by teller ID
     */
    long countByTellerIdAndStatus(String tellerId, String status);

    /**
     * Count active sessions by branch code
     */
    long countByBranchCodeAndStatus(String branchCode, String status);

    /**
     * Find active teller sessions
     */
    @Query("SELECT ts FROM TellerSession ts WHERE ts.status = 'ACTIVE'")
    List<TellerSession> findActiveSessions();

    /**
     * Find teller sessions by date range
     */
    @Query("SELECT ts FROM TellerSession ts WHERE ts.startTime BETWEEN :startDate AND :endDate")
    List<TellerSession> findByDateRange(@Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find teller sessions by teller ID and date range
     */
    @Query("SELECT ts FROM TellerSession ts WHERE ts.tellerId = :tellerId AND ts.startTime BETWEEN :startDate AND :endDate")
    List<TellerSession> findByTellerIdAndDateRange(@Param("tellerId") String tellerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find teller sessions by branch code and date range
     */
    @Query("SELECT ts FROM TellerSession ts WHERE ts.branchCode = :branchCode AND ts.startTime BETWEEN :startDate AND :endDate")
    List<TellerSession> findByBranchCodeAndDateRange(@Param("branchCode") String branchCode,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Find long running sessions (more than specified hours)
     */
    @Query("SELECT ts FROM TellerSession ts WHERE ts.status = 'ACTIVE' AND ts.startTime < :cutoffTime")
    List<TellerSession> findLongRunningSessions(@Param("cutoffTime") LocalDateTime cutoffTime);

    /**
     * Find teller sessions by search criteria
     */
    @Query("SELECT ts FROM TellerSession ts WHERE " +
            "(:tellerId IS NULL OR ts.tellerId = :tellerId) AND " +
            "(:branchCode IS NULL OR ts.branchCode = :branchCode) AND " +
            "(:status IS NULL OR ts.status = :status)")
    List<TellerSession> findBySearchCriteria(@Param("tellerId") String tellerId,
            @Param("branchCode") String branchCode,
            @Param("status") String status);
}