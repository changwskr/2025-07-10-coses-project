package com.skcc.oversea.eplatonframework.business.delegate.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;
import com.skcc.oversea.eplatonframework.transfer.EPlatonCommonDTO;
import com.skcc.oversea.eplatonframework.transfer.TPSVCINFODTO;
import com.skcc.oversea.foundation.logej.LOGEJ;
import com.skcc.oversea.foundation.constant.Constants;

/**
 * E Common Business Action for SKCC Oversea
 * 
 * Handles E common business operations
 * using Spring Boot dependency injection and transaction management.
 */
@Component
public class ECommonBizAction extends EPlatonBizAction {

  private static final Logger logger = LoggerFactory.getLogger(ECommonBizAction.class);

  @Autowired
  private ECommonService ecommonService;

  /**
   * Execute E common business logic
   */
  @Override
  protected EPlatonEvent executeBusinessLogic(EPlatonEvent event) {
    try {
      logger.info("Starting E common business action execution");

      // Validate event
      if (!isValidECommonEvent(event)) {
        setErrorInfo(event, "EECM001", "Invalid E common event data");
        return event;
      }

      // Get request type
      String requestType = event.getTPSVCINFODTO().getReqName();

      // Route to appropriate service method
      EPlatonEvent result = routeToECommonService(event, requestType);

      logger.info("E common business action completed successfully");
      return result;

    } catch (Exception e) {
      logger.error("Error in E common business action", e);
      setErrorInfo(event, "EECM002", "E common business action failed: " + e.getMessage());
      return event;
    }
  }

  /**
   * Route to appropriate E common service method
   */
  private EPlatonEvent routeToECommonService(EPlatonEvent event, String requestType) {
    switch (requestType) {
      case "GET_E_SYSTEM_INFO":
        return ecommonService.getESystemInfo(event);
      case "GET_E_USER_INFO":
        return ecommonService.getEUserInfo(event);
      case "VALIDATE_E_SESSION":
        return ecommonService.validateESession(event);
      case "GET_E_CONFIGURATION":
        return ecommonService.getEConfiguration(event);
      case "UPDATE_E_CONFIGURATION":
        return ecommonService.updateEConfiguration(event);
      case "GET_E_AUDIT_LOG":
        return ecommonService.getEAuditLog(event);
      case "CLEAR_E_CACHE":
        return ecommonService.clearECache(event);
      case "GET_E_REFERENCE_DATA":
        return ecommonService.getEReferenceData(event);
      case "PROCESS_E_TRANSACTION":
        return ecommonService.processETransaction(event);
      default:
        setErrorInfo(event, "EECM003", "Unknown request type: " + requestType);
        return event;
    }
  }

  /**
   * Validate E common event
   */
  private boolean isValidECommonEvent(EPlatonEvent event) {
    if (event == null || event.getTPSVCINFODTO() == null) {
      return false;
    }

    String reqName = event.getTPSVCINFODTO().getReqName();
    return reqName != null && !reqName.trim().isEmpty();
  }

  /**
   * Pre-act processing for E common
   */
  @Override
  public void preAct(EPlatonEvent event) {
    logger.debug("Pre-act processing for E common business action");
    // Add any pre-processing logic here
  }

  /**
   * Post-act processing for E common
   */
  @Override
  public void postAct(EPlatonEvent event) {
    logger.debug("Post-act processing for E common business action");
    // Add any post-processing logic here
  }

  /**
   * E Common Service interface
   */
  public interface ECommonService {
    EPlatonEvent getESystemInfo(EPlatonEvent event);

    EPlatonEvent getEUserInfo(EPlatonEvent event);

    EPlatonEvent validateESession(EPlatonEvent event);

    EPlatonEvent getEConfiguration(EPlatonEvent event);

    EPlatonEvent updateEConfiguration(EPlatonEvent event);

    EPlatonEvent getEAuditLog(EPlatonEvent event);

    EPlatonEvent clearECache(EPlatonEvent event);

    EPlatonEvent getEReferenceData(EPlatonEvent event);

    EPlatonEvent processETransaction(EPlatonEvent event);
  }
}
