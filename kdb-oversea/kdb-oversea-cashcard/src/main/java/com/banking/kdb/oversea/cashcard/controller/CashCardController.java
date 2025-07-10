package com.banking.kdb.oversea.cashcard.controller;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.cashcard.business.CashCardService;
import com.banking.kdb.oversea.cashcard.transfer.CashCardDTO;
import com.banking.kdb.oversea.cashcard.transfer.CashCardTransactionDTO;
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

import java.util.List;

/**
 * CashCard REST Controller for KDB Oversea
 * 
 * Provides REST API endpoints for cash card operations including
 * card management and transaction processing.
 */
@RestController
@RequestMapping("/api/v1/cashcards")
@Validated
@Tag(name = "CashCard Management", description = "Cash card operations and transaction management")
public class CashCardController {

    private static final FoundationLogger logger = FoundationLogger.getLogger(CashCardController.class);

    @Autowired
    private CashCardService cashCardService;

    /**
     * Create a new cash card
     */
    @PostMapping
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Create cash card", description = "Create a new cash card for a customer")
    public ResponseEntity<CashCardDTO> createCashCard(
            @Parameter(description = "Cash card information", required = true) @Valid @RequestBody CashCardDTO cashCardDTO) {

        logger.info("Creating cash card - Customer ID: {}", cashCardDTO.getCustomerId());

        try {
            CashCardDTO createdCard = cashCardService.createCashCard(cashCardDTO);
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
     * Get cash card by card number
     */
    @GetMapping("/{cardNumber}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get cash card", description = "Get cash card information by card number")
    public ResponseEntity<CashCardDTO> getCashCard(
            @Parameter(description = "Card number", required = true) @PathVariable String cardNumber) {

        logger.debug("Getting cash card - Card Number: {}", cardNumber);

        try {
            CashCardDTO cashCard = cashCardService.getCashCard(cardNumber);
            return ResponseEntity.ok(cashCard);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to get cash card: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error getting cash card", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get cash cards by customer ID
     */
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get customer cash cards", description = "Get all cash cards for a customer")
    public ResponseEntity<List<CashCardDTO>> getCashCardsByCustomer(
            @Parameter(description = "Customer ID", required = true) @PathVariable String customerId) {

        logger.debug("Getting cash cards for customer: {}", customerId);

        try {
            List<CashCardDTO> cashCards = cashCardService.getCashCardsByCustomer(customerId);
            return ResponseEntity.ok(cashCards);
        } catch (Exception e) {
            logger.error("Unexpected error getting customer cash cards", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update cash card
     */
    @PutMapping("/{cardNumber}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Update cash card", description = "Update cash card information")
    public ResponseEntity<CashCardDTO> updateCashCard(
            @Parameter(description = "Card number", required = true) @PathVariable String cardNumber,
            @Parameter(description = "Updated cash card information", required = true) @Valid @RequestBody CashCardDTO cashCardDTO) {

        logger.info("Updating cash card - Card Number: {}", cardNumber);

        try {
            CashCardDTO updatedCard = cashCardService.updateCashCard(cardNumber, cashCardDTO);
            return ResponseEntity.ok(updatedCard);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to update cash card: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error updating cash card", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Deactivate cash card
     */
    @PutMapping("/{cardNumber}/deactivate")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Deactivate cash card", description = "Deactivate a cash card")
    public ResponseEntity<Void> deactivateCashCard(
            @Parameter(description = "Card number", required = true) @PathVariable String cardNumber) {

        logger.info("Deactivating cash card - Card Number: {}", cardNumber);

        try {
            cashCardService.deactivateCashCard(cardNumber);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            logger.error("Failed to deactivate cash card: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error deactivating cash card", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Activate cash card
     */
    @PutMapping("/{cardNumber}/activate")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Activate cash card", description = "Activate a cash card")
    public ResponseEntity<Void> activateCashCard(
            @Parameter(description = "Card number", required = true) @PathVariable String cardNumber) {

        logger.info("Activating cash card - Card Number: {}", cardNumber);

        try {
            cashCardService.activateCashCard(cardNumber);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            logger.error("Failed to activate cash card: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error activating cash card", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Process cash card transaction
     */
    @PostMapping("/transactions")
    @PreAuthorize("hasRole('TELLER') or hasRole('MERCHANT')")
    @Operation(summary = "Process transaction", description = "Process a cash card transaction")
    public ResponseEntity<CashCardTransactionDTO> processTransaction(
            @Parameter(description = "Transaction information", required = true) @Valid @RequestBody CashCardTransactionDTO transactionDTO) {

        logger.info("Processing cash card transaction - Card: {}, Amount: {}",
                transactionDTO.getCardNumber(), transactionDTO.getAmount());

        try {
            CashCardTransactionDTO result = cashCardService.processTransaction(transactionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to process transaction: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error processing transaction", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get transaction history for a card
     */
    @GetMapping("/{cardNumber}/transactions")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get transaction history", description = "Get transaction history for a cash card")
    public ResponseEntity<List<CashCardTransactionDTO>> getTransactionHistory(
            @Parameter(description = "Card number", required = true) @PathVariable String cardNumber) {

        logger.debug("Getting transaction history for card: {}", cardNumber);

        try {
            List<CashCardTransactionDTO> transactions = cashCardService.getTransactionHistory(cardNumber);
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
    public ResponseEntity<CashCardTransactionDTO> getTransaction(
            @Parameter(description = "Transaction ID", required = true) @PathVariable String transactionId) {

        logger.debug("Getting transaction - Transaction ID: {}", transactionId);

        try {
            CashCardTransactionDTO transaction = cashCardService.getTransaction(transactionId);
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
     * Get daily transaction total for a card
     */
    @GetMapping("/{cardNumber}/daily-total")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get daily transaction total", description = "Get total transaction amount for today")
    public ResponseEntity<java.math.BigDecimal> getDailyTransactionTotal(
            @Parameter(description = "Card number", required = true) @PathVariable String cardNumber) {

        logger.debug("Getting daily transaction total for card: {}", cardNumber);

        try {
            java.math.BigDecimal total = cashCardService.getDailyTransactionTotal(cardNumber);
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
     * Get monthly transaction total for a card
     */
    @GetMapping("/{cardNumber}/monthly-total")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get monthly transaction total", description = "Get total transaction amount for current month")
    public ResponseEntity<java.math.BigDecimal> getMonthlyTransactionTotal(
            @Parameter(description = "Card number", required = true) @PathVariable String cardNumber) {

        logger.debug("Getting monthly transaction total for card: {}", cardNumber);

        try {
            java.math.BigDecimal total = cashCardService.getMonthlyTransactionTotal(cardNumber);
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
    @Operation(summary = "Health check", description = "Check if the cash card service is running")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("CashCard service is running");
    }
}