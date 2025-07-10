package com.banking.service.impl;

import com.banking.model.dto.CashCardRequest;
import com.banking.model.dto.CashCardResponse;
import com.banking.model.dto.TransactionRequest;
import com.banking.model.dto.TransactionResponse;
import com.banking.model.entity.CashCard;
import com.banking.model.entity.Transaction;
import com.banking.repository.CashCardRepository;
import com.banking.repository.CustomerRepository;
import com.banking.repository.TransactionRepository;
import com.banking.service.CashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * CashCard Service Implementation
 * 
 * Provides business logic implementation for cash card operations.
 */
@Service
@Transactional
public class CashCardServiceImpl implements CashCardService {

    @Autowired
    private CashCardRepository cashCardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CashCardResponse createCashCard(CashCardRequest request) {
        // Validate customer exists
        if (!customerRepository.existsByCustomerId(request.getCustomerId())) {
            throw new RuntimeException("Customer not found: " + request.getCustomerId());
        }

        // Generate unique card number
        String cardNumber = generateCardNumber();

        // Create cash card entity
        CashCard cashCard = new CashCard();
        cashCard.setCardNumber(cardNumber);
        cashCard.setCustomerId(request.getCustomerId());
        cashCard.setCardType(request.getCardType());
        cashCard.setBalance(request.getInitialBalance());
        cashCard.setBankCode(request.getBankCode());
        cashCard.setBranchCode(request.getBranchCode());
        cashCard.setStatus("ACTIVE");
        cashCard.setExpiryDate(LocalDateTime.now().plusYears(3)); // 3 years expiry
        cashCard.setDailyLimit(new BigDecimal("1000000")); // 1M daily limit
        cashCard.setMonthlyLimit(new BigDecimal("10000000")); // 10M monthly limit

        // Save to database
        CashCard savedCard = cashCardRepository.save(cashCard);

        return convertToResponse(savedCard);
    }

    @Override
    public CashCardResponse getCashCard(String cardNumber) {
        Optional<CashCard> cashCard = cashCardRepository.findByCardNumber(cardNumber);
        if (cashCard.isPresent()) {
            return convertToResponse(cashCard.get());
        }
        throw new RuntimeException("Cash card not found: " + cardNumber);
    }

