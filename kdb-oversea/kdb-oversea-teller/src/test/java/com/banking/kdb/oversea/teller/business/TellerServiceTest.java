package com.banking.kdb.oversea.teller.business;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.teller.model.TellerSession;
import com.banking.kdb.oversea.teller.model.TellerTransaction;
import com.banking.kdb.oversea.teller.repository.TellerSessionRepository;
import com.banking.kdb.oversea.teller.repository.TellerTransactionRepository;
import com.banking.kdb.oversea.teller.transfer.TellerSessionDTO;
import com.banking.kdb.oversea.teller.transfer.TellerTransactionDTO;
import com.banking.kdb.oversea.common.business.CustomerService;
import com.banking.kdb.oversea.cashcard.business.CashCardService;
import com.banking.kdb.oversea.deposit.business.DepositService;
import com.banking.kdb.oversea.common.model.Customer;
import com.banking.kdb.oversea.cashcard.transfer.CashCardDTO;
import com.banking.kdb.oversea.cashcard.transfer.CashCardTransactionDTO;
import com.banking.kdb.oversea.deposit.transfer.DepositAccountDTO;
import com.banking.kdb.oversea.deposit.transfer.DepositTransactionDTO;
import com.banking.kdb.oversea.foundation.config.KdbConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TellerService
 */
@ExtendWith(MockitoExtension.class)
class TellerServiceTest {

    @Mock
    private TellerSessionRepository tellerSessionRepository;

    @Mock
    private TellerTransactionRepository tellerTransactionRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private CashCardService cashCardService;

    @Mock
    private DepositService depositService;

    @Mock
    private KdbConfig kdbConfig;

    @InjectMocks
    private TellerService tellerService;

    private TellerSession testTellerSession;
    private TellerSessionDTO testTellerSessionDTO;
    private Customer testCustomer;
    private CashCardDTO testCashCardDTO;
    private DepositAccountDTO testDepositAccountDTO;

    @BeforeEach
    void setUp() {
        // Setup test teller session
        testTellerSession = new TellerSession();
        testTellerSession.setId(1L);
        testTellerSession.setSessionId("SESS123456789ABC");
        testTellerSession.setTellerId("TELLER001");
        testTellerSession.setBranchCode("BR001");
        testTellerSession.setStatus("ACTIVE");
        testTellerSession.setStartTime(LocalDateTime.now());
        testTellerSession.setCreatedBy("TELLER001");

        // Setup test customer
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setCustomerId("CUST12345678");
        testCustomer.setName("홍길동");
        testCustomer.setEmail("hong@example.com");
        testCustomer.setStatus("ACTIVE");

        // Setup test cash card DTO
        testCashCardDTO = new CashCardDTO();
        testCashCardDTO.setCustomerId("CUST12345678");
        testCashCardDTO.setCardType("DEBIT");
        testCashCardDTO.setDailyLimit(new BigDecimal("1000000"));
        testCashCardDTO.setMonthlyLimit(new BigDecimal("10000000"));
        testCashCardDTO.setCurrency("KRW");
        testCashCardDTO.setCreatedBy("TELLER001");

        // Setup test deposit account DTO
        testDepositAccountDTO = new DepositAccountDTO();
        testDepositAccountDTO.setCustomerId("CUST12345678");
        testDepositAccountDTO.setAccountType("SAVINGS");
        testDepositAccountDTO.setInitialBalance(new BigDecimal("1000000"));
        testDepositAccountDTO.setCurrency("KRW");
        testDepositAccountDTO.setInterestRate(new BigDecimal("2.5"));
        testDepositAccountDTO.setCreatedBy("TELLER001");
    }

    @Test
    void testStartTellerSession_Success() {
        // Given
        when(tellerSessionRepository.findByTellerIdAndStatus("TELLER001", "ACTIVE")).thenReturn(Optional.empty());
        when(tellerSessionRepository.save(any(TellerSession.class))).thenReturn(testTellerSession);

        // When
        TellerSessionDTO result = tellerService.startTellerSession("TELLER001", "BR001");

        // Then
        assertNotNull(result);
        assertEquals("TELLER001", result.getTellerId());
        assertEquals("BR001", result.getBranchCode());
        assertEquals("ACTIVE", result.getStatus());

        verify(tellerSessionRepository).findByTellerIdAndStatus("TELLER001", "ACTIVE");
        verify(tellerSessionRepository).save(any(TellerSession.class));
    }

