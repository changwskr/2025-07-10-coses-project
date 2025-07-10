package com.chb.coses.framework.business.delegate.action;

import com.chb.coses.framework.business.delegate.IBizDelegate;
import com.chb.coses.framework.transfer.CosesCommonDTO;
import com.chb.coses.framework.transfer.CosesEvent;

/**
 * Abstract base class for business actions
 */
public abstract class AbstractBizAction {

    protected IBizDelegate bizDelegate;

    public AbstractBizAction() {
        // Default constructor
    }

    public AbstractBizAction(IBizDelegate bizDelegate) {
        this.bizDelegate = bizDelegate;
    }

    public void setBizDelegate(IBizDelegate bizDelegate) {
        this.bizDelegate = bizDelegate;
    }

    public IBizDelegate getBizDelegate() {
        return bizDelegate;
    }

    /**
     * Execute business action
     */
    public abstract CosesCommonDTO execute(CosesEvent event) throws Exception;

    /**
     * Validate input parameters
     */
    protected boolean validateInput(CosesEvent event) {
        return event != null;
    }
}