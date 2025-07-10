package com.banking.kdb.oversea.foundation.utility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CommonUtil
 */
@ExtendWith(MockitoExtension.class)
class CommonUtilTest {

    @Test
    void testGenerateUniqueId() {
        String id1 = CommonUtil.generateUniqueId();
        String id2 = CommonUtil.generateUniqueId();

        assertNotNull(id1);
        assertNotNull(id2);
        assertNotEquals(id1, id2);
        assertEquals(32, id1.length());
        assertEquals(32, id2.length());
    }

    @Test
    void testGenerateCustomerId() {
        String customerId = CommonUtil.generateCustomerId();

        assertNotNull(customerId);
        assertTrue(customerId.startsWith("CUST"));
        assertTrue(customerId.length() > 4);
    }

    @Test
    void testGenerateAccountNumber() {
        String accountNumber = CommonUtil.generateAccountNumber();

        assertNotNull(accountNumber);
        assertTrue(accountNumber.startsWith("ACC"));
        assertTrue(accountNumber.length() > 3);
    }

    @Test
    void testGenerateTransactionId() {
        String transactionId = CommonUtil.generateTransactionId();

        assertNotNull(transactionId);
        assertTrue(transactionId.startsWith("TXN"));
        assertTrue(transactionId.length() > 3);
    }

    @Test
    void testGenerateRandomNumber() {
        int min = 1;
        int max = 100;

        for (int i = 0; i < 100; i++) {
            int random = CommonUtil.generateRandomNumber(min, max);
            assertTrue(random >= min && random <= max);
        }
    }

    @Test
    void testGenerateRandomString() {
        int length = 10;
        String randomString = CommonUtil.generateRandomString(length);

        assertNotNull(randomString);
        assertEquals(length, randomString.length());
        assertTrue(randomString.matches("[A-Za-z0-9]+"));
    }

    @Test
    void testGenerateRandomNumericString() {
        int length = 8;
        String randomString = CommonUtil.generateRandomNumericString(length);

        assertNotNull(randomString);
        assertEquals(length, randomString.length());
        assertTrue(randomString.matches("[0-9]+"));
    }

    @Test
    void testIsValidEmail() {
        assertTrue(CommonUtil.isValidEmail("test@example.com"));
        assertTrue(CommonUtil.isValidEmail("user.name@domain.co.uk"));
        assertTrue(CommonUtil.isValidEmail("user+tag@example.org"));

        assertFalse(CommonUtil.isValidEmail("invalid-email"));
        assertFalse(CommonUtil.isValidEmail("@example.com"));
        assertFalse(CommonUtil.isValidEmail("test@"));
        assertFalse(CommonUtil.isValidEmail(""));
        assertFalse(CommonUtil.isValidEmail(null));
    }

    @Test
    void testIsValidPhoneNumber() {
        assertTrue(CommonUtil.isValidPhoneNumber("+1234567890"));
        assertTrue(CommonUtil.isValidPhoneNumber("123-456-7890"));
        assertTrue(CommonUtil.isValidPhoneNumber("(123) 456-7890"));
        assertTrue(CommonUtil.isValidPhoneNumber("123 456 7890"));

        assertFalse(CommonUtil.isValidPhoneNumber("invalid-phone"));
        assertFalse(CommonUtil.isValidPhoneNumber("abc-def-ghij"));
        assertFalse(CommonUtil.isValidPhoneNumber(""));
        assertFalse(CommonUtil.isValidPhoneNumber(null));
    }

    @Test
    void testIsAlphaNumeric() {
        assertTrue(CommonUtil.isAlphaNumeric("abc123"));
        assertTrue(CommonUtil.isAlphaNumeric("ABC123"));
        assertTrue(CommonUtil.isAlphaNumeric("123abc"));

        assertFalse(CommonUtil.isAlphaNumeric("abc-123"));
        assertFalse(CommonUtil.isAlphaNumeric("abc 123"));
        assertFalse(CommonUtil.isAlphaNumeric(""));
        assertFalse(CommonUtil.isAlphaNumeric(null));
    }

    @Test
    void testIsNumeric() {
        assertTrue(CommonUtil.isNumeric("123"));
        assertTrue(CommonUtil.isNumeric("0"));
        assertTrue(CommonUtil.isNumeric("123456789"));

        assertFalse(CommonUtil.isNumeric("123abc"));
        assertFalse(CommonUtil.isNumeric("abc123"));
        assertFalse(CommonUtil.isNumeric(""));
        assertFalse(CommonUtil.isNumeric(null));
    }

