package com.banking.kdb.oversea.deposit.controller;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.deposit.business.DepositService;
import com.banking.kdb.oversea.deposit.transfer.DepositAccountDTO;
import com.banking.kdb.oversea.deposit.transfer.DepositTransactionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Deposit REST Controller for KDB Oversea
 * 
 * Provides REST API endpoints for deposit operations including
 * account management and transaction processing.
 */
@RestController
@RequestMapping("/api/v1/deposits")
@Validated
@Tag(name = "Deposit Management", description = "Deposit account operations and transaction management")
public class DepositController {

    private static final FoundationLogger logger = FoundationLogger.getLogger(DepositController.class);

    @Autowired
    private DepositService depositService;

    /**
     * Create a new deposit account
     */
    @PostMapping("/accounts")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Create deposit account", description = "Create a new deposit account for a customer")
    public ResponseEntity<DepositAccountDTO> createDepositAccount(
            @Parameter(description = "Deposit account information", required = true) @Valid @RequestBody DepositAccountDTO accountDTO) {

        logger.info("Creating deposit account - Customer ID: {}", accountDTO.getCustomerId());

        try {
            DepositAccountDTO createdAccount = depositService.createDepositAccount(accountDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to create deposit account: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error creating deposit account", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get deposit account by account number
     */
    @GetMapping("/accounts/{accountNumber}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get deposit account", description = "Get deposit account information by account number")
    public ResponseEntity<DepositAccountDTO> getDepositAccount(
            @Parameter(description = "Account number", required = true) @PathVariable String accountNumber) {

        logger.debug("Getting deposit account - Account Number: {}", accountNumber);

        try {
            DepositAccountDTO account = depositService.getDepositAccount(accountNumber);
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to get deposit account: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting deposit account", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get deposit accounts by customer ID
     */
    @GetMapping("/accounts/customer/{customerId}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get customer deposit accounts", description = "Get all deposit accounts for a customer")
    public ResponseEntity<List<DepositAccountDTO>> getDepositAccountsByCustomer(
            @Parameter(description = "Customer ID", required = true) @PathVariable String customerId) {

        logger.debug("Getting deposit accounts for customer: {}", customerId);

        try {
            List<DepositAccountDTO> accounts = depositService.getDepositAccountsByCustomer(customerId);
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            logger.error("Unexpected error getting customer deposit accounts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update deposit account
     */
    @PutMapping("/accounts/{accountNumber}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Update deposit account", description = "Update deposit account information")
    public ResponseEntity<DepositAccountDTO> updateDepositAccount(
            @Parameter(description = "Account number", required = true) @PathVariable String accountNumber,
            @Parameter(description = "Updated account information", required = true) @Valid @RequestBody DepositAccountDTO accountDTO) {

        logger.info("Updating deposit account - Account Number: {}", accountNumber);

        try {
            DepositAccountDTO updatedAccount = depositService.updateDepositAccount(accountNumber, accountDTO);
            return ResponseEntity.ok(updatedAccount);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to update deposit account: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error updating deposit account", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Close deposit account
     */
    @PutMapping("/accounts/{accountNumber}/close")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Close deposit account", description = "Close a deposit account")
    public ResponseEntity<Void> closeDepositAccount(
            @Parameter(description = "Account number", required = true) @PathVariable String accountNumber) {

        logger.info("Closing deposit account - Account Number: {}", accountNumber);

        try {
            depositService.closeDepositAccount(accountNumber);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            logger.error("Failed to close deposit account: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error closing deposit account", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Process deposit transaction
     */
    @PostMapping("/accounts/{accountNumber}/deposits")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Process deposit", description = "Process a deposit transaction")
    public ResponseEntity<DepositTransactionDTO> processDeposit(
            @Parameter(description = "Account number", required = true) @PathVariable String accountNumber,
            @Parameter(description = "Transaction information", required = true) @Valid @RequestBody DepositTransactionDTO transactionDTO) {

        logger.info("Processing deposit - Account: {}, Amount: {}", accountNumber, transactionDTO.getAmount());

        try {
            DepositTransactionDTO result = depositService.processDeposit(accountNumber, transactionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to process deposit: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error processing deposit", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Process withdrawal transaction
     */
    @PostMapping("/accounts/{accountNumber}/withdrawals")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Process withdrawal", description = "Process a withdrawal transaction")
    public ResponseEntity<DepositTransactionDTO> processWithdrawal(
            @Parameter(description = "Account number", required = true) @PathVariable String accountNumber,
            @Parameter(description = "Transaction information", required = true) @Valid @RequestBody DepositTransactionDTO transactionDTO) {

        logger.info("Processing withdrawal - Account: {}, Amount: {}", accountNumber, transactionDTO.getAmount());

        try {
            DepositTransactionDTO result = depositService.processWithdrawal(accountNumber, transactionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to process withdrawal: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error processing withdrawal", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Process transfer between accounts
     */
    @PostMapping("/transfers")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Process transfer", description = "Process a transfer between accounts")
    public ResponseEntity<DepositTransactionDTO> processTransfer(
            @Parameter(description = "From account number", required = true) @RequestParam String fromAccount,
            @Parameter(description = "To account number", required = true) @RequestParam String toAccount,
            @Parameter(description = "Transaction information", required = true) @Valid @RequestBody DepositTransactionDTO transactionDTO) {

        logger.info("Processing transfer - From: {}, To: {}, Amount: {}", fromAccount, toAccount,
                transactionDTO.getAmount());

        try {
            DepositTransactionDTO result = depositService.processTransfer(fromAccount, toAccount, transactionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to process transfer: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error processing transfer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transaction history for an account
     */
    @GetMapping("/accounts/{accountNumber}/transactions")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get transaction history", description = "Get transaction history for a deposit account")
    public ResponseEntity<List<DepositTransactionDTO>> getTransactionHistory(
            @Parameter(description = "Account number", required = true) @PathVariable String accountNumber) {

        logger.debug("Getting transaction history for account: {}", accountNumber);

        try {
            List<DepositTransactionDTO> transactions = depositService.getTransactionHistory(accountNumber);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to get transaction history: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting transaction history", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transaction by transaction ID
     */
    @GetMapping("/transactions/{transactionId}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get transaction", description = "Get transaction details by transaction ID")
    public ResponseEntity<DepositTransactionDTO> getTransaction(
            @Parameter(description = "Transaction ID", required = true) @PathVariable String transactionId) {

        logger.debug("Getting transaction - Transaction ID: {}", transactionId);

        try {
            DepositTransactionDTO transaction = depositService.getTransaction(transactionId);
            return ResponseEntity.ok(transaction);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to get transaction: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting transaction", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get account balance
     */
    @GetMapping("/accounts/{accountNumber}/balance")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get account balance", description = "Get current balance of a deposit account")
    public ResponseEntity<BigDecimal> getAccountBalance(
            @Parameter(description = "Account number", required = true) @PathVariable String accountNumber) {

        logger.debug("Getting account balance - Account Number: {}", accountNumber);

        try {
            BigDecimal balance = depositService.getAccountBalance(accountNumber);
            return ResponseEntity.ok(balance);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to get account balance: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting account balance", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get daily transaction total for an account
     */
    @GetMapping("/accounts/{accountNumber}/daily-total")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get daily transaction total", description = "Get total transaction amount for today")
    public ResponseEntity<BigDecimal> getDailyTransactionTotal(
            @Parameter(description = "Account number", required = true) @PathVariable String accountNumber) {

        logger.debug("Getting daily transaction total for account: {}", accountNumber);

        try {
            BigDecimal total = depositService.getDailyTransactionTotal(accountNumber);
            return ResponseEntity.ok(total);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to get daily transaction total: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting daily transaction total", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get monthly transaction total for an account
     */
    @GetMapping("/accounts/{accountNumber}/monthly-total")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get monthly transaction total", description = "Get total transaction amount for current month")
    public ResponseEntity<BigDecimal> getMonthlyTransactionTotal(
            @Parameter(description = "Account number", required = true) @PathVariable String accountNumber) {

        logger.debug("Getting monthly transaction total for account: {}", accountNumber);

        try {
            BigDecimal total = depositService.getMonthlyTransactionTotal(accountNumber);
            return ResponseEntity.ok(total);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to get monthly transaction total: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting monthly transaction total", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if the deposit service is running")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Deposit service is running");
    }
}