package com.skcc.oversea.eplatonframework.business.repository;

import com.skcc.oversea.eplatonframework.business.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Deposit Repository
 */
@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

    /**
     * Find by account number
     */
    Optional<Deposit> findByAccountNo(String accountNo);

    /**
     * Find by customer ID
     */
    List<Deposit> findByCustomerId(String customerId);

    /**
     * Find by account status
     */
    List<Deposit> findByAccountStatus(String accountStatus);

    /**
     * Find by account type
     */
    List<Deposit> findByAccountType(String accountType);

    /**
     * Find by currency code
     */
    List<Deposit> findByCurrencyCode(String currencyCode);

    /**
     * Find by branch code
     */
    List<Deposit> findByBranchCode(String branchCode);

    /**
     * Find by product code
     */
    List<Deposit> findByProductCode(String productCode);

    /**
     * Find by balance range
     */
    @Query("SELECT d FROM Deposit d WHERE d.balance BETWEEN :minBalance AND :maxBalance")
    List<Deposit> findByBalanceRange(@Param("minBalance") BigDecimal minBalance,
            @Param("maxBalance") BigDecimal maxBalance);

    /**
     * Find by interest rate range
     */
    @Query("SELECT d FROM Deposit d WHERE d.interestRate BETWEEN :minRate AND :maxRate")
    List<Deposit> findByInterestRateRange(@Param("minRate") BigDecimal minRate,
            @Param("maxRate") BigDecimal maxRate);

    /**
     * Find by open date range
     */
    @Query("SELECT d FROM Deposit d WHERE d.openDate BETWEEN :startDate AND :endDate")
    List<Deposit> findByOpenDateRange(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Find by maturity date range
     */
    @Query("SELECT d FROM Deposit d WHERE d.maturityDate BETWEEN :startDate AND :endDate")
    List<Deposit> findByMaturityDateRange(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Find matured deposits
     */
    @Query("SELECT d FROM Deposit d WHERE d.maturityDate <= :currentDate")
    List<Deposit> findMaturedDeposits(@Param("currentDate") LocalDate currentDate);

    /**
     * Find by account name (partial match)
     */
    @Query("SELECT d FROM Deposit d WHERE d.accountName LIKE %:name%")
    List<Deposit> findByAccountNameContaining(@Param("name") String name);

    /**
     * Find active accounts by customer ID
     */
    @Query("SELECT d FROM Deposit d WHERE d.customerId = :customerId AND d.accountStatus = 'AC'")
    List<Deposit> findActiveAccountsByCustomerId(@Param("customerId") String customerId);

    /**
     * Count accounts by status
     */
    long countByAccountStatus(String accountStatus);

    /**
     * Find accounts with balance below minimum
     */
    @Query("SELECT d FROM Deposit d WHERE d.balance < d.minimumBalance")
    List<Deposit> findAccountsBelowMinimumBalance();

    /**
     * Find by daily limit range
     */
    @Query("SELECT d FROM Deposit d WHERE d.dailyLimit BETWEEN :minLimit AND :maxLimit")
    List<Deposit> findByDailyLimitRange(@Param("minLimit") BigDecimal minLimit,
            @Param("maxLimit") BigDecimal maxLimit);

    /**
     * Sum total balance by customer ID
     */
    @Query("SELECT SUM(d.balance) FROM Deposit d WHERE d.customerId = :customerId")
    BigDecimal sumBalanceByCustomerId(@Param("customerId") String customerId);

    /**
     * Find accounts expiring soon
     */
    @Query("SELECT d FROM Deposit d WHERE d.maturityDate BETWEEN :currentDate AND :expiryDate")
    List<Deposit> findAccountsExpiringSoon(@Param("currentDate") LocalDate currentDate,
            @Param("expiryDate") LocalDate expiryDate);
}