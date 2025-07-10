package com.chb.coses.deposit.business.facade;

import com.chb.coses.framework.transfer.CosesCommonDTO;
import com.chb.coses.framework.transfer.CosesEvent;
import com.chb.coses.deposit.transfer.DepositDTO;

/**
 * Interface for Deposit Management Session Bean
 */
public interface IDepositManagementSB {

    /**
     * Create new deposit account
     */
    CosesCommonDTO createDepositAccount(CosesEvent event) throws Exception;

    /**
     * Update deposit account
     */
    CosesCommonDTO updateDepositAccount(CosesEvent event) throws Exception;

    /**
     * Close deposit account
     */
    CosesCommonDTO closeDepositAccount(CosesEvent event) throws Exception;

    /**
     * Get deposit account by number
     */
    DepositDTO getDepositAccount(String accountNumber) throws Exception;

    /**
     * Get all deposit accounts for customer
     */
    CosesCommonDTO getCustomerDeposits(String customerId) throws Exception;

    /**
     * Process deposit transaction
     */
    CosesCommonDTO processDepositTransaction(CosesEvent event) throws Exception;
}