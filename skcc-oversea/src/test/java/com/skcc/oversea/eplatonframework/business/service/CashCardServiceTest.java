package com.skcc.oversea.eplatonframework.business.service;

import com.skcc.oversea.eplatonframework.business.entity.CashCard;
import com.skcc.oversea.eplatonframework.business.repository.CashCardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * CashCardService Unit Tests
 */
@ExtendWith(MockitoExtension.class)
class CashCardServiceTest {

    @Mock
    private CashCardRepository cashCardRepository;

    @InjectMocks
    private CashCardServiceImpl cashCardService;

    private CashCard testCashCard;

    @BeforeEach
    void setUp() {
        testCashCard = new CashCard();
        testCashCard.setId(1L);
        testCashCard.setCardNo("1234567890123456");
        testCashCard.setAccountNo("1234567890");
        testCashCard.setCustomerId("CUST001");
        testCashCard.setCardType("DEBIT");
        testCashCard.setCardStatus("AC");
        testCashCard.setIssueDate(LocalDate.now());
        testCashCard.setExpiryDate(LocalDate.now().plusYears(3));
        testCashCard.setDailyLimit(new BigDecimal("1000000"));
        testCashCard.setMonthlyLimit(new BigDecimal("50000000"));
        testCashCard.setCurrentBalance(new BigDecimal("500000"));
        testCashCard.setCurrencyCode("KRW");
        testCashCard.setPinNumber("1234");
        testCashCard.setCardHolderName("John Doe");
    }

