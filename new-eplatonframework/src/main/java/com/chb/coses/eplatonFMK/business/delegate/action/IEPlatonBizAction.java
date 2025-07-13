package com.chb.coses.eplatonFMK.business.delegate.action;

import org.springframework.stereotype.Component;
import com.chb.coses.framework.exception.BizActionException;
import com.chb.coses.framework.transfer.IEvent;

/**
 * EPlaton Business Action Interface
 * Spring 기반으로 전환된 비즈니스 액션 인터페이스
 */
@Component
public interface IEPlatonBizAction {

    /**
     * 액션 실행 전 처리
     * 
     * @param iEvent 비즈니스 이벤트
     */
    void preAct(IEvent iEvent);

    /**
     * 액션 실행
     * 
     * @param iEvent 비즈니스 이벤트
     * @return 처리 결과 이벤트
     * @throws BizActionException 비즈니스 액션 예외
     */
    IEvent act(IEvent iEvent) throws BizActionException;

    /**
     * 액션 실행 후 처리
     * 
     * @param iEvent 비즈니스 이벤트
     */
    void postAct(IEvent iEvent);
}