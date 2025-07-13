package com.chb.coses.eplatonFMK.business.tcf;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;

/**
 * IETF (Error Transaction Framework) 인터페이스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
public interface IETF {

    /**
     * 에러 트랜잭션 실행
     * 
     * @param event EPlaton 이벤트
     * @return 실행 결과
     * @throws Exception 예외
     */
    Object execute(EPlatonEvent event) throws Exception;

    /**
     * 에러 트랜잭션 검증
     * 
     * @param event EPlaton 이벤트
     * @return 검증 결과
     * @throws Exception 예외
     */
    boolean validate(EPlatonEvent event) throws Exception;

    /**
     * 에러 트랜잭션 롤백
     * 
     * @param event EPlaton 이벤트
     * @throws Exception 예외
     */
    void rollback(EPlatonEvent event) throws Exception;
}