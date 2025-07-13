package com.chb.coses.eplatonFMK.business.delegate.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.business.tcf.TCF;

/**
 * 공통 비즈니스 Action 구현체
 * TCF를 통해 트랜잭션을 제어하는 구조
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@Component("commonbizaction")
public class CommonBizAction implements EPlatonBizAction {

    private static final Logger logger = LoggerFactory.getLogger(CommonBizAction.class);

    @Autowired
    private TCF tcf;

    @Override
    public Object act(EPlatonEvent event) throws Exception {
        logger.info("CommonBizAction 실행 - 트랜잭션ID: {}", event.getTransactionId());

        // TCF를 통해 공통 관련 트랜잭션 실행
        Object result = tcf.execute(event);

        logger.info("CommonBizAction 완료 - 트랜잭션ID: {}", event.getTransactionId());
        return result;
    }
}