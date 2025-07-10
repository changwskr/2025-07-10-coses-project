package com.banking.kdb.oversea.cashcard.business;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.cashcard.model.CashCard;
import com.banking.kdb.oversea.cashcard.model.CashCardTransaction;
import com.banking.kdb.oversea.cashcard.repository.CashCardRepository;
import com.banking.kdb.oversea.cashcard.repository.CashCardTransactionRepository;
import com.banking.kdb.oversea.cashcard.transfer.CashCardDTO;
import com.banking.kdb.oversea.cashcard.transfer.CashCardTransactionDTO;
import com.banking.kdb.oversea.common.model.Customer;
import com.banking.kdb.oversea.common.repository.CustomerRepository;
import com.banking.kdb.oversea.foundation.config.KdbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * CashCard Service for KDB Oversea
 * 
 * Provides cash card management functionality including
 * card creation, transactions, and status management.
 */
@Service
@Transactional
public class CashCardService {

    private static final FoundationLogger logger = FoundationLogger.getLogger(CashCardService.class);

    @Autowired
    private CashCardRepository cashCardRepository;

    @Autowired
    private CashCardTransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private KdbConfig kdbConfig;

    /**
     * Create a new cash card
     */
    public CashCardDTO createCashCard(CashCardDTO cashCardDTO) {
        logger.info("Creating cash card - Customer ID: {}, Card Type: {}",
                cashCardDTO.getCustomerId(), cashCardDTO.getCardType());

        // Validate customer exists
        Optional<Customer> customer = customerRepository.findByCustomerId(cashCardDTO.getCustomerId());
        if (customer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found with ID: " + cashCardDTO.getCustomerId());
        }

        // Generate card number
        String cardNumber = generateCardNumber();

        CashCard cashCard = new CashCard();
        cashCard.setCardNumber(cardNumber);
        cashCard.setCustomerId(cashCardDTO.getCustomerId());
        cashCard.setCardType(cashCardDTO.getCardType());
        cashCard.setCardStatus("ACTIVE");
        cashCard.setDailyLimit(cashCardDTO.getDailyLimit());
        cashCard.setMonthlyLimit(cashCardDTO.getMonthlyLimit());
        cashCard.setCurrency(cashCardDTO.getCurrency());
        cashCard.setExpiryDate(cashCardDTO.getExpiryDate());
        cashCard.setPin(cashCardDTO.getPin());
        cashCard.setCreatedBy(cashCardDTO.getCreatedBy());
        cashCard.setCreatedDateTime(LocalDateTime.now());

        CashCard savedCard = cashCardRepository.save(cashCard);

        CashCardDTO result = convertToDTO(savedCard);
        logger.info("Cash card created successfully - Card Number: {}", result.getCardNumber());

        return result;
    }

    /**
     * Find cash card by card number
     */
    public Optional<CashCardDTO> findCashCardByNumber(String cardNumber) {
        logger.debug("Finding cash card by number: {}", cardNumber);

        Optional<CashCard> cashCard = cashCardRepository.findByCardNumber(cardNumber);
        return cashCard.map(this::convertToDTO);
    }

