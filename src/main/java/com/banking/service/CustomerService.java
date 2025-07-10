package com.banking.service;

import com.banking.model.dto.CustomerRequest;
import com.banking.model.dto.CustomerResponse;

import java.util.List;

/**
 * Customer Service Interface
 * 
 * Provides business operations for customer management including
 * customer registration, profile updates, and KYC processing.
 */
public interface CustomerService {

    /**
     * Create a new customer
     */
    CustomerResponse createCustomer(CustomerRequest request);

    /**
     * Get customer by customer ID
     */
    CustomerResponse getCustomer(String customerId);

    /**
     * Get customer by email
     */
    CustomerResponse getCustomerByEmail(String email);

    /**
     * Update customer information
     */
    CustomerResponse updateCustomer(String customerId, CustomerRequest request);

    /**
     * Get all customers
     */
    List<CustomerResponse> getAllCustomers();

    /**
     * Get customers by status
     */
    List<CustomerResponse> getCustomersByStatus(String status);

    /**
     * Get customers by customer type
     */
    List<CustomerResponse> getCustomersByType(String customerType);

    /**
     * Update KYC status
     */
    CustomerResponse updateKycStatus(String customerId, String kycStatus);

    /**
     * Deactivate customer
     */
    void deactivateCustomer(String customerId);

    /**
     * Search customers by name
     */
    List<CustomerResponse> searchCustomersByName(String name);

    /**
     * Get customers with incomplete KYC
     */
    List<CustomerResponse> getCustomersWithIncompleteKYC();
}