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
 * SP Common Business Action for SKCC Oversea
 * 
 * Handles SP common business operations
 * using Spring Boot dependency injection and transaction management.
 */
@Component
public class SPcommonBizAction extends EPlatonBizAction {

  private static final Logger logger = LoggerFactory.getLogger(SPcommonBizAction.class);

  @Autowired
  private SPcommonService spcommonService;

  /**
   * Execute SP common business logic
   */
  @Override
  protected EPlatonEvent executeBusinessLogic(EPlatonEvent event) {
    try {
      logger.info("Starting SP common business action execution");

      // Validate event
      if (!isValidSPcommonEvent(event)) {
        setErrorInfo(event, "ESPC001", "Invalid SP common event data");
        return event;
      }

      // Get request type
      String requestType = event.getTPSVCINFODTO().getReqName();

      // Route to appropriate service method
      EPlatonEvent result = routeToSPcommonService(event, requestType);

      logger.info("SP common business action completed successfully");
      return result;

    } catch (Exception e) {
      logger.error("Error in SP common business action", e);
      setErrorInfo(event, "ESPC002", "SP common business action failed: " + e.getMessage());
      return event;
    }
  }

  /**
   * Route to appropriate SP common service method
   */
  private EPlatonEvent routeToSPcommonService(EPlatonEvent event, String requestType) {
    switch (requestType) {
      case "GET_SP_SYSTEM_INFO":
        return spcommonService.getSPSystemInfo(event);
      case "GET_SP_USER_INFO":
        return spcommonService.getSPUserInfo(event);
      case "VALIDATE_SP_SESSION":
        return spcommonService.validateSPSession(event);
      case "GET_SP_CONFIGURATION":
        return spcommonService.getSPConfiguration(event);
      case "UPDATE_SP_CONFIGURATION":
        return spcommonService.updateSPConfiguration(event);
      case "GET_SP_AUDIT_LOG":
        return spcommonService.getSPAuditLog(event);
      case "CLEAR_SP_CACHE":
        return spcommonService.clearSPCache(event);
      case "GET_SP_REFERENCE_DATA":
        return spcommonService.getSPReferenceData(event);
      default:
        setErrorInfo(event, "ESPC003", "Unknown request type: " + requestType);
        return event;
    }
  }

  /**
   * Validate SP common event
   */
  private boolean isValidSPcommonEvent(EPlatonEvent event) {
    if (event == null || event.getTPSVCINFODTO() == null) {
      return false;
    }

    String reqName = event.getTPSVCINFODTO().getReqName();
    return reqName != null && !reqName.trim().isEmpty();
  }

  /**
   * Pre-act processing for SP common
   */
  @Override
  public void preAct(EPlatonEvent event) {
    logger.debug("Pre-act processing for SP common business action");
    // Add any pre-processing logic here
  }

  /**
   * Post-act processing for SP common
   */
  @Override
  public void postAct(EPlatonEvent event) {
    logger.debug("Post-act processing for SP common business action");
    // Add any post-processing logic here
  }

  /**
   * SP Common Service interface
   */
  public interface SPcommonService {
    EPlatonEvent getSPSystemInfo(EPlatonEvent event);

    EPlatonEvent getSPUserInfo(EPlatonEvent event);

    EPlatonEvent validateSPSession(EPlatonEvent event);

    EPlatonEvent getSPConfiguration(EPlatonEvent event);

    EPlatonEvent updateSPConfiguration(EPlatonEvent event);

    EPlatonEvent getSPAuditLog(EPlatonEvent event);

    EPlatonEvent clearSPCache(EPlatonEvent event);

    EPlatonEvent getSPReferenceData(EPlatonEvent event);
  }
}
