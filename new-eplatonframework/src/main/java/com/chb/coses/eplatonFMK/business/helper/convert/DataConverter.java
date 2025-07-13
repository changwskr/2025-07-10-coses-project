package com.chb.coses.eplatonFMK.business.helper.convert;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.math.BigDecimal;

/**
 * Data Converter
 * Spring 기반으로 전환된 데이터 변환 유틸리티 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class DataConverter {

    private static final Logger logger = LoggerFactory.getLogger(DataConverter.class);

    /**
     * 날짜 포맷터
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HHmmss");
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 숫자 포맷터
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");

    /**
     * String을 Date로 변환
     */
    public static Date stringToDate(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            logger.error("stringToDate() 에러", e);
            return null;
        }
    }

    /**
     * Date를 String으로 변환
     */
    public static String dateToString(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            logger.error("dateToString() 에러", e);
            return null;
        }
    }

    /**
     * String을 Integer로 변환
     */
    public static Integer stringToInteger(String str) {
        try {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            return Integer.parseInt(str.trim());
        } catch (Exception e) {
            logger.error("stringToInteger() 에러", e);
            return null;
        }
    }

    /**
     * String을 Long으로 변환
     */
    public static Long stringToLong(String str) {
        try {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            return Long.parseLong(str.trim());
        } catch (Exception e) {
            logger.error("stringToLong() 에러", e);
            return null;
        }
    }

    /**
     * String을 Double로 변환
     */
    public static Double stringToDouble(String str) {
        try {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            return Double.parseDouble(str.trim());
        } catch (Exception e) {
            logger.error("stringToDouble() 에러", e);
            return null;
        }
    }

    /**
     * String을 BigDecimal로 변환
     */
    public static BigDecimal stringToBigDecimal(String str) {
        try {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            return new BigDecimal(str.trim());
        } catch (Exception e) {
            logger.error("stringToBigDecimal() 에러", e);
            return null;
        }
    }

    /**
     * Integer를 String으로 변환
     */
    public static String integerToString(Integer value) {
        try {
            if (value == null) {
                return null;
            }
            return value.toString();
        } catch (Exception e) {
            logger.error("integerToString() 에러", e);
            return null;
        }
    }

    /**
     * Long을 String으로 변환
     */
    public static String longToString(Long value) {
        try {
            if (value == null) {
                return null;
            }
            return value.toString();
        } catch (Exception e) {
            logger.error("longToString() 에러", e);
            return null;
        }
    }

    /**
     * Double을 String으로 변환
     */
    public static String doubleToString(Double value) {
        try {
            if (value == null) {
                return null;
            }
            return value.toString();
        } catch (Exception e) {
            logger.error("doubleToString() 에러", e);
            return null;
        }
    }

    /**
     * BigDecimal을 String으로 변환
     */
    public static String bigDecimalToString(BigDecimal value) {
        try {
            if (value == null) {
                return null;
            }
            return value.toString();
        } catch (Exception e) {
            logger.error("bigDecimalToString() 에러", e);
            return null;
        }
    }

    /**
     * 숫자를 포맷된 문자열로 변환
     */
    public static String formatNumber(Number value, String pattern) {
        try {
            if (value == null) {
                return null;
            }
            DecimalFormat df = new DecimalFormat(pattern);
            return df.format(value);
        } catch (Exception e) {
            logger.error("formatNumber() 에러", e);
            return null;
        }
    }

    /**
     * 날짜를 포맷된 문자열로 변환
     */
    public static String formatDate(Date date, String pattern) {
        try {
            if (date == null) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Exception e) {
            logger.error("formatDate() 에러", e);
            return null;
        }
    }

    /**
     * 문자열을 대문자로 변환
     */
    public static String toUpperCase(String str) {
        try {
            if (str == null) {
                return null;
            }
            return str.toUpperCase();
        } catch (Exception e) {
            logger.error("toUpperCase() 에러", e);
            return str;
        }
    }

    /**
     * 문자열을 소문자로 변환
     */
    public static String toLowerCase(String str) {
        try {
            if (str == null) {
                return null;
            }
            return str.toLowerCase();
        } catch (Exception e) {
            logger.error("toLowerCase() 에러", e);
            return str;
        }
    }

    /**
     * 문자열 좌측 공백 제거
     */
    public static String trimLeft(String str) {
        try {
            if (str == null) {
                return null;
            }
            return str.replaceAll("^\\s+", "");
        } catch (Exception e) {
            logger.error("trimLeft() 에러", e);
            return str;
        }
    }

    /**
     * 문자열 우측 공백 제거
     */
    public static String trimRight(String str) {
        try {
            if (str == null) {
                return null;
            }
            return str.replaceAll("\\s+$", "");
        } catch (Exception e) {
            logger.error("trimRight() 에러", e);
            return str;
        }
    }

    /**
     * 문자열 좌우 공백 제거
     */
    public static String trim(String str) {
        try {
            if (str == null) {
                return null;
            }
            return str.trim();
        } catch (Exception e) {
            logger.error("trim() 에러", e);
            return str;
        }
    }

    /**
     * 문자열을 지정된 길이로 자르기
     */
    public static String substring(String str, int length) {
        try {
            if (str == null) {
                return null;
            }
            if (str.length() <= length) {
                return str;
            }
            return str.substring(0, length);
        } catch (Exception e) {
            logger.error("substring() 에러", e);
            return str;
        }
    }

    /**
     * 문자열을 지정된 길이로 패딩 (좌측)
     */
    public static String padLeft(String str, int length, char padChar) {
        try {
            if (str == null) {
                str = "";
            }
            while (str.length() < length) {
                str = padChar + str;
            }
            return str;
        } catch (Exception e) {
            logger.error("padLeft() 에러", e);
            return str;
        }
    }

    /**
     * 문자열을 지정된 길이로 패딩 (우측)
     */
    public static String padRight(String str, int length, char padChar) {
        try {
            if (str == null) {
                str = "";
            }
            while (str.length() < length) {
                str = str + padChar;
            }
            return str;
        } catch (Exception e) {
            logger.error("padRight() 에러", e);
            return str;
        }
    }

    /**
     * null 체크
     */
    public static String nullToEmpty(String str) {
        return str == null ? "" : str;
    }

    /**
     * 빈 문자열 체크
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 빈 문자열이 아닌지 체크
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}