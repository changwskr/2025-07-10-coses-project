package com.banking.eplaton.tcf;

import com.banking.eplaton.transfer.EPlatonEvent;

/**
 * TCF (Transaction Control Framework) Interface
 * 
 * Defines the contract for transaction control framework operations.
 */
public interface ITCF {

    /**
     * Execute transaction control framework
     * 
     * @param event EPlaton event to process
     * @return processed EPlaton event
     */
    EPlatonEvent execute(EPlatonEvent event);

    /**
     * Get the current EPlaton event
     * 
     * @return current EPlaton event
     */
    EPlatonEvent getEPlatonEvent();

    /**
     * Check if TCF is ready for execution
     * 
     * @return true if ready, false otherwise
     */
    boolean isReady();

    /**
     * Initialize TCF components
     */
    void initialize();

    /**
     * Cleanup TCF resources
     */
    void cleanup();
}