    @Test
    void testIsAlpha() {
        assertTrue(CommonUtil.isAlpha("abc"));
        assertTrue(CommonUtil.isAlpha("ABC"));
        assertTrue(CommonUtil.isAlpha("abc def"));

        assertFalse(CommonUtil.isAlpha("abc123"));
        assertFalse(CommonUtil.isAlpha("123"));
        assertFalse(CommonUtil.isAlpha(""));
        assertFalse(CommonUtil.isAlpha(null));
    }

    @Test
    void testIsValidPassword() {
        assertTrue(CommonUtil.isValidPassword("Password123!"));
        assertTrue(CommonUtil.isValidPassword("MyP@ssw0rd"));
        assertTrue(CommonUtil.isValidPassword("Str0ng#Pass"));

        assertFalse(CommonUtil.isValidPassword("weak"));
        assertFalse(CommonUtil.isValidPassword("password123"));
        assertFalse(CommonUtil.isValidPassword("PASSWORD123"));
        assertFalse(CommonUtil.isValidPassword("Password123"));
        assertFalse(CommonUtil.isValidPassword(""));
        assertFalse(CommonUtil.isValidPassword(null));
    }

    @Test
    void testHashPassword() {
        String password = "testPassword123!";
        String hash1 = CommonUtil.hashPassword(password);
        String hash2 = CommonUtil.hashPassword(password);

        assertNotNull(hash1);
        assertNotNull(hash2);
        assertEquals(hash1, hash2); // Same password should produce same hash
        assertNotEquals(password, hash1); // Hash should not be the same as password
    }

    @Test
    void testFormatCurrency() {
        BigDecimal amount = new BigDecimal("1234.56");
        String formatted = CommonUtil.formatCurrency(amount);

        assertEquals("1,234.56", formatted);
    }

    @Test
    void testFormatCurrencyWithCode() {
        BigDecimal amount = new BigDecimal("1234.56");
        String formatted = CommonUtil.formatCurrency(amount, "USD");

        assertEquals("1,234.56 USD", formatted);
    }

    @Test
    void testFormatNumber() {
        BigDecimal number = new BigDecimal("1234567");
        String formatted = CommonUtil.formatNumber(number);

        assertEquals("1,234,567", formatted);
    }

    @Test
    void testRoundAmount() {
        BigDecimal amount = new BigDecimal("123.456");
        BigDecimal rounded = CommonUtil.roundAmount(amount);

        assertEquals(new BigDecimal("123.46"), rounded);
    }

    @Test
    void testCalculateInterest() {
        BigDecimal principal = new BigDecimal("10000");
        double rate = 0.05; // 5%
        int days = 365;

        BigDecimal interest = CommonUtil.calculateInterest(principal, rate, days);

        assertEquals(new BigDecimal("500.00"), interest);
    }

    @Test
    void testCalculateCompoundInterest() {
        BigDecimal principal = new BigDecimal("10000");
        double rate = 0.05; // 5%
        int years = 1;
        int compoundingFrequency = 12; // Monthly

        BigDecimal interest = CommonUtil.calculateCompoundInterest(principal, rate, years, compoundingFrequency);

        assertTrue(interest.compareTo(BigDecimal.ZERO) > 0);
        assertTrue(interest.compareTo(new BigDecimal("500")) > 0); // Should be more than simple interest
    }

    @Test
    void testFormatDate() {
        LocalDate date = LocalDate.of(2023, 12, 25);
        String formatted = CommonUtil.formatDate(date);

        assertEquals("2023-12-25", formatted);
    }

    @Test
    void testFormatDateWithCustomPattern() {
        LocalDate date = LocalDate.of(2023, 12, 25);
        String formatted = CommonUtil.formatDate(date, "dd/MM/yyyy");

        assertEquals("25/12/2023", formatted);
    }

