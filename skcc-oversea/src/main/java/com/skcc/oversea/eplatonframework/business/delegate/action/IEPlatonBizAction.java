package com.skcc.oversea.eplatonframework.business.delegate.action;

import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;

/**
 * EPlaton Business Action Interface for SKCC Oversea
 * 
 * Defines the contract for EPlaton business actions
 * using Spring Boot and modern Java patterns.
 */
public interface IEPlatonBizAction {

  /**
   * Pre-act processing
   * Called before the main business logic execution
   */
  void preAct(EPlatonEvent event);

  /**
   * Execute business action
   * Main business logic execution method
   */
  EPlatonEvent act(EPlatonEvent event);

  /**
   * Post-act processing
   * Called after the main business logic execution
   */
  void postAct(EPlatonEvent event);
}
