package com.banking.controller;

import com.banking.model.dto.ApiResponse;
import com.banking.model.dto.CustomerRequest;
import com.banking.model.dto.CustomerResponse;
import com.banking.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Customer REST Controller
 * 
 * Provides REST API endpoints for customer management operations.
 */
@RestController
@RequestMapping("/customers")
@Tag(name = "Customer Management", description = "Customer management APIs")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Create a new customer
     */
    @PostMapping
    @Operation(summary = "Create Customer", description = "Create a new customer")
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(@Valid @RequestBody CustomerRequest request) {
        try {
            CustomerResponse customer = customerService.createCustomer(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Customer created successfully", customer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Get customer by ID
     */
    @GetMapping("/{customerId}")
    @Operation(summary = "Get Customer", description = "Get customer by customer ID")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(@PathVariable String customerId) {
        try {
            CustomerResponse customer = customerService.getCustomer(customerId);
            return ResponseEntity.ok(ApiResponse.success(customer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Get customer by email
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "Get Customer by Email", description = "Get customer by email address")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerByEmail(@PathVariable String email) {
        try {
            CustomerResponse customer = customerService.getCustomerByEmail(email);
            return ResponseEntity.ok(ApiResponse.success(customer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Update customer
     */
    @PutMapping("/{customerId}")
    @Operation(summary = "Update Customer", description = "Update customer information")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable String customerId,
            @Valid @RequestBody CustomerRequest request) {
        try {
            CustomerResponse customer = customerService.updateCustomer(customerId, request);
            return ResponseEntity.ok(ApiResponse.success("Customer updated successfully", customer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Get all customers
     */
    @GetMapping
    @Operation(summary = "Get All Customers", description = "Get all customers")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers() {
        try {
            List<CustomerResponse> customers = customerService.getAllCustomers();
            return ResponseEntity.ok(ApiResponse.success(customers));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Get customers by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get Customers by Status", description = "Get customers by status")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getCustomersByStatus(@PathVariable String status) {
        try {
            List<CustomerResponse> customers = customerService.getCustomersByStatus(status);
            return ResponseEntity.ok(ApiResponse.success(customers));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Get customers by type
     */
    @GetMapping("/type/{customerType}")
    @Operation(summary = "Get Customers by Type", description = "Get customers by customer type")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getCustomersByType(@PathVariable String customerType) {
        try {
            List<CustomerResponse> customers = customerService.getCustomersByType(customerType);
            return ResponseEntity.ok(ApiResponse.success(customers));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Update KYC status
     */
    @PutMapping("/{customerId}/kyc")
    @Operation(summary = "Update KYC Status", description = "Update customer KYC status")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateKycStatus(
            @PathVariable String customerId,
            @RequestParam String kycStatus) {
        try {
            CustomerResponse customer = customerService.updateKycStatus(customerId, kycStatus);
            return ResponseEntity.ok(ApiResponse.success("KYC status updated successfully", customer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Deactivate customer
     */
    @DeleteMapping("/{customerId}")
    @Operation(summary = "Deactivate Customer", description = "Deactivate a customer")
    public ResponseEntity<ApiResponse<Void>> deactivateCustomer(@PathVariable String customerId) {
        try {
            customerService.deactivateCustomer(customerId);
            return ResponseEntity.ok(ApiResponse.success("Customer deactivated successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Search customers by name
     */
    @GetMapping("/search")
    @Operation(summary = "Search Customers", description = "Search customers by name")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> searchCustomers(@RequestParam String name) {
        try {
            List<CustomerResponse> customers = customerService.searchCustomersByName(name);
            return ResponseEntity.ok(ApiResponse.success(customers));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * Get customers with incomplete KYC
     */
    @GetMapping("/kyc/incomplete")
    @Operation(summary = "Get Incomplete KYC Customers", description = "Get customers with incomplete KYC")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getCustomersWithIncompleteKYC() {
        try {
            List<CustomerResponse> customers = customerService.getCustomersWithIncompleteKYC();
            return ResponseEntity.ok(ApiResponse.success(customers));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}