package com.chb.coses.framework.transfer;

import java.io.Serializable;

/**
 * Base interface for all Data Transfer Objects in the EPlaton Framework
 */
public interface IDTO extends Serializable {

    /**
     * Get the DTO ID
     * 
     * @return DTO ID
     */
    String getId();

    /**
     * Set the DTO ID
     * 
     * @param id DTO ID
     */
    void setId(String id);

    /**
     * Get the DTO version
     * 
     * @return DTO version
     */
    String getVersion();

    /**
     * Set the DTO version
     * 
     * @param version DTO version
     */
    void setVersion(String version);

    /**
     * Get the DTO creation timestamp
     * 
     * @return creation timestamp
     */
    long getCreationTimestamp();

    /**
     * Set the DTO creation timestamp
     * 
     * @param creationTimestamp creation timestamp
     */
    void setCreationTimestamp(long creationTimestamp);

    /**
     * Get the DTO modification timestamp
     * 
     * @return modification timestamp
     */
    long getModificationTimestamp();

    /**
     * Set the DTO modification timestamp
     * 
     * @param modificationTimestamp modification timestamp
     */
    void setModificationTimestamp(long modificationTimestamp);
}