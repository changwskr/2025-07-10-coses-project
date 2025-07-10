package com.banking.kdb.oversea.common.business;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.common.model.Customer;
import com.banking.kdb.oversea.common.repository.CustomerRepository;
import com.banking.kdb.oversea.common.transfer.CustomerDTO;
import com.banking.kdb.oversea.foundation.config.KdbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Customer Service for KDB Oversea Common
 * 
 * Provides customer management functionality including
 * customer creation, updates, and queries.
 */
@Service
@Transactional
public class CustomerService {

    private static final FoundationLogger logger = FoundationLogger.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private KdbConfig kdbConfig;

    /**
     * Create a new customer
     */
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        logger.info("Creating customer - Name: {}, ID: {}", customerDTO.getName(), customerDTO.getCustomerId());

        Customer customer = new Customer();
        customer.setCustomerId(generateCustomerId());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        customer.setNationality(customerDTO.getNationality());
        customer.setPassportNumber(customerDTO.getPassportNumber());
        customer.setDateOfBirth(customerDTO.getDateOfBirth());
        customer.setStatus("ACTIVE");
        customer.setCreatedBy(customerDTO.getCreatedBy());
        customer.setCreatedDateTime(LocalDateTime.now());

        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO result = convertToDTO(savedCustomer);
        logger.info("Customer created successfully - Customer ID: {}", result.getCustomerId());

