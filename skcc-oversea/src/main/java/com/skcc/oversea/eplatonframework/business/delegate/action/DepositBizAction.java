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
 * Deposit Business Action for SKCC Oversea
 * 
 * Handles deposit related business operations
 * using Spring Boot dependency injection and transaction management.
 */
@Component
public class DepositBizAction extends EPlatonBizAction {

  private static final Logger logger = LoggerFactory.getLogger(DepositBizAction.class);

  @Autowired
  private DepositService depositService;

  /**
   * Execute deposit business logic
   */
  @Override
  protected EPlatonEvent executeBusinessLogic(EPlatonEvent event) {
    try {
      logger.info("Starting deposit business action execution");

      // Validate event
      if (!isValidDepositEvent(event)) {
        setErrorInfo(event, "EDEP001", "Invalid deposit event data");
        return event;
      }

      // Get request type
      String requestType = event.getTPSVCINFODTO().getReqName();

      // Route to appropriate service method
      EPlatonEvent result = routeToDepositService(event, requestType);

      logger.info("Deposit business action completed successfully");
      return result;

    } catch (Exception e) {
      logger.error("Error in deposit business action", e);
      setErrorInfo(event, "EDEP002", "Deposit business action failed: " + e.getMessage());
      return event;
    }
  }

  /**
   * Route to appropriate deposit service method
   */
  private EPlatonEvent routeToDepositService(EPlatonEvent event, String requestType) {
    switch (requestType) {
      case "CREATE_DEPOSIT":
        return depositService.createDeposit(event);
      case "UPDATE_DEPOSIT":
        return depositService.updateDeposit(event);
      case "DELETE_DEPOSIT":
        return depositService.deleteDeposit(event);
      case "GET_DEPOSIT":
        return depositService.getDeposit(event);
      case "GET_DEPOSIT_LIST":
        return depositService.getDepositList(event);
      case "WITHDRAW_DEPOSIT":
        return depositService.withdrawDeposit(event);
      case "TRANSFER_DEPOSIT":
        return depositService.transferDeposit(event);
      default:
        setErrorInfo(event, "EDEP003", "Unknown request type: " + requestType);
        return event;
    }
  }

  /**
   * Validate deposit event
   */
  private boolean isValidDepositEvent(EPlatonEvent event) {
    if (event == null || event.getTPSVCINFODTO() == null) {
      return false;
    }

    String reqName = event.getTPSVCINFODTO().getReqName();
    return reqName != null && !reqName.trim().isEmpty();
  }

  /**
   * Pre-act processing for deposit
   */
  @Override
  public void preAct(EPlatonEvent event) {
    logger.debug("Pre-act processing for deposit business action");
    // Add any pre-processing logic here
  }

  /**
   * Post-act processing for deposit
   */
  @Override
  public void postAct(EPlatonEvent event) {
    logger.debug("Post-act processing for deposit business action");
    // Add any post-processing logic here
  }

  /**
   * Deposit Service interface
   */
  public interface DepositService {
    EPlatonEvent createDeposit(EPlatonEvent event);

    EPlatonEvent updateDeposit(EPlatonEvent event);

    EPlatonEvent deleteDeposit(EPlatonEvent event);

    EPlatonEvent getDeposit(EPlatonEvent event);

    EPlatonEvent getDepositList(EPlatonEvent event);

    EPlatonEvent withdrawDeposit(EPlatonEvent event);

    EPlatonEvent transferDeposit(EPlatonEvent event);
  }
}
