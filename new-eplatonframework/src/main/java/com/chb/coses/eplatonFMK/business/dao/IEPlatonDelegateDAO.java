package com.chb.coses.eplatonFMK.business.dao;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;

/**
 * EPlaton Delegate DAO 인터페이스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
public interface IEPlatonDelegateDAO {

    /**
     * 트랜잭션 입력 로그를 데이터베이스에 삽입
     * 
     * @param event EPlaton 이벤트
     * @throws DAOException DAO 예외
     */
    void DB_INSERTinlog(EPlatonEvent event) throws DAOException;

    /**
     * 트랜잭션 출력 로그를 데이터베이스에 삽입
     * 
     * @param event EPlaton 이벤트
     * @throws DAOException DAO 예외
     */
    void DB_INSERToutlog(EPlatonEvent event) throws DAOException;

    /**
     * 비즈니스 날짜 조회
     * 
     * @param bankCode 은행 코드
     * @return 비즈니스 날짜
     */
    String queryForBusinessDate(String bankCode);

    /**
     * 로깅 가능 여부 확인
     * 
     * @return 로깅 가능 여부
     */
    boolean isLogabled();

    /**
     * 로그 메시지 출력
     * 
     * @param message 로그 메시지
     */
    void log(Object message);

    /**
     * 로그 메시지와 예외 출력
     * 
     * @param message   로그 메시지
     * @param throwable 예외
     */
    void log(Object message, Throwable throwable);
}