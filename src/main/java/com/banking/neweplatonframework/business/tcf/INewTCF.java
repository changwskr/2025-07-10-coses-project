package com.banking.neweplatonframework.business.tcf;

import com.banking.neweplatonframework.transfer.NewEPlatonEvent;

/**
 * New TCF (Transaction Control Framework) Interface
 * 
 * Defines the contract for transaction control framework operations in New
 * EPlaton.
 */
public interface INewTCF {

    /**
     * Execute transaction control framework
     * 
     * @param event New EPlaton event to process
     * @return processed New EPlaton event
     */
    NewEPlatonEvent execute(NewEPlatonEvent event);

    /**
     * Get the current New EPlaton event
     * 
     * @return current New EPlaton event
     */
    NewEPlatonEvent getNewEPlatonEvent();

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