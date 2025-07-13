package com.chb.coses.eplatonFMK.business.facade.ecommon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.business.tcf.TCF;

/**
 * 공통 관리 서비스 구현 클래스
 * TCF를 통해 트랜잭션을 제어하는 구조
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@Service
public class ECommonManagementSB implements IECommonManagementSB {

    private static final Logger logger = LoggerFactory.getLogger(ECommonManagementSB.class);

    @Autowired
    private TCF tcf;

    /**
     * 기본 생성자
     */
    public ECommonManagementSB() {
        super();
    }

    @Override
    public Object execute(EPlatonEvent event) throws Exception {
        logger.debug("ECommonManagementSB execute for event: {}", event.getTransactionId());

        // TCF를 통해 트랜잭션 제어 및 비즈니스 로직 실행
        Object result = tcf.execute(event);

        logger.debug("ECommonManagementSB execute completed for event: {}", event.getTransactionId());
        return result;
    }

    @Override
    public boolean validate(EPlatonEvent event) throws Exception {
        logger.debug("ECommonManagementSB validate for event: {}", event.getTransactionId());

        // TCF를 통한 검증
        boolean result = tcf.validate(event);

        logger.debug("ECommonManagementSB validate passed for event: {}", event.getTransactionId());
        return result;
    }
}