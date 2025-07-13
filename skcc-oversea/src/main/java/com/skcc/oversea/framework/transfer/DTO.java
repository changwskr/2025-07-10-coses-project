package com.skcc.oversea.framework.transfer;

import java.io.Serializable;

/**
 * Base DTO (Data Transfer Object) class
 * Replaces com.chb.coses.framework.transfer.DTO
 */
public abstract class DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public DTO() {
    }

    /**
     * Clone this DTO
     */
    public abstract DTO clone();

    /**
     * Convert to string representation
     */
    @Override
    public abstract String toString();

    /**
     * Check if this DTO equals another object
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * Get hash code
     */
    @Override
    public abstract int hashCode();
}