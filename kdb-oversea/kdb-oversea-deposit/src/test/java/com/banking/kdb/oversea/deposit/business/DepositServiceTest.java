package com.banking.kdb.oversea.deposit.business;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.deposit.model.DepositAccount;
import com.banking.kdb.oversea.deposit.model.DepositTransaction;
import com.banking.kdb.oversea.deposit.repository.DepositAccountRepository;
import com.banking.kdb.oversea.deposit.repository.DepositTransactionRepository;
import com.banking.kdb.oversea.deposit.transfer.DepositAccountDTO;
import com.banking.kdb.oversea.deposit.transfer.DepositTransactionDTO;
import com.banking.kdb.oversea.common.model.Customer;
import com.banking.kdb.oversea.common.repository.CustomerRepository;
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
 * Unit tests for DepositService
 */
@ExtendWith(MockitoExtension.class)
class DepositServiceTest {

    @Mock
    private DepositAccountRepository depositAccountRepository;

    @Mock
    private DepositTransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private KdbConfig kdbConfig;

    @InjectMocks
    private DepositService depositService;

    private DepositAccount testDepositAccount;
    private DepositAccountDTO testDepositAccountDTO;
    private Customer testCustomer;
    private DepositTransaction testTransaction;
    private DepositTransactionDTO testTransactionDTO;

    @BeforeEach
    void setUp() {
        // Setup test customer
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setCustomerId("CUST12345678");
        testCustomer.setName("홍길동");
        testCustomer.setEmail("hong@example.com");
        testCustomer.setStatus("ACTIVE");

        // Setup test deposit account
        testDepositAccount = new DepositAccount();
        testDepositAccount.setId(1L);
        testDepositAccount.setAccountNumber("ACC1234567890");
        testDepositAccount.setCustomerId("CUST12345678");
        testDepositAccount.setAccountType("SAVINGS");
        testDepositAccount.setBalance(new BigDecimal("1000000"));
        testDepositAccount.setCurrency("KRW");
        testDepositAccount.setInterestRate(new BigDecimal("2.5"));
        testDepositAccount.setMinimumBalance(new BigDecimal("10000"));
        testDepositAccount.setDailyLimit(new BigDecimal("1000000"));
        testDepositAccount.setMonthlyLimit(new BigDecimal("10000000"));
        testDepositAccount.setStatus("ACTIVE");
        testDepositAccount.setOpenDate(LocalDateTime.now());
        testDepositAccount.setCreatedBy("TELLER001");

        // Setup test deposit account DTO
        testDepositAccountDTO = new DepositAccountDTO();
        testDepositAccountDTO.setCustomerId("CUST12345678");
        testDepositAccountDTO.setAccountType("SAVINGS");
        testDepositAccountDTO.setInitialBalance(new BigDecimal("1000000"));
        testDepositAccountDTO.setCurrency("KRW");
        testDepositAccountDTO.setInterestRate(new BigDecimal("2.5"));
        testDepositAccountDTO.setMinimumBalance(new BigDecimal("10000"));
        testDepositAccountDTO.setDailyLimit(new BigDecimal("1000000"));
        testDepositAccountDTO.setMonthlyLimit(new BigDecimal("10000000"));
        testDepositAccountDTO.setCreatedBy("TELLER001");

        // Setup test transaction
        testTransaction = new DepositTransaction();
        testTransaction.setId(1L);
        testTransaction.setTransactionId("DTXN123456789ABC");
        testTransaction.setAccountNumber("ACC1234567890");
        testTransaction.setTransactionType("DEPOSIT");
        testTransaction.setAmount(new BigDecimal("100000"));
        testTransaction.setCurrency("KRW");
        testTransaction.setBalanceBefore(new BigDecimal("1000000"));
        testTransaction.setBalanceAfter(new BigDecimal("1100000"));
        testTransaction.setStatus("COMPLETED");
        testTransaction.setTransactionDate(LocalDateTime.now());

        // Setup test transaction DTO
        testTransactionDTO = new DepositTransactionDTO();
        testTransactionDTO.setAccountNumber("ACC1234567890");
        testTransactionDTO.setTransactionType("DEPOSIT");
        testTransactionDTO.setAmount(new BigDecimal("100000"));
        testTransactionDTO.setCurrency("KRW");
        testTransactionDTO.setCreatedBy("TELLER001");
    }

