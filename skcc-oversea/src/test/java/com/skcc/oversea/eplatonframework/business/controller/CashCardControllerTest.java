package com.skcc.oversea.eplatonframework.business.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcc.oversea.eplatonframework.business.entity.CashCard;
import com.skcc.oversea.eplatonframework.business.service.CashCardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * CashCardController Integration Tests
 */
@WebMvcTest(CashCardController.class)
class CashCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CashCardService cashCardService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testGetAllCashCards() throws Exception {
        // Given
        List<CashCard> cashCards = Arrays.asList(testCashCard);
        when(cashCardService.getAllCashCards()).thenReturn(cashCards);

        // When & Then
        mockMvc.perform(get("/api/cashcard"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].cardNo").value("1234567890123456"))
                .andExpect(jsonPath("$.data[0].customerId").value("CUST001"));

        verify(cashCardService, times(1)).getAllCashCards();
    }

    @Test
    void testGetCashCardById() throws Exception {
        // Given
        when(cashCardService.getCashCardById(1L)).thenReturn(testCashCard);

        // When & Then
        mockMvc.perform(get("/api/cashcard/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.cardNo").value("1234567890123456"))
                .andExpect(jsonPath("$.data.customerId").value("CUST001"));

        verify(cashCardService, times(1)).getCashCardById(1L);
    }

    @Test
    void testGetCashCardById_NotFound() throws Exception {
        // Given
        when(cashCardService.getCashCardById(999L)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/cashcard/999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Cash card not found"));

        verify(cashCardService, times(1)).getCashCardById(999L);
    }

    @Test
    void testGetCashCardByCardNo() throws Exception {
        // Given
        when(cashCardService.getCashCardByCardNo("1234567890123456")).thenReturn(testCashCard);

        // When & Then
        mockMvc.perform(get("/api/cashcard/card/1234567890123456"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.cardNo").value("1234567890123456"));

        verify(cashCardService, times(1)).getCashCardByCardNo("1234567890123456");
    }

    @Test
    void testGetCashCardsByCustomerId() throws Exception {
        // Given
        List<CashCard> cashCards = Arrays.asList(testCashCard);
        when(cashCardService.getCashCardsByCustomerId("CUST001")).thenReturn(cashCards);

        // When & Then
        mockMvc.perform(get("/api/cashcard/customer/CUST001"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].customerId").value("CUST001"));

        verify(cashCardService, times(1)).getCashCardsByCustomerId("CUST001");
    }

    @Test
    void testCreateCashCard() throws Exception {
        // Given
        CashCard newCard = new CashCard();
        newCard.setCardNo("9876543210987654");
        newCard.setCustomerId("CUST002");

        when(cashCardService.createCashCard(any(CashCard.class))).thenReturn(newCard);

        // When & Then
        mockMvc.perform(post("/api/cashcard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCard)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cash card created successfully"))
                .andExpect(jsonPath("$.data.cardNo").value("9876543210987654"));

        verify(cashCardService, times(1)).createCashCard(any(CashCard.class));
    }

    @Test
    void testUpdateCashCard() throws Exception {
        // Given
        CashCard updatedCard = new CashCard();
        updatedCard.setId(1L);
        updatedCard.setCardNo("1234567890123456");
        updatedCard.setCurrentBalance(new BigDecimal("750000"));

        when(cashCardService.updateCashCard(any(CashCard.class))).thenReturn(updatedCard);

        // When & Then
        mockMvc.perform(put("/api/cashcard/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCard)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cash card updated successfully"))
                .andExpect(jsonPath("$.data.currentBalance").value(750000));

        verify(cashCardService, times(1)).updateCashCard(any(CashCard.class));
    }

    @Test
    void testUpdateCashCard_NotFound() throws Exception {
        // Given
        CashCard updatedCard = new CashCard();
        updatedCard.setId(999L);

        when(cashCardService.updateCashCard(any(CashCard.class))).thenReturn(null);

        // When & Then
        mockMvc.perform(put("/api/cashcard/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCard)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Cash card not found"));

        verify(cashCardService, times(1)).updateCashCard(any(CashCard.class));
    }

    @Test
    void testDeleteCashCard() throws Exception {
        // Given
        when(cashCardService.deleteCashCard(1L)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/api/cashcard/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Cash card deleted successfully"));

        verify(cashCardService, times(1)).deleteCashCard(1L);
    }

    @Test
    void testDeleteCashCard_NotFound() throws Exception {
        // Given
        when(cashCardService.deleteCashCard(999L)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/api/cashcard/999"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Cash card not found"));

        verify(cashCardService, times(1)).deleteCashCard(999L);
    }

    @Test
    void testGetCashCardsByStatus() throws Exception {
        // Given
        List<CashCard> cashCards = Arrays.asList(testCashCard);
        when(cashCardService.getCashCardsByStatus("AC")).thenReturn(cashCards);

        // When & Then
        mockMvc.perform(get("/api/cashcard/status/AC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].cardStatus").value("AC"));

        verify(cashCardService, times(1)).getCashCardsByStatus("AC");
    }

    @Test
    void testGetExpiredCashCards() throws Exception {
        // Given
        List<CashCard> cashCards = Arrays.asList(testCashCard);
        when(cashCardService.getExpiredCashCards()).thenReturn(cashCards);

        // When & Then
        mockMvc.perform(get("/api/cashcard/expired"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());

        verify(cashCardService, times(1)).getExpiredCashCards();
    }

    @Test
    void testUpdateCardBalance() throws Exception {
        // Given
        CashCard updatedCard = new CashCard();
        updatedCard.setId(1L);
        updatedCard.setCurrentBalance(new BigDecimal("1000000"));

        when(cashCardService.updateCardBalance(1L, new BigDecimal("1000000"))).thenReturn(updatedCard);

        // When & Then
        mockMvc.perform(put("/api/cashcard/1/balance")
                .param("newBalance", "1000000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Card balance updated successfully"))
                .andExpect(jsonPath("$.data.currentBalance").value(1000000));

        verify(cashCardService, times(1)).updateCardBalance(1L, new BigDecimal("1000000"));
    }

    @Test
    void testUpdateCardStatus() throws Exception {
        // Given
        CashCard updatedCard = new CashCard();
        updatedCard.setId(1L);
        updatedCard.setCardStatus("BL");

        when(cashCardService.updateCardStatus(1L, "BL")).thenReturn(updatedCard);

        // When & Then
        mockMvc.perform(put("/api/cashcard/1/status")
                .param("status", "BL"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Card status updated successfully"))
                .andExpect(jsonPath("$.data.cardStatus").value("BL"));

        verify(cashCardService, times(1)).updateCardStatus(1L, "BL");
    }
}