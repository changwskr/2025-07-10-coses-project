package com.chb.coses.eplatonFMK.business.helper.tpmservice;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TPM Service Monitor
 * Spring 기반으로 전환된 TPM 서비스 모니터링 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class TPMServiceMonitor {

    private static final Logger logger = LoggerFactory.getLogger(TPMServiceMonitor.class);

    @Autowired
    private TPMServiceManager serviceManager;

    private final Map<String, Object> monitoringData = new ConcurrentHashMap<>();
    private final List<String> alertHistory = new ArrayList<>();

    /**
     * 서비스 상태 모니터링 (5분마다 실행)
     */
    @Scheduled(fixedRate = 300000) // 5분 = 300,000ms
    public void monitorServiceStatus() {
        try {
            logger.debug("서비스 상태 모니터링 시작");

            // 서비스 사용 가능 여부 확인
            boolean isAvailable = serviceManager.isServiceAvailable();
            monitoringData.put("serviceAvailable", isAvailable);
            monitoringData.put("lastCheckTime", new Date());

            if (!isAvailable) {
                String alert = "서비스 사용 불가 - " + new Date();
                alertHistory.add(alert);
                logger.warn("서비스 사용 불가 알림: {}", alert);
            }

            // 통계 정보 수집
            Map<String, Object> statistics = serviceManager.getServiceStatistics();
            monitoringData.put("statistics", statistics);

            // 설정 정보 수집
            Map<String, Object> configuration = serviceManager.getServiceConfiguration();
            monitoringData.put("configuration", configuration);

            logger.debug("서비스 상태 모니터링 완료");

        } catch (Exception e) {
            logger.error("서비스 상태 모니터링 에러", e);
        }
    }

    /**
     * 서비스 성능 모니터링 (1분마다 실행)
     */
    @Scheduled(fixedRate = 60000) // 1분 = 60,000ms
    public void monitorServicePerformance() {
        try {
            logger.debug("서비스 성능 모니터링 시작");

            Map<String, Object> statistics = serviceManager.getServiceStatistics();

            // 평균 처리 시간 확인
            Double avgProcessingTime = (Double) statistics.get("avgProcessingTime");
            if (avgProcessingTime != null && avgProcessingTime > 5000) { // 5초 이상
                String alert = "평균 처리 시간 초과 - " + avgProcessingTime + "ms - " + new Date();
                alertHistory.add(alert);
                logger.warn("성능 알림: {}", alert);
            }

            // 성공률 확인
            Double successRate = (Double) statistics.get("successRate");
            if (successRate != null && successRate < 95.0) { // 95% 미만
                String alert = "성공률 저하 - " + successRate + "% - " + new Date();
                alertHistory.add(alert);
                logger.warn("성능 알림: {}", alert);
            }

            // 에러 이벤트 수 확인
            Long errorEvents = (Long) statistics.get("errorEvents");
            if (errorEvents != null && errorEvents > 100) { // 100개 이상
                String alert = "에러 이벤트 수 증가 - " + errorEvents + "개 - " + new Date();
                alertHistory.add(alert);
                logger.warn("성능 알림: {}", alert);
            }

            monitoringData.put("performanceData", statistics);
            monitoringData.put("lastPerformanceCheck", new Date());

            logger.debug("서비스 성능 모니터링 완료");

        } catch (Exception e) {
            logger.error("서비스 성능 모니터링 에러", e);
        }
    }

    /**
     * 서비스 리소스 모니터링 (30초마다 실행)
     */
    @Scheduled(fixedRate = 30000) // 30초 = 30,000ms
    public void monitorServiceResources() {
        try {
            logger.debug("서비스 리소스 모니터링 시작");

            Runtime runtime = Runtime.getRuntime();

            // 메모리 사용량 확인
            long totalMemory = runtime.totalMemory();
            long freeMemory = runtime.freeMemory();
            long usedMemory = totalMemory - freeMemory;
            double memoryUsage = (double) usedMemory / totalMemory * 100;

            Map<String, Object> resourceData = new HashMap<>();
            resourceData.put("totalMemory", totalMemory);
            resourceData.put("freeMemory", freeMemory);
            resourceData.put("usedMemory", usedMemory);
            resourceData.put("memoryUsage", memoryUsage);
            resourceData.put("maxMemory", runtime.maxMemory());
            resourceData.put("availableProcessors", runtime.availableProcessors());

            // 메모리 사용량이 80% 이상이면 알림
            if (memoryUsage > 80.0) {
                String alert = "메모리 사용량 높음 - " + String.format("%.2f", memoryUsage) + "% - " + new Date();
                alertHistory.add(alert);
                logger.warn("리소스 알림: {}", alert);
            }

            monitoringData.put("resourceData", resourceData);
            monitoringData.put("lastResourceCheck", new Date());

            logger.debug("서비스 리소스 모니터링 완료");

        } catch (Exception e) {
            logger.error("서비스 리소스 모니터링 에러", e);
        }
    }

    /**
     * 모니터링 데이터 가져오기
     */
    public Map<String, Object> getMonitoringData() {
        try {
            return new HashMap<>(monitoringData);
        } catch (Exception e) {
            logger.error("모니터링 데이터 가져오기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 알림 히스토리 가져오기
     */
    public List<String> getAlertHistory() {
        try {
            return new ArrayList<>(alertHistory);
        } catch (Exception e) {
            logger.error("알림 히스토리 가져오기 에러", e);
            return new ArrayList<>();
        }
    }

    /**
     * 알림 히스토리 정리 (최근 100개만 유지)
     */
    public void cleanupAlertHistory() {
        try {
            if (alertHistory.size() > 100) {
                alertHistory.subList(0, alertHistory.size() - 100).clear();
                logger.info("알림 히스토리 정리 완료");
            }
        } catch (Exception e) {
            logger.error("알림 히스토리 정리 에러", e);
        }
    }

    /**
     * 서비스 상태 리포트 생성
     */
    public Map<String, Object> generateServiceReport() {
        try {
            Map<String, Object> report = new HashMap<>();

            // 기본 정보
            report.put("reportTime", new Date());
            report.put("serviceName", "TPM Service");
            report.put("version", "1.0.0");

            // 모니터링 데이터
            report.put("monitoringData", getMonitoringData());

            // 알림 히스토리
            report.put("alertHistory", getAlertHistory());

            // 서비스 통계
            Map<String, Object> statistics = serviceManager.getServiceStatistics();
            report.put("statistics", statistics);

            // 서비스 설정
            Map<String, Object> configuration = serviceManager.getServiceConfiguration();
            report.put("configuration", configuration);

            logger.info("서비스 상태 리포트 생성 완료");
            return report;

        } catch (Exception e) {
            logger.error("서비스 상태 리포트 생성 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 서비스 상태 요약 가져오기
     */
    public Map<String, Object> getServiceSummary() {
        try {
            Map<String, Object> summary = new HashMap<>();

            // 서비스 상태
            summary.put("serviceAvailable", monitoringData.get("serviceAvailable"));
            summary.put("lastCheckTime", monitoringData.get("lastCheckTime"));

            // 성능 요약
            Map<String, Object> performanceData = (Map<String, Object>) monitoringData.get("performanceData");
            if (performanceData != null) {
                summary.put("avgProcessingTime", performanceData.get("avgProcessingTime"));
                summary.put("successRate", performanceData.get("successRate"));
                summary.put("totalEvents", performanceData.get("totalEvents"));
            }

            // 리소스 요약
            Map<String, Object> resourceData = (Map<String, Object>) monitoringData.get("resourceData");
            if (resourceData != null) {
                summary.put("memoryUsage", resourceData.get("memoryUsage"));
                summary.put("usedMemory", resourceData.get("usedMemory"));
            }

            // 알림 개수
            summary.put("alertCount", alertHistory.size());

            return summary;

        } catch (Exception e) {
            logger.error("서비스 상태 요약 가져오기 에러", e);
            return new HashMap<>();
        }
    }
}