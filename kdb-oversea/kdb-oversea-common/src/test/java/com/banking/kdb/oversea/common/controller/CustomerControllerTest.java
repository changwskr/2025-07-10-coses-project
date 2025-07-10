package com.banking.kdb.oversea.common.controller;

import com.banking.kdb.oversea.common.business.CustomerService;
import com.banking.kdb.oversea.common.transfer.CustomerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for CustomerController
 */
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private CustomerDTO testCustomerDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        objectMapper = new ObjectMapper();

        testCustomerDTO = new CustomerDTO();
        testCustomerDTO.setId(1L);
        testCustomerDTO.setCustomerId("CUST123456");
        testCustomerDTO.setName("John Doe");
        testCustomerDTO.setEmail("john.doe@example.com");
        testCustomerDTO.setPhoneNumber("+1234567890");
        testCustomerDTO.setAddress("123 Main St");
        testCustomerDTO.setCity("New York");
        testCustomerDTO.setCountry("USA");
        testCustomerDTO.setNationality("American");
        testCustomerDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        testCustomerDTO.setStatus("ACTIVE");
        testCustomerDTO.setCustomerType("INDIVIDUAL");
        testCustomerDTO.setRiskLevel("LOW");
        testCustomerDTO.setKycStatus("PENDING");
        testCustomerDTO.setCreatedBy("system");
    }

    @Test
    void testCreateCustomer_Success() throws Exception {
        // Given
        CustomerDTO createDTO = new CustomerDTO();
        createDTO.setName("Jane Doe");
        createDTO.setEmail("jane.doe@example.com");
        createDTO.setCreatedBy("teller");

        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(testCustomerDTO);

        // When & Then
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value("CUST123456"))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(customerService).createCustomer(any(CustomerDTO.class));
    }

    @Test
    void testCreateCustomer_ValidationError() throws Exception {
        // Given
        CustomerDTO invalidDTO = new CustomerDTO();
        // Missing required fields

        // When & Then
        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());

        verify(customerService, never()).createCustomer(any(CustomerDTO.class));
    }

    @Test
    void testGetCustomer_Success() throws Exception {
        // Given
        when(customerService.findCustomerById("CUST123456")).thenReturn(Optional.of(testCustomerDTO));

        // When & Then
        mockMvc.perform(get("/api/v1/customers/CUST123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("CUST123456"))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(customerService).findCustomerById("CUST123456");
    }

    @Test
    void testGetCustomer_NotFound() throws Exception {
        // Given
        when(customerService.findCustomerById("NONEXISTENT")).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/v1/customers/NONEXISTENT"))
                .andExpect(status().isNotFound());

        verify(customerService).findCustomerById("NONEXISTENT");
    }

    @Test
    void testGetCustomerByEmail_Success() throws Exception {
        // Given
        when(customerService.findCustomerByEmail("john.doe@example.com")).thenReturn(Optional.of(testCustomerDTO));

        // When & Then
        mockMvc.perform(get("/api/v1/customers/email/john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(customerService).findCustomerByEmail("john.doe@example.com");
    }

    @Test
    void testGetAllCustomers_Success() throws Exception {
        // Given
        List<CustomerDTO> customers = Arrays.asList(testCustomerDTO);
        when(customerService.findAllCustomers(anyInt(), anyInt())).thenReturn(customers);

        // When & Then
        mockMvc.perform(get("/api/v1/customers")
                .param("page", "0")
                .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value("CUST123456"));

        verify(customerService).findAllCustomers(0, 20);
    }

    @Test
    void testSearchCustomers_Success() throws Exception {
        // Given
        List<CustomerDTO> customers = Arrays.asList(testCustomerDTO);
        when(customerService.searchCustomers(anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(customers);

        // When & Then
        mockMvc.perform(get("/api/v1/customers/search")
                .param("searchTerm", "John")
                .param("customerType", "INDIVIDUAL")
                .param("status", "ACTIVE")
                .param("page", "0")
                .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));

        verify(customerService).searchCustomers("John", "INDIVIDUAL", "ACTIVE", null, 0, 20);
    }

    @Test
    void testUpdateCustomer_Success() throws Exception {
        // Given
        CustomerDTO updateDTO = new CustomerDTO();
        updateDTO.setName("Jane Doe");
        updateDTO.setEmail("jane.doe@example.com");

        when(customerService.updateCustomer(eq("CUST123456"), any(CustomerDTO.class))).thenReturn(testCustomerDTO);

        // When & Then
        mockMvc.perform(put("/api/v1/customers/CUST123456")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("CUST123456"));

        verify(customerService).updateCustomer(eq("CUST123456"), any(CustomerDTO.class));
    }

    @Test
    void testDeactivateCustomer_Success() throws Exception {
        // Given
        doNothing().when(customerService).deactivateCustomer("CUST123456");

        // When & Then
        mockMvc.perform(put("/api/v1/customers/CUST123456/deactivate"))
                .andExpect(status().isOk());

        verify(customerService).deactivateCustomer("CUST123456");
    }

    @Test
    void testActivateCustomer_Success() throws Exception {
        // Given
        doNothing().when(customerService).activateCustomer("CUST123456");

        // When & Then
        mockMvc.perform(put("/api/v1/customers/CUST123456/activate"))
                .andExpect(status().isOk());

        verify(customerService).activateCustomer("CUST123456");
    }

    @Test
    void testUpdateKycStatus_Success() throws Exception {
        // Given
        doNothing().when(customerService).updateKycStatus("CUST123456", "COMPLETED");

        // When & Then
        mockMvc.perform(put("/api/v1/customers/CUST123456/kyc")
                .param("kycStatus", "COMPLETED"))
                .andExpect(status().isOk());

        verify(customerService).updateKycStatus("CUST123456", "COMPLETED");
    }

    @Test
    void testUpdateRiskLevel_Success() throws Exception {
        // Given
        doNothing().when(customerService).updateRiskLevel("CUST123456", "MEDIUM");

        // When & Then
        mockMvc.perform(put("/api/v1/customers/CUST123456/risk-level")
                .param("riskLevel", "MEDIUM"))
                .andExpect(status().isOk());

        verify(customerService).updateRiskLevel("CUST123456", "MEDIUM");
    }

    @Test
    void testGetCustomerStatistics_Success() throws Exception {
        // Given
        Object statistics = new Object() {
            public final long totalCustomers = 100L;
            public final long activeCustomers = 80L;
        };
        when(customerService.getCustomerStatistics()).thenReturn(statistics);

        // When & Then
        mockMvc.perform(get("/api/v1/customers/statistics"))
                .andExpect(status().isOk());

        verify(customerService).getCustomerStatistics();
    }

    @Test
    void testHealthCheck_Success() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/customers/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer service is running"));
    }
}