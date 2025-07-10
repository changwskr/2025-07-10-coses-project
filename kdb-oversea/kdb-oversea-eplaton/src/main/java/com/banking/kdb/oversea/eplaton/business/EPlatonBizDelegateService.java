package com.banking.kdb.oversea.eplaton.business;

import com.banking.coses.framework.transfer.IEvent;
import com.banking.coses.framework.exception.BizDelegateException;
import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.eplaton.transfer.EPlatonEvent;
import com.banking.kdb.oversea.eplaton.transfer.EPlatonCommonDTO;
import com.banking.kdb.oversea.eplaton.transfer.TPSVCINFODTO;
import com.banking.kdb.oversea.foundation.config.KdbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;

/**
 * EPlaton Business Delegate Service for KDB Oversea
 * 
 * Handles business logic execution and transaction management
 * in Spring Boot environment.
 */
@Service
@Transactional
public class EPlatonBizDelegateService {

    private static final FoundationLogger logger = FoundationLogger.getLogger(EPlatonBizDelegateService.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private KdbConfig kdbConfig;

    private final ConcurrentHashMap<String, Class<?>> actionClassCache = new ConcurrentHashMap<>();

    /**
     * Execute business logic based on event
     */
    public IEvent execute(IEvent event) throws BizDelegateException {
        EPlatonEvent eplEvent = (EPlatonEvent) event;
        long startTime = System.currentTimeMillis();

        try {
            logger.info("Executing EPlaton event - Action: {}, User: {}",
                    eplEvent.getAction(), eplEvent.getSourceSystem());

            // Validate event
            validateEvent(eplEvent);

            // Process event
            IEvent resultEvent = processEvent(eplEvent);

            // Log execution time
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("Event executed successfully - Action: {}, Time: {}ms",
                    eplEvent.getAction(), executionTime);

            return resultEvent;

        } catch (Exception e) {
            logger.error("Failed to execute event - Action: {}, Error: {}",
                    eplEvent.getAction(), e.getMessage());

            // Set error information
            TPSVCINFODTO tpsvcInfo = eplEvent.getTPSVCINFODTO();
            tpsvcInfo.setErrorcode("EDEL001");
            tpsvcInfo.setError_message("Business delegate execution error: " + e.getMessage());

            throw new BizDelegateException("EDEL001", "Business delegate execution failed", e);
        }
    }

    /**
     * Validate EPlaton event
     */
    private void validateEvent(EPlatonEvent event) throws BizDelegateException {
        if (event == null) {
            throw new BizDelegateException("EVENT_NULL", "Event cannot be null");
        }

        TPSVCINFODTO tpsvcInfo = event.getTPSVCINFODTO();
        if (tpsvcInfo == null) {
            throw new BizDelegateException("TPSVCINFO_NULL", "TPSVCINFODTO cannot be null");
        }

        String actionName = tpsvcInfo.getAction_name();
        if (actionName == null || actionName.trim().isEmpty()) {
            throw new BizDelegateException("ACTION_NAME_NULL", "Action name cannot be null or empty");
        }

        // Check for existing errors
        if (isError(event)) {
            logger.warn("Event contains errors - ErrorCode: {}, ErrorMessage: {}",
                    tpsvcInfo.getErrorcode(), tpsvcInfo.getError_message());
        }
    }

    /**
     * Process EPlaton event
     */
    private IEvent processEvent(EPlatonEvent event) throws Exception {
        TPSVCINFODTO tpsvcInfo = event.getTPSVCINFODTO();
        String actionClassName = tpsvcInfo.getAction_name();

        // Get or create action instance
        EPlatonBizAction action = getActionInstance(actionClassName);

        // Execute action
        return action.act(event);
    }

    /**
     * Get action instance from class name
     */
    private EPlatonBizAction getActionInstance(String actionClassName) throws Exception {
        // Check cache first
        Class<?> actionClass = actionClassCache.get(actionClassName);

        if (actionClass == null) {
            // Load class
            actionClass = Class.forName(actionClassName);
            actionClassCache.put(actionClassName, actionClass);
        }

        // Try to get from Spring context first
        try {
            String beanName = getBeanNameFromClassName(actionClassName);
            if (applicationContext.containsBean(beanName)) {
                return (EPlatonBizAction) applicationContext.getBean(beanName);
            }
        } catch (Exception e) {
            logger.debug("Bean not found in Spring context, creating new instance: {}", actionClassName);
        }

        // Create new instance
        Constructor<?> constructor = actionClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        EPlatonBizAction action = (EPlatonBizAction) constructor.newInstance();

        // Inject dependencies if possible
        try {
            applicationContext.getAutowireCapableBeanFactory().autowireBean(action);
        } catch (Exception e) {
            logger.debug("Failed to autowire action: {}", e.getMessage());
        }

        return action;
    }

    /**
     * Get bean name from class name
     */
    private String getBeanNameFromClassName(String className) {
        String simpleName = className.substring(className.lastIndexOf('.') + 1);
        return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
    }

    /**
     * Check if event contains errors
     */
    private boolean isError(EPlatonEvent event) {
        String errorCode = event.getTPSVCINFODTO().getErrorcode();
        if (errorCode == null || errorCode.trim().isEmpty()) {
            return false;
        }

        char firstChar = errorCode.charAt(0);
        return firstChar == 'e' || firstChar == 's' || firstChar == 'E' || firstChar == 'S' || firstChar == '*';
    }

    /**
     * Clear action class cache
     */
    public void clearActionCache() {
        actionClassCache.clear();
        logger.info("Action class cache cleared");
    }

    /**
     * Get cache statistics
     */
    public int getCacheSize() {
        return actionClassCache.size();
    }

    /**
     * Check if development mode is enabled
     */
    public boolean isDevelopmentMode() {
        return kdbConfig.isDevelopmentMode();
    }
}