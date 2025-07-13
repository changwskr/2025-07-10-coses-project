package com.chb.coses.eplatonFMK.business;

import org.springframework.stereotype.Service;
import com.chb.coses.framework.business.delegate.IBizDelegate;
import com.chb.coses.framework.transfer.IEvent;
import com.chb.coses.framework.exception.BizDelegateException;

/**
 * EPlaton Business Delegate Service Bean
 * Spring 기반으로 전환된 비즈니스 델리게이트 인터페이스
 */
@Service
public interface EPlatonBizDelegateSB extends IBizDelegate {

    /**
     * 비즈니스 로직 실행
     * 
     * @param event 비즈니스 이벤트
     * @return 처리 결과 이벤트
     * @throws BizDelegateException 비즈니스 델리게이트 예외
     */
    IEvent execute(IEvent event) throws BizDelegateException;
}