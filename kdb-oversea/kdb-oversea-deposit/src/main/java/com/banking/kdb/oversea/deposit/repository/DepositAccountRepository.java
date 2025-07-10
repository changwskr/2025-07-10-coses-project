package com.banking.kdb.oversea.deposit.repository;

import com.banking.kdb.oversea.deposit.model.DepositAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Deposit Account Repository for KDB Oversea
 * 
 * Provides data access methods for DepositAccount entity.
 */
@Repository
public interface DepositAccountRepository extends JpaRepository<DepositAccount, Long> {

    /**
     * Find deposit account by account number
     */
    Optional<DepositAccount> findByAccountNumber(String accountNumber);

    /**
     * Find deposit accounts by customer ID
     */
    List<DepositAccount> findByCustomerId(String customerId);

    /**
     * Find deposit accounts by status
     */
    List<DepositAccount> findByStatus(String status);

    /**
     * Find deposit accounts by account type
     */
    List<DepositAccount> findByAccountType(String accountType);

    /**
     * Find deposit accounts by currency
     */
    List<DepositAccount> findByCurrency(String currency);

    /**
     * Check if deposit account exists by account number
     */
    boolean existsByAccountNumber(String accountNumber);

    /**
     * Count deposit accounts by status
     */
    long countByStatus(String status);

    /**
     * Count deposit accounts by account type
     */
    long countByAccountType(String accountType);

    /**
     * Count deposit accounts by currency
     */
    long countByCurrency(String currency);

    /**
     * Find active deposit accounts
     */
    @Query("SELECT da FROM DepositAccount da WHERE da.status = 'ACTIVE'")
    List<DepositAccount> findActiveAccounts();

    /**
     * Find deposit accounts by customer ID and status
     */
    List<DepositAccount> findByCustomerIdAndStatus(String customerId, String status);

    /**
     * Find deposit accounts by customer ID and account type
     */
    List<DepositAccount> findByCustomerIdAndAccountType(String customerId, String accountType);

    /**
     * Find deposit accounts with balance greater than specified amount
     */
    @Query("SELECT da FROM DepositAccount da WHERE da.balance > :minBalance")
    List<DepositAccount> findAccountsWithBalanceGreaterThan(@Param("minBalance") java.math.BigDecimal minBalance);

    /**
     * Find deposit accounts with balance less than specified amount
     */
    @Query("SELECT da FROM DepositAccount da WHERE da.balance < :maxBalance")
    List<DepositAccount> findAccountsWithBalanceLessThan(@Param("maxBalance") java.math.BigDecimal maxBalance);

    /**
     * Find deposit accounts by search criteria
     */
    @Query("SELECT da FROM DepositAccount da WHERE " +
            "(:customerId IS NULL OR da.customerId = :customerId) AND " +
            "(:accountType IS NULL OR da.accountType = :accountType) AND " +
            "(:status IS NULL OR da.status = :status) AND " +
            "(:currency IS NULL OR da.currency = :currency)")
    List<DepositAccount> findBySearchCriteria(@Param("customerId") String customerId,
            @Param("accountType") String accountType,
            @Param("status") String status,
            @Param("currency") String currency);
}