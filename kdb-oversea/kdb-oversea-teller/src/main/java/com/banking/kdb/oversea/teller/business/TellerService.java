package com.banking.kdb.oversea.teller.business;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.teller.model.TellerSession;
import com.banking.kdb.oversea.teller.model.TellerTransaction;
import com.banking.kdb.oversea.teller.repository.TellerSessionRepository;
import com.banking.kdb.oversea.teller.repository.TellerTransactionRepository;
import com.banking.kdb.oversea.teller.transfer.TellerSessionDTO;
import com.banking.kdb.oversea.teller.transfer.TellerTransactionDTO;
import com.banking.kdb.oversea.common.business.CustomerService;
import com.banking.kdb.oversea.cashcard.business.CashCardService;
import com.banking.kdb.oversea.deposit.business.DepositService;
import com.banking.kdb.oversea.common.model.Customer;
import com.banking.kdb.oversea.cashcard.transfer.CashCardDTO;
import com.banking.kdb.oversea.cashcard.transfer.CashCardTransactionDTO;
import com.banking.kdb.oversea.deposit.transfer.DepositAccountDTO;
import com.banking.kdb.oversea.deposit.transfer.DepositTransactionDTO;
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
 * Teller Service for KDB Oversea
 * 
 * Provides teller operations functionality including
 * session management, customer service, and transaction processing.
 */
@Service
@Transactional
public class TellerService {

    private static final FoundationLogger logger = FoundationLogger.getLogger(TellerService.class);

    @Autowired
    private TellerSessionRepository tellerSessionRepository;

    @Autowired
    private TellerTransactionRepository tellerTransactionRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CashCardService cashCardService;

    @Autowired
    private DepositService depositService;

    @Autowired
    private KdbConfig kdbConfig;

    /**
     * Start teller session
     */
    public TellerSessionDTO startTellerSession(String tellerId, String branchCode) {
        logger.info("Starting teller session - Teller ID: {}, Branch: {}", tellerId, branchCode);

        // Check if teller already has an active session
        Optional<TellerSession> existingSession = tellerSessionRepository.findByTellerIdAndStatus(tellerId, "ACTIVE");
        if (existingSession.isPresent()) {
            throw new IllegalArgumentException("Teller already has an active session");
        }

        TellerSession session = new TellerSession();
        session.setSessionId(generateSessionId());
        session.setTellerId(tellerId);
        session.setBranchCode(branchCode);
        session.setStatus("ACTIVE");
        session.setStartTime(LocalDateTime.now());
        session.setCreatedBy(tellerId);
        session.setCreatedDateTime(LocalDateTime.now());

        TellerSession savedSession = tellerSessionRepository.save(session);

        TellerSessionDTO result = convertToDTO(savedSession);
        logger.info("Teller session started successfully - Session ID: {}", result.getSessionId());

        return result;
    }

    /**
     * End teller session
     */
    public void endTellerSession(String sessionId, String tellerId) {
        logger.info("Ending teller session - Session ID: {}, Teller ID: {}", sessionId, tellerId);

        Optional<TellerSession> session = tellerSessionRepository.findBySessionId(sessionId);
        if (session.isEmpty()) {
            throw new IllegalArgumentException("Teller session not found: " + sessionId);
        }

        TellerSession tellerSession = session.get();
        if (!tellerSession.getTellerId().equals(tellerId)) {
            throw new IllegalArgumentException("Teller ID mismatch for session: " + sessionId);
        }

        if (!"ACTIVE".equals(tellerSession.getStatus())) {
            throw new IllegalArgumentException("Teller session is not active: " + sessionId);
        }

        tellerSession.setStatus("CLOSED");
        tellerSession.setEndTime(LocalDateTime.now());
        tellerSession.setModifiedBy(tellerId);
        tellerSession.setModifiedDateTime(LocalDateTime.now());

        tellerSessionRepository.save(tellerSession);
        logger.info("Teller session ended successfully - Session ID: {}", sessionId);
    }

    /**
     * Get active teller session
     */
    public Optional<TellerSessionDTO> getActiveTellerSession(String tellerId) {
        logger.debug("Getting active teller session for teller: {}", tellerId);

        Optional<TellerSession> session = tellerSessionRepository.findByTellerIdAndStatus(tellerId, "ACTIVE");
        return session.map(this::convertToDTO);
    }

