package com.banking.framework.business.actions;

import com.banking.framework.business.CosesBizAction;
import com.banking.framework.exception.CosesAppException;
import com.banking.framework.transfer.CosesCommonDTO;
import com.banking.framework.transfer.CosesEvent;
import com.banking.model.dto.CashCardRequest;
import com.banking.model.dto.CashCardResponse;
import com.banking.service.CashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * CashCard Create Business Action
 * 
 * Handles cash card creation business logic through the Coses framework.
 */
@Component
public class CashCardCreateAction extends CosesBizAction {

    @Autowired
    private CashCardService cashCardService;

    public CashCardCreateAction() {
        super("CashCardCreateAction", "CASH_CARD", "CREATION");
        setRequiresTransaction(true);
        setTimeoutSeconds(60);
    }

    @Override
    protected CosesCommonDTO executeBusinessLogic(CosesEvent event) throws CosesAppException {
        try {
            // Extract data from event
            Map<String, Object> eventData = event.getEventData();

            // Create CashCardRequest from event data
            CashCardRequest request = createCashCardRequest(eventData);

            // Execute business logic
            CashCardResponse response = cashCardService.createCashCard(request);

            // Create result DTO
            CosesCommonDTO result = new CosesCommonDTO();
            result.setTransactionId(event.getEventId());
            result.addEventData("cardNumber", response.getCardNumber());
            result.addEventData("customerId", response.getCustomerId());
            result.addEventData("balance", response.getBalance());
            result.addEventData("status", response.getStatus());

            return result;

        } catch (Exception e) {
            throw new CosesAppException("CASH_CARD_CREATE_ERROR",
                    "Failed to create cash card: " + e.getMessage(), e);
        }
    }

    @Override
    protected void validateBusinessParameters(CosesEvent event) throws CosesAppException {
        Map<String, Object> eventData = event.getEventData();

        if (eventData == null) {
            throw new CosesAppException("INVALID_PARAMETERS", "Event data is required");
        }

        if (!eventData.containsKey("customerId")) {
            throw new CosesAppException("INVALID_PARAMETERS", "Customer ID is required");
        }

        if (!eventData.containsKey("cardType")) {
            throw new CosesAppException("INVALID_PARAMETERS", "Card type is required");
        }

        if (!eventData.containsKey("initialBalance")) {
            throw new CosesAppException("INVALID_PARAMETERS", "Initial balance is required");
        }

        if (!eventData.containsKey("bankCode")) {
            throw new CosesAppException("INVALID_PARAMETERS", "Bank code is required");
        }

        if (!eventData.containsKey("branchCode")) {
            throw new CosesAppException("INVALID_PARAMETERS", "Branch code is required");
        }
    }

    private CashCardRequest createCashCardRequest(Map<String, Object> eventData) {
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

        return request;
    }
}