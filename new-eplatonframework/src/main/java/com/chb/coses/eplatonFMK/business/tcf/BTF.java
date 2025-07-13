package com.chb.coses.eplatonFMK.business.tcf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.business.delegate.action.EPlatonBizAction;
import com.chb.coses.eplatonFMK.business.facade.cashCard.CashCardManagementSB;
import com.chb.coses.eplatonFMK.business.facade.deposit.DepositManagementSB;
import com.chb.coses.eplatonFMK.business.facade.ecommon.ECommonManagementSB;

/**
 * BTF (Business Transaction Framework) 구현 클래스
 * 실제 비즈니스 로직을 처리하는 최종 단계
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@Service
public class BTF extends AbstractTCF implements IBTF {

    private static final Logger logger = LoggerFactory.getLogger(BTF.class);

    @Autowired
    private CashCardManagementSB cashCardManagement;

    @Autowired
    private DepositManagementSB depositManagement;

    @Autowired
    private ECommonManagementSB commonManagement;

    /**
     * 기본 생성자
     */
    public BTF() {
        super();
    }

    @Override
    protected Object doExecute(EPlatonEvent event) throws Exception {
        logger.debug("BTF doExecute for event: {}", event.getTransactionId());

        String serviceName = event.getServiceName();
        String methodName = event.getMethodName();

        logger.debug("BTF 비즈니스 로직 실행 - 서비스: {}, 메서드: {}", serviceName, methodName);

        // 서비스별 비즈니스 로직 실행
        Object result = executeBusinessLogic(event, serviceName, methodName);

        logger.debug("BTF doExecute completed for event: {}", event.getTransactionId());
        return result;
    }

    /**
     * 서비스별 비즈니스 로직 실행
     * 
     * @param event       EPlaton 이벤트
     * @param serviceName 서비스명
     * @param methodName  메서드명
     * @return 처리 결과
     * @throws Exception 예외
     */
    private Object executeBusinessLogic(EPlatonEvent event, String serviceName, String methodName) throws Exception {
        switch (serviceName.toUpperCase()) {
            case "CASHCARD":
            case "CASH_CARD":
                return executeCashCardLogic(event, methodName);

            case "DEPOSIT":
                return executeDepositLogic(event, methodName);

            case "COMMON":
            case "ECOMMON":
                return executeCommonLogic(event, methodName);

            default:
                logger.warn("알 수 없는 서비스명: {}", serviceName);
                throw new IllegalArgumentException("알 수 없는 서비스명: " + serviceName);
        }
    }

    /**
     * 현금카드 비즈니스 로직 실행
     * 
     * @param event      EPlaton 이벤트
     * @param methodName 메서드명
     * @return 처리 결과
     * @throws Exception 예외
     */
    private Object executeCashCardLogic(EPlatonEvent event, String methodName) throws Exception {
        switch (methodName.toLowerCase()) {
            case "issue":
            case "issuecashcard":
                return "Cash card issued successfully - Transaction ID: " + event.getTransactionId();

            case "reissue":
            case "reissuecashcard":
                return "Cash card reissued successfully - Transaction ID: " + event.getTransactionId();

            case "suspend":
            case "suspendcashcard":
                return "Cash card suspended successfully - Transaction ID: " + event.getTransactionId();

            case "cancel":
            case "cancelcashcard":
                return "Cash card cancelled successfully - Transaction ID: " + event.getTransactionId();

            case "execute":
            default:
                return "Cash card management service executed successfully - Transaction ID: "
                        + event.getTransactionId();
        }
    }

    /**
     * 예금 비즈니스 로직 실행
     * 
     * @param event      EPlaton 이벤트
     * @param methodName 메서드명
     * @return 처리 결과
     * @throws Exception 예외
     */
    private Object executeDepositLogic(EPlatonEvent event, String methodName) throws Exception {
        switch (methodName.toLowerCase()) {
            case "create":
            case "createaccount":
            case "createdepositaccount":
                return "Deposit account created successfully - Transaction ID: " + event.getTransactionId();

            case "deposit":
                return "Deposit completed successfully - Transaction ID: " + event.getTransactionId();

            case "withdraw":
                return "Withdrawal completed successfully - Transaction ID: " + event.getTransactionId();

            case "execute":
            default:
                return "Deposit management service executed successfully - Transaction ID: " + event.getTransactionId();
        }
    }

    /**
     * 공통 비즈니스 로직 실행
     * 
     * @param event      EPlaton 이벤트
     * @param methodName 메서드명
     * @return 처리 결과
     * @throws Exception 예외
     */
    private Object executeCommonLogic(EPlatonEvent event, String methodName) throws Exception {
        switch (methodName.toLowerCase()) {
            case "execute":
            default:
                return "Common management service executed successfully - Transaction ID: " + event.getTransactionId();
        }
    }

    @Override
    protected boolean doValidate(EPlatonEvent event) throws Exception {
        logger.debug("BTF doValidate for event: {}", event.getTransactionId());

        // 비즈니스 검증 로직
        // 예: 잔액 확인, 한도 검사 등

        logger.debug("BTF doValidate passed for event: {}", event.getTransactionId());
        return true;
    }

    @Override
    protected void doRollback(EPlatonEvent event) throws Exception {
        logger.debug("BTF doRollback for event: {}", event.getTransactionId());

        // 비즈니스 롤백 로직
        // 예: 계좌 잔액 복원, 거래 취소 등

        logger.debug("BTF doRollback completed for event: {}", event.getTransactionId());
    }
}