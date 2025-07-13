package com.chb.coses.eplatonFMK.business.helper.tpmservice;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TPM Service Metrics
 * Spring 기반으로 전환된 TPM 서비스 메트릭 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class TPMServiceMetrics {

    private static final Logger logger = LoggerFactory.getLogger(TPMServiceMetrics.class);

    @Autowired
    private TPMServiceManager serviceManager;

    private final AtomicLong totalRequests = new AtomicLong(0);
    private final AtomicLong successfulRequests = new AtomicLong(0);
    private final AtomicLong failedRequests = new AtomicLong(0);
    private final AtomicLong totalProcessingTime = new AtomicLong(0);
    private final Map<String, AtomicLong> systemMetrics = new ConcurrentHashMap<>();
    private final Map<String, AtomicLong> operationMetrics = new ConcurrentHashMap<>();
    private final List<Long> responseTimes = new ArrayList<>();

    /**
     * 요청 카운터 증가
     */
    public void incrementTotalRequests() {
        totalRequests.incrementAndGet();
    }

    /**
     * 성공 요청 카운터 증가
     */
    public void incrementSuccessfulRequests() {
        successfulRequests.incrementAndGet();
    }

    /**
     * 실패 요청 카운터 증가
     */
    public void incrementFailedRequests() {
        failedRequests.incrementAndGet();
    }

    /**
     * 처리 시간 추가
     */
    public void addProcessingTime(long processingTime) {
        totalProcessingTime.addAndGet(processingTime);
        synchronized (responseTimes) {
            responseTimes.add(processingTime);
            // 최근 1000개만 유지
            if (responseTimes.size() > 1000) {
                responseTimes.remove(0);
            }
        }
    }

    /**
     * 시스템별 메트릭 증가
     */
    public void incrementSystemMetric(String systemName) {
        systemMetrics.computeIfAbsent(systemName, k -> new AtomicLong(0)).incrementAndGet();
    }

    /**
     * 오퍼레이션별 메트릭 증가
     */
    public void incrementOperationMetric(String operationName) {
        operationMetrics.computeIfAbsent(operationName, k -> new AtomicLong(0)).incrementAndGet();
    }

    /**
     * 전체 메트릭 가져오기
     */
    public Map<String, Object> getAllMetrics() {
        try {
            Map<String, Object> metrics = new HashMap<>();

            // 기본 메트릭
            metrics.put("totalRequests", totalRequests.get());
            metrics.put("successfulRequests", successfulRequests.get());
            metrics.put("failedRequests", failedRequests.get());
            metrics.put("totalProcessingTime", totalProcessingTime.get());

            // 성공률 계산
            long total = totalRequests.get();
            if (total > 0) {
                double successRate = (double) successfulRequests.get() / total * 100;
                metrics.put("successRate", successRate);
            }

            // 평균 처리 시간 계산
            if (total > 0) {
                double avgProcessingTime = (double) totalProcessingTime.get() / total;
                metrics.put("avgProcessingTime", avgProcessingTime);
            }

            // 시스템별 메트릭
            Map<String, Long> systemMetricsMap = new HashMap<>();
            for (Map.Entry<String, AtomicLong> entry : systemMetrics.entrySet()) {
                systemMetricsMap.put(entry.getKey(), entry.getValue().get());
            }
            metrics.put("systemMetrics", systemMetricsMap);

            // 오퍼레이션별 메트릭
            Map<String, Long> operationMetricsMap = new HashMap<>();
            for (Map.Entry<String, AtomicLong> entry : operationMetrics.entrySet()) {
                operationMetricsMap.put(entry.getKey(), entry.getValue().get());
            }
            metrics.put("operationMetrics", operationMetricsMap);

            // 응답 시간 통계
            metrics.put("responseTimeStats", getResponseTimeStats());

            // 수집 시간
            metrics.put("collectionTime", new Date());

            return metrics;

        } catch (Exception e) {
            logger.error("전체 메트릭 가져오기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 응답 시간 통계 가져오기
     */
    public Map<String, Object> getResponseTimeStats() {
        try {
            Map<String, Object> stats = new HashMap<>();

            synchronized (responseTimes) {
                if (responseTimes.isEmpty()) {
                    stats.put("count", 0);
                    stats.put("min", 0);
                    stats.put("max", 0);
                    stats.put("avg", 0.0);
                    stats.put("median", 0.0);
                    return stats;
                }

                List<Long> sortedTimes = new ArrayList<>(responseTimes);
                Collections.sort(sortedTimes);

                stats.put("count", sortedTimes.size());
                stats.put("min", sortedTimes.get(0));
                stats.put("max", sortedTimes.get(sortedTimes.size() - 1));

                // 평균 계산
                double avg = sortedTimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
                stats.put("avg", avg);

                // 중간값 계산
                int size = sortedTimes.size();
                double median;
                if (size % 2 == 0) {
                    median = (sortedTimes.get(size / 2 - 1) + sortedTimes.get(size / 2)) / 2.0;
                } else {
                    median = sortedTimes.get(size / 2);
                }
                stats.put("median", median);

                // 95th percentile
                int index95 = (int) Math.ceil(size * 0.95) - 1;
                if (index95 >= 0 && index95 < size) {
                    stats.put("percentile95", sortedTimes.get(index95));
                }

                // 99th percentile
                int index99 = (int) Math.ceil(size * 0.99) - 1;
                if (index99 >= 0 && index99 < size) {
                    stats.put("percentile99", sortedTimes.get(index99));
                }
            }

            return stats;

        } catch (Exception e) {
            logger.error("응답 시간 통계 가져오기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 시스템별 메트릭 가져오기
     */
    public Map<String, Long> getSystemMetrics() {
        try {
            Map<String, Long> metrics = new HashMap<>();
            for (Map.Entry<String, AtomicLong> entry : systemMetrics.entrySet()) {
                metrics.put(entry.getKey(), entry.getValue().get());
            }
            return metrics;
        } catch (Exception e) {
            logger.error("시스템별 메트릭 가져오기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 오퍼레이션별 메트릭 가져오기
     */
    public Map<String, Long> getOperationMetrics() {
        try {
            Map<String, Long> metrics = new HashMap<>();
            for (Map.Entry<String, AtomicLong> entry : operationMetrics.entrySet()) {
                metrics.put(entry.getKey(), entry.getValue().get());
            }
            return metrics;
        } catch (Exception e) {
            logger.error("오퍼레이션별 메트릭 가져오기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 메트릭 리셋
     */
    public void resetMetrics() {
        try {
            totalRequests.set(0);
            successfulRequests.set(0);
            failedRequests.set(0);
            totalProcessingTime.set(0);
            systemMetrics.clear();
            operationMetrics.clear();

            synchronized (responseTimes) {
                responseTimes.clear();
            }

            logger.info("메트릭 리셋 완료");
        } catch (Exception e) {
            logger.error("메트릭 리셋 에러", e);
        }
    }

    /**
     * 메트릭 요약 가져오기
     */
    public Map<String, Object> getMetricsSummary() {
        try {
            Map<String, Object> summary = new HashMap<>();

            long total = totalRequests.get();
            long successful = successfulRequests.get();
            long failed = failedRequests.get();

            summary.put("totalRequests", total);
            summary.put("successfulRequests", successful);
            summary.put("failedRequests", failed);

            if (total > 0) {
                summary.put("successRate", (double) successful / total * 100);
                summary.put("avgProcessingTime", (double) totalProcessingTime.get() / total);
            }

            summary.put("systemCount", systemMetrics.size());
            summary.put("operationCount", operationMetrics.size());
            summary.put("responseTimeCount", responseTimes.size());

            return summary;

        } catch (Exception e) {
            logger.error("메트릭 요약 가져오기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 메트릭 내보내기
     */
    public Map<String, Object> exportMetrics() {
        try {
            Map<String, Object> export = new HashMap<>();

            // 전체 메트릭
            export.put("metrics", getAllMetrics());

            // 서비스 정보
            Map<String, Object> serviceInfo = new HashMap<>();
            serviceInfo.put("serviceName", "TPM Service");
            serviceInfo.put("version", "1.0.0");
            serviceInfo.put("exportTime", new Date());
            export.put("serviceInfo", serviceInfo);

            // 서비스 통계
            Map<String, Object> statistics = serviceManager.getServiceStatistics();
            export.put("statistics", statistics);

            return export;

        } catch (Exception e) {
            logger.error("메트릭 내보내기 에러", e);
            return new HashMap<>();
        }
    }
}