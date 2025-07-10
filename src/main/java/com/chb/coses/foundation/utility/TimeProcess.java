package com.chb.coses.foundation.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Time processing utility class
 */
public class TimeProcess {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_TIMEZONE = "Asia/Seoul";

    /**
     * Get current timestamp
     * 
     * @return current timestamp in milliseconds
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * Get current date as string
     * 
     * @return current date string
     */
    public static String getCurrentDateString() {
        return getCurrentDateString(DEFAULT_DATE_FORMAT);
    }

    /**
     * Get current date as string with specified format
     * 
     * @param format date format
     * @return current date string
     */
    public static String getCurrentDateString(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
        return sdf.format(new Date());
    }

    /**
     * Format timestamp to string
     * 
     * @param timestamp timestamp in milliseconds
     * @return formatted date string
     */
    public static String formatTimestamp(long timestamp) {
        return formatTimestamp(timestamp, DEFAULT_DATE_FORMAT);
    }

    /**
     * Format timestamp to string with specified format
     * 
     * @param timestamp timestamp in milliseconds
     * @param format    date format
     * @return formatted date string
     */
    public static String formatTimestamp(long timestamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
        return sdf.format(new Date(timestamp));
    }

    /**
     * Parse date string to timestamp
     * 
     * @param dateString date string
     * @return timestamp in milliseconds
     */
    public static long parseDateString(String dateString) {
        return parseDateString(dateString, DEFAULT_DATE_FORMAT);
    }

    /**
     * Parse date string to timestamp with specified format
     * 
     * @param dateString date string
     * @param format     date format
     * @return timestamp in milliseconds
     */
    public static long parseDateString(String dateString, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
            return sdf.parse(dateString).getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Add days to current date
     * 
     * @param days number of days to add
     * @return timestamp after adding days
     */
    public static long addDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTimeInMillis();
    }

    /**
     * Add hours to current date
     * 
     * @param hours number of hours to add
     * @return timestamp after adding hours
     */
    public static long addHours(int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTimeInMillis();
    }

    /**
     * Add minutes to current date
     * 
     * @param minutes number of minutes to add
     * @return timestamp after adding minutes
     */
    public static long addMinutes(int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTimeInMillis();
    }

    /**
     * Get start of day timestamp
     * 
     * @return start of day timestamp
     */
    public static long getStartOfDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * Get end of day timestamp
     * 
     * @return end of day timestamp
     */
    public static long getEndOfDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTimeInMillis();
    }

    private TimeProcess() {
        // Utility class - prevent instantiation
    }
}