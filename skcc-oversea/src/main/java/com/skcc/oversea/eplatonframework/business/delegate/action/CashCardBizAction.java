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
 * Cash Card Business Action for SKCC Oversea
 * 
 * Handles cash card related business operations
 * using Spring Boot dependency injection and transaction management.
 */
@Component
public class CashCardBizAction extends EPlatonBizAction {

  private static final Logger logger = LoggerFactory.getLogger(CashCardBizAction.class);

  @Autowired
  private com.skcc.oversea.eplatonframework.business.service.CashCardServiceImpl cashCardService;

  /**
   * Execute cash card business logic
   */
  @Override
  protected EPlatonEvent executeBusinessLogic(EPlatonEvent event) {
    try {
      logger.info("Starting cash card business action execution");

      // Validate event
      if (!isValidCashCardEvent(event)) {
        setErrorInfo(event, "ECARD001", "Invalid cash card event data");
        return event;
      }

      // Get request type
      String requestType = event.getTPSVCINFODTO().getReqName();

      // Route to appropriate service method
      EPlatonEvent result = routeToCashCardService(event, requestType);

      logger.info("Cash card business action completed successfully");
      return result;

    } catch (Exception e) {
      logger.error("Error in cash card business action", e);
      setErrorInfo(event, "ECARD002", "Cash card business action failed: " + e.getMessage());
      return event;
    }
  }

  /**
   * Route to appropriate cash card service method
   */
  private EPlatonEvent routeToCashCardService(EPlatonEvent event, String requestType) {
    switch (requestType) {
      case "CREATE_CARD":
        return cashCardService.createCard(event);
      case "UPDATE_CARD":
        return cashCardService.updateCard(event);
      case "DELETE_CARD":
        return cashCardService.deleteCard(event);
      case "GET_CARD":
        return cashCardService.getCard(event);
      case "GET_CARD_LIST":
        return cashCardService.getCardList(event);
      case "BLOCK_CARD":
        return cashCardService.blockCard(event);
      case "UNBLOCK_CARD":
        return cashCardService.unblockCard(event);
      default:
        setErrorInfo(event, "ECARD003", "Unknown request type: " + requestType);
        return event;
    }
  }

  /**
   * Validate cash card event
   */
  private boolean isValidCashCardEvent(EPlatonEvent event) {
    if (event == null || event.getTPSVCINFODTO() == null) {
      return false;
    }

    String reqName = event.getTPSVCINFODTO().getReqName();
    return reqName != null && !reqName.trim().isEmpty();
  }

  /**
   * Pre-act processing for cash card
   */
  @Override
  public void preAct(EPlatonEvent event) {
    logger.debug("Pre-act processing for cash card business action");
    // Add any pre-processing logic here
  }

  /**
   * Post-act processing for cash card
   */
  @Override
  public void postAct(EPlatonEvent event) {
    logger.debug("Post-act processing for cash card business action");
    // Add any post-processing logic here
  }

  /**
   * Cash Card Service interface
   */
  public interface CashCardService {
    EPlatonEvent createCard(EPlatonEvent event);

    EPlatonEvent updateCard(EPlatonEvent event);

    EPlatonEvent deleteCard(EPlatonEvent event);

    EPlatonEvent getCard(EPlatonEvent event);

    EPlatonEvent getCardList(EPlatonEvent event);

    EPlatonEvent blockCard(EPlatonEvent event);

    EPlatonEvent unblockCard(EPlatonEvent event);
  }
}
