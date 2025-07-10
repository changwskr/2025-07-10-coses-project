package com.banking.repository;

import com.banking.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Customer Repository
 * 
 * JPA repository for Customer entity providing data access operations.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find customer by customer ID
     */
    Optional<Customer> findByCustomerId(String customerId);

    /**
     * Find customer by email
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Find customers by status
     */
    List<Customer> findByStatus(String status);

    /**
     * Find customers by customer type
     */
    List<Customer> findByCustomerType(String customerType);

    /**
     * Find customers by KYC status
     */
    List<Customer> findByKycStatus(String kycStatus);

    /**
     * Find customers by bank code and branch code
     */
    List<Customer> findByBankCodeAndBranchCode(String bankCode, String branchCode);

    /**
     * Find customers by first name and last name
     */
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Find customers by first name containing
     */
    List<Customer> findByFirstNameContainingIgnoreCase(String firstName);

    /**
     * Find customers by last name containing
     */
    List<Customer> findByLastNameContainingIgnoreCase(String lastName);

    /**
     * Find customers by phone number
     */
    List<Customer> findByPhoneNumber(String phoneNumber);

    /**
     * Check if customer ID exists
     */
    boolean existsByCustomerId(String customerId);

    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);

    /**
     * Count customers by status
     */
    long countByStatus(String status);

    /**
     * Count customers by customer type
     */
    long countByCustomerType(String customerType);

    /**
     * Count customers by KYC status
     */
    long countByKycStatus(String kycStatus);

    /**
     * Custom query to find customers created in the last N days
     */
    @Query("SELECT c FROM Customer c WHERE c.createdAt >= CURRENT_DATE - :days")
    List<Customer> findCustomersCreatedInLastDays(@Param("days") int days);

    /**
     * Custom query to find customers by age range
     */
    @Query("SELECT c FROM Customer c WHERE c.dateOfBirth BETWEEN :startDate AND :endDate")
    List<Customer> findCustomersByAgeRange(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Custom query to find customers with incomplete KYC
     */
    @Query("SELECT c FROM Customer c WHERE c.kycStatus IN ('PENDING', 'REJECTED')")
    List<Customer> findCustomersWithIncompleteKYC();

    /**
     * Custom query to find customers by address containing
     */
    @Query("SELECT c FROM Customer c WHERE c.address LIKE %:address%")
    List<Customer> findCustomersByAddressContaining(@Param("address") String address);

    /**
     * Custom query to find customers with multiple cards
     */
    @Query("SELECT c FROM Customer c WHERE c.customerId IN " +
            "(SELECT cc.customerId FROM CashCard cc GROUP BY cc.customerId HAVING COUNT(cc) > :cardCount)")
    List<Customer> findCustomersWithMultipleCards(@Param("cardCount") int cardCount);

    /**
     * Custom query to find customers by bank code
     */
    @Query("SELECT c FROM Customer c WHERE c.bankCode = :bankCode")
    List<Customer> findCustomersByBankCode(@Param("bankCode") String bankCode);

    /**
     * Custom query to find customers by branch code
     */
    @Query("SELECT c FROM Customer c WHERE c.branchCode = :branchCode")
    List<Customer> findCustomersByBranchCode(@Param("branchCode") String branchCode);
}