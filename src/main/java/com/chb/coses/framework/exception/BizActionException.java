package com.chb.coses.framework.exception;

/**
 * Business Action Exception for the EPlaton Framework
 */
public class BizActionException extends CosesAppException {

    private static final long serialVersionUID = 1L;

    private String actionName;
    private String businessErrorCode;

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

    public BizActionException(String actionName, String errorCode, String message) {
        super(errorCode, message);
        this.actionName = actionName;
        this.businessErrorCode = errorCode;
    }

    public BizActionException(String actionName, String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
        this.actionName = actionName;
        this.businessErrorCode = errorCode;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getBusinessErrorCode() {
        return businessErrorCode;
    }

    public void setBusinessErrorCode(String businessErrorCode) {
        this.businessErrorCode = businessErrorCode;
    }
}