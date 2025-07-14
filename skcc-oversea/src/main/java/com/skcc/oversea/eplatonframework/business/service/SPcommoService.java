package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;

/**
 * SP Commo Service Interface for SKCC Oversea
 * 
 * Defines SP commo business operations
 * using Spring Boot and modern Java patterns.
 */
public interface SPcommoService {

    /**
     * Get SP commo info
     */
    EPlatonEvent getSPCommoInfo(EPlatonEvent event);

    /**
     * Create SP commo
     */
    EPlatonEvent createSPCommo(EPlatonEvent event);

    /**
     * Update SP commo
     */
    EPlatonEvent updateSPCommo(EPlatonEvent event);

    /**
     * Delete SP commo
     */
    EPlatonEvent deleteSPCommo(EPlatonEvent event);

    /**
     * Validate SP commo
     */
    EPlatonEvent validateSPCommo(EPlatonEvent event);
}