package com.chb.coses.cosesFramework.action;

import com.chb.coses.framework.transfer.CosesCommonDTO;
import com.chb.coses.framework.transfer.CosesEvent;

/**
 * Abstract base class for COSES Framework actions
 */
public abstract class AbstractAction {

    protected String actionName;
    protected String actionType;

    public AbstractAction() {
        this.actionName = this.getClass().getSimpleName();
    }

    public AbstractAction(String actionName) {
        this.actionName = actionName;
    }

    /**
     * Execute the action
     */
    public abstract CosesCommonDTO execute(CosesEvent event) throws Exception;

    /**
     * Validate action parameters
     */
    protected boolean validateParameters(CosesEvent event) {
        return event != null && event.getEventData() != null;
    }

    /**
     * Pre-process action
     */
    protected void preProcess(CosesEvent event) throws Exception {
        // Default implementation - override if needed
    }

    /**
     * Post-process action
     */
    protected void postProcess(CosesEvent event, CosesCommonDTO result) throws Exception {
        // Default implementation - override if needed
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}