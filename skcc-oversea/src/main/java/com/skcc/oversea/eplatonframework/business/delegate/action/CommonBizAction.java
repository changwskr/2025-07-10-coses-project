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
 * Common Business Action for SKCC Oversea
 * 
 * Handles common business operations
 * using Spring Boot dependency injection and transaction management.
 */
@Component
public class CommonBizAction extends EPlatonBizAction {

  private static final Logger logger = LoggerFactory.getLogger(CommonBizAction.class);

  @Autowired
  private CommonService commonService;

  /**
   * Execute common business logic
   */
  @Override
  protected EPlatonEvent executeBusinessLogic(EPlatonEvent event) {
    try {
      logger.info("Starting common business action execution");

      // Validate event
      if (!isValidCommonEvent(event)) {
        setErrorInfo(event, "ECOM001", "Invalid common event data");
        return event;
      }

      // Get request type
      String requestType = event.getTPSVCINFODTO().getReqName();

      // Route to appropriate service method
      EPlatonEvent result = routeToCommonService(event, requestType);

      logger.info("Common business action completed successfully");
      return result;

    } catch (Exception e) {
      logger.error("Error in common business action", e);
      setErrorInfo(event, "ECOM002", "Common business action failed: " + e.getMessage());
      return event;
    }
  }

  /**
   * Route to appropriate common service method
   */
  private EPlatonEvent routeToCommonService(EPlatonEvent event, String requestType) {
    switch (requestType) {
      case "GET_SYSTEM_INFO":
        return commonService.getSystemInfo(event);
      case "GET_USER_INFO":
        return commonService.getUserInfo(event);
      case "VALIDATE_SESSION":
        return commonService.validateSession(event);
      case "GET_CONFIGURATION":
        return commonService.getConfiguration(event);
      case "UPDATE_CONFIGURATION":
        return commonService.updateConfiguration(event);
      case "GET_AUDIT_LOG":
        return commonService.getAuditLog(event);
      case "CLEAR_CACHE":
        return commonService.clearCache(event);
      default:
        setErrorInfo(event, "ECOM003", "Unknown request type: " + requestType);
        return event;
    }
  }

  /**
   * Validate common event
   */
  private boolean isValidCommonEvent(EPlatonEvent event) {
    if (event == null || event.getTPSVCINFODTO() == null) {
      return false;
    }

    String reqName = event.getTPSVCINFODTO().getReqName();
    return reqName != null && !reqName.trim().isEmpty();
  }

  /**
   * Pre-act processing for common
   */
  @Override
  public void preAct(EPlatonEvent event) {
    logger.debug("Pre-act processing for common business action");
    // Add any pre-processing logic here
  }

  /**
   * Post-act processing for common
   */
  @Override
  public void postAct(EPlatonEvent event) {
    logger.debug("Post-act processing for common business action");
    // Add any post-processing logic here
  }

  /**
   * Common Service interface
   */
  public interface CommonService {
    EPlatonEvent getSystemInfo(EPlatonEvent event);

    EPlatonEvent getUserInfo(EPlatonEvent event);

    EPlatonEvent validateSession(EPlatonEvent event);

    EPlatonEvent getConfiguration(EPlatonEvent event);

    EPlatonEvent updateConfiguration(EPlatonEvent event);

    EPlatonEvent getAuditLog(EPlatonEvent event);

    EPlatonEvent clearCache(EPlatonEvent event);
  }
}
