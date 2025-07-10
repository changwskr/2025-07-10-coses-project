package com.banking.service.impl;

import com.banking.model.dto.CustomerRequest;
import com.banking.model.dto.CustomerResponse;
import com.banking.model.entity.Customer;
import com.banking.repository.CustomerRepository;
import com.banking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Customer Service Implementation
 * 
 * Provides business logic implementation for customer management operations.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        // Generate unique customer ID
        String customerId = generateCustomerId();

        // Check if email already exists
        if (request.getEmail() != null && customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Customer with email already exists: " + request.getEmail());
        }

        // Create customer entity
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setAddress(request.getAddress());
        customer.setBankCode(request.getBankCode());
        customer.setBranchCode(request.getBranchCode());
        customer.setCustomerType(request.getCustomerType() != null ? request.getCustomerType() : "INDIVIDUAL");
        customer.setKycStatus(request.getKycStatus() != null ? request.getKycStatus() : "PENDING");
        customer.setStatus("ACTIVE");

        // Save to database
        Customer savedCustomer = customerRepository.save(customer);

        return convertToResponse(savedCustomer);
    }

    @Override
    public CustomerResponse getCustomer(String customerId) {
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
        if (customer.isPresent()) {
            return convertToResponse(customer.get());
        }
        throw new RuntimeException("Customer not found: " + customerId);
    }

    @Override
    public CustomerResponse getCustomerByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            return convertToResponse(customer.get());
        }
        throw new RuntimeException("Customer not found with email: " + email);
    }

    @Override
    public CustomerResponse updateCustomer(String customerId, CustomerRequest request) {
        Optional<Customer> customerOpt = customerRepository.findByCustomerId(customerId);
        if (!customerOpt.isPresent()) {
            throw new RuntimeException("Customer not found: " + customerId);
        }

        Customer customer = customerOpt.get();

        // Update fields
        if (request.getFirstName() != null) {
            customer.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            customer.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            // Check if email is being changed and if new email already exists
            if (!request.getEmail().equals(customer.getEmail()) &&
                    customerRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Customer with email already exists: " + request.getEmail());
            }
            customer.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null) {
            customer.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getDateOfBirth() != null) {
            customer.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getAddress() != null) {
            customer.setAddress(request.getAddress());
        }
        if (request.getBankCode() != null) {
            customer.setBankCode(request.getBankCode());
        }
        if (request.getBranchCode() != null) {
            customer.setBranchCode(request.getBranchCode());
        }
        if (request.getCustomerType() != null) {
            customer.setCustomerType(request.getCustomerType());
        }

        Customer updatedCustomer = customerRepository.save(customer);
        return convertToResponse(updatedCustomer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponse> getCustomersByStatus(String status) {
        List<Customer> customers = customerRepository.findByStatus(status);
        return customers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponse> getCustomersByType(String customerType) {
        List<Customer> customers = customerRepository.findByCustomerType(customerType);
        return customers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse updateKycStatus(String customerId, String kycStatus) {
        Optional<Customer> customerOpt = customerRepository.findByCustomerId(customerId);
        if (!customerOpt.isPresent()) {
            throw new RuntimeException("Customer not found: " + customerId);
        }

        Customer customer = customerOpt.get();
        customer.setKycStatus(kycStatus);

        Customer updatedCustomer = customerRepository.save(customer);
        return convertToResponse(updatedCustomer);
    }

    @Override
    public void deactivateCustomer(String customerId) {
        Optional<Customer> customerOpt = customerRepository.findByCustomerId(customerId);
        if (!customerOpt.isPresent()) {
            throw new RuntimeException("Customer not found: " + customerId);
        }

        Customer customer = customerOpt.get();
        customer.setStatus("INACTIVE");
        customerRepository.save(customer);
    }

    @Override
    public List<CustomerResponse> searchCustomersByName(String name) {
        List<Customer> customers = customerRepository.findByFirstNameContainingIgnoreCase(name);
        customers.addAll(customerRepository.findByLastNameContainingIgnoreCase(name));

        return customers.stream()
                .distinct()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerResponse> getCustomersWithIncompleteKYC() {
        List<Customer> customers = customerRepository.findCustomersWithIncompleteKYC();
        return customers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Private helper methods

    private String generateCustomerId() {
        // Generate a unique customer ID
        String customerId;
        do {
            customerId = "CUST" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        } while (customerRepository.existsByCustomerId(customerId));

        return customerId;
    }

    private CustomerResponse convertToResponse(Customer customer) {
        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getDateOfBirth(),
                customer.getAddress(),
                customer.getStatus(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getBankCode(),
                customer.getBranchCode(),
                customer.getCustomerType(),
                customer.getKycStatus());
    }
}