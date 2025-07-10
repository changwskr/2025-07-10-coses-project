package com.banking.coses.framework.constants;

/**
 * General constants for the COSES Framework
 * 
 * Provides common constants used throughout the framework.
 */
public class Constants {

    // System Properties
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String PATH_SEPARATOR = System.getProperty("path.separator");

    // Encoding and Locale
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String DEFAULT_LOCALE = "ko_KR";

    // Status Constants
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String ERROR = "ERROR";
    public static final String PENDING = "PENDING";
    public static final String PROCESSING = "PROCESSING";
    public static final String COMPLETED = "COMPLETED";
    public static final String CANCELLED = "CANCELLED";

    // Yes/No Constants
    public static final String YES = "Y";
    public static final String NO = "N";

    // Boolean Constants
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    // Empty Constants
    public static final String BLANK = "";
    public static final String NULL_STRING = "null";

    // Timeout Constants
    public static final int DEFAULT_TIMEOUT = 30000; // 30 seconds
    public static final int SHORT_TIMEOUT = 5000; // 5 seconds
    public static final int LONG_TIMEOUT = 60000; // 60 seconds

    // Priority Constants
    public static final String PRIORITY_HIGH = "HIGH";
    public static final String PRIORITY_NORMAL = "NORMAL";
    public static final String PRIORITY_LOW = "LOW";

    // Version Constants
    public static final String DEFAULT_VERSION = "1.0";
    public static final String FRAMEWORK_VERSION = "1.0.0";

    // Date Format Constants
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String DATETIME_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss";

    // File Extension Constants
    public static final String EXT_XML = ".xml";
    public static final String EXT_JSON = ".json";
    public static final String EXT_PROPERTIES = ".properties";
    public static final String EXT_LOG = ".log";

    // HTTP Constants
    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";
    public static final String HTTP_PUT = "PUT";
    public static final String HTTP_DELETE = "DELETE";

    // Content Type Constants
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_XML = "application/xml";
    public static final String CONTENT_TYPE_TEXT = "text/plain";
    public static final String CONTENT_TYPE_HTML = "text/html";

    // Character Constants
    public static final String COMMA = ",";
    public static final String SEMICOLON = ";";
    public static final String COLON = ":";
    public static final String EQUALS = "=";
    public static final String PIPE = "|";
    public static final String UNDERSCORE = "_";
    public static final String HYPHEN = "-";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String DOT = ".";
    public static final String SPACE = " ";
    public static final String TAB = "\t";
    public static final String NEWLINE = "\n";
    public static final String CARRIAGE_RETURN = "\r";

    // Array Constants
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    // Numeric Constants
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int MINUS_ONE = -1;
    public static final long ZERO_LONG = 0L;
    public static final long ONE_LONG = 1L;
    public static final long MINUS_ONE_LONG = -1L;

    // Cache Constants
    public static final int DEFAULT_CACHE_SIZE = 1000;
    public static final int DEFAULT_CACHE_TTL = 3600; // 1 hour in seconds

    // Thread Pool Constants
    public static final int DEFAULT_CORE_POOL_SIZE = 5;
    public static final int DEFAULT_MAX_POOL_SIZE = 20;
    public static final int DEFAULT_QUEUE_CAPACITY = 100;

    // Batch Constants
    public static final int DEFAULT_BATCH_SIZE = 100;
    public static final int MAX_BATCH_SIZE = 1000;

    // Validation Constants
    public static final int MAX_STRING_LENGTH = 1000;
    public static final int MAX_ID_LENGTH = 50;
    public static final int MAX_DESCRIPTION_LENGTH = 500;

    private Constants() {
        // Utility class - prevent instantiation
    }
}