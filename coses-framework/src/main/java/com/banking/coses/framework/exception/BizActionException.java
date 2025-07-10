package com.banking.coses.framework.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Exception class for business action errors in the COSES Framework
 * 
 * Used when business logic actions fail during execution.
 */
public class BizActionException extends CosesAppException {

    private static final long serialVersionUID = 1L;

    @JsonProperty("actionName")
    private String actionName;

    @JsonProperty("actionType")
    private String actionType;

    @JsonProperty("businessObject")
    private String businessObject;

    public BizActionException() {
        super();
    }

    public BizActionException(String message) {
        super(message);
    }

    public BizActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizActionException(String errorCode, String message) {
        super(errorCode, message);
    }

    public BizActionException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public BizActionException(String errorCode, String message, String actionName) {
        super(errorCode, message);
        this.actionName = actionName;
    }

    public BizActionException(String errorCode, String message, String actionName, String actionType) {
        super(errorCode, message);
        this.actionName = actionName;
        this.actionType = actionType;
    }

    public BizActionException(String errorCode, String message, String actionName, String actionType,
            String businessObject) {
        super(errorCode, message);
        this.actionName = actionName;
        this.actionType = actionType;
        this.businessObject = businessObject;
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

    public String getBusinessObject() {
        return businessObject;
    }

    public void setBusinessObject(String businessObject) {
        this.businessObject = businessObject;
    }

    @Override
    public String getFullErrorInfo() {
        StringBuilder sb = new StringBuilder(super.getFullErrorInfo());
        if (actionName != null) {
            sb.append(", Action: ").append(actionName);
        }
        if (actionType != null) {
            sb.append(", Type: ").append(actionType);
        }
        if (businessObject != null) {
            sb.append(", Object: ").append(businessObject);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "BizActionException{" +
                "errorCode='" + getErrorCode() + '\'' +
                ", errorMessage='" + getErrorMessage() + '\'' +
                ", actionName='" + actionName + '\'' +
                ", actionType='" + actionType + '\'' +
                ", businessObject='" + businessObject + '\'' +
                ", timestamp=" + getTimestamp() +
                '}';
    }
}