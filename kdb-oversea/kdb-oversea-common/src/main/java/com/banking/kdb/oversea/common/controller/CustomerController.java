package com.banking.kdb.oversea.common.controller;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.common.business.CustomerService;
import com.banking.kdb.oversea.common.model.Customer;
import com.banking.kdb.oversea.common.transfer.CustomerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Customer REST Controller for KDB Oversea Common
 * 
 * Provides REST API endpoints for customer management operations.
 */
@RestController
@RequestMapping("/api/v1/customers")
@Validated
@Tag(name = "Customer Management", description = "Customer operations and management")
public class CustomerController {

    private static final FoundationLogger logger = FoundationLogger.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    /**
     * Create a new customer
     */
    @PostMapping
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Create customer", description = "Create a new customer")
    public ResponseEntity<CustomerDTO> createCustomer(
            @Parameter(description = "Customer information", required = true) @Valid @RequestBody CustomerDTO customerDTO) {

        logger.info("Creating customer - Name: {}, Email: {}", customerDTO.getName(), customerDTO.getEmail());

        try {
            CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to create customer: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error creating customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get customer by customer ID
     */
    @GetMapping("/{customerId}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get customer", description = "Get customer information by customer ID")
    public ResponseEntity<CustomerDTO> getCustomer(
            @Parameter(description = "Customer ID", required = true) @PathVariable String customerId) {

        logger.debug("Getting customer - Customer ID: {}", customerId);

        try {
            Optional<CustomerDTO> customer = customerService.findCustomerById(customerId);
            return customer.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Unexpected error getting customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get customer by email
     */
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Get customer by email", description = "Get customer information by email address")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(
            @Parameter(description = "Email address", required = true) @PathVariable String email) {

        logger.debug("Getting customer by email - Email: {}", email);

        try {
            Optional<CustomerDTO> customer = customerService.findCustomerByEmail(email);
            return customer.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Unexpected error getting customer by email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get all customers
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all customers", description = "Get all customers with pagination")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {

        logger.debug("Getting all customers - Page: {}, Size: {}", page, size);

        try {
            List<CustomerDTO> customers = customerService.findAllCustomers(page, size);
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            logger.error("Unexpected error getting all customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Search customers
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Search customers", description = "Search customers by various criteria")
    public ResponseEntity<List<CustomerDTO>> searchCustomers(
            @Parameter(description = "Search term") @RequestParam(required = false) String searchTerm,
            @Parameter(description = "Customer type") @RequestParam(required = false) String customerType,
            @Parameter(description = "Status") @RequestParam(required = false) String status,
            @Parameter(description = "Risk level") @RequestParam(required = false) String riskLevel,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {

        logger.debug("Searching customers - Term: {}, Type: {}, Status: {}", searchTerm, customerType, status);

        try {
            List<CustomerDTO> customers = customerService.searchCustomers(searchTerm, customerType, status, riskLevel,
                    page, size);
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            logger.error("Unexpected error searching customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update customer
     */
    @PutMapping("/{customerId}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Update customer", description = "Update customer information")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @Parameter(description = "Customer ID", required = true) @PathVariable String customerId,
            @Parameter(description = "Updated customer information", required = true) @Valid @RequestBody CustomerDTO customerDTO) {

        logger.info("Updating customer - Customer ID: {}", customerId);

        try {
            CustomerDTO updatedCustomer = customerService.updateCustomer(customerId, customerDTO);
            return ResponseEntity.ok(updatedCustomer);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to update customer: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error updating customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Deactivate customer
     */
    @PutMapping("/{customerId}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deactivate customer", description = "Deactivate a customer")
    public ResponseEntity<Void> deactivateCustomer(
            @Parameter(description = "Customer ID", required = true) @PathVariable String customerId) {

        logger.info("Deactivating customer - Customer ID: {}", customerId);

        try {
            customerService.deactivateCustomer(customerId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            logger.error("Failed to deactivate customer: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error deactivating customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Activate customer
     */
    @PutMapping("/{customerId}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Activate customer", description = "Activate a customer")
    public ResponseEntity<Void> activateCustomer(
            @Parameter(description = "Customer ID", required = true) @PathVariable String customerId) {

        logger.info("Activating customer - Customer ID: {}", customerId);

        try {
            customerService.activateCustomer(customerId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            logger.error("Failed to activate customer: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error activating customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update KYC status
     */
    @PutMapping("/{customerId}/kyc")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update KYC status", description = "Update customer KYC status")
    public ResponseEntity<Void> updateKycStatus(
            @Parameter(description = "Customer ID", required = true) @PathVariable String customerId,
            @Parameter(description = "KYC status", required = true) @RequestParam String kycStatus) {

        logger.info("Updating KYC status - Customer ID: {}, KYC Status: {}", customerId, kycStatus);

        try {
            customerService.updateKycStatus(customerId, kycStatus);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            logger.error("Failed to update KYC status: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error updating KYC status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update risk level
     */
    @PutMapping("/{customerId}/risk-level")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update risk level", description = "Update customer risk level")
    public ResponseEntity<Void> updateRiskLevel(
            @Parameter(description = "Customer ID", required = true) @PathVariable String customerId,
            @Parameter(description = "Risk level", required = true) @RequestParam String riskLevel) {

        logger.info("Updating risk level - Customer ID: {}, Risk Level: {}", customerId, riskLevel);

        try {
            customerService.updateRiskLevel(customerId, riskLevel);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            logger.error("Failed to update risk level: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error updating risk level", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get customer statistics
     */
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get customer statistics", description = "Get customer statistics and metrics")
    public ResponseEntity<Object> getCustomerStatistics() {

        logger.debug("Getting customer statistics");

        try {
            Object statistics = customerService.getCustomerStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            logger.error("Unexpected error getting customer statistics", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if the customer service is running")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Customer service is running");
    }
}