    @Test
    void testStartTellerSession_AlreadyActiveSession() {
        // Given
        when(tellerSessionRepository.findByTellerIdAndStatus("TELLER001", "ACTIVE"))
                .thenReturn(Optional.of(testTellerSession));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tellerService.startTellerSession("TELLER001", "BR001");
        });

        assertEquals("Teller already has an active session", exception.getMessage());

        verify(tellerSessionRepository).findByTellerIdAndStatus("TELLER001", "ACTIVE");
        verify(tellerSessionRepository, never()).save(any(TellerSession.class));
    }

    @Test
    void testEndTellerSession_Success() {
        // Given
        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.of(testTellerSession));
        when(tellerSessionRepository.save(any(TellerSession.class))).thenReturn(testTellerSession);

        // When
        tellerService.endTellerSession("SESS123456789ABC", "TELLER001");

        // Then
        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
        verify(tellerSessionRepository).save(any(TellerSession.class));
    }

    @Test
    void testEndTellerSession_SessionNotFound() {
        // Given
        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tellerService.endTellerSession("SESS123456789ABC", "TELLER001");
        });

        assertEquals("Teller session not found: SESS123456789ABC", exception.getMessage());

        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
        verify(tellerSessionRepository, never()).save(any(TellerSession.class));
    }

    @Test
    void testEndTellerSession_TellerIdMismatch() {
        // Given
        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.of(testTellerSession));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tellerService.endTellerSession("SESS123456789ABC", "TELLER002");
        });

        assertEquals("Teller ID mismatch for session: SESS123456789ABC", exception.getMessage());

        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
        verify(tellerSessionRepository, never()).save(any(TellerSession.class));
    }

    @Test
    void testEndTellerSession_SessionNotActive() {
        // Given
        testTellerSession.setStatus("CLOSED");
        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.of(testTellerSession));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tellerService.endTellerSession("SESS123456789ABC", "TELLER001");
        });

        assertEquals("Teller session is not active: SESS123456789ABC", exception.getMessage());

        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
        verify(tellerSessionRepository, never()).save(any(TellerSession.class));
    }

    @Test
    void testGetActiveTellerSession_Success() {
        // Given
        when(tellerSessionRepository.findByTellerIdAndStatus("TELLER001", "ACTIVE"))
                .thenReturn(Optional.of(testTellerSession));

        // When
        Optional<TellerSessionDTO> result = tellerService.getActiveTellerSession("TELLER001");

        // Then
        assertTrue(result.isPresent());
        assertEquals("TELLER001", result.get().getTellerId());
        assertEquals("BR001", result.get().getBranchCode());

        verify(tellerSessionRepository).findByTellerIdAndStatus("TELLER001", "ACTIVE");
    }

    @Test
    void testGetActiveTellerSession_NotFound() {
        // Given
        when(tellerSessionRepository.findByTellerIdAndStatus("TELLER001", "ACTIVE")).thenReturn(Optional.empty());

        // When
        Optional<TellerSessionDTO> result = tellerService.getActiveTellerSession("TELLER001");

        // Then
        assertFalse(result.isPresent());

        verify(tellerSessionRepository).findByTellerIdAndStatus("TELLER001", "ACTIVE");
    }

    @Test
    void testGetCustomerInformation_Success() {
        // Given
        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.of(testTellerSession));
        when(customerService.findCustomerById("CUST12345678")).thenReturn(Optional.of(testCustomer));
        when(tellerTransactionRepository.save(any(TellerTransaction.class))).thenReturn(new TellerTransaction());

        // When
        Customer result = tellerService.getCustomerInformation("CUST12345678", "SESS123456789ABC");

        // Then
        assertNotNull(result);
        assertEquals("CUST12345678", result.getCustomerId());
        assertEquals("홍길동", result.getName());

        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
        verify(customerService).findCustomerById("CUST12345678");
        verify(tellerTransactionRepository).save(any(TellerTransaction.class));
    }

    @Test
    void testGetCustomerInformation_CustomerNotFound() {
        // Given
        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.of(testTellerSession));
        when(customerService.findCustomerById("CUST12345678")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tellerService.getCustomerInformation("CUST12345678", "SESS123456789ABC");
        });

        assertEquals("Customer not found: CUST12345678", exception.getMessage());

        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
        verify(customerService).findCustomerById("CUST12345678");
        verify(tellerTransactionRepository, never()).save(any(TellerTransaction.class));
    }

    @Test
    void testCreateCashCardForCustomer_Success() {
        // Given
        CashCardDTO createdCard = new CashCardDTO();
        createdCard.setCardNumber("CC1234567890123456");
        createdCard.setCustomerId("CUST12345678");

        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.of(testTellerSession));
        when(cashCardService.createCashCard(testCashCardDTO)).thenReturn(createdCard);
        when(tellerTransactionRepository.save(any(TellerTransaction.class))).thenReturn(new TellerTransaction());

        // When
        CashCardDTO result = tellerService.createCashCardForCustomer(testCashCardDTO, "SESS123456789ABC");

        // Then
        assertNotNull(result);
        assertEquals("CC1234567890123456", result.getCardNumber());

        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
        verify(cashCardService).createCashCard(testCashCardDTO);
        verify(tellerTransactionRepository).save(any(TellerTransaction.class));
    }

    @Test
    void testCreateDepositAccountForCustomer_Success() {
        // Given
        DepositAccountDTO createdAccount = new DepositAccountDTO();
        createdAccount.setAccountNumber("ACC1234567890");
        createdAccount.setCustomerId("CUST12345678");

        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.of(testTellerSession));
        when(depositService.createDepositAccount(testDepositAccountDTO)).thenReturn(createdAccount);
        when(tellerTransactionRepository.save(any(TellerTransaction.class))).thenReturn(new TellerTransaction());

        // When
        DepositAccountDTO result = tellerService.createDepositAccountForCustomer(testDepositAccountDTO,
                "SESS123456789ABC");

        // Then
        assertNotNull(result);
        assertEquals("ACC1234567890", result.getAccountNumber());

        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
        verify(depositService).createDepositAccount(testDepositAccountDTO);
        verify(tellerTransactionRepository).save(any(TellerTransaction.class));
    }

    @Test
    void testProcessCashDeposit_Success() {
        // Given
        DepositTransactionDTO transactionDTO = new DepositTransactionDTO();
        transactionDTO.setTransactionId("DTXN123456789ABC");

        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.of(testTellerSession));
        when(depositService.processDeposit(eq("ACC1234567890"), any(DepositTransactionDTO.class)))
                .thenReturn(transactionDTO);
        when(tellerTransactionRepository.save(any(TellerTransaction.class))).thenReturn(new TellerTransaction());

        // When
        DepositTransactionDTO result = tellerService.processCashDeposit("ACC1234567890", new BigDecimal("100000"),
                "KRW", "SESS123456789ABC", "TELLER001");

        // Then
        assertNotNull(result);
        assertEquals("DTXN123456789ABC", result.getTransactionId());

        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
        verify(depositService).processDeposit(eq("ACC1234567890"), any(DepositTransactionDTO.class));
        verify(tellerTransactionRepository).save(any(TellerTransaction.class));
    }

    @Test
    void testProcessCashWithdrawal_Success() {
        // Given
        DepositTransactionDTO transactionDTO = new DepositTransactionDTO();
        transactionDTO.setTransactionId("DTXN123456789DEF");

        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.of(testTellerSession));
        when(depositService.processWithdrawal(eq("ACC1234567890"), any(DepositTransactionDTO.class)))
                .thenReturn(transactionDTO);
        when(tellerTransactionRepository.save(any(TellerTransaction.class))).thenReturn(new TellerTransaction());

        // When
        DepositTransactionDTO result = tellerService.processCashWithdrawal("ACC1234567890", new BigDecimal("50000"),
                "KRW", "SESS123456789ABC", "TELLER001");

        // Then
        assertNotNull(result);
        assertEquals("DTXN123456789DEF", result.getTransactionId());

        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
        verify(depositService).processWithdrawal(eq("ACC1234567890"), any(DepositTransactionDTO.class));
        verify(tellerTransactionRepository).save(any(TellerTransaction.class));
    }

    @Test
    void testProcessAccountTransfer_Success() {
        // Given
        DepositTransactionDTO transactionDTO = new DepositTransactionDTO();
        transactionDTO.setTransactionId("DTXN123456789GHI");

        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.of(testTellerSession));
        when(depositService.processTransfer(eq("ACC1234567890"), eq("ACC2345678901"), any(DepositTransactionDTO.class)))
                .thenReturn(transactionDTO);
        when(tellerTransactionRepository.save(any(TellerTransaction.class))).thenReturn(new TellerTransaction());

        // When
        DepositTransactionDTO result = tellerService.processAccountTransfer("ACC1234567890", "ACC2345678901",
                new BigDecimal("100000"), "KRW", "SESS123456789ABC", "TELLER001");

        // Then
        assertNotNull(result);
        assertEquals("DTXN123456789GHI", result.getTransactionId());

        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
        verify(depositService).processTransfer(eq("ACC1234567890"), eq("ACC2345678901"),
                any(DepositTransactionDTO.class));
        verify(tellerTransactionRepository).save(any(TellerTransaction.class));
    }

    @Test
    void testProcessCashCardTransaction_Success() {
        // Given
        CashCardTransactionDTO transactionDTO = new CashCardTransactionDTO();
        transactionDTO.setCardNumber("CC1234567890123456");
        transactionDTO.setAmount(new BigDecimal("50000"));

        CashCardTransactionDTO resultDTO = new CashCardTransactionDTO();
        resultDTO.setTransactionId("CTXN123456789ABC");

        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.of(testTellerSession));
        when(cashCardService.processTransaction(transactionDTO)).thenReturn(resultDTO);
        when(tellerTransactionRepository.save(any(TellerTransaction.class))).thenReturn(new TellerTransaction());

        // When
        CashCardTransactionDTO result = tellerService.processCashCardTransaction(transactionDTO, "SESS123456789ABC");

        // Then
        assertNotNull(result);
        assertEquals("CTXN123456789ABC", result.getTransactionId());

        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
        verify(cashCardService).processTransaction(transactionDTO);
        verify(tellerTransactionRepository).save(any(TellerTransaction.class));
    }

    @Test
    void testGetTellerTransactionHistory_Success() {
        // Given
        TellerTransaction transaction = new TellerTransaction();
        transaction.setTransactionId("TTXN123456789ABC");
        transaction.setSessionId("SESS123456789ABC");
        transaction.setTransactionType("CASH_DEPOSIT");

        List<TellerTransaction> transactions = Arrays.asList(transaction);
        when(tellerTransactionRepository.findBySessionIdOrderByTransactionDateDesc("SESS123456789ABC"))
                .thenReturn(transactions);

        // When
        List<TellerTransactionDTO> result = tellerService.getTellerTransactionHistory("SESS123456789ABC");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("TTXN123456789ABC", result.get(0).getTransactionId());

        verify(tellerTransactionRepository).findBySessionIdOrderByTransactionDateDesc("SESS123456789ABC");
    }

    @Test
    void testGetTellerSessionSummary_Success() {
        // Given
        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.of(testTellerSession));

        // When
        TellerSessionDTO result = tellerService.getTellerSessionSummary("SESS123456789ABC");

        // Then
        assertNotNull(result);
        assertEquals("SESS123456789ABC", result.getSessionId());
        assertEquals("TELLER001", result.getTellerId());
        assertEquals("BR001", result.getBranchCode());

        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
    }

    @Test
    void testGetTellerSessionSummary_SessionNotFound() {
        // Given
        when(tellerSessionRepository.findBySessionId("SESS123456789ABC")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            tellerService.getTellerSessionSummary("SESS123456789ABC");
        });

        assertEquals("Teller session not found: SESS123456789ABC", exception.getMessage());

        verify(tellerSessionRepository).findBySessionId("SESS123456789ABC");
    }
}