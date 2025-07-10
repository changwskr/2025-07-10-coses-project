package com.ims.oversea.framework.transaction.constant;

/**
 * TCF (Transaction Control Framework) Error Codes
 */
public class TCFConstantErrcode {
  public static final String ERR_SUCCESS = "0000";
  public static final String ERR_SYSTEM = "9999";
  public static final String ERR_VALIDATION = "1001";
  public static final String ERR_DATABASE = "2001";
  public static final String ERR_NETWORK = "3001";
  public static final String ERR_UNKNOWN = "9998";
  public static final String ERR_TIMEOUT = "9001";
  public static final String ERR_PERMISSION = "8001";
  public static final String ERR_DUPLICATE = "7001";
  public static final String ERR_NOT_FOUND = "6001";
  public static final String ERR_INVALID_INPUT = "5001";
  public static final String ERR_SERVICE_UNAVAILABLE = "4001";
  public static final String ERR_UNAUTHORIZED = "3002";
  public static final String ERR_CONFLICT = "2002";
  public static final String ERR_INTERNAL = "1002";

  private TCFConstantErrcode() {
    // Prevent instantiation
  }
}