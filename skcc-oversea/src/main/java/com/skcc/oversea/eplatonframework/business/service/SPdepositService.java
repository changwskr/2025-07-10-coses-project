package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;

/**
 * SP Deposit Service Interface for SKCC Oversea
 * 
 * Defines SP deposit business operations
 * using Spring Boot and modern Java patterns.
 */
public interface SPdepositService {

    /**
     * Get SP deposit info
     */
    EPlatonEvent getSPDepositInfo(EPlatonEvent event);

    /**
     * Create SP deposit
     */
    EPlatonEvent createSPDeposit(EPlatonEvent event);

    /**
     * Update SP deposit
     */
    EPlatonEvent updateSPDeposit(EPlatonEvent event);

    /**
     * Delete SP deposit
     */
    EPlatonEvent deleteSPDeposit(EPlatonEvent event);

    /**
     * Validate SP deposit
     */
    EPlatonEvent validateSPDeposit(EPlatonEvent event);
}