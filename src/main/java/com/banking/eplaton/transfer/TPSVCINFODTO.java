package com.banking.eplaton.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * TPSVCINFO DTO
 * 
 * Contains TPC service information for EPlaton events.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TPSVCINFODTO {

    private String systemName;
    private String actionName;
    private String operationName;
    private String cdtoName;
    private String tpfq;
    private String errorCode;
    private String errorMessage;
    private String txTimer;
    private String requestData;
    private String responseData;
    private String status;

    public TPSVCINFODTO() {
        this.errorCode = "IZZ000";
        this.status = "PENDING";
    }

    // Getters and Setters
    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getCdtoName() {
        return cdtoName;
    }

    public void setCdtoName(String cdtoName) {
        this.cdtoName = cdtoName;
    }

    public String getTpfq() {
        return tpfq;
    }

    public void setTpfq(String tpfq) {
        this.tpfq = tpfq;
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

    public String getTxTimer() {
        return txTimer;
    }

    public void setTxTimer(String txTimer) {
        this.txTimer = txTimer;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Helper methods
    public boolean hasError() {
        return errorCode != null && !"IZZ000".equals(errorCode);
    }

    public void setError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = "ERROR";
    }

    public void clearError() {
        this.errorCode = "IZZ000";
        this.errorMessage = null;
        this.status = "SUCCESS";
    }

    @Override
    public String toString() {
        return "TPSVCINFODTO{" +
                "systemName='" + systemName + '\'' +
                ", actionName='" + actionName + '\'' +
                ", operationName='" + operationName + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}