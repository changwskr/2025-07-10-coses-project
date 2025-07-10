package com.banking.framework.action;

import com.banking.framework.exception.CosesAppException;
import com.banking.framework.transfer.CosesCommonDTO;
import com.banking.framework.transfer.CosesEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Action Manager for Coses Framework (Spring Boot Version)
 * 
 * Manages the lifecycle and execution of framework actions.
 */
@Service
public class ActionManager {

    private static final Logger logger = LoggerFactory.getLogger(ActionManager.class);

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<String, AbstractAction> actions = new ConcurrentHashMap<>();
    private final Map<String, String> actionTypeMapping = new HashMap<>();

    @PostConstruct
    public void initialize() {
        logger.info("Initializing ActionManager...");

        // Register all AbstractAction beans
        Map<String, AbstractAction> actionBeans = applicationContext.getBeansOfType(AbstractAction.class);

        for (Map.Entry<String, AbstractAction> entry : actionBeans.entrySet()) {
            AbstractAction action = entry.getValue();
            String actionName = action.getActionName();

            actions.put(actionName, action);
            actionTypeMapping.put(action.getActionType(), actionName);

            logger.info("Registered action: {} (type: {})", actionName, action.getActionType());
        }

        logger.info("ActionManager initialized with {} actions", actions.size());
    }

    /**
     * Execute an action by name
     * 
     * @param actionName the name of the action to execute
     * @param event      the event to process
     * @return execution result
     * @throws CosesAppException if execution fails
     */
    public CosesCommonDTO executeAction(String actionName, CosesEvent event) throws CosesAppException {
        AbstractAction action = getAction(actionName);
        if (action == null) {
            throw new CosesAppException("ACTION_NOT_FOUND",
                    "Action not found: " + actionName,
                    org.springframework.http.HttpStatus.NOT_FOUND);
        }

        logger.debug("Executing action: {}", actionName);
        return action.execute(event);
    }

    /**
     * Execute an action by type
     * 
     * @param actionType the type of the action to execute
     * @param event      the event to process
     * @return execution result
     * @throws CosesAppException if execution fails
     */
    public CosesCommonDTO executeActionByType(String actionType, CosesEvent event) throws CosesAppException {
        String actionName = actionTypeMapping.get(actionType);
        if (actionName == null) {
            throw new CosesAppException("ACTION_TYPE_NOT_FOUND",
                    "Action type not found: " + actionType,
                    org.springframework.http.HttpStatus.NOT_FOUND);
        }

        return executeAction(actionName, event);
    }

    /**
     * Get an action by name
     * 
     * @param actionName the name of the action
     * @return the action, or null if not found
     */
    public AbstractAction getAction(String actionName) {
        return actions.get(actionName);
    }

    /**
     * Get all registered actions
     * 
     * @return map of action names to actions
     */
    public Map<String, AbstractAction> getAllActions() {
        return new HashMap<>(actions);
    }

    /**
     * Get actions by type
     * 
     * @param actionType the action type
     * @return map of action names to actions for the given type
     */
    public Map<String, AbstractAction> getActionsByType(String actionType) {
        Map<String, AbstractAction> result = new HashMap<>();

        for (Map.Entry<String, AbstractAction> entry : actions.entrySet()) {
            if (actionType.equals(entry.getValue().getActionType())) {
                result.put(entry.getKey(), entry.getValue());
            }
        }

        return result;
    }

    /**
     * Register a new action
     * 
     * @param action the action to register
     * @throws CosesAppException if action with same name already exists
     */
    public void registerAction(AbstractAction action) throws CosesAppException {
        String actionName = action.getActionName();

        if (actions.containsKey(actionName)) {
            throw new CosesAppException("ACTION_ALREADY_EXISTS",
                    "Action already exists: " + actionName,
                    org.springframework.http.HttpStatus.CONFLICT);
        }

        actions.put(actionName, action);
        actionTypeMapping.put(action.getActionType(), actionName);

        logger.info("Registered new action: {} (type: {})", actionName, action.getActionType());
    }

    /**
     * Unregister an action
     * 
     * @param actionName the name of the action to unregister
     * @return true if action was unregistered, false if not found
     */
    public boolean unregisterAction(String actionName) {
        AbstractAction action = actions.remove(actionName);
        if (action != null) {
            actionTypeMapping.remove(action.getActionType());
            logger.info("Unregistered action: {}", actionName);
            return true;
        }
        return false;
    }

    /**
     * Enable an action
     * 
     * @param actionName the name of the action to enable
     * @throws CosesAppException if action not found
     */
    public void enableAction(String actionName) throws CosesAppException {
        AbstractAction action = getAction(actionName);
        if (action == null) {
            throw new CosesAppException("ACTION_NOT_FOUND",
                    "Action not found: " + actionName,
                    org.springframework.http.HttpStatus.NOT_FOUND);
        }

        action.enable();
        logger.info("Enabled action: {}", actionName);
    }

    /**
     * Disable an action
     * 
     * @param actionName the name of the action to disable
     * @throws CosesAppException if action not found
     */
    public void disableAction(String actionName) throws CosesAppException {
        AbstractAction action = getAction(actionName);
        if (action == null) {
            throw new CosesAppException("ACTION_NOT_FOUND",
                    "Action not found: " + actionName,
                    org.springframework.http.HttpStatus.NOT_FOUND);
        }

        action.disable();
        logger.info("Disabled action: {}", actionName);
    }

    /**
     * Check if an action exists
     * 
     * @param actionName the name of the action
     * @return true if action exists, false otherwise
     */
    public boolean hasAction(String actionName) {
        return actions.containsKey(actionName);
    }

    /**
     * Get the number of registered actions
     * 
     * @return the number of actions
     */
    public int getActionCount() {
        return actions.size();
    }

    /**
     * Get action statistics
     * 
     * @return map containing action statistics
     */
    public Map<String, Object> getActionStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalActions", actions.size());
        stats.put("enabledActions", actions.values().stream().filter(AbstractAction::isEnabled).count());
        stats.put("disabledActions", actions.values().stream().filter(a -> !a.isEnabled()).count());

        Map<String, Long> typeStats = new HashMap<>();
        actions.values().stream()
                .map(AbstractAction::getActionType)
                .forEach(type -> typeStats.merge(type, 1L, Long::sum));
        stats.put("actionsByType", typeStats);

        return stats;
    }
}