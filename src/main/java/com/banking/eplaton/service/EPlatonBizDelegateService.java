package com.banking.eplaton.service;

import com.banking.eplaton.transfer.EPlatonEvent;
import com.banking.eplaton.business.EPlatonBizAction;
import com.banking.framework.exception.CosesAppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * EPlaton Business Delegate Service
 * 
 * Service for executing EPlaton business actions.
 * This replaces the legacy EPlatonBizDelegateSBBean with Spring Boot features.
 */
@Service
@Transactional
public class EPlatonBizDelegateService {

    private static final Logger logger = LoggerFactory.getLogger(EPlatonBizDelegateService.class);

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<String, EPlatonBizAction> actionCache = new ConcurrentHashMap<>();

    /**
     * Execute an EPlaton event
     */
    public EPlatonEvent execute(EPlatonEvent event) throws CosesAppException {
        try {
            logger.info("Executing EPlaton event: {}", event.getEventId());

            // Validate event
            validateEvent(event);

            // Get action class name
            String actionClassName = getActionClassName(event);

            // Get or create action instance
            EPlatonBizAction action = getActionInstance(actionClassName);

            // Execute action
            EPlatonEvent result = action.act(event);

            logger.info("Completed EPlaton event: {}", event.getEventId());

            return result;

        } catch (Exception e) {
            logger.error("Error executing EPlaton event: {}", event.getEventId(), e);

            // Set error information
            event.setError("EDEL001", "CALL INITIAL ERROR: " + e.getMessage());

            throw new CosesAppException("EPLATON_DELEGATE_ERROR",
                    "Error executing EPlaton event: " + e.getMessage(), e);
        }
    }

    /**
     * Validate the event
     */
    private void validateEvent(EPlatonEvent event) throws CosesAppException {
        if (event == null) {
            throw new CosesAppException("INVALID_EVENT", "Event cannot be null");
        }

        if (event.getCommon() == null) {
            throw new CosesAppException("INVALID_EVENT", "Event common data cannot be null");
        }

        if (event.getTpsvcInfo() == null) {
            throw new CosesAppException("INVALID_EVENT", "Event TPSVC info cannot be null");
        }
    }

    /**
     * Get action class name from event
     */
    private String getActionClassName(EPlatonEvent event) throws CosesAppException {
        String actionClassName = event.getTpsvcInfo().getActionName();

        if (actionClassName == null || actionClassName.trim().isEmpty()) {
            actionClassName = event.getCommon().getReqName();
        }

        if (actionClassName == null || actionClassName.trim().isEmpty()) {
            throw new CosesAppException("INVALID_ACTION", "Action class name not found in event");
        }

        logger.debug("Action class name: {}", actionClassName);
        return actionClassName;
    }

    /**
     * Get action instance from cache or create new one
     */
    private EPlatonBizAction getActionInstance(String actionClassName) throws CosesAppException {
        // Check cache first
        EPlatonBizAction cachedAction = actionCache.get(actionClassName);
        if (cachedAction != null) {
            return cachedAction;
        }

        try {
            // Try to get from Spring context first
            Class<?> actionClass = Class.forName(actionClassName);

            // Check if it's a Spring component
            if (applicationContext.containsBean(actionClass.getSimpleName())) {
                EPlatonBizAction action = applicationContext.getBean(actionClass.getSimpleName(),
                        EPlatonBizAction.class);
                actionCache.put(actionClassName, action);
                return action;
            }

            // Create new instance if not in Spring context
            EPlatonBizAction action = (EPlatonBizAction) actionClass.newInstance();
            actionCache.put(actionClassName, action);
            return action;

        } catch (ClassNotFoundException e) {
            throw new CosesAppException("ACTION_NOT_FOUND",
                    "Action class not found: " + actionClassName, e);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CosesAppException("ACTION_INSTANTIATION_ERROR",
                    "Error creating action instance: " + actionClassName, e);
        } catch (ClassCastException e) {
            throw new CosesAppException("INVALID_ACTION_TYPE",
                    "Action class must extend EPlatonBizAction: " + actionClassName, e);
        }
    }

    /**
     * Clear action cache
     */
    public void clearActionCache() {
        actionCache.clear();
        logger.info("EPlaton action cache cleared");
    }

    /**
     * Get cache statistics
     */
    public Map<String, Object> getCacheStatistics() {
        Map<String, Object> stats = new ConcurrentHashMap<>();
        stats.put("cacheSize", actionCache.size());
        stats.put("cachedActions", actionCache.keySet());
        return stats;
    }
}