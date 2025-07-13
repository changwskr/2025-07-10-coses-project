package com.chb.coses.eplatonFMK.business.helper.tpmservice;

/**
 * TPM Service Exception
 * Spring 기반으로 전환된 TPM 서비스 예외 클래스
 * 
 * @author unascribed
 * @version 1.0
 */
public class TPMServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMessage;
    private String systemName;
    private String operationName;

    /**
     * 기본 생성자
     */
    public TPMServiceException() {
        super();
    }

    /**
     * 메시지가 있는 생성자
     */
    public TPMServiceException(String message) {
        super(message);
        this.errorMessage = message;
    }

    /**
     * 원인 예외가 있는 생성자
     */
    public TPMServiceException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
    }

    /**
     * 원인 예외만 있는 생성자
     */
    public TPMServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * 에러 코드와 메시지가 있는 생성자
     */
    public TPMServiceException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * 에러 코드, 메시지, 원인 예외가 있는 생성자
     */
    public TPMServiceException(String errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * 모든 정보가 있는 생성자
     */
    public TPMServiceException(String errorCode, String errorMessage, String systemName, String operationName) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.systemName = systemName;
        this.operationName = operationName;
    }

    /**
     * 모든 정보와 원인 예외가 있는 생성자
     */
    public TPMServiceException(String errorCode, String errorMessage, String systemName, String operationName,
            Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.systemName = systemName;
        this.operationName = operationName;
    }

    /**
     * 에러 코드 가져오기
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 에러 코드 설정
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 에러 메시지 가져오기
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 에러 메시지 설정
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 시스템 이름 가져오기
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * 시스템 이름 설정
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    /**
     * 오퍼레이션 이름 가져오기
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * 오퍼레이션 이름 설정
     */
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    /**
     * 상세 메시지 가져오기
     */
    public String getDetailedMessage() {
        StringBuilder sb = new StringBuilder();

        if (errorCode != null) {
            sb.append("Error Code: ").append(errorCode);
        }

        if (errorMessage != null) {
            if (sb.length() > 0) {
                sb.append(" - ");
            }
            sb.append("Error Message: ").append(errorMessage);
        }

        if (systemName != null) {
            if (sb.length() > 0) {
                sb.append(" - ");
            }
            sb.append("System: ").append(systemName);
        }

        if (operationName != null) {
            if (sb.length() > 0) {
                sb.append(" - ");
            }
            sb.append("Operation: ").append(operationName);
        }

        return sb.toString();
    }

    /**
     * 시스템 에러 예외 생성
     */
    public static TPMServiceException systemError(String message) {
        return new TPMServiceException(TPMServiceConstants.ERROR_CODE_SYSTEM_ERROR, message);
    }

    /**
     * 시스템 에러 예외 생성 (원인 포함)
     */
    public static TPMServiceException systemError(String message, Throwable cause) {
        return new TPMServiceException(TPMServiceConstants.ERROR_CODE_SYSTEM_ERROR, message, cause);
    }

    /**
     * 파라미터 에러 예외 생성
     */
    public static TPMServiceException parameterError(String message) {
        return new TPMServiceException(TPMServiceConstants.ERROR_CODE_INVALID_PARAMETER, message);
    }

    /**
     * 데이터베이스 에러 예외 생성
     */
    public static TPMServiceException databaseError(String message) {
        return new TPMServiceException(TPMServiceConstants.ERROR_CODE_DATABASE_ERROR, message);
    }

    /**
     * 데이터베이스 에러 예외 생성 (원인 포함)
     */
    public static TPMServiceException databaseError(String message, Throwable cause) {
        return new TPMServiceException(TPMServiceConstants.ERROR_CODE_DATABASE_ERROR, message, cause);
    }

    /**
     * 비즈니스 에러 예외 생성
     */
    public static TPMServiceException businessError(String message) {
        return new TPMServiceException(TPMServiceConstants.ERROR_CODE_BUSINESS_ERROR, message);
    }

    /**
     * TPM 에러 예외 생성
     */
    public static TPMServiceException tpmError(String message) {
        return new TPMServiceException(TPMServiceConstants.ERROR_CODE_TPM_ERROR, message);
    }

    /**
     * TPM 에러 예외 생성 (시스템, 오퍼레이션 포함)
     */
    public static TPMServiceException tpmError(String message, String systemName, String operationName) {
        return new TPMServiceException(TPMServiceConstants.ERROR_CODE_TPM_ERROR, message, systemName, operationName);
    }

    /**
     * 세션 에러 예외 생성
     */
    public static TPMServiceException sessionError(String message) {
        return new TPMServiceException(TPMServiceConstants.ERROR_CODE_SESSION_ERROR, message);
    }

    /**
     * 권한 에러 예외 생성
     */
    public static TPMServiceException permissionError(String message) {
        return new TPMServiceException(TPMServiceConstants.ERROR_CODE_PERMISSION_ERROR, message);
    }

    /**
     * 캐시 에러 예외 생성
     */
    public static TPMServiceException cacheError(String message) {
        return new TPMServiceException(TPMServiceConstants.ERROR_CODE_CACHE_ERROR, message);
    }

    /**
     * 감사 에러 예외 생성
     */
    public static TPMServiceException auditError(String message) {
        return new TPMServiceException(TPMServiceConstants.ERROR_CODE_AUDIT_ERROR, message);
    }

    @Override
    public String toString() {
        return "TPMServiceException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", systemName='" + systemName + '\'' +
                ", operationName='" + operationName + '\'' +
                ", cause=" + getCause() +
                '}';
    }
}