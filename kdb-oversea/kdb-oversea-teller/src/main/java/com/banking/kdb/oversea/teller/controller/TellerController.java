package com.banking.kdb.oversea.teller.controller;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.teller.business.TellerService;
import com.banking.kdb.oversea.teller.transfer.TellerSessionDTO;
import com.banking.kdb.oversea.teller.transfer.TellerTransactionDTO;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Teller REST Controller for KDB Oversea
 * 
 * Provides REST API endpoints for teller operations including
 * session management, customer service, and transaction processing.
 */
@RestController
@RequestMapping("/api/v1/teller")
@Validated
@Tag(name = "Teller Management", description = "Teller operations and session management")
public class TellerController {

    private static final FoundationLogger logger = FoundationLogger.getLogger(TellerController.class);

    @Autowired
    private TellerService tellerService;

    /**
     * Start teller session
     */
    @PostMapping("/sessions")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Start teller session", description = "Start a new teller session for the specified teller")
    public ResponseEntity<TellerSessionDTO> startTellerSession(
            @Parameter(description = "Teller ID", required = true) @RequestParam String tellerId,
            @Parameter(description = "Branch code", required = true) @RequestParam String branchCode) {

        logger.info("Starting teller session - Teller ID: {}, Branch: {}", tellerId, branchCode);

        try {
            TellerSessionDTO session = tellerService.startTellerSession(tellerId, branchCode);
            return ResponseEntity.status(HttpStatus.CREATED).body(session);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to start teller session: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error starting teller session", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * End teller session
     */
    @PutMapping("/sessions/{sessionId}/end")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "End teller session", description = "End an active teller session")
    public ResponseEntity<Void> endTellerSession(
            @Parameter(description = "Session ID", required = true) @PathVariable String sessionId,
            @Parameter(description = "Teller ID", required = true) @RequestParam String tellerId) {

        logger.info("Ending teller session - Session ID: {}, Teller ID: {}", sessionId, tellerId);

        try {
            tellerService.endTellerSession(sessionId, tellerId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            logger.error("Failed to end teller session: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error ending teller session", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get active teller session
     */
    @GetMapping("/sessions/active")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Get active teller session", description = "Get the active session for the specified teller")
    public ResponseEntity<TellerSessionDTO> getActiveTellerSession(
            @Parameter(description = "Teller ID", required = true) @RequestParam String tellerId) {

        logger.debug("Getting active teller session for teller: {}", tellerId);

        try {
            Optional<TellerSessionDTO> session = tellerService.getActiveTellerSession(tellerId);
            return session.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Unexpected error getting active teller session", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get teller session summary
     */
    @GetMapping("/sessions/{sessionId}")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Get teller session summary", description = "Get summary information for a specific session")
    public ResponseEntity<TellerSessionDTO> getTellerSessionSummary(
            @Parameter(description = "Session ID", required = true) @PathVariable String sessionId) {

        logger.debug("Getting teller session summary for session: {}", sessionId);

        try {
            TellerSessionDTO session = tellerService.getTellerSessionSummary(sessionId);
            return ResponseEntity.ok(session);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to get teller session summary: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting teller session summary", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get customer information
     */
    @GetMapping("/customers/{customerId}")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Get customer information", description = "Get customer information for teller inquiry")
    public ResponseEntity<Customer> getCustomerInformation(
            @Parameter(description = "Customer ID", required = true) @PathVariable String customerId,
            @Parameter(description = "Session ID", required = true) @RequestParam String sessionId) {

        logger.info("Getting customer information - Customer ID: {}, Session ID: {}", customerId, sessionId);

        try {
            Customer customer = tellerService.getCustomerInformation(customerId, sessionId);
            return ResponseEntity.ok(customer);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to get customer information: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting customer information", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Create cash card for customer
     */
    @PostMapping("/cashcards")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Create cash card", description = "Create a new cash card for a customer")
    public ResponseEntity<CashCardDTO> createCashCard(
            @Parameter(description = "Cash card information", required = true) @Valid @RequestBody CashCardDTO cashCardDTO,
            @Parameter(description = "Session ID", required = true) @RequestParam String sessionId) {

        logger.info("Creating cash card - Customer ID: {}, Session ID: {}", cashCardDTO.getCustomerId(), sessionId);

        try {
            CashCardDTO createdCard = tellerService.createCashCardForCustomer(cashCardDTO, sessionId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCard);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to create cash card: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error creating cash card", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Create deposit account for customer
     */
    @PostMapping("/deposit-accounts")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Create deposit account", description = "Create a new deposit account for a customer")
    public ResponseEntity<DepositAccountDTO> createDepositAccount(
            @Parameter(description = "Deposit account information", required = true) @Valid @RequestBody DepositAccountDTO accountDTO,
            @Parameter(description = "Session ID", required = true) @RequestParam String sessionId) {

        logger.info("Creating deposit account - Customer ID: {}, Session ID: {}", accountDTO.getCustomerId(),
                sessionId);

        try {
            DepositAccountDTO createdAccount = tellerService.createDepositAccountForCustomer(accountDTO, sessionId);
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
     * Process cash deposit
     */
    @PostMapping("/deposits/cash")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Process cash deposit", description = "Process a cash deposit transaction")
    public ResponseEntity<DepositTransactionDTO> processCashDeposit(
            @Parameter(description = "Account number", required = true) @RequestParam String accountNumber,
            @Parameter(description = "Amount", required = true) @RequestParam BigDecimal amount,
            @Parameter(description = "Currency", required = true) @RequestParam String currency,
            @Parameter(description = "Session ID", required = true) @RequestParam String sessionId,
            @Parameter(description = "Teller ID", required = true) @RequestParam String tellerId) {

        logger.info("Processing cash deposit - Account: {}, Amount: {}, Session: {}", accountNumber, amount, sessionId);

        try {
            DepositTransactionDTO transaction = tellerService.processCashDeposit(accountNumber, amount, currency,
                    sessionId, tellerId);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to process cash deposit: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error processing cash deposit", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Process cash withdrawal
     */
    @PostMapping("/withdrawals/cash")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Process cash withdrawal", description = "Process a cash withdrawal transaction")
    public ResponseEntity<DepositTransactionDTO> processCashWithdrawal(
            @Parameter(description = "Account number", required = true) @RequestParam String accountNumber,
            @Parameter(description = "Amount", required = true) @RequestParam BigDecimal amount,
            @Parameter(description = "Currency", required = true) @RequestParam String currency,
            @Parameter(description = "Session ID", required = true) @RequestParam String sessionId,
            @Parameter(description = "Teller ID", required = true) @RequestParam String tellerId) {

        logger.info("Processing cash withdrawal - Account: {}, Amount: {}, Session: {}", accountNumber, amount,
                sessionId);

        try {
            DepositTransactionDTO transaction = tellerService.processCashWithdrawal(accountNumber, amount, currency,
                    sessionId, tellerId);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to process cash withdrawal: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error processing cash withdrawal", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Process account transfer
     */
    @PostMapping("/transfers")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Process account transfer", description = "Process a transfer between accounts")
    public ResponseEntity<DepositTransactionDTO> processAccountTransfer(
            @Parameter(description = "From account number", required = true) @RequestParam String fromAccount,
            @Parameter(description = "To account number", required = true) @RequestParam String toAccount,
            @Parameter(description = "Amount", required = true) @RequestParam BigDecimal amount,
            @Parameter(description = "Currency", required = true) @RequestParam String currency,
            @Parameter(description = "Session ID", required = true) @RequestParam String sessionId,
            @Parameter(description = "Teller ID", required = true) @RequestParam String tellerId) {

        logger.info("Processing account transfer - From: {}, To: {}, Amount: {}, Session: {}",
                fromAccount, toAccount, amount, sessionId);

        try {
            DepositTransactionDTO transaction = tellerService.processAccountTransfer(fromAccount, toAccount, amount,
                    currency, sessionId, tellerId);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to process account transfer: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error processing account transfer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Process cash card transaction
     */
    @PostMapping("/cashcard-transactions")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Process cash card transaction", description = "Process a cash card transaction")
    public ResponseEntity<CashCardTransactionDTO> processCashCardTransaction(
            @Parameter(description = "Cash card transaction information", required = true) @Valid @RequestBody CashCardTransactionDTO transactionDTO,
            @Parameter(description = "Session ID", required = true) @RequestParam String sessionId) {

        logger.info("Processing cash card transaction - Card: {}, Amount: {}, Session: {}",
                transactionDTO.getCardNumber(), transactionDTO.getAmount(), sessionId);

        try {
            CashCardTransactionDTO result = tellerService.processCashCardTransaction(transactionDTO, sessionId);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to process cash card transaction: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error processing cash card transaction", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get teller transaction history
     */
    @GetMapping("/sessions/{sessionId}/transactions")
    @PreAuthorize("hasRole('TELLER')")
    @Operation(summary = "Get transaction history", description = "Get transaction history for a teller session")
    public ResponseEntity<List<TellerTransactionDTO>> getTellerTransactionHistory(
            @Parameter(description = "Session ID", required = true) @PathVariable String sessionId) {

        logger.debug("Getting transaction history for session: {}", sessionId);

        try {
            List<TellerTransactionDTO> transactions = tellerService.getTellerTransactionHistory(sessionId);
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            logger.error("Unexpected error getting transaction history", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if the teller service is running")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Teller service is running");
    }
}