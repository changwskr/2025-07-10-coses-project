package com.chb.banking.business;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for AccountService
 */
public class AccountServiceTest {

    @Test
    public void testValidateAccount() {
        AccountService service = new AccountService();

        assertTrue("Valid account should return true",
                service.validateAccount("1234567890"));
        assertFalse("Null account should return false",
                service.validateAccount(null));
        assertFalse("Empty account should return false",
                service.validateAccount(""));
    }

    @Test
    public void testGetBalance() {
        AccountService service = new AccountService();

        double balance = service.getBalance("1234567890");
        assertEquals("Initial balance should be 0.0", 0.0, balance, 0.001);
    }
}