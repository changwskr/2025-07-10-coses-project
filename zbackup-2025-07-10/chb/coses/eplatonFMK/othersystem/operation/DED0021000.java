package com.chb.coses.eplatonFMK.othersystem.operation;

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

import com.chb.coses.eplatonFMK.business.operation.IBizOperation;
import com.chb.coses.eplatonFMK.transfer.EPlatonEvent;
import com.chb.coses.eplatonFMK.business.helper.logej.LOGEJ;
import com.chb.coses.deposit.transfer.AccountQueryCDTO;
import com.chb.coses.eplatonFMK.transfer.*;
import com.chb.coses.eplatonFMK.business.helper.TPMSVCAPI;

public class DED0021000 implements IBizOperation
{
  public EPlatonEvent act(EPlatonEvent event) throws BizActionException
  {
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    AccountQueryCDTO rescdto = null;
    AccountQueryCDTO reqcdto = new AccountQueryCDTO();

    LOGEJ.getInstance().printf((EPlatonEvent)event,"==================[DED0021000 START]");

    commonDTO = (EPlatonCommonDTO)event.getCommon();
    tpsvcinfo = event.getTPSVCINFODTO();
    rescdto = (AccountQueryCDTO)event.getRequest();

    LOGEJ.getInstance().printf((EPlatonEvent)event,"dfsafasfsadfa신규계좌에 대한 정보를 셋팅한다");
    LOGEJ.getInstance().printf((EPlatonEvent)event,"1");
    LOGEJ.getInstance().printf((EPlatonEvent)event,"1");
    LOGEJ.getInstance().printf((EPlatonEvent)event,"1");
    reqcdto.setAccountNumber("0001100100000088");
    reqcdto.setBankCode("03");
    reqcdto.setAccountNumber("8888888888888888");

    event.setResponse(reqcdto);
    LOGEJ.getInstance().printf((EPlatonEvent)event,"==================[DED0021000  END]");
    return event;
  }
}