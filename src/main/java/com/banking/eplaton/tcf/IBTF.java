package com.banking.eplaton.tcf;

import com.banking.eplaton.transfer.EPlatonEvent;

/**
 * BTF (Business Transaction Framework) Interface
 * 
 * Defines the contract for business transaction framework operations.
 */
public interface IBTF {

    /**
     * Execute business transaction framework
     * 
     * @param event EPlaton event to process
     * @return processed EPlaton event
     */
    EPlatonEvent execute(EPlatonEvent event);

    /**
     * Validate business transaction
     * 
     * @param event EPlaton event to validate
     * @return true if valid, false otherwise
     */
    boolean validate(EPlatonEvent event);

    /**
     * Pre-process business transaction
     * 
     * @param event EPlaton event to pre-process
     */
    void preProcess(EPlatonEvent event);

    /**
     * Post-process business transaction
     * 
     * @param event EPlaton event to post-process
     */
    void postProcess(EPlatonEvent event);

    /**
     * Route business transaction
     * 
     * @param event EPlaton event to route
     * @return routed EPlaton event
     */
    EPlatonEvent route(EPlatonEvent event);
}