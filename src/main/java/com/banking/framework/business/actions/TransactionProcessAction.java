package com.banking.framework.business.actions;

import com.banking.framework.business.CosesBizAction;
import com.banking.framework.exception.CosesAppException;
import com.banking.framework.transfer.CosesCommonDTO;
import com.banking.framework.transfer.CosesEvent;
import com.banking.model.dto.TransactionRequest;
import com.banking.model.dto.TransactionResponse;
import com.banking.service.CashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Transaction Process Business Action
 * 
 * Handles transaction processing business logic through the Coses framework.
 */
@Component
public class TransactionProcessAction extends CosesBizAction {

    @Autowired
    private CashCardService cashCardService;

    public TransactionProcessAction() {
        super("TransactionProcessAction", "TRANSACTION", "PROCESSING");
        setRequiresTransaction(true);
        setTimeoutSeconds(120);
    }

    @Override
    protected CosesCommonDTO executeBusinessLogic(CosesEvent event) throws CosesAppException {
        try {
            // Extract data from event
            Map<String, Object> eventData = event.getEventData();

            // Create TransactionRequest from event data
            TransactionRequest request = createTransactionRequest(eventData);

            // Execute business logic
            TransactionResponse response = cashCardService.processTransaction(request);

            // Create result DTO
            CosesCommonDTO result = new CosesCommonDTO();
            result.setTransactionId(event.getEventId());
            result.addEventData("transactionId", response.getTransactionId());
            result.addEventData("cardNumber", response.getCardNumber());
            result.addEventData("transactionType", response.getTransactionType());
            result.addEventData("amount", response.getAmount());
            result.addEventData("balanceAfterTransaction", response.getBalanceAfterTransaction());
            result.addEventData("status", response.getStatus());

            return result;

        } catch (Exception e) {
            throw new CosesAppException("TRANSACTION_PROCESS_ERROR",
                    "Failed to process transaction: " + e.getMessage(), e);
        }
    }

    @Override
    protected void validateBusinessParameters(CosesEvent event) throws CosesAppException {
        Map<String, Object> eventData = event.getEventData();

        if (eventData == null) {
            throw new CosesAppException("INVALID_PARAMETERS", "Event data is required");
        }

        if (!eventData.containsKey("cardNumber")) {
            throw new CosesAppException("INVALID_PARAMETERS", "Card number is required");
        }

        if (!eventData.containsKey("transactionType")) {
            throw new CosesAppException("INVALID_PARAMETERS", "Transaction type is required");
        }

        if (!eventData.containsKey("amount")) {
            throw new CosesAppException("INVALID_PARAMETERS", "Amount is required");
        }

        if (!eventData.containsKey("description")) {
            throw new CosesAppException("INVALID_PARAMETERS", "Description is required");
        }

        // Validate transaction type
        String transactionType = (String) eventData.get("transactionType");
        if (!isValidTransactionType(transactionType)) {
            throw new CosesAppException("INVALID_PARAMETERS",
                    "Invalid transaction type: " + transactionType);
        }

        // Validate amount
        Object amountObj = eventData.get("amount");
        if (amountObj == null) {
            throw new CosesAppException("INVALID_PARAMETERS", "Amount cannot be null");
        }

        BigDecimal amount;
        try {
            if (amountObj instanceof Number) {
                amount = new BigDecimal(amountObj.toString());
            } else if (amountObj instanceof String) {
                amount = new BigDecimal((String) amountObj);
            } else {
                throw new CosesAppException("INVALID_PARAMETERS", "Invalid amount format");
            }

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new CosesAppException("INVALID_PARAMETERS", "Amount must be positive");
            }
        } catch (NumberFormatException e) {
            throw new CosesAppException("INVALID_PARAMETERS", "Invalid amount format");
        }
    }

    private TransactionRequest createTransactionRequest(Map<String, Object> eventData) {
        TransactionRequest request = new TransactionRequest();

        request.setCardNumber((String) eventData.get("cardNumber"));
        request.setTransactionType((String) eventData.get("transactionType"));
        request.setDescription((String) eventData.get("description"));
        request.setTargetCardNumber((String) eventData.get("targetCardNumber"));

        // Handle amount
        Object amountObj = eventData.get("amount");
        if (amountObj instanceof Number) {
            request.setAmount(new BigDecimal(amountObj.toString()));
        } else if (amountObj instanceof String) {
            request.setAmount(new BigDecimal((String) amountObj));
        }

        return request;
    }

    private boolean isValidTransactionType(String transactionType) {
        if (transactionType == null) {
            return false;
        }

        String upperType = transactionType.toUpperCase();
        return "DEPOSIT".equals(upperType) ||
                "WITHDRAWAL".equals(upperType) ||
                "TRANSFER".equals(upperType);
    }
}