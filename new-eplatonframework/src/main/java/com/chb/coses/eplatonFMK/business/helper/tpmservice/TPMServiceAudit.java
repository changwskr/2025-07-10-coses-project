package com.chb.coses.eplatonFMK.business.helper.tpmservice;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.transfer.TPSVCINFODTO;
import com.chb.coses.eplatonFMK.transfer.EPlatonCommonDTO;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TPM Service Audit
 * Spring 기반으로 전환된 TPM 서비스 감사 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class TPMServiceAudit {

    private static final Logger logger = LoggerFactory.getLogger(TPMServiceAudit.class);

    @Autowired
    private TPMServiceManager serviceManager;

    private final List<AuditLog> auditLogs = new ArrayList<>();
    private final Map<String, List<AuditLog>> userAuditLogs = new ConcurrentHashMap<>();
    private final Map<String, List<AuditLog>> systemAuditLogs = new ConcurrentHashMap<>();

    /**
     * 감사 로그 클래스
     */
    public static class AuditLog {
        private String id;
        private String userId;
        private String systemName;
        private String operationName;
        private String eventNo;
        private String action;
        private String status;
        private String errorCode;
        private String errorMessage;
        private long processingTime;
        private Date timestamp;
        private String ipAddress;
        private String sessionId;
        private Map<String, Object> additionalData;

        public AuditLog() {
            this.id = UUID.randomUUID().toString();
            this.timestamp = new Date();
            this.additionalData = new HashMap<>();
        }

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSystemName() {
            return systemName;
        }

        public void setSystemName(String systemName) {
            this.systemName = systemName;
        }

        public String getOperationName() {
            return operationName;
        }

        public void setOperationName(String operationName) {
            this.operationName = operationName;
        }

        public String getEventNo() {
            return eventNo;
        }

        public void setEventNo(String eventNo) {
            this.eventNo = eventNo;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public long getProcessingTime() {
            return processingTime;
        }

        public void setProcessingTime(long processingTime) {
            this.processingTime = processingTime;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public String getIpAddress() {
            return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public Map<String, Object> getAdditionalData() {
            return additionalData;
        }

        public void setAdditionalData(Map<String, Object> additionalData) {
            this.additionalData = additionalData;
        }

        @Override
        public String toString() {
            return "AuditLog{" +
                    "id='" + id + '\'' +
                    ", userId='" + userId + '\'' +
                    ", systemName='" + systemName + '\'' +
                    ", operationName='" + operationName + '\'' +
                    ", eventNo='" + eventNo + '\'' +
                    ", action='" + action + '\'' +
                    ", status='" + status + '\'' +
                    ", processingTime=" + processingTime +
                    ", timestamp=" + timestamp +
                    '}';
        }
    }

    /**
     * 감사 로그 기록
     */
    public void logAudit(EPlatonEvent event, String action, String status, long processingTime) {
        try {
            AuditLog auditLog = new AuditLog();

            // 기본 정보 설정
            TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
            EPlatonCommonDTO common = event.getCommon();

            auditLog.setUserId(common.getUserID());
            auditLog.setSystemName(tpsvcinfo.getSystem_name());
            auditLog.setOperationName(tpsvcinfo.getOperation_name());
            auditLog.setEventNo(common.getEventNo());
            auditLog.setAction(action);
            auditLog.setStatus(status);
            auditLog.setProcessingTime(processingTime);
            auditLog.setErrorCode(tpsvcinfo.getErrorcode());
            auditLog.setErrorMessage(tpsvcinfo.getError_message());
            auditLog.setIpAddress(common.getIPAddress());

            // 추가 데이터 설정
            Map<String, Object> additionalData = new HashMap<>();
            additionalData.put("terminalID", common.getTerminalID());
            additionalData.put("terminalType", common.getTerminalType());
            additionalData.put("bankCode", common.getBankCode());
            additionalData.put("branchCode", common.getBranchCode());
            additionalData.put("channelType", common.getChannelType());
            additionalData.put("nation", common.getNation());
            additionalData.put("regionCode", common.getRegionCode());
            additionalData.put("timeZone", common.getTimeZone());
            additionalData.put("systemDate", common.getSystemDate());
            additionalData.put("systemInTime", common.getSystemInTime());
            additionalData.put("systemOutTime", common.getSystemOutTime());
            additionalData.put("transactionNo", common.getTransactionNo());
            additionalData.put("baseCurrency", common.getBaseCurrency());
            additionalData.put("userLevel", common.getUserLevel());

            auditLog.setAdditionalData(additionalData);

            // 감사 로그 저장
            synchronized (auditLogs) {
                auditLogs.add(auditLog);
                // 최근 10000개만 유지
                if (auditLogs.size() > 10000) {
                    auditLogs.remove(0);
                }
            }

            // 사용자별 감사 로그 저장
            if (auditLog.getUserId() != null) {
                userAuditLogs.computeIfAbsent(auditLog.getUserId(), k -> new ArrayList<>()).add(auditLog);
            }

            // 시스템별 감사 로그 저장
            if (auditLog.getSystemName() != null) {
                systemAuditLogs.computeIfAbsent(auditLog.getSystemName(), k -> new ArrayList<>()).add(auditLog);
            }

            logger.debug("감사 로그 기록: {}", auditLog);

        } catch (Exception e) {
            logger.error("감사 로그 기록 에러", e);
        }
    }

    /**
     * 전체 감사 로그 가져오기
     */
    public List<AuditLog> getAllAuditLogs() {
        try {
            synchronized (auditLogs) {
                return new ArrayList<>(auditLogs);
            }
        } catch (Exception e) {
            logger.error("전체 감사 로그 가져오기 에러", e);
            return new ArrayList<>();
        }
    }

    /**
     * 사용자별 감사 로그 가져오기
     */
    public List<AuditLog> getUserAuditLogs(String userId) {
        try {
            return userAuditLogs.getOrDefault(userId, new ArrayList<>());
        } catch (Exception e) {
            logger.error("사용자별 감사 로그 가져오기 에러", e);
            return new ArrayList<>();
        }
    }

    /**
     * 시스템별 감사 로그 가져오기
     */
    public List<AuditLog> getSystemAuditLogs(String systemName) {
        try {
            return systemAuditLogs.getOrDefault(systemName, new ArrayList<>());
        } catch (Exception e) {
            logger.error("시스템별 감사 로그 가져오기 에러", e);
            return new ArrayList<>();
        }
    }

    /**
     * 기간별 감사 로그 가져오기
     */
    public List<AuditLog> getAuditLogsByPeriod(Date startDate, Date endDate) {
        try {
            List<AuditLog> result = new ArrayList<>();
            synchronized (auditLogs) {
                for (AuditLog log : auditLogs) {
                    if (log.getTimestamp().after(startDate) && log.getTimestamp().before(endDate)) {
                        result.add(log);
                    }
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("기간별 감사 로그 가져오기 에러", e);
            return new ArrayList<>();
        }
    }

    /**
     * 상태별 감사 로그 가져오기
     */
    public List<AuditLog> getAuditLogsByStatus(String status) {
        try {
            List<AuditLog> result = new ArrayList<>();
            synchronized (auditLogs) {
                for (AuditLog log : auditLogs) {
                    if (status.equals(log.getStatus())) {
                        result.add(log);
                    }
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("상태별 감사 로그 가져오기 에러", e);
            return new ArrayList<>();
        }
    }

    /**
     * 감사 로그 통계 가져오기
     */
    public Map<String, Object> getAuditStatistics() {
        try {
            Map<String, Object> statistics = new HashMap<>();

            synchronized (auditLogs) {
                statistics.put("totalLogs", auditLogs.size());

                // 상태별 통계
                Map<String, Long> statusStats = new HashMap<>();
                for (AuditLog log : auditLogs) {
                    statusStats.merge(log.getStatus(), 1L, Long::sum);
                }
                statistics.put("statusStatistics", statusStats);

                // 시스템별 통계
                Map<String, Long> systemStats = new HashMap<>();
                for (AuditLog log : auditLogs) {
                    if (log.getSystemName() != null) {
                        systemStats.merge(log.getSystemName(), 1L, Long::sum);
                    }
                }
                statistics.put("systemStatistics", systemStats);

                // 사용자별 통계
                Map<String, Long> userStats = new HashMap<>();
                for (AuditLog log : auditLogs) {
                    if (log.getUserId() != null) {
                        userStats.merge(log.getUserId(), 1L, Long::sum);
                    }
                }
                statistics.put("userStatistics", userStats);

                // 평균 처리 시간
                if (!auditLogs.isEmpty()) {
                    double avgProcessingTime = auditLogs.stream()
                            .mapToLong(AuditLog::getProcessingTime)
                            .average()
                            .orElse(0.0);
                    statistics.put("avgProcessingTime", avgProcessingTime);
                }
            }

            return statistics;

        } catch (Exception e) {
            logger.error("감사 로그 통계 가져오기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 감사 로그 내보내기
     */
    public Map<String, Object> exportAuditLogs() {
        try {
            Map<String, Object> export = new HashMap<>();

            // 전체 감사 로그
            export.put("auditLogs", getAllAuditLogs());

            // 통계 정보
            export.put("statistics", getAuditStatistics());

            // 내보내기 정보
            Map<String, Object> exportInfo = new HashMap<>();
            exportInfo.put("exportTime", new Date());
            exportInfo.put("totalLogs", auditLogs.size());
            exportInfo.put("format", "JSON");
            export.put("exportInfo", exportInfo);

            return export;

        } catch (Exception e) {
            logger.error("감사 로그 내보내기 에러", e);
            return new HashMap<>();
        }
    }

    /**
     * 감사 로그 정리
     */
    public void cleanupAuditLogs(int maxLogs) {
        try {
            synchronized (auditLogs) {
                if (auditLogs.size() > maxLogs) {
                    int removeCount = auditLogs.size() - maxLogs;
                    for (int i = 0; i < removeCount; i++) {
                        AuditLog removedLog = auditLogs.remove(0);

                        // 사용자별 로그에서도 제거
                        if (removedLog.getUserId() != null) {
                            List<AuditLog> userLogs = userAuditLogs.get(removedLog.getUserId());
                            if (userLogs != null) {
                                userLogs.remove(removedLog);
                            }
                        }

                        // 시스템별 로그에서도 제거
                        if (removedLog.getSystemName() != null) {
                            List<AuditLog> systemLogs = systemAuditLogs.get(removedLog.getSystemName());
                            if (systemLogs != null) {
                                systemLogs.remove(removedLog);
                            }
                        }
                    }
                    logger.info("감사 로그 정리 완료: {}개 제거", removeCount);
                }
            }
        } catch (Exception e) {
            logger.error("감사 로그 정리 에러", e);
        }
    }
}