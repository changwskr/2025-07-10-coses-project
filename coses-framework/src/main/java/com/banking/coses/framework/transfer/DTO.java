package com.banking.coses.framework.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * Base abstract class for all Data Transfer Objects in the COSES Framework
 * 
 * Provides common functionality for DTO objects including ID management,
 * timestamps, audit fields, and validation.
 */
public abstract class DTO implements IDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("version")
    private String version = "1.0";

    @JsonProperty("creationTimestamp")
    private long creationTimestamp;

    @JsonProperty("modificationTimestamp")
    private long modificationTimestamp;

    @JsonProperty("creationDateTime")
    private LocalDateTime creationDateTime;

    @JsonProperty("modificationDateTime")
    private LocalDateTime modificationDateTime;

    @JsonProperty("createdBy")
    private String createdBy;

    @JsonProperty("modifiedBy")
    private String modifiedBy;

    @JsonProperty("status")
    private String status = "ACTIVE";

    public DTO() {
        this.id = UUID.randomUUID().toString();
        this.creationTimestamp = System.currentTimeMillis();
        this.modificationTimestamp = this.creationTimestamp;
        this.creationDateTime = LocalDateTime.now(ZoneId.systemDefault());
        this.modificationDateTime = this.creationDateTime;
    }

    public DTO(String id) {
        this.id = id;
        this.creationTimestamp = System.currentTimeMillis();
        this.modificationTimestamp = this.creationTimestamp;
        this.creationDateTime = LocalDateTime.now(ZoneId.systemDefault());
        this.modificationDateTime = this.creationDateTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    @Override
    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
        this.creationDateTime = LocalDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(creationTimestamp),
                ZoneId.systemDefault());
    }

    @Override
    public long getModificationTimestamp() {
        return modificationTimestamp;
    }

    @Override
    public void setModificationTimestamp(long modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
        this.modificationDateTime = LocalDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(modificationTimestamp),
                ZoneId.systemDefault());
    }

    @Override
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    @Override
    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
        this.creationTimestamp = creationDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Override
    public LocalDateTime getModificationDateTime() {
        return modificationDateTime;
    }

    @Override
    public void setModificationDateTime(LocalDateTime modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
        this.modificationTimestamp = modificationDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String getModifiedBy() {
        return modifiedBy;
    }

    @Override
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Update the modification timestamp to current time
     */
    protected void updateModificationTimestamp() {
        this.modificationTimestamp = System.currentTimeMillis();
        this.modificationDateTime = LocalDateTime.now(ZoneId.systemDefault());
    }

    @Override
    public boolean isValid() {
        return id != null && !id.trim().isEmpty();
    }

    @Override
    public void validate() {
        if (!isValid()) {
            throw new IllegalArgumentException("DTO is not valid: ID is required");
        }
    }

    @Override
    public IDTO clone() {
        try {
            return (IDTO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Failed to clone DTO", e);
        }
    }

    @Override
    public void copyFrom(IDTO source) {
        if (source != null) {
            this.id = source.getId();
            this.version = source.getVersion();
            this.creationTimestamp = source.getCreationTimestamp();
            this.modificationTimestamp = source.getModificationTimestamp();
            this.creationDateTime = source.getCreationDateTime();
            this.modificationDateTime = source.getModificationDateTime();
            this.createdBy = source.getCreatedBy();
            this.modifiedBy = source.getModifiedBy();
            this.status = source.getStatus();
        }
    }

    @Override
    public void clear() {
        this.id = UUID.randomUUID().toString();
        this.version = "1.0";
        this.creationTimestamp = System.currentTimeMillis();
        this.modificationTimestamp = this.creationTimestamp;
        this.creationDateTime = LocalDateTime.now(ZoneId.systemDefault());
        this.modificationDateTime = this.creationDateTime;
        this.createdBy = null;
        this.modifiedBy = null;
        this.status = "ACTIVE";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        DTO dto = (DTO) obj;
        return id != null ? id.equals(dto.id) : dto.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DTO{" +
                "id='" + id + '\'' +
                ", version='" + version + '\'' +
                ", status='" + status + '\'' +
                ", creationDateTime=" + creationDateTime +
                ", modificationDateTime=" + modificationDateTime +
                '}';
    }
}