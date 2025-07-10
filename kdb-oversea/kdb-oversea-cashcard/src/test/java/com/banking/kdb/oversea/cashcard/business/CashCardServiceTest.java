package com.banking.kdb.oversea.cashcard.business;

import com.banking.foundation.log.FoundationLogger;
import com.banking.kdb.oversea.cashcard.model.CashCard;
import com.banking.kdb.oversea.cashcard.model.CashCardTransaction;
import com.banking.kdb.oversea.cashcard.repository.CashCardRepository;
import com.banking.kdb.oversea.cashcard.repository.CashCardTransactionRepository;
import com.banking.kdb.oversea.cashcard.transfer.CashCardDTO;
import com.banking.kdb.oversea.cashcard.transfer.CashCardTransactionDTO;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CashCardService
 */
@ExtendWith(MockitoExtension.class)
class CashCardServiceTest {

    @Mock
    private CashCardRepository cashCardRepository;

    @Mock
    private CashCardTransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private KdbConfig kdbConfig;

    @InjectMocks
    private CashCardService cashCardService;

    private CashCard testCashCard;
    private CashCardDTO testCashCardDTO;
    private Customer testCustomer;
    private CashCardTransaction testTransaction;
    private CashCardTransactionDTO testTransactionDTO;

    @BeforeEach
    void setUp() {
        // Setup test customer
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setCustomerId("CUST12345678");
        testCustomer.setName("홍길동");
        testCustomer.setEmail("hong@example.com");
        testCustomer.setStatus("ACTIVE");

        // Setup test cash card
        testCashCard = new CashCard();
        testCashCard.setId(1L);
        testCashCard.setCardNumber("CC1234567890123456");
        testCashCard.setCustomerId("CUST12345678");
        testCashCard.setCardType("DEBIT");
        testCashCard.setCardStatus("ACTIVE");
        testCashCard.setDailyLimit(new BigDecimal("1000000"));
        testCashCard.setMonthlyLimit(new BigDecimal("10000000"));
        testCashCard.setCurrency("KRW");
        testCashCard.setExpiryDate(LocalDate.now().plusYears(3));
        testCashCard.setCreatedBy("TELLER001");

        // Setup test cash card DTO
        testCashCardDTO = new CashCardDTO();
        testCashCardDTO.setCustomerId("CUST12345678");
        testCashCardDTO.setCardType("DEBIT");
        testCashCardDTO.setDailyLimit(new BigDecimal("1000000"));
        testCashCardDTO.setMonthlyLimit(new BigDecimal("10000000"));
        testCashCardDTO.setCurrency("KRW");
        testCashCardDTO.setExpiryDate(LocalDate.now().plusYears(3));
        testCashCardDTO.setCreatedBy("TELLER001");

        // Setup test transaction
        testTransaction = new CashCardTransaction();
        testTransaction.setId(1L);
        testTransaction.setTransactionId("CTXN123456789ABC");
        testTransaction.setCardNumber("CC1234567890123456");
        testTransaction.setTransactionType("PURCHASE");
        testTransaction.setAmount(new BigDecimal("50000"));
        testTransaction.setCurrency("KRW");
        testTransaction.setStatus("COMPLETED");
        testTransaction.setTransactionDate(LocalDateTime.now());

        // Setup test transaction DTO
        testTransactionDTO = new CashCardTransactionDTO();
        testTransactionDTO.setCardNumber("CC1234567890123456");
        testTransactionDTO.setTransactionType("PURCHASE");
        testTransactionDTO.setAmount(new BigDecimal("50000"));
        testTransactionDTO.setCurrency("KRW");
        testTransactionDTO.setCreatedBy("TELLER001");
    }

    @Test
    void testCreateCashCard_Success() {
        // Given
        when(customerRepository.findByCustomerId("CUST12345678")).thenReturn(Optional.of(testCustomer));
        when(cashCardRepository.save(any(CashCard.class))).thenReturn(testCashCard);

        // When
        CashCardDTO result = cashCardService.createCashCard(testCashCardDTO);

        // Then
        assertNotNull(result);
        assertEquals("CC1234567890123456", result.getCardNumber());
        assertEquals("CUST12345678", result.getCustomerId());
        assertEquals("DEBIT", result.getCardType());
        assertEquals("ACTIVE", result.getCardStatus());

        verify(customerRepository).findByCustomerId("CUST12345678");
        verify(cashCardRepository).save(any(CashCard.class));
    }

