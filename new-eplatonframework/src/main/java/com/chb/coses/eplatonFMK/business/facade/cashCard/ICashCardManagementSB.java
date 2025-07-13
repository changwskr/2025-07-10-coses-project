package com.chb.coses.eplatonFMK.business.facade.cashCard;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;

/**
 * 현금카드 관리 서비스 인터페이스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
public interface ICashCardManagementSB {

    /**
     * 현금카드 관리 서비스 실행
     * 
     * @param event EPlaton 이벤트
     * @return 실행 결과
     * @throws Exception 예외
     */
    Object execute(EPlatonEvent event) throws Exception;

    /**
     * 현금카드 관리 서비스 검증
     * 
     * @param event EPlaton 이벤트
     * @return 검증 결과
     * @throws Exception 예외
     */
    boolean validate(EPlatonEvent event) throws Exception;

    /**
     * 현금카드 발급
     * 
     * @param event EPlaton 이벤트
     * @return 발급 결과
     * @throws Exception 예외
     */
    Object issueCashCard(EPlatonEvent event) throws Exception;

    /**
     * 현금카드 재발급
     * 
     * @param event EPlaton 이벤트
     * @return 재발급 결과
     * @throws Exception 예외
     */
    Object reissueCashCard(EPlatonEvent event) throws Exception;

    /**
     * 현금카드 정지
     * 
     * @param event EPlaton 이벤트
     * @return 정지 결과
     * @throws Exception 예외
     */
    Object suspendCashCard(EPlatonEvent event) throws Exception;

    /**
     * 현금카드 해지
     * 
     * @param event EPlaton 이벤트
     * @return 해지 결과
     * @throws Exception 예외
     */
    Object cancelCashCard(EPlatonEvent event) throws Exception;
}