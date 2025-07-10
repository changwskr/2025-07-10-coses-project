package com.banking.kdb.oversea.common.business;

import com.banking.kdb.oversea.common.model.Customer;
import com.banking.kdb.oversea.common.repository.CustomerRepository;
import com.banking.kdb.oversea.common.transfer.CustomerDTO;
import com.banking.kdb.oversea.foundation.config.KdbConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CustomerService
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private KdbConfig kdbConfig;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;
    private CustomerDTO testCustomerDTO;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setCustomerId("CUST123456");
        testCustomer.setName("John Doe");
        testCustomer.setEmail("john.doe@example.com");
        testCustomer.setPhone("+1234567890");
        testCustomer.setAddress("123 Main St");
        testCustomer.setCity("New York");
        testCustomer.setCountry("USA");
        testCustomer.setNationality("American");
        testCustomer.setDateOfBirth(LocalDate.of(1990, 1, 1));
        testCustomer.setStatus("ACTIVE");
        testCustomer.setCustomerType("INDIVIDUAL");
        testCustomer.setRiskLevel("LOW");
        testCustomer.setKycStatus("PENDING");
        testCustomer.setCreatedBy("system");
        testCustomer.setCreatedDateTime(LocalDateTime.now());

        testCustomerDTO = new CustomerDTO();
        testCustomerDTO.setName("John Doe");
        testCustomerDTO.setEmail("john.doe@example.com");
        testCustomerDTO.setPhoneNumber("+1234567890");
        testCustomerDTO.setAddress("123 Main St");
        testCustomerDTO.setCity("New York");
        testCustomerDTO.setCountry("USA");
        testCustomerDTO.setNationality("American");
        testCustomerDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        testCustomerDTO.setCreatedBy("system");
    }

    @Test
    void testCreateCustomer_Success() {
        // Given
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // When
        CustomerDTO result = customerService.createCustomer(testCustomerDTO);

        // Then
        assertNotNull(result);
        assertEquals(testCustomer.getName(), result.getName());
        assertEquals(testCustomer.getEmail(), result.getEmail());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testFindCustomerById_Success() {
        // Given
        when(customerRepository.findByCustomerId("CUST123456")).thenReturn(Optional.of(testCustomer));

        // When
        Optional<CustomerDTO> result = customerService.findCustomerById("CUST123456");

        // Then
        assertTrue(result.isPresent());
        assertEquals(testCustomer.getCustomerId(), result.get().getCustomerId());
        verify(customerRepository).findByCustomerId("CUST123456");
    }

    @Test
    void testFindCustomerById_NotFound() {
        // Given
        when(customerRepository.findByCustomerId("NONEXISTENT")).thenReturn(Optional.empty());

        // When
        Optional<CustomerDTO> result = customerService.findCustomerById("NONEXISTENT");

        // Then
        assertFalse(result.isPresent());
        verify(customerRepository).findByCustomerId("NONEXISTENT");
    }

    @Test
    void testFindCustomerByEmail_Success() {
        // Given
        when(customerRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(testCustomer));

        // When
        Optional<CustomerDTO> result = customerService.findCustomerByEmail("john.doe@example.com");

        // Then
        assertTrue(result.isPresent());
        assertEquals(testCustomer.getEmail(), result.get().getEmail());
        verify(customerRepository).findByEmail("john.doe@example.com");
    }

    @Test
    void testFindAllCustomers_Success() {
        // Given
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findAll()).thenReturn(customers);

        // When
        List<CustomerDTO> result = customerService.findAllCustomers();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCustomer.getName(), result.get(0).getName());
        verify(customerRepository).findAll();
    }

    @Test
    void testUpdateCustomer_Success() {
        // Given
        CustomerDTO updateDTO = new CustomerDTO();
        updateDTO.setName("Jane Doe");
        updateDTO.setEmail("jane.doe@example.com");
        updateDTO.setModifiedBy("admin");

        when(customerRepository.findByCustomerId("CUST123456")).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // When
        CustomerDTO result = customerService.updateCustomer("CUST123456", updateDTO);

        // Then
        assertNotNull(result);
        verify(customerRepository).findByCustomerId("CUST123456");
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testUpdateCustomer_NotFound() {
        // Given
        when(customerRepository.findByCustomerId("NONEXISTENT")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            customerService.updateCustomer("NONEXISTENT", testCustomerDTO);
        });
        verify(customerRepository).findByCustomerId("NONEXISTENT");
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void testDeactivateCustomer_Success() {
        // Given
        when(customerRepository.findByCustomerId("CUST123456")).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // When
        customerService.deactivateCustomer("CUST123456");

        // Then
        verify(customerRepository).findByCustomerId("CUST123456");
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testActivateCustomer_Success() {
        // Given
        testCustomer.setStatus("INACTIVE");
        when(customerRepository.findByCustomerId("CUST123456")).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // When
        customerService.activateCustomer("CUST123456");

        // Then
        verify(customerRepository).findByCustomerId("CUST123456");
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testUpdateKycStatus_Success() {
        // Given
        when(customerRepository.findByCustomerId("CUST123456")).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // When
        customerService.updateKycStatus("CUST123456", "COMPLETED");

        // Then
        verify(customerRepository).findByCustomerId("CUST123456");
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testUpdateRiskLevel_Success() {
        // Given
        when(customerRepository.findByCustomerId("CUST123456")).thenReturn(Optional.of(testCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // When
        customerService.updateRiskLevel("CUST123456", "MEDIUM");

        // Then
        verify(customerRepository).findByCustomerId("CUST123456");
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testExistsByEmail_True() {
        // Given
        when(customerRepository.existsByEmail("john.doe@example.com")).thenReturn(true);

        // When
        boolean result = customerService.existsByEmail("john.doe@example.com");

        // Then
        assertTrue(result);
        verify(customerRepository).existsByEmail("john.doe@example.com");
    }

    @Test
    void testExistsByEmail_False() {
        // Given
        when(customerRepository.existsByEmail("nonexistent@example.com")).thenReturn(false);

        // When
        boolean result = customerService.existsByEmail("nonexistent@example.com");

        // Then
        assertFalse(result);
        verify(customerRepository).existsByEmail("nonexistent@example.com");
    }

    @Test
    void testExistsByCustomerId_True() {
        // Given
        when(customerRepository.existsByCustomerId("CUST123456")).thenReturn(true);

        // When
        boolean result = customerService.existsByCustomerId("CUST123456");

        // Then
        assertTrue(result);
        verify(customerRepository).existsByCustomerId("CUST123456");
    }

    @Test
    void testGetCustomerStatistics_Success() {
        // Given
        when(customerRepository.count()).thenReturn(100L);
        when(customerRepository.countByStatus("ACTIVE")).thenReturn(80L);
        when(customerRepository.countByStatus("INACTIVE")).thenReturn(20L);
        when(customerRepository.countByKycStatus("PENDING")).thenReturn(30L);
        when(customerRepository.countByKycStatus("COMPLETED")).thenReturn(70L);

        // When
        Object result = customerService.getCustomerStatistics();

        // Then
        assertNotNull(result);
        verify(customerRepository).count();
        verify(customerRepository).countByStatus("ACTIVE");
        verify(customerRepository).countByStatus("INACTIVE");
        verify(customerRepository).countByKycStatus("PENDING");
        verify(customerRepository).countByKycStatus("COMPLETED");
    }
}