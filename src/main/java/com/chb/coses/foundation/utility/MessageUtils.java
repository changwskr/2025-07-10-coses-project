package com.chb.coses.foundation.utility;

import java.util.ResourceBundle;

/**
 * Message utility class for handling internationalized messages
 */
public class MessageUtils {

    private static ResourceBundle messageBundle;

    static {
        try {
            messageBundle = ResourceBundle.getBundle("messages");
        } catch (Exception e) {
            // Fallback to default messages
            System.err.println("Warning: Could not load message bundle: " + e.getMessage());
        }
    }

    /**
     * Get message by key
     * 
     * @param key message key
     * @return message value
     */
    public static String getMessage(String key) {
        if (messageBundle != null && messageBundle.containsKey(key)) {
            return messageBundle.getString(key);
        }
        return key; // Return key if message not found
    }

    /**
     * Get message by key with parameters
     * 
     * @param key    message key
     * @param params parameters to substitute
     * @return formatted message
     */
    public static String getMessage(String key, Object... params) {
        String message = getMessage(key);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                message = message.replace("{" + i + "}", String.valueOf(params[i]));
            }
        }
        return message;
    }
}