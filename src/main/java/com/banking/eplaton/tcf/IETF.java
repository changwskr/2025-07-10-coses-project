package com.banking.eplaton.tcf;

import com.banking.eplaton.transfer.EPlatonEvent;

/**
 * ETF (End Transaction Framework) Interface
 * 
 * Defines the contract for end transaction framework operations.
 */
public interface IETF {

    /**
     * Execute end transaction framework
     * 
     * @param event EPlaton event to process
     * @return processed EPlaton event
     */
    EPlatonEvent execute(EPlatonEvent event);

    /**
     * Commit transaction
     * 
     * @param event EPlaton event to commit
     */
    void commit(EPlatonEvent event);

    /**
     * Rollback transaction
     * 
     * @param event EPlaton event to rollback
     */
    void rollback(EPlatonEvent event);

    /**
     * Finalize transaction
     * 
     * @param event EPlaton event to finalize
     */
    void finalize(EPlatonEvent event);

    /**
     * Log transaction result
     * 
     * @param event EPlaton event to log
     */
    void logResult(EPlatonEvent event);
}