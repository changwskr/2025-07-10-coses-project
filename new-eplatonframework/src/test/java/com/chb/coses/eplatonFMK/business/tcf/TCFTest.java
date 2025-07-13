package com.chb.coses.eplatonFMK.business.tcf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TCF 테스트 클래스
 * 
 * @author Spring Boot Migration
 * @version 1.0
 */
@SpringBootTest
class TCFTest {

    @InjectMocks
    private TCF tcf;

    @Mock
    private STF stf;

    @Mock
    private ETF etf;

    @Mock
    private BTF btf;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute() throws Exception {
        // Given
        EPlatonEvent event = new EPlatonEvent();
        event.setTransactionId("TEST-001");

        when(stf.execute(any(EPlatonEvent.class))).thenReturn("STF Result");
        when(etf.execute(any(EPlatonEvent.class))).thenReturn("ETF Result");
        when(btf.execute(any(EPlatonEvent.class))).thenReturn("BTF Result");
        when(stf.validate(any(EPlatonEvent.class))).thenReturn(true);
        when(etf.validate(any(EPlatonEvent.class))).thenReturn(true);
        when(btf.validate(any(EPlatonEvent.class))).thenReturn(true);

        // When
        Object result = tcf.execute(event);

        // Then
        assertNotNull(result);
        assertEquals("BTF Result", result);

        verify(stf).execute(event);
        verify(etf).execute(event);
        verify(btf).execute(event);
    }

    @Test
    void testValidate() throws Exception {
        // Given
        EPlatonEvent event = new EPlatonEvent();
        event.setTransactionId("TEST-001");

        when(stf.validate(any(EPlatonEvent.class))).thenReturn(true);
        when(etf.validate(any(EPlatonEvent.class))).thenReturn(true);
        when(btf.validate(any(EPlatonEvent.class))).thenReturn(true);

        // When
        boolean result = tcf.validate(event);

        // Then
        assertTrue(result);

        verify(stf).validate(event);
        verify(etf).validate(event);
        verify(btf).validate(event);
    }

    @Test
    void testValidateWithNullEvent() throws Exception {
        // When
        boolean result = tcf.validate(null);

        // Then
        assertFalse(result);
    }

    @Test
    void testValidateWithEmptyTransactionId() throws Exception {
        // Given
        EPlatonEvent event = new EPlatonEvent();
        event.setTransactionId("");

        // When
        boolean result = tcf.validate(event);

        // Then
        assertFalse(result);
    }
}