    /**
     * Find cash cards by customer ID
     */
    public List<CashCardDTO> findCashCardsByCustomerId(String customerId) {
        logger.debug("Finding cash cards by customer ID: {}", customerId);

        List<CashCard> cashCards = cashCardRepository.findByCustomerId(customerId);
        return cashCards.stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Update cash card information
     */
    public CashCardDTO updateCashCard(String cardNumber, CashCardDTO cashCardDTO) {
        logger.info("Updating cash card - Card Number: {}", cardNumber);

        Optional<CashCard> existingCard = cashCardRepository.findByCardNumber(cardNumber);
        if (existingCard.isEmpty()) {
            throw new IllegalArgumentException("Cash card not found with number: " + cardNumber);
        }

        CashCard cashCard = existingCard.get();
        cashCard.setCardType(cashCardDTO.getCardType());
        cashCard.setDailyLimit(cashCardDTO.getDailyLimit());
        cashCard.setMonthlyLimit(cashCardDTO.getMonthlyLimit());
        cashCard.setCurrency(cashCardDTO.getCurrency());
        cashCard.setExpiryDate(cashCardDTO.getExpiryDate());
        cashCard.setModifiedBy(cashCardDTO.getModifiedBy());
        cashCard.setModifiedDateTime(LocalDateTime.now());

        CashCard updatedCard = cashCardRepository.save(cashCard);

        CashCardDTO result = convertToDTO(updatedCard);
        logger.info("Cash card updated successfully - Card Number: {}", result.getCardNumber());

        return result;
    }

    /**
     * Deactivate cash card
     */
    public void deactivateCashCard(String cardNumber, String modifiedBy) {
        logger.info("Deactivating cash card - Card Number: {}", cardNumber);

        Optional<CashCard> existingCard = cashCardRepository.findByCardNumber(cardNumber);
        if (existingCard.isEmpty()) {
            throw new IllegalArgumentException("Cash card not found with number: " + cardNumber);
        }

        CashCard cashCard = existingCard.get();
        cashCard.setCardStatus("INACTIVE");
        cashCard.setModifiedBy(modifiedBy);
        cashCard.setModifiedDateTime(LocalDateTime.now());

        cashCardRepository.save(cashCard);
        logger.info("Cash card deactivated successfully - Card Number: {}", cardNumber);
    }

    /**
     * Process cash card transaction
     */
    public CashCardTransactionDTO processTransaction(CashCardTransactionDTO transactionDTO) {
        logger.info("Processing transaction - Card Number: {}, Amount: {}",
                transactionDTO.getCardNumber(), transactionDTO.getAmount());

        // Validate card exists and is active
        Optional<CashCard> cashCard = cashCardRepository.findByCardNumber(transactionDTO.getCardNumber());
        if (cashCard.isEmpty()) {
            throw new IllegalArgumentException("Cash card not found: " + transactionDTO.getCardNumber());
        }

        CashCard card = cashCard.get();
        if (!"ACTIVE".equals(card.getCardStatus())) {
            throw new IllegalArgumentException("Cash card is not active: " + transactionDTO.getCardNumber());
        }

        // Validate PIN if provided
        if (transactionDTO.getPin() != null && !transactionDTO.getPin().equals(card.getPin())) {
            throw new IllegalArgumentException("Invalid PIN for card: " + transactionDTO.getCardNumber());
        }

        // Check limits
        validateTransactionLimits(card, transactionDTO.getAmount());

        // Create transaction
        CashCardTransaction transaction = new CashCardTransaction();
        transaction.setTransactionId(generateTransactionId());
        transaction.setCardNumber(transactionDTO.getCardNumber());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setMerchantName(transactionDTO.getMerchantName());
        transaction.setMerchantId(transactionDTO.getMerchantId());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus("COMPLETED");
        transaction.setCreatedBy(transactionDTO.getCreatedBy());
        transaction.setCreatedDateTime(LocalDateTime.now());

        CashCardTransaction savedTransaction = transactionRepository.save(transaction);

        CashCardTransactionDTO result = convertTransactionToDTO(savedTransaction);
        logger.info("Transaction processed successfully - Transaction ID: {}", result.getTransactionId());

        return result;
    }

    /**
     * Get cash card by card number
     */
    public CashCardDTO getCashCard(String cardNumber) {
        logger.debug("Getting cash card - Card Number: {}", cardNumber);

        Optional<CashCard> cashCard = cashCardRepository.findByCardNumber(cardNumber);
        if (cashCard.isEmpty()) {
            throw new IllegalArgumentException("Cash card not found: " + cardNumber);
        }

        return convertToDTO(cashCard.get());
    }

    /**
     * Get cash cards by customer ID
     */
    public List<CashCardDTO> getCashCardsByCustomer(String customerId) {
        logger.debug("Getting cash cards for customer: {}", customerId);

        List<CashCard> cashCards = cashCardRepository.findByCustomerId(customerId);
        return cashCards.stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Activate cash card
     */
    public void activateCashCard(String cardNumber) {
        logger.info("Activating cash card - Card Number: {}", cardNumber);

        Optional<CashCard> existingCard = cashCardRepository.findByCardNumber(cardNumber);
        if (existingCard.isEmpty()) {
            throw new IllegalArgumentException("Cash card not found with number: " + cardNumber);
        }

        CashCard cashCard = existingCard.get();
        cashCard.setCardStatus("ACTIVE");
        cashCard.setModifiedDateTime(LocalDateTime.now());

        cashCardRepository.save(cashCard);
        logger.info("Cash card activated successfully - Card Number: {}", cardNumber);
    }

    /**
     * Deactivate cash card
     */
    public void deactivateCashCard(String cardNumber) {
        logger.info("Deactivating cash card - Card Number: {}", cardNumber);

        Optional<CashCard> existingCard = cashCardRepository.findByCardNumber(cardNumber);
        if (existingCard.isEmpty()) {
            throw new IllegalArgumentException("Cash card not found with number: " + cardNumber);
        }

        CashCard cashCard = existingCard.get();
        cashCard.setCardStatus("INACTIVE");
        cashCard.setModifiedDateTime(LocalDateTime.now());

        cashCardRepository.save(cashCard);
        logger.info("Cash card deactivated successfully - Card Number: {}", cardNumber);
    }

    /**
     * Get transaction by transaction ID
     */
    public CashCardTransactionDTO getTransaction(String transactionId) {
        logger.debug("Getting transaction - Transaction ID: {}", transactionId);

        CashCardTransaction transaction = transactionRepository.findByTransactionId(transactionId);
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction not found: " + transactionId);
        }

        return convertTransactionToDTO(transaction);
    }

    /**
     * Get daily transaction total for a card
     */
    public BigDecimal getDailyTransactionTotal(String cardNumber) {
        logger.debug("Getting daily transaction total for card: {}", cardNumber);

        // Validate card exists
        Optional<CashCard> cashCard = cashCardRepository.findByCardNumber(cardNumber);
        if (cashCard.isEmpty()) {
            throw new IllegalArgumentException("Cash card not found: " + cardNumber);
        }

        return transactionRepository.getDailyTransactionTotal(cardNumber);
    }

    /**
     * Get monthly transaction total for a card
     */
    public BigDecimal getMonthlyTransactionTotal(String cardNumber) {
        logger.debug("Getting monthly transaction total for card: {}", cardNumber);

        // Validate card exists
        Optional<CashCard> cashCard = cashCardRepository.findByCardNumber(cardNumber);
        if (cashCard.isEmpty()) {
            throw new IllegalArgumentException("Cash card not found: " + cardNumber);
        }

        return transactionRepository.getMonthlyTransactionTotal(cardNumber);
    }

    /**
     * Get transaction history for a card
     */
    public List<CashCardTransactionDTO> getTransactionHistory(String cardNumber) {
        logger.debug("Getting transaction history for card: {}", cardNumber);

        List<CashCardTransaction> transactions = transactionRepository
                .findByCardNumberOrderByTransactionDateDesc(cardNumber);
        return transactions.stream()
                .map(this::convertTransactionToDTO)
                .toList();
    }

    /**
     * Validate transaction limits
     */
    private void validateTransactionLimits(CashCard card, BigDecimal amount) {
        // Check daily limit
        BigDecimal dailyTotal = transactionRepository.getDailyTransactionTotal(card.getCardNumber());
        if (dailyTotal.add(amount).compareTo(card.getDailyLimit()) > 0) {
            throw new IllegalArgumentException("Transaction exceeds daily limit");
        }

        // Check monthly limit
        BigDecimal monthlyTotal = transactionRepository.getMonthlyTransactionTotal(card.getCardNumber());
        if (monthlyTotal.add(amount).compareTo(card.getMonthlyLimit()) > 0) {
            throw new IllegalArgumentException("Transaction exceeds monthly limit");
        }
    }

    /**
     * Generate unique card number
     */
    private String generateCardNumber() {
        return "CC" + UUID.randomUUID().toString().substring(0, 16).toUpperCase();
    }

    /**
     * Generate unique transaction ID
     */
    private String generateTransactionId() {
        return "TXN" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }

    /**
     * Convert CashCard entity to DTO
     */
    private CashCardDTO convertToDTO(CashCard cashCard) {
        CashCardDTO dto = new CashCardDTO();
        dto.setId(cashCard.getId());
        dto.setCardNumber(cashCard.getCardNumber());
        dto.setCustomerId(cashCard.getCustomerId());
        dto.setCardType(cashCard.getCardType());
        dto.setCardStatus(cashCard.getCardStatus());
        dto.setDailyLimit(cashCard.getDailyLimit());
        dto.setMonthlyLimit(cashCard.getMonthlyLimit());
        dto.setCurrency(cashCard.getCurrency());
        dto.setExpiryDate(cashCard.getExpiryDate());
        dto.setCreatedBy(cashCard.getCreatedBy());
        dto.setCreatedDateTime(cashCard.getCreatedDateTime());
        dto.setModifiedBy(cashCard.getModifiedBy());
        dto.setModifiedDateTime(cashCard.getModifiedDateTime());
        return dto;
    }

    /**
     * Convert CashCardTransaction entity to DTO
     */
    private CashCardTransactionDTO convertTransactionToDTO(CashCardTransaction transaction) {
        CashCardTransactionDTO dto = new CashCardTransactionDTO();
        dto.setId(transaction.getId());
        dto.setTransactionId(transaction.getTransactionId());
        dto.setCardNumber(transaction.getCardNumber());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setAmount(transaction.getAmount());
        dto.setCurrency(transaction.getCurrency());
        dto.setMerchantName(transaction.getMerchantName());
        dto.setMerchantId(transaction.getMerchantId());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setStatus(transaction.getStatus());
        dto.setCreatedBy(transaction.getCreatedBy());
        dto.setCreatedDateTime(transaction.getCreatedDateTime());
        return dto;
    }
}