package com.banking.kdb.oversea.framework.transaction.service;

import com.banking.kdb.oversea.framework.constant.FrameworkConstants;
import com.banking.kdb.oversea.framework.transaction.dao.TransactionDAO;
import com.banking.kdb.oversea.framework.transaction.delegate.TransactionDelegate;
import com.banking.kdb.oversea.framework.transaction.model.Transaction;
import com.banking.kdb.oversea.framework.transaction.tcf.TransactionControlFramework;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TransactionService
 */
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionDAO transactionDAO;

    @Mock
    private TransactionDelegate transactionDelegate;

    @Mock
    private TransactionControlFramework transactionControlFramework;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction testTransaction;

    @BeforeEach
    void setUp() {
        testTransaction = new Transaction();
        testTransaction.setTransactionId("TXN123456789");
        testTransaction.setTransactionType(FrameworkConstants.TRANSACTION_TYPE_TRANSFER);
        testTransaction.setAmount(new BigDecimal("1000.00"));
        testTransaction.setCurrency("USD");
        testTransaction.setCustomerId("CUST001");
        testTransaction.setSourceAccount("1234567890");
        testTransaction.setDestinationAccount("0987654321");
        testTransaction.setChannel(FrameworkConstants.CHANNEL_API);
        testTransaction.setStatus(FrameworkConstants.TRANSACTION_STATUS_INITIATED);
        testTransaction.setCreatedDateTime(LocalDateTime.now());
    }

    @Test
    void testCreateTransaction_Success() {
        // Given
        when(transactionDelegate.createTransaction(any(Transaction.class))).thenReturn(testTransaction);
        when(transactionDelegate.validateTransaction(any(Transaction.class))).thenReturn(true);

        // When
        Transaction result = transactionService.createTransaction(testTransaction);

        // Then
        assertNotNull(result);
        assertEquals(testTransaction.getTransactionId(), result.getTransactionId());
        assertEquals(testTransaction.getTransactionType(), result.getTransactionType());
        assertEquals(testTransaction.getAmount(), result.getAmount());
        assertEquals(testTransaction.getCurrency(), result.getCurrency());

        verify(transactionDelegate).createTransaction(any(Transaction.class));
        verify(transactionControlFramework).startTransaction(any(Transaction.class));
    }

    @Test
    void testCreateTransaction_WithGeneratedId() {
        // Given
        testTransaction.setTransactionId(null);
        when(transactionDelegate.createTransaction(any(Transaction.class))).thenReturn(testTransaction);
        when(transactionDelegate.validateTransaction(any(Transaction.class))).thenReturn(true);

        // When
        Transaction result = transactionService.createTransaction(testTransaction);

        // Then
        assertNotNull(result);
        assertNotNull(result.getTransactionId());
        assertTrue(result.getTransactionId().startsWith("TXN"));

        verify(transactionDelegate).createTransaction(any(Transaction.class));
        verify(transactionControlFramework).startTransaction(any(Transaction.class));
    }

    @Test
    void testCreateTransaction_ValidationFailed() {
        // Given
        when(transactionDelegate.validateTransaction(any(Transaction.class))).thenReturn(false);

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            transactionService.createTransaction(testTransaction);
        });

        verify(transactionDelegate, never()).createTransaction(any(Transaction.class));
        verify(transactionControlFramework, never()).startTransaction(any(Transaction.class));
    }

    @Test
    void testCreateTransaction_DelegateException() {
        // Given
        when(transactionDelegate.createTransaction(any(Transaction.class)))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            transactionService.createTransaction(testTransaction);
        });

        verify(transactionDelegate).createTransaction(any(Transaction.class));
        verify(transactionControlFramework, never()).startTransaction(any(Transaction.class));
    }

    @Test
    void testProcessTransaction_Success() {
        // Given
        String transactionId = "TXN123456789";
        when(transactionControlFramework.continueTransaction(eq(transactionId), eq("PROCESSING")))
                .thenReturn(testTransaction);

        // When
        Transaction result = transactionService.processTransaction(transactionId);

        // Then
        assertNotNull(result);
        assertEquals(testTransaction.getTransactionId(), result.getTransactionId());

        verify(transactionControlFramework).continueTransaction(transactionId, "PROCESSING");
    }

    @Test
    void testProcessTransaction_Exception() {
        // Given
        String transactionId = "TXN123456789";
        when(transactionControlFramework.continueTransaction(eq(transactionId), eq("PROCESSING")))
                .thenThrow(new RuntimeException("Processing error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            transactionService.processTransaction(transactionId);
        });

        verify(transactionControlFramework).continueTransaction(transactionId, "PROCESSING");
    }

    @Test
    void testFindTransactionById_Success() {
        // Given
        String transactionId = "TXN123456789";
        when(transactionDelegate.findTransactionById(transactionId))
                .thenReturn(Optional.of(testTransaction));

        // When
        Optional<Transaction> result = transactionService.findTransactionById(transactionId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testTransaction.getTransactionId(), result.get().getTransactionId());

        verify(transactionDelegate).findTransactionById(transactionId);
    }

    @Test
    void testFindTransactionById_NotFound() {
        // Given
        String transactionId = "TXN123456789";
        when(transactionDelegate.findTransactionById(transactionId))
                .thenReturn(Optional.empty());

        // When
        Optional<Transaction> result = transactionService.findTransactionById(transactionId);

        // Then
        assertFalse(result.isPresent());

        verify(transactionDelegate).findTransactionById(transactionId);
    }

    @Test
    void testFindTransactionsByCustomerId_Success() {
        // Given
        String customerId = "CUST001";
        List<Transaction> expectedTransactions = List.of(testTransaction);
        when(transactionDelegate.findTransactionsByCustomerId(customerId))
                .thenReturn(expectedTransactions);

        // When
        List<Transaction> result = transactionService.findTransactionsByCustomerId(customerId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTransaction.getTransactionId(), result.get(0).getTransactionId());

        verify(transactionDelegate).findTransactionsByCustomerId(customerId);
    }

    @Test
    void testFindTransactionsByAccount_Success() {
        // Given
        String accountNumber = "1234567890";
        List<Transaction> expectedTransactions = List.of(testTransaction);
        when(transactionDelegate.findTransactionsByAccount(accountNumber))
                .thenReturn(expectedTransactions);

        // When
        List<Transaction> result = transactionService.findTransactionsByAccount(accountNumber);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTransaction.getTransactionId(), result.get(0).getTransactionId());

        verify(transactionDelegate).findTransactionsByAccount(accountNumber);
    }

    @Test
    void testFindTransactionsByStatus_Success() {
        // Given
        String status = FrameworkConstants.TRANSACTION_STATUS_COMPLETED;
        List<Transaction> expectedTransactions = List.of(testTransaction);
        when(transactionDelegate.findTransactionsByStatus(status))
                .thenReturn(expectedTransactions);

        // When
        List<Transaction> result = transactionService.findTransactionsByStatus(status);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTransaction.getTransactionId(), result.get(0).getTransactionId());

        verify(transactionDelegate).findTransactionsByStatus(status);
    }

    @Test
    void testFindTransactionsByType_Success() {
        // Given
        String transactionType = FrameworkConstants.TRANSACTION_TYPE_TRANSFER;
        List<Transaction> expectedTransactions = List.of(testTransaction);
        when(transactionDelegate.findTransactionsByType(transactionType))
                .thenReturn(expectedTransactions);

        // When
        List<Transaction> result = transactionService.findTransactionsByType(transactionType);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTransaction.getTransactionId(), result.get(0).getTransactionId());

        verify(transactionDelegate).findTransactionsByType(transactionType);
    }

    @Test
    void testFindTransactionsByAmountRange_Success() {
        // Given
        BigDecimal minAmount = new BigDecimal("100.00");
        BigDecimal maxAmount = new BigDecimal("10000.00");
        List<Transaction> expectedTransactions = List.of(testTransaction);
        when(transactionDelegate.findTransactionsByAmountRange(minAmount, maxAmount))
                .thenReturn(expectedTransactions);

        // When
        List<Transaction> result = transactionService.findTransactionsByAmountRange(minAmount, maxAmount);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTransaction.getTransactionId(), result.get(0).getTransactionId());

        verify(transactionDelegate).findTransactionsByAmountRange(minAmount, maxAmount);
    }

    @Test
    void testFindTransactionsByDateRange_Success() {
        // Given
        String startDate = "2024-01-01T00:00:00";
        String endDate = "2024-12-31T23:59:59";
        List<Transaction> expectedTransactions = List.of(testTransaction);
        when(transactionDelegate.findTransactionsByDateRange(startDate, endDate))
                .thenReturn(expectedTransactions);

        // When
        List<Transaction> result = transactionService.findTransactionsByDateRange(startDate, endDate);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTransaction.getTransactionId(), result.get(0).getTransactionId());

        verify(transactionDelegate).findTransactionsByDateRange(startDate, endDate);
    }

    @Test
    void testUpdateTransactionStatus_Success() {
        // Given
        String transactionId = "TXN123456789";
        String status = FrameworkConstants.TRANSACTION_STATUS_COMPLETED;
        String modifiedBy = "USER001";
        when(transactionDelegate.updateTransactionStatus(transactionId, status, modifiedBy))
                .thenReturn(testTransaction);

        // When
        Transaction result = transactionService.updateTransactionStatus(transactionId, status, modifiedBy);

        // Then
        assertNotNull(result);
        assertEquals(testTransaction.getTransactionId(), result.getTransactionId());

        verify(transactionDelegate).updateTransactionStatus(transactionId, status, modifiedBy);
    }

    @Test
    void testUpdateTransactionStatus_Exception() {
        // Given
        String transactionId = "TXN123456789";
        String status = FrameworkConstants.TRANSACTION_STATUS_COMPLETED;
        String modifiedBy = "USER001";
        when(transactionDelegate.updateTransactionStatus(transactionId, status, modifiedBy))
                .thenThrow(new RuntimeException("Update error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            transactionService.updateTransactionStatus(transactionId, status, modifiedBy);
        });

        verify(transactionDelegate).updateTransactionStatus(transactionId, status, modifiedBy);
    }

    @Test
    void testAuthorizeTransaction_Success() {
        // Given
        String transactionId = "TXN123456789";
        String authorizedBy = "AUTH001";
        String authorizationLevel = "LEVEL1";
        when(transactionDelegate.authorizeTransaction(transactionId, authorizedBy, authorizationLevel))
                .thenReturn(testTransaction);

        // When
        Transaction result = transactionService.authorizeTransaction(transactionId, authorizedBy, authorizationLevel);

        // Then
        assertNotNull(result);
        assertEquals(testTransaction.getTransactionId(), result.getTransactionId());

        verify(transactionDelegate).authorizeTransaction(transactionId, authorizedBy, authorizationLevel);
        verify(transactionControlFramework).continueTransaction(transactionId, "AUTHORIZATION");
    }

    @Test
    void testAuthorizeTransaction_Exception() {
        // Given
        String transactionId = "TXN123456789";
        String authorizedBy = "AUTH001";
        String authorizationLevel = "LEVEL1";
        when(transactionDelegate.authorizeTransaction(transactionId, authorizedBy, authorizationLevel))
                .thenThrow(new RuntimeException("Authorization error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            transactionService.authorizeTransaction(transactionId, authorizedBy, authorizationLevel);
        });

        verify(transactionDelegate).authorizeTransaction(transactionId, authorizedBy, authorizationLevel);
        verify(transactionControlFramework, never()).continueTransaction(anyString(), anyString());
    }

    @Test
    void testDeclineTransaction_Success() {
        // Given
        String transactionId = "TXN123456789";
        String reason = "Insufficient funds";
        String modifiedBy = "USER001";
        when(transactionDelegate.declineTransaction(transactionId, reason, modifiedBy))
                .thenReturn(testTransaction);

        // When
        Transaction result = transactionService.declineTransaction(transactionId, reason, modifiedBy);

        // Then
        assertNotNull(result);
        assertEquals(testTransaction.getTransactionId(), result.getTransactionId());

        verify(transactionDelegate).declineTransaction(transactionId, reason, modifiedBy);
    }

    @Test
    void testRetryTransaction_Success() {
        // Given
        String transactionId = "TXN123456789";
        String modifiedBy = "USER001";
        when(transactionDelegate.retryTransaction(transactionId, modifiedBy))
                .thenReturn(testTransaction);

        // When
        Transaction result = transactionService.retryTransaction(transactionId, modifiedBy);

        // Then
        assertNotNull(result);
        assertEquals(testTransaction.getTransactionId(), result.getTransactionId());

        verify(transactionDelegate).retryTransaction(transactionId, modifiedBy);
    }

    @Test
    void testCancelTransaction_Success() {
        // Given
        String transactionId = "TXN123456789";
        String reason = "Customer request";
        String modifiedBy = "USER001";
        when(transactionDelegate.cancelTransaction(transactionId, reason, modifiedBy))
                .thenReturn(testTransaction);

        // When
        Transaction result = transactionService.cancelTransaction(transactionId, reason, modifiedBy);

        // Then
        assertNotNull(result);
        assertEquals(testTransaction.getTransactionId(), result.getTransactionId());

        verify(transactionDelegate).cancelTransaction(transactionId, reason, modifiedBy);
        verify(transactionControlFramework).cancelTransaction(transactionId, reason);
    }

    @Test
    void testValidateTransaction_Success() {
        // Given
        when(transactionDelegate.validateTransaction(any(Transaction.class))).thenReturn(true);

        // When
        boolean result = transactionService.validateTransaction(testTransaction);

        // Then
        assertTrue(result);

        verify(transactionDelegate).validateTransaction(testTransaction);
    }

    @Test
    void testValidateTransaction_Failed() {
        // Given
        when(transactionDelegate.validateTransaction(any(Transaction.class))).thenReturn(false);

        // When
        boolean result = transactionService.validateTransaction(testTransaction);

        // Then
        assertFalse(result);

        verify(transactionDelegate).validateTransaction(testTransaction);
    }

    @Test
    void testFindPendingTransactions_Success() {
        // Given
        List<Transaction> expectedTransactions = List.of(testTransaction);
        when(transactionDelegate.findPendingTransactions())
                .thenReturn(expectedTransactions);

        // When
        List<Transaction> result = transactionService.findPendingTransactions();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTransaction.getTransactionId(), result.get(0).getTransactionId());

        verify(transactionDelegate).findPendingTransactions();
    }

    @Test
    void testFindTransactionsRequiringAuthorization_Success() {
        // Given
        List<Transaction> expectedTransactions = List.of(testTransaction);
        when(transactionDelegate.findTransactionsRequiringAuthorization())
                .thenReturn(expectedTransactions);

        // When
        List<Transaction> result = transactionService.findTransactionsRequiringAuthorization();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTransaction.getTransactionId(), result.get(0).getTransactionId());

        verify(transactionDelegate).findTransactionsRequiringAuthorization();
    }

    @Test
    void testFindHighPriorityTransactions_Success() {
        // Given
        List<Transaction> expectedTransactions = List.of(testTransaction);
        when(transactionDelegate.findHighPriorityTransactions())
                .thenReturn(expectedTransactions);

        // When
        List<Transaction> result = transactionService.findHighPriorityTransactions();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTransaction.getTransactionId(), result.get(0).getTransactionId());

        verify(transactionDelegate).findHighPriorityTransactions();
    }

    @Test
    void testFindSlowTransactions_Success() {
        // Given
        Long thresholdMs = 5000L;
        List<Transaction> expectedTransactions = List.of(testTransaction);
        when(transactionDelegate.findSlowTransactions(thresholdMs))
                .thenReturn(expectedTransactions);

        // When
        List<Transaction> result = transactionService.findSlowTransactions(thresholdMs);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTransaction.getTransactionId(), result.get(0).getTransactionId());

        verify(transactionDelegate).findSlowTransactions(thresholdMs);
    }

    @Test
    void testFindFailedTransactions_Success() {
        // Given
        List<Transaction> expectedTransactions = List.of(testTransaction);
        when(transactionDelegate.findFailedTransactions())
                .thenReturn(expectedTransactions);

        // When
        List<Transaction> result = transactionService.findFailedTransactions();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTransaction.getTransactionId(), result.get(0).getTransactionId());

        verify(transactionDelegate).findFailedTransactions();
    }

    @Test
    void testFindRetryTransactions_Success() {
        // Given
        List<Transaction> expectedTransactions = List.of(testTransaction);
        when(transactionDelegate.findRetryTransactions())
                .thenReturn(expectedTransactions);

        // When
        List<Transaction> result = transactionService.findRetryTransactions();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTransaction.getTransactionId(), result.get(0).getTransactionId());

        verify(transactionDelegate).findRetryTransactions();
    }

    @Test
    void testGetSystemStatistics_Success() {
        // Given
        Object expectedStats = new Object();
        when(transactionControlFramework.getSystemStatistics()).thenReturn(expectedStats);

        // When
        Object result = transactionService.getSystemStatistics();

        // Then
        assertNotNull(result);

        verify(transactionControlFramework).getSystemStatistics();
    }

    @Test
    void testGetPerformanceSummary_Success() {
        // When
        Object result = transactionService.getPerformanceSummary();

        // Then
        assertNotNull(result);
    }

    @Test
    void testGetTransactionMonitoringData_Success() {
        // Given
        String transactionId = "TXN123456789";

        // When
        Object result = transactionService.getTransactionMonitoringData(transactionId);

        // Then
        assertNotNull(result);
    }

    @Test
    void testClearAllMonitoringData_Success() {
        // When
        transactionService.clearAllMonitoringData();

        // Then
        // No exception should be thrown
    }

    @Test
    void testResetSystemStatistics_Success() {
        // When
        transactionService.resetSystemStatistics();

        // Then
        // No exception should be thrown
    }

    @Test
    void testShutdown_Success() {
        // When
        transactionService.shutdown();

        // Then
        // No exception should be thrown
    }
}