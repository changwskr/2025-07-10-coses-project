package com.banking.framework.business;

import com.banking.framework.action.AbstractAction;
import com.banking.framework.exception.CosesAppException;
import com.banking.framework.transfer.CosesCommonDTO;
import com.banking.framework.transfer.CosesEvent;
import org.springframework.stereotype.Component;

/**
 * Coses Business Action for Spring Boot Framework
 * 
 * Provides business logic execution capabilities with enhanced features
 * for the migrated Coses framework.
 */
@Component
public abstract class CosesBizAction extends AbstractAction {

    private String businessType;
    private String businessCategory;
    private boolean requiresTransaction;
    private int timeoutSeconds;

    // Constructors
    public CosesBizAction() {
        super();
        this.actionType = "BUSINESS";
        this.businessType = "DEFAULT";
        this.businessCategory = "GENERAL";
        this.requiresTransaction = false;
        this.timeoutSeconds = 30;
    }

    public CosesBizAction(String actionName, String businessType, String businessCategory) {
        super(actionName);
        this.actionType = "BUSINESS";
        this.businessType = businessType;
        this.businessCategory = businessCategory;
        this.requiresTransaction = false;
        this.timeoutSeconds = 30;
    }

    public CosesBizAction(String actionName, String businessType, String businessCategory,
            boolean requiresTransaction, int timeoutSeconds) {
        super(actionName);
        this.actionType = "BUSINESS";
        this.businessType = businessType;
        this.businessCategory = businessCategory;
        this.requiresTransaction = requiresTransaction;
        this.timeoutSeconds = timeoutSeconds;
    }

    @Override
    protected CosesCommonDTO doExecute(CosesEvent event) throws CosesAppException {
        logger.debug("Executing business action: {} (type: {}, category: {})",
                actionName, businessType, businessCategory);

        // Business-specific validation
        validateBusinessParameters(event);

        // Execute business logic
        CosesCommonDTO result = executeBusinessLogic(event);

        // Business-specific post-processing
        postProcessBusinessLogic(event, result);

        return result;
    }

    /**
     * Execute the main business logic - to be implemented by subclasses
     * 
     * @param event the event to process
     * @return execution result
     * @throws CosesAppException if execution fails
     */
    protected abstract CosesCommonDTO executeBusinessLogic(CosesEvent event) throws CosesAppException;

    /**
     * Validate business-specific parameters
     * 
     * @param event the event to validate
     * @throws CosesAppException if validation fails
     */
    protected void validateBusinessParameters(CosesEvent event) throws CosesAppException {
        // Default implementation - override if needed
        logger.debug("Validating business parameters for action: {}", actionName);
    }

    /**
     * Post-process business logic - override if needed
     * 
     * @param event  the original event
     * @param result the execution result
     * @throws CosesAppException if post-processing fails
     */
    protected void postProcessBusinessLogic(CosesEvent event, CosesCommonDTO result) throws CosesAppException {
        // Default implementation - override if needed
        logger.debug("Post-processing business logic for action: {}", actionName);
    }

    /**
     * Execute business logic with transaction support
     * 
     * @param event the event to process
     * @return execution result
     * @throws CosesAppException if execution fails
     */
    public CosesCommonDTO executeWithTransaction(CosesEvent event) throws CosesAppException {
        if (!requiresTransaction) {
            return execute(event);
        }

        logger.debug("Executing business action with transaction: {}", actionName);

        // TODO: Implement transaction management
        // This would typically use @Transactional annotation or programmatic
        // transaction management

        return execute(event);
    }

    /**
     * Execute business logic with timeout
     * 
     * @param event the event to process
     * @return execution result
     * @throws CosesAppException if execution fails or times out
     */
    public CosesCommonDTO executeWithTimeout(CosesEvent event) throws CosesAppException {
        logger.debug("Executing business action with timeout ({}s): {}", timeoutSeconds, actionName);

        // TODO: Implement timeout mechanism
        // This would typically use CompletableFuture with timeout

        return execute(event);
    }

    // Getters and Setters
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public boolean isRequiresTransaction() {
        return requiresTransaction;
    }

    public void setRequiresTransaction(boolean requiresTransaction) {
        this.requiresTransaction = requiresTransaction;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    @Override
    public String toString() {
        return "CosesBizAction{" +
                "actionName='" + actionName + '\'' +
                ", businessType='" + businessType + '\'' +
                ", businessCategory='" + businessCategory + '\'' +
                ", requiresTransaction=" + requiresTransaction +
                ", timeoutSeconds=" + timeoutSeconds +
                ", enabled=" + enabled +
                '}';
    }
}