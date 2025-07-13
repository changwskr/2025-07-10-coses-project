package com.chb.coses.eplatonFMK.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.business.helper.TPMSVCAPI;
import com.chb.coses.eplatonFMK.business.helper.logej.LOGEJ;
import com.chb.coses.eplatonFMK.business.delegate.action.EPlatonBizAction;
import com.chb.coses.eplatonFMK.business.facade.cashCard.CashCardManagementSB;
import com.chb.coses.eplatonFMK.business.facade.deposit.DepositManagementSB;
import com.chb.coses.eplatonFMK.business.facade.ecommon.ECommonManagementSB;
import com.chb.coses.eplatonFMK.business.tcf.TCF;

import java.util.Map;
import java.util.HashMap;

/**
 * EPlaton Business Delegate Service Bean Implementation
 * 외부 연계를 담당하고 delegate의 Action들을 통해 facade를 찾아가는 구조
 * Spring 기반으로 전환된 비즈니스 델리게이트 구현체
 */
@Service
public class EPlatonBizDelegateSBBean {

    private static final Logger logger = LoggerFactory.getLogger(EPlatonBizDelegateSBBean.class);

    @Autowired
    private TPMSVCAPI tpmSvcApi;

    @Autowired
    private LOGEJ logEj;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CashCardManagementSB cashCardManagement;

    @Autowired
    private DepositManagementSB depositManagement;

    @Autowired
    private ECommonManagementSB commonManagement;

    @Autowired
    private TCF tcf;

    /**
     * 외부 연계를 담당하는 메인 execute 메서드
     * 
     * @param event EPlaton 이벤트
     * @return 처리 결과
     * @throws Exception 예외
     */
    public Object execute(EPlatonEvent event) throws Exception {
        logger.info("EPlatonBizDelegateSBBean execute 시작 - 트랜잭션ID: {}", event.getTransactionId());

        Object result = null;

        try {
            // TPM 서비스 API를 통한 요청 수신 처리
            tpmSvcApi.TPSrecv(event);

            // 에러 체크
            if (!isErr(event)) {
                logger.debug("TCF 실행 - 서비스: {}, 메서드: {}",
                        event.getServiceName(), event.getMethodName());

                // TCF를 통한 트랜잭션 제어 및 비즈니스 로직 실행
                result = tcf.execute(event);

                logger.info("TCF 실행 완료 - 트랜잭션ID: {}", event.getTransactionId());
            } else {
                logger.warn("에러 발생으로 TCF 실행 건너뜀 - 트랜잭션ID: {}, 에러코드: {}",
                        event.getTransactionId(), event.getTPSVCINFODTO().getErrorcode());
            }

        } catch (Exception e) {
            logger.error("EPlatonBizDelegateSBBean execute 에러 - 트랜잭션ID: {}",
                    event.getTransactionId(), e);

            // 에러 정보 설정
            if (event.getTPSVCINFODTO() != null) {
                event.getTPSVCINFODTO().setErrorcode("EDEL001");
                event.getTPSVCINFODTO().setError_message("CALL INITIAL ERROR: " + e.getMessage());
            }

            logEj.printf(event, "EPlatonBizDelegateSBBean-execute()에서 에러발생:[EDEL001]");
            logEj.eprintf(event, e);

            throw e;
        } finally {
            // TPM 서비스 API를 통한 응답 전송 처리
            tpmSvcApi.TPSsend(event);
        }

        logger.info("EPlatonBizDelegateSBBean execute 완료 - 트랜잭션ID: {}", event.getTransactionId());
        return result;
    }

    /**
     * 서비스명에 따라 적절한 Action으로 라우팅 (TCF에서 사용)
     * 
     * @param event EPlaton 이벤트
     * @return 처리 결과
     * @throws Exception 예외
     */
    public Object routeToAction(EPlatonEvent event) throws Exception {
        String serviceName = event.getServiceName();
        String methodName = event.getMethodName();

        logger.debug("Action 라우팅 - 서비스: {}, 메서드: {}", serviceName, methodName);

        // 서비스명에 따라 적절한 Action Bean을 찾아 실행
        EPlatonBizAction action = getActionBean(serviceName);

        if (action != null) {
            return action.act(event);
        } else {
            // 직접 Facade로 라우팅 (기본 처리)
            return routeToFacade(event);
        }
    }