    /**
     * Process customer inquiry
     */
    public Customer getCustomerInformation(String customerId, String sessionId) {
        logger.info("Processing customer inquiry - Customer ID: {}, Session ID: {}", customerId, sessionId);

        // Validate session
        validateActiveSession(sessionId);

        // Get customer information
        Optional<Customer> customer = customerService.findCustomerById(customerId);
        if (customer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found: " + customerId);
        }

        // Log transaction
        logTellerTransaction(sessionId, "CUSTOMER_INQUIRY", "Customer information inquiry", customerId);

        return customer.get();
    }

    /**
     * Process cash card creation
     */
    public CashCardDTO createCashCardForCustomer(CashCardDTO cashCardDTO, String sessionId) {
        logger.info("Processing cash card creation - Customer ID: {}, Session ID: {}",
                cashCardDTO.getCustomerId(), sessionId);

        // Validate session
        validateActiveSession(sessionId);

        // Create cash card
        CashCardDTO createdCard = cashCardService.createCashCard(cashCardDTO);

        // Log transaction
        logTellerTransaction(sessionId, "CASH_CARD_CREATION", "Cash card created", createdCard.getCardNumber());

        return createdCard;
    }

    /**
     * Process deposit account creation
     */
    public DepositAccountDTO createDepositAccountForCustomer(DepositAccountDTO accountDTO, String sessionId) {
        logger.info("Processing deposit account creation - Customer ID: {}, Session ID: {}",
                accountDTO.getCustomerId(), sessionId);

        // Validate session
        validateActiveSession(sessionId);

        // Create deposit account
        DepositAccountDTO createdAccount = depositService.createDepositAccount(accountDTO);

        // Log transaction
        logTellerTransaction(sessionId, "DEPOSIT_ACCOUNT_CREATION", "Deposit account created",
                createdAccount.getAccountNumber());

        return createdAccount;
    }

    /**
     * Process cash deposit
     */
    public DepositTransactionDTO processCashDeposit(String accountNumber, BigDecimal amount,
            String currency, String sessionId, String tellerId) {
        logger.info("Processing cash deposit - Account: {}, Amount: {}, Session: {}",
                accountNumber, amount, sessionId);

        // Validate session
        validateActiveSession(sessionId);

        // Create transaction DTO
        DepositTransactionDTO transactionDTO = new DepositTransactionDTO();
        transactionDTO.setAccountNumber(accountNumber);
        transactionDTO.setAmount(amount);
        transactionDTO.setCurrency(currency);
        transactionDTO.setDescription("Cash deposit by teller");
        transactionDTO.setCreatedBy(tellerId);

        // Process deposit
        DepositTransactionDTO result = depositService.processDeposit(accountNumber, transactionDTO);

        // Log transaction
        logTellerTransaction(sessionId, "CASH_DEPOSIT", "Cash deposit processed", result.getTransactionId());

        return result;
    }

    /**
     * Process cash withdrawal
     */
    public DepositTransactionDTO processCashWithdrawal(String accountNumber, BigDecimal amount,
            String currency, String sessionId, String tellerId) {
        logger.info("Processing cash withdrawal - Account: {}, Amount: {}, Session: {}",
                accountNumber, amount, sessionId);

        // Validate session
        validateActiveSession(sessionId);

        // Create transaction DTO
        DepositTransactionDTO transactionDTO = new DepositTransactionDTO();
        transactionDTO.setAccountNumber(accountNumber);
        transactionDTO.setAmount(amount);
        transactionDTO.setCurrency(currency);
        transactionDTO.setDescription("Cash withdrawal by teller");
        transactionDTO.setCreatedBy(tellerId);

        // Process withdrawal
        DepositTransactionDTO result = depositService.processWithdrawal(accountNumber, transactionDTO);

        // Log transaction
        logTellerTransaction(sessionId, "CASH_WITHDRAWAL", "Cash withdrawal processed", result.getTransactionId());

        return result;
    }

    /**
     * Process account transfer
     */
    public DepositTransactionDTO processAccountTransfer(String fromAccount, String toAccount,
            BigDecimal amount, String currency,
            String sessionId, String tellerId) {
        logger.info("Processing account transfer - From: {}, To: {}, Amount: {}, Session: {}",
                fromAccount, toAccount, amount, sessionId);

        // Validate session
        validateActiveSession(sessionId);

        // Create transaction DTO
        DepositTransactionDTO transactionDTO = new DepositTransactionDTO();
        transactionDTO.setAccountNumber(fromAccount);
        transactionDTO.setAmount(amount);
        transactionDTO.setCurrency(currency);
        transactionDTO.setDescription("Account transfer by teller");
        transactionDTO.setCreatedBy(tellerId);

        // Process transfer
        DepositTransactionDTO result = depositService.processTransfer(fromAccount, toAccount, transactionDTO);

        // Log transaction
        logTellerTransaction(sessionId, "ACCOUNT_TRANSFER", "Account transfer processed", result.getTransactionId());

        return result;
    }