    @Test
    void testFormatDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 25, 14, 30, 45);
        String formatted = CommonUtil.formatDateTime(dateTime);

        assertEquals("2023-12-25 14:30:45", formatted);
    }

    @Test
    void testParseDate() {
        String dateStr = "2023-12-25";
        LocalDate date = CommonUtil.parseDate(dateStr);

        assertNotNull(date);
        assertEquals(2023, date.getYear());
        assertEquals(12, date.getMonthValue());
        assertEquals(25, date.getDayOfMonth());
    }

    @Test
    void testParseDateTime() {
        String dateTimeStr = "2023-12-25 14:30:45";
        LocalDateTime dateTime = CommonUtil.parseDateTime(dateTimeStr);

        assertNotNull(dateTime);
        assertEquals(2023, dateTime.getYear());
        assertEquals(12, dateTime.getMonthValue());
        assertEquals(25, dateTime.getDayOfMonth());
        assertEquals(14, dateTime.getHour());
        assertEquals(30, dateTime.getMinute());
        assertEquals(45, dateTime.getSecond());
    }

    @Test
    void testIsNullOrEmpty() {
        assertTrue(CommonUtil.isNullOrEmpty(null));
        assertTrue(CommonUtil.isNullOrEmpty(""));
        assertTrue(CommonUtil.isNullOrEmpty("   "));

        assertFalse(CommonUtil.isNullOrEmpty("test"));
        assertFalse(CommonUtil.isNullOrEmpty(" test "));
    }

    @Test
    void testIsNotNullOrEmpty() {
        assertFalse(CommonUtil.isNotNullOrEmpty(null));
        assertFalse(CommonUtil.isNotNullOrEmpty(""));
        assertFalse(CommonUtil.isNotNullOrEmpty("   "));

        assertTrue(CommonUtil.isNotNullOrEmpty("test"));
        assertTrue(CommonUtil.isNotNullOrEmpty(" test "));
    }

    @Test
    void testGetDefaultIfNull() {
        assertEquals("default", CommonUtil.getDefaultIfNull(null, "default"));
        assertEquals("default", CommonUtil.getDefaultIfNull("", "default"));
        assertEquals("test", CommonUtil.getDefaultIfNull("test", "default"));
    }

    @Test
    void testGetDefaultIfNullBigDecimal() {
        BigDecimal defaultValue = new BigDecimal("100");

        assertEquals(defaultValue, CommonUtil.getDefaultIfNull(null, defaultValue));
        assertEquals(new BigDecimal("50"), CommonUtil.getDefaultIfNull(new BigDecimal("50"), defaultValue));
    }

    @Test
    void testTruncateString() {
        assertEquals("test", CommonUtil.truncateString("test", 10));
        assertEquals("test", CommonUtil.truncateString("test", 4));
        assertEquals("tes", CommonUtil.truncateString("test", 3));
        assertNull(CommonUtil.truncateString(null, 10));
    }

    @Test
    void testMaskSensitiveData() {
        assertEquals("****1234", CommonUtil.maskSensitiveData("12345678", 4));
        assertEquals("****", CommonUtil.maskSensitiveData("1234", 4));
        assertEquals("1234", CommonUtil.maskSensitiveData("1234", 4));
        assertNull(CommonUtil.maskSensitiveData(null, 4));
    }

    @Test
    void testMaskEmail() {
        assertEquals("j***n@example.com", CommonUtil.maskEmail("john@example.com"));
        assertEquals("a@example.com", CommonUtil.maskEmail("a@example.com"));
        assertEquals("ab@example.com", CommonUtil.maskEmail("ab@example.com"));
        assertEquals("a***b@example.com", CommonUtil.maskEmail("alice@example.com"));
        assertEquals("invalid", CommonUtil.maskEmail("invalid"));
    }

    @Test
    void testMaskPhoneNumber() {
        assertEquals("12****90", CommonUtil.maskPhoneNumber("1234567890"));
        assertEquals("12****34", CommonUtil.maskPhoneNumber("1234"));
        assertEquals("12****56", CommonUtil.maskPhoneNumber("123456"));
        assertEquals("123", CommonUtil.maskPhoneNumber("123"));
        assertEquals("", CommonUtil.maskPhoneNumber(""));
        assertNull(CommonUtil.maskPhoneNumber(null));
    }

    @Test
    void testIsValidAmount() {
        assertTrue(CommonUtil.isValidAmount(new BigDecimal("100")));
        assertTrue(CommonUtil.isValidAmount(new BigDecimal("0.01")));

        assertFalse(CommonUtil.isValidAmount(new BigDecimal("0")));
        assertFalse(CommonUtil.isValidAmount(new BigDecimal("-100")));
        assertFalse(CommonUtil.isValidAmount(null));
    }

    @Test
    void testIsNonNegativeAmount() {
        assertTrue(CommonUtil.isNonNegativeAmount(new BigDecimal("100")));
        assertTrue(CommonUtil.isNonNegativeAmount(new BigDecimal("0")));

        assertFalse(CommonUtil.isNonNegativeAmount(new BigDecimal("-100")));
        assertFalse(CommonUtil.isNonNegativeAmount(null));
    }

    @Test
    void testExceedsLimit() {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal limit = new BigDecimal("50");

        assertTrue(CommonUtil.exceedsLimit(amount, limit));
        assertFalse(CommonUtil.exceedsLimit(limit, amount));
        assertFalse(CommonUtil.exceedsLimit(null, limit));
        assertFalse(CommonUtil.exceedsLimit(amount, null));
    }

    @Test
    void testCalculatePercentage() {
        BigDecimal amount = new BigDecimal("50");
        BigDecimal total = new BigDecimal("200");

        BigDecimal percentage = CommonUtil.calculatePercentage(amount, total);

        assertEquals(new BigDecimal("25.00"), percentage);
    }

    @Test
    void testGetCurrentTimestamp() {
        String timestamp = CommonUtil.getCurrentTimestamp();

        assertNotNull(timestamp);
        assertTrue(timestamp.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }

    @Test
    void testGetCurrentDate() {
        String date = CommonUtil.getCurrentDate();

        assertNotNull(date);
        assertTrue(date.matches("\\d{4}-\\d{2}-\\d{2}"));
    }

    @Test
    void testIsDateInPast() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        LocalDate futureDate = LocalDate.now().plusDays(1);
        LocalDate today = LocalDate.now();

        assertTrue(CommonUtil.isDateInPast(pastDate));
        assertFalse(CommonUtil.isDateInPast(futureDate));
        assertFalse(CommonUtil.isDateInPast(today));
        assertFalse(CommonUtil.isDateInPast(null));
    }

    @Test
    void testIsDateInFuture() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        LocalDate futureDate = LocalDate.now().plusDays(1);
        LocalDate today = LocalDate.now();

        assertFalse(CommonUtil.isDateInFuture(pastDate));
        assertTrue(CommonUtil.isDateInFuture(futureDate));
        assertFalse(CommonUtil.isDateInFuture(today));
        assertFalse(CommonUtil.isDateInFuture(null));
    }

    @Test
    void testIsDateToday() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        LocalDate futureDate = LocalDate.now().plusDays(1);
        LocalDate today = LocalDate.now();

        assertFalse(CommonUtil.isDateToday(pastDate));
        assertFalse(CommonUtil.isDateToday(futureDate));
        assertTrue(CommonUtil.isDateToday(today));
        assertFalse(CommonUtil.isDateToday(null));
    }

    @Test
    void testGetDaysBetween() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        long days = CommonUtil.getDaysBetween(startDate, endDate);

        assertEquals(9, days);
    }

    @Test
    void testToBigDecimal() {
        assertEquals(new BigDecimal("123.45"), CommonUtil.toBigDecimal("123.45"));
        assertEquals(BigDecimal.ZERO, CommonUtil.toBigDecimal(""));
        assertEquals(BigDecimal.ZERO, CommonUtil.toBigDecimal(null));
        assertEquals(BigDecimal.ZERO, CommonUtil.toBigDecimal("invalid"));
    }

    @Test
    void testToInteger() {
        assertEquals(Integer.valueOf(123), CommonUtil.toInteger("123"));
        assertNull(CommonUtil.toInteger(""));
        assertNull(CommonUtil.toInteger(null));
        assertNull(CommonUtil.toInteger("invalid"));
    }

    @Test
    void testToLong() {
        assertEquals(Long.valueOf(123L), CommonUtil.toLong("123"));
        assertNull(CommonUtil.toLong(""));
        assertNull(CommonUtil.toLong(null));
        assertNull(CommonUtil.toLong("invalid"));
    }

    @Test
    void testToDouble() {
        assertEquals(Double.valueOf(123.45), CommonUtil.toDouble("123.45"));
        assertNull(CommonUtil.toDouble(""));
        assertNull(CommonUtil.toDouble(null));
        assertNull(CommonUtil.toDouble("invalid"));
    }
}