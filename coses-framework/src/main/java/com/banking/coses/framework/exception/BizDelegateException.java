package com.banking.coses.framework.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Exception class for business delegate errors in the COSES Framework
 * 
 * Used when business delegate operations fail during execution.
 */
public class BizDelegateException extends CosesAppException {

    private static final long serialVersionUID = 1L;

    @JsonProperty("delegateName")
    private String delegateName;

    @JsonProperty("delegateType")
    private String delegateType;

    @JsonProperty("operation")
    private String operation;

    @JsonProperty("targetSystem")
    private String targetSystem;

    public BizDelegateException() {
        super();
    }

    public BizDelegateException(String message) {
        super(message);
    }

    public BizDelegateException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizDelegateException(String errorCode, String message) {
        super(errorCode, message);
    }

    public BizDelegateException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public BizDelegateException(String errorCode, String message, String delegateName) {
        super(errorCode, message);
        this.delegateName = delegateName;
    }

    public BizDelegateException(String errorCode, String message, String delegateName, String operation) {
        super(errorCode, message);
        this.delegateName = delegateName;
        this.operation = operation;
    }

    public BizDelegateException(String errorCode, String message, String delegateName, String operation,
            String targetSystem) {
        super(errorCode, message);
        this.delegateName = delegateName;
        this.operation = operation;
        this.targetSystem = targetSystem;
    }

    public String getDelegateName() {
        return delegateName;
    }

    public void setDelegateName(String delegateName) {
        this.delegateName = delegateName;
    }

    public String getDelegateType() {
        return delegateType;
    }

    public void setDelegateType(String delegateType) {
        this.delegateType = delegateType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getTargetSystem() {
        return targetSystem;
    }

    public void setTargetSystem(String targetSystem) {
        this.targetSystem = targetSystem;
    }

    @Override
    public String getFullErrorInfo() {
        StringBuilder sb = new StringBuilder(super.getFullErrorInfo());
        if (delegateName != null) {
            sb.append(", Delegate: ").append(delegateName);
        }
        if (delegateType != null) {
            sb.append(", Type: ").append(delegateType);
        }
        if (operation != null) {
            sb.append(", Operation: ").append(operation);
        }
        if (targetSystem != null) {
            sb.append(", Target: ").append(targetSystem);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "BizDelegateException{" +
                "errorCode='" + getErrorCode() + '\'' +
                ", errorMessage='" + getErrorMessage() + '\'' +
                ", delegateName='" + delegateName + '\'' +
                ", delegateType='" + delegateType + '\'' +
                ", operation='" + operation + '\'' +
                ", targetSystem='" + targetSystem + '\'' +
                ", timestamp=" + getTimestamp() +
                '}';
    }
}