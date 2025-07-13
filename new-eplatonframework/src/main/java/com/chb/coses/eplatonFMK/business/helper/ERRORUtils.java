package com.chb.coses.eplatonFMK.business.helper;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * ERROR Utils
 * Spring 기반으로 전환된 에러 유틸리티 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
@Component
public class ERRORUtils {

    private static final Logger logger = LoggerFactory.getLogger(ERRORUtils.class);

    /**
     * 에러 코드 정의
     */
    public static final String ERROR_CODE_SUCCESS = "0000";
    public static final String ERROR_CODE_SYSTEM_ERROR = "9999";
    public static final String ERROR_CODE_INVALID_PARAMETER = "1001";
    public static final String ERROR_CODE_DATABASE_ERROR = "2001";
    public static final String ERROR_CODE_BUSINESS_ERROR = "3001";

    /**
     * 에러 메시지 정의
     */
    public static final String ERROR_MSG_SUCCESS = "정상 처리되었습니다.";
    public static final String ERROR_MSG_SYSTEM_ERROR = "시스템 오류가 발생했습니다.";
    public static final String ERROR_MSG_INVALID_PARAMETER = "잘못된 파라미터입니다.";
    public static final String ERROR_MSG_DATABASE_ERROR = "데이터베이스 오류가 발생했습니다.";
    public static final String ERROR_MSG_BUSINESS_ERROR = "비즈니스 오류가 발생했습니다.";

    /**
     * 에러 정보 클래스
     */
    public static class ErrorInfo {
        private String errorCode;
        private String errorMessage;
        private String errorDetail;

        public ErrorInfo(String errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        public ErrorInfo(String errorCode, String errorMessage, String errorDetail) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
            this.errorDetail = errorDetail;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorDetail() {
            return errorDetail;
        }

        public void setErrorDetail(String errorDetail) {
            this.errorDetail = errorDetail;
        }
    }

    /**
     * 성공 에러 정보 생성
     */
    public static ErrorInfo createSuccessError() {
        return new ErrorInfo(ERROR_CODE_SUCCESS, ERROR_MSG_SUCCESS);
    }

    /**
     * 시스템 에러 정보 생성
     */
    public static ErrorInfo createSystemError() {
        return new ErrorInfo(ERROR_CODE_SYSTEM_ERROR, ERROR_MSG_SYSTEM_ERROR);
    }

    /**
     * 시스템 에러 정보 생성 (상세 메시지 포함)
     */
    public static ErrorInfo createSystemError(String detail) {
        return new ErrorInfo(ERROR_CODE_SYSTEM_ERROR, ERROR_MSG_SYSTEM_ERROR, detail);
    }

    /**
     * 파라미터 에러 정보 생성
     */
    public static ErrorInfo createParameterError() {
        return new ErrorInfo(ERROR_CODE_INVALID_PARAMETER, ERROR_MSG_INVALID_PARAMETER);
    }

    /**
     * 파라미터 에러 정보 생성 (상세 메시지 포함)
     */
    public static ErrorInfo createParameterError(String detail) {
        return new ErrorInfo(ERROR_CODE_INVALID_PARAMETER, ERROR_MSG_INVALID_PARAMETER, detail);
    }

    /**
     * 데이터베이스 에러 정보 생성
     */
    public static ErrorInfo createDatabaseError() {
        return new ErrorInfo(ERROR_CODE_DATABASE_ERROR, ERROR_MSG_DATABASE_ERROR);
    }

    /**
     * 데이터베이스 에러 정보 생성 (상세 메시지 포함)
     */
    public static ErrorInfo createDatabaseError(String detail) {
        return new ErrorInfo(ERROR_CODE_DATABASE_ERROR, ERROR_MSG_DATABASE_ERROR, detail);
    }

    /**
     * 비즈니스 에러 정보 생성
     */
    public static ErrorInfo createBusinessError() {
        return new ErrorInfo(ERROR_CODE_BUSINESS_ERROR, ERROR_MSG_BUSINESS_ERROR);
    }

    /**
     * 비즈니스 에러 정보 생성 (상세 메시지 포함)
     */
    public static ErrorInfo createBusinessError(String detail) {
        return new ErrorInfo(ERROR_CODE_BUSINESS_ERROR, ERROR_MSG_BUSINESS_ERROR, detail);
    }

    /**
     * 커스텀 에러 정보 생성
     */
    public static ErrorInfo createCustomError(String errorCode, String errorMessage) {
        return new ErrorInfo(errorCode, errorMessage);
    }

    /**
     * 커스텀 에러 정보 생성 (상세 메시지 포함)
     */
    public static ErrorInfo createCustomError(String errorCode, String errorMessage, String errorDetail) {
        return new ErrorInfo(errorCode, errorMessage, errorDetail);
    }

    /**
     * 에러 로그 기록
     */
    public static void logError(ErrorInfo errorInfo) {
        logger.error("Error Code: {}, Error Message: {}, Error Detail: {}",
                errorInfo.getErrorCode(), errorInfo.getErrorMessage(), errorInfo.getErrorDetail());
    }

    /**
     * 에러 로그 기록 (예외 포함)
     */
    public static void logError(ErrorInfo errorInfo, Exception e) {
        logger.error("Error Code: {}, Error Message: {}, Error Detail: {}",
                errorInfo.getErrorCode(), errorInfo.getErrorMessage(), errorInfo.getErrorDetail(), e);
    }

    /**
     * 성공 여부 확인
     */
    public static boolean isSuccess(String errorCode) {
        return ERROR_CODE_SUCCESS.equals(errorCode);
    }

    /**
     * 에러 여부 확인
     */
    public static boolean isError(String errorCode) {
        return !isSuccess(errorCode);
    }
}