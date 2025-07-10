package com.banking.eplaton.tcf;

import com.banking.eplaton.transfer.EPlatonEvent;

/**
 * STF (Service Transaction Framework) Interface
 * 
 * Defines the contract for service transaction framework operations.
 */
public interface ISTF {

    /**
     * Execute service transaction framework
     * 
     * @param event EPlaton event to process
     * @return processed EPlaton event
     */
    EPlatonEvent execute(EPlatonEvent event);

    /**
     * Get STF transaction info
     * 
     * @return STF transaction information
     */
    Object getSTF_SPtxinfo();

    /**
     * Validate service transaction
     * 
     * @param event EPlaton event to validate
     * @return true if valid, false otherwise
     */
    boolean validate(EPlatonEvent event);

    /**
     * Pre-process service transaction
     * 
     * @param event EPlaton event to pre-process
     */
    void preProcess(EPlatonEvent event);

    /**
     * Post-process service transaction
     * 
     * @param event EPlaton event to post-process
     */
    void postProcess(EPlatonEvent event);
}