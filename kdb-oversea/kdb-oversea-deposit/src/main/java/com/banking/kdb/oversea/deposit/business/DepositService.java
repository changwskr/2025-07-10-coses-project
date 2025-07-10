package com.banking.kdb.oversea.deposit.business;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.deposit.model.DepositAccount;
import com.banking.kdb.oversea.deposit.model.DepositTransaction;
import com.banking.kdb.oversea.deposit.repository.DepositAccountRepository;
import com.banking.kdb.oversea.deposit.repository.DepositTransactionRepository;
import com.banking.kdb.oversea.deposit.transfer.DepositAccountDTO;
import com.banking.kdb.oversea.deposit.transfer.DepositTransactionDTO;
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
 * Deposit Service for KDB Oversea
 * 
 * Provides deposit account management functionality including
 * account creation, deposits, withdrawals, and transfers.
 */
@Service
@Transactional
public class DepositService {

    private static final FoundationLogger logger = FoundationLogger.getLogger(DepositService.class);

    @Autowired
    private DepositAccountRepository depositAccountRepository;

    @Autowired
    private DepositTransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private KdbConfig kdbConfig;

    /**
     * Create a new deposit account
     */
    public DepositAccountDTO createDepositAccount(DepositAccountDTO accountDTO) {
        logger.info("Creating deposit account - Customer ID: {}, Account Type: {}",
                accountDTO.getCustomerId(), accountDTO.getAccountType());

        // Validate customer exists
        Optional<Customer> customer = customerRepository.findByCustomerId(accountDTO.getCustomerId());
        if (customer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found with ID: " + accountDTO.getCustomerId());
        }

        // Generate account number
        String accountNumber = generateAccountNumber();

        DepositAccount account = new DepositAccount();
        account.setAccountNumber(accountNumber);
        account.setCustomerId(accountDTO.getCustomerId());
        account.setAccountType(accountDTO.getAccountType());
        account.setBalance(accountDTO.getInitialBalance() != null ? accountDTO.getInitialBalance() : BigDecimal.ZERO);
        account.setCurrency(accountDTO.getCurrency());
        account.setInterestRate(accountDTO.getInterestRate());
        account.setMinimumBalance(accountDTO.getMinimumBalance());
        account.setDailyLimit(accountDTO.getDailyLimit());
        account.setMonthlyLimit(accountDTO.getMonthlyLimit());
        account.setStatus("ACTIVE");
        account.setOpenDate(LocalDateTime.now());
        account.setMaturityDate(accountDTO.getMaturityDate());
        account.setCreatedBy(accountDTO.getCreatedBy());
        account.setCreatedDateTime(LocalDateTime.now());

        DepositAccount savedAccount = depositAccountRepository.save(account);

        DepositAccountDTO result = convertToDTO(savedAccount);
        logger.info("Deposit account created successfully - Account Number: {}", result.getAccountNumber());

        return result;
    }

    /**
     * Find deposit account by account number
     */
    public Optional<DepositAccountDTO> findDepositAccountByNumber(String accountNumber) {
        logger.debug("Finding deposit account by number: {}", accountNumber);

        Optional<DepositAccount> account = depositAccountRepository.findByAccountNumber(accountNumber);
        return account.map(this::convertToDTO);
    }

