package com.chb.coses.cosesFramework.business.delegate.action;

/**
 * Coses Business Action for legacy compatibility
 */
public abstract class CosesBizAction {

    private String actionName;
    private String actionType;
    private String description;

    public CosesBizAction() {
        this.actionName = "DefaultAction";
        this.actionType = "DEFAULT";
        this.description = "Default action description";
    }

    public CosesBizAction(String actionName, String actionType, String description) {
        this.actionName = actionName;
        this.actionType = actionType;
        this.description = description;
    }

    /**
     * Execute business action
     * 
     * @param parameters action parameters
     * @return execution result
     */
    public abstract Object execute(Object... parameters);

    /**
     * Validate action parameters
     * 
     * @param parameters parameters to validate
     * @return validation result
     */
    public boolean validateParameters(Object... parameters) {
        // Default implementation - always returns true
        return true;
    }

    /**
     * Get action name
     * 
     * @return action name
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * Set action name
     * 
     * @param actionName action name
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    /**
     * Get action type
     * 
     * @return action type
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * Set action type
     * 
     * @param actionType action type
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    /**
     * Get description
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description
     * 
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}