    /**
     * Process cash card transaction
     */
    public CashCardTransactionDTO processCashCardTransaction(CashCardTransactionDTO transactionDTO, String sessionId) {
        logger.info("Processing cash card transaction - Card: {}, Amount: {}, Session: {}",
                transactionDTO.getCardNumber(), transactionDTO.getAmount(), sessionId);

        // Validate session
        validateActiveSession(sessionId);

        // Process transaction
        CashCardTransactionDTO result = cashCardService.processTransaction(transactionDTO);

        // Log transaction
        logTellerTransaction(sessionId, "CASH_CARD_TRANSACTION", "Cash card transaction processed",
                result.getTransactionId());

        return result;
    }

    /**
     * Get teller transaction history
     */
    public List<TellerTransactionDTO> getTellerTransactionHistory(String sessionId) {
        logger.debug("Getting teller transaction history for session: {}", sessionId);

        List<TellerTransaction> transactions = tellerTransactionRepository
                .findBySessionIdOrderByTransactionDateDesc(sessionId);
        return transactions.stream()
                .map(this::convertTransactionToDTO)
                .toList();
    }

    /**
     * Get teller session summary
     */
    public TellerSessionDTO getTellerSessionSummary(String sessionId) {
        logger.debug("Getting teller session summary for session: {}", sessionId);

        Optional<TellerSession> session = tellerSessionRepository.findBySessionId(sessionId);
        if (session.isEmpty()) {
            throw new IllegalArgumentException("Teller session not found: " + sessionId);
        }

        return convertToDTO(session.get());
    }

    /**
     * Validate active session
     */
    private void validateActiveSession(String sessionId) {
        Optional<TellerSession> session = tellerSessionRepository.findBySessionId(sessionId);
        if (session.isEmpty()) {
            throw new IllegalArgumentException("Teller session not found: " + sessionId);
        }

        if (!"ACTIVE".equals(session.get().getStatus())) {
            throw new IllegalArgumentException("Teller session is not active: " + sessionId);
        }
    }

    /**
     * Log teller transaction
     */
    private void logTellerTransaction(String sessionId, String transactionType, String description, String reference) {
        TellerTransaction transaction = new TellerTransaction();
        transaction.setTransactionId(generateTransactionId());
        transaction.setSessionId(sessionId);
        transaction.setTransactionType(transactionType);
        transaction.setDescription(description);
        transaction.setReference(reference);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus("COMPLETED");
        transaction.setCreatedDateTime(LocalDateTime.now());

        tellerTransactionRepository.save(transaction);
    }

    /**
     * Generate unique session ID
     */
    private String generateSessionId() {
        return "SESS" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }

    /**
     * Generate unique transaction ID
     */
    private String generateTransactionId() {
        return "TTXN" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }

    /**
     * Convert TellerSession entity to DTO
     */
    private TellerSessionDTO convertToDTO(TellerSession session) {
        TellerSessionDTO dto = new TellerSessionDTO();
        dto.setId(session.getId());
        dto.setSessionId(session.getSessionId());
        dto.setTellerId(session.getTellerId());
        dto.setBranchCode(session.getBranchCode());
        dto.setStatus(session.getStatus());
        dto.setStartTime(session.getStartTime());
        dto.setEndTime(session.getEndTime());
        dto.setCreatedBy(session.getCreatedBy());
        dto.setCreatedDateTime(session.getCreatedDateTime());
        dto.setModifiedBy(session.getModifiedBy());
        dto.setModifiedDateTime(session.getModifiedDateTime());
        return dto;
    }

    /**
     * Convert TellerTransaction entity to DTO
     */
    private TellerTransactionDTO convertTransactionToDTO(TellerTransaction transaction) {
        TellerTransactionDTO dto = new TellerTransactionDTO();
        dto.setId(transaction.getId());
        dto.setTransactionId(transaction.getTransactionId());
        dto.setSessionId(transaction.getSessionId());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setDescription(transaction.getDescription());
        dto.setReference(transaction.getReference());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setStatus(transaction.getStatus());
        dto.setCreatedDateTime(transaction.getCreatedDateTime());
        return dto;
    }
}