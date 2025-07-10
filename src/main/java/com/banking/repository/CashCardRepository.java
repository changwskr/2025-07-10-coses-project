package com.banking.repository;

import com.banking.model.entity.CashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * CashCard Repository
 * 
 * JPA repository for CashCard entity providing data access operations.
 */
@Repository
public interface CashCardRepository extends JpaRepository<CashCard, Long> {

    /**
     * Find cash card by card number
     */
    Optional<CashCard> findByCardNumber(String cardNumber);

    /**
     * Find all cash cards by customer ID
     */
    List<CashCard> findByCustomerId(String customerId);

    /**
     * Find all active cash cards by customer ID
     */
    List<CashCard> findByCustomerIdAndStatus(String customerId, String status);

    /**
     * Find cash cards by bank code and branch code
     */
    List<CashCard> findByBankCodeAndBranchCode(String bankCode, String branchCode);

    /**
     * Find cash cards by status
     */
    List<CashCard> findByStatus(String status);

    /**
     * Find cash cards with balance greater than specified amount
     */
    List<CashCard> findByBalanceGreaterThan(BigDecimal amount);

    /**
     * Find cash cards with balance less than specified amount
     */
    List<CashCard> findByBalanceLessThan(BigDecimal amount);

    /**
     * Check if card number exists
     */
    boolean existsByCardNumber(String cardNumber);

    /**
     * Count cards by customer ID
     */
    long countByCustomerId(String customerId);

    /**
     * Find cards by card type
     */
    List<CashCard> findByCardType(String cardType);

    /**
     * Custom query to find cards with balance between two amounts
     */
    @Query("SELECT c FROM CashCard c WHERE c.balance BETWEEN :minAmount AND :maxAmount")
    List<CashCard> findCardsWithBalanceBetween(@Param("minAmount") BigDecimal minAmount,
            @Param("maxAmount") BigDecimal maxAmount);

    /**
     * Custom query to find cards created in the last N days
     */
    @Query("SELECT c FROM CashCard c WHERE c.createdAt >= CURRENT_DATE - :days")
    List<CashCard> findCardsCreatedInLastDays(@Param("days") int days);

    /**
     * Custom query to find cards expiring soon
     */
    @Query("SELECT c FROM CashCard c WHERE c.expiryDate <= CURRENT_DATE + :days AND c.status = 'ACTIVE'")
    List<CashCard> findCardsExpiringSoon(@Param("days") int days);
}