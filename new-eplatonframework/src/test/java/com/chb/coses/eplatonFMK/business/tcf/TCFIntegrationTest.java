package com.chb.coses.eplatonFMK.business.tcf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.business.facade.cashCard.CashCardManagementSB;
import com.chb.coses.eplatonFMK.business.facade.deposit.DepositManagementSB;
import com.chb.coses.eplatonFMK.business.facade.ecommon.ECommonManagementSB;
import com.chb.coses.eplatonFMK.business.delegate.action.CashCardBizAction;
import com.chb.coses.eplatonFMK.business.delegate.action.DepositBizAction;
import com.chb.coses.eplatonFMK.business.delegate.action.CommonBizAction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TCF 통합 테스트 클래스
 * 모든 Facade가 TCF를 통해 트랜잭션이 제어되는지 확인
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@SpringBootTest
class TCFIntegrationTest {

    @InjectMocks
    private TCF tcf;

    @Mock
    private CashCardManagementSB cashCardManagement;

    @Mock
    private DepositManagementSB depositManagement;

    @Mock
    private ECommonManagementSB commonManagement;

    @Mock
    private CashCardBizAction cashCardBizAction;

    @Mock
    private DepositBizAction depositBizAction;

    @Mock
    private CommonBizAction commonBizAction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCashCardFacadeThroughTCF() throws Exception {
        // Given
        EPlatonEvent event = new EPlatonEvent();
        event.setTransactionId("TEST-CASHCARD-001");
        event.setServiceName("CashCard");
        event.setMethodName("issueCashCard");

        when(cashCardManagement.issueCashCard(any(EPlatonEvent.class)))
                .thenReturn("Cash card issued successfully");

        // When
        Object result = cashCardManagement.issueCashCard(event);

        // Then
        assertNotNull(result);
        assertEquals("Cash card issued successfully", result);

        verify(cashCardManagement).issueCashCard(event);
    }

    @Test
    void testDepositFacadeThroughTCF() throws Exception {
        // Given
        EPlatonEvent event = new EPlatonEvent();
        event.setTransactionId("TEST-DEPOSIT-001");
        event.setServiceName("Deposit");
        event.setMethodName("createDepositAccount");

        when(depositManagement.createDepositAccount(any(EPlatonEvent.class)))
                .thenReturn("Deposit account created successfully");

        // When
        Object result = depositManagement.createDepositAccount(event);

        // Then
        assertNotNull(result);
        assertEquals("Deposit account created successfully", result);

        verify(depositManagement).createDepositAccount(event);
    }

    @Test
    void testCommonFacadeThroughTCF() throws Exception {
        // Given
        EPlatonEvent event = new EPlatonEvent();
        event.setTransactionId("TEST-COMMON-001");
        event.setServiceName("Common");
        event.setMethodName("execute");

        when(commonManagement.execute(any(EPlatonEvent.class)))
                .thenReturn("Common service executed successfully");

        // When
        Object result = commonManagement.execute(event);

        // Then
        assertNotNull(result);
        assertEquals("Common service executed successfully", result);

        verify(commonManagement).execute(event);
    }

    @Test
    void testCashCardActionThroughTCF() throws Exception {
        // Given
        EPlatonEvent event = new EPlatonEvent();
        event.setTransactionId("TEST-ACTION-CASHCARD-001");
        event.setServiceName("CashCard");
        event.setMethodName("issueCashCard");

        when(cashCardBizAction.act(any(EPlatonEvent.class)))
                .thenReturn("Cash card action executed successfully");

        // When
        Object result = cashCardBizAction.act(event);

        // Then
        assertNotNull(result);
        assertEquals("Cash card action executed successfully", result);

        verify(cashCardBizAction).act(event);
    }

    @Test
    void testDepositActionThroughTCF() throws Exception {
        // Given
        EPlatonEvent event = new EPlatonEvent();
        event.setTransactionId("TEST-ACTION-DEPOSIT-001");
        event.setServiceName("Deposit");
        event.setMethodName("createDepositAccount");

        when(depositBizAction.act(any(EPlatonEvent.class)))
                .thenReturn("Deposit action executed successfully");

        // When
        Object result = depositBizAction.act(event);

        // Then
        assertNotNull(result);
        assertEquals("Deposit action executed successfully", result);

        verify(depositBizAction).act(event);
    }

    @Test
    void testCommonActionThroughTCF() throws Exception {
        // Given
        EPlatonEvent event = new EPlatonEvent();
        event.setTransactionId("TEST-ACTION-COMMON-001");
        event.setServiceName("Common");
        event.setMethodName("execute");

        when(commonBizAction.act(any(EPlatonEvent.class)))
                .thenReturn("Common action executed successfully");

        // When
        Object result = commonBizAction.act(event);

        // Then
        assertNotNull(result);
        assertEquals("Common action executed successfully", result);

        verify(commonBizAction).act(event);
    }

    @Test
    void testTCFValidation() throws Exception {
        // Given
        EPlatonEvent event = new EPlatonEvent();
        event.setTransactionId("TEST-VALIDATION-001");
        event.setServiceName("CashCard");
        event.setMethodName("issueCashCard");

        // When
        boolean result = tcf.validate(event);

        // Then
        assertTrue(result);
    }

    @Test
    void testTCFValidationWithNullEvent() throws Exception {
        // When
        boolean result = tcf.validate(null);

        // Then
        assertFalse(result);
    }

    @Test
    void testTCFValidationWithEmptyTransactionId() throws Exception {
        // Given
        EPlatonEvent event = new EPlatonEvent();
        event.setTransactionId("");
        event.setServiceName("CashCard");
        event.setMethodName("issueCashCard");

        // When
        boolean result = tcf.validate(event);

        // Then
        assertFalse(result);
    }
}