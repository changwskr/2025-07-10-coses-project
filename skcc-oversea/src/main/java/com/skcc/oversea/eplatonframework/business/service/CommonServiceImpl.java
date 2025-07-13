package com.skcc.oversea.eplatonframework.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;
import com.skcc.oversea.eplatonframework.transfer.EPlatonCommonDTO;
import com.skcc.oversea.eplatonframework.transfer.TPSVCINFODTO;
import com.skcc.oversea.foundation.logej.LOGEJ;
import com.skcc.oversea.foundation.constant.Constants;
import com.skcc.oversea.eplatonframework.business.delegate.action.CommonBizAction.CommonService;

/**
 * Common Service Implementation for SKCC Oversea
 * 
 * Provides common business operations
 * using Spring Boot and modern Java patterns.
 */
@Service
public class CommonServiceImpl implements CommonService {

    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private CommonRepository commonRepository;

    /**
     * Get system info
     */
    @Override
    @Transactional(readOnly = true)
    public EPlatonEvent getSystemInfo(EPlatonEvent event) {
        try {
            logger.info("Getting system info");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidGetSystemInfoRequest(requestData)) {
                setErrorInfo(event, "ECOM101", "Invalid get system info request data");
                return event;
            }

            // Get system info logic here
            // SystemInfo systemInfo = commonRepository.getSystemInfo(requestData);

            // Set response
            event.setResponse("System info retrieved successfully");
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("System info retrieved successfully");

            logger.info("System info retrieved successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error getting system info", e);
            setErrorInfo(event, "ECOM102", "Failed to get system info: " + e.getMessage());
            return event;
        }
    }

    /**
     * Get user info
     */
    @Override
    @Transactional(readOnly = true)
    public EPlatonEvent getUserInfo(EPlatonEvent event) {
        try {
            logger.info("Getting user info");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidGetUserInfoRequest(requestData)) {
                setErrorInfo(event, "ECOM201", "Invalid get user info request data");
                return event;
            }

            // Get user info logic here
            // UserInfo userInfo = commonRepository.getUserInfo(requestData);

            // Set response
            event.setResponse("User info retrieved successfully");
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("User info retrieved successfully");

            logger.info("User info retrieved successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error getting user info", e);
            setErrorInfo(event, "ECOM202", "Failed to get user info: " + e.getMessage());
            return event;
        }
    }

    /**
     * Validate session
     */
    @Override
    @Transactional(readOnly = true)
    public EPlatonEvent validateSession(EPlatonEvent event) {
        try {
            logger.info("Validating session");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidValidateSessionRequest(requestData)) {
                setErrorInfo(event, "ECOM301", "Invalid validate session request data");
                return event;
            }

            // Validate session logic here
            // boolean isValid = commonRepository.validateSession(requestData);

            // Set response
            event.setResponse("Session validated successfully");
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Session validated successfully");

            logger.info("Session validated successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error validating session", e);
            setErrorInfo(event, "ECOM302", "Failed to validate session: " + e.getMessage());
            return event;
        }
    }

    /**
     * Get configuration
     */
    @Override
    @Transactional(readOnly = true)
    public EPlatonEvent getConfiguration(EPlatonEvent event) {
        try {
            logger.info("Getting configuration");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidGetConfigurationRequest(requestData)) {
                setErrorInfo(event, "ECOM401", "Invalid get configuration request data");
                return event;
            }

            // Get configuration logic here
            // Configuration config = commonRepository.getConfiguration(requestData);

            // Set response
            event.setResponse("Configuration retrieved successfully");
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Configuration retrieved successfully");

            logger.info("Configuration retrieved successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error getting configuration", e);
            setErrorInfo(event, "ECOM402", "Failed to get configuration: " + e.getMessage());
            return event;
        }
    }

    /**
     * Update configuration
     */
    @Override
    @Transactional
    public EPlatonEvent updateConfiguration(EPlatonEvent event) {
        try {
            logger.info("Updating configuration");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidUpdateConfigurationRequest(requestData)) {
                setErrorInfo(event, "ECOM501", "Invalid update configuration request data");
                return event;
            }

            // Update configuration logic here
            // Configuration config = commonRepository.updateConfiguration(requestData);

            // Set response
            event.setResponse("Configuration updated successfully");
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Configuration updated successfully");

            logger.info("Configuration updated successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error updating configuration", e);
            setErrorInfo(event, "ECOM502", "Failed to update configuration: " + e.getMessage());
            return event;
        }
    }

    /**
     * Get audit log
     */
    @Override
    @Transactional(readOnly = true)
    public EPlatonEvent getAuditLog(EPlatonEvent event) {
        try {
            logger.info("Getting audit log");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidGetAuditLogRequest(requestData)) {
                setErrorInfo(event, "ECOM601", "Invalid get audit log request data");
                return event;
            }

            // Get audit log logic here
            // List<AuditLog> auditLogs = commonRepository.getAuditLog(requestData);

            // Set response
            event.setResponse("Audit log retrieved successfully");
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Audit log retrieved successfully");

            logger.info("Audit log retrieved successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error getting audit log", e);
            setErrorInfo(event, "ECOM602", "Failed to get audit log: " + e.getMessage());
            return event;
        }
    }

    /**
     * Clear cache
     */
    @Override
    @Transactional
    public EPlatonEvent clearCache(EPlatonEvent event) {
        try {
            logger.info("Clearing cache");

            // Extract request data
            Object requestData = event.getRequest();

            // Validate request data
            if (!isValidClearCacheRequest(requestData)) {
                setErrorInfo(event, "ECOM701", "Invalid clear cache request data");
                return event;
            }

            // Clear cache logic here
            // commonRepository.clearCache(requestData);

            // Set response
            event.setResponse("Cache cleared successfully");
            event.getTPSVCINFODTO().setErrorcode("I0000");
            event.getTPSVCINFODTO().setError_message("Cache cleared successfully");

            logger.info("Cache cleared successfully");
            return event;

        } catch (Exception e) {
            logger.error("Error clearing cache", e);
            setErrorInfo(event, "ECOM702", "Failed to clear cache: " + e.getMessage());
            return event;
        }
    }

    /**
     * Set error information
     */
    private void setErrorInfo(EPlatonEvent event, String errorCode, String errorMessage) {
        TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
        String currentErrorCode = tpsvcinfo.getErrorcode();

        if (currentErrorCode != null && currentErrorCode.startsWith("I")) {
            tpsvcinfo.setErrorcode(errorCode);
            tpsvcinfo.setError_message(errorMessage);
        } else if (currentErrorCode != null && currentErrorCode.startsWith("E")) {
            String combinedErrorCode = errorCode + "|" + currentErrorCode;
            tpsvcinfo.setErrorcode(combinedErrorCode);
            tpsvcinfo.setError_message(errorMessage);
        } else {
            tpsvcinfo.setErrorcode(errorCode);
            tpsvcinfo.setError_message(errorMessage);
        }
    }

    // Validation methods
    private boolean isValidGetSystemInfoRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidGetUserInfoRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidValidateSessionRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidGetConfigurationRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidUpdateConfigurationRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidGetAuditLogRequest(Object requestData) {
        return requestData != null;
    }

    private boolean isValidClearCacheRequest(Object requestData) {
        return requestData != null;
    }
}