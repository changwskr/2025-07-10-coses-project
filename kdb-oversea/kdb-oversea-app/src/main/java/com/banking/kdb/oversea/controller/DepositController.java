package com.banking.kdb.oversea.controller;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.deposit.business.DepositService;
import com.banking.kdb.oversea.deposit.transfer.DepositAccountDTO;
import com.banking.kdb.oversea.deposit.transfer.DepositTransactionDTO;
import com.banking.kdb.oversea.common.transfer.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST Controller for Deposit operations
 * 
 * Provides REST API endpoints for deposit account management
 * including account creation, deposits, withdrawals, and transfers.
 */
@RestController
@RequestMapping("/api/v1/deposit")
@Tag(name = "Deposit", description = "Deposit account management API")
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
    public ResponseEntity<ApiResponse<DepositAccountDTO>> createDepositAccount(
            @Valid @RequestBody DepositAccountDTO accountDTO) {

        logger.info("Creating deposit account for customer: {}", accountDTO.getCustomerId());

        try {
            DepositAccountDTO createdAccount = depositService.createDepositAccount(accountDTO);

            ApiResponse<DepositAccountDTO> response = ApiResponse.<DepositAccountDTO>builder()
                    .success(true)
                    .message("Deposit account created successfully")
                    .data(createdAccount)
                    .build();

            logger.info("Deposit account created successfully - Account Number: {}", createdAccount.getAccountNumber());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logger.error("Failed to create deposit account: {}", e.getMessage());

            ApiResponse<DepositAccountDTO> response = ApiResponse.<DepositAccountDTO>builder()
                    .success(false)
                    .message("Failed to create deposit account: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get deposit account by account number
     */
    @GetMapping("/accounts/{accountNumber}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get deposit account", description = "Get deposit account information by account number")
    public ResponseEntity<ApiResponse<DepositAccountDTO>> getDepositAccount(
            @Parameter(description = "Account number") @PathVariable String accountNumber) {

        logger.debug("Getting deposit account: {}", accountNumber);

        try {
            var account = depositService.findDepositAccountByNumber(accountNumber);

            if (account.isPresent()) {
                ApiResponse<DepositAccountDTO> response = ApiResponse.<DepositAccountDTO>builder()
                        .success(true)
                        .message("Deposit account found")
                        .data(account.get())
                        .build();

                return ResponseEntity.ok(response);
            } else {
                ApiResponse<DepositAccountDTO> response = ApiResponse.<DepositAccountDTO>builder()
                        .success(false)
                        .message("Deposit account not found")
                        .build();

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            logger.error("Failed to get deposit account: {}", e.getMessage());

            ApiResponse<DepositAccountDTO> response = ApiResponse.<DepositAccountDTO>builder()
                    .success(false)
                    .message("Failed to get deposit account: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get deposit accounts by customer ID
     */
    @GetMapping("/accounts/customer/{customerId}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get customer accounts", description = "Get all deposit accounts for a customer")
    public ResponseEntity<ApiResponse<List<DepositAccountDTO>>> getCustomerAccounts(
            @Parameter(description = "Customer ID") @PathVariable String customerId) {

        logger.debug("Getting deposit accounts for customer: {}", customerId);

        try {
            List<DepositAccountDTO> accounts = depositService.findDepositAccountsByCustomerId(customerId);

            ApiResponse<List<DepositAccountDTO>> response = ApiResponse.<List<DepositAccountDTO>>builder()
                    .success(true)
                    .message("Customer accounts retrieved successfully")
                    .data(accounts)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to get customer accounts: {}", e.getMessage());

            ApiResponse<List<DepositAccountDTO>> response = ApiResponse.<List<DepositAccountDTO>>builder()
                    .success(false)
                    .message("Failed to get customer accounts: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Process deposit transaction
     */
    @PostMapping("/accounts/{accountNumber}/deposit")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Process deposit", description = "Process a deposit transaction")
    public ResponseEntity<ApiResponse<DepositTransactionDTO>> processDeposit(
            @Parameter(description = "Account number") @PathVariable String accountNumber,
            @Valid @RequestBody DepositTransactionDTO transactionDTO) {

        logger.info("Processing deposit for account: {}", accountNumber);

        try {
            transactionDTO.setAccountNumber(accountNumber);
            DepositTransactionDTO processedTransaction = depositService.processDeposit(accountNumber, transactionDTO);

            ApiResponse<DepositTransactionDTO> response = ApiResponse.<DepositTransactionDTO>builder()
                    .success(true)
                    .message("Deposit processed successfully")
                    .data(processedTransaction)
                    .build();

            logger.info("Deposit processed successfully - Transaction ID: {}", processedTransaction.getTransactionId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to process deposit: {}", e.getMessage());

            ApiResponse<DepositTransactionDTO> response = ApiResponse.<DepositTransactionDTO>builder()
                    .success(false)
                    .message("Failed to process deposit: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Process withdrawal transaction
     */
    @PostMapping("/accounts/{accountNumber}/withdrawal")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Process withdrawal", description = "Process a withdrawal transaction")
    public ResponseEntity<ApiResponse<DepositTransactionDTO>> processWithdrawal(
            @Parameter(description = "Account number") @PathVariable String accountNumber,
            @Valid @RequestBody DepositTransactionDTO transactionDTO) {

        logger.info("Processing withdrawal for account: {}", accountNumber);

        try {
            transactionDTO.setAccountNumber(accountNumber);
            DepositTransactionDTO processedTransaction = depositService.processWithdrawal(accountNumber,
                    transactionDTO);

            ApiResponse<DepositTransactionDTO> response = ApiResponse.<DepositTransactionDTO>builder()
                    .success(true)
                    .message("Withdrawal processed successfully")
                    .data(processedTransaction)
                    .build();

            logger.info("Withdrawal processed successfully - Transaction ID: {}",
                    processedTransaction.getTransactionId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to process withdrawal: {}", e.getMessage());

            ApiResponse<DepositTransactionDTO> response = ApiResponse.<DepositTransactionDTO>builder()
                    .success(false)
                    .message("Failed to process withdrawal: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Process account transfer
     */
    @PostMapping("/transfer")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Process transfer", description = "Process a transfer between accounts")
    public ResponseEntity<ApiResponse<DepositTransactionDTO>> processTransfer(
            @RequestParam String fromAccount,
            @RequestParam String toAccount,
            @Valid @RequestBody DepositTransactionDTO transactionDTO) {

        logger.info("Processing transfer from {} to {}", fromAccount, toAccount);

        try {
            DepositTransactionDTO processedTransaction = depositService.processTransfer(fromAccount, toAccount,
                    transactionDTO);

            ApiResponse<DepositTransactionDTO> response = ApiResponse.<DepositTransactionDTO>builder()
                    .success(true)
                    .message("Transfer processed successfully")
                    .data(processedTransaction)
                    .build();

            logger.info("Transfer processed successfully - Transaction ID: {}",
                    processedTransaction.getTransactionId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to process transfer: {}", e.getMessage());

            ApiResponse<DepositTransactionDTO> response = ApiResponse.<DepositTransactionDTO>builder()
                    .success(false)
                    .message("Failed to process transfer: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get transaction history
     */
    @GetMapping("/accounts/{accountNumber}/transactions")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get transaction history", description = "Get transaction history for a deposit account")
    public ResponseEntity<ApiResponse<List<DepositTransactionDTO>>> getTransactionHistory(
            @Parameter(description = "Account number") @PathVariable String accountNumber) {

        logger.debug("Getting transaction history for account: {}", accountNumber);

        try {
            List<DepositTransactionDTO> transactions = depositService.getTransactionHistory(accountNumber);

            ApiResponse<List<DepositTransactionDTO>> response = ApiResponse.<List<DepositTransactionDTO>>builder()
                    .success(true)
                    .message("Transaction history retrieved successfully")
                    .data(transactions)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to get transaction history: {}", e.getMessage());

            ApiResponse<List<DepositTransactionDTO>> response = ApiResponse.<List<DepositTransactionDTO>>builder()
                    .success(false)
                    .message("Failed to get transaction history: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Calculate interest for an account
     */
    @GetMapping("/accounts/{accountNumber}/interest")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Calculate interest", description = "Calculate interest for a deposit account")
    public ResponseEntity<ApiResponse<BigDecimal>> calculateInterest(
            @Parameter(description = "Account number") @PathVariable String accountNumber) {

        logger.debug("Calculating interest for account: {}", accountNumber);

        try {
            BigDecimal interest = depositService.calculateInterest(accountNumber);

            ApiResponse<BigDecimal> response = ApiResponse.<BigDecimal>builder()
                    .success(true)
                    .message("Interest calculated successfully")
                    .data(interest)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to calculate interest: {}", e.getMessage());

            ApiResponse<BigDecimal> response = ApiResponse.<BigDecimal>builder()
                    .success(false)
                    .message("Failed to calculate interest: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}