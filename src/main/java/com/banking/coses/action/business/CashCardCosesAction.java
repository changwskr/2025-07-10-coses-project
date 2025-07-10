package com.banking.coses.action.business;

import com.banking.coses.action.AbstractAction;
import com.banking.coses.transfer.CosesCommonDTO;
import com.banking.coses.transfer.CosesEvent;
import com.banking.coses.exception.CosesAppException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * CashCard COSES Action
 * 
 * Sample business action for CashCard operations in the COSES framework.
 */
@Component
public class CashCardCosesAction extends AbstractAction {

    public CashCardCosesAction() {
        super("CashCardCosesAction", "BUSINESS");
    }

    @Override
    protected CosesCommonDTO executeAction(CosesEvent event) throws Exception {
        logger.info("Executing CashCard COSES action for event: {}", event.getEventId());

        // Get event data
        Object eventData = event.getEventData();

        if (!(eventData instanceof Map)) {
            throw CosesAppException.invalidInput("Event data must be a Map");
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) eventData;

        // Extract operation type
        String operation = (String) data.get("operation");
        if (operation == null) {
            throw CosesAppException.invalidInput("Operation is required");
        }

        // Execute based on operation
        CosesCommonDTO result = new CosesCommonDTO();

        switch (operation.toLowerCase()) {
            case "create":
                result = createCard(data);
                break;
            case "read":
                result = readCard(data);
                break;
            case "update":
                result = updateCard(data);
                break;
            case "delete":
                result = deleteCard(data);
                break;
            default:
                throw CosesAppException.invalidInput("Unknown operation: " + operation);
        }

        // Set transaction ID
        result.setTransactionId(event.getTransactionId());

        logger.info("CashCard COSES action completed for event: {}", event.getEventId());

        return result;
    }

    /**
     * Create card operation
     */
    private CosesCommonDTO createCard(Map<String, Object> data) {
        logger.debug("Creating card with data: {}", data);

        // Validate required fields
        String cardNumber = (String) data.get("cardNumber");
        String customerId = (String) data.get("customerId");

        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw CosesAppException.validationError("Card number is required");
        }

        if (customerId == null || customerId.trim().isEmpty()) {
            throw CosesAppException.validationError("Customer ID is required");
        }

        // Simulate card creation
        CosesCommonDTO result = new CosesCommonDTO();
        result.setSuccess();
        result.setTransactionId("TXN_" + System.currentTimeMillis());

        logger.info("Card created successfully: {}", cardNumber);

        return result;
    }

    /**
     * Read card operation
     */
    private CosesCommonDTO readCard(Map<String, Object> data) {
        logger.debug("Reading card with data: {}", data);

        // Validate required fields
        String cardNumber = (String) data.get("cardNumber");

        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw CosesAppException.validationError("Card number is required");
        }

        // Simulate card reading
        CosesCommonDTO result = new CosesCommonDTO();
        result.setSuccess();
        result.setTransactionId("TXN_" + System.currentTimeMillis());

        logger.info("Card read successfully: {}", cardNumber);

        return result;
    }

    /**
     * Update card operation
     */
    private CosesCommonDTO updateCard(Map<String, Object> data) {
        logger.debug("Updating card with data: {}", data);

        // Validate required fields
        String cardNumber = (String) data.get("cardNumber");

        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw CosesAppException.validationError("Card number is required");
        }

        // Simulate card update
        CosesCommonDTO result = new CosesCommonDTO();
        result.setSuccess();
        result.setTransactionId("TXN_" + System.currentTimeMillis());

        logger.info("Card updated successfully: {}", cardNumber);

        return result;
    }

    /**
     * Delete card operation
     */
    private CosesCommonDTO deleteCard(Map<String, Object> data) {
        logger.debug("Deleting card with data: {}", data);

        // Validate required fields
        String cardNumber = (String) data.get("cardNumber");

        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw CosesAppException.validationError("Card number is required");
        }

        // Simulate card deletion
        CosesCommonDTO result = new CosesCommonDTO();
        result.setSuccess();
        result.setTransactionId("TXN_" + System.currentTimeMillis());

        logger.info("Card deleted successfully: {}", cardNumber);

        return result;
    }
}