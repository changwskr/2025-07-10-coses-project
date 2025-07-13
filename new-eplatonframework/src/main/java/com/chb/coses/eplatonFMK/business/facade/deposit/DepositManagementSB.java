package com.chb.coses.eplatonFMK.business.facade.deposit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.business.tcf.TCF;

/**
 * 예금 관리 서비스 구현 클래스
 * TCF를 통해 트랜잭션을 제어하는 구조
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@Service
public class DepositManagementSB implements IDepositManagementSB {

    private static final Logger logger = LoggerFactory.getLogger(DepositManagementSB.class);

    @Autowired
    private TCF tcf;

    /**
     * 기본 생성자
     */
    public DepositManagementSB() {
        super();
    }

    @Override
    public Object execute(EPlatonEvent event) throws Exception {
        logger.debug("DepositManagementSB execute for event: {}", event.getTransactionId());

        // TCF를 통해 트랜잭션 제어 및 비즈니스 로직 실행
        Object result = tcf.execute(event);

        logger.debug("DepositManagementSB execute completed for event: {}", event.getTransactionId());
        return result;
    }

    @Override
    public boolean validate(EPlatonEvent event) throws Exception {
        logger.debug("DepositManagementSB validate for event: {}", event.getTransactionId());

        // TCF를 통한 검증
        boolean result = tcf.validate(event);

        logger.debug("DepositManagementSB validate passed for event: {}", event.getTransactionId());
        return result;
    }

    @Override
    public Object createDepositAccount(EPlatonEvent event) throws Exception {
        logger.debug("DepositManagementSB createDepositAccount for event: {}", event.getTransactionId());

        // 메서드명 설정
        event.setMethodName("createDepositAccount");

        // TCF를 통해 예금 계좌 생성 트랜잭션 실행
        Object result = tcf.execute(event);

        logger.debug("DepositManagementSB createDepositAccount completed for event: {}", event.getTransactionId());
        return result;
    }

    @Override
    public Object deposit(EPlatonEvent event) throws Exception {
        logger.debug("DepositManagementSB deposit for event: {}", event.getTransactionId());

        // 메서드명 설정
        event.setMethodName("deposit");

        // TCF를 통해 예금 입금 트랜잭션 실행
        Object result = tcf.execute(event);

        logger.debug("DepositManagementSB deposit completed for event: {}", event.getTransactionId());
        return result;
    }

    @Override
    public Object withdraw(EPlatonEvent event) throws Exception {
        logger.debug("DepositManagementSB withdraw for event: {}", event.getTransactionId());

        // 메서드명 설정
        event.setMethodName("withdraw");

        // TCF를 통해 예금 출금 트랜잭션 실행
        Object result = tcf.execute(event);

        logger.debug("DepositManagementSB withdraw completed for event: {}", event.getTransactionId());
        return result;
    }
}