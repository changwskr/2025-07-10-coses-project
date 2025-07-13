package com.chb.coses.eplatonFMK.business.tcf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.business.dao.IEPlatonDelegateDAO;

/**
 * TCF (Transaction Control Framework) 구현 클래스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@Service
public class TCF extends AbstractTCF {

    private static final Logger logger = LoggerFactory.getLogger(TCF.class);

    @Autowired
    private IEPlatonDelegateDAO delegateDAO;

    @Autowired
    private STF stf;

    @Autowired
    private ETF etf;

    @Autowired
    private BTF btf;

    /**
     * 기본 생성자
     */
    public TCF() {
        super();
    }

    @Override
    protected Object doExecute(EPlatonEvent event) throws Exception {
        logger.debug("TCF doExecute started for event: {}", event.getTransactionId());

        try {
            // 입력 로그 기록
            delegateDAO.DB_INSERTinlog(event);

            // STF (Service Transaction Framework) 실행
            Object stfResult = stf.execute(event);

            // ETF (Error Transaction Framework) 실행
            Object etfResult = etf.execute(event);

            // BTF (Business Transaction Framework) 실행
            Object btfResult = btf.execute(event);

            // 출력 로그 기록
            delegateDAO.DB_INSERToutlog(event);

            logger.debug("TCF doExecute completed for event: {}", event.getTransactionId());
            return btfResult;

        } catch (Exception e) {
            logger.error("TCF doExecute failed for event: {}", event.getTransactionId(), e);
            throw e;
        }
    }

    @Override
    protected boolean doValidate(EPlatonEvent event) throws Exception {
        logger.debug("TCF doValidate for event: {}", event.getTransactionId());

        // 기본 검증 로직
        if (event == null) {
            logger.error("Event is null");
            return false;
        }

        if (event.getTransactionId() == null || event.getTransactionId().trim().isEmpty()) {
            logger.error("Transaction ID is null or empty");
            return false;
        }

        // STF 검증
        if (!stf.validate(event)) {
            logger.error("STF validation failed");
            return false;
        }

        // ETF 검증
        if (!etf.validate(event)) {
            logger.error("ETF validation failed");
            return false;
        }

        // BTF 검증
        if (!btf.validate(event)) {
            logger.error("BTF validation failed");
            return false;
        }

        logger.debug("TCF doValidate passed for event: {}", event.getTransactionId());
        return true;
    }

    @Override
    protected void doRollback(EPlatonEvent event) throws Exception {
        logger.debug("TCF doRollback for event: {}", event.getTransactionId());

        try {
            // BTF 롤백
            btf.rollback(event);

            // ETF 롤백
            etf.rollback(event);

            // STF 롤백
            stf.rollback(event);

            logger.debug("TCF doRollback completed for event: {}", event.getTransactionId());

        } catch (Exception e) {
            logger.error("TCF doRollback failed for event: {}", event.getTransactionId(), e);
            throw e;
        }
    }
}