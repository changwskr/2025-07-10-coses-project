package com.chb.coses.cosesFramework.business.delegate.model;

import com.chb.coses.framework.transfer.CosesCommonDTO;
import com.chb.coses.framework.transfer.CosesEvent;

/**
 * Interface for Business Delegate Model
 */
public interface IBizDelegateModel {

    /**
     * Execute business logic
     */
    CosesCommonDTO execute(CosesEvent event) throws Exception;

    /**
     * Validate business rules
     */
    boolean validate(CosesEvent event) throws Exception;

    /**
     * Process business transaction
     */
    CosesCommonDTO processTransaction(CosesEvent event) throws Exception;

    /**
     * Get model information
     */
    String getModelInfo();
}