    @Test
    void testCreateCashCard_CustomerNotFound() {
        // Given
        when(customerRepository.findByCustomerId("CUST12345678")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cashCardService.createCashCard(testCashCardDTO);
        });

        assertEquals("Customer not found with ID: CUST12345678", exception.getMessage());

        verify(customerRepository).findByCustomerId("CUST12345678");
        verify(cashCardRepository, never()).save(any(CashCard.class));
    }

    @Test
    void testGetCashCard_Success() {
        // Given
        when(cashCardRepository.findByCardNumber("CC1234567890123456")).thenReturn(Optional.of(testCashCard));

        // When
        CashCardDTO result = cashCardService.getCashCard("CC1234567890123456");

        // Then
        assertNotNull(result);
        assertEquals("CC1234567890123456", result.getCardNumber());
        assertEquals("CUST12345678", result.getCustomerId());

        verify(cashCardRepository).findByCardNumber("CC1234567890123456");
    }

    @Test
    void testGetCashCard_NotFound() {
        // Given
        when(cashCardRepository.findByCardNumber("CC1234567890123456")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cashCardService.getCashCard("CC1234567890123456");
        });

        assertEquals("Cash card not found: CC1234567890123456", exception.getMessage());

        verify(cashCardRepository).findByCardNumber("CC1234567890123456");
    }

    @Test
    void testGetCashCardsByCustomer_Success() {
        // Given
        List<CashCard> cashCards = Arrays.asList(testCashCard);
        when(cashCardRepository.findByCustomerId("CUST12345678")).thenReturn(cashCards);

        // When
        List<CashCardDTO> result = cashCardService.getCashCardsByCustomer("CUST12345678");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CC1234567890123456", result.get(0).getCardNumber());

        verify(cashCardRepository).findByCustomerId("CUST12345678");
    }

    @Test
    void testUpdateCashCard_Success() {
        // Given
        CashCardDTO updateDTO = new CashCardDTO();
        updateDTO.setCardType("CREDIT");
        updateDTO.setDailyLimit(new BigDecimal("2000000"));
        updateDTO.setModifiedBy("TELLER002");

        when(cashCardRepository.findByCardNumber("CC1234567890123456")).thenReturn(Optional.of(testCashCard));
        when(cashCardRepository.save(any(CashCard.class))).thenReturn(testCashCard);

        // When
        CashCardDTO result = cashCardService.updateCashCard("CC1234567890123456", updateDTO);

        // Then
        assertNotNull(result);
        assertEquals("CC1234567890123456", result.getCardNumber());

        verify(cashCardRepository).findByCardNumber("CC1234567890123456");
        verify(cashCardRepository).save(any(CashCard.class));
    }

    @Test
    void testUpdateCashCard_NotFound() {
        // Given
        when(cashCardRepository.findByCardNumber("CC1234567890123456")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cashCardService.updateCashCard("CC1234567890123456", testCashCardDTO);
        });

        assertEquals("Cash card not found with number: CC1234567890123456", exception.getMessage());

        verify(cashCardRepository).findByCardNumber("CC1234567890123456");
        verify(cashCardRepository, never()).save(any(CashCard.class));
    }

    @Test
    void testActivateCashCard_Success() {
        // Given
        testCashCard.setCardStatus("INACTIVE");
        when(cashCardRepository.findByCardNumber("CC1234567890123456")).thenReturn(Optional.of(testCashCard));
        when(cashCardRepository.save(any(CashCard.class))).thenReturn(testCashCard);

        // When
        cashCardService.activateCashCard("CC1234567890123456");

        // Then
        verify(cashCardRepository).findByCardNumber("CC1234567890123456");
        verify(cashCardRepository).save(any(CashCard.class));
    }

    @Test
    void testDeactivateCashCard_Success() {
        // Given
        when(cashCardRepository.findByCardNumber("CC1234567890123456")).thenReturn(Optional.of(testCashCard));
        when(cashCardRepository.save(any(CashCard.class))).thenReturn(testCashCard);

        // When
        cashCardService.deactivateCashCard("CC1234567890123456");

        // Then
        verify(cashCardRepository).findByCardNumber("CC1234567890123456");
        verify(cashCardRepository).save(any(CashCard.class));
    }

    @Test
    void testProcessTransaction_Success() {
        // Given
        when(cashCardRepository.findByCardNumber("CC1234567890123456")).thenReturn(Optional.of(testCashCard));
        when(transactionRepository.save(any(CashCardTransaction.class))).thenReturn(testTransaction);

        // When
        CashCardTransactionDTO result = cashCardService.processTransaction(testTransactionDTO);

        // Then
        assertNotNull(result);
        assertEquals("CTXN123456789ABC", result.getTransactionId());
        assertEquals("CC1234567890123456", result.getCardNumber());
        assertEquals("COMPLETED", result.getStatus());

        verify(cashCardRepository).findByCardNumber("CC1234567890123456");
        verify(transactionRepository).save(any(CashCardTransaction.class));
    }

    @Test
    void testProcessTransaction_CardNotFound() {
        // Given
        when(cashCardRepository.findByCardNumber("CC1234567890123456")).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cashCardService.processTransaction(testTransactionDTO);
        });

        assertEquals("Cash card not found: CC1234567890123456", exception.getMessage());

        verify(cashCardRepository).findByCardNumber("CC1234567890123456");
        verify(transactionRepository, never()).save(any(CashCardTransaction.class));
    }

    @Test
    void testProcessTransaction_CardInactive() {
        // Given
        testCashCard.setCardStatus("INACTIVE");
        when(cashCardRepository.findByCardNumber("CC1234567890123456")).thenReturn(Optional.of(testCashCard));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cashCardService.processTransaction(testTransactionDTO);
        });

        assertEquals("Cash card is not active: CC1234567890123456", exception.getMessage());

        verify(cashCardRepository).findByCardNumber("CC1234567890123456");
        verify(transactionRepository, never()).save(any(CashCardTransaction.class));
    }

    @Test
    void testGetTransaction_Success() {
        // Given
        when(transactionRepository.findByTransactionId("CTXN123456789ABC")).thenReturn(testTransaction);

        // When
        CashCardTransactionDTO result = cashCardService.getTransaction("CTXN123456789ABC");

        // Then
        assertNotNull(result);
        assertEquals("CTXN123456789ABC", result.getTransactionId());
        assertEquals("CC1234567890123456", result.getCardNumber());

        verify(transactionRepository).findByTransactionId("CTXN123456789ABC");
    }

    @Test
    void testGetTransaction_NotFound() {
        // Given
        when(transactionRepository.findByTransactionId("CTXN123456789ABC")).thenReturn(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cashCardService.getTransaction("CTXN123456789ABC");
        });

        assertEquals("Transaction not found: CTXN123456789ABC", exception.getMessage());

        verify(transactionRepository).findByTransactionId("CTXN123456789ABC");
    }

    @Test
    void testGetTransactionHistory_Success() {
        // Given
        List<CashCardTransaction> transactions = Arrays.asList(testTransaction);
        when(transactionRepository.findByCardNumberOrderByTransactionDateDesc("CC1234567890123456"))
                .thenReturn(transactions);

        // When
        List<CashCardTransactionDTO> result = cashCardService.getTransactionHistory("CC1234567890123456");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CTXN123456789ABC", result.get(0).getTransactionId());

        verify(transactionRepository).findByCardNumberOrderByTransactionDateDesc("CC1234567890123456");
    }

    @Test
    void testGetDailyTransactionTotal_Success() {
        // Given
        when(cashCardRepository.findByCardNumber("CC1234567890123456")).thenReturn(Optional.of(testCashCard));
        when(transactionRepository.getDailyTransactionTotal("CC1234567890123456")).thenReturn(new BigDecimal("150000"));

        // When
        BigDecimal result = cashCardService.getDailyTransactionTotal("CC1234567890123456");

        // Then
        assertNotNull(result);
        assertEquals(new BigDecimal("150000"), result);

        verify(cashCardRepository).findByCardNumber("CC1234567890123456");
        verify(transactionRepository).getDailyTransactionTotal("CC1234567890123456");
    }

    @Test
    void testGetMonthlyTransactionTotal_Success() {
        // Given
        when(cashCardRepository.findByCardNumber("CC1234567890123456")).thenReturn(Optional.of(testCashCard));
        when(transactionRepository.getMonthlyTransactionTotal("CC1234567890123456"))
                .thenReturn(new BigDecimal("1500000"));

        // When
        BigDecimal result = cashCardService.getMonthlyTransactionTotal("CC1234567890123456");

        // Then
        assertNotNull(result);
        assertEquals(new BigDecimal("1500000"), result);

        verify(cashCardRepository).findByCardNumber("CC1234567890123456");
        verify(transactionRepository).getMonthlyTransactionTotal("CC1234567890123456");
    }
}