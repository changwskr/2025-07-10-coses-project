package com.banking.kdb.oversea.common.repository;

import com.banking.kdb.oversea.common.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for CustomerRepository
 */
@DataJpaTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testSaveAndFindById() {
        // Given
        Customer customer = createTestCustomer();
        customer.setCustomerId("CUST123456");

        // When
        Customer savedCustomer = customerRepository.save(customer);
        Optional<Customer> foundCustomer = customerRepository.findById(savedCustomer.getId());

        // Then
        assertTrue(foundCustomer.isPresent());
        assertEquals("CUST123456", foundCustomer.get().getCustomerId());
        assertEquals("John Doe", foundCustomer.get().getName());
    }

    @Test
    void testFindByCustomerId() {
        // Given
        Customer customer = createTestCustomer();
        customer.setCustomerId("CUST789012");
        entityManager.persistAndFlush(customer);

        // When
        Optional<Customer> foundCustomer = customerRepository.findByCustomerId("CUST789012");

        // Then
        assertTrue(foundCustomer.isPresent());
        assertEquals("CUST789012", foundCustomer.get().getCustomerId());
    }

    @Test
    void testFindByEmail() {
        // Given
        Customer customer = createTestCustomer();
        customer.setEmail("test@example.com");
        entityManager.persistAndFlush(customer);

        // When
        Optional<Customer> foundCustomer = customerRepository.findByEmail("test@example.com");

        // Then
        assertTrue(foundCustomer.isPresent());
        assertEquals("test@example.com", foundCustomer.get().getEmail());
    }

    @Test
    void testFindByPassportNumber() {
        // Given
        Customer customer = createTestCustomer();
        customer.setPassportNumber("PASS123456");
        entityManager.persistAndFlush(customer);

        // When
        Optional<Customer> foundCustomer = customerRepository.findByPassportNumber("PASS123456");

        // Then
        assertTrue(foundCustomer.isPresent());
        assertEquals("PASS123456", foundCustomer.get().getPassportNumber());
    }

    @Test
    void testFindByStatus() {
        // Given
        Customer customer1 = createTestCustomer();
        customer1.setStatus("ACTIVE");
        Customer customer2 = createTestCustomer();
        customer2.setStatus("INACTIVE");
        entityManager.persistAndFlush(customer1);
        entityManager.persistAndFlush(customer2);

        // When
        List<Customer> activeCustomers = customerRepository.findByStatus("ACTIVE");

        // Then
        assertEquals(1, activeCustomers.size());
        assertEquals("ACTIVE", activeCustomers.get(0).getStatus());
    }

    @Test
    void testFindByNationality() {
        // Given
        Customer customer = createTestCustomer();
        customer.setNationality("Korean");
        entityManager.persistAndFlush(customer);

        // When
        List<Customer> koreanCustomers = customerRepository.findByNationality("Korean");

        // Then
        assertEquals(1, koreanCustomers.size());
        assertEquals("Korean", koreanCustomers.get(0).getNationality());
    }

    @Test
    void testFindByNameContainingIgnoreCase() {
        // Given
        Customer customer1 = createTestCustomer();
        customer1.setName("John Doe");
        Customer customer2 = createTestCustomer();
        customer2.setName("Jane Doe");
        entityManager.persistAndFlush(customer1);
        entityManager.persistAndFlush(customer2);

        // When
        List<Customer> doeCustomers = customerRepository.findByNameContainingIgnoreCase("doe");

        // Then
        assertEquals(2, doeCustomers.size());
    }

    @Test
    void testExistsByCustomerId() {
        // Given
        Customer customer = createTestCustomer();
        customer.setCustomerId("CUST999999");
        entityManager.persistAndFlush(customer);

        // When
        boolean exists = customerRepository.existsByCustomerId("CUST999999");
        boolean notExists = customerRepository.existsByCustomerId("NONEXISTENT");

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void testExistsByEmail() {
        // Given
        Customer customer = createTestCustomer();
        customer.setEmail("exists@example.com");
        entityManager.persistAndFlush(customer);

        // When
        boolean exists = customerRepository.existsByEmail("exists@example.com");
        boolean notExists = customerRepository.existsByEmail("nonexistent@example.com");

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void testExistsByPassportNumber() {
        // Given
        Customer customer = createTestCustomer();
        customer.setPassportNumber("PASS999999");
        entityManager.persistAndFlush(customer);

        // When
        boolean exists = customerRepository.existsByPassportNumber("PASS999999");
        boolean notExists = customerRepository.existsByPassportNumber("NONEXISTENT");

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void testFindBySearchCriteria() {
        // Given
        Customer customer = createTestCustomer();
        customer.setName("John Doe");
        customer.setNationality("American");
        customer.setStatus("ACTIVE");
        entityManager.persistAndFlush(customer);

        // When
        List<Customer> results = customerRepository.findBySearchCriteria("John", "American", "ACTIVE");

        // Then
        assertEquals(1, results.size());
        assertEquals("John Doe", results.get(0).getName());
    }

    @Test
    void testFindActiveCustomers() {
        // Given
        Customer customer1 = createTestCustomer();
        customer1.setStatus("ACTIVE");
        Customer customer2 = createTestCustomer();
        customer2.setStatus("INACTIVE");
        entityManager.persistAndFlush(customer1);
        entityManager.persistAndFlush(customer2);

        // When
        List<Customer> activeCustomers = customerRepository.findActiveCustomers();

        // Then
        assertEquals(1, activeCustomers.size());
        assertEquals("ACTIVE", activeCustomers.get(0).getStatus());
    }

    @Test
    void testCountByStatus() {
        // Given
        Customer customer1 = createTestCustomer();
        customer1.setStatus("ACTIVE");
        Customer customer2 = createTestCustomer();
        customer2.setStatus("ACTIVE");
        Customer customer3 = createTestCustomer();
        customer3.setStatus("INACTIVE");
        entityManager.persistAndFlush(customer1);
        entityManager.persistAndFlush(customer2);
        entityManager.persistAndFlush(customer3);

        // When
        long activeCount = customerRepository.countByStatus("ACTIVE");
        long inactiveCount = customerRepository.countByStatus("INACTIVE");

        // Then
        assertEquals(2, activeCount);
        assertEquals(1, inactiveCount);
    }

    @Test
    void testCountByNationality() {
        // Given
        Customer customer1 = createTestCustomer();
        customer1.setNationality("Korean");
        Customer customer2 = createTestCustomer();
        customer2.setNationality("Korean");
        Customer customer3 = createTestCustomer();
        customer3.setNationality("American");
        entityManager.persistAndFlush(customer1);
        entityManager.persistAndFlush(customer2);
        entityManager.persistAndFlush(customer3);

        // When
        long koreanCount = customerRepository.countByNationality("Korean");
        long americanCount = customerRepository.countByNationality("American");

        // Then
        assertEquals(2, koreanCount);
        assertEquals(1, americanCount);
    }

    private Customer createTestCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId("CUST" + System.currentTimeMillis());
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        customer.setPhone("+1234567890");
        customer.setAddress("123 Test St");
        customer.setCity("Test City");
        customer.setCountry("Test Country");
        customer.setNationality("Test Nationality");
        customer.setDateOfBirth(LocalDate.of(1990, 1, 1));
        customer.setStatus("ACTIVE");
        customer.setCustomerType("INDIVIDUAL");
        customer.setRiskLevel("LOW");
        customer.setKycStatus("PENDING");
        customer.setCreatedBy("test");
        customer.setCreatedDateTime(LocalDateTime.now());
        return customer;
    }
}