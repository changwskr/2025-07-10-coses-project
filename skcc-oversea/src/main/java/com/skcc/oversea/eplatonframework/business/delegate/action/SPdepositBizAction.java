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
 * SP Deposit Business Action for SKCC Oversea
 * 
 * Handles SP deposit related business operations
 * using Spring Boot dependency injection and transaction management.
 */
@Component
public class SPdepositBizAction extends EPlatonBizAction {

  private static final Logger logger = LoggerFactory.getLogger(SPdepositBizAction.class);

  @Autowired
  private SPdepositService spdepositService;

  /**
   * Execute SP deposit business logic
   */
  @Override
  protected EPlatonEvent executeBusinessLogic(EPlatonEvent event) {
    try {
      logger.info("Starting SP deposit business action execution");

      // Validate event
      if (!isValidSPdepositEvent(event)) {
        setErrorInfo(event, "ESPD001", "Invalid SP deposit event data");
        return event;
      }

      // Get request type
      String requestType = event.getTPSVCINFODTO().getReqName();

      // Route to appropriate service method
      EPlatonEvent result = routeToSPdepositService(event, requestType);

      logger.info("SP deposit business action completed successfully");
      return result;

    } catch (Exception e) {
      logger.error("Error in SP deposit business action", e);
      setErrorInfo(event, "ESPD002", "SP deposit business action failed: " + e.getMessage());
      return event;
    }
  }

  /**
   * Route to appropriate SP deposit service method
   */
  private EPlatonEvent routeToSPdepositService(EPlatonEvent event, String requestType) {
    switch (requestType) {
      case "CREATE_SP_DEPOSIT":
        return spdepositService.createSPDeposit(event);
      case "UPDATE_SP_DEPOSIT":
        return spdepositService.updateSPDeposit(event);
      case "DELETE_SP_DEPOSIT":
        return spdepositService.deleteSPDeposit(event);
      case "GET_SP_DEPOSIT":
        return spdepositService.getSPDeposit(event);
      case "GET_SP_DEPOSIT_LIST":
        return spdepositService.getSPDepositList(event);
      case "WITHDRAW_SP_DEPOSIT":
        return spdepositService.withdrawSPDeposit(event);
      case "TRANSFER_SP_DEPOSIT":
        return spdepositService.transferSPDeposit(event);
      case "CALCULATE_SP_INTEREST":
        return spdepositService.calculateSPInterest(event);
      default:
        setErrorInfo(event, "ESPD003", "Unknown request type: " + requestType);
        return event;
    }
  }

  /**
   * Validate SP deposit event
   */
  private boolean isValidSPdepositEvent(EPlatonEvent event) {
    if (event == null || event.getTPSVCINFODTO() == null) {
      return false;
    }

    String reqName = event.getTPSVCINFODTO().getReqName();
    return reqName != null && !reqName.trim().isEmpty();
  }

  /**
   * Pre-act processing for SP deposit
   */
  @Override
  public void preAct(EPlatonEvent event) {
    logger.debug("Pre-act processing for SP deposit business action");
    // Add any pre-processing logic here
  }

  /**
   * Post-act processing for SP deposit
   */
  @Override
  public void postAct(EPlatonEvent event) {
    logger.debug("Post-act processing for SP deposit business action");
    // Add any post-processing logic here
  }

  /**
   * SP Deposit Service interface
   */
  public interface SPdepositService {
    EPlatonEvent createSPDeposit(EPlatonEvent event);

    EPlatonEvent updateSPDeposit(EPlatonEvent event);

    EPlatonEvent deleteSPDeposit(EPlatonEvent event);

    EPlatonEvent getSPDeposit(EPlatonEvent event);

    EPlatonEvent getSPDepositList(EPlatonEvent event);

    EPlatonEvent withdrawSPDeposit(EPlatonEvent event);

    EPlatonEvent transferSPDeposit(EPlatonEvent event);

    EPlatonEvent calculateSPInterest(EPlatonEvent event);
  }
}
