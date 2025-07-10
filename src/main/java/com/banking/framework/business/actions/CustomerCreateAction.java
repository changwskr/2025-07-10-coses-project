package com.banking.framework.business.actions;

import com.banking.framework.business.CosesBizAction;
import com.banking.framework.exception.CosesAppException;
import com.banking.framework.transfer.CosesCommonDTO;
import com.banking.framework.transfer.CosesEvent;
import com.banking.model.dto.CustomerRequest;
import com.banking.model.dto.CustomerResponse;
import com.banking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

/**
 * Customer Create Business Action
 * 
 * Handles customer creation business logic through the Coses framework.
 */
@Component
public class CustomerCreateAction extends CosesBizAction {

    @Autowired
    private CustomerService customerService;

    public CustomerCreateAction() {
        super("CustomerCreateAction", "CUSTOMER", "CREATION");
        setRequiresTransaction(true);
        setTimeoutSeconds(60);
    }

    @Override
    protected CosesCommonDTO executeBusinessLogic(CosesEvent event) throws CosesAppException {
        try {
            // Extract data from event
            Map<String, Object> eventData = event.getEventData();

            // Create CustomerRequest from event data
            CustomerRequest request = createCustomerRequest(eventData);

            // Execute business logic
            CustomerResponse response = customerService.createCustomer(request);

            // Create result DTO
            CosesCommonDTO result = new CosesCommonDTO();
            result.setTransactionId(event.getEventId());
            result.addEventData("customerId", response.getCustomerId());
            result.addEventData("fullName", response.getFullName());
            result.addEventData("email", response.getEmail());
            result.addEventData("status", response.getStatus());
            result.addEventData("kycStatus", response.getKycStatus());

            return result;

        } catch (Exception e) {
            throw new CosesAppException("CUSTOMER_CREATE_ERROR",
                    "Failed to create customer: " + e.getMessage(), e);
        }
    }

    @Override
    protected void validateBusinessParameters(CosesEvent event) throws CosesAppException {
        Map<String, Object> eventData = event.getEventData();

        if (eventData == null) {
            throw new CosesAppException("INVALID_PARAMETERS", "Event data is required");
        }

        if (!eventData.containsKey("firstName")) {
            throw new CosesAppException("INVALID_PARAMETERS", "First name is required");
        }

        if (!eventData.containsKey("lastName")) {
            throw new CosesAppException("INVALID_PARAMETERS", "Last name is required");
        }

        if (!eventData.containsKey("email")) {
            throw new CosesAppException("INVALID_PARAMETERS", "Email is required");
        }
    }

    private CustomerRequest createCustomerRequest(Map<String, Object> eventData) {
        CustomerRequest request = new CustomerRequest();

        request.setFirstName((String) eventData.get("firstName"));
        request.setLastName((String) eventData.get("lastName"));
        request.setEmail((String) eventData.get("email"));
        request.setPhoneNumber((String) eventData.get("phoneNumber"));
        request.setAddress((String) eventData.get("address"));
        request.setBankCode((String) eventData.get("bankCode"));
        request.setBranchCode((String) eventData.get("branchCode"));
        request.setCustomerType((String) eventData.get("customerType"));
        request.setKycStatus((String) eventData.get("kycStatus"));

        // Handle date of birth
        Object dobObj = eventData.get("dateOfBirth");
        if (dobObj instanceof String) {
            request.setDateOfBirth(LocalDate.parse((String) dobObj));
        } else if (dobObj instanceof LocalDate) {
            request.setDateOfBirth((LocalDate) dobObj);
        }

        return request;
    }
}