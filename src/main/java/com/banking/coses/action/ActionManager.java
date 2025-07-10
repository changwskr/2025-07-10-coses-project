package com.banking.coses.action;

import com.banking.coses.transfer.CosesCommonDTO;
import com.banking.coses.transfer.CosesEvent;
import com.banking.coses.exception.CosesAppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Action Manager for COSES Framework
 * 
 * Manages registration and execution of COSES actions.
 * This replaces the legacy ActionManager with Spring Boot features.
 */
@Component
public class ActionManager {

    private static final Logger logger = LoggerFactory.getLogger(ActionManager.class);

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<String, AbstractAction> actions = new HashMap<>();
    private final Map<String, String> actionTypes = new HashMap<>();

    @PostConstruct
    public void initialize() {
        logger.info("Initializing ActionManager");
        registerSpringActions();
        logger.info("ActionManager initialized with {} actions", actions.size());
    }

    /**
     * Register an action
     */
    public void registerAction(String name, AbstractAction action) {
        if (name == null || name.trim().isEmpty()) {
            throw CosesAppException.invalidInput("Action name cannot be null or empty");
        }

        if (action == null) {
            throw CosesAppException.invalidInput("Action cannot be null");
        }

        actions.put(name, action);
        actionTypes.put(name, action.getActionType());

        logger.info("Registered action: {} of type: {}", name, action.getActionType());
    }

    /**
     * Get an action by name
     */
    public AbstractAction getAction(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw CosesAppException.invalidInput("Action name cannot be null or empty");
        }

        AbstractAction action = actions.get(name);

        if (action == null) {
            logger.warn("Action not found: {}", name);
            throw CosesAppException.notFound("Action not found: " + name);
        }

        return action;
    }

    /**
     * Check if action exists
     */
    public boolean hasAction(String name) {
        return name != null && actions.containsKey(name);
    }

    /**
     * Execute an action by name
     */
    public CosesCommonDTO executeAction(String actionName, CosesEvent event) throws Exception {
        logger.info("Executing action: {} for event: {}", actionName, event.getEventId());

        AbstractAction action = getAction(actionName);

        if (!action.isReady()) {
            throw CosesAppException.systemError("Action is not ready: " + actionName);
        }

        return action.execute(event);
    }

    /**
     * Get all registered action names
     */
    public Set<String> getActionNames() {
        return actions.keySet();
    }

    /**
     * Get action type
     */
    public String getActionType(String actionName) {
        return actionTypes.get(actionName);
    }

    /**
     * Get action count
     */
    public int getActionCount() {
        return actions.size();
    }

    /**
     * Get actions by type
     */
    public Map<String, AbstractAction> getActionsByType(String type) {
        Map<String, AbstractAction> typeActions = new HashMap<>();

        for (Map.Entry<String, AbstractAction> entry : actions.entrySet()) {
            if (type.equals(entry.getValue().getActionType())) {
                typeActions.put(entry.getKey(), entry.getValue());
            }
        }

        return typeActions;
    }

    /**
     * Remove an action
     */
    public void removeAction(String name) {
        if (name != null && actions.containsKey(name)) {
            actions.remove(name);
            actionTypes.remove(name);
            logger.info("Removed action: {}", name);
        }
    }

    /**
     * Clear all actions
     */
    public void clearActions() {
        actions.clear();
        actionTypes.clear();
        logger.info("Cleared all actions");
    }

    /**
     * Register Spring-managed actions
     */
    private void registerSpringActions() {
        try {
            // Find all AbstractAction beans
            Map<String, AbstractAction> actionBeans = applicationContext.getBeansOfType(AbstractAction.class);

            for (Map.Entry<String, AbstractAction> entry : actionBeans.entrySet()) {
                String beanName = entry.getKey();
                AbstractAction action = entry.getValue();

                // Register with bean name and class name
                registerAction(beanName, action);
                registerAction(action.getActionName(), action);

                logger.debug("Registered Spring action: {} -> {}", beanName, action.getActionName());
            }

        } catch (Exception e) {
            logger.error("Error registering Spring actions", e);
            throw CosesAppException.systemError("Failed to register Spring actions", e);
        }
    }

    /**
     * Get action statistics
     */
    public Map<String, Object> getActionStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalActions", actions.size());
        stats.put("actionNames", getActionNames());

        // Count by type
        Map<String, Integer> typeCounts = new HashMap<>();
        for (String type : actionTypes.values()) {
            typeCounts.put(type, typeCounts.getOrDefault(type, 0) + 1);
        }
        stats.put("actionsByType", typeCounts);

        return stats;
    }

    /**
     * Validate action manager
     */
    public boolean isValid() {
        return !actions.isEmpty();
    }
}