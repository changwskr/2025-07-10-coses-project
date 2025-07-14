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
 * Teller Business Action for SKCC Oversea
 * 
 * Handles teller related business operations
 * using Spring Boot dependency injection and transaction management.
 */
@Component
public class TellerBizAction extends EPlatonBizAction {

  private static final Logger logger = LoggerFactory.getLogger(TellerBizAction.class);

  @Autowired
  private TellerService tellerService;

  /**
   * Execute teller business logic
   */
  @Override
  protected EPlatonEvent executeBusinessLogic(EPlatonEvent event) {
    try {
      logger.info("Starting teller business action execution");

      // Validate event
      if (!isValidTellerEvent(event)) {
        setErrorInfo(event, "ETEL001", "Invalid teller event data");
        return event;
      }

      // Get request type
      String requestType = event.getTPSVCINFODTO().getReqName();

      // Route to appropriate service method
      EPlatonEvent result = routeToTellerService(event, requestType);

      logger.info("Teller business action completed successfully");
      return result;

    } catch (Exception e) {
      logger.error("Error in teller business action", e);
      setErrorInfo(event, "ETEL002", "Teller business action failed: " + e.getMessage());
      return event;
    }
  }

  /**
   * Route to appropriate teller service method
   */
  private EPlatonEvent routeToTellerService(EPlatonEvent event, String requestType) {
    switch (requestType) {
      case "LOGIN_TELLER":
        return tellerService.loginTeller(event);
      case "LOGOUT_TELLER":
        return tellerService.logoutTeller(event);
      case "GET_TELLER_INFO":
        return tellerService.getTellerInfo(event);
      case "UPDATE_TELLER_INFO":
        return tellerService.updateTellerInfo(event);
      case "GET_TELLER_PERMISSIONS":
        return tellerService.getTellerPermissions(event);
      case "VALIDATE_TELLER_SESSION":
        return tellerService.validateTellerSession(event);
      case "GET_TELLER_TRANSACTIONS":
        return tellerService.getTellerTransactions(event);
      default:
        setErrorInfo(event, "ETEL003", "Unknown request type: " + requestType);
        return event;
    }
  }

  /**
   * Validate teller event
   */
  private boolean isValidTellerEvent(EPlatonEvent event) {
    if (event == null || event.getTPSVCINFODTO() == null) {
      return false;
    }

    String reqName = event.getTPSVCINFODTO().getReqName();
    return reqName != null && !reqName.trim().isEmpty();
  }

  /**
   * Pre-act processing for teller
   */
  @Override
  public void preAct(EPlatonEvent event) {
    logger.debug("Pre-act processing for teller business action");
    // Add any pre-processing logic here
  }

  /**
   * Post-act processing for teller
   */
  @Override
  public void postAct(EPlatonEvent event) {
    logger.debug("Post-act processing for teller business action");
    // Add any post-processing logic here
  }

  /**
   * Teller Service interface
   */
  public interface TellerService {
    EPlatonEvent loginTeller(EPlatonEvent event);

    EPlatonEvent logoutTeller(EPlatonEvent event);

    EPlatonEvent getTellerInfo(EPlatonEvent event);

    EPlatonEvent updateTellerInfo(EPlatonEvent event);

    EPlatonEvent getTellerPermissions(EPlatonEvent event);

    EPlatonEvent validateTellerSession(EPlatonEvent event);

    EPlatonEvent getTellerTransactions(EPlatonEvent event);
  }
}
