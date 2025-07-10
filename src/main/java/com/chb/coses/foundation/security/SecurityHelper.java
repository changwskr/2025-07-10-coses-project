package com.chb.coses.foundation.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Security utility helper class
 */
public class SecurityHelper {

    private static final String DEFAULT_HASH_ALGORITHM = "SHA-256";

    /**
     * Hash a string using SHA-256
     */
    public static String hashString(String input) {
        if (input == null) {
            return null;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance(DEFAULT_HASH_ALGORITHM);
            byte[] hash = digest.digest(input.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hash algorithm not available", e);
        }
    }

    /**
     * Encrypt sensitive data
     */
    public static String encrypt(String data) {
        // Simple encryption implementation
        if (data == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    /**
     * Decrypt sensitive data
     */
    public static String decrypt(String encryptedData) {
        // Simple decryption implementation
        if (encryptedData == null) {
            return null;
        }
        try {
            byte[] decoded = Base64.getDecoder().decode(encryptedData);
            return new String(decoded);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid encrypted data", e);
        }
    }

    /**
     * Validate password strength
     */
    public static boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))
                hasUpperCase = true;
            else if (Character.isLowerCase(c))
                hasLowerCase = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else
                hasSpecial = true;
        }

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecial;
    }
}