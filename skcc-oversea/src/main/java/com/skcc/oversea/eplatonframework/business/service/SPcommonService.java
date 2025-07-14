package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;

/**
 * SP Common Service Interface for SKCC Oversea
 * 
 * Defines SP common business operations
 * using Spring Boot and modern Java patterns.
 */
public interface SPcommonService {

    /**
     * Get SP system info
     */
    EPlatonEvent getSPSystemInfo(EPlatonEvent event);

    /**
     * Get SP user info
     */
    EPlatonEvent getSPUserInfo(EPlatonEvent event);

    /**
     * Validate SP session
     */
    EPlatonEvent validateSPSession(EPlatonEvent event);

    /**
     * Get SP configuration
     */
    EPlatonEvent getSPConfiguration(EPlatonEvent event);

    /**
     * Update SP configuration
     */
    EPlatonEvent updateSPConfiguration(EPlatonEvent event);

    /**
     * Get SP audit log
     */
    EPlatonEvent getSPAuditLog(EPlatonEvent event);

    /**
     * Clear SP cache
     */
    EPlatonEvent clearSPCache(EPlatonEvent event);

    /**
     * Get SP reference data
     */
    EPlatonEvent getSPReferenceData(EPlatonEvent event);
}