    @Test
    void testGetAllCashCards() {
        // Given
        List<CashCard> expectedCards = Arrays.asList(testCashCard);
        when(cashCardRepository.findAll()).thenReturn(expectedCards);

        // When
        List<CashCard> result = cashCardService.getAllCashCards();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCashCard.getCardNo(), result.get(0).getCardNo());
        verify(cashCardRepository, times(1)).findAll();
    }

    @Test
    void testGetCashCardById() {
        // Given
        when(cashCardRepository.findById(1L)).thenReturn(Optional.of(testCashCard));

        // When
        CashCard result = cashCardService.getCashCardById(1L);

        // Then
        assertNotNull(result);
        assertEquals(testCashCard.getCardNo(), result.getCardNo());
        verify(cashCardRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCashCardById_NotFound() {
        // Given
        when(cashCardRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        CashCard result = cashCardService.getCashCardById(999L);

        // Then
        assertNull(result);
        verify(cashCardRepository, times(1)).findById(999L);
    }

    @Test
    void testGetCashCardByCardNo() {
        // Given
        when(cashCardRepository.findByCardNo("1234567890123456")).thenReturn(Optional.of(testCashCard));

        // When
        CashCard result = cashCardService.getCashCardByCardNo("1234567890123456");

        // Then
        assertNotNull(result);
        assertEquals(testCashCard.getCardNo(), result.getCardNo());
        verify(cashCardRepository, times(1)).findByCardNo("1234567890123456");
    }

    @Test
    void testGetCashCardsByCustomerId() {
        // Given
        List<CashCard> expectedCards = Arrays.asList(testCashCard);
        when(cashCardRepository.findByCustomerId("CUST001")).thenReturn(expectedCards);

        // When
        List<CashCard> result = cashCardService.getCashCardsByCustomerId("CUST001");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCashCard.getCustomerId(), result.get(0).getCustomerId());
        verify(cashCardRepository, times(1)).findByCustomerId("CUST001");
    }

    @Test
    void testCreateCashCard() {
        // Given
        CashCard newCard = new CashCard();
        newCard.setCardNo("9876543210987654");
        newCard.setCustomerId("CUST002");

        when(cashCardRepository.save(any(CashCard.class))).thenReturn(newCard);

        // When
        CashCard result = cashCardService.createCashCard(newCard);

        // Then
        assertNotNull(result);
        assertEquals(newCard.getCardNo(), result.getCardNo());
        verify(cashCardRepository, times(1)).save(newCard);
    }

    @Test
    void testUpdateCashCard() {
        // Given
        CashCard updatedCard = new CashCard();
        updatedCard.setId(1L);
        updatedCard.setCardNo("1234567890123456");
        updatedCard.setCurrentBalance(new BigDecimal("750000"));

        when(cashCardRepository.findById(1L)).thenReturn(Optional.of(testCashCard));
        when(cashCardRepository.save(any(CashCard.class))).thenReturn(updatedCard);

        // When
        CashCard result = cashCardService.updateCashCard(updatedCard);

        // Then
        assertNotNull(result);
        assertEquals(updatedCard.getCurrentBalance(), result.getCurrentBalance());
        verify(cashCardRepository, times(1)).findById(1L);
        verify(cashCardRepository, times(1)).save(updatedCard);
    }

    @Test
    void testUpdateCashCard_NotFound() {
        // Given
        CashCard updatedCard = new CashCard();
        updatedCard.setId(999L);

        when(cashCardRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        CashCard result = cashCardService.updateCashCard(updatedCard);

        // Then
        assertNull(result);
        verify(cashCardRepository, times(1)).findById(999L);
        verify(cashCardRepository, never()).save(any(CashCard.class));
    }

    @Test
    void testDeleteCashCard() {
        // Given
        when(cashCardRepository.findById(1L)).thenReturn(Optional.of(testCashCard));
        doNothing().when(cashCardRepository).delete(testCashCard);

        // When
        boolean result = cashCardService.deleteCashCard(1L);

        // Then
        assertTrue(result);
        verify(cashCardRepository, times(1)).findById(1L);
        verify(cashCardRepository, times(1)).delete(testCashCard);
    }

    @Test
    void testDeleteCashCard_NotFound() {
        // Given
        when(cashCardRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        boolean result = cashCardService.deleteCashCard(999L);

        // Then
        assertFalse(result);
        verify(cashCardRepository, times(1)).findById(999L);
        verify(cashCardRepository, never()).delete(any(CashCard.class));
    }

    @Test
    void testGetCashCardsByStatus() {
        // Given
        List<CashCard> expectedCards = Arrays.asList(testCashCard);
        when(cashCardRepository.findByCardStatus("AC")).thenReturn(expectedCards);

        // When
        List<CashCard> result = cashCardService.getCashCardsByStatus("AC");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("AC", result.get(0).getCardStatus());
        verify(cashCardRepository, times(1)).findByCardStatus("AC");
    }

    @Test
    void testGetExpiredCashCards() {
        // Given
        List<CashCard> expectedCards = Arrays.asList(testCashCard);
        when(cashCardRepository.findExpiredCards(any(LocalDate.class))).thenReturn(expectedCards);

        // When
        List<CashCard> result = cashCardService.getExpiredCashCards();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(cashCardRepository, times(1)).findExpiredCards(any(LocalDate.class));
    }

    @Test
    void testUpdateCardBalance() {
        // Given
        BigDecimal newBalance = new BigDecimal("1000000");
        CashCard updatedCard = new CashCard();
        updatedCard.setId(1L);
        updatedCard.setCurrentBalance(newBalance);

        when(cashCardRepository.findById(1L)).thenReturn(Optional.of(testCashCard));
        when(cashCardRepository.save(any(CashCard.class))).thenReturn(updatedCard);

        // When
        CashCard result = cashCardService.updateCardBalance(1L, newBalance);

        // Then
        assertNotNull(result);
        assertEquals(newBalance, result.getCurrentBalance());
        verify(cashCardRepository, times(1)).findById(1L);
        verify(cashCardRepository, times(1)).save(any(CashCard.class));
    }

    @Test
    void testUpdateCardStatus() {
        // Given
        String newStatus = "BL";
        CashCard updatedCard = new CashCard();
        updatedCard.setId(1L);
        updatedCard.setCardStatus(newStatus);

        when(cashCardRepository.findById(1L)).thenReturn(Optional.of(testCashCard));
        when(cashCardRepository.save(any(CashCard.class))).thenReturn(updatedCard);

        // When
        CashCard result = cashCardService.updateCardStatus(1L, newStatus);

        // Then
        assertNotNull(result);
        assertEquals(newStatus, result.getCardStatus());
        verify(cashCardRepository, times(1)).findById(1L);
        verify(cashCardRepository, times(1)).save(any(CashCard.class));
    }
}