package com.banking.kdb.oversea.eplaton.transfer;

import com.banking.coses.framework.transfer.DTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * TPSVCINFO DTO for EPlaton Framework
 * 
 * Contains service information and transaction details
 * for EPlaton business operations.
 */
public class TPSVCINFODTO extends DTO {

    @JsonProperty("actionName")
    @NotBlank(message = "Action name is required")
    @Size(max = 200, message = "Action name cannot exceed 200 characters")
    private String action_name;

    @JsonProperty("requestName")
    @NotBlank(message = "Request name is required")
    @Size(max = 100, message = "Request name cannot exceed 100 characters")
    private String request_name;

    @JsonProperty("responseName")
    @Size(max = 100, message = "Response name cannot exceed 100 characters")
    private String response_name;

    @JsonProperty("userId")
    @NotBlank(message = "User ID is required")
    @Size(max = 50, message = "User ID cannot exceed 50 characters")
    private String user_id;

    @JsonProperty("sessionId")
    @Size(max = 100, message = "Session ID cannot exceed 100 characters")
    private String session_id;

    @JsonProperty("transactionId")
    @Size(max = 50, message = "Transaction ID cannot exceed 50 characters")
    private String transaction_id;

    @JsonProperty("branchCode")
    @Size(max = 10, message = "Branch code cannot exceed 10 characters")
    private String branch_code;

    @JsonProperty("tellerId")
    @Size(max = 20, message = "Teller ID cannot exceed 20 characters")
    private String teller_id;

    @JsonProperty("terminalId")
    @Size(max = 20, message = "Terminal ID cannot exceed 20 characters")
    private String terminal_id;

    @JsonProperty("channelType")
    @Size(max = 10, message = "Channel type cannot exceed 10 characters")
    private String channel_type;

    @JsonProperty("errorCode")
    @Size(max = 10, message = "Error code cannot exceed 10 characters")
    private String errorcode = "I0000";

    @JsonProperty("errorMessage")
    @Size(max = 500, message = "Error message cannot exceed 500 characters")
    private String error_message = "SUCCESS";

    @JsonProperty("hostSeq")
    @Size(max = 10, message = "Host sequence cannot exceed 10 characters")
    private String hostseq;

    @JsonProperty("orgSeq")
    @Size(max = 10, message = "Organization sequence cannot exceed 10 characters")
    private String orgseq;

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

    public TPSVCINFODTO() {
        super();
    }

    public TPSVCINFODTO(String actionName, String requestName, String userId) {
        super();
        this.action_name = actionName;
        this.request_name = requestName;
        this.user_id = userId;
    }

    // Getters and Setters
    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    public String getRequest_name() {
        return request_name;
    }

    public void setRequest_name(String request_name) {
        this.request_name = request_name;
    }

    public String getResponse_name() {
        return response_name;
    }

    public void setResponse_name(String response_name) {
        this.response_name = response_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    public String getTeller_id() {
        return teller_id;
    }

    public void setTeller_id(String teller_id) {
        this.teller_id = teller_id;
    }

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getChannel_type() {
        return channel_type;
    }

    public void setChannel_type(String channel_type) {
        this.channel_type = channel_type;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getHostseq() {
        return hostseq;
    }

    public void setHostseq(String hostseq) {
        this.hostseq = hostseq;
    }

    public String getOrgseq() {
        return orgseq;
    }

    public void setOrgseq(String orgseq) {
        this.orgseq = orgseq;
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
                action_name != null && !action_name.trim().isEmpty() &&
                request_name != null && !request_name.trim().isEmpty() &&
                user_id != null && !user_id.trim().isEmpty();
    }

    @Override
    public void validate() {
        super.validate();
        if (action_name == null || action_name.trim().isEmpty()) {
            throw new IllegalArgumentException("Action name is required");
        }
        if (request_name == null || request_name.trim().isEmpty()) {
            throw new IllegalArgumentException("Request name is required");
        }
        if (user_id == null || user_id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }
    }

    /**
     * Check if there are errors
     */
    public boolean hasErrors() {
        return errorcode != null && !errorcode.equals("I0000");
    }

    /**
     * Set success status
     */
    public void setSuccess() {
        this.errorcode = "I0000";
        this.error_message = "SUCCESS";
    }

    /**
     * Set error status
     */
    public void setError(String errorCode, String errorMessage) {
        this.errorcode = errorCode;
        this.error_message = errorMessage;
    }

    /**
     * Copy service info from another DTO
     */
    public void copyServiceInfo(TPSVCINFODTO source) {
        if (source != null) {
            this.user_id = source.getUser_id();
            this.session_id = source.getSession_id();
            this.branch_code = source.getBranch_code();
            this.teller_id = source.getTeller_id();
            this.terminal_id = source.getTerminal_id();
            this.channel_type = source.getChannel_type();
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
        if (request_name != null && !request_name.trim().isEmpty()) {
            this.response_name = request_name.replace("Request", "Response");
        }
    }

    @Override
    public String toString() {
        return "TPSVCINFODTO{" +
                "id='" + getId() + '\'' +
                ", action_name='" + action_name + '\'' +
                ", request_name='" + request_name + '\'' +
                ", response_name='" + response_name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", errorcode='" + errorcode + '\'' +
                ", error_message='" + error_message + '\'' +
                ", branch_code='" + branch_code + '\'' +
                ", teller_id='" + teller_id + '\'' +
                ", channel_type='" + channel_type + '\'' +
                '}';
    }
}