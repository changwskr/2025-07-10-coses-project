package com.chb.coses.framework.constants;

/**
 * General constants for the EPlaton Framework
 */
public class Constants {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");

    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String DEFAULT_LOCALE = "ko_KR";

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String ERROR = "ERROR";

    public static final String YES = "Y";
    public static final String NO = "N";

    public static final String TRUE = "true";
    public static final String FALSE = "false";

    public static final String BLANK = "";

    public static final int DEFAULT_TIMEOUT = 30000; // 30 seconds

    private Constants() {
        // Utility class - prevent instantiation
    }
}