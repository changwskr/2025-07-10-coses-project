package com.banking.kdb.oversea.teller.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Teller Session DTO for KDB Oversea
 * 
 * Data transfer object for teller session information.
 */
public class TellerSessionDTO {

    private Long id;

    @JsonProperty("sessionId")
    @NotBlank(message = "Session ID is required")
    @Size(max = 20, message = "Session ID cannot exceed 20 characters")
    private String sessionId;

    @JsonProperty("tellerId")
    @NotBlank(message = "Teller ID is required")
    @Size(max = 20, message = "Teller ID cannot exceed 20 characters")
    private String tellerId;

    @JsonProperty("branchCode")
    @NotBlank(message = "Branch code is required")
    @Size(max = 10, message = "Branch code cannot exceed 10 characters")
    private String branchCode;

    @JsonProperty("status")
    @NotBlank(message = "Status is required")
    @Size(max = 20, message = "Status cannot exceed 20 characters")
    private String status;

    @JsonProperty("startTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonProperty("endTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @JsonProperty("createdBy")
    @Size(max = 50, message = "Created by cannot exceed 50 characters")
    private String createdBy;

    @JsonProperty("createdDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    @JsonProperty("modifiedBy")
    @Size(max = 50, message = "Modified by cannot exceed 50 characters")
    private String modifiedBy;

    @JsonProperty("modifiedDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDateTime;

    public TellerSessionDTO() {
        this.status = "ACTIVE";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTellerId() {
        return tellerId;
    }

    public void setTellerId(String tellerId) {
        this.tellerId = tellerId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(LocalDateTime modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    @Override
    public String toString() {
        return "TellerSessionDTO{" +
                "id=" + id +
                ", sessionId='" + sessionId + '\'' +
                ", tellerId='" + tellerId + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", status='" + status + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}