    /**
     * 서비스명에 따라 적절한 Facade로 라우팅
     * 
     * @param event EPlaton 이벤트
     * @return 처리 결과
     * @throws Exception 예외
     */
    private Object routeToFacade(EPlatonEvent event) throws Exception {
        String serviceName = event.getServiceName();
        String methodName = event.getMethodName();

        logger.debug("Facade 라우팅 - 서비스: {}, 메서드: {}", serviceName, methodName);

        switch (serviceName.toUpperCase()) {
            case "CASHCARD":
            case "CASH_CARD":
                return executeCashCardMethod(event, methodName);

            case "DEPOSIT":
                return executeDepositMethod(event, methodName);

            case "COMMON":
            case "ECOMMON":
                return executeCommonMethod(event, methodName);

            default:
                logger.warn("알 수 없는 서비스명: {}", serviceName);
                throw new IllegalArgumentException("알 수 없는 서비스명: " + serviceName);
        }
    }

    /**
     * 현금카드 관련 메서드 실행
     * 
     * @param event      EPlaton 이벤트
     * @param methodName 메서드명
     * @return 처리 결과
     * @throws Exception 예외
     */
    private Object executeCashCardMethod(EPlatonEvent event, String methodName) throws Exception {
        switch (methodName.toLowerCase()) {
            case "issue":
            case "issuecashcard":
                return cashCardManagement.issueCashCard(event);

            case "reissue":
            case "reissuecashcard":
                return cashCardManagement.reissueCashCard(event);

            case "suspend":
            case "suspendcashcard":
                return cashCardManagement.suspendCashCard(event);

            case "cancel":
            case "cancelcashcard":
                return cashCardManagement.cancelCashCard(event);

            case "execute":
            default:
                return cashCardManagement.execute(event);
        }
    }

    /**
     * 예금 관련 메서드 실행
     * 
     * @param event      EPlaton 이벤트
     * @param methodName 메서드명
     * @return 처리 결과
     * @throws Exception 예외
     */
    private Object executeDepositMethod(EPlatonEvent event, String methodName) throws Exception {
        switch (methodName.toLowerCase()) {
            case "create":
            case "createaccount":
            case "createdepositaccount":
                return depositManagement.createDepositAccount(event);

            case "deposit":
                return depositManagement.deposit(event);

            case "withdraw":
                return depositManagement.withdraw(event);

            case "execute":
            default:
                return depositManagement.execute(event);
        }
    }

    /**
     * 공통 관련 메서드 실행
     * 
     * @param event      EPlaton 이벤트
     * @param methodName 메서드명
     * @return 처리 결과
     * @throws Exception 예외
     */
    private Object executeCommonMethod(EPlatonEvent event, String methodName) throws Exception {
        switch (methodName.toLowerCase()) {
            case "execute":
            default:
                return commonManagement.execute(event);
        }
    }

    /**
     * Spring ApplicationContext를 통해 Action Bean을 가져옴
     * 
     * @param serviceName 서비스명
     * @return Action Bean
     * @throws Exception 예외
     */
    private EPlatonBizAction getActionBean(String serviceName) throws Exception {
        try {
            // 서비스명에 따른 Action Bean 이름 생성
            String beanName = serviceName.toLowerCase() + "bizaction";

            // ApplicationContext에서 Bean 조회
            if (applicationContext.containsBean(beanName)) {
                return (EPlatonBizAction) applicationContext.getBean(beanName);
            }

            // 클래스명으로 직접 생성 시도
            String className = "com.chb.coses.eplatonFMK.business.delegate.action." +
                    serviceName.substring(0, 1).toUpperCase() +
                    serviceName.substring(1).toLowerCase() + "BizAction";

            Class<?> actionClass = Class.forName(className);
            return (EPlatonBizAction) actionClass.getDeclaredConstructor().newInstance();

        } catch (Exception e) {
            logger.debug("Action Bean을 찾을 수 없음 - 서비스: {}, 에러: {}", serviceName, e.getMessage());
            return null;
        }
    }

    /**
     * 에러 체크
     * 
     * @param eplevent EPlaton 이벤트
     * @return 에러 여부
     */
    private boolean isErr(EPlatonEvent eplevent) {
        if (eplevent.getTPSVCINFODTO() == null ||
                eplevent.getTPSVCINFODTO().getErrorcode() == null ||
                eplevent.getTPSVCINFODTO().getErrorcode().isEmpty()) {
            return false;
        }

        switch (eplevent.getTPSVCINFODTO().getErrorcode().charAt(0)) {
            case 'e':
            case 's':
            case 'E':
            case 'S':
                return true;
            case 'I':
                return false;
            case '*':
            default:
                return true;
        }
    }
}