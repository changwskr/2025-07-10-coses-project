package com.banking.eplaton.business.actions;

import com.banking.eplaton.business.EPlatonBizAction;
import com.banking.eplaton.transfer.EPlatonEvent;
import com.banking.framework.exception.CosesAppException;
import com.banking.model.dto.CashCardRequest;
import com.banking.model.dto.CashCardResponse;
import com.banking.service.CashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * CashCard EPlaton Business Action
 * 
 * Handles cash card operations through the EPlaton framework.
 */
@Component
public class CashCardEPlatonAction extends EPlatonBizAction {

    @Autowired
    private CashCardService cashCardService;

    public CashCardEPlatonAction() {
        super("CashCardEPlatonAction", "CashCard");
        this.operationName = "com.banking.eplaton.business.operations.CashCardOperation";
        this.requiresTransaction = true;
        this.timeoutSeconds = 60;
    }

    @Override
    protected EPlatonEvent executeBusinessLogic(EPlatonEvent event) throws CosesAppException {
        try {
            Map<String, Object> eventData = getEventData(event);
            String operation = (String) eventData.get("operation");

            switch (operation) {
                case "CREATE":
                    return createCashCard(event);
                case "QUERY":
                    return queryCashCard(event);
                case "UPDATE":
                    return updateCashCard(event);
                case "DELETE":
                    return deleteCashCard(event);
                case "TRANSACTION":
                    return processTransaction(event);
                default:
                    throw new CosesAppException("INVALID_OPERATION",
                            "Unknown operation: " + operation);
            }

        } catch (Exception e) {
            throw new CosesAppException("CASH_CARD_EPLATON_ERROR",
                    "Error in CashCard EPlaton action: " + e.getMessage(), e);
        }
    }

    private EPlatonEvent createCashCard(EPlatonEvent event) throws CosesAppException {
        Map<String, Object> eventData = getEventData(event);

        CashCardRequest request = new CashCardRequest();
        request.setCustomerId((String) eventData.get("customerId"));
        request.setCardType((String) eventData.get("cardType"));

        Object balanceObj = eventData.get("initialBalance");
        if (balanceObj instanceof Number) {
            request.setInitialBalance(new BigDecimal(balanceObj.toString()));
        } else if (balanceObj instanceof String) {
            request.setInitialBalance(new BigDecimal((String) balanceObj));
        }

        request.setBankCode((String) eventData.get("bankCode"));
        request.setBranchCode((String) eventData.get("branchCode"));

        CashCardResponse response = cashCardService.createCashCard(request);

        // Set response data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("cardNumber", response.getCardNumber());
        responseData.put("customerId", response.getCustomerId());
        responseData.put("balance", response.getBalance());
        responseData.put("status", response.getStatus());
        responseData.put("cardType", response.getCardType());

        setEventData(event, responseData);

        return event;
    }

    private EPlatonEvent queryCashCard(EPlatonEvent event) throws CosesAppException {
        Map<String, Object> eventData = getEventData(event);
        String cardNumber = (String) eventData.get("cardNumber");

        CashCardResponse response = cashCardService.getCashCard(cardNumber);

        // Set response data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("cardNumber", response.getCardNumber());
        responseData.put("customerId", response.getCustomerId());
        responseData.put("balance", response.getBalance());
        responseData.put("status", response.getStatus());
        responseData.put("cardType", response.getCardType());
        responseData.put("createdAt", response.getCreatedAt());
        // Note: expiryDate not available in CashCardResponse

        setEventData(event, responseData);

        return event;
    }

    private EPlatonEvent updateCashCard(EPlatonEvent event) throws CosesAppException {
        Map<String, Object> eventData = getEventData(event);
        String cardNumber = (String) eventData.get("cardNumber");

        CashCardRequest request = new CashCardRequest();
        request.setCardType((String) eventData.get("cardType"));
        request.setBankCode((String) eventData.get("bankCode"));
        request.setBranchCode((String) eventData.get("branchCode"));

        CashCardResponse response = cashCardService.updateCashCard(cardNumber, request);

        // Set response data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("cardNumber", response.getCardNumber());
        responseData.put("status", response.getStatus());
        responseData.put("updatedAt", response.getUpdatedAt());

        setEventData(event, responseData);

        return event;
    }

    private EPlatonEvent deleteCashCard(EPlatonEvent event) throws CosesAppException {
        Map<String, Object> eventData = getEventData(event);
        String cardNumber = (String) eventData.get("cardNumber");

        cashCardService.deactivateCashCard(cardNumber);

        // Set response data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("cardNumber", cardNumber);
        responseData.put("status", "DEACTIVATED");

        setEventData(event, responseData);

        return event;
    }

    private EPlatonEvent processTransaction(EPlatonEvent event) throws CosesAppException {
        Map<String, Object> eventData = getEventData(event);

        // This would typically use a TransactionService
        // For now, we'll just return success
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("transactionId", eventData.get("transactionId"));
        responseData.put("status", "PROCESSED");
        responseData.put("amount", eventData.get("amount"));

        setEventData(event, responseData);

        return event;
    }
}