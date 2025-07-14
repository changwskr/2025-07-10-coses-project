package com.skcc.oversea.deposit.business.facade;

import java.text.*;
import java.util.*;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skcc.oversea.framework.exception.CosesAppException;
import com.skcc.oversea.deposit.business.facade.*;
import com.skcc.oversea.deposit.transfer.*;
import com.skcc.oversea.eplatonframework.transfer.EPLcommonCDTO;
import com.skcc.oversea.eplatonframework.transfer.*;
import com.skcc.oversea.foundation.logej.*;

/**
 * Deposit Management Service for SKCC Oversea
 * Spring Boot service replacing EJB session bean
 */
@Service
@Transactional
public class DepositManagementSBean implements IDepositManagementSB {

  private static final Logger logger = LoggerFactory.getLogger(DepositManagementSBean.class);

  /**
   * Call method 01 - Account information setup
   */
  @Transactional(readOnly = false)
  public EPlatonEvent callmethod01(EPlatonEvent event) throws CosesAppException {
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    EPLcommonCDTO rescdto = null;
    EPLcommonCDTO reqcdto = new EPLcommonCDTO();

    logger.info("==================[callmethod01 START]");

    try {
      commonDTO = (EPlatonCommonDTO) event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();
      rescdto = (EPLcommonCDTO) event.getRequest();

      logger.info("Setting up new account information");
      logger.debug("Processing account setup for transaction: {}", tpsvcinfo.getTransaction_id());

      reqcdto.setAccountNumber("0001100100000088");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("8888888888888888");

      event.setResponse(reqcdto);

      logger.info("==================[callmethod01 END]");
      return event;

    } catch (Exception e) {
      logger.error("Error in callmethod01", e);
      throw new CosesAppException("Failed to process callmethod01", e);
    }
  }

  /**
   * Call method 02 - Account information setup (alternative)
   */
  @Transactional(readOnly = false)
  public EPlatonEvent callmethod02(EPlatonEvent event) throws CosesAppException {
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    EPLcommonCDTO rescdto = null;
    EPLcommonCDTO reqcdto = new EPLcommonCDTO();

    logger.info("==================[callmethod02 START]");

    try {
      commonDTO = (EPlatonCommonDTO) event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();
      rescdto = (EPLcommonCDTO) event.getRequest();

      logger.info("Setting up new account information");
      logger.debug("Processing account setup for transaction: {}", tpsvcinfo.getTransaction_id());

      reqcdto.setAccountNumber("0001100100000088");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("8888888888888888");

      event.setResponse(reqcdto);

      logger.info("==================[callmethod02 END]");
      return event;

    } catch (Exception e) {
      logger.error("Error in callmethod02", e);
      throw new CosesAppException("Failed to process callmethod02", e);
    }
  }
}
