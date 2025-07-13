package com.chb.coses.eplatonFMK.business.dao;

/**
 * DAO 예외 클래스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
public class DAOException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 기본 생성자
     */
    public DAOException() {
        super();
    }

    /**
     * 메시지를 포함한 생성자
     * 
     * @param message 예외 메시지
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * 원인 예외를 포함한 생성자
     * 
     * @param cause 원인 예외
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * 메시지와 원인 예외를 포함한 생성자
     * 
     * @param message 예외 메시지
     * @param cause   원인 예외
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}