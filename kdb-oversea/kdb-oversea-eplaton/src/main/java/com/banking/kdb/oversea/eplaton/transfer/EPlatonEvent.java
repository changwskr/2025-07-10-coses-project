package com.banking.kdb.oversea.eplaton.transfer;

import com.banking.coses.framework.transfer.CosesEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * EPlaton Event for KDB Oversea
 * 
 * Extends CosesEvent with EPlaton-specific functionality
 * including TPSVCINFODTO and EPlatonCommonDTO.
 */
public class EPlatonEvent extends CosesEvent {

    @JsonProperty("tpsvcInfo")
    @NotNull(message = "TPSVCINFODTO is required")
    @Valid
    private TPSVCINFODTO tpsvcInfo;

    @JsonProperty("request")
    @Valid
    private EPlatonCommonDTO request;

    @JsonProperty("response")
    @Valid
    private EPlatonCommonDTO response;

    public EPlatonEvent() {
        super();
        this.tpsvcInfo = new TPSVCINFODTO();
    }

    public EPlatonEvent(String eventType, String action) {
        super(eventType, action);
        this.tpsvcInfo = new TPSVCINFODTO();
    }

    public EPlatonEvent(String eventId, String eventType, String action) {
        super(eventId, eventType, action);
        this.tpsvcInfo = new TPSVCINFODTO();
    }

    /**
     * Get TPSVCINFODTO
     */
    public TPSVCINFODTO getTPSVCINFODTO() {
        return tpsvcInfo;
    }

    /**
     * Set TPSVCINFODTO
     */
    public void setTPSVCINFODTO(TPSVCINFODTO tpsvcInfo) {
        this.tpsvcInfo = tpsvcInfo;
    }

    /**
     * Get request data
     */
    @Override
    public EPlatonCommonDTO getRequest() {
        return request;
    }

    /**
     * Set request data
     */
    @Override
    public void setRequest(EPlatonCommonDTO request) {
        this.request = request;
    }

    /**
     * Get response data
     */
    @Override
    public EPlatonCommonDTO getResponse() {
        return response;
    }

    /**
     * Set response data
     */
    @Override
    public void setResponse(EPlatonCommonDTO response) {
        this.response = response;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && tpsvcInfo != null && tpsvcInfo.isValid();
    }

    @Override
    public void validate() {
        super.validate();
        if (tpsvcInfo == null) {
            throw new IllegalArgumentException("TPSVCINFODTO cannot be null");
        }
        tpsvcInfo.validate();
    }

    /**
     * Check if event has errors
     */
    public boolean hasErrors() {
        return tpsvcInfo != null && tpsvcInfo.hasErrors();
    }

    /**
     * Get error code
     */
    public String getErrorCode() {
        return tpsvcInfo != null ? tpsvcInfo.getErrorcode() : null;
    }

    /**
     * Get error message
     */
    public String getErrorMessage() {
        return tpsvcInfo != null ? tpsvcInfo.getError_message() : null;
    }

    /**
     * Set error information
     */
    public void setError(String errorCode, String errorMessage) {
        if (tpsvcInfo != null) {
            tpsvcInfo.setErrorcode(errorCode);
            tpsvcInfo.setError_message(errorMessage);
        }
    }

    /**
     * Set success information
     */
    public void setSuccess() {
        if (tpsvcInfo != null) {
            tpsvcInfo.setErrorcode("I0000");
            tpsvcInfo.setError_message("SUCCESS");
        }
    }

    @Override
    public String toString() {
        return "EPlatonEvent{" +
                "eventId='" + getEventId() + '\'' +
                ", eventType='" + getEventType() + '\'' +
                ", action='" + getAction() + '\'' +
                ", eventStatus='" + getEventStatus() + '\'' +
                ", tpsvcInfo=" + tpsvcInfo +
                ", request=" + (request != null ? request.getClass().getSimpleName() : "null") +
                ", response=" + (response != null ? response.getClass().getSimpleName() : "null") +
                '}';
    }
}