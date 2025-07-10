package com.chb.coses.framework.exception;

/**
 * Business Delegate Exception for the EPlaton Framework
 */
public class BizDelegateException extends CosesAppException {

    private static final long serialVersionUID = 1L;

    private String delegateName;
    private String methodName;

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

    public BizDelegateException(String delegateName, String methodName, String message) {
        super(message);
        this.delegateName = delegateName;
        this.methodName = methodName;
    }

    public BizDelegateException(String delegateName, String methodName, String message, Throwable cause) {
        super(message, cause);
        this.delegateName = delegateName;
        this.methodName = methodName;
    }

    public BizDelegateException(String delegateName, String methodName, String errorCode, String message) {
        super(errorCode, message);
        this.delegateName = delegateName;
        this.methodName = methodName;
    }

    public BizDelegateException(String delegateName, String methodName, String errorCode, String message,
            Throwable cause) {
        super(errorCode, message, cause);
        this.delegateName = delegateName;
        this.methodName = methodName;
    }

    public String getDelegateName() {
        return delegateName;
    }

    public void setDelegateName(String delegateName) {
        this.delegateName = delegateName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}