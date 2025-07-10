package com.banking.service;

import com.banking.model.dto.CashCardRequest;
import com.banking.model.dto.CashCardResponse;
import com.banking.model.dto.TransactionRequest;
import com.banking.model.dto.TransactionResponse;

import java.util.List;

/**
 * CashCard Service Interface
 * 
 * Provides business operations for cash card management including
 * card creation, transaction processing, and balance inquiries.
 */
public interface CashCardService {

    /**
     * Create a new cash card
     */
    CashCardResponse createCashCard(CashCardRequest request);

    /**
     * Get cash card by card number
     */
    CashCardResponse getCashCard(String cardNumber);

    /**
     * Get all cash cards for a customer
     */
    List<CashCardResponse> getCashCardsByCustomer(String customerId);

    /**
     * Process a cash card transaction
     */
    TransactionResponse processTransaction(TransactionRequest request);

    /**
     * Get transaction history for a card
     */
    List<TransactionResponse> getTransactionHistory(String cardNumber);

    /**
     * Update cash card information
     */
    CashCardResponse updateCashCard(String cardNumber, CashCardRequest request);

    /**
     * Deactivate a cash card
     */
    void deactivateCashCard(String cardNumber);
}