package com.ims.eplaton.eplatonFWK.othersystem.operation;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */


import com.chb.coses.framework.exception.BizActionException;
import com.chb.coses.framework.transfer.IEvent;

import com.ims.eplaton.eplatonFWK.business.operation.IBizOperation;
import com.ims.eplaton.eplatonFWK.transfer.EPlatonEvent;
import com.chb.coses.deposit.transfer.AccountQueryCDTO;
import com.ims.eplaton.eplatonFWK.transfer.*;
import com.ims.eplaton.foundation.helper.*;
import com.ims.eplaton.foundation.helper.logej.*;

public class DED0021000 implements IBizOperation
{
  public EPlatonEvent act(EPlatonEvent event) throws BizActionException
  {
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    AccountQueryCDTO rescdto = null;
    AccountQueryCDTO reqcdto = new AccountQueryCDTO();

    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[DED0021000 START]");

    commonDTO = (EPlatonCommonDTO)event.getCommon();
    tpsvcinfo = event.getTPSVCINFODTO();
    rescdto = (AccountQueryCDTO)event.getRequest();

    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"dfsafasfsadfa신규계좌에 대한 정보를 셋팅한다");
    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"1");
    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"1");
    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"1");
    reqcdto.setAccountNumber("0001100100000088");
    reqcdto.setBankCode("03");
    reqcdto.setAccountNumber("8888888888888888");

    event.setResponse(reqcdto);
    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[DED0021000  END]");
    return event;
  }

  public EPlatonEvent procact(EPlatonEvent event) throws BizActionException
  {
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    AccountQueryCDTO rescdto = null;
    AccountQueryCDTO reqcdto = new AccountQueryCDTO();

    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[DED0021000 START]");

    commonDTO = (EPlatonCommonDTO)event.getCommon();
    tpsvcinfo = event.getTPSVCINFODTO();
    rescdto = (AccountQueryCDTO)event.getRequest();

    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"dfsafasfsadfa신규계좌에 대한 정보를 셋팅한다");
    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"1");
    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"1");
    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"1");
    reqcdto.setAccountNumber("0001100100000088");
    reqcdto.setBankCode("03");
    reqcdto.setAccountNumber("8888888888888888");

    event.setResponse(reqcdto);
    LOGEJ.getInstance().printf(1,(EPlatonEvent)event,"==================[DED0021000  END]");
    return event;
  }


}