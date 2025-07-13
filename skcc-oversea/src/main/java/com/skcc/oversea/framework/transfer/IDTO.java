package com.skcc.oversea.framework.transfer;

import java.io.Serializable;

/**
 * Interface for Data Transfer Objects
 * Replaces com.chb.coses.framework.transfer.IDTO
 */
public interface IDTO extends Serializable {

    /**
     * Clone this DTO
     */
    IDTO clone();

    /**
     * Convert to string representation
     */
    String toString();

    /**
     * Check if this DTO equals another object
     */
    boolean equals(Object obj);

    /**
     * Get hash code
     */
    int hashCode();
}