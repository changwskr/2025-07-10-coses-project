package com.chb.coses.common.business.facade;

import com.chb.coses.framework.exception.CosesAppException;

/**
 * Common Management Session Bean Interface
 */
public interface ICommonManagementSB {

    /**
     * Get system information
     * 
     * @return system information
     * @throws CosesAppException if operation fails
     */
    String getSystemInfo() throws CosesAppException;

    /**
     * Get application version
     * 
     * @return application version
     * @throws CosesAppException if operation fails
     */
    String getVersion() throws CosesAppException;

    /**
     * Check system health
     * 
     * @return health status
     * @throws CosesAppException if operation fails
     */
    boolean isHealthy() throws CosesAppException;
}