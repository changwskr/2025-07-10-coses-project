package com.banking.neweplatonframework.business;

import com.banking.neweplatonframework.transfer.NewEPlatonEvent;
import com.banking.neweplatonframework.exception.NewEPlatonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

/**
 * New EPlaton Business Delegate Service
 * 
 * Handles business delegation and routing in the New EPlaton framework.
 * This replaces the legacy EPlatonBizDelegateSBBean with Spring Boot features.
 */
@Service
@Transactional
public class NewEPlatonBizDelegateService {

    private static final Logger logger = LoggerFactory.getLogger(NewEPlatonBizDelegateService.class);

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Execute business operation
     */
    public NewEPlatonEvent execute(NewEPlatonEvent event) throws NewEPlatonException {
        try {
            logger.info("New EPlaton Business Delegate execution started for event: {}", event.getEventId());

            // Validate event
            if (!validateEvent(event)) {
                throw NewEPlatonException.invalidEvent("Invalid event for business delegate");
            }

            // Get action class name
            String actionClassName = event.getTpsvcInfo().getActionName();
            if (actionClassName == null || actionClassName.trim().isEmpty()) {
                throw NewEPlatonException.invalidInput("Action name is required");
            }

            logger.info("Routing to action: {}", actionClassName);

            // Execute action
            NewEPlatonEvent result = executeAction(actionClassName, event);

            logger.info("New EPlaton Business Delegate execution completed for event: {}", event.getEventId());

            return result;

        } catch (Exception e) {
            logger.error("New EPlaton Business Delegate execution failed for event: {}", event.getEventId(), e);

            if (e instanceof NewEPlatonException) {
                throw (NewEPlatonException) e;
            } else {
                throw NewEPlatonException.systemError("Business delegate execution error: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Execute action by class name
     */
    private NewEPlatonEvent executeAction(String actionClassName, NewEPlatonEvent event) throws Exception {
        try {
            // Try to get action from Spring context first
            Object actionBean = getActionFromSpringContext(actionClassName);

            if (actionBean != null) {
                logger.debug("Found action bean in Spring context: {}", actionClassName);
                return executeSpringAction(actionBean, event);
            }

            // Fallback to reflection-based instantiation
            logger.debug("Creating action instance via reflection: {}", actionClassName);
            Object action = Class.forName(actionClassName).getDeclaredConstructor().newInstance();
            return executeReflectionAction(action, event);

        } catch (ClassNotFoundException e) {
            throw NewEPlatonException.notFound("Action class not found: " + actionClassName);
        } catch (InstantiationException | IllegalAccessException e) {
            throw NewEPlatonException.systemError("Failed to instantiate action: " + actionClassName, e);
        }
    }

    /**
     * Get action from Spring context
     */
    private Object getActionFromSpringContext(String actionClassName) {
        try {
            // Try to get by class name
            Class<?> actionClass = Class.forName(actionClassName);
            return applicationContext.getBean(actionClass);
        } catch (Exception e) {
            logger.debug("Action not found in Spring context: {}", actionClassName);
            return null;
        }
    }

    /**
     * Execute Spring-managed action
     */
    private NewEPlatonEvent executeSpringAction(Object actionBean, NewEPlatonEvent event) throws Exception {
        if (actionBean instanceof NewEPlatonBizAction) {
            return ((NewEPlatonBizAction) actionBean).act(event);
        } else {
            throw NewEPlatonException
                    .systemError("Action bean is not a NewEPlatonBizAction: " + actionBean.getClass().getName());
        }
    }

    /**
     * Execute reflection-based action
     */
    private NewEPlatonEvent executeReflectionAction(Object action, NewEPlatonEvent event) throws Exception {
        if (action instanceof NewEPlatonBizAction) {
            return ((NewEPlatonBizAction) action).act(event);
        } else {
            throw NewEPlatonException
                    .systemError("Action is not a NewEPlatonBizAction: " + action.getClass().getName());
        }
    }

    /**
     * Validate event
     */
    private boolean validateEvent(NewEPlatonEvent event) {
        if (event == null) {
            logger.error("Event is null");
            return false;
        }

        if (event.getTpsvcInfo() == null) {
            logger.error("TPSVC info is null");
            return false;
        }

        if (event.getCommon() == null) {
            logger.error("Common data is null");
            return false;
        }

        return true;
    }

    /**
     * Get available actions
     */
    public Map<String, Object> getAvailableActions() {
        Map<String, Object> actions = new HashMap<>();

        try {
            // Get all NewEPlatonBizAction beans
            Map<String, NewEPlatonBizAction> actionBeans = applicationContext.getBeansOfType(NewEPlatonBizAction.class);

            for (Map.Entry<String, NewEPlatonBizAction> entry : actionBeans.entrySet()) {
                String beanName = entry.getKey();
                NewEPlatonBizAction action = entry.getValue();

                Map<String, Object> actionInfo = new HashMap<>();
                actionInfo.put("beanName", beanName);
                actionInfo.put("className", action.getClass().getName());
                actionInfo.put("actionName", action.getActionName());
                actionInfo.put("actionType", action.getActionType());

                actions.put(beanName, actionInfo);
            }

        } catch (Exception e) {
            logger.error("Error getting available actions", e);
        }

        return actions;
    }

    /**
     * Get action statistics
     */
    public Map<String, Object> getActionStatistics() {
        Map<String, Object> stats = new HashMap<>();

        Map<String, Object> actions = getAvailableActions();
        stats.put("totalActions", actions.size());
        stats.put("actions", actions);
        stats.put("timestamp", LocalDateTime.now());

        return stats;
    }
}