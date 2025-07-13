package com.chb.coses.eplatonFMK.business.facade.cashCard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.business.tcf.TCF;

/**
 * 현금카드 관리 서비스 구현 클래스
 * TCF를 통해 트랜잭션을 제어하는 구조
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@Service
public class CashCardManagementSB implements ICashCardManagementSB {

    private static final Logger logger = LoggerFactory.getLogger(CashCardManagementSB.class);

    @Autowired
    private TCF tcf;

    /**
     * 기본 생성자
     */
    public CashCardManagementSB() {
        super();
    }

    @Override
    public Object execute(EPlatonEvent event) throws Exception {
        logger.debug("CashCardManagementSB execute for event: {}", event.getTransactionId());

        // TCF를 통해 트랜잭션 제어 및 비즈니스 로직 실행
        Object result = tcf.execute(event);

        logger.debug("CashCardManagementSB execute completed for event: {}", event.getTransactionId());
        return result;
    }

    @Override
    public boolean validate(EPlatonEvent event) throws Exception {
        logger.debug("CashCardManagementSB validate for event: {}", event.getTransactionId());

        // TCF를 통한 검증
        boolean result = tcf.validate(event);

        logger.debug("CashCardManagementSB validate passed for event: {}", event.getTransactionId());
        return result;
    }

    @Override
    public Object issueCashCard(EPlatonEvent event) throws Exception {
        logger.debug("CashCardManagementSB issueCashCard for event: {}", event.getTransactionId());

        // 메서드명 설정
        event.setMethodName("issueCashCard");

        // TCF를 통해 현금카드 발급 트랜잭션 실행
        Object result = tcf.execute(event);

        logger.debug("CashCardManagementSB issueCashCard completed for event: {}", event.getTransactionId());
        return result;
    }

    @Override
    public Object reissueCashCard(EPlatonEvent event) throws Exception {
        logger.debug("CashCardManagementSB reissueCashCard for event: {}", event.getTransactionId());

        // 메서드명 설정
        event.setMethodName("reissueCashCard");

        // TCF를 통해 현금카드 재발급 트랜잭션 실행
        Object result = tcf.execute(event);

        logger.debug("CashCardManagementSB reissueCashCard completed for event: {}", event.getTransactionId());
        return result;
    }

    @Override
    public Object suspendCashCard(EPlatonEvent event) throws Exception {
        logger.debug("CashCardManagementSB suspendCashCard for event: {}", event.getTransactionId());

        // 메서드명 설정
        event.setMethodName("suspendCashCard");

        // TCF를 통해 현금카드 정지 트랜잭션 실행
        Object result = tcf.execute(event);

        logger.debug("CashCardManagementSB suspendCashCard completed for event: {}", event.getTransactionId());
        return result;
    }

    @Override
    public Object cancelCashCard(EPlatonEvent event) throws Exception {
        logger.debug("CashCardManagementSB cancelCashCard for event: {}", event.getTransactionId());

        // 메서드명 설정
        event.setMethodName("cancelCashCard");

        // TCF를 통해 현금카드 해지 트랜잭션 실행
        Object result = tcf.execute(event);

        logger.debug("CashCardManagementSB cancelCashCard completed for event: {}", event.getTransactionId());
        return result;
    }
}