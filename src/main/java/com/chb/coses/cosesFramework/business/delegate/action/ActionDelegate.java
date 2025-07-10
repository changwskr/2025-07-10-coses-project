package com.chb.coses.cosesFramework.business.delegate.action;

/**
 * Action Delegate for Coses Framework
 */
public class ActionDelegate {

    private String actionName;
    private String actionType;

    public ActionDelegate() {
        this.actionName = "DefaultAction";
        this.actionType = "DEFAULT";
    }

    public ActionDelegate(String actionName, String actionType) {
        this.actionName = actionName;
        this.actionType = actionType;
    }

    /**
     * Execute action
     * 
     * @param parameters action parameters
     * @return execution result
     */
    public Object execute(Object... parameters) {
        // Default implementation
        return null;
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