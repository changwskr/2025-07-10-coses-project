package com.skcc.oversea.framework.transfer;

import java.io.Serializable;

/**
 * CosesCommonDTO - Common DTO for SKCC Oversea
 * 
 * Base DTO class for common data transfer operations
 * in the Spring Boot migration.
 */
public class CosesCommonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String requestId;
    private String userId;
    private String sessionId;
    private String timestamp;
    private Object data;
    private String bankCode;

    public CosesCommonDTO() {
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }

    public CosesCommonDTO(String requestId, String userId) {
        this();
        this.requestId = requestId;
        this.userId = userId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}