    /**
     * Find deposit accounts by customer ID
     */
    public List<DepositAccountDTO> findDepositAccountsByCustomerId(String customerId) {
        logger.debug("Finding deposit accounts by customer ID: {}", customerId);

        List<DepositAccount> accounts = depositAccountRepository.findByCustomerId(customerId);
        return accounts.stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Process deposit transaction
     */
    public DepositTransactionDTO processDeposit(String accountNumber, DepositTransactionDTO transactionDTO) {
        logger.info("Processing deposit - Account Number: {}, Amount: {}",
                accountNumber, transactionDTO.getAmount());

        // Validate account exists and is active
        Optional<DepositAccount> account = depositAccountRepository.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            throw new IllegalArgumentException("Deposit account not found: " + accountNumber);
        }

        DepositAccount depositAccount = account.get();
        if (!"ACTIVE".equals(depositAccount.getStatus())) {
            throw new IllegalArgumentException("Deposit account is not active: " + accountNumber);
        }

        // Validate currency match
        if (!depositAccount.getCurrency().equals(transactionDTO.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch for account: " + accountNumber);
        }

        // Check daily limit
        validateDailyLimit(depositAccount, transactionDTO.getAmount());

        // Create transaction
        DepositTransaction transaction = new DepositTransaction();
        transaction.setTransactionId(generateTransactionId());
        transaction.setAccountNumber(accountNumber);
        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setBalanceBefore(depositAccount.getBalance());
        transaction.setBalanceAfter(depositAccount.getBalance().add(transactionDTO.getAmount()));
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setReferenceNumber(transactionDTO.getReferenceNumber());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus("COMPLETED");
        transaction.setCreatedBy(transactionDTO.getCreatedBy());
        transaction.setCreatedDateTime(LocalDateTime.now());

        // Update account balance
        depositAccount.setBalance(depositAccount.getBalance().add(transactionDTO.getAmount()));
        depositAccount.setModifiedBy(transactionDTO.getCreatedBy());
        depositAccount.setModifiedDateTime(LocalDateTime.now());

        // Save transaction and account
        DepositTransaction savedTransaction = transactionRepository.save(transaction);
        depositAccountRepository.save(depositAccount);

        DepositTransactionDTO result = convertTransactionToDTO(savedTransaction);
        logger.info("Deposit processed successfully - Transaction ID: {}", result.getTransactionId());

        return result;
    }

    /**
     * Process withdrawal transaction
     */
    public DepositTransactionDTO processWithdrawal(String accountNumber, DepositTransactionDTO transactionDTO) {
        logger.info("Processing withdrawal - Account Number: {}, Amount: {}",
                accountNumber, transactionDTO.getAmount());

        // Validate account exists and is active
        Optional<DepositAccount> account = depositAccountRepository.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            throw new IllegalArgumentException("Deposit account not found: " + accountNumber);
        }

        DepositAccount depositAccount = account.get();
        if (!"ACTIVE".equals(depositAccount.getStatus())) {
            throw new IllegalArgumentException("Deposit account is not active: " + accountNumber);
        }

        // Validate currency match
        if (!depositAccount.getCurrency().equals(transactionDTO.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch for account: " + accountNumber);
        }

        // Check sufficient balance
        if (depositAccount.getBalance().compareTo(transactionDTO.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance for withdrawal");
        }

        // Check minimum balance
        if (depositAccount.getMinimumBalance() != null &&
                depositAccount.getBalance().subtract(transactionDTO.getAmount())
                        .compareTo(depositAccount.getMinimumBalance()) < 0) {
            throw new IllegalArgumentException("Withdrawal would violate minimum balance requirement");
        }

        // Check daily limit
        validateDailyLimit(depositAccount, transactionDTO.getAmount());

        // Create transaction
        DepositTransaction transaction = new DepositTransaction();
        transaction.setTransactionId(generateTransactionId());
        transaction.setAccountNumber(accountNumber);
        transaction.setTransactionType("WITHDRAWAL");
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setBalanceBefore(depositAccount.getBalance());
        transaction.setBalanceAfter(depositAccount.getBalance().subtract(transactionDTO.getAmount()));
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setReferenceNumber(transactionDTO.getReferenceNumber());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus("COMPLETED");
        transaction.setCreatedBy(transactionDTO.getCreatedBy());
        transaction.setCreatedDateTime(LocalDateTime.now());

        // Update account balance
        depositAccount.setBalance(depositAccount.getBalance().subtract(transactionDTO.getAmount()));
        depositAccount.setModifiedBy(transactionDTO.getCreatedBy());
        depositAccount.setModifiedDateTime(LocalDateTime.now());

        // Save transaction and account
        DepositTransaction savedTransaction = transactionRepository.save(transaction);
        depositAccountRepository.save(depositAccount);

        DepositTransactionDTO result = convertTransactionToDTO(savedTransaction);
        logger.info("Withdrawal processed successfully - Transaction ID: {}", result.getTransactionId());

        return result;
    }

    /**
     * Process transfer between accounts
     */
    public DepositTransactionDTO processTransfer(String fromAccountNumber, String toAccountNumber,
            DepositTransactionDTO transactionDTO) {
        logger.info("Processing transfer - From: {}, To: {}, Amount: {}",
                fromAccountNumber, toAccountNumber, transactionDTO.getAmount());

        // Process withdrawal from source account
        DepositTransactionDTO withdrawalTransaction = processWithdrawal(fromAccountNumber, transactionDTO);

        // Process deposit to destination account
        DepositTransactionDTO depositTransaction = processDeposit(toAccountNumber, transactionDTO);

        // Link transactions
        withdrawalTransaction.setReferenceNumber(depositTransaction.getTransactionId());
        depositTransaction.setReferenceNumber(withdrawalTransaction.getTransactionId());

        logger.info("Transfer processed successfully - Withdrawal ID: {}, Deposit ID: {}",
                withdrawalTransaction.getTransactionId(), depositTransaction.getTransactionId());

        return withdrawalTransaction;
    }

    /**
     * Get transaction history for an account
     */
    public List<DepositTransactionDTO> getTransactionHistory(String accountNumber) {
        logger.debug("Getting transaction history for account: {}", accountNumber);

        List<DepositTransaction> transactions = transactionRepository
                .findByAccountNumberOrderByTransactionDateDesc(accountNumber);
        return transactions.stream()
                .map(this::convertTransactionToDTO)
                .toList();
    }

    /**
     * Calculate interest for an account
     */
    public BigDecimal calculateInterest(String accountNumber) {
        logger.debug("Calculating interest for account: {}", accountNumber);

        Optional<DepositAccount> account = depositAccountRepository.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            throw new IllegalArgumentException("Deposit account not found: " + accountNumber);
        }

        DepositAccount depositAccount = account.get();
        if (depositAccount.getInterestRate() == null
                || depositAccount.getInterestRate().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        // Simple interest calculation (can be enhanced for compound interest)
        BigDecimal principal = depositAccount.getBalance();
        BigDecimal rate = depositAccount.getInterestRate().divide(new BigDecimal("100"));

        return principal.multiply(rate);
    }

    /**
     * Validate daily limit
     */
    private void validateDailyLimit(DepositAccount account, BigDecimal amount) {
        if (account.getDailyLimit() != null) {
            BigDecimal dailyTotal = transactionRepository.getDailyTransactionTotal(account.getAccountNumber());
            if (dailyTotal.add(amount).compareTo(account.getDailyLimit()) > 0) {
                throw new IllegalArgumentException("Transaction exceeds daily limit");
            }
        }
    }

    /**
     * Generate unique account number
     */
    private String generateAccountNumber() {
        return "ACC" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
    }

    /**
     * Generate unique transaction ID
     */
    private String generateTransactionId() {
        return "DTXN" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
    }

    /**
     * Convert DepositAccount entity to DTO
     */
    private DepositAccountDTO convertToDTO(DepositAccount account) {
        DepositAccountDTO dto = new DepositAccountDTO();
        dto.setId(account.getId());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setCustomerId(account.getCustomerId());
        dto.setAccountType(account.getAccountType());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        dto.setInterestRate(account.getInterestRate());
        dto.setMinimumBalance(account.getMinimumBalance());
        dto.setDailyLimit(account.getDailyLimit());
        dto.setMonthlyLimit(account.getMonthlyLimit());
        dto.setStatus(account.getStatus());
        dto.setOpenDate(account.getOpenDate());
        dto.setMaturityDate(account.getMaturityDate());
        dto.setCreatedBy(account.getCreatedBy());
        dto.setCreatedDateTime(account.getCreatedDateTime());
        dto.setModifiedBy(account.getModifiedBy());
        dto.setModifiedDateTime(account.getModifiedDateTime());
        return dto;
    }

    /**
     * Convert DepositTransaction entity to DTO
     */
    private DepositTransactionDTO convertTransactionToDTO(DepositTransaction transaction) {
        DepositTransactionDTO dto = new DepositTransactionDTO();
        dto.setId(transaction.getId());
        dto.setTransactionId(transaction.getTransactionId());
        dto.setAccountNumber(transaction.getAccountNumber());
        dto.setTransactionType(transaction.getTransactionType());
        dto.setAmount(transaction.getAmount());
        dto.setCurrency(transaction.getCurrency());
        dto.setBalanceBefore(transaction.getBalanceBefore());
        dto.setBalanceAfter(transaction.getBalanceAfter());
        dto.setDescription(transaction.getDescription());
        dto.setReferenceNumber(transaction.getReferenceNumber());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setStatus(transaction.getStatus());
        dto.setFailureReason(transaction.getFailureReason());
        dto.setCreatedBy(transaction.getCreatedBy());
        dto.setCreatedDateTime(transaction.getCreatedDateTime());
        return dto;
    }

    /**
     * Get deposit account by account number
     */
    public DepositAccountDTO getDepositAccount(String accountNumber) {
        logger.debug("Getting deposit account - Account Number: {}", accountNumber);

        Optional<DepositAccount> account = depositAccountRepository.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            throw new IllegalArgumentException("Deposit account not found: " + accountNumber);
        }

        return convertToDTO(account.get());
    }

    /**
     * Get deposit accounts by customer ID
     */
    public List<DepositAccountDTO> getDepositAccountsByCustomer(String customerId) {
        logger.debug("Getting deposit accounts for customer: {}", customerId);

        List<DepositAccount> accounts = depositAccountRepository.findByCustomerId(customerId);
        return accounts.stream()
                .map(this::convertToDTO)
                .toList();
    }

    /**
     * Update deposit account
     */
    public DepositAccountDTO updateDepositAccount(String accountNumber, DepositAccountDTO accountDTO) {
        logger.info("Updating deposit account - Account Number: {}", accountNumber);

        Optional<DepositAccount> existingAccount = depositAccountRepository.findByAccountNumber(accountNumber);
        if (existingAccount.isEmpty()) {
            throw new IllegalArgumentException("Deposit account not found with number: " + accountNumber);
        }

        DepositAccount account = existingAccount.get();
        account.setAccountType(accountDTO.getAccountType());
        account.setInterestRate(accountDTO.getInterestRate());
        account.setMinimumBalance(accountDTO.getMinimumBalance());
        account.setDailyLimit(accountDTO.getDailyLimit());
        account.setMonthlyLimit(accountDTO.getMonthlyLimit());
        account.setMaturityDate(accountDTO.getMaturityDate());
        account.setModifiedBy(accountDTO.getModifiedBy());
        account.setModifiedDateTime(LocalDateTime.now());

        DepositAccount updatedAccount = depositAccountRepository.save(account);

        DepositAccountDTO result = convertToDTO(updatedAccount);
        logger.info("Deposit account updated successfully - Account Number: {}", result.getAccountNumber());

        return result;
    }

    /**
     * Close deposit account
     */
    public void closeDepositAccount(String accountNumber) {
        logger.info("Closing deposit account - Account Number: {}", accountNumber);

        Optional<DepositAccount> existingAccount = depositAccountRepository.findByAccountNumber(accountNumber);
        if (existingAccount.isEmpty()) {
            throw new IllegalArgumentException("Deposit account not found with number: " + accountNumber);
        }

        DepositAccount account = existingAccount.get();
        if (!"ACTIVE".equals(account.getStatus())) {
            throw new IllegalArgumentException("Deposit account is not active: " + accountNumber);
        }

        if (account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("Cannot close account with positive balance: " + accountNumber);
        }

        account.setStatus("CLOSED");
        account.setCloseDate(LocalDateTime.now());
        account.setModifiedDateTime(LocalDateTime.now());

        depositAccountRepository.save(account);
        logger.info("Deposit account closed successfully - Account Number: {}", accountNumber);
    }

    /**
     * Get transaction by transaction ID
     */
    public DepositTransactionDTO getTransaction(String transactionId) {
        logger.debug("Getting transaction - Transaction ID: {}", transactionId);

        DepositTransaction transaction = transactionRepository.findByTransactionId(transactionId);
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction not found: " + transactionId);
        }

        return convertTransactionToDTO(transaction);
    }

    /**
     * Get account balance
     */
    public BigDecimal getAccountBalance(String accountNumber) {
        logger.debug("Getting account balance - Account Number: {}", accountNumber);

        Optional<DepositAccount> account = depositAccountRepository.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            throw new IllegalArgumentException("Deposit account not found: " + accountNumber);
        }

        return account.get().getBalance();
    }

    /**
     * Get daily transaction total for an account
     */
    public BigDecimal getDailyTransactionTotal(String accountNumber) {
        logger.debug("Getting daily transaction total for account: {}", accountNumber);

        // Validate account exists
        Optional<DepositAccount> account = depositAccountRepository.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            throw new IllegalArgumentException("Deposit account not found: " + accountNumber);
        }

        return transactionRepository.getDailyTransactionTotal(accountNumber);
    }

    /**
     * Get monthly transaction total for an account
     */
    public BigDecimal getMonthlyTransactionTotal(String accountNumber) {
        logger.debug("Getting monthly transaction total for account: {}", accountNumber);

        // Validate account exists
        Optional<DepositAccount> account = depositAccountRepository.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            throw new IllegalArgumentException("Deposit account not found: " + accountNumber);
        }

        return transactionRepository.getMonthlyTransactionTotal(accountNumber);
    }
}