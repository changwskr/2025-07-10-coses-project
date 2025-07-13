package com.chb.coses.eplatonFMK.business.facade.deposit;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;

/**
 * 예금 관리 서비스 인터페이스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
public interface IDepositManagementSB {

    /**
     * 예금 관리 서비스 실행
     * 
     * @param event EPlaton 이벤트
     * @return 실행 결과
     * @throws Exception 예외
     */
    Object execute(EPlatonEvent event) throws Exception;

    /**
     * 예금 관리 서비스 검증
     * 
     * @param event EPlaton 이벤트
     * @return 검증 결과
     * @throws Exception 예외
     */
    boolean validate(EPlatonEvent event) throws Exception;

    /**
     * 예금 계좌 생성
     * 
     * @param event EPlaton 이벤트
     * @return 생성 결과
     * @throws Exception 예외
     */
    Object createDepositAccount(EPlatonEvent event) throws Exception;

    /**
     * 예금 입금
     * 
     * @param event EPlaton 이벤트
     * @return 입금 결과
     * @throws Exception 예외
     */
    Object deposit(EPlatonEvent event) throws Exception;

    /**
     * 예금 출금
     * 
     * @param event EPlaton 이벤트
     * @return 출금 결과
     * @throws Exception 예외
     */
    Object withdraw(EPlatonEvent event) throws Exception;
}