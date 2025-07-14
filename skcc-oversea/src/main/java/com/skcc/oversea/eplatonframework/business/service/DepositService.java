package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;
import com.skcc.oversea.eplatonframework.business.entity.Deposit;
import java.math.BigDecimal;
import java.util.List;

/**
 * Deposit Service Interface for SKCC Oversea
 * 
 * Defines deposit business operations
 * using Spring Boot and modern Java patterns.
 */
public interface DepositService {

    // EPlaton Event methods
    /**
     * Get deposit info
     */
    EPlatonEvent getDepositInfo(EPlatonEvent event);

    /**
     * Create deposit
     */
    EPlatonEvent createDeposit(EPlatonEvent event);

    /**
     * Update deposit
     */
    EPlatonEvent updateDeposit(EPlatonEvent event);

    /**
     * Delete deposit
     */
    EPlatonEvent deleteDeposit(EPlatonEvent event);

    /**
     * Validate deposit
     */
    EPlatonEvent validateDeposit(EPlatonEvent event);

    // Controller expected methods
    /**
     * Get all deposits
     */
    List<Deposit> getAllDeposits();

    /**
     * Get deposit by ID
     */
    Deposit getDepositById(Long id);

    /**
     * Get deposit by account number
     */
    Deposit getDepositByAccountNo(String accountNo);

    /**
     * Get deposits by customer ID
     */
    List<Deposit> getDepositsByCustomerId(String customerId);

    /**
     * Create deposit
     */
    Deposit createDeposit(Deposit deposit);

    /**
     * Update deposit
     */
    Deposit updateDeposit(Deposit deposit);

    /**
     * Delete deposit
     */
    boolean deleteDeposit(Long id);

    /**
     * Get deposits by status
     */
    List<Deposit> getDepositsByStatus(String status);

    /**
     * Get deposits by account type
     */
    List<Deposit> getDepositsByAccountType(String accountType);

    /**
     * Get deposits by branch code
     */
    List<Deposit> getDepositsByBranchCode(String branchCode);

    /**
     * Update account balance
     */
    Deposit updateAccountBalance(Long id, BigDecimal newBalance);

    /**
     * Get total balance by customer ID
     */
    BigDecimal getTotalBalanceByCustomerId(String customerId);

    /**
     * Get matured deposits
     */
    List<Deposit> getMaturedDeposits();

    /**
     * Update account status
     */
    Deposit updateAccountStatus(Long id, String status);
}