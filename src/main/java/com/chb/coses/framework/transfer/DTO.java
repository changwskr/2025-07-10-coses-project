package com.chb.coses.framework.transfer;

import java.io.Serializable;

/**
 * Base abstract class for all Data Transfer Objects in the EPlaton Framework
 */
public abstract class DTO implements IDTO {

    private String id;
    private String version;
    private long creationTimestamp;
    private long modificationTimestamp;

    public DTO() {
        this.creationTimestamp = System.currentTimeMillis();
        this.modificationTimestamp = this.creationTimestamp;
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
    }

    @Override
    public long getModificationTimestamp() {
        return modificationTimestamp;
    }

    @Override
    public void setModificationTimestamp(long modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }

    /**
     * Update the modification timestamp to current time
     */
    protected void updateModificationTimestamp() {
        this.modificationTimestamp = System.currentTimeMillis();
    }
}