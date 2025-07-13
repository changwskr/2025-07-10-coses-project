package com.chb.coses.eplatonFMK.business.helper.tpmservice;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * TPM Service Utils
 * Spring 기반으로 전환된 TPM 서비스 유틸리티 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class TPMServiceUtils {

    private static final Logger logger = LoggerFactory.getLogger(TPMServiceUtils.class);

    /**
     * UUID 생성
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 세션 ID 생성
     */
    public static String generateSessionId() {
        return "SESS_" + System.currentTimeMillis() + "_" + ThreadLocalRandom.current().nextInt(1000, 9999);
    }

    /**
     * 트랜잭션 ID 생성
     */
    public static String generateTransactionId() {
        return "TXN_" + System.currentTimeMillis() + "_" + ThreadLocalRandom.current().nextInt(1000, 9999);
    }

    /**
     * 이벤트 번호 생성
     */
    public static String generateEventNo() {
        return "EVT_" + System.currentTimeMillis() + "_" + ThreadLocalRandom.current().nextInt(1000, 9999);
    }

    /**
     * 현재 날짜 문자열 가져오기 (yyyyMMdd)
     */
    public static String getCurrentDate() {
        return new SimpleDateFormat(TPMServiceConstants.DATE_FORMAT_YYYYMMDD).format(new Date());
    }

    /**
     * 현재 시간 문자열 가져오기 (HHmmss)
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat(TPMServiceConstants.TIME_FORMAT_HHMMSS).format(new Date());
    }

    /**
     * 현재 날짜시간 문자열 가져오기 (yyyyMMddHHmmss)
     */
    public static String getCurrentDateTime() {
        return new SimpleDateFormat(TPMServiceConstants.DATETIME_FORMAT_YYYYMMDDHHMMSS).format(new Date());
    }

    /**
     * 날짜 포맷팅
     */
    public static String formatDate(Date date, String pattern) {
        try {
            if (date == null) {
                return null;
            }
            return new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            logger.error("날짜 포맷팅 에러", e);
            return null;
        }
    }

    /**
     * 문자열을 날짜로 파싱
     */
    public static Date parseDate(String dateStr, String pattern) {
        try {
            if (dateStr == null || dateStr.trim().isEmpty()) {
                return null;
            }
            return new SimpleDateFormat(pattern).parse(dateStr);
        } catch (Exception e) {
            logger.error("날짜 파싱 에러", e);
            return null;
        }
    }

    /**
     * 숫자 포맷팅
     */
    public static String formatNumber(Number number, String pattern) {
        try {
            if (number == null) {
                return null;
            }
            return new DecimalFormat(pattern).format(number);
        } catch (Exception e) {
            logger.error("숫자 포맷팅 에러", e);
            return null;
        }
    }

    /**
     * 문자열을 숫자로 변환
     */
    public static Integer parseInteger(String str) {
        try {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            return Integer.parseInt(str.trim());
        } catch (Exception e) {
            logger.error("정수 파싱 에러", e);
            return null;
        }
    }

    /**
     * 문자열을 Long으로 변환
     */
    public static Long parseLong(String str) {
        try {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            return Long.parseLong(str.trim());
        } catch (Exception e) {
            logger.error("Long 파싱 에러", e);
            return null;
        }
    }

    /**
     * 문자열을 Double로 변환
     */
    public static Double parseDouble(String str) {
        try {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            return Double.parseDouble(str.trim());
        } catch (Exception e) {
            logger.error("Double 파싱 에러", e);
            return null;
        }
    }

    /**
     * 문자열을 Boolean으로 변환
     */
    public static Boolean parseBoolean(String str) {
        try {
            if (str == null || str.trim().isEmpty()) {
                return null;
            }
            return Boolean.parseBoolean(str.trim());
        } catch (Exception e) {
            logger.error("Boolean 파싱 에러", e);
            return null;
        }
    }

    /**
     * SHA-256 해시 생성
     */
    public static String generateSHA256(String input) {
        try {
            if (input == null) {
                return null;
            }
            MessageDigest md = MessageDigest.getInstance(TPMServiceConstants.SECURITY_ALGORITHM_SHA256);
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("SHA-256 해시 생성 에러", e);
            return null;
        }
    }

    /**
     * MD5 해시 생성
     */
    public static String generateMD5(String input) {
        try {
            if (input == null) {
                return null;
            }
            MessageDigest md = MessageDigest.getInstance(TPMServiceConstants.SECURITY_ALGORITHM_MD5);
            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("MD5 해시 생성 에러", e);
            return null;
        }
    }

    /**
     * 랜덤 문자열 생성
     */
    public static String generateRandomString(int length) {
        try {
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("랜덤 문자열 생성 에러", e);
            return null;
        }
    }

    /**
     * 랜덤 숫자 생성
     */
    public static String generateRandomNumber(int length) {
        try {
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                sb.append(random.nextInt(10));
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("랜덤 숫자 생성 에러", e);
            return null;
        }
    }

    /**
     * 문자열 좌측 패딩
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
            logger.error("좌측 패딩 에러", e);
            return str;
        }
    }

    /**
     * 문자열 우측 패딩
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
            logger.error("우측 패딩 에러", e);
            return str;
        }
    }

    /**
     * 문자열 자르기
     */
    public static String substring(String str, int start, int end) {
        try {
            if (str == null) {
                return null;
            }
            if (start < 0) {
                start = 0;
            }
            if (end > str.length()) {
                end = str.length();
            }
            if (start >= end) {
                return "";
            }
            return str.substring(start, end);
        } catch (Exception e) {
            logger.error("문자열 자르기 에러", e);
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
            logger.error("문자열 공백 제거 에러", e);
            return str;
        }
    }

    /**
     * 문자열 대소문자 변환
     */
    public static String toUpperCase(String str) {
        try {
            if (str == null) {
                return null;
            }
            return str.toUpperCase();
        } catch (Exception e) {
            logger.error("대문자 변환 에러", e);
            return str;
        }
    }

    /**
     * 문자열 대소문자 변환
     */
    public static String toLowerCase(String str) {
        try {
            if (str == null) {
                return null;
            }
            return str.toLowerCase();
        } catch (Exception e) {
            logger.error("소문자 변환 에러", e);
            return str;
        }
    }

    /**
     * 문자열 치환
     */
    public static String replace(String str, String target, String replacement) {
        try {
            if (str == null) {
                return null;
            }
            if (target == null) {
                return str;
            }
            if (replacement == null) {
                replacement = "";
            }
            return str.replace(target, replacement);
        } catch (Exception e) {
            logger.error("문자열 치환 에러", e);
            return str;
        }
    }

    /**
     * 문자열 포함 여부 확인
     */
    public static boolean contains(String str, String target) {
        try {
            if (str == null || target == null) {
                return false;
            }
            return str.contains(target);
        } catch (Exception e) {
            logger.error("문자열 포함 여부 확인 에러", e);
            return false;
        }
    }

    /**
     * 문자열 시작 여부 확인
     */
    public static boolean startsWith(String str, String prefix) {
        try {
            if (str == null || prefix == null) {
                return false;
            }
            return str.startsWith(prefix);
        } catch (Exception e) {
            logger.error("문자열 시작 여부 확인 에러", e);
            return false;
        }
    }

    /**
     * 문자열 끝 여부 확인
     */
    public static boolean endsWith(String str, String suffix) {
        try {
            if (str == null || suffix == null) {
                return false;
            }
            return str.endsWith(suffix);
        } catch (Exception e) {
            logger.error("문자열 끝 여부 확인 에러", e);
            return false;
        }
    }

    /**
     * 문자열 분할
     */
    public static String[] split(String str, String delimiter) {
        try {
            if (str == null) {
                return new String[0];
            }
            if (delimiter == null) {
                return new String[] { str };
            }
            return str.split(delimiter);
        } catch (Exception e) {
            logger.error("문자열 분할 에러", e);
            return new String[] { str };
        }
    }

    /**
     * 문자열 결합
     */
    public static String join(String[] array, String delimiter) {
        try {
            if (array == null || array.length == 0) {
                return "";
            }
            if (delimiter == null) {
                delimiter = "";
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                if (i > 0) {
                    sb.append(delimiter);
                }
                sb.append(array[i]);
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("문자열 결합 에러", e);
            return "";
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

    /**
     * 리스트가 비어있는지 체크
     */
    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 맵이 비어있는지 체크
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 배열이 비어있는지 체크
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }
}