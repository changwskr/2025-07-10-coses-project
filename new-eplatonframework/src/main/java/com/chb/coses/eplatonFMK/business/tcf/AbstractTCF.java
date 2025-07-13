package com.chb.coses.eplatonFMK.business.tcf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;

/**
 * 추상 TCF (Transaction Control Framework) 클래스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@Component
public abstract class AbstractTCF implements ITCF {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTCF.class);

    /**
     * 기본 생성자
     */
    public AbstractTCF() {
        super();
    }

    @Override
    public Object execute(EPlatonEvent event) throws Exception {
        logger.debug("Executing transaction for event: {}", event.getTransactionId());

        try {
            // 검증 수행
            if (!validate(event)) {
                throw new Exception("Transaction validation failed");
            }

            // 실제 실행 로직
            Object result = doExecute(event);

            logger.debug("Transaction executed successfully: {}", event.getTransactionId());
            return result;

        } catch (Exception e) {
            logger.error("Transaction execution failed: {}", event.getTransactionId(), e);
            rollback(event);
            throw e;
        }
    }

    @Override
    public boolean validate(EPlatonEvent event) throws Exception {
        logger.debug("Validating transaction for event: {}", event.getTransactionId());
        return doValidate(event);
    }

    @Override
    public void rollback(EPlatonEvent event) throws Exception {
        logger.debug("Rolling back transaction for event: {}", event.getTransactionId());
        doRollback(event);
    }

    /**
     * 실제 실행 로직 구현
     * 
     * @param event EPlaton 이벤트
     * @return 실행 결과
     * @throws Exception 예외
     */
    protected abstract Object doExecute(EPlatonEvent event) throws Exception;

    /**
     * 실제 검증 로직 구현
     * 
     * @param event EPlaton 이벤트
     * @return 검증 결과
     * @throws Exception 예외
     */
    protected abstract boolean doValidate(EPlatonEvent event) throws Exception;

    /**
     * 실제 롤백 로직 구현
     * 
     * @param event EPlaton 이벤트
     * @throws Exception 예외
     */
    protected abstract void doRollback(EPlatonEvent event) throws Exception;
}