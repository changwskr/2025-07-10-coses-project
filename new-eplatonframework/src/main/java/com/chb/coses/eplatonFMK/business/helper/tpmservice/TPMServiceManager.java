package com.chb.coses.eplatonFMK.business.helper.tpmservice;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.business.helper.TPMUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TPM Service Manager
 * Spring 기반으로 전환된 TPM 서비스 관리자 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class TPMServiceManager {

    private static final Logger logger = LoggerFactory.getLogger(TPMServiceManager.class);

    @Autowired
    private TPMService tpmService;

    @Autowired
    private TPMServiceConfig.TPMServiceProperties properties;

    private final Map<String, Object> serviceRegistry = new ConcurrentHashMap<>();
    private final Map<String, Long> serviceStatistics = new ConcurrentHashMap<>();

    /**
     * TPM 서비스 초기화
     */
    public boolean initialize() {
        try {
            logger.info("TPM 서비스 매니저 초기화 시작");

            // TPM 서비스 초기화
            if (!tpmService.initialize()) {
                logger.error("TPM 서비스 초기화 실패");
                return false;
            }

            // 서비스 레지스트리 초기화
            initializeServiceRegistry();

            // 통계 정보 초기화
            initializeStatistics();

            logger.info("TPM 서비스 매니저 초기화 완료");
            return true;

        } catch (Exception e) {
            logger.error("TPM 서비스 매니저 초기화 에러", e);
            return false;
        }
    }

    /**
     * TPM 서비스 종료
     */
    public boolean shutdown() {
        try {
            logger.info("TPM 서비스 매니저 종료 시작");

            // TPM 서비스 종료
            if (!tpmService.shutdown()) {
                logger.error("TPM 서비스 종료 실패");
                return false;
            }

            // 서비스 레지스트리 정리
            serviceRegistry.clear();

            // 통계 정보 정리
            serviceStatistics.clear();

            logger.info("TPM 서비스 매니저 종료 완료");
            return true;

        } catch (Exception e) {
            logger.error("TPM 서비스 매니저 종료 에러", e);
            return false;
        }
    }

    /**
     * TPM 이벤트 처리
     */
    public EPlatonEvent processEvent(EPlatonEvent event) {
        try {
            logger.info("TPM 이벤트 처리 시작");

            // 이벤트 검증
            if (!TPMUtil.validateTPMEvent(event)) {
                TPMUtil.setTPMErrorInfo(event, "ETPM001", "이벤트 검증 실패");
                return event;
            }

            // 서비스 상태 확인
            if (!isServiceAvailable()) {
                TPMUtil.setTPMErrorInfo(event, "ETPM002", "서비스 사용 불가");
                return event;
            }

            // 이벤트 처리 시작 시간 기록
            long startTime = System.currentTimeMillis();

            // TPM 서비스를 통한 이벤트 처리
            EPlatonEvent result = tpmService.processEvent(event);

            // 이벤트 처리 종료 시간 기록
            long endTime = System.currentTimeMillis();
            long processingTime = endTime - startTime;

            // 통계 정보 업데이트
            updateStatistics(event, processingTime);

            logger.info("TPM 이벤트 처리 완료 (처리시간: {}ms)", processingTime);
            return result;

        } catch (Exception e) {
            logger.error("TPM 이벤트 처리 에러", e);
            TPMUtil.logTPMError(event, e);
            TPMUtil.setTPMErrorInfo(event, "ETPM003", "TPM 이벤트 처리 중 오류 발생");
            return event;
        }
    }

    /**
     * 서비스 레지스트리 초기화
     */
    private void initializeServiceRegistry() {
        try {
            logger.info("서비스 레지스트리 초기화");

            // 기본 서비스 등록
            serviceRegistry.put("TPM_SERVICE", tpmService);
            serviceRegistry.put("PROPERTIES", properties);

            logger.info("서비스 레지스트리 초기화 완료");

        } catch (Exception e) {
            logger.error("서비스 레지스트리 초기화 에러", e);
        }
    }

    /**
     * 통계 정보 초기화
     */
    private void initializeStatistics() {
        try {
            logger.info("통계 정보 초기화");

            serviceStatistics.put("totalEvents", 0L);
            serviceStatistics.put("successEvents", 0L);
            serviceStatistics.put("errorEvents", 0L);
            serviceStatistics.put("totalProcessingTime", 0L);
            serviceStatistics.put("startTime", System.currentTimeMillis());

            logger.info("통계 정보 초기화 완료");

        } catch (Exception e) {
            logger.error("통계 정보 초기화 에러", e);
        }
    }

    /**
     * 통계 정보 업데이트
     */
    private void updateStatistics(EPlatonEvent event, long processingTime) {
        try {
            // 총 이벤트 수 증가
            serviceStatistics.merge("totalEvents", 1L, Long::sum);

            // 처리 시간 누적
            serviceStatistics.merge("totalProcessingTime", processingTime, Long::sum);

            // 성공/실패 이벤트 수 업데이트
            String errorCode = event.getTPSVCINFODTO().getErrorcode();
            if ("0000".equals(errorCode)) {
                serviceStatistics.merge("successEvents", 1L, Long::sum);
            } else {
                serviceStatistics.merge("errorEvents", 1L, Long::sum);
            }

        } catch (Exception e) {
            logger.error("통계 정보 업데이트 에러", e);
        }
    }

    /**
     * 서비스 사용 가능 여부 확인
     */
    public boolean isServiceAvailable() {
        try {
            return tpmService.isServiceAvailable();
        } catch (Exception e) {
            logger.error("서비스 사용 가능 여부 확인 에러", e);
            return false;
        }
    }

    /**
     * 서비스 통계 정보 가져오기
     */
    public Map<String, Object> getServiceStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();

            // 기본 통계 정보
            statistics.putAll(serviceStatistics);

            // 평균 처리 시간 계산
            long totalEvents = serviceStatistics.getOrDefault("totalEvents", 0L);
            long totalProcessingTime = serviceStatistics.getOrDefault("totalProcessingTime", 0L);
            if (totalEvents > 0) {
                double avgProcessingTime = (double) totalProcessingTime / totalEvents;
                statistics.put("avgProcessingTime", avgProcessingTime);
            }

            // 성공률 계산
            long successEvents = serviceStatistics.getOrDefault("successEvents", 0L);
            if (totalEvents > 0) {
                double successRate = (double) successEvents / totalEvents * 100;
                statistics.put("successRate", successRate);
            }

            // 서비스 시작 시간
            long startTime = serviceStatistics.getOrDefault("startTime", 0L);
            if (startTime > 0) {
                long uptime = System.currentTimeMillis() - startTime;
                statistics.put("uptime", uptime);
            }

            return statistics;

        } catch (Exception e) {
            logger.error("서비스 통계 정보 가져오기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 서비스 설정 정보 가져오기
     */
    public Map<String, Object> getServiceConfiguration() {
        try {
            return tpmService.getServiceConfiguration();
        } catch (Exception e) {
            logger.error("서비스 설정 정보 가져오기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 서비스 레지스트리 정보 가져오기
     */
    public Map<String, Object> getServiceRegistry() {
        try {
            return new HashMap<>(serviceRegistry);
        } catch (Exception e) {
            logger.error("서비스 레지스트리 정보 가져오기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 서비스 등록
     */
    public void registerService(String serviceName, Object service) {
        try {
            serviceRegistry.put(serviceName, service);
            logger.info("서비스 등록: {}", serviceName);
        } catch (Exception e) {
            logger.error("서비스 등록 에러", e);
        }
    }

    /**
     * 서비스 해제
     */
    public void unregisterService(String serviceName) {
        try {
            serviceRegistry.remove(serviceName);
            logger.info("서비스 해제: {}", serviceName);
        } catch (Exception e) {
            logger.error("서비스 해제 에러", e);
        }
    }

    /**
     * 서비스 가져오기
     */
    public Object getService(String serviceName) {
        try {
            return serviceRegistry.get(serviceName);
        } catch (Exception e) {
            logger.error("서비스 가져오기 에러", e);
            return null;
        }
    }
}