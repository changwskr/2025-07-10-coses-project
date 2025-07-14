package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;
import com.skcc.oversea.eplatonframework.business.entity.Common;
import java.util.List;

/**
 * Common Service Interface for SKCC Oversea
 * 
 * Defines common business operations
 * using Spring Boot and modern Java patterns.
 */
public interface CommonService {

    // EPlaton Event methods
    /**
     * Get common info
     */
    EPlatonEvent getCommonInfo(EPlatonEvent event);

    /**
     * Create common
     */
    EPlatonEvent createCommon(EPlatonEvent event);

    /**
     * Update common
     */
    EPlatonEvent updateCommon(EPlatonEvent event);

    /**
     * Delete common
     */
    EPlatonEvent deleteCommon(EPlatonEvent event);

    /**
     * Validate common
     */
    EPlatonEvent validateCommon(EPlatonEvent event);

    // Controller expected methods
    /**
     * Find all commons
     */
    List<Common> findAll();

    /**
     * Find by ID
     */
    Common findById(Long id);

    /**
     * Find by common code
     */
    Common findByCommonCode(String commonCode);

    /**
     * Find by common type
     */
    List<Common> findByCommonType(String commonType);

    /**
     * Find active commons
     */
    List<Common> findActiveCommons();

    /**
     * Save common
     */
    Common save(Common common);

    /**
     * Delete by ID
     */
    void deleteById(Long id);

    /**
     * Check if exists by ID
     */
    boolean existsById(Long id);
}