    @Test
    void testCreateDepositAccount_Success() {
        // Given
        when(customerRepository.findByCustomerId("CUST12345678")).thenReturn(Optional.of(testCustomer));
        when(depositAccountRepository.save(any(DepositAccount.class))).thenReturn(testDepositAccount);

        // When
        DepositAccountDTO result = depositService.createDepositAccount(testDepositAccountDTO);

        // Then
        assertNotNull(result);
        assertEquals("ACC1234567890", result.getAccountNumber());
        assertEquals("CUST12345678", result.getCustomerId());
        assertEquals("SAVINGS", result.getAccountType());
        assertEquals("ACTIVE", result.getStatus());

        verify(customerRepository).findByCustomerId("CUST12345678");
        verify(depositAccountRepository).save(any(DepositAccount.class));
    }

    @Test
    void testCreateDepositAccount_CustomerNotFound() {
        // Given
        when(customerRepository.findByCustomerId("CUST12345678")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            depositService.createDepositAccount(testDepositAccountDTO);
        });

        assertEquals("Customer not found with ID: CUST12345678", exception.getMessage());

        verify(customerRepository).findByCustomerId("CUST12345678");
        verify(depositAccountRepository, never()).save(any(DepositAccount.class));
    }

    @Test
    void testGetDepositAccount_Success() {
        // Given
        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.of(testDepositAccount));

        // When
        DepositAccountDTO result = depositService.getDepositAccount("ACC1234567890");

        // Then
        assertNotNull(result);
        assertEquals("ACC1234567890", result.getAccountNumber());
        assertEquals("CUST12345678", result.getCustomerId());

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
    }

    @Test
    void testGetDepositAccount_NotFound() {
        // Given
        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            depositService.getDepositAccount("ACC1234567890");
        });

        assertEquals("Deposit account not found: ACC1234567890", exception.getMessage());

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
    }

    @Test
    void testGetDepositAccountsByCustomer_Success() {
        // Given
        List<DepositAccount> accounts = Arrays.asList(testDepositAccount);
        when(depositAccountRepository.findByCustomerId("CUST12345678")).thenReturn(accounts);

        // When
        List<DepositAccountDTO> result = depositService.getDepositAccountsByCustomer("CUST12345678");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ACC1234567890", result.get(0).getAccountNumber());

        verify(depositAccountRepository).findByCustomerId("CUST12345678");
    }

    @Test
    void testUpdateDepositAccount_Success() {
        // Given
        DepositAccountDTO updateDTO = new DepositAccountDTO();
        updateDTO.setAccountType("CHECKING");
        updateDTO.setInterestRate(new BigDecimal("3.0"));
        updateDTO.setModifiedBy("TELLER002");

        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.of(testDepositAccount));
        when(depositAccountRepository.save(any(DepositAccount.class))).thenReturn(testDepositAccount);

        // When
        DepositAccountDTO result = depositService.updateDepositAccount("ACC1234567890", updateDTO);

        // Then
        assertNotNull(result);
        assertEquals("ACC1234567890", result.getAccountNumber());

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
        verify(depositAccountRepository).save(any(DepositAccount.class));
    }

    @Test
    void testUpdateDepositAccount_NotFound() {
        // Given
        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            depositService.updateDepositAccount("ACC1234567890", testDepositAccountDTO);
        });

        assertEquals("Deposit account not found with number: ACC1234567890", exception.getMessage());

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
        verify(depositAccountRepository, never()).save(any(DepositAccount.class));
    }

    @Test
    void testCloseDepositAccount_Success() {
        // Given
        testDepositAccount.setBalance(BigDecimal.ZERO);
        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.of(testDepositAccount));
        when(depositAccountRepository.save(any(DepositAccount.class))).thenReturn(testDepositAccount);

        // When
        depositService.closeDepositAccount("ACC1234567890");

        // Then
        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
        verify(depositAccountRepository).save(any(DepositAccount.class));
    }

    @Test
    void testCloseDepositAccount_WithBalance() {
        // Given
        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.of(testDepositAccount));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            depositService.closeDepositAccount("ACC1234567890");
        });

        assertEquals("Cannot close account with positive balance: ACC1234567890", exception.getMessage());

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
        verify(depositAccountRepository, never()).save(any(DepositAccount.class));
    }

    @Test
    void testProcessDeposit_Success() {
        // Given
        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.of(testDepositAccount));
        when(transactionRepository.save(any(DepositTransaction.class))).thenReturn(testTransaction);
        when(depositAccountRepository.save(any(DepositAccount.class))).thenReturn(testDepositAccount);

        // When
        DepositTransactionDTO result = depositService.processDeposit("ACC1234567890", testTransactionDTO);

        // Then
        assertNotNull(result);
        assertEquals("DTXN123456789ABC", result.getTransactionId());
        assertEquals("ACC1234567890", result.getAccountNumber());
        assertEquals("COMPLETED", result.getStatus());

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
        verify(transactionRepository).save(any(DepositTransaction.class));
        verify(depositAccountRepository).save(any(DepositAccount.class));
    }

    @Test
    void testProcessDeposit_AccountNotFound() {
        // Given
        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            depositService.processDeposit("ACC1234567890", testTransactionDTO);
        });

        assertEquals("Deposit account not found: ACC1234567890", exception.getMessage());

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
        verify(transactionRepository, never()).save(any(DepositTransaction.class));
    }

    @Test
    void testProcessDeposit_AccountInactive() {
        // Given
        testDepositAccount.setStatus("CLOSED");
        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.of(testDepositAccount));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            depositService.processDeposit("ACC1234567890", testTransactionDTO);
        });

        assertEquals("Deposit account is not active: ACC1234567890", exception.getMessage());

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
        verify(transactionRepository, never()).save(any(DepositTransaction.class));
    }

    @Test
    void testProcessWithdrawal_Success() {
        // Given
        testTransactionDTO.setTransactionType("WITHDRAWAL");
        testTransactionDTO.setAmount(new BigDecimal("50000"));

        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.of(testDepositAccount));
        when(transactionRepository.save(any(DepositTransaction.class))).thenReturn(testTransaction);
        when(depositAccountRepository.save(any(DepositAccount.class))).thenReturn(testDepositAccount);

        // When
        DepositTransactionDTO result = depositService.processWithdrawal("ACC1234567890", testTransactionDTO);

        // Then
        assertNotNull(result);
        assertEquals("DTXN123456789ABC", result.getTransactionId());
        assertEquals("ACC1234567890", result.getAccountNumber());

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
        verify(transactionRepository).save(any(DepositTransaction.class));
        verify(depositAccountRepository).save(any(DepositAccount.class));
    }

    @Test
    void testProcessWithdrawal_InsufficientBalance() {
        // Given
        testTransactionDTO.setTransactionType("WITHDRAWAL");
        testTransactionDTO.setAmount(new BigDecimal("2000000")); // More than balance

        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.of(testDepositAccount));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            depositService.processWithdrawal("ACC1234567890", testTransactionDTO);
        });

        assertEquals("Insufficient balance for withdrawal", exception.getMessage());

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
        verify(transactionRepository, never()).save(any(DepositTransaction.class));
    }

    @Test
    void testProcessTransfer_Success() {
        // Given
        DepositAccount toAccount = new DepositAccount();
        toAccount.setAccountNumber("ACC2345678901");
        toAccount.setBalance(new BigDecimal("500000"));
        toAccount.setCurrency("KRW");
        toAccount.setStatus("ACTIVE");

        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.of(testDepositAccount));
        when(depositAccountRepository.findByAccountNumber("ACC2345678901")).thenReturn(Optional.of(toAccount));
        when(transactionRepository.save(any(DepositTransaction.class))).thenReturn(testTransaction);
        when(depositAccountRepository.save(any(DepositAccount.class))).thenReturn(testDepositAccount);

        // When
        DepositTransactionDTO result = depositService.processTransfer("ACC1234567890", "ACC2345678901",
                testTransactionDTO);

        // Then
        assertNotNull(result);
        assertEquals("DTXN123456789ABC", result.getTransactionId());

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
        verify(depositAccountRepository).findByAccountNumber("ACC2345678901");
        verify(transactionRepository).save(any(DepositTransaction.class));
        verify(depositAccountRepository, times(2)).save(any(DepositAccount.class));
    }

    @Test
    void testGetTransaction_Success() {
        // Given
        when(transactionRepository.findByTransactionId("DTXN123456789ABC")).thenReturn(testTransaction);

        // When
        DepositTransactionDTO result = depositService.getTransaction("DTXN123456789ABC");

        // Then
        assertNotNull(result);
        assertEquals("DTXN123456789ABC", result.getTransactionId());
        assertEquals("ACC1234567890", result.getAccountNumber());

        verify(transactionRepository).findByTransactionId("DTXN123456789ABC");
    }

    @Test
    void testGetTransaction_NotFound() {
        // Given
        when(transactionRepository.findByTransactionId("DTXN123456789ABC")).thenReturn(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            depositService.getTransaction("DTXN123456789ABC");
        });

        assertEquals("Transaction not found: DTXN123456789ABC", exception.getMessage());

        verify(transactionRepository).findByTransactionId("DTXN123456789ABC");
    }

    @Test
    void testGetTransactionHistory_Success() {
        // Given
        List<DepositTransaction> transactions = Arrays.asList(testTransaction);
        when(transactionRepository.findByAccountNumberOrderByTransactionDateDesc("ACC1234567890"))
                .thenReturn(transactions);

        // When
        List<DepositTransactionDTO> result = depositService.getTransactionHistory("ACC1234567890");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("DTXN123456789ABC", result.get(0).getTransactionId());

        verify(transactionRepository).findByAccountNumberOrderByTransactionDateDesc("ACC1234567890");
    }

    @Test
    void testGetAccountBalance_Success() {
        // Given
        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.of(testDepositAccount));

        // When
        BigDecimal result = depositService.getAccountBalance("ACC1234567890");

        // Then
        assertNotNull(result);
        assertEquals(new BigDecimal("1000000"), result);

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
    }

    @Test
    void testGetDailyTransactionTotal_Success() {
        // Given
        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.of(testDepositAccount));
        when(transactionRepository.getDailyTransactionTotal("ACC1234567890")).thenReturn(new BigDecimal("150000"));

        // When
        BigDecimal result = depositService.getDailyTransactionTotal("ACC1234567890");

        // Then
        assertNotNull(result);
        assertEquals(new BigDecimal("150000"), result);

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
        verify(transactionRepository).getDailyTransactionTotal("ACC1234567890");
    }

    @Test
    void testGetMonthlyTransactionTotal_Success() {
        // Given
        when(depositAccountRepository.findByAccountNumber("ACC1234567890")).thenReturn(Optional.of(testDepositAccount));
        when(transactionRepository.getMonthlyTransactionTotal("ACC1234567890")).thenReturn(new BigDecimal("1500000"));

        // When
        BigDecimal result = depositService.getMonthlyTransactionTotal("ACC1234567890");

        // Then
        assertNotNull(result);
        assertEquals(new BigDecimal("1500000"), result);

        verify(depositAccountRepository).findByAccountNumber("ACC1234567890");
        verify(transactionRepository).getMonthlyTransactionTotal("ACC1234567890");
    }
}