    @Override
    public List<CashCardResponse> getCashCardsByCustomer(String customerId) {
        List<CashCard> cashCards = cashCardRepository.findByCustomerId(customerId);
        return cashCards.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionResponse processTransaction(TransactionRequest request) {
        // Find the cash card
        Optional<CashCard> cashCardOpt = cashCardRepository.findByCardNumber(request.getCardNumber());
        if (!cashCardOpt.isPresent()) {
            throw new RuntimeException("Cash card not found: " + request.getCardNumber());
        }

        CashCard cashCard = cashCardOpt.get();

        // Validate card status
        if (!"ACTIVE".equals(cashCard.getStatus())) {
            throw new RuntimeException("Cash card is not active: " + request.getCardNumber());
        }

        // Create transaction entity
        Transaction transaction = new Transaction();
        transaction.setTransactionId(generateTransactionId());
        transaction.setCard(cashCard);
        transaction.setTransactionType(request.getTransactionType());
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setBalanceBefore(cashCard.getBalance());
        transaction.setTargetCardNumber(request.getTargetCardNumber());

        try {
            // Process transaction based on type
            switch (request.getTransactionType().toUpperCase()) {
                case "DEPOSIT":
                    processDeposit(cashCard, request.getAmount());
                    break;
                case "WITHDRAWAL":
                    processWithdrawal(cashCard, request.getAmount());
                    break;
                case "TRANSFER":
                    processTransfer(cashCard, request.getTargetCardNumber(), request.getAmount());
                    break;
                default:
                    throw new RuntimeException("Invalid transaction type: " + request.getTransactionType());
            }

            // Update transaction status
            transaction.setStatus("COMPLETED");
            transaction.setBalanceAfter(cashCard.getBalance());

            // Save transaction
            Transaction savedTransaction = transactionRepository.save(transaction);

            return convertToTransactionResponse(savedTransaction);

        } catch (Exception e) {
            // Handle transaction failure
            transaction.setStatus("FAILED");
            transaction.setErrorMessage(e.getMessage());
            transactionRepository.save(transaction);
            throw new RuntimeException("Transaction failed: " + e.getMessage());
        }
    }

    @Override
    public CashCardResponse updateCashCard(String cardNumber, CashCardRequest request) {
        Optional<CashCard> cashCardOpt = cashCardRepository.findByCardNumber(cardNumber);
        if (!cashCardOpt.isPresent()) {
            throw new RuntimeException("Cash card not found: " + cardNumber);
        }

        CashCard cashCard = cashCardOpt.get();

        // Update fields
        if (request.getCardType() != null) {
            cashCard.setCardType(request.getCardType());
        }
        if (request.getBankCode() != null) {
            cashCard.setBankCode(request.getBankCode());
        }
        if (request.getBranchCode() != null) {
            cashCard.setBranchCode(request.getBranchCode());
        }

        CashCard updatedCard = cashCardRepository.save(cashCard);
        return convertToResponse(updatedCard);
    }

    @Override
    public void deactivateCashCard(String cardNumber) {
        Optional<CashCard> cashCardOpt = cashCardRepository.findByCardNumber(cardNumber);
        if (!cashCardOpt.isPresent()) {
            throw new RuntimeException("Cash card not found: " + cardNumber);
        }

        CashCard cashCard = cashCardOpt.get();
        cashCard.setStatus("INACTIVE");
        cashCardRepository.save(cashCard);
    }

    @Override
    public List<TransactionResponse> getTransactionHistory(String cardNumber) {
        List<Transaction> transactions = transactionRepository.findByCardCardNumber(cardNumber);
        return transactions.stream()
                .map(this::convertToTransactionResponse)
                .collect(Collectors.toList());
    }

    // Private helper methods

    private String generateCardNumber() {
        // Generate a unique 16-digit card number
        String cardNumber;
        do {
            cardNumber = String.format("%016d", (long) (Math.random() * 10000000000000000L));
        } while (cashCardRepository.existsByCardNumber(cardNumber));

        return cardNumber;
    }

    private String generateTransactionId() {
        // Generate a unique transaction ID
        String transactionId;
        do {
            transactionId = "TXN" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        } while (transactionRepository.existsByTransactionId(transactionId));

        return transactionId;
    }

    private void processDeposit(CashCard cashCard, BigDecimal amount) {
        cashCard.setBalance(cashCard.getBalance().add(amount));
        cashCardRepository.save(cashCard);
    }

    private void processWithdrawal(CashCard cashCard, BigDecimal amount) {
        if (cashCard.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Check daily limit
        BigDecimal dailyTotal = transactionRepository.calculateTotalAmountByType(
                cashCard.getCardNumber(), "WITHDRAWAL");
        if (dailyTotal != null && dailyTotal.add(amount).compareTo(cashCard.getDailyLimit()) > 0) {
            throw new RuntimeException("Daily withdrawal limit exceeded");
        }

        cashCard.setBalance(cashCard.getBalance().subtract(amount));
        cashCardRepository.save(cashCard);
    }

    private void processTransfer(CashCard sourceCard, String targetCardNumber, BigDecimal amount) {
        // Find target card
        Optional<CashCard> targetCardOpt = cashCardRepository.findByCardNumber(targetCardNumber);
        if (!targetCardOpt.isPresent()) {
            throw new RuntimeException("Target card not found: " + targetCardNumber);
        }

        CashCard targetCard = targetCardOpt.get();

        // Validate target card status
        if (!"ACTIVE".equals(targetCard.getStatus())) {
            throw new RuntimeException("Target card is not active: " + targetCardNumber);
        }

        // Process withdrawal from source
        processWithdrawal(sourceCard, amount);

        // Process deposit to target
        processDeposit(targetCard, amount);
    }

    private CashCardResponse convertToResponse(CashCard cashCard) {
        return new CashCardResponse(
                cashCard.getCardNumber(),
                cashCard.getCustomerId(),
                cashCard.getCardType(),
                cashCard.getBalance(),
                cashCard.getStatus(),
                cashCard.getBankCode(),
                cashCard.getBranchCode(),
                cashCard.getCreatedAt(),
                cashCard.getUpdatedAt());
    }

    private TransactionResponse convertToTransactionResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getCard().getCardNumber(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getBalanceAfter(),
                transaction.getDescription(),
                transaction.getStatus(),
                transaction.getTransactionDate());
    }
}