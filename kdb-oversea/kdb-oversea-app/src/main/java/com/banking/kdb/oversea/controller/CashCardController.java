package com.banking.kdb.oversea.controller;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.cashcard.business.CashCardService;
import com.banking.kdb.oversea.cashcard.transfer.CashCardDTO;
import com.banking.kdb.oversea.cashcard.transfer.CashCardTransactionDTO;
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

import java.util.List;

/**
 * REST Controller for CashCard operations
 * 
 * Provides REST API endpoints for cash card management
 * including creation, updates, and transaction processing.
 */
@RestController
@RequestMapping("/api/v1/cashcard")
@Tag(name = "CashCard", description = "CashCard management API")
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
    public ResponseEntity<ApiResponse<CashCardDTO>> createCashCard(
            @Valid @RequestBody CashCardDTO cashCardDTO) {

        logger.info("Creating cash card for customer: {}", cashCardDTO.getCustomerId());

        try {
            CashCardDTO createdCard = cashCardService.createCashCard(cashCardDTO);

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
     * Get cash card by card number
     */
    @GetMapping("/{cardNumber}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get cash card", description = "Get cash card information by card number")
    public ResponseEntity<ApiResponse<CashCardDTO>> getCashCard(
            @Parameter(description = "Card number") @PathVariable String cardNumber) {

        logger.debug("Getting cash card: {}", cardNumber);

        try {
            var cashCard = cashCardService.findCashCardByNumber(cardNumber);

            if (cashCard.isPresent()) {
                ApiResponse<CashCardDTO> response = ApiResponse.<CashCardDTO>builder()
                        .success(true)
                        .message("Cash card found")
                        .data(cashCard.get())
                        .build();

                return ResponseEntity.ok(response);
            } else {
                ApiResponse<CashCardDTO> response = ApiResponse.<CashCardDTO>builder()
                        .success(false)
                        .message("Cash card not found")
                        .build();

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            logger.error("Failed to get cash card: {}", e.getMessage());

            ApiResponse<CashCardDTO> response = ApiResponse.<CashCardDTO>builder()
                    .success(false)
                    .message("Failed to get cash card: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get cash cards by customer ID
     */
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get customer cards", description = "Get all cash cards for a customer")
    public ResponseEntity<ApiResponse<List<CashCardDTO>>> getCustomerCards(
            @Parameter(description = "Customer ID") @PathVariable String customerId) {

        logger.debug("Getting cash cards for customer: {}", customerId);

        try {
            List<CashCardDTO> cards = cashCardService.findCashCardsByCustomerId(customerId);

            ApiResponse<List<CashCardDTO>> response = ApiResponse.<List<CashCardDTO>>builder()
                    .success(true)
                    .message("Customer cards retrieved successfully")
                    .data(cards)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to get customer cards: {}", e.getMessage());

            ApiResponse<List<CashCardDTO>> response = ApiResponse.<List<CashCardDTO>>builder()
                    .success(false)
                    .message("Failed to get customer cards: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Update cash card
     */
    @PutMapping("/{cardNumber}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Update cash card", description = "Update cash card information")
    public ResponseEntity<ApiResponse<CashCardDTO>> updateCashCard(
            @Parameter(description = "Card number") @PathVariable String cardNumber,
            @Valid @RequestBody CashCardDTO cashCardDTO) {

        logger.info("Updating cash card: {}", cardNumber);

        try {
            CashCardDTO updatedCard = cashCardService.updateCashCard(cardNumber, cashCardDTO);

            ApiResponse<CashCardDTO> response = ApiResponse.<CashCardDTO>builder()
                    .success(true)
                    .message("Cash card updated successfully")
                    .data(updatedCard)
                    .build();

            logger.info("Cash card updated successfully - Card Number: {}", updatedCard.getCardNumber());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to update cash card: {}", e.getMessage());

            ApiResponse<CashCardDTO> response = ApiResponse.<CashCardDTO>builder()
                    .success(false)
                    .message("Failed to update cash card: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Deactivate cash card
     */
    @DeleteMapping("/{cardNumber}")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN')")
    @Operation(summary = "Deactivate cash card", description = "Deactivate a cash card")
    public ResponseEntity<ApiResponse<Void>> deactivateCashCard(
            @Parameter(description = "Card number") @PathVariable String cardNumber,
            @RequestParam String modifiedBy) {

        logger.info("Deactivating cash card: {}", cardNumber);

        try {
            cashCardService.deactivateCashCard(cardNumber, modifiedBy);

            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .success(true)
                    .message("Cash card deactivated successfully")
                    .build();

            logger.info("Cash card deactivated successfully - Card Number: {}", cardNumber);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to deactivate cash card: {}", e.getMessage());

            ApiResponse<Void> response = ApiResponse.<Void>builder()
                    .success(false)
                    .message("Failed to deactivate cash card: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Process cash card transaction
     */
    @PostMapping("/{cardNumber}/transaction")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Process transaction", description = "Process a cash card transaction")
    public ResponseEntity<ApiResponse<CashCardTransactionDTO>> processTransaction(
            @Parameter(description = "Card number") @PathVariable String cardNumber,
            @Valid @RequestBody CashCardTransactionDTO transactionDTO) {

        logger.info("Processing transaction for card: {}", cardNumber);

        try {
            transactionDTO.setCardNumber(cardNumber);
            CashCardTransactionDTO processedTransaction = cashCardService.processTransaction(transactionDTO);

            ApiResponse<CashCardTransactionDTO> response = ApiResponse.<CashCardTransactionDTO>builder()
                    .success(true)
                    .message("Transaction processed successfully")
                    .data(processedTransaction)
                    .build();

            logger.info("Transaction processed successfully - Transaction ID: {}",
                    processedTransaction.getTransactionId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to process transaction: {}", e.getMessage());

            ApiResponse<CashCardTransactionDTO> response = ApiResponse.<CashCardTransactionDTO>builder()
                    .success(false)
                    .message("Failed to process transaction: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get transaction history
     */
    @GetMapping("/{cardNumber}/transactions")
    @PreAuthorize("hasRole('TELLER') or hasRole('ADMIN') or hasRole('CUSTOMER')")
    @Operation(summary = "Get transaction history", description = "Get transaction history for a cash card")
    public ResponseEntity<ApiResponse<List<CashCardTransactionDTO>>> getTransactionHistory(
            @Parameter(description = "Card number") @PathVariable String cardNumber) {

        logger.debug("Getting transaction history for card: {}", cardNumber);

        try {
            List<CashCardTransactionDTO> transactions = cashCardService.getTransactionHistory(cardNumber);

            ApiResponse<List<CashCardTransactionDTO>> response = ApiResponse.<List<CashCardTransactionDTO>>builder()
                    .success(true)
                    .message("Transaction history retrieved successfully")
                    .data(transactions)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to get transaction history: {}", e.getMessage());

            ApiResponse<List<CashCardTransactionDTO>> response = ApiResponse.<List<CashCardTransactionDTO>>builder()
                    .success(false)
                    .message("Failed to get transaction history: " + e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}