package com.chb.coses.eplatonFMK.business.delegate.action;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;

/**
 * EPlaton 비즈니스 Action 인터페이스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
public interface EPlatonBizAction {

    /**
     * Action 실행
     * 
     * @param event EPlaton 이벤트
     * @return 처리 결과
     * @throws Exception 예외
     */
    Object act(EPlatonEvent event) throws Exception;
}