package com.chb.coses.foundation.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Calendar utility helper class
 */
public class CalendarHelper {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Get current date as string
     */
    public static String getCurrentDate() {
        return getCurrentDate(DEFAULT_DATE_FORMAT);
    }

    /**
     * Get current date with custom format
     */
    public static String getCurrentDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * Get current datetime as string
     */
    public static String getCurrentDateTime() {
        return getCurrentDateTime(DEFAULT_DATETIME_FORMAT);
    }

    /**
     * Get current datetime with custom format
     */
    public static String getCurrentDateTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * Add days to current date
     */
    public static Date addDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * Add months to current date
     */
    public static Date addMonths(int months) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * Check if date is business day (Monday-Friday)
     */
    public static boolean isBusinessDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY;
    }

    /**
     * Get next business day
     */
    public static Date getNextBusinessDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        do {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        } while (!isBusinessDay(cal.getTime()));

        return cal.getTime();
    }

    /**
     * Get previous business day
     */
    public static Date getPreviousBusinessDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        do {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        } while (!isBusinessDay(cal.getTime()));

        return cal.getTime();
    }

    /**
     * Check if year is leap year
     */
    public static boolean isLeapYear(int year) {
        return new GregorianCalendar().isLeapYear(year);
    }

    /**
     * Get days between two dates
     */
    public static long getDaysBetween(Date startDate, Date endDate) {
        long diffInMillies = endDate.getTime() - startDate.getTime();
        return diffInMillies / (24 * 60 * 60 * 1000);
    }
}