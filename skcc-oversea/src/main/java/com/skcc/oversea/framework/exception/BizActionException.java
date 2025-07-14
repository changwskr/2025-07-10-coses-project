package com.skcc.oversea.framework.exception;

public class BizActionException extends Exception {
    private static final long serialVersionUID = 1L;

    public BizActionException() {
        super();
    }

    public BizActionException(String message) {
        super(message);
    }

    public BizActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizActionException(Throwable cause) {
        super(cause);
    }
}