        return result;
    }

    /**
     * Find customer by ID
     */
    public Optional<CustomerDTO> findCustomerById(String customerId) {
        logger.debug("Finding customer by ID: {}", customerId);

        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
        return customer.map(this::convertToDTO);
    }

    /**
     * Find customer by passport number
     */
    public Optional<CustomerDTO> findCustomerByPassport(String passportNumber) {
        logger.debug("Finding customer by passport: {}", passportNumber);

        Optional<Customer> customer = customerRepository.findByPassportNumber(passportNumber);
        return customer.map(this::convertToDTO);
    }

    /**
     * Find all customers
     */
    public List<CustomerDTO> findAllCustomers() {
        logger.debug("Finding all customers");

        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Find customer by email
     */
    public Optional<CustomerDTO> findCustomerByEmail(String email) {
        logger.debug("Finding customer by email: {}", email);

        Optional<Customer> customer = customerRepository.findByEmail(email);
        return customer.map(this::convertToDTO);
    }

    /**
     * Find all customers with pagination
     */
    public List<CustomerDTO> findAllCustomers(int page, int size) {
        logger.debug("Finding all customers - Page: {}, Size: {}", page, size);

        List<Customer> customers = customerRepository.findAllWithPagination(page, size);
        return customers.stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Update customer information
     */
    public CustomerDTO updateCustomer(String customerId, CustomerDTO customerDTO) {
        logger.info("Updating customer - Customer ID: {}", customerId);

        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        if (existingCustomer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }

        Customer customer = existingCustomer.get();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        customer.setNationality(customerDTO.getNationality());
        customer.setPassportNumber(customerDTO.getPassportNumber());
        customer.setDateOfBirth(customerDTO.getDateOfBirth());
        customer.setModifiedBy(customerDTO.getModifiedBy());
        customer.setModifiedDateTime(LocalDateTime.now());

        Customer updatedCustomer = customerRepository.save(customer);

        CustomerDTO result = convertToDTO(updatedCustomer);
        logger.info("Customer updated successfully - Customer ID: {}", result.getCustomerId());

        return result;
    }

    /**
     * Deactivate customer
     */
    public void deactivateCustomer(String customerId, String modifiedBy) {
        logger.info("Deactivating customer - Customer ID: {}", customerId);

        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        if (existingCustomer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }

        Customer customer = existingCustomer.get();
        customer.setStatus("INACTIVE");
        customer.setModifiedBy(modifiedBy);
        customer.setModifiedDateTime(LocalDateTime.now());

        customerRepository.save(customer);
        logger.info("Customer deactivated successfully - Customer ID: {}", customerId);
    }

    /**
     * Activate customer
     */
    public void activateCustomer(String customerId) {
        logger.info("Activating customer - Customer ID: {}", customerId);

        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        if (existingCustomer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }

        Customer customer = existingCustomer.get();
        customer.setStatus("ACTIVE");
        customer.setModifiedDateTime(LocalDateTime.now());

        customerRepository.save(customer);
        logger.info("Customer activated successfully - Customer ID: {}", customerId);
    }

    /**
     * Deactivate customer
     */
    public void deactivateCustomer(String customerId) {
        logger.info("Deactivating customer - Customer ID: {}", customerId);

        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        if (existingCustomer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }

        Customer customer = existingCustomer.get();
        customer.setStatus("INACTIVE");
        customer.setModifiedDateTime(LocalDateTime.now());

        customerRepository.save(customer);
        logger.info("Customer deactivated successfully - Customer ID: {}", customerId);
    }

    /**
     * Update KYC status
     */
    public void updateKycStatus(String customerId, String kycStatus) {
        logger.info("Updating KYC status - Customer ID: {}, KYC Status: {}", customerId, kycStatus);

        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        if (existingCustomer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }

        Customer customer = existingCustomer.get();
        customer.setKycStatus(kycStatus);
        customer.setKycDate(LocalDateTime.now());
        customer.setModifiedDateTime(LocalDateTime.now());

        customerRepository.save(customer);
        logger.info("KYC status updated successfully - Customer ID: {}", customerId);
    }

    /**
     * Update risk level
     */
    public void updateRiskLevel(String customerId, String riskLevel) {
        logger.info("Updating risk level - Customer ID: {}, Risk Level: {}", customerId, riskLevel);

        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(customerId);
        if (existingCustomer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }

        Customer customer = existingCustomer.get();
        customer.setRiskLevel(riskLevel);
        customer.setModifiedDateTime(LocalDateTime.now());

        customerRepository.save(customer);
        logger.info("Risk level updated successfully - Customer ID: {}", customerId);
    }

    /**
     * Search customers by criteria
     */
    public List<CustomerDTO> searchCustomers(String name, String nationality, String status) {
        logger.debug("Searching customers - Name: {}, Nationality: {}, Status: {}", name, nationality, status);

        List<Customer> customers = customerRepository.findBySearchCriteria(name, nationality, status);
        return customers.stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Search customers by various criteria with pagination
     */
    public List<CustomerDTO> searchCustomers(String searchTerm, String customerType, String status, String riskLevel,
            int page, int size) {
        logger.debug("Searching customers - Term: {}, Type: {}, Status: {}, Risk: {}, Page: {}, Size: {}",
                searchTerm, customerType, status, riskLevel, page, size);

        List<Customer> customers = customerRepository.findByAdvancedSearchCriteria(searchTerm, customerType, status,
                riskLevel, page, size);
        return customers.stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Generate unique customer ID
     */
    private String generateCustomerId() {
        return "CUST" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Convert Customer entity to DTO
     */
    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setCustomerId(customer.getCustomerId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        dto.setNationality(customer.getNationality());
        dto.setPassportNumber(customer.getPassportNumber());
        dto.setDateOfBirth(customer.getDateOfBirth());
        dto.setStatus(customer.getStatus());
        dto.setCreatedBy(customer.getCreatedBy());
        dto.setCreatedDateTime(customer.getCreatedDateTime());
        dto.setModifiedBy(customer.getModifiedBy());
        dto.setModifiedDateTime(customer.getModifiedDateTime());
        return dto;
    }

    /**
     * Get customer statistics
     */
    public Object getCustomerStatistics() {
        logger.debug("Getting customer statistics");

        // This would typically return a complex object with various statistics
        // For now, returning a simple object structure
        return new Object() {
            public final long totalCustomers = customerRepository.count();
            public final long activeCustomers = customerRepository.countByStatus("ACTIVE");
            public final long inactiveCustomers = customerRepository.countByStatus("INACTIVE");
            public final long pendingKycCustomers = customerRepository.countByKycStatus("PENDING");
            public final long completedKycCustomers = customerRepository.countByKycStatus("COMPLETED");
        };
    }

    /**
     * Check if customer exists by email
     */
    public boolean existsByEmail(String email) {
        logger.debug("Checking if customer exists by email: {}", email);
        return customerRepository.existsByEmail(email);
    }

    /**
     * Check if customer exists by customer ID
     */
    public boolean existsByCustomerId(String customerId) {
        logger.debug("Checking if customer exists by customer ID: {}", customerId);
        return customerRepository.existsByCustomerId(customerId);
    }
}