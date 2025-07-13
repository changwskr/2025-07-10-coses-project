package com.skcc.oversea.eplatonframework.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationContext;
import com.skcc.oversea.foundation.logej.LOGEJ;
import com.skcc.oversea.foundation.constant.Constants;
import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;
import com.skcc.oversea.eplatonframework.business.delegate.action.EPlatonBizAction;
import com.skcc.oversea.framework.transaction.tcf.TCF;

/**
 * EPlaton Business Delegate Service Bean for SKCC Oversea
 * 
 * Provides business delegate functionality for the EPlaton framework
 * using Spring Boot dependency injection and transaction management.
 */
@Service
public class EPlatonBizDelegateSBBean {

  private static final Logger logger = LoggerFactory.getLogger(EPlatonBizDelegateSBBean.class);

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private TCF tcf;

  /**
   * Execute business delegate operation
   */
  public EPlatonEvent execute(EPlatonEvent event) {
    long startTime = System.currentTimeMillis();
    EPlatonEvent resultEvent = null;

    try {
      logger.info("Starting EPlaton business delegate execution for action: {}",
          event.getTPSVCINFODTO().getAction_name());

      // Validate event
      if (!isValidEvent(event)) {
        event.getTPSVCINFODTO().setErrorcode("EVAL001");
        event.getTPSVCINFODTO().setError_message("Invalid event data");
        LOGEJ.logError("EVAL001", "Invalid event data", "EPlatonBizDelegate", null);
        return event;
      }

      // Process event using TCF
      String transactionId = generateTransactionId();
      String transactionType = event.getTPSVCINFODTO().getAction_name();

      Object result = tcf.execute(transactionId, transactionType, event);

      if (result instanceof EPlatonEvent) {
        resultEvent = (EPlatonEvent) result;
      } else {
        // Convert result to EPlatonEvent if needed
        resultEvent = convertToEPlatonEvent(result, event);
      }

      long endTime = System.currentTimeMillis();
      LOGEJ.logPerformance("EPlatonBizDelegate", endTime - startTime,
          "Action: " + event.getTPSVCINFODTO().getAction_name());

      logger.info("EPlaton business delegate execution completed successfully");

    } catch (Exception e) {
      logger.error("Error in EPlaton business delegate execution", e);

      event.getTPSVCINFODTO().setErrorcode("EDEL001");
      event.getTPSVCINFODTO().setError_message("Business delegate execution failed: " + e.getMessage());

      LOGEJ.logError("EDEL001", "Business delegate execution failed", "EPlatonBizDelegate", e);

      resultEvent = event;
    }

    return resultEvent;
  }

  /**
   * Execute read-only business delegate operation
   */
  public EPlatonEvent executeReadOnly(EPlatonEvent event) {
    long startTime = System.currentTimeMillis();
    EPlatonEvent resultEvent = null;

    try {
      logger.info("Starting EPlaton read-only business delegate execution for action: {}",
          event.getTPSVCINFODTO().getAction_name());

      // Validate event
      if (!isValidEvent(event)) {
        event.getTPSVCINFODTO().setErrorcode("EVAL001");
        event.getTPSVCINFODTO().setError_message("Invalid event data");
        LOGEJ.logError("EVAL001", "Invalid event data", "EPlatonBizDelegate", null);
        return event;
      }

      // Process event using TCF (read-only)
      String transactionId = generateTransactionId();
      String transactionType = event.getTPSVCINFODTO().getAction_name();

      Object result = tcf.executeReadOnly(transactionId, transactionType, event);

      if (result instanceof EPlatonEvent) {
        resultEvent = (EPlatonEvent) result;
      } else {
        // Convert result to EPlatonEvent if needed
        resultEvent = convertToEPlatonEvent(result, event);
      }

      long endTime = System.currentTimeMillis();
      LOGEJ.logPerformance("EPlatonBizDelegateReadOnly", endTime - startTime,
          "Action: " + event.getTPSVCINFODTO().getAction_name());

      logger.info("EPlaton read-only business delegate execution completed successfully");

    } catch (Exception e) {
      logger.error("Error in EPlaton read-only business delegate execution", e);

      event.getTPSVCINFODTO().setErrorcode("EDEL001");
      event.getTPSVCINFODTO().setError_message("Read-only business delegate execution failed: " + e.getMessage());

      LOGEJ.logError("EDEL001", "Read-only business delegate execution failed", "EPlatonBizDelegate", e);

      resultEvent = event;
    }

    return resultEvent;
  }

  /**
   * Route to specific business action
   */
  public EPlatonEvent routeToAction(EPlatonEvent event) {
    try {
      String actionClassName = event.getTPSVCINFODTO().getAction_name();

      // Get action bean from Spring context
      EPlatonBizAction action = applicationContext.getBean(actionClassName, EPlatonBizAction.class);

      if (action == null) {
        throw new RuntimeException("Action not found: " + actionClassName);
      }

      return action.act(event);

    } catch (Exception e) {
      logger.error("Error routing to action: {}", event.getTPSVCINFODTO().getAction_name(), e);
      throw new RuntimeException("Action routing failed", e);
    }
  }

  /**
   * Validate event
   */
  private boolean isValidEvent(EPlatonEvent event) {
    return event != null &&
        event.getTPSVCINFODTO() != null &&
        event.getTPSVCINFODTO().getAction_name() != null;
  }

  /**
   * Generate transaction ID
   */
  private String generateTransactionId() {
    return "TXN" + System.currentTimeMillis() + "_" + Thread.currentThread().getId();
  }

  /**
   * Convert result to EPlatonEvent
   */
  private EPlatonEvent convertToEPlatonEvent(Object result, EPlatonEvent originalEvent) {
    if (result == null) {
      return originalEvent;
    }

    // If result is already EPlatonEvent, return it
    if (result instanceof EPlatonEvent) {
      return (EPlatonEvent) result;
    }

    // Otherwise, create a new event with the result
    EPlatonEvent newEvent = new EPlatonEvent();
    newEvent.setTPSVCINFODTO(originalEvent.getTPSVCINFODTO());
    newEvent.setCommon(originalEvent.getCommon());
    newEvent.setRequest(result);

    return newEvent;
  }

  /**
   * Check if event has error
   */
  private boolean isErr(EPlatonEvent event) {
    String errorCode = event.getTPSVCINFODTO().getErrorcode();
    if (errorCode == null || errorCode.isEmpty()) {
      return false;
    }

    char firstChar = errorCode.charAt(0);
    return firstChar == 'e' || firstChar == 's' || firstChar == 'E' || firstChar == 'S' || firstChar == '*';
  }
}
