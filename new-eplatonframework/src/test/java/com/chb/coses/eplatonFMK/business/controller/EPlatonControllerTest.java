package com.chb.coses.eplatonFMK.business.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.chb.coses.eplatonFMK.business.EPlatonBizDelegateSBBean;
import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * EPlaton 컨트롤러 테스트 클래스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@SpringBootTest
class EPlatonControllerTest {

    @InjectMocks
    private EPlatonController ePlatonController;

    @Mock
    private EPlatonBizDelegateSBBean eplatonBizDelegate;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ePlatonController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testExecuteSuccess() throws Exception {
        // Given
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("serviceName", "CashCard");
        requestBody.put("methodName", "issue");
        requestBody.put("data", "test data");

        when(eplatonBizDelegate.execute(any(EPlatonEvent.class)))
                .thenReturn("Cash card issued successfully");

        // When & Then
        mockMvc.perform(post("/api/eplaton/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result").value("Cash card issued successfully"))
                .andExpect(jsonPath("$.transactionId").exists());
    }

    @Test
    void testExecuteWithDepositService() throws Exception {
        // Given
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("serviceName", "Deposit");
        requestBody.put("methodName", "create");
        requestBody.put("accountData", "account info");

        when(eplatonBizDelegate.execute(any(EPlatonEvent.class)))
                .thenReturn("Deposit account created successfully");

        // When & Then
        mockMvc.perform(post("/api/eplaton/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result").value("Deposit account created successfully"));
    }

    @Test
    void testExecuteWithCommonService() throws Exception {
        // Given
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("serviceName", "Common");
        requestBody.put("methodName", "execute");
        requestBody.put("commonData", "common info");

        when(eplatonBizDelegate.execute(any(EPlatonEvent.class)))
                .thenReturn("Common service executed successfully");

        // When & Then
        mockMvc.perform(post("/api/eplaton/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result").value("Common service executed successfully"));
    }

    @Test
    void testExecuteWithException() throws Exception {
        // Given
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("serviceName", "InvalidService");
        requestBody.put("methodName", "execute");

        when(eplatonBizDelegate.execute(any(EPlatonEvent.class)))
                .thenThrow(new RuntimeException("Service not found"));

        // When & Then
        mockMvc.perform(post("/api/eplaton/execute")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void testExecuteGet() throws Exception {
        // Given
        when(eplatonBizDelegate.execute(any(EPlatonEvent.class)))
                .thenReturn("Get request processed successfully");

        // When & Then
        mockMvc.perform(get("/api/eplaton/execute")
                .param("serviceName", "CashCard")
                .param("methodName", "execute"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result").value("Get request processed successfully"));
    }

    @Test
    void testHealth() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/eplaton/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("EPlaton Framework"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}