package com.chb.coses.eplatonFMK.business.helper.tpmservice;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * TPM Service Health
 * Spring 기반으로 전환된 TPM 서비스 헬스 체크 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class TPMServiceHealth implements HealthIndicator {

    private static final Logger logger = LoggerFactory.getLogger(TPMServiceHealth.class);

    @Autowired
    private TPMServiceManager serviceManager;

    @Autowired
    private TPMServiceMonitor serviceMonitor;

    /**
     * 헬스 체크 실행
     */
    @Override
    public Health health() {
        try {
            logger.debug("TPM 서비스 헬스 체크 시작");

            // 서비스 사용 가능 여부 확인
            boolean isServiceAvailable = serviceManager.isServiceAvailable();
            if (!isServiceAvailable) {
                return Health.down()
                        .withDetail("service", "TPM Service")
                        .withDetail("status", "DOWN")
                        .withDetail("message", "서비스 사용 불가")
                        .build();
            }

            // 서비스 통계 정보 가져오기
            Map<String, Object> statistics = serviceManager.getServiceStatistics();

            // 성공률 확인
            Double successRate = (Double) statistics.get("successRate");
            if (successRate != null && successRate < 90.0) {
                return Health.down()
                        .withDetail("service", "TPM Service")
                        .withDetail("status", "DEGRADED")
                        .withDetail("successRate", successRate)
                        .withDetail("message", "성공률 저하")
                        .build();
            }

            // 평균 처리 시간 확인
            Double avgProcessingTime = (Double) statistics.get("avgProcessingTime");
            if (avgProcessingTime != null && avgProcessingTime > 10000) { // 10초 이상
                return Health.down()
                        .withDetail("service", "TPM Service")
                        .withDetail("status", "DEGRADED")
                        .withDetail("avgProcessingTime", avgProcessingTime)
                        .withDetail("message", "처리 시간 지연")
                        .build();
            }

            // 리소스 사용량 확인
            Map<String, Object> resourceData = (Map<String, Object>) serviceMonitor.getMonitoringData()
                    .get("resourceData");
            if (resourceData != null) {
                Double memoryUsage = (Double) resourceData.get("memoryUsage");
                if (memoryUsage != null && memoryUsage > 90.0) {
                    return Health.down()
                            .withDetail("service", "TPM Service")
                            .withDetail("status", "DEGRADED")
                            .withDetail("memoryUsage", memoryUsage)
                            .withDetail("message", "메모리 사용량 높음")
                            .build();
                }
            }

            // 모든 체크 통과
            return Health.up()
                    .withDetail("service", "TPM Service")
                    .withDetail("status", "UP")
                    .withDetail("successRate", successRate)
                    .withDetail("avgProcessingTime", avgProcessingTime)
                    .withDetail("totalEvents", statistics.get("totalEvents"))
                    .withDetail("uptime", statistics.get("uptime"))
                    .build();

        } catch (Exception e) {
            logger.error("TPM 서비스 헬스 체크 에러", e);
            return Health.down()
                    .withDetail("service", "TPM Service")
                    .withDetail("status", "DOWN")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }

    /**
     * 상세 헬스 체크
     */
    public Map<String, Object> detailedHealthCheck() {
        try {
            Map<String, Object> healthInfo = new HashMap<>();

            // 기본 헬스 정보
            Health health = health();
            healthInfo.put("status", health.getStatus().getCode());
            healthInfo.put("details", health.getDetails());

            // 서비스 통계
            Map<String, Object> statistics = serviceManager.getServiceStatistics();
            healthInfo.put("statistics", statistics);

            // 모니터링 데이터
            Map<String, Object> monitoringData = serviceMonitor.getMonitoringData();
            healthInfo.put("monitoringData", monitoringData);

            // 서비스 설정
            Map<String, Object> configuration = serviceManager.getServiceConfiguration();
            healthInfo.put("configuration", configuration);

            // 알림 히스토리
            List<String> alertHistory = serviceMonitor.getAlertHistory();
            healthInfo.put("alertHistory", alertHistory);

            // 체크 시간
            healthInfo.put("checkTime", new Date());

            return healthInfo;

        } catch (Exception e) {
            logger.error("상세 헬스 체크 에러", e);
            Map<String, Object> errorInfo = new HashMap<>();
            errorInfo.put("status", "ERROR");
            errorInfo.put("error", e.getMessage());
            errorInfo.put("checkTime", new Date());
            return errorInfo;
        }
    }

    /**
     * 서비스 상태 확인
     */
    public boolean isHealthy() {
        try {
            Health health = health();
            return health.getStatus().getCode().equals("UP");
        } catch (Exception e) {
            logger.error("서비스 상태 확인 에러", e);
            return false;
        }
    }

    /**
     * 서비스 상태 요약
     */
    public Map<String, Object> getHealthSummary() {
        try {
            Map<String, Object> summary = new HashMap<>();

            // 헬스 상태
            Health health = health();
            summary.put("status", health.getStatus().getCode());
            summary.put("details", health.getDetails());

            // 서비스 요약
            Map<String, Object> serviceSummary = serviceMonitor.getServiceSummary();
            summary.put("serviceSummary", serviceSummary);

            // 체크 시간
            summary.put("checkTime", new Date());

            return summary;

        } catch (Exception e) {
            logger.error("헬스 요약 가져오기 에러", e);
            Map<String, Object> errorSummary = new HashMap<>();
            errorSummary.put("status", "ERROR");
            errorSummary.put("error", e.getMessage());
            errorSummary.put("checkTime", new Date());
            return errorSummary;
        }
    }

    /**
     * 헬스 체크 설정
     */
    public Map<String, Object> getHealthCheckConfiguration() {
        try {
            Map<String, Object> config = new HashMap<>();

            config.put("checkInterval", "30초");
            config.put("timeout", "5초");
            config.put("retryCount", 3);
            config.put("successRateThreshold", 90.0);
            config.put("processingTimeThreshold", 10000.0);
            config.put("memoryUsageThreshold", 90.0);

            return config;

        } catch (Exception e) {
            logger.error("헬스 체크 설정 가져오기 에러", e);
            return new HashMap<>();
        }
    }
}