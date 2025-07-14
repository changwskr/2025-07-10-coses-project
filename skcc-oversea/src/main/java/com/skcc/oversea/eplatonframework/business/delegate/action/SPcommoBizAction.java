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
 * SP Commo Business Action for SKCC Oversea
 * 
 * Handles SP commo business operations
 * using Spring Boot dependency injection and transaction management.
 */
@Component
public class SPcommoBizAction extends EPlatonBizAction {

  private static final Logger logger = LoggerFactory.getLogger(SPcommoBizAction.class);

  @Autowired
  private SPcommoService spcommoService;

  /**
   * Execute SP commo business logic
   */
  @Override
  protected EPlatonEvent executeBusinessLogic(EPlatonEvent event) {
    try {
      logger.info("Starting SP commo business action execution");

      // Validate event
      if (!isValidSPcommoEvent(event)) {
        setErrorInfo(event, "ESPM001", "Invalid SP commo event data");
        return event;
      }

      // Get request type
      String requestType = event.getTPSVCINFODTO().getReqName();

      // Route to appropriate service method
      EPlatonEvent result = routeToSPcommoService(event, requestType);

      logger.info("SP commo business action completed successfully");
      return result;

    } catch (Exception e) {
      logger.error("Error in SP commo business action", e);
      setErrorInfo(event, "ESPM002", "SP commo business action failed: " + e.getMessage());
      return event;
    }
  }

  /**
   * Route to appropriate SP commo service method
   */
  private EPlatonEvent routeToSPcommoService(EPlatonEvent event, String requestType) {
    switch (requestType) {
      case "GET_SP_COMMO_INFO":
        return spcommoService.getSPCommoInfo(event);
      case "UPDATE_SP_COMMO_INFO":
        return spcommoService.updateSPCommoInfo(event);
      case "GET_SP_COMMO_LIST":
        return spcommoService.getSPCommoList(event);
      case "CREATE_SP_COMMO":
        return spcommoService.createSPCommo(event);
      case "DELETE_SP_COMMO":
        return spcommoService.deleteSPCommo(event);
      case "VALIDATE_SP_COMMO":
        return spcommoService.validateSPCommo(event);
      case "GET_SP_COMMO_HISTORY":
        return spcommoService.getSPCommoHistory(event);
      case "PROCESS_SP_COMMO_TRANSACTION":
        return spcommoService.processSPCommoTransaction(event);
      default:
        setErrorInfo(event, "ESPM003", "Unknown request type: " + requestType);
        return event;
    }
  }

  /**
   * Validate SP commo event
   */
  private boolean isValidSPcommoEvent(EPlatonEvent event) {
    if (event == null || event.getTPSVCINFODTO() == null) {
      return false;
    }

    String reqName = event.getTPSVCINFODTO().getReqName();
    return reqName != null && !reqName.trim().isEmpty();
  }

  /**
   * Pre-act processing for SP commo
   */
  @Override
  public void preAct(EPlatonEvent event) {
    logger.debug("Pre-act processing for SP commo business action");
    // Add any pre-processing logic here
  }

  /**
   * Post-act processing for SP commo
   */
  @Override
  public void postAct(EPlatonEvent event) {
    logger.debug("Post-act processing for SP commo business action");
    // Add any post-processing logic here
  }

  /**
   * SP Commo Service interface
   */
  public interface SPcommoService {
    EPlatonEvent getSPCommoInfo(EPlatonEvent event);

    EPlatonEvent updateSPCommoInfo(EPlatonEvent event);

    EPlatonEvent getSPCommoList(EPlatonEvent event);

    EPlatonEvent createSPCommo(EPlatonEvent event);

    EPlatonEvent deleteSPCommo(EPlatonEvent event);

    EPlatonEvent validateSPCommo(EPlatonEvent event);

    EPlatonEvent getSPCommoHistory(EPlatonEvent event);

    EPlatonEvent processSPCommoTransaction(EPlatonEvent event);
  }
}
