package com.chb.coses.eplatonFMK.business.facade.ecommon;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;

/**
 * 공통 관리 서비스 인터페이스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
public interface IECommonManagementSB {

    /**
     * 공통 관리 서비스 실행
     * 
     * @param event EPlaton 이벤트
     * @return 실행 결과
     * @throws Exception 예외
     */
    Object execute(EPlatonEvent event) throws Exception;

    /**
     * 공통 관리 서비스 검증
     * 
     * @param event EPlaton 이벤트
     * @return 검증 결과
     * @throws Exception 예외
     */
    boolean validate(EPlatonEvent event) throws Exception;
}