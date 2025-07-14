package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;

/**
 * SP CashCard Service Interface for SKCC Oversea
 * 
 * Defines SP cashcard business operations
 * using Spring Boot and modern Java patterns.
 */
public interface SPcashcardService {

    /**
     * Get SP cashcard info
     */
    EPlatonEvent getSPCashCardInfo(EPlatonEvent event);

    /**
     * Create SP cashcard
     */
    EPlatonEvent createSPCashCard(EPlatonEvent event);

    /**
     * Update SP cashcard
     */
    EPlatonEvent updateSPCashCard(EPlatonEvent event);

    /**
     * Delete SP cashcard
     */
    EPlatonEvent deleteSPCashCard(EPlatonEvent event);

    /**
     * Validate SP cashcard
     */
    EPlatonEvent validateSPCashCard(EPlatonEvent event);
}