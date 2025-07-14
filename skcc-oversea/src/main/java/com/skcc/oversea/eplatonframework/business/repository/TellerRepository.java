package com.skcc.oversea.eplatonframework.business.repository;

import com.skcc.oversea.eplatonframework.business.entity.Teller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Teller Repository
 */
@Repository
public interface TellerRepository extends JpaRepository<Teller, Long> {

    /**
     * Find by teller ID
     */
    Optional<Teller> findByTellerId(String tellerId);

    /**
     * Find by branch code
     */
    List<Teller> findByBranchCode(String branchCode);

    /**
     * Find by teller type
     */
    List<Teller> findByTellerType(String tellerType);

    /**
     * Find by teller status
     */
    List<Teller> findByTellerStatus(String tellerStatus);

    /**
     * Find by currency code
     */
    List<Teller> findByCurrencyCode(String currencyCode);

    /**
     * Find by email
     */
    Optional<Teller> findByEmail(String email);

    /**
     * Find by phone
     */
    Optional<Teller> findByPhone(String phone);

    /**
     * Find by daily limit range
     */
    @Query("SELECT t FROM Teller t WHERE t.dailyLimit BETWEEN :minLimit AND :maxLimit")
    List<Teller> findByDailyLimitRange(@Param("minLimit") BigDecimal minLimit,
            @Param("maxLimit") BigDecimal maxLimit);

    /**
     * Find by monthly limit range
     */
    @Query("SELECT t FROM Teller t WHERE t.monthlyLimit BETWEEN :minLimit AND :maxLimit")
    List<Teller> findByMonthlyLimitRange(@Param("minLimit") BigDecimal minLimit,
            @Param("maxLimit") BigDecimal maxLimit);

    /**
     * Find by hire date range
     */
    @Query("SELECT t FROM Teller t WHERE t.hireDate BETWEEN :startDate AND :endDate")
    List<Teller> findByHireDateRange(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Find active tellers
     */
    @Query("SELECT t FROM Teller t WHERE t.terminationDate IS NULL OR t.terminationDate > :currentDate")
    List<Teller> findActiveTellers(@Param("currentDate") LocalDate currentDate);

    /**
     * Count by branch code
     */
    long countByBranchCode(String branchCode);

    /**
     * Count by teller type
     */
    long countByTellerType(String tellerType);
}