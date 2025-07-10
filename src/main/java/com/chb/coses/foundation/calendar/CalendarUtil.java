package com.chb.coses.foundation.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUtil {
    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}