package com.banking.controller;

import com.banking.model.dto.ApiResponse;
import com.banking.model.dto.CashCardRequest;
import com.banking.model.dto.CashCardResponse;
import com.banking.model.dto.TransactionRequest;
import com.banking.model.dto.TransactionResponse;
import com.banking.service.CashCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * CashCard REST Controller
 * 
 * Provides REST API endpoints for cash card management operations.
 */
@RestController
@RequestMapping("/cashcard")
@Tag(name = "CashCard Management", description = "Cash card management operations")
public class CashCardController {

    @Autowired
    private CashCardService cashCardService;

    @PostMapping
    @Operation(summary = "Create a new cash card", description = "Creates a new cash card for a customer")
    public ResponseEntity<ApiResponse<CashCardResponse>> createCashCard(
            @Valid @RequestBody CashCardRequest request) {
        try {
            CashCardResponse response = cashCardService.createCashCard(request);
            return ResponseEntity.ok(ApiResponse.success("Cash card created successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create cash card: " + e.getMessage()));
        }
    }

    @GetMapping("/{cardNumber}")
    @Operation(summary = "Get cash card by card number", description = "Retrieves cash card information by card number")
    public ResponseEntity<ApiResponse<CashCardResponse>> getCashCard(@PathVariable String cardNumber) {
        try {
            CashCardResponse response = cashCardService.getCashCard(cardNumber);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Cash card not found: " + e.getMessage()));
        }
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get cash cards by customer", description = "Retrieves all cash cards for a specific customer")
    public ResponseEntity<ApiResponse<List<CashCardResponse>>> getCashCardsByCustomer(
            @PathVariable String customerId) {
        try {
            List<CashCardResponse> response = cashCardService.getCashCardsByCustomer(customerId);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve cash cards: " + e.getMessage()));
        }
    }

    @PostMapping("/transaction")
    @Operation(summary = "Process transaction", description = "Processes a cash card transaction")
    public ResponseEntity<ApiResponse<TransactionResponse>> processTransaction(
            @Valid @RequestBody TransactionRequest request) {
        try {
            TransactionResponse response = cashCardService.processTransaction(request);
            return ResponseEntity.ok(ApiResponse.success("Transaction processed successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Transaction failed: " + e.getMessage()));
        }
    }

    @GetMapping("/{cardNumber}/transactions")
    @Operation(summary = "Get transaction history", description = "Retrieves transaction history for a cash card")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> getTransactionHistory(
            @PathVariable String cardNumber) {
        try {
            List<TransactionResponse> response = cashCardService.getTransactionHistory(cardNumber);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve transaction history: " + e.getMessage()));
        }
    }

    @PutMapping("/{cardNumber}")
    @Operation(summary = "Update cash card", description = "Updates cash card information")
    public ResponseEntity<ApiResponse<CashCardResponse>> updateCashCard(
            @PathVariable String cardNumber, @Valid @RequestBody CashCardRequest request) {
        try {
            CashCardResponse response = cashCardService.updateCashCard(cardNumber, request);
            return ResponseEntity.ok(ApiResponse.success("Cash card updated successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update cash card: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{cardNumber}")
    @Operation(summary = "Deactivate cash card", description = "Deactivates a cash card")
    public ResponseEntity<ApiResponse<Void>> deactivateCashCard(@PathVariable String cardNumber) {
        try {
            cashCardService.deactivateCashCard(cardNumber);
            return ResponseEntity.ok(ApiResponse.success("Cash card deactivated successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to deactivate cash card: " + e.getMessage()));
        }
    }
}