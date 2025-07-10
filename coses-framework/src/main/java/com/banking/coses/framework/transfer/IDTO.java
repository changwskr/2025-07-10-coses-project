package com.banking.coses.framework.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base interface for all Data Transfer Objects in the COSES Framework
 * 
 * Defines the contract for DTO objects that are used for data transfer
 * between different layers of the application.
 */
public interface IDTO extends Serializable {

    /**
     * Get the ID
     * 
     * @return ID
     */
    String getId();

    /**
     * Set the ID
     * 
     * @param id ID
     */
    void setId(String id);

    /**
     * Get the version
     * 
     * @return version
     */
    String getVersion();

    /**
     * Set the version
     * 
     * @param version version
     */
    void setVersion(String version);

    /**
     * Get the creation timestamp
     * 
     * @return creation timestamp
     */
    @JsonIgnore
    long getCreationTimestamp();

    /**
     * Set the creation timestamp
     * 
     * @param creationTimestamp creation timestamp
     */
    void setCreationTimestamp(long creationTimestamp);

    /**
     * Get the modification timestamp
     * 
     * @return modification timestamp
     */
    @JsonIgnore
    long getModificationTimestamp();

    /**
     * Set the modification timestamp
     * 
     * @param modificationTimestamp modification timestamp
     */
    void setModificationTimestamp(long modificationTimestamp);

    /**
     * Get the creation date time
     * 
     * @return creation date time
     */
    LocalDateTime getCreationDateTime();

    /**
     * Set the creation date time
     * 
     * @param creationDateTime creation date time
     */
    void setCreationDateTime(LocalDateTime creationDateTime);

    /**
     * Get the modification date time
     * 
     * @return modification date time
     */
    LocalDateTime getModificationDateTime();

    /**
     * Set the modification date time
     * 
     * @param modificationDateTime modification date time
     */
    void setModificationDateTime(LocalDateTime modificationDateTime);

    /**
     * Get the created by
     * 
     * @return created by
     */
    String getCreatedBy();

    /**
     * Set the created by
     * 
     * @param createdBy created by
     */
    void setCreatedBy(String createdBy);

    /**
     * Get the modified by
     * 
     * @return modified by
     */
    String getModifiedBy();

    /**
     * Set the modified by
     * 
     * @param modifiedBy modified by
     */
    void setModifiedBy(String modifiedBy);

    /**
     * Get the status
     * 
     * @return status
     */
    String getStatus();

    /**
     * Set the status
     * 
     * @param status status
     */
    void setStatus(String status);

    /**
     * Check if DTO is valid
     * 
     * @return true if valid, false otherwise
     */
    @JsonIgnore
    boolean isValid();

    /**
     * Validate DTO
     * 
     * @throws IllegalArgumentException if DTO is invalid
     */
    @JsonIgnore
    void validate();

    /**
     * Clone DTO
     * 
     * @return cloned DTO
     */
    @JsonIgnore
    IDTO clone();

    /**
     * Copy from another DTO
     * 
     * @param source source DTO
     */
    void copyFrom(IDTO source);

    /**
     * Clear DTO
     */
    void clear();
}