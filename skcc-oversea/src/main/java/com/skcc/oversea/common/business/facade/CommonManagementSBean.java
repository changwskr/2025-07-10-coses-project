package com.skcc.oversea.common.business.facade;

import java.text.*;
import java.util.*;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skcc.oversea.framework.exception.CosesAppException;
import com.skcc.oversea.deposit.business.facade.*;
import com.skcc.oversea.deposit.transfer.*;
import com.skcc.oversea.framework.transaction.helper.Convert;
import com.skcc.oversea.foundation.logej.*;
import com.skcc.oversea.framework.transaction.tpmutil.TPMSVCAPI;
import com.skcc.oversea.framework.transaction.tpmutil.TPSsendrecv;
import com.skcc.oversea.eplatonframework.transfer.*;
import com.chb.coses.deposit.transfer.AccountQueryCDTO;
import com.skcc.oversea.eplatonframework.transfer.EPLcommonCDTO;

/**
 * Common Management Service for SKCC Oversea
 * Spring Boot service replacing EJB session bean
 */
@Service
@Transactional
public class CommonManagementSBean implements ICommonManagementSB {

  private static final Logger logger = LoggerFactory.getLogger(CommonManagementSBean.class);

  @Autowired
  private TPSsendrecv tpsSendRecv;

  /**
   * Call method 01 - Common business logic
   */
  @Transactional(readOnly = false)
  public EPlatonEvent callmethod01(EPlatonEvent event) throws CosesAppException {
    EPlatonEvent response_event = Convert.createEPlatonEvent(event);
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    EPLcommonCDTO rescdto = null;
    EPLcommonCDTO reqcdto = new EPLcommonCDTO();

    try {
      logger.info("==================[callmethod01 START]");

      commonDTO = (EPlatonCommonDTO) event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();
      rescdto = (EPLcommonCDTO) event.getRequest();

      logger.info("Setting up new account information");
      reqcdto.setAccountNumber("0001100100000048");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("9999999999999999");

      // TPSsendrecv TEST
      EPlatonCommonDTO tpmcommonDTO = event.getCommon();
      TPSVCINFODTO tpmtpsvcinfoDTO = event.getTPSVCINFODTO();
      response_event = tpsSendRecv.CallEJB("spcashcard-callmethod01", event, reqcdto);

      logger.debug("TPSsendrecv response received");

      switch (response_event.getTPSVCINFODTO().getErrorcode().charAt(0)) {
        case 'I':
          response_event.setResponse(response_event.getResponse());
          break;
        case 'E':
          response_event.getTPSVCINFODTO().setErrorcode(response_event.getTPSVCINFODTO().getErrorcode());
          String errorcode = "EFWK0047" + "|" + response_event.getTPSVCINFODTO().getErrorcode();
          tpsvcinfo.setErrorcode(errorcode);
          tpsvcinfo.setError_message(this.getClass().getName() + ".callmethod01():TPSsendrecv Fail");
          break;
      }

      if (response_event.getResponse() == null) {
        logger.warn("Response CDTO is null");
      } else {
        logger.debug("Response CDTO is not null");
      }
    } catch (Exception ex) {
      logger.error("Error in callmethod01", ex);
      String errorcode = "EFWK0048" + "|" + tpsvcinfo.getErrorcode();
      tpsvcinfo.setErrorcode(errorcode);
      tpsvcinfo.setError_message(this.getClass().getName() + ".callmethod01():TPSsendrecv Fail");
      throw new CosesAppException("Failed to process callmethod01", ex);
    }

    // Pass CDTO from external system to client
    event.setResponse(response_event.getResponse());

    logger.info("==================[callmethod01 END] {}", event.getTPSVCINFODTO().getSystem_name());
    return event;
  }

  /**
   * Call method 02 - Common business logic (alternative)
   */
  @Transactional(readOnly = false)
  public EPlatonEvent callmethod02(EPlatonEvent event) throws CosesAppException {
    EPlatonEvent response_event = Convert.createEPlatonEvent(event);
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    EPLcommonCDTO rescdto = null;
    EPLcommonCDTO reqcdto = new EPLcommonCDTO();

    try {
      logger.info("==================[callmethod02 START]");

      commonDTO = (EPlatonCommonDTO) event.getCommon();
      tpsvcinfo = event.getTPSVCINFODTO();
      rescdto = (EPLcommonCDTO) event.getRequest();

      logger.info("Setting up new account information");
      reqcdto.setAccountNumber("0001100100000048");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("9999999999999999");

      // TPSsendrecv TEST
      EPlatonCommonDTO tpmcommonDTO = event.getCommon();
      TPSVCINFODTO tpmtpsvcinfoDTO = event.getTPSVCINFODTO();
      response_event = tpsSendRecv.CallEJB("spcashcard-callmethod02", event, reqcdto);
      response_event = tpsSendRecv.CallEJB("spdeposit-callmethod01", event, reqcdto);

      logger.debug("TPSsendrecv response received");

      switch (response_event.getTPSVCINFODTO().getErrorcode().charAt(0)) {
        case 'I':
          response_event.setResponse(response_event.getResponse());
          break;
        case 'E':
          response_event.getTPSVCINFODTO().setErrorcode(response_event.getTPSVCINFODTO().getErrorcode());
          String errorcode = "EFWK0047" + "|" + response_event.getTPSVCINFODTO().getErrorcode();
          tpsvcinfo.setErrorcode(errorcode);
          tpsvcinfo.setError_message(this.getClass().getName() + ".callmethod02():TPSsendrecv Fail");
          break;
      }

      if (response_event.getResponse() == null) {
        logger.warn("Response CDTO is null");
      } else {
        logger.debug("Response CDTO is not null");
      }
    } catch (Exception ex) {
      logger.error("Error in callmethod02", ex);
      String errorcode = "EFWK0048" + "|" + tpsvcinfo.getErrorcode();
      tpsvcinfo.setErrorcode(errorcode);
      tpsvcinfo.setError_message(this.getClass().getName() + ".callmethod02():TPSsendrecv Fail");
      throw new CosesAppException("Failed to process callmethod02", ex);
    }

    // Pass CDTO from external system to client
    event.setResponse(response_event.getResponse());

    logger.info("==================[callmethod02 END] {}", event.getTPSVCINFODTO().getSystem_name());
    return event;
  }

  /**
   * Call method 03 - Simple account setup
   */
  @Transactional(readOnly = false)
  public EPlatonEvent callmethod03(EPlatonEvent event) throws CosesAppException {
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    EPLcommonCDTO reqcdto = new EPLcommonCDTO();

    try {
      reqcdto.setAccountNumber("0001100100000048");
      reqcdto.setBankCode("03");
      reqcdto.setAccountNumber("9999999999999999");

      event.setResponse(reqcdto);

      logger.info("==================[callmethod03 START] {}", event.getTPSVCINFODTO().getSystem_name());
      logger.info(" -->> call method 3이 호출되었습니다.");
      logger.info("==================[callmethod03 END] {}", event.getTPSVCINFODTO().getSystem_name());

      return event;
    } catch (Exception e) {
      logger.error("Error in callmethod03", e);
      throw new CosesAppException("Failed to process callmethod03", e);
    }
  }
}
