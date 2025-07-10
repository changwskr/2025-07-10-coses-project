package com.chb.coses.reference.business.facade;

import com.chb.coses.framework.exception.CosesAppException;

/**
 * Reference Management Session Bean Interface
 */
public interface IReferenceManagementSB {

    /**
     * Get reference data
     * 
     * @param referenceType reference type
     * @return reference data
     * @throws CosesAppException if operation fails
     */
    String getReferenceData(String referenceType) throws CosesAppException;

    /**
     * Get reference list
     * 
     * @param referenceType reference type
     * @return reference list
     * @throws CosesAppException if operation fails
     */
    String[] getReferenceList(String referenceType) throws CosesAppException;

    /**
     * Refresh reference data
     * 
     * @param referenceType reference type
     * @throws CosesAppException if operation fails
     */
    void refreshReferenceData(String referenceType) throws CosesAppException;
}