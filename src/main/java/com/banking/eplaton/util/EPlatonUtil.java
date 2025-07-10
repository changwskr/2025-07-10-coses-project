package com.banking.eplaton.util;

import com.banking.eplaton.transfer.EPlatonEvent;
import com.banking.eplaton.transfer.EPlatonCommonDTO;
import com.banking.eplaton.transfer.TPSVCINFODTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * EPlaton Utility Class
 * 
 * Utility methods for EPlaton framework operations.
 */
public class EPlatonUtil {

    private static final Logger logger = LoggerFactory.getLogger(EPlatonUtil.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");

    /**
     * Create a new EPlaton event with default values
     */
    public static EPlatonEvent createEvent(String actionName, String systemName) {
        EPlatonEvent event = new EPlatonEvent(actionName, systemName);

        // Set default common values
        EPlatonCommonDTO common = event.getCommon();
        common.setBankCode("001");
        common.setBranchCode("001");
        common.setChannelType("03");
        common.setEventNo(generateEventNo());
        common.setSystemDate(LocalDateTime.now().format(DATE_FORMATTER));
        common.setUserId("SYSTEM");
        common.setIpAddress("127.0.0.1");
        common.setSessionId(UUID.randomUUID().toString());
        common.setTransactionId(UUID.randomUUID().toString());

        // Set default TPSVC values
        TPSVCINFODTO tpsvc = event.getTpsvcInfo();
        tpsvc.setSystemName(systemName);
        tpsvc.setActionName(actionName);
        tpsvc.setTpfq("200");
        tpsvc.setTxTimer("030");

        return event;
    }

    /**
     * Generate a unique event number
     */
    public static String generateEventNo() {
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DATE_FORMATTER);
        String time = now.format(TIME_FORMATTER);
        String random = String.format("%04d", (int) (Math.random() * 10000));
        return date + time + random;
    }

    /**
     * Create event data map with common fields
     */
    public static Map<String, Object> createEventData(String operation, Map<String, Object> data) {
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("operation", operation);
        eventData.put("timestamp", LocalDateTime.now());
        eventData.put("requestId", UUID.randomUUID().toString());

        if (data != null) {
            eventData.putAll(data);
        }

        return eventData;
    }

    /**
     * Validate EPlaton event
     */
    public static boolean isValidEvent(EPlatonEvent event) {
        if (event == null) {
            logger.error("Event is null");
            return false;
        }

        if (event.getCommon() == null) {
            logger.error("Event common data is null");
            return false;
        }

        if (event.getTpsvcInfo() == null) {
            logger.error("Event TPSVC info is null");
            return false;
        }

        if (event.getActionName() == null || event.getActionName().trim().isEmpty()) {
            logger.error("Event action name is null or empty");
            return false;
        }

        if (event.getSystemName() == null || event.getSystemName().trim().isEmpty()) {
            logger.error("Event system name is null or empty");
            return false;
        }

        return true;
    }

    /**
     * Log EPlaton event
     */
    public static void logEvent(EPlatonEvent event, String level) {
        if (event == null) {
            logger.warn("Cannot log null event");
            return;
        }

        String message = String.format("EPlaton Event [%s] - Action: %s, System: %s, Status: %s",
                event.getEventId(),
                event.getActionName(),
                event.getSystemName(),
                event.getCommon().getStatus());

        switch (level.toUpperCase()) {
            case "DEBUG":
                logger.debug(message);
                break;
            case "INFO":
                logger.info(message);
                break;
            case "WARN":
                logger.warn(message);
                break;
            case "ERROR":
                logger.error(message);
                break;
            default:
                logger.info(message);
        }
    }

    /**
     * Format error message
     */
    public static String formatErrorMessage(String errorCode, String message) {
        return String.format("[%s] %s", errorCode, message);
    }

    /**
     * Check if error code indicates an error
     */
    public static boolean isErrorCode(String errorCode) {
        if (errorCode == null || errorCode.trim().isEmpty()) {
            return false;
        }

        String code = errorCode.trim().toUpperCase();
        return code.startsWith("E") || code.startsWith("S") || code.equals("*");
    }

    /**
     * Get current system date in EPlaton format
     */
    public static String getCurrentSystemDate() {
        return LocalDateTime.now().format(DATE_FORMATTER);
    }

    /**
     * Get current system time in EPlaton format
     */
    public static String getCurrentSystemTime() {
        return LocalDateTime.now().format(TIME_FORMATTER);
    }
}