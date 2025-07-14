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
 * SP Cash Card Business Action for SKCC Oversea
 * 
 * Handles SP cash card business operations
 * using Spring Boot dependency injection and transaction management.
 */
@Component
public class SPcashcardBizAction extends EPlatonBizAction {

  private static final Logger logger = LoggerFactory.getLogger(SPcashcardBizAction.class);

  @Autowired
  private SPcashcardService spcashcardService;

  /**
   * Execute SP cash card business logic
   */
  @Override
  protected EPlatonEvent executeBusinessLogic(EPlatonEvent event) {
    try {
      logger.info("Starting SP cash card business action execution");

      // Validate event
      if (!isValidSPcashcardEvent(event)) {
        setErrorInfo(event, "ESPC001", "Invalid SP cash card event data");
        return event;
      }

      // Get request type
      String requestType = event.getTPSVCINFODTO().getReqName();

      // Route to appropriate service method
      EPlatonEvent result = routeToSPcashcardService(event, requestType);

      logger.info("SP cash card business action completed successfully");
      return result;

    } catch (Exception e) {
      logger.error("Error in SP cash card business action", e);
      setErrorInfo(event, "ESPC002", "SP cash card business action failed: " + e.getMessage());
      return event;
    }
  }

  /**
   * Route to appropriate SP cash card service method
   */
  private EPlatonEvent routeToSPcashcardService(EPlatonEvent event, String requestType) {
    switch (requestType) {
      case "CREATE_SP_CASH_CARD":
        return spcashcardService.createSPCashCard(event);
      case "UPDATE_SP_CASH_CARD":
        return spcashcardService.updateSPCashCard(event);
      case "DELETE_SP_CASH_CARD":
        return spcashcardService.deleteSPCashCard(event);
      case "GET_SP_CASH_CARD":
        return spcashcardService.getSPCashCard(event);
      case "GET_SP_CASH_CARD_LIST":
        return spcashcardService.getSPCashCardList(event);
      case "BLOCK_SP_CASH_CARD":
        return spcashcardService.blockSPCashCard(event);
      case "UNBLOCK_SP_CASH_CARD":
        return spcashcardService.unblockSPCashCard(event);
      case "RECHARGE_SP_CASH_CARD":
        return spcashcardService.rechargeSPCashCard(event);
      case "GET_SP_CASH_CARD_BALANCE":
        return spcashcardService.getSPCashCardBalance(event);
      default:
        setErrorInfo(event, "ESPC003", "Unknown request type: " + requestType);
        return event;
    }
  }

  /**
   * Validate SP cash card event
   */
  private boolean isValidSPcashcardEvent(EPlatonEvent event) {
    if (event == null || event.getTPSVCINFODTO() == null) {
      return false;
    }

    String reqName = event.getTPSVCINFODTO().getReqName();
    return reqName != null && !reqName.trim().isEmpty();
  }

  /**
   * Pre-act processing for SP cash card
   */
  @Override
  public void preAct(EPlatonEvent event) {
    logger.debug("Pre-act processing for SP cash card business action");
    // Add any pre-processing logic here
  }

  /**
   * Post-act processing for SP cash card
   */
  @Override
  public void postAct(EPlatonEvent event) {
    logger.debug("Post-act processing for SP cash card business action");
    // Add any post-processing logic here
  }

  /**
   * SP Cash Card Service interface
   */
  public interface SPcashcardService {
    EPlatonEvent createSPCashCard(EPlatonEvent event);

    EPlatonEvent updateSPCashCard(EPlatonEvent event);

    EPlatonEvent deleteSPCashCard(EPlatonEvent event);

    EPlatonEvent getSPCashCard(EPlatonEvent event);

    EPlatonEvent getSPCashCardList(EPlatonEvent event);

    EPlatonEvent blockSPCashCard(EPlatonEvent event);

    EPlatonEvent unblockSPCashCard(EPlatonEvent event);

    EPlatonEvent rechargeSPCashCard(EPlatonEvent event);

    EPlatonEvent getSPCashCardBalance(EPlatonEvent event);
  }
}
