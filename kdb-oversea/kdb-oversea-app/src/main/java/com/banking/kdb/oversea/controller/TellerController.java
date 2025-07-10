package com.banking.kdb.oversea.controller;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.teller.business.TellerService;
import com.banking.kdb.oversea.teller.transfer.TellerSessionDTO;
import com.banking.kdb.oversea.teller.transfer.TellerTransactionDTO;
import com.banking.kdb.oversea.common.transfer.ApiResponse;
import com.banking.kdb.oversea.common.model.Customer;
import com.banking.kdb.oversea.cashcard.transfer.CashCardDTO;
import com.banking.kdb.oversea.cashcard.transfer.CashCardTransactionDTO;
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
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST Controller for Teller operations
 * 
 * Provides REST API endpoints for teller operations
 * including session management and customer service.
 */
@RestController
@RequestMapping("/api/v1/teller")
@Tag(name = "Teller", description = "Teller operations API")
public class TellerController {

    private static final FoundationLogger logger = FoundationLogger.getLogger(TellerController.class);

    @Autowired
    private TellerService tellerService;

    /**
     * Start teller session
     */
    @PostMapping("/sessions")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Start teller session", description = "Start a new teller session")
    public ResponseEntity<ApiResponse<TellerSessionDTO>> startTellerSession(
            @RequestParam String tellerId,
            @RequestParam String branchCode) {

        logger.info("Starting teller session - Teller ID: {}, Branch: {}", tellerId, branchCode);

        try {
            TellerSessionDTO session = tellerService.startTellerSession(tellerId, branchCode);

            ApiResponse<TellerSessionDTO> response = ApiResponse.<TellerSessionDTO>builder()
                    .success(true)
                    .message("Teller session started successfully")
                    .data(session)
                    .build();

            logger.info("Teller session started successfully - Session ID: {}", session.getSessionId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logger.error("Failed to start teller session: {}", e.getMessage());

            ApiResponse<TellerSessionDTO> response = ApiResponse.<TellerSessionDTO>builder()
                    .success(false)
                    .message("Failed to start teller session: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * End teller session
     */
    @DeleteMapping("/sessions/{sessionId}")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "End teller session", description = "End an active teller session")
    public ResponseEntity<ApiResponse<Void>> endTellerSession(
            @Parameter(description = "Session ID") @PathVariable String sessionId,
            @RequestParam String tellerId) {

        logger.info("Ending teller session - Session ID: {}, Teller ID: {}", sessionId, tellerId);

        try {
            tellerService.endTellerSession(sessionId, tellerId);

            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .success(true)
                    .message("Teller session ended successfully")
                    .build();

            logger.info("Teller session ended successfully - Session ID: {}", sessionId);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to end teller session: {}", e.getMessage());

            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .success(false)
                    .message("Failed to end teller session: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get active teller session
     */
    @GetMapping("/sessions/active")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Get active session", description = "Get active teller session for a teller")
    public ResponseEntity<ApiResponse<TellerSessionDTO>> getActiveTellerSession(
            @RequestParam String tellerId) {

        logger.debug("Getting active teller session for teller: {}", tellerId);

        try {
            var session = tellerService.getActiveTellerSession(tellerId);

            if (session.isPresent()) {
                ApiResponse<TellerSessionDTO> response = ApiResponse.<TellerSessionDTO>builder()
                        .success(true)
                        .message("Active teller session found")
                        .data(session.get())
                        .build();

                return ResponseEntity.ok(response);
            } else {
                ApiResponse<TellerSessionDTO> response = ApiResponse.<TellerSessionDTO>builder()
                        .success(false)
                        .message("No active teller session found")
                        .build();

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            logger.error("Failed to get active teller session: {}", e.getMessage());

            ApiResponse<TellerSessionDTO> response = ApiResponse.<TellerSessionDTO>builder()
                    .success(false)
                    .message("Failed to get active teller session: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get customer information
     */
    @GetMapping("/customers/{customerId}")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Get customer information", description = "Get customer information for teller inquiry")
    public ResponseEntity<ApiResponse<Customer>> getCustomerInformation(
            @Parameter(description = "Customer ID") @PathVariable String customerId,
            @RequestParam String sessionId) {

        logger.info("Getting customer information - Customer ID: {}, Session ID: {}", customerId, sessionId);

        try {
            Customer customer = tellerService.getCustomerInformation(customerId, sessionId);

            ApiResponse<Customer> response = ApiResponse.<Customer>builder()
                    .success(true)
                    .message("Customer information retrieved successfully")
                    .data(customer)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to get customer information: {}", e.getMessage());

            ApiResponse<Customer> response = ApiResponse.<Customer>builder()
                    .success(false)
                    .message("Failed to get customer information: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Create cash card for customer
     */
    @PostMapping("/customers/{customerId}/cashcards")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Create cash card", description = "Create a cash card for a customer")
    public ResponseEntity<ApiResponse<CashCardDTO>> createCashCardForCustomer(
            @Parameter(description = "Customer ID") @PathVariable String customerId,
            @RequestParam String sessionId,
            @Valid @RequestBody CashCardDTO cashCardDTO) {

        logger.info("Creating cash card for customer - Customer ID: {}, Session ID: {}", customerId, sessionId);

        try {
            cashCardDTO.setCustomerId(customerId);
            CashCardDTO createdCard = tellerService.createCashCardForCustomer(cashCardDTO, sessionId);

            ApiResponse<CashCardDTO> response = ApiResponse.<CashCardDTO>builder()
                    .success(true)
                    .message("Cash card created successfully")
                    .data(createdCard)
                    .build();

            logger.info("Cash card created successfully - Card Number: {}", createdCard.getCardNumber());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logger.error("Failed to create cash card: {}", e.getMessage());

            ApiResponse<CashCardDTO> response = ApiResponse.<CashCardDTO>builder()
                    .success(false)
                    .message("Failed to create cash card: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Create deposit account for customer
     */
    @PostMapping("/customers/{customerId}/deposit-accounts")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Create deposit account", description = "Create a deposit account for a customer")
    public ResponseEntity<ApiResponse<DepositAccountDTO>> createDepositAccountForCustomer(
            @Parameter(description = "Customer ID") @PathVariable String customerId,
            @RequestParam String sessionId,
            @Valid @RequestBody DepositAccountDTO accountDTO) {

        logger.info("Creating deposit account for customer - Customer ID: {}, Session ID: {}", customerId, sessionId);

        try {
            accountDTO.setCustomerId(customerId);
            DepositAccountDTO createdAccount = tellerService.createDepositAccountForCustomer(accountDTO, sessionId);

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
     * Process cash deposit
     */
    @PostMapping("/deposits")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Process cash deposit", description = "Process a cash deposit transaction")
    public ResponseEntity<ApiResponse<DepositTransactionDTO>> processCashDeposit(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam String currency,
            @RequestParam String sessionId,
            @RequestParam String tellerId) {

        logger.info("Processing cash deposit - Account: {}, Amount: {}, Session: {}", accountNumber, amount, sessionId);

        try {
            DepositTransactionDTO result = tellerService.processCashDeposit(accountNumber, amount, currency, sessionId,
                    tellerId);

            ApiResponse<DepositTransactionDTO> response = ApiResponse.<DepositTransactionDTO>builder()
                    .success(true)
                    .message("Cash deposit processed successfully")
                    .data(result)
                    .build();

            logger.info("Cash deposit processed successfully - Transaction ID: {}", result.getTransactionId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to process cash deposit: {}", e.getMessage());

            ApiResponse<DepositTransactionDTO> response = ApiResponse.<DepositTransactionDTO>builder()
                    .success(false)
                    .message("Failed to process cash deposit: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Process cash withdrawal
     */
    @PostMapping("/withdrawals")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Process cash withdrawal", description = "Process a cash withdrawal transaction")
    public ResponseEntity<ApiResponse<DepositTransactionDTO>> processCashWithdrawal(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam String currency,
            @RequestParam String sessionId,
            @RequestParam String tellerId) {

        logger.info("Processing cash withdrawal - Account: {}, Amount: {}, Session: {}", accountNumber, amount,
                sessionId);

        try {
            DepositTransactionDTO result = tellerService.processCashWithdrawal(accountNumber, amount, currency,
                    sessionId, tellerId);

            ApiResponse<DepositTransactionDTO> response = ApiResponse.<DepositTransactionDTO>builder()
                    .success(true)
                    .message("Cash withdrawal processed successfully")
                    .data(result)
                    .build();

            logger.info("Cash withdrawal processed successfully - Transaction ID: {}", result.getTransactionId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to process cash withdrawal: {}", e.getMessage());

            ApiResponse<DepositTransactionDTO> response = ApiResponse.<DepositTransactionDTO>builder()
                    .success(false)
                    .message("Failed to process cash withdrawal: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Process account transfer
     */
    @PostMapping("/transfers")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Process account transfer", description = "Process a transfer between accounts")
    public ResponseEntity<ApiResponse<DepositTransactionDTO>> processAccountTransfer(
            @RequestParam String fromAccount,
            @RequestParam String toAccount,
            @RequestParam BigDecimal amount,
            @RequestParam String currency,
            @RequestParam String sessionId,
            @RequestParam String tellerId) {

        logger.info("Processing account transfer - From: {}, To: {}, Amount: {}, Session: {}",
                fromAccount, toAccount, amount, sessionId);

        try {
            DepositTransactionDTO result = tellerService.processAccountTransfer(fromAccount, toAccount, amount,
                    currency, sessionId, tellerId);

            ApiResponse<DepositTransactionDTO> response = ApiResponse.<DepositTransactionDTO>builder()
                    .success(true)
                    .message("Account transfer processed successfully")
                    .data(result)
                    .build();

            logger.info("Account transfer processed successfully - Transaction ID: {}", result.getTransactionId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to process account transfer: {}", e.getMessage());

            ApiResponse<DepositTransactionDTO> response = ApiResponse.<DepositTransactionDTO>builder()
                    .success(false)
                    .message("Failed to process account transfer: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Process cash card transaction
     */
    @PostMapping("/cashcard-transactions")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Process cash card transaction", description = "Process a cash card transaction")
    public ResponseEntity<ApiResponse<CashCardTransactionDTO>> processCashCardTransaction(
            @RequestParam String sessionId,
            @Valid @RequestBody CashCardTransactionDTO transactionDTO) {

        logger.info("Processing cash card transaction - Card: {}, Amount: {}, Session: {}",
                transactionDTO.getCardNumber(), transactionDTO.getAmount(), sessionId);

        try {
            CashCardTransactionDTO result = tellerService.processCashCardTransaction(transactionDTO, sessionId);

            ApiResponse<CashCardTransactionDTO> response = ApiResponse.<CashCardTransactionDTO>builder()
                    .success(true)
                    .message("Cash card transaction processed successfully")
                    .data(result)
                    .build();

            logger.info("Cash card transaction processed successfully - Transaction ID: {}", result.getTransactionId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to process cash card transaction: {}", e.getMessage());

            ApiResponse<CashCardTransactionDTO> response = ApiResponse.<CashCardTransactionDTO>builder()
                    .success(false)
                    .message("Failed to process cash card transaction: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get teller transaction history
     */
    @GetMapping("/sessions/{sessionId}/transactions")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Get transaction history", description = "Get transaction history for a teller session")
    public ResponseEntity<ApiResponse<List<TellerTransactionDTO>>> getTellerTransactionHistory(
            @Parameter(description = "Session ID") @PathVariable String sessionId) {

        logger.debug("Getting teller transaction history for session: {}", sessionId);

        try {
            List<TellerTransactionDTO> transactions = tellerService.getTellerTransactionHistory(sessionId);

            ApiResponse<List<TellerTransactionDTO>> response = ApiResponse.<List<TellerTransactionDTO>>builder()
                    .success(true)
                    .message("Teller transaction history retrieved successfully")
                    .data(transactions)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to get teller transaction history: {}", e.getMessage());

            ApiResponse<List<TellerTransactionDTO>> response = ApiResponse.<List<TellerTransactionDTO>>builder()
                    .success(false)
                    .message("Failed to get teller transaction history: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get teller session summary
     */
    @GetMapping("/sessions/{sessionId}/summary")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Get session summary", description = "Get summary information for a teller session")
    public ResponseEntity<ApiResponse<TellerSessionDTO>> getTellerSessionSummary(
            @Parameter(description = "Session ID") @PathVariable String sessionId) {

        logger.debug("Getting teller session summary for session: {}", sessionId);

        try {
            TellerSessionDTO summary = tellerService.getTellerSessionSummary(sessionId);

            ApiResponse<TellerSessionDTO> response = ApiResponse.<TellerSessionDTO>builder()
                    .success(true)
                    .message("Teller session summary retrieved successfully")
                    .data(summary)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to get teller session summary: {}", e.getMessage());

            ApiResponse<TellerSessionDTO> response = ApiResponse.<TellerSessionDTO>builder()
                    .success(false)
                    .message("Failed to get teller session summary: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}