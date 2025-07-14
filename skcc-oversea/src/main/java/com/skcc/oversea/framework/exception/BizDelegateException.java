package com.skcc.oversea.framework.exception;

public class BizDelegateException extends Exception {
    private static final long serialVersionUID = 1L;

    public BizDelegateException() {
        super();
    }

    public BizDelegateException(String message) {
        super(message);
    }

    public BizDelegateException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizDelegateException(Throwable cause) {
        super(cause);
    }
}