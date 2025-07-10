package com.ims.eplaton.eplatonFKC.business.facade.ecommon;

import com.chb.coses.framework.transfer.CosesCommonDTO;
import com.chb.coses.framework.transfer.CosesEvent;

/**
 * Interface for Common Management in EPlaton FKC
 */
public interface ICommonManagement {

    /**
     * Get system information
     */
    CosesCommonDTO getSystemInfo(CosesEvent event) throws Exception;

    /**
     * Get configuration parameters
     */
    CosesCommonDTO getConfigParameters(CosesEvent event) throws Exception;

    /**
     * Update configuration parameters
     */
    CosesCommonDTO updateConfigParameters(CosesEvent event) throws Exception;

    /**
     * Get reference data
     */
    CosesCommonDTO getReferenceData(CosesEvent event) throws Exception;

    /**
     * Process common operations
     */
    CosesCommonDTO processCommonOperation(CosesEvent event) throws Exception;

    /**
     * Validate system status
     */
    boolean isSystemAvailable() throws Exception;

    /**
     * Get service status
     */
    CosesCommonDTO getServiceStatus(String serviceName) throws Exception;
}