package com.banking.kdb.oversea.common.repository;

import com.banking.kdb.oversea.common.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Customer Repository for KDB Oversea
 * 
 * Provides data access methods for Customer entity.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

        /**
         * Find customer by customer ID
         */
        Optional<Customer> findByCustomerId(String customerId);

        /**
         * Find customer by passport number
         */
        Optional<Customer> findByPassportNumber(String passportNumber);

        /**
         * Find customers by status
         */
        List<Customer> findByStatus(String status);

        /**
         * Find customers by nationality
         */
        List<Customer> findByNationality(String nationality);

        /**
         * Find customers by name containing (case-insensitive)
         */
        List<Customer> findByNameContainingIgnoreCase(String name);

        /**
         * Find customers by email
         */
        Optional<Customer> findByEmail(String email);

        /**
         * Check if customer exists by customer ID
         */
        boolean existsByCustomerId(String customerId);

        /**
         * Check if customer exists by passport number
         */
        boolean existsByPassportNumber(String passportNumber);

        /**
         * Check if customer exists by email
         */
        boolean existsByEmail(String email);

        /**
         * Search customers by multiple criteria
         */
        @Query("SELECT c FROM Customer c WHERE " +
                        "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
                        "(:nationality IS NULL OR c.nationality = :nationality) AND " +
                        "(:status IS NULL OR c.status = :status)")
        List<Customer> findBySearchCriteria(@Param("name") String name,
                        @Param("nationality") String nationality,
                        @Param("status") String status);

        /**
         * Find active customers
         */
        @Query("SELECT c FROM Customer c WHERE c.status = 'ACTIVE'")
        List<Customer> findActiveCustomers();

        /**
         * Count customers by status
         */
        long countByStatus(String status);

        /**
         * Count customers by nationality
         */
        long countByNationality(String nationality);

        /**
         * Find all customers with pagination
         */
        @Query("SELECT c FROM Customer c ORDER BY c.createdDateTime DESC")
        List<Customer> findAllWithPagination(@Param("page") int page, @Param("size") int size);

        /**
         * Advanced search customers by multiple criteria with pagination
         */
        @Query("SELECT c FROM Customer c WHERE " +
                        "(:searchTerm IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                        "LOWER(c.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                        "LOWER(c.customerId) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
                        "(:customerType IS NULL OR c.customerType = :customerType) AND " +
                        "(:status IS NULL OR c.status = :status) AND " +
                        "(:riskLevel IS NULL OR c.riskLevel = :riskLevel) " +
                        "ORDER BY c.createdDateTime DESC")
        List<Customer> findByAdvancedSearchCriteria(@Param("searchTerm") String searchTerm,
                        @Param("customerType") String customerType,
                        @Param("status") String status,
                        @Param("riskLevel") String riskLevel,
                        @Param("page") int page,
                        @Param("size") int size);

        /**
         * Find customers by customer type
         */
        List<Customer> findByCustomerType(String customerType);

        /**
         * Find customers by risk level
         */
        List<Customer> findByRiskLevel(String riskLevel);

        /**
         * Find customers by KYC status
         */
        List<Customer> findByKycStatus(String kycStatus);

        /**
         * Count customers by customer type
         */
        long countByCustomerType(String customerType);

        /**
         * Count customers by risk level
         */
        long countByRiskLevel(String riskLevel);

        /**
         * Count customers by KYC status
         */
        long countByKycStatus(String kycStatus);

        /**
         * Find customers by phone number
         */
        Optional<Customer> findByPhoneNumber(String phoneNumber);

        /**
         * Check if customer exists by phone number
         */
        boolean existsByPhoneNumber(String phoneNumber);

        /**
         * Find customers by country
         */
        List<Customer> findByCountry(String country);

        /**
         * Count customers by country
         */
        long countByCountry(String country);

        /**
         * Find customers created in date range
         */
        @Query("SELECT c FROM Customer c WHERE c.createdDateTime BETWEEN :startDate AND :endDate")
        List<Customer> findByCreatedDateRange(@Param("startDate") java.time.LocalDateTime startDate,
                        @Param("endDate") java.time.LocalDateTime endDate);

        /**
         * Find customers by multiple nationalities
         */
        @Query("SELECT c FROM Customer c WHERE c.nationality IN :nationalities")
        List<Customer> findByNationalities(@Param("nationalities") List<String> nationalities);

        /**
         * Find customers with incomplete KYC
         */
        @Query("SELECT c FROM Customer c WHERE c.kycStatus IN ('PENDING', 'INCOMPLETE')")
        List<Customer> findCustomersWithIncompleteKyc();

        /**
         * Find high-risk customers
         */
        @Query("SELECT c FROM Customer c WHERE c.riskLevel IN ('HIGH', 'VERY_HIGH')")
        List<Customer> findHighRiskCustomers();

        /**
         * Get customer statistics summary
         */
        @Query("SELECT c.status, COUNT(c) FROM Customer c GROUP BY c.status")
        List<Object[]> getCustomerStatusStatistics();

        /**
         * Get customer type statistics
         */
        @Query("SELECT c.customerType, COUNT(c) FROM Customer c GROUP BY c.customerType")
        List<Object[]> getCustomerTypeStatistics();

        /**
         * Get risk level statistics
         */
        @Query("SELECT c.riskLevel, COUNT(c) FROM Customer c GROUP BY c.riskLevel")
        List<Object[]> getRiskLevelStatistics();

        /**
         * Get KYC status statistics
         */
        @Query("SELECT c.kycStatus, COUNT(c) FROM Customer c GROUP BY c.kycStatus")
        List<Object[]> getKycStatusStatistics();
}