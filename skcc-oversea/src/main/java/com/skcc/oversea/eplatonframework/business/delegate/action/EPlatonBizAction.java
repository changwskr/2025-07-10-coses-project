package com.skcc.oversea.eplatonframework.business.delegate.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;
import com.skcc.oversea.eplatonframework.transfer.EPlatonCommonDTO;
import com.skcc.oversea.eplatonframework.transfer.TPSVCINFODTO;
import com.skcc.oversea.foundation.logej.LOGEJ;
import com.skcc.oversea.foundation.constant.Constants;
import com.skcc.oversea.framework.transaction.tcf.TCF;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

/**
 * EPlaton Business Action for SKCC Oversea
 * 
 * Provides business action functionality for the EPlaton framework
 * using Spring Boot dependency injection and transaction management.
 */
@Component
public abstract class EPlatonBizAction implements IEPlatonBizAction {

  private static final Logger logger = LoggerFactory.getLogger(EPlatonBizAction.class);
  private static final AtomicLong transactionCounter = new AtomicLong(0);

  @Autowired
  protected TCF tcf;

  protected EPlatonEvent currentEvent;

  /**
   * Execute business action
   */
  @Transactional
  public EPlatonEvent act(EPlatonEvent event) {
    this.currentEvent = event;
    String transactionId = generateTransactionId();

    LOGEJ.startTransaction(transactionId, "BIZ_ACTION", getClass().getSimpleName());

    try {
      logger.info("Starting business action: {}", getClass().getSimpleName());

      // Pre-act processing
      preAct(event);

      // Execute business logic
      EPlatonEvent result = executeBusinessLogic(event);

      // Post-act processing
      postAct(result);

      LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_SUCCESS, "Business action completed successfully");
      logger.info("Business action completed successfully: {}", getClass().getSimpleName());

      return result;

    } catch (Exception e) {
      LOGEJ.logError("EACT001", "Business action failed: " + e.getMessage(),
          getClass().getSimpleName(), e);
      LOGEJ.endTransaction(transactionId, Constants.TXN_STATUS_FAILED,
          "Business action failed: " + e.getMessage());

      logger.error("Business action failed: {}", getClass().getSimpleName(), e);

      // Set error information
      setErrorInfo(event, "EACT001", "Business action failed: " + e.getMessage());

      return event;
    }
  }

  /**
   * Execute business logic - to be implemented by subclasses
   */
  protected abstract EPlatonEvent executeBusinessLogic(EPlatonEvent event);

  /**
   * Execute business action using reflection (legacy compatibility)
   */
  protected EPlatonEvent doAct(Object target, EPlatonEvent event) {
    this.currentEvent = event;
    TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
    String methodName = "execute";

    LOGEJ.logBusinessEvent("DO_ACT", event.getTPSVCINFODTO().getReqName(),
        "Target: " + target.getClass().getSimpleName());

    try {
      // Get method using reflection
      Method method = target.getClass().getMethod(methodName, EPlatonEvent.class);

      logger.debug("Calling method: {} on target: {}", method.toString(), target.getClass().getSimpleName());

      // Invoke method
      EPlatonEvent result = (EPlatonEvent) method.invoke(target, event);

      // Check for errors
      if (isError(result)) {
        logger.warn("Error detected in business action execution");
        return result;
      }

      return result;

    } catch (Exception e) {
      logger.error("Error in doAct method", e);
      setErrorInfo(event, "EFWK0036", "Method invocation failed: " + e.getMessage());
      LOGEJ.logError("EFWK0036", "Method invocation failed", getClass().getSimpleName(), e);
      return event;
    }
  }

  /**
   * Set error information
   */
  protected void setErrorInfo(EPlatonEvent event, String errorCode, String errorMessage) {
    TPSVCINFODTO tpsvcinfo = event.getTPSVCINFODTO();
    String currentErrorCode = tpsvcinfo.getErrorcode();

    if (currentErrorCode != null && currentErrorCode.startsWith("I")) {
      tpsvcinfo.setErrorcode(errorCode);
      tpsvcinfo.setError_message(errorMessage);
    } else if (currentErrorCode != null && currentErrorCode.startsWith("E")) {
      String combinedErrorCode = errorCode + "|" + currentErrorCode;
      tpsvcinfo.setErrorcode(combinedErrorCode);
      tpsvcinfo.setError_message(errorMessage);
    } else {
      tpsvcinfo.setErrorcode(errorCode);
      tpsvcinfo.setError_message(errorMessage);
    }
  }

  /**
   * Check if event has error
   */
  protected boolean isError(EPlatonEvent event) {
    if (event == null || event.getTPSVCINFODTO() == null) {
      return true;
    }

    String errorCode = event.getTPSVCINFODTO().getErrorcode();
    if (errorCode == null || errorCode.isEmpty()) {
      return false;
    }

    char firstChar = errorCode.charAt(0);
    return firstChar == 'e' || firstChar == 's' || firstChar == 'E' || firstChar == 'S' || firstChar == '*';
  }

  /**
   * Generate transaction ID
   */
  protected String generateTransactionId() {
    return "TXN" + System.currentTimeMillis() + "_" + transactionCounter.incrementAndGet();
  }

  /**
   * Pre-act processing
   */
  public void preAct(EPlatonEvent event) {
    logger.debug("Pre-act processing for: {}", getClass().getSimpleName());
    // Override in subclasses if needed
  }

  /**
   * Post-act processing
   */
  public void postAct(EPlatonEvent event) {
    logger.debug("Post-act processing for: {}", getClass().getSimpleName());
    // Override in subclasses if needed
  }

  /**
   * Get current event
   */
  protected EPlatonEvent getCurrentEvent() {
    return currentEvent;
  }

  /**
   * Get TCF instance
   */
  protected TCF getTcf() {
    return tcf;
  }
}
