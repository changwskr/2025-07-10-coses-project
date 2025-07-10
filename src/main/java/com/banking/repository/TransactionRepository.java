package com.banking.repository;

import com.banking.model.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Transaction Repository
 * 
 * JPA repository for Transaction entity providing data access operations.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Find transaction by transaction ID
     */
    Optional<Transaction> findByTransactionId(String transactionId);

    /**
     * Find all transactions by card number
     */
    List<Transaction> findByCardCardNumber(String cardNumber);

    /**
     * Find all transactions by card number with pagination
     */
    Page<Transaction> findByCardCardNumber(String cardNumber, Pageable pageable);

    /**
     * Find transactions by card number and transaction type
     */
    List<Transaction> findByCardCardNumberAndTransactionType(String cardNumber, String transactionType);

    /**
     * Find transactions by status
     */
    List<Transaction> findByStatus(String status);

    /**
     * Find transactions by transaction type
     */
    List<Transaction> findByTransactionType(String transactionType);

    /**
     * Find transactions with amount greater than specified amount
     */
    List<Transaction> findByAmountGreaterThan(BigDecimal amount);

    /**
     * Find transactions with amount less than specified amount
     */
    List<Transaction> findByAmountLessThan(BigDecimal amount);

    /**
     * Find transactions between two dates
     */
    List<Transaction> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Find transactions by card number between two dates
     */
    List<Transaction> findByCardCardNumberAndTransactionDateBetween(String cardNumber,
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Check if transaction ID exists
     */
    boolean existsByTransactionId(String transactionId);

    /**
     * Count transactions by card number
     */
    long countByCardCardNumber(String cardNumber);

    /**
     * Count transactions by card number and status
     */
    long countByCardCardNumberAndStatus(String cardNumber, String status);

    /**
     * Find transactions by reference number
     */
    List<Transaction> findByReferenceNumber(String referenceNumber);

    /**
     * Find transactions by target card number (for transfers)
     */
    List<Transaction> findByTargetCardNumber(String targetCardNumber);

    /**
     * Custom query to find transactions with amount between two values
     */
    @Query("SELECT t FROM Transaction t WHERE t.amount BETWEEN :minAmount AND :maxAmount")
    List<Transaction> findTransactionsWithAmountBetween(@Param("minAmount") BigDecimal minAmount,
            @Param("maxAmount") BigDecimal maxAmount);

    /**
     * Custom query to find transactions created in the last N days
     */
    @Query("SELECT t FROM Transaction t WHERE t.createdAt >= CURRENT_DATE - :days")
    List<Transaction> findTransactionsCreatedInLastDays(@Param("days") int days);

    /**
     * Custom query to find transactions by card number with pagination and sorting
     */
    @Query("SELECT t FROM Transaction t WHERE t.card.cardNumber = :cardNumber ORDER BY t.transactionDate DESC")
    Page<Transaction> findTransactionsByCardNumberOrdered(@Param("cardNumber") String cardNumber,
            Pageable pageable);

    /**
     * Custom query to calculate total amount by transaction type for a card
     */
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.card.cardNumber = :cardNumber AND t.transactionType = :transactionType")
    BigDecimal calculateTotalAmountByType(@Param("cardNumber") String cardNumber,
            @Param("transactionType") String transactionType);

    /**
     * Custom query to find failed transactions
     */
    @Query("SELECT t FROM Transaction t WHERE t.status = 'FAILED' AND t.errorMessage IS NOT NULL")
    List<Transaction> findFailedTransactions();
}