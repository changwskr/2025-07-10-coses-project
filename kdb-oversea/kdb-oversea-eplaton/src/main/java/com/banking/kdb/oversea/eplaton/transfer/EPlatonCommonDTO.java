package com.banking.kdb.oversea.eplaton.transfer;

import com.banking.coses.framework.transfer.DTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Common DTO for EPlaton Framework
 * 
 * Base class for all EPlaton data transfer objects
 * with common fields and validation.
 */
public class EPlatonCommonDTO extends DTO {

    @JsonProperty("requestName")
    @NotBlank(message = "Request name is required")
    @Size(max = 100, message = "Request name cannot exceed 100 characters")
    private String requestName;

    @JsonProperty("responseName")
    @Size(max = 100, message = "Response name cannot exceed 100 characters")
    private String responseName;

    @JsonProperty("userId")
    @NotBlank(message = "User ID is required")
    @Size(max = 50, message = "User ID cannot exceed 50 characters")
    private String userId;

    @JsonProperty("sessionId")
    @Size(max = 100, message = "Session ID cannot exceed 100 characters")
    private String sessionId;

    @JsonProperty("branchCode")
    @Size(max = 10, message = "Branch code cannot exceed 10 characters")
    private String branchCode;

    @JsonProperty("tellerId")
    @Size(max = 20, message = "Teller ID cannot exceed 20 characters")
    private String tellerId;

    @JsonProperty("terminalId")
    @Size(max = 20, message = "Terminal ID cannot exceed 20 characters")
    private String terminalId;

    @JsonProperty("channelType")
    @Size(max = 10, message = "Channel type cannot exceed 10 characters")
    private String channelType;

    @JsonProperty("language")
    @Size(max = 5, message = "Language cannot exceed 5 characters")
    private String language = "ko";

    @JsonProperty("currency")
    @Size(max = 3, message = "Currency cannot exceed 3 characters")
    private String currency = "KRW";

    @JsonProperty("timezone")
    @Size(max = 20, message = "Timezone cannot exceed 20 characters")
    private String timezone = "Asia/Seoul";

    @JsonProperty("version")
    @Size(max = 10, message = "Version cannot exceed 10 characters")
    private String version = "1.0";

    @JsonProperty("description")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    public EPlatonCommonDTO() {
        super();
    }

    public EPlatonCommonDTO(String requestName, String userId) {
        super();
        this.requestName = requestName;
        this.userId = userId;
    }

    // Getters and Setters
    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getResponseName() {
        return responseName;
    }

    public void setResponseName(String responseName) {
        this.responseName = responseName;
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

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getTellerId() {
        return tellerId;
    }

    public void setTellerId(String tellerId) {
        this.tellerId = tellerId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean isValid() {
        return super.isValid() &&
                requestName != null && !requestName.trim().isEmpty() &&
                userId != null && !userId.trim().isEmpty();
    }

    @Override
    public void validate() {
        super.validate();
        if (requestName == null || requestName.trim().isEmpty()) {
            throw new IllegalArgumentException("Request name is required");
        }
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }
    }

    /**
     * Copy common fields from another DTO
     */
    public void copyCommonFields(EPlatonCommonDTO source) {
        if (source != null) {
            this.userId = source.getUserId();
            this.sessionId = source.getSessionId();
            this.branchCode = source.getBranchCode();
            this.tellerId = source.getTellerId();
            this.terminalId = source.getTerminalId();
            this.channelType = source.getChannelType();
            this.language = source.getLanguage();
            this.currency = source.getCurrency();
            this.timezone = source.getTimezone();
            this.version = source.getVersion();
        }
    }

    /**
     * Set response name based on request name
     */
    public void setResponseNameFromRequest() {
        if (requestName != null && !requestName.trim().isEmpty()) {
            this.responseName = requestName.replace("Request", "Response");
        }
    }

    @Override
    public String toString() {
        return "EPlatonCommonDTO{" +
                "id='" + getId() + '\'' +
                ", requestName='" + requestName + '\'' +
                ", responseName='" + responseName + '\'' +
                ", userId='" + userId + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", tellerId='" + tellerId + '\'' +
                ", channelType='" + channelType + '\'' +
